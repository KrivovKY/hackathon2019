package five.bits.router;

import five.bits.model.Car;
import five.bits.model.MainMap;
import five.bits.model.Point;
import five.bits.model.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Путь до ближайшей точки
 */
public class NearPointRouter extends AbstractRouter {
    private static final Logger LOGGER = LogManager.getLogger(NearPointRouter.class);

    @Override
    public List<Route> getRoute(MainMap map, Point currPoint, Car currCar) {
        List<Route> localRoutes = new ArrayList<>();
        // выбираем все смежные с данной точкой маршруты
        for (Route route : map.getRoutes()) {
            if ((route.getTo().equals(currPoint) && (route.getTo().getMoney() != 0)) &&     // совпала точка КУДА
                    route.getTo().getMoney() < currCar.getCapacity()) {
                // Переварачивае ребро для сортировки
                localRoutes.add(new Route(route.getTo(), route.getFrom(), route.getTime()));
            }
            if ((route.getFrom().equals(currPoint) && (route.getFrom().getMoney() != 0)) &&     // совпала точка ОТКУДА
                    route.getFrom().getMoney() < currCar.getCapacity()) {
                localRoutes.add(route);
            }
        }
        localRoutes.sort(Route.timeComparator);
        result.add(localRoutes.get(0));
        LOGGER.info("found Next point {} for car {}", result.get(0).getTo().getId(), currCar.getId());
        return result;
    }
}
