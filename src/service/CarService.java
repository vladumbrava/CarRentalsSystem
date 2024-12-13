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

    public ArrayList<Car> getGivenMakeDieselCarsDescendingByHorsePower(String make) {
        Car[] allCars = getAllCars().toArray(new Car[0]);
        ArrayList<Car> result = new ArrayList<>();
        Arrays.stream(allCars)
                .filter(car -> car.getModelName().startsWith(make))
                .filter(car->car.getFuelType().equals(FuelType.diesel))
                .sorted((car1, car2) -> car2.getHorsePower() - car1.getHorsePower())
                .forEach(result::add);
        return result;
    }

    public ArrayList<Car> getGivenMakesTwoSeatedGasolineCars(String[] makes) {
        Car[] allCars = getAllCars().toArray(new Car[0]);
        ArrayList<Car> result = new ArrayList<>();
        Arrays.stream(allCars)
                .filter(car -> car.getNumberSeats() == 2)
                .filter(car -> car.getFuelType() == FuelType.gasoline)
                .filter(car -> Arrays.stream(makes).anyMatch(make -> car.getModelName().startsWith(make)))
                .forEach(result::add);
        return result;
    }

    public ArrayList<String> getCarsModelNameOfGivenFuelTypeAndColourByHorsePower(FuelType fuelType, Colour colour) {
        Car[] allCars = getAllCars().toArray(new Car[0]);
        ArrayList<String> result = new ArrayList<>();
        Arrays.stream(allCars)
                .filter(car -> car.getFuelType().equals(fuelType))
                .filter(car -> car.getColour().equals(colour))
                .sorted((car1, car2) -> car2.getHorsePower() - car1.getHorsePower())
                .map(Car::getModelName)
                .forEach(result::add);
        return result;
    }
}
