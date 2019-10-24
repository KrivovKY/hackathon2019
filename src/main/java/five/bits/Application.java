package five.bits;

import five.bits.data.ClientMessage;
import five.bits.data.DataProvider;
import five.bits.data.GotoMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        DataProvider provider = new DataProvider("ws://localhost:8080/race");
        provider.send(new ClientMessage("5bit"));

        provider.getCars().forEach(c -> {
            LOGGER.info(c.getId());
        });

        LOGGER.info("points: {}", provider.getPoints().size());
        LOGGER.info("traffic: {}", provider.getTraffic().size());
        LOGGER.info("routes: {}", provider.getRoutes().size());

        provider.send(new GotoMessage(2, "sb0"));
        LOGGER.info("traffic: {}", provider.getTraffic().size());

        provider.close();

    }

}
