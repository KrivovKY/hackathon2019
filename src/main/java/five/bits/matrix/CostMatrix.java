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
import five.bits.data.dto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
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

    public void setServices(List<Point> lp){
        for(Point p: lp){
            services.add(Service.Builder.newInstance(p.getP()).addSizeDimension(0, p.getMoney())
                .setLocation(Location.newInstance(p.getP())).build());
        }
    }

    public void setCostMatrix(List<Route> routes,List<Traffic> traffic){
        for(Route r: routes){
            List<Traffic> result = traffic.stream()
                .filter(item -> item.getA().equals(r.getA()) && item.getB().equals(r.getB()))
                .collect(Collectors.toList());
            if(result.size() == 0){
                LOGGER.info("Not found jam for a:{} b:{}", r.getA().toString(),r.getB().toString());
                continue;
            }
            if(result.size() > 1){
                LOGGER.info("More jams than 1 for a:{} b:{}", r.getA().toString(),r.getB().toString());
                continue;
            }
            r.setTime(r.getTime() * result.get(0).getJam());

            costMatrixBuilder.addTransportDistance(r.getA().toString(), r.getB().toString(), r.getTime());
            costMatrixBuilder.addTransportTime(r.getA().toString(), r.getB().toString(), r.getTime());
        }
    }

    public void setUp(){
        costMatrix = costMatrixBuilder.build();
        type = VehicleTypeImpl.Builder.newInstance("type").addCapacityDimension(0, 1000000).setCostPerTime(1).setCostPerDistance(1).build();
        vehicle = VehicleImpl.Builder.newInstance("vehicle")
            .setStartLocation(Location.newInstance("0")).
                setEndLocation(Location.newInstance("1")).setType(type).build();

        builder.addVehicle(vehicle).addAllJobs(services).setRoutingCost(costMatrix);
        vrp = builder.build();
        vra = Jsprit.Builder.newInstance(vrp).setProperty(Jsprit.Parameter.FAST_REGRET,"true").buildAlgorithm();//Jsprit.createAlgorithm(vrp);
        solutions = vra.searchSolutions();
    }

    public void printSolutions(){
        for(VehicleRoute vr: Solutions.bestOf(solutions).getRoutes()){
            printTrace(vehicle,vr,costMatrix);
            /*if(shortSolution > vr.getTourActivities().jobSize()) {

                if(!vrts.isEmpty())
                    vrts.clear();
                vrts.add(vr);
                shortSolution = vr.getTourActivities().jobSize();
            }*/
        }
    }

    static void printTrace(Vehicle vehicle, VehicleRoute vrts, VehicleRoutingTransportCosts costMatrix){
        Double totalTime = 0.0;
        int totalCapacity = 0;
        System.out.println("Start Point: " + vehicle.getStartLocation().getId());
        String fromPoint = vehicle.getStartLocation().getId();
        String endPoint = "";
        for (TourActivity ta : vrts.getActivities()) {
            endPoint = ta.getLocation().getId();
            System.out.println("Next Point: " + endPoint +
                " | Capacity: " + String.valueOf(ta.getSize().get(0)) +
                " | Time: " + ((VehicleRoutingTransportCostsMatrix) costMatrix).getDistance(fromPoint,endPoint));
            totalCapacity += ta.getSize().get(0);
            totalTime += ((VehicleRoutingTransportCostsMatrix) costMatrix).getDistance(fromPoint,endPoint);

            fromPoint = endPoint;
        }
        fromPoint = endPoint;
        endPoint = vehicle.getEndLocation().getId();
        System.out.println("End Point: " + endPoint + " | Time: " + ((VehicleRoutingTransportCostsMatrix) costMatrix).getDistance(fromPoint,endPoint));
        totalTime += ((VehicleRoutingTransportCostsMatrix) costMatrix).getDistance(fromPoint,endPoint);
        System.out.println("TotTime: " + totalTime);
        System.out.println("TotCapacity: " + totalCapacity);
    }

}
