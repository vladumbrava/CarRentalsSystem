package filters;

import domain.Rental;

import java.time.Duration;
import java.time.LocalDate;

public class FilterRentalTimeMoreThanDuration implements AbstractFilter<Rental>{
    private final Duration duration;

    public FilterRentalTimeMoreThanDuration(Duration duration) {
        this.duration = duration;
    }

    //rentals that have interval of time more than duration
    @Override
    public boolean accept(Rental rentalToCheck) {
        return LocalDate.now().plusDays(duration.toDays()).isBefore(rentalToCheck.getReturnDate());
    }
}
