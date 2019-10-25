package five.bits.model;

import five.bits.router.NearPointRouter;
import five.bits.router.NextPointRouter;
import five.bits.router.WayHomeRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MapHandler {
    static final Double TOTAL_TIME = 480d;  //общее время задания
    private static final Logger LOGGER = LogManager.getLogger(MapHandler.class);
    private static final Point START = new Point("0", 0d);
    private static final Point HOME = new Point("1", 0d);
    private MainMap mainMap = new MainMap(START, HOME, TOTAL_TIME);

     /**
     * Создание модели данных
     */
    public void createMap(List<Car> cars, List<Point> points, List<Route> routes, List<Traffic> traffics) {
        LOGGER.info("create new Map");
        mainMap.setCars(cars);
        mainMap.setPoints(points);
        mainMap.setRoutes(routes);
        mainMap.updateJam(traffics);
        mainMap.setTeamSum(0d);
    }

    /**
     * Получаем следующую точку для авто
     *
     * @param currPoint текущая точка
     * @param car       машина
     * @return Следубщая точка
     */
    public Point getNextPoint(Point currPoint, Car car) {
        LOGGER.info("get next point for car {} from point {}", car.getId(), currPoint.getId());
        if (currPoint.getId().equals("1")) {
            LOGGER.debug("restore car capacity to EMPTY");
            car.setCapacity(0d);
        }
        List<Route> next;
        // Ищем Лучший вариант
        next = new NextPointRouter().getRoute(mainMap, currPoint, car);  // одна запись
        // Проверяем возврат домой
        if (next==null || mainMap.needGoHome(car, next.get(0).getTo())) {    //со следующей точки не успеваем вернуться
            // ищем ближайшую точку
            next = new NearPointRouter().getRoute(mainMap, currPoint, car);
            // Проверяем возврат домой
            if (next==null || mainMap.needGoHome(car, next.get(0).getTo())) {    //с ближайшей точки не успеваем вернуться
                if (currPoint.getId().equals("1")) {
                    LOGGER.debug("FINISH");

                } else {
                    next = new WayHomeRouter().getRoute(mainMap, currPoint, car);
                    LOGGER.info("need GO HOME. routes found {}", next);
                    mainMap.setTeamSum(mainMap.getTeamSum() + car.getCapacity());
                }
            }
        }
        if (next != null) {
            mainMap.getPoint(next.get(0).getTo().getId()).setMoney(0d); //обнуляем сумму (больше в нее не заходим)
            car.reduceRestTime(next.get(0).getTime());                   // уменьшаем оставшееся время
            car.addCapacity(next.get(0).getTo().getMoney());
            return next.get(0).getTo();
        } else {
            LOGGER.info("NEXT POINT NOT FOUND");

        }
        return null;
    }

    /**
     * Обновление данных о пробках
     *
     * @param newJams список новых коэффициентов
     */
    public void updateTraffic(List<Traffic> newJams) {
        mainMap.updateJam(newJams);
    }

    /**
     * Установить текущее заполнение машины
     *
     * @param currCar  машина
     * @param capacity сумма
     */
    public void setCarCapacity(Car currCar, Double capacity) {
        LOGGER.info("set capacity {} for car {}", capacity, currCar.getId());
        Boolean found = false;
        for (Car car : mainMap.getCars()) {
            if (currCar.equals(car)) {
                car.setCapacity(capacity);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Car not found");
        }
    }

    /**
     * Установка текущей суммы в хранилище
     *
     * @param summa сумма
     */
    public void setTeamSum(Double summa) {
        mainMap.setTeamSum(summa);
    }

    public List<Car> getCars() {
        return mainMap.getCars();
    }

    public Point getStart(){
        return mainMap.getStartPoint();
    }

    public void printPoints() {
        LOGGER.debug("TEAM summa = {}", mainMap.getTeamSum());
        mainMap.getPoints().forEach(LOGGER::debug);
    }
}

