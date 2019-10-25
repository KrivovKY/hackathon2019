package five.bits.router;

import five.bits.model.Car;
import five.bits.model.MainMap;
import five.bits.model.Point;
import five.bits.model.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class NextPointRouter extends AbstractRouter {
    private static final Logger LOGGER = LogManager.getLogger(NextPointRouter.class);

    @Override
    public List<Route> getRoute(MainMap map, Point current, Car currCar) {
        //LOGGER.debug("point {}, car {}, ", current, currCar);
        List<Route> localRoutes = new ArrayList<>();
        // выбираем все смежные с данной точкой маршруты
        //LOGGER.debug("Routes count {}", map.getRoutes().size());
        for (Route route : map.getRoutes()) {
            //LOGGER.debug("check route {}", route);
            if (route.getTo().equals(current)) {
                Double moneyFrom = getMoneyForPoint(map, route.getFrom());
                //LOGGER.debug("found point To {} with money {}", route.getTo(), moneyFrom);
                if (moneyFrom !=0 && moneyFrom < currCar.getRestCapacity()) {
                    route.getFrom().setMoney(moneyFrom);
                    // Переварачиваем ребро для сортировки
                    localRoutes.add(new Route(route.getTo(), route.getFrom(), route.getTime()));
                }
            }
            if (route.getFrom().equals(current)) {
                Double moneyTo = getMoneyForPoint(map, route.getTo());
                //LOGGER.debug("found point From {} with money {}", route.getFrom(), moneyTo);
                if (moneyTo !=0 && moneyTo < currCar.getRestCapacity()) {
                    route.getTo().setMoney(moneyTo);
                    localRoutes.add(route);
                }
            }
        }
        //localRoutes.forEach(route -> LOGGER.debug(route.toString()));
        if (!localRoutes.isEmpty()) {
            localRoutes.sort(Route.smartComparatorTo);
            result.add(localRoutes.get(0));
            LOGGER.info("found route {} for car {}", result, currCar.getId());
            return result;
        } else {
            LOGGER.debug("NEXT route not found");
            return null;
        }
    }
}
