package five.bits;

import five.bits.model.*;
import five.bits.router.NearPointRouter;
import five.bits.router.NextPointRouter;
import five.bits.model.MainMap;

import java.util.List;

public class MainApp {

    private static final Point START = new Point("0", 0d);
    private static final Point HOME = new Point("1", 0d);
    private static final Double TOTAL_TIME = 480d;  //общее время задания
    private MainMap mainMap = new MainMap(START, HOME, TOTAL_TIME);

    /**
     * Создание модели данных
     */
    public void createMap (List<Car> cars, List<Point> points, List<Route> routes, List<Traffic> traffics) {
        mainMap.setCars(cars);
        mainMap.setPoints(points);
        mainMap.setRoutes(routes);
        mainMap.updateJam(traffics);
    }

    /**
     * Получаем следующую точку для авто
     * @param currPoint текущая точка
     * @param car машина
     * @return Следубщая точка
     */
    public Point getNextPoint (Point currPoint, Car car) {
        Point next;
        // Ищем Лучший вариант
        next = new NextPointRouter().getRoute(mainMap, currPoint, car).get(0);  // одна запись
        // Проверяем возврат домой
        if (mainMap.needGoHome(car, next)) {    //со следующей точки не успеваем вернуться
            // ищем ближайшую точку
            next = new NearPointRouter().getRoute(mainMap, currPoint, car).get(0);
            // Проверяем возврат домой
            if (mainMap.needGoHome(car, next)) {    //с ближайшей точки не успеваем вернуться
                next = mainMap.getEndPoint();
            }
        }
        if (next != null) {
            next.setMoney(0d);  //обнуляем сумму (больше в нее не заходим)
        }
        return next;
    }

    /**
     * Обновление данных о пробках
     * @param newJams список новых коэффициентов
     */
    public void updateTraffic(List<Traffic> newJams) {
        mainMap.updateJam(newJams);
    }

    /**
     * Установить текущее заполнение машины
     * @param currCar машина
     * @param capacity сумма
     */
    public void setCarCapacity(Car currCar, Double capacity) {
        Boolean found = false;
        for (Car car: mainMap.getCars()) {
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
     * @param summa сумма
     */
    public void setTeamSum(Double summa) {
        mainMap.setTeamSum(summa);
    }
}
