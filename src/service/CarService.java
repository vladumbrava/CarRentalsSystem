package service;

import domain.Car;
import domain.Colour;
import domain.FuelType;
import repository.IRepository;

import java.util.ArrayList;
import java.util.Iterator;

public class CarService {
    private final IRepository<String, Car> carRepo;

    public CarService(IRepository<String, Car> carRepo) {
        this.carRepo = carRepo;
    }

    public IRepository<String, Car> getCarRepo() {
        return carRepo;
    }

    public void addCar(String modelName, int horsePower, int numberSeats, FuelType fuelType, Colour colour){
        Car car = new Car(modelName,horsePower,numberSeats,fuelType,colour);
        carRepo.add(car.getID(),car);
    }

    public void deleteCar(String carID){
        if (carRepo.findByID(carID) == null){
            throw new IllegalArgumentException("Car ID not found in the repository.");
        }
        carRepo.delete(carID);
    }

    public String findCarIDbyModelName(String modelName){
        //iterate through the car repository map in order to find the key(the id),
        //by the value's (car) attribute modelName, return the id
        String carID = "";
        Iterator<Car> carIterator = carRepo.iterator();
        while (carIterator.hasNext()){
            Car car = carIterator.next();
            if (car.getModelName().equals(modelName)){
                carID = car.getID();
            }
        }
        return carID;
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
