package five.bits.model;

import five.bits.router.WayHomeRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Класс, описывающий карту
 */
public class MainMap {
    private static final Logger LOGGER = LogManager.getLogger(MainMap.class);
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
     * Общая сумма в хранилище (для отладки и проверки)
     */
    private Double teamSum;

    /**
     * Конструктор с обязательными параметрами
     *
     * @param startPoint старт
     * @param endPoint   база
     * @param restTime   оставшееся время
     */
    public MainMap(Point startPoint, Point endPoint, Double restTime) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.restTime = restTime;
    }

    /**
     * Обновление списка маршрутов по новым пробкам
     *
     * @param newTraffics пробки
     */
    public void updateJam(List<Traffic> newTraffics) {
        LOGGER.info("update Jams");
        for (Traffic newTraffic : newTraffics) {
            for (Route oldRoute : routes) {
                if (oldRoute.getFrom().equals(newTraffic.getFrom()) && oldRoute.getTo().equals(newTraffic.getTo())) {
                    double newTime = oldRoute.getTime() * newTraffic.getJam();
                    oldRoute.setJamTime(newTime);
                }
            }
        }
    }

    /**
     * нужно возвращаться
     *
     * @param car
     * @return
     */
    public Boolean needGoHome(Car car, Point nextPoint) {
        LOGGER.debug("check route for car {} to home from {}", car, nextPoint);
        LOGGER.debug("car rest capacity {}", car.getRestCapacity());
        double nextHomeTime = WayHomeRouter.getHomeWayLength(this, nextPoint, new WayHomeRouter().getRoute(this, nextPoint, car));
        //LOGGER.info("best way to Home is {}, rest time is {}", nextHomeTime, car.getRestTime());
        Boolean go = nextHomeTime > car.getRestTime() || car.getRestCapacity()<nextPoint.getMoney();
        if (go) {
            LOGGER.info("NEED GO HOME");
        }
        return go;
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

    public Double getTeamSum() {
        return teamSum;
    }

    public void setTeamSum(Double teamSum) {
        this.teamSum = teamSum;
    }

    public Point getPoint(String id) {
        for (Point point : this.points) {
            if (id.equals(point.getId())) {
                return point;
            }
        }
        return null;
    }
}
