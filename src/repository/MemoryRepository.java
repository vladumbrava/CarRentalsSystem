package repository;

import domain.Identifiable;
import java.util.HashMap;
import java.util.Iterator;

public class MemoryRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T> {
    protected final HashMap<ID, T> map = new HashMap<>();

    @Override
    public void add(ID id, T elem) {
        map.put(id, elem);
    }

    @Override
    public void delete(ID id) {
        map.remove(id);
    }

    @Override
    public Iterator<T> iterator() {
        return map.values().iterator();
    }

    @Override
    public T findByID(ID id) {
        return map.get(id);
    }
}
