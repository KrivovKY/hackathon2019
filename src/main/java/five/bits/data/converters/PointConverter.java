package five.bits.data.converters;

import five.bits.model.Point;

import java.util.ArrayList;
import java.util.List;

public class PointConverter {

    public static List<Point> getPointList(List<five.bits.data.dto.Point> jsonPoints) {
        List<Point> points = new ArrayList<>();
        for (five.bits.data.dto.Point jsonPoint: jsonPoints) {
            points.add(new Point(jsonPoint.getP().toString(), jsonPoint.getMoney().doubleValue()));
        }
        return points;
    }
}
