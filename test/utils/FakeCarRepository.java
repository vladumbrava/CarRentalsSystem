package utils;

import domain.Car;
import repository.IRepository;

import java.util.*;

public class FakeCarRepository implements IRepository<UUID, Car> {
    private final Map<UUID, Car> storage = new HashMap<>();

    @Override
    public void add(UUID id, Car car) {
        storage.put(id, car);
    }

    @Override
    public Car findByID(UUID id) {
        return storage.get(id);
    }

    @Override
    public void delete(UUID id) {
        storage.remove(id);
    }

    @Override
    public Iterator<Car> iterator() {
        return storage.values().iterator();
    }
}