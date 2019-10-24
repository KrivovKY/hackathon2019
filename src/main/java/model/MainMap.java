package model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import router.WayHomeRouter;

import java.util.List;

/**
 * Класс, описывающий карту
 */
public class MainMap {
    /**
     * Конструктор с обязательными параметрами
     * @param startPoint    старт
     * @param endPoint      база
     * @param restTime      оставшееся время
     */
    public MainMap(List<Point> points, Point startPoint, Point endPoint, Double restTime) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.restTime = restTime;
    }

    /**
     * Массив точек (вершины)
     */
    private List<Point> points;

    /**
     * маршруты (ребра)
     */
    private List<Route> routes;

    /**
     * автомобили
     */
    private List<Car> cars;

    /**
     * Точка начала
     */
    private Point startPoint;
    /**
     * Точка сброса денег (home, база и т.д.)
     */
    private Point endPoint;

    /**
     * Оставшееся время
     */
    private Double restTime;

    /**
     * Обновление списка маршрутов по новым пробкам
     *
     * @param newTraffics
     */
    public void updateJam(List<Traffic> newTraffics) {
        for (Traffic newTraffic : newTraffics) {
            for (Route oldRoute : routes) {
                if (oldRoute.getFrom().equals(newTraffic.getFrom()) && oldRoute.getTo().equals(newTraffic.getTo())) {
                    double newTime = oldRoute.getTime() * newTraffic.getJam();
                    oldRoute.setTime(newTime);
                }
            }
        }
    }

    /**
     * нужно возвращаться
     * @param car
     * @return
     */
    public Boolean needGoHome(Car car, Point nextPoint) {
        double nextHomeTime = WayHomeRouter.getHomeWayLength(this, nextPoint, new WayHomeRouter().getRoute(this, null, car));
        return nextHomeTime < restTime ;
     }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }
    public Double getRestTime() {
        return restTime;
    }

    public void setRestTime(Double restTime) {
        this.restTime = restTime;
    }

}
