package service;

import domain.Car;
import domain.Colour;
import domain.FuelType;
import repository.IRepository;
import validator.CarValidator;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void printGivenMakeDieselCarsDescendingByHorsePower(String make) {
        Car[] allCars = getAllCars().toArray(new Car[0]);
        Arrays.stream(allCars)
                .filter(car -> car.getModelName().startsWith(make))
                .filter(car->car.getFuelType().equals(FuelType.diesel))
                .sorted((car1, car2) -> car2.getHorsePower() - car1.getHorsePower())
                .forEach(System.out::println);
    }

    public void printGivenMakesTwoSeatedGasolineCars(String[] makes) {
        Car[] allCars = getAllCars().toArray(new Car[0]);
        Arrays.stream(allCars)
                .filter(car -> car.getNumberSeats() == 2)
                .filter(car -> car.getFuelType() == FuelType.gasoline)
                .filter(car -> Arrays.stream(makes).anyMatch(make -> car.getModelName().startsWith(make)))
                .forEach(System.out::println);
    }

    public void printCarsModelNameOfGivenFuelTypeAndColourByHorsePower(FuelType fuelType, Colour colour) {
        Car[] allCars = getAllCars().toArray(new Car[0]);
        Arrays.stream(allCars)
                .filter(car -> car.getFuelType().equals(fuelType))
                .filter(car -> car.getColour().equals(colour))
                .sorted((car1, car2) -> car2.getHorsePower() - car1.getHorsePower())
                .map(Car::getModelName)
                .forEach(System.out::println);
    }

}
