package repository;

import domain.Identifiable;
import java.util.HashMap;
import java.util.Iterator;

public class MemoryRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T> {
    protected HashMap<ID, T> map = new HashMap<>();

    @Override
    public void add(ID idToAdd, T objectToAdd) {
        map.put(idToAdd, objectToAdd);
    }

    @Override
    public void delete(ID idToDelete) {
        map.remove(idToDelete);
    }

    @Override
    public Iterator<T> iterator() {
        return map.values().iterator();
    }

    @Override
    public T findByID(ID idUsedToFindObject) {
        return map.get(idUsedToFindObject);
    }

    @Override
    public void addInitialObjects() {

    }
}
