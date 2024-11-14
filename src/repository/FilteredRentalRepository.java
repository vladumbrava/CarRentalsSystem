package repository;

import domain.Rental;
import filters.AbstractFilter;

public class FilteredRentalRepository extends FilteredRepository<String, Rental> {
    public FilteredRentalRepository(AbstractFilter<Rental> filter) {
        super(filter);
    }
}
