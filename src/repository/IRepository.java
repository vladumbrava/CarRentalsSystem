package repository;

import domain.Identifiable;

import java.util.Iterator;

public interface IRepository<ID, T extends Identifiable<ID>> {
    void add(ID idToAdd, T objectToAdd);

    void delete(ID idToDelete);

    Iterator<T> iterator();

    T findByID(ID idUsedToFindObject);
}
