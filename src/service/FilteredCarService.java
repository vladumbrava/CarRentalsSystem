package service;

import domain.Car;
import filters.AbstractFilter;
import repository.IRepository;

import java.util.ArrayList;

public class FilteredCarService extends CarService{
    private final AbstractFilter<Car> filter;

    public FilteredCarService(IRepository<String, Car> carRepo, AbstractFilter<Car> filter) {
        super(carRepo);
        this.filter = filter;
    }

    @Override
    public ArrayList<Car> getAllCars() {
        ArrayList<Car> cars = super.getAllCars();
        ArrayList<Car> filteredCars = new ArrayList<>();
        for (Car car : cars){
            if (filter.accept(car)){
                filteredCars.add(car);
            }
        }
        return filteredCars;
    }
}
