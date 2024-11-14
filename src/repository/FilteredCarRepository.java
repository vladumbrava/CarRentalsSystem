package repository;

import domain.Car;
import filters.AbstractFilter;

import java.util.UUID;

public class FilteredCarRepository extends FilteredRepository<UUID, Car> {
    public FilteredCarRepository(AbstractFilter<Car> filter) {
        super(filter);
    }
}
