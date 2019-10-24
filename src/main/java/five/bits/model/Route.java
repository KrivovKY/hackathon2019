package five.bits.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class Route implements Comparable {
    private static final Logger LOGGER = LogManager.getLogger(Route.class);
    /**
     * Сортировка от Игоря
     * определяется стоимость минуты в пути
     */
    public static Comparator<Route> smartComparatorTo = (o1, o2) -> (int) (o2.getTo().getMoney() / o2.getTime() - o1.getTo().getMoney() / o1.getTime());
    /**
     * Сотировка по сумме Конечной точки
     */
    public static Comparator<Route> moneyComparatorTo = (o1, o2) -> (int) (o2.getTo().getMoney() - o1.getTo().getMoney());
    /**
     * Сотировка по сумме начальной точки
     */
    public static Comparator<Route> moneyComparatorFrom = (o1, o2) -> (int) (o2.getFrom().getMoney() - o1.getFrom().getMoney());
    /**
     * Сотировка по расстоянию
     */
    public static Comparator<Route> timeComparator = (o1, o2) -> (int) (o1.getJamTime() - o2.getJamTime());
    private Point from;
    private Point to;
    private Double time;
    private Double jamTime;

    public Route(Point from, Point to, Double time) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.jamTime = time;
    }

    /**
     * Ищет прямой маршрут между заданными точками
     *
     * @param routes
     * @param from
     * @param to
     * @return
     */
    public static Route getRouteForPoint(List<Route> routes, Point from, Point to) {
        for (Route route : routes) {
            if ((route.getFrom().equals(from) && route.getTo().equals(to)) ||
                    (route.getFrom().equals(to) && route.getTo().equals(from))) {
                LOGGER.info("found route from {} to {}", from.getId(), to.getId());
                return route;
            }
        }
        return null;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getJamTime() {
        return jamTime;
    }

    public void setJamTime(Double jamTime) {
        this.jamTime = jamTime;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
