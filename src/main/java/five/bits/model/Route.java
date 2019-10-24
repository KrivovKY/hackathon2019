package five.bits.model;

import java.util.Comparator;
import java.util.List;

public class Route implements Comparable{
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

    /**
     * Ищет прямой маршрут между заданными точками
     * @param routes
     * @param from
     * @param to
     * @return
     */
    public static Route getRouteForPoint(List<Route> routes, Point from, Point to) {
        for (Route route : routes) {
            if ((route.getFrom().equals(from) && route.getTo().equals(to)) ||
                (route.getFrom().equals(to) && route.getTo().equals(from))) {
                return route;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    /**
     * Сортировка от Игоря
     * определяется стоимость минуты в пути
     */
    public static Comparator<Route> smartComparatorTo = (o1, o2) -> (int) (o1.getTo().getMoney()/o1.getTime() - o2.getTo().getMoney()/o2.getTime());

    /**
     * Сотировка по сумме Конечной точки
     */
    public static Comparator<Route> moneyComparatorTo = (o1, o2) -> (int) (o1.getTo().getMoney() - o2.getTo().getMoney());

    /**
     * Сотировка по сумме начальной точки
     */
    public static Comparator<Route> moneyComparatorFrom = (o1, o2) -> (int) (o1.getFrom().getMoney() - o2.getFrom().getMoney());

    /**
     * Сотировка по расстоянию
     */
    public static Comparator<Route> timeComparator = (o1, o2) -> (int) (o1.getJamTime() - o2.getJamTime());
}
