package five.bits;

import five.bits.data.TeamMessage;
import five.bits.data.DataProvider;
import five.bits.data.GotoMessage;
import five.bits.matrix.CostMatrix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class Application {
    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        DataProvider provider = new DataProvider("ws://localhost:8080/race");
        //DataProvider provider = new DataProvider("ws://172.30.9.50:8080/race");
        provider.send(new TeamMessage("5bit"));

        provider.getCars().forEach(c -> {
            LOGGER.info(c.getId());
        });
        LOGGER.info("points: {}", provider.getPoints().size());
        LOGGER.info("traffic: {}", provider.getTraffic().size());
        LOGGER.info("routes: {}", provider.getRoutes().size());
        CostMatrix matrix = new CostMatrix();
        matrix.setServices(provider.getPoints());
        matrix.setCostMatrix(provider.getRoutes(),provider.getTraffic());
        matrix.setUp();

        matrix.printSolutions();
        //Thread.sleep(10000);
        boolean whileDo = true;
        Integer lastGoTo = 0;
        while(whileDo){
            if(matrix.getNextPoint(false) == lastGoTo) {
                whileDo = false;
                continue;
            }
            provider.send(new GotoMessage(matrix.getNextPoint(true), matrix.getVehicleId()));
            lastGoTo = matrix.getNextPoint(false);
            LOGGER.info("traffic: {}", provider.getTraffic().size());
            matrix.setServices(provider.getPoints());
            matrix.setCostMatrix(provider.getRoutes(),provider.getTraffic());
            matrix.setUp();
        }

        //Thread.sleep(60000);
        provider.close();

    }

}
