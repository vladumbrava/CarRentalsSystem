package service;

import domain.Car;
import domain.Rental;
import repository.IRepository;
import validator.RentalValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

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
        //we must iterate through the rentalRepo, if we find a rental with the carID specified,
        //we then iterate through the carRepo, in order to find the car object with that carID
        //we return the car object
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
}
