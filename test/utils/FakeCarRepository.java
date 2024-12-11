package utils;

import domain.Car;
import domain.Colour;
import domain.FuelType;
import repository.IRepository;

import java.util.*;

public class FakeCarRepository implements IRepository<UUID, Car> {

    public boolean deleteShouldThrowException = false;
    public boolean addShouldThrowException = false;

    @Override
    public void add(UUID id, Car car) {
        if(addShouldThrowException) {
            throw new IllegalArgumentException("Test exception. Could not add car.");
        }
    }

    @Override
    public Car findByID(UUID id) {
        return new Car("1",1,5, FuelType.diesel, Colour.red);
    }

    @Override
    public void delete(UUID id) {
        if(deleteShouldThrowException) {
            throw new IllegalArgumentException("Test exception. Could not delete car.");
        }
    }

    @Override
    public Iterator<Car> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Car next() {
                return null;
            }
        };
    }
}