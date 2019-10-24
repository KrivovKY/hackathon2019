package five.bits.router;

import five.bits.model.Car;
import five.bits.model.MainMap;
import five.bits.model.Point;
import five.bits.model.Route;

import java.util.ArrayList;
import java.util.List;

public class NextPointRouter extends AbstractRouter {

    @Override
    public List<Route> getRoute(MainMap map, Point current, Car currCar) {
        List<Route> localRoutes = new ArrayList<>();
        // выбираем все смежные с данной точкой маршруты
        for (Route route : map.getRoutes()) {
            if ((route.getTo().equals(current) && (route.getTo().getMoney() != 0)) &&     // совпала точка КУДА
                    route.getTo().getMoney() < currCar.getCapacity()) {
                // Переварачиваем ребро для сортировки
                localRoutes.add(new Route(route.getTo(), route.getFrom(), route.getTime()));
            }
            if ((route.getFrom().equals(current) && (route.getFrom().getMoney() != 0)) &&     // совпала точка ОТКУДА
                    route.getFrom().getMoney() < currCar.getCapacity()) {
                localRoutes.add(route);
            }
        }
        localRoutes.sort(Route.smartComparatorTo);
        result.add(localRoutes.get(0));
        return result;
    }
}
