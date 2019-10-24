package five.bits.data.converters;

import five.bits.model.Car;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarConverter {

    public static List<Car> getCarList(List<five.bits.data.dto.Car> jsonCars) {
        List<Car> cars = new ArrayList<>();
        for (five.bits.data.dto.Car jsonCar: jsonCars) {
            cars.add(new Car(jsonCar.getId(), jsonCar.getCapacity()));
        }
        return cars;
    }
}
