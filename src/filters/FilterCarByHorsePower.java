package filters;

import domain.Car;

public class FilterCarByHorsePower implements AbstractFilter<Car>{
    private final int horsePower;

    public FilterCarByHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public boolean accept(Car car) {
        return car.getHorsePower() >= horsePower;
    }
}
