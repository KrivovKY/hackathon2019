package five.bits.router;

import five.bits.model.Car;
import five.bits.model.MainMap;
import five.bits.model.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRouter {
    List<Point> result = new ArrayList<>();

    public abstract List<Point> getRoute(MainMap map, Point currPoint, Car currCar);

}
