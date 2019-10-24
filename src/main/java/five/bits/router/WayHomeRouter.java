package five.bits.router;

import five.bits.model.Car;
import five.bits.model.MainMap;
import five.bits.model.Point;
import five.bits.model.Route;

import java.util.List;

/**
 * Возвращает путь до базовой точки
 */
public class WayHomeRouter extends AbstractRouter {

    /**
     * длинна пути на базу из заданной точки
     *
     * @param map
     * @param fromPoint
     * @param homeWay
     * @return
     */
    public static Double getHomeWayLength(MainMap map, Point fromPoint, List<Route> homeWay) {
        double wayTime = 0;
        Point homePoint = map.getEndPoint(); //TODO homeWay.get(0);
        Route homeRoute = Route.getRouteForPoint(map.getRoutes(), fromPoint, homePoint);
        if (homeRoute != null) {
            wayTime = +homeRoute.getTime();
        }
        return wayTime;
    }

    /**
     * Заглушка !!!! ВСЕГДА ПО ПРЯМОЙ
     * Путь домой
     *
     * @param map
     * @param current
     * @return
     */
    @Override
    public List<Route> getRoute(MainMap map, Point current, Car currCar) {
        result.add(Route.getRouteForPoint(map.getRoutes(), current, map.getEndPoint()));
        return result;
    }
}
