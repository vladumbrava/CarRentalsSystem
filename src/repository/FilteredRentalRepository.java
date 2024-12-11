package repository;

import domain.Rental;
import filters.AbstractFilter;

import java.util.UUID;

public class FilteredRentalRepository extends FilteredRepository<UUID, Rental> {
    public FilteredRentalRepository(AbstractFilter<Rental> filter) {
        super(filter);
    }
}
