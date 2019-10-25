package five.bits.matrix;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.cost.VehicleRoutingTransportCosts;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.problem.vehicle.Vehicle;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import five.bits.data.dto.Point;
import five.bits.data.dto.Route;
import five.bits.data.dto.Traffic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CostMatrix {
    private static final Logger LOGGER = LogManager.getLogger(CostMatrix.class);
    private List<Service> services = new ArrayList<Service>();
    VehicleRoutingTransportCostsMatrix.Builder costMatrixBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(true);
    VehicleRoutingTransportCosts costMatrix;
    VehicleType type;
    VehicleImpl vehicle;
    VehicleRoutingProblem.Builder builder = VehicleRoutingProblem.Builder.newInstance().setFleetSize(VehicleRoutingProblem.FleetSize.INFINITE);
    VehicleRoutingProblem vrp;
    VehicleRoutingAlgorithm vra;
    Collection<VehicleRoutingProblemSolution> solutions;
    private Integer dimension = 1000000;
    private String startLoc = "0";
    List<String> visited = new ArrayList<String>();

    public void setServices(List<Point> lp) {
        services = new ArrayList<Service>();
        for (Point p : lp) {
            if(p.getMoney() > 0 && !visited.contains(p.getP().toString()))
                services.add(Service.Builder.newInstance(p.getP().toString()).addSizeDimension(0, p.getMoney())
                        .setLocation(Location.newInstance(p.getP())).build());
        }
    }

    public void setCostMatrix(List<Route> routes, List<Traffic> traffic) {
        costMatrixBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(true);
        for (Route r : routes) {
            List<Traffic> result = traffic.stream()
                    .filter(item -> item.getA().equals(r.getA()) && item.getB().equals(r.getB()))
                    .collect(Collectors.toList());
            if (result.size() == 0) {
                LOGGER.info("Not found jam for a:{} b:{}", r.getA().toString(), r.getB().toString());
                continue;
            }
            if (result.size() > 1) {
                LOGGER.info("More jams than 1 for a:{} b:{}", r.getA().toString(), r.getB().toString());
                continue;
            }
            r.setTime(r.getTime() * result.get(0).getJam());

            costMatrixBuilder.addTransportDistance(r.getA().toString(), r.getB().toString(), r.getTime());
            costMatrixBuilder.addTransportTime(r.getA().toString(), r.getB().toString(), r.getTime());
        }
    }

    public void setUp() {
        costMatrix = costMatrixBuilder.build();

        type = VehicleTypeImpl.Builder.newInstance("type")
                .addCapacityDimension(0, dimension)
                .setCostPerTime(1).setCostPerDistance(1)
                .build();
        vehicle = VehicleImpl.Builder.newInstance("sb0")
                .setStartLocation(Location.newInstance(startLoc)).
                        setEndLocation(Location.newInstance("1")).setType(type).build();

        builder = VehicleRoutingProblem.Builder.newInstance().setFleetSize(VehicleRoutingProblem.FleetSize.INFINITE).addVehicle(vehicle).addAllJobs(services).setRoutingCost(costMatrix);
        vrp = builder.build();
        vra = Jsprit.Builder.newInstance(vrp).setProperty(Jsprit.Parameter.THREADS,"4").buildAlgorithm();//Jsprit.createAlgorithm(vrp);
        solutions = vra.searchSolutions();
    }

    public String getVehicleId(){
        if(Solutions.bestOf(solutions).getRoutes().size() > 0)
            return Solutions.bestOf(solutions).getRoutes().stream().findFirst().get().getVehicle().getId();
        else
            return vehicle.getId();
    }
    public Integer getNextPoint(boolean decrement){
        if(Solutions.bestOf(solutions).getRoutes().size() == 0) {
            LOGGER.info("END {}", new Date().toString());
            return 1;
        }
        String idPoint = Solutions.bestOf(solutions).getRoutes().stream().findFirst().get().
                getTourActivities().getActivities().stream().findFirst().get().
                getLocation().getId();
        LOGGER.info("Point {}, Total Dimension Before {}", idPoint, dimension);

        services.stream().filter(item -> item.getId().equalsIgnoreCase(idPoint)).forEach(item -> dimension = dimension - (decrement ? 0 : item.getSize().get(0)));
        startLoc = idPoint;
        LOGGER.info("Point {}, Total Dimension After {}", idPoint, dimension);
        if(decrement)
                visited.add(idPoint);
        if(dimension < 0)
            LOGGER.info("SUKA HERE");
        return Integer.valueOf(idPoint);
    }
    public void printSolutions() {
        for (VehicleRoute vr : Solutions.bestOf(solutions).getRoutes()) {
            printTrace(vehicle, vr, costMatrix);
            /*if(shortSolution > vr.getTourActivities().jobSize()) {

                if(!vrts.isEmpty())
                    vrts.clear();
                vrts.add(vr);
                shortSolution = vr.getTourActivities().jobSize();
            }*/
        }
    }

    static void printTrace(Vehicle vehicle, VehicleRoute vrts, VehicleRoutingTransportCosts costMatrix) {
        Double totalTime = 0.0;
        int totalCapacity = 0;
        LOGGER.debug("Start Point: {}", vehicle.getStartLocation().getId());
        String fromPoint = vehicle.getStartLocation().getId();
        String endPoint = "";
        for (TourActivity ta : vrts.getActivities()) {
            endPoint = ta.getLocation().getId();
            LOGGER.debug("Next Point: {} | Capacity: {} | Time: {}", endPoint, ta.getSize().get(0), ((VehicleRoutingTransportCostsMatrix) costMatrix).getDistance(fromPoint, endPoint));
            totalCapacity += ta.getSize().get(0);
            totalTime += ((VehicleRoutingTransportCostsMatrix) costMatrix).getDistance(fromPoint, endPoint);

            fromPoint = endPoint;
        }
        fromPoint = endPoint;
        endPoint = vehicle.getEndLocation().getId();
        LOGGER.debug("End Point: {} | Time: {}", endPoint, ((VehicleRoutingTransportCostsMatrix) costMatrix).getDistance(fromPoint, endPoint));
        totalTime += ((VehicleRoutingTransportCostsMatrix) costMatrix).getDistance(fromPoint, endPoint);
        LOGGER.debug("TotTime: {}", totalTime);
        LOGGER.debug("TotCapacity: {}", totalCapacity);
    }

}
