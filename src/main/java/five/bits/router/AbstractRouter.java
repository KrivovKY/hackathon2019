package five.bits.router;

import five.bits.model.Car;
import five.bits.model.MainMap;
import five.bits.model.Point;
import five.bits.model.Route;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRouter {
    List<Route> result = new ArrayList<>();

    public abstract List<Route> getRoute(MainMap map, Point currPoint, Car currCar);

}
