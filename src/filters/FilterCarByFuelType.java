package filters;

import domain.Car;
import domain.FuelType;

public class FilterCarByFuelType implements AbstractFilter<Car>{
    private final FuelType fuelType;

    public FilterCarByFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public boolean accept(Car carToCheck) {
        return fuelType.equals(carToCheck.getFuelType());
    }
}
