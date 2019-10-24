package router;

import model.Car;
import model.MainMap;
import model.Point;
import model.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Путь до ближайшей точки
 */
public class NearPointRouter extends AbstractRouter {
    @Override
    public List<Point> getRoute(MainMap map, Point currPoint, Car currCar) {
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
        result.add(localRoutes.get(0).getTo());
        return result;
    }
}
