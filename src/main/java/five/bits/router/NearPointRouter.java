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
            if (route.getTo().equals(currPoint)) {
                Double moneyFrom = getMoneyForPoint(map, route.getFrom());
                if (moneyFrom !=0 && moneyFrom < currCar.getRestCapacity()) {
                    route.getFrom().setMoney(moneyFrom);
                    // Переварачиваем ребро для сортировки
                    localRoutes.add(new Route(route.getTo(), route.getFrom(), route.getTime()));
                }
            }
            if (route.getFrom().equals(currPoint)) {
                Double moneyTo = getMoneyForPoint(map, route.getTo());
                if (moneyTo !=0 && moneyTo < currCar.getRestCapacity()) {
                    route.getTo().setMoney(moneyTo);
                    localRoutes.add(route);
                }
            }
        }
        if (!localRoutes.isEmpty()) {
            localRoutes.sort(Route.timeComparator);
            result.add(localRoutes.get(0));
            LOGGER.info("found route {} for car {}", result, currCar.getId());
            return result;
        } else {
            LOGGER.debug("NEARBY route not found");
            return null;
        }
    }
}
