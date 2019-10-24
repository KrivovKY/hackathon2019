package five.bits.data.converters;

import five.bits.model.Point;
import five.bits.model.Traffic;

import java.util.ArrayList;
import java.util.List;

public class TrafficConverter {

    public static List<Traffic> getTrafficList(List<five.bits.data.dto.Traffic> jsonTraffics) {
        List<Traffic> traffics = new ArrayList<>();
        for (five.bits.data.dto.Traffic jsonTraffic : jsonTraffics) {
            traffics.add(new Traffic(
                    new Point(jsonTraffic.getA().toString(), 0d),
                    new Point(jsonTraffic.getB().toString(), 0d),
                    jsonTraffic.getJam()));
        }
        return traffics;
    }

}
