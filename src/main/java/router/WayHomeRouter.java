package router;

import model.Car;
import model.MainMap;
import model.Point;
import model.Route;

import java.util.List;

/**
 * Возвращает путь до базовой точки
 */
public class WayHomeRouter extends AbstractRouter {

    /**
     * Заглушка !!!! ВСЕГДА ПО ПРЯМОЙ
     * Путь домой
     * @param map
     * @param current
     * @return
     */
    @Override
    public List<Point> getRoute(MainMap map, Point current, Car currCar) {
        result.add(map.getEndPoint());
        return result;
    }

    /**
     * длинна пути на базу из заданной точки
     * @param map
     * @param fromPoint
     * @param homeWay
     * @return
     */
    public static Double getHomeWayLength(MainMap map, Point fromPoint, List<Point> homeWay) {
        double wayTime = 0;
        Point homePoint = map.getEndPoint(); //TODO homeWay.get(0);
        Route homeRoute = Route.getRouteForPoint(map.getRoutes(), fromPoint, homePoint);
        if (homeRoute!=null) {
            wayTime =+ homeRoute.getTime();
        }
        return wayTime;
    }
}
