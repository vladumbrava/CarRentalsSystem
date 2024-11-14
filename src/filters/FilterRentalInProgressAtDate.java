package filters;

import domain.Rental;

import java.time.LocalDate;

public class FilterRentalInProgressAtDate implements AbstractFilter<Rental>{
    private final LocalDate date;

    public FilterRentalInProgressAtDate(LocalDate date) {
        this.date = date;
    }

    // rentals that are still in progress at 'date'
    @Override
    public boolean accept(Rental rental) {
        return rental.getReturnDate().isAfter(date);
    }
}
