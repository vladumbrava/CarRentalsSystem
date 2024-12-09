package service;

import domain.Car;
import domain.Colour;
import domain.FuelType;
import repository.IRepository;
import validator.CarValidator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class CarService {
    private final IRepository<UUID, Car> carRepo;


    public CarService(IRepository<UUID, Car> carRepo) {
        this.carRepo = carRepo;
    }

    public IRepository<UUID, Car> getCarRepo() {
        return carRepo;
    }

    public void addCar(String modelName, int horsePower, int numberSeats, FuelType fuelType, Colour colour){
        Car carToAdd = new Car(modelName,horsePower,numberSeats,fuelType,colour);
        CarValidator carValidator = new CarValidator();
        carValidator.validateCar(carToAdd);
        carRepo.add(carToAdd.getID(),carToAdd);
    }

    public void deleteCar(UUID carIDUsedToDelete){
        if (carRepo.findByID(carIDUsedToDelete) == null){
            throw new IllegalArgumentException("Car ID not found in the repository.");
        }
        carRepo.delete(carIDUsedToDelete);
    }

    public UUID findCarIDbyModelName(String modelNameUsedToFindID){
        //iterate through the car repository map in order to find the key(the id),
        //by the value's (car) attribute modelName, return the id
        UUID carIDToFind = null;
        Iterator<Car> carIterator = carRepo.iterator();
        while (carIterator.hasNext()){
            Car car = carIterator.next();
            if (car.getModelName().equals(modelNameUsedToFindID)){
                carIDToFind = car.getID();
            }
        }
        return carIDToFind;
    }

    public ArrayList<Car> getAllCars(){
        ArrayList<Car> cars = new ArrayList<>();

        Iterator<Car> carIterator = carRepo.iterator();
        while (carIterator.hasNext()){
            cars.add(carIterator.next());
        }

        return cars;
    }
}
