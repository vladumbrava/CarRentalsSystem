package repository;

import domain.Car;
import filters.AbstractFilter;

public class FilteredCarRepository extends FilteredRepository<String, Car> {
    public FilteredCarRepository(AbstractFilter<Car> filter) {
        super(filter);
    }
}
