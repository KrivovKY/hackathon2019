package router;

import model.Car;
import model.MainMap;
import model.Point;
import model.Route;

import java.util.ArrayList;
import java.util.List;

public class NextPointRouter extends AbstractRouter {

    @Override
    public List<Point> getRoute(MainMap map, Point current, Car currCar) {
        List<Route> localRoutes = new ArrayList<>();
        // выбираем все смежные с данной точкой маршруты
        for (Route route : map.getRoutes()) {
            if ((route.getTo().equals(current) && (route.getTo().getMoney() != 0)) &&     // совпала точка КУДА
                route.getTo().getMoney() < currCar.getCapacity()) {
                // Переварачивае ребро для сортировки
                localRoutes.add(new Route(route.getTo(), route.getFrom(), route.getTime()));
            }
            if ((route.getFrom().equals(current) && (route.getFrom().getMoney() != 0)) &&     // совпала точка ОТКУДА
                route.getFrom().getMoney() < currCar.getCapacity()) {
                localRoutes.add(route);
            }
        }
        localRoutes.sort(Route.moneyComparatorTo);
        result.add(localRoutes.get(0).getTo());
        return result;
    }
}
