package service;

import domain.Car;
import domain.Rental;
import repository.IRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class RentalService {
    private final IRepository<String, Rental> rentalRepo;
    private final IRepository<String, Car> carRepo;

    public RentalService(IRepository<String, Rental> rentalRepo, IRepository<String, Car> carRepo) {
        this.rentalRepo = rentalRepo;
        this.carRepo = carRepo;
    }

    public IRepository<String, Rental> getRentalRepo() {
        return rentalRepo;
    }

    public void addRental(String carID, LocalDate returnDate){
        if (carRepo.findByID(carID) == null){
            throw new IllegalArgumentException("Car ID not found in the repository.");
        }
        Rental newRental = new Rental(carID,returnDate);
        rentalRepo.add(newRental.getID(),newRental);
    }

    public void deleteRental(String rentalID){
        if (rentalRepo.findByID(rentalID) == null){
            throw new IllegalArgumentException("Rental ID not found in the repository.");
        }
        rentalRepo.delete(rentalID);
    }

    public Car findRentedCarByCarID(String carID){
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
