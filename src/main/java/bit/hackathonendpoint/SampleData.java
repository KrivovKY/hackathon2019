package bit.hackathonendpoint;

import bit.hackathonendpoint.data.Car;
import bit.hackathonendpoint.data.Point;
import bit.hackathonendpoint.data.Route;
import bit.hackathonendpoint.data.Traffic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
public class SampleData {

    private List<Car> cars;
    private List<Point> points;
    private List<Route> route;

    @GetMapping("/start")
    public Object start() {
        cars = new ArrayList<>();
        cars.add(new Car("car1", 1000000D));
        cars.add(new Car("car2", 1000000D));
        points = new ArrayList<>();
        points.add(new Point("0", 500000D));
        points.add(new Point("1", 300000D));
        points.add(new Point("2", 100000D));
        points.add(new Point("3", 5000D));
        points.add(new Point("4", 20000D));

        route = new ArrayList<>();
        route.add(new Route("0", "1", 10d));
        route.add(new Route("0", "2", 12d));
        route.add(new Route("1", "2", 12d));
        route.add(new Route("1", "3", 20d));
        route.add(new Route("2", "4", 15d));
        route.add(new Route("2", "5", 18d));
        route.add(new Route("4", "5", 6d));
        HashMap<String, Object> data = new HashMap<>();
        data.put("token", "random");
        data.put("cars", cars);
        return data;
    }

    @GetMapping("/info")
    public Object info() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("points", points);
        data.put("route", route);
        data.put("traffic", genTraffic());
        return data;
    }

    @GetMapping("/goto")
    public Object step() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("trafficjam", genTraffic());
        return data;
    }

    protected List<Traffic> genTraffic() {
        List<Traffic> traffic = new ArrayList<>();
        Random random = new Random();
        int cnt = random.nextInt(points.size());
        for (int i=0; i<cnt; i++) {
            Integer from = random.nextInt(points.size());
            Integer to = from;
            while (from.equals(to)) {
                to = random.nextInt(points.size());
            }
            traffic.add(new Traffic(from.toString(), to.toString(), 11d));
        }
        return traffic;
    }
}
