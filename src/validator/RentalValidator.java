package validator;

import domain.Rental;

import java.time.LocalDate;

public class RentalValidator {

    public void validateRental(Rental rentalToBeValidated) {
        if(rentalToBeValidated.getReturnDate().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Invalid date. Please enter a date in the future.");
        }
    }
}
