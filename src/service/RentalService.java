package service;

import domain.Car;
import domain.FuelType;
import domain.Rental;
import repository.IRepository;
import validator.RentalValidator;

import java.time.LocalDate;
import java.util.*;

public class RentalService {
    private final IRepository<UUID, Rental> rentalRepo;
    private final IRepository<UUID, Car> carRepo;

    public RentalService(IRepository<UUID, Rental> rentalRepo, IRepository<UUID, Car> carRepo) {
        this.rentalRepo = rentalRepo;
        this.carRepo = carRepo;
    }

    public IRepository<UUID, Rental> getRentalRepo() {
        return rentalRepo;
    }

    public void addRental(UUID carID, LocalDate returnDate){
        if (carRepo.findByID(carID) == null){
            throw new IllegalArgumentException("Car ID not found in the repository.");
        }
        Rental newRental = new Rental(carID,returnDate);
        RentalValidator rentalValidator = new RentalValidator();
        rentalValidator.validateRental(newRental);
        rentalRepo.add(newRental.getID(),newRental);
    }

    public void deleteRental(UUID rentalID){
        if (rentalRepo.findByID(rentalID) == null){
            throw new IllegalArgumentException("Rental ID not found in the repository.");
        }
        rentalRepo.delete(rentalID);
    }

    public Car findRentedCarByCarID(UUID carID){
        Car rentedCar = null;
        Iterator<Rental> rentalIterator = rentalRepo.iterator();
        while (rentalIterator.hasNext()){
            Rental rental = rentalIterator.next();
            if (rental.getCarID().equals(carID)){
                Iterator<Car> carIterator = carRepo.iterator();
                while (carIterator.hasNext()){
                    Car iteratedCar = carIterator.next();
                    if (iteratedCar.getID().equals(carID)){
                        rentedCar = iteratedCar;
                    }
                }
            }
        }
        return rentedCar;
    }

    public ArrayList<Rental> getAllRentals(){
        ArrayList<Rental> rentals = new ArrayList<>();

        Iterator<Rental> rentalIterator = rentalRepo.iterator();
        while (rentalIterator.hasNext()){
            rentals.add(rentalIterator.next());
        }

        return rentals;
    }

    public void printAllRentedCarsThatAreReturnedAtGivenDate(LocalDate returnDate) {
        Rental[] allRentals = getAllRentals().toArray(new Rental[0]);
        Arrays.stream(allRentals)
                .filter(rental -> rental.getReturnDate().equals(returnDate))
                .map(rental -> findRentedCarByCarID(rental.getCarID()).getModelName())
                .forEach(System.out::println);
    }

    public void printAllAvailableCarsOfFuelTypeSortedAlphabetically(FuelType fuelType) {
        ArrayList<Car> allAvailableCarsList = new ArrayList<>();
        ArrayList<Rental> allRentals = getAllRentals();
        Iterator<Rental> rentalIterator = allRentals.iterator();
        Iterator<Car> carIterator = carRepo.iterator();
        while (carIterator.hasNext()){
            Car car = carIterator.next();
            boolean isAvailable = true;
            while (rentalIterator.hasNext()){
                Rental rental = rentalIterator.next();
                if (rental.getCarID().equals(car.getID())){
                    isAvailable = false;
                }
            }
            if (isAvailable){
                allAvailableCarsList.add(car);
            }
        }
        Car[] allAvailableCars = allAvailableCarsList.toArray(new Car[0]);

        Arrays.stream(allAvailableCars)
                .filter(car -> car.getFuelType().equals(fuelType))
                .sorted(Comparator.comparing(Car::getModelName))
                .map(Car::getModelName)
                .forEach(System.out::println);
    }
}
