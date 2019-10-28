package five.bits;

import five.bits.data.TeamMessage;
import five.bits.data.DataProvider;
import five.bits.data.GotoMessage;
import five.bits.data.converters.CarConverter;
import five.bits.data.converters.PointConverter;
import five.bits.data.converters.RouteConvert;
import five.bits.data.converters.TrafficConverter;
import five.bits.matrix.CostMatrix;
import five.bits.model.MapHandler;
import five.bits.model.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Application {
    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        //DataProvider provider = new DataProvider("ws://localhost:8080/race");
        DataProvider provider = new DataProvider("ws://172.30.9.50:8080/race");
        provider.send(new TeamMessage("5bit_fast"));

        provider.getCars().forEach(c -> {
            LOGGER.info(c.getId());
        });

        LOGGER.info("points: {}", provider.getPoints().size());
        LOGGER.info("traffic: {}", provider.getTraffic().size());
        LOGGER.info("routes: {}", provider.getRoutes().size());
        /*
        CostMatrix matrix = new CostMatrix();
        matrix.setServices(provider.getPoints());
        matrix.setCostMatrix(provider.getRoutes(),provider.getTraffic());
        matrix.setUp();
        matrix.printSolutions();
        */

        MapHandler mapHandler = new MapHandler();
        mapHandler.createMap(CarConverter.getCarList(provider.getCars()),
                PointConverter.getPointList(provider.getPoints()),
                RouteConvert.getRouteList(provider.getRoutes()),
                TrafficConverter.getTrafficList(provider.getTraffic()));
        mapHandler.getCars().forEach(car -> {
            try {
                Point prevPoint = mapHandler.getStart();
                while (car.getRestTime()>0) {

                    Point nextPoint = mapHandler.getNextPoint(prevPoint, car);
                    if (nextPoint!=null) {
                        provider.send(new GotoMessage(Integer.parseInt(nextPoint.getId()), car.getId()));
                    } else {
                        break;
                    }
                    prevPoint = nextPoint;
                    mapHandler.updateTraffic(TrafficConverter.getTrafficList(provider.getTraffic()));
                }
                mapHandler.printPoints();
            } catch (IOException | InterruptedException | ExecutionException e) {
                LOGGER.error("alarm", e);
            }
        });

        //Thread.sleep(10000);
        //provider.send(new GotoMessage(2, "sb0"));
        LOGGER.info("traffic: {}", provider.getTraffic().size());

        //Thread.sleep(60000);
        provider.close();

    }

}
