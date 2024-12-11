package filters;

import domain.Car;

public class FilterCarByNumSeats implements AbstractFilter<Car>{
    private final int numberSeats;

    public FilterCarByNumSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    @Override
    public boolean accept(Car carToCheck) {
        return carToCheck.getNumberSeats() == numberSeats;
    }
}
