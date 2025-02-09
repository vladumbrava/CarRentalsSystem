package repository;

import domain.Identifiable;
import filters.AbstractFilter;

public class FilteredRepository<ID, T extends Identifiable<ID>> extends MemoryRepository<ID,T> {
    private AbstractFilter<T> filter;

    public FilteredRepository(AbstractFilter<T> filter) {
        this.filter = filter;
    }

    @Override
    public void add(ID idToAdd, T objectToAddIfCompliant) {
        if (filter.accept(objectToAddIfCompliant))
            super.add(idToAdd, objectToAddIfCompliant);
    }

    public void setFilter(AbstractFilter<T> filter) {
        this.filter = filter;
    }
}
