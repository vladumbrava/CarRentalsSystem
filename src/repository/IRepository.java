package repository;

import domain.Identifiable;

import java.util.Iterator;

public interface IRepository<ID, T extends Identifiable<ID>> {
    void add(ID id, T elem);

    void delete(ID id);

    Iterator<T> iterator();

    T findByID(ID id);
}
