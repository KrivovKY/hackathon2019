package five.bits.data.converters;

import five.bits.model.Point;
import five.bits.model.Route;

import java.util.ArrayList;
import java.util.List;

public class RouteConvert {

    public static List<Route> getRouteList(List<five.bits.data.dto.Route> jsonRoutes) {
        List<Route> routes = new ArrayList<>();
        for (five.bits.data.dto.Route jsonRoute : jsonRoutes) {
            routes.add(new Route(
                    new Point(jsonRoute.getA().toString(), 0d),
                    new Point(jsonRoute.getB().toString(), 0d),
                    jsonRoute.getTime()));
        }
        return routes;
    }
}
