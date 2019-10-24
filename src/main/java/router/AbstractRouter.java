package router;

import model.Car;
import model.MainMap;
import model.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRouter {
    List<Point> result = new ArrayList<>();

    public abstract List<Point> getRoute(MainMap map, Point currPoint, Car currCar);

}
