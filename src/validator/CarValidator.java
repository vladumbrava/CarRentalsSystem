package validator;

import domain.Car;

import java.util.ArrayList;
import java.util.Arrays;

public class CarValidator {

    public static final ArrayList<Integer> VALID_NUMBER_SEATS = new ArrayList<>(Arrays.asList(2, 4, 5, 7));


    public void validateCar(Car carToBeValidated) {
        if (!VALID_NUMBER_SEATS.contains(carToBeValidated.getNumberSeats())){
            throw new IllegalArgumentException("Incorrect value for 'numberSeats'.");
        }
        if (carToBeValidated.getHorsePower() <= 0){
            throw new IllegalArgumentException("Incorrect value for 'horsePower'");
        }
    }
}
