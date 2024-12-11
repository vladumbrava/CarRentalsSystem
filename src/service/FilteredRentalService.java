package service;

import domain.Car;
import domain.Rental;
import filters.AbstractFilter;
import repository.IRepository;

import java.util.ArrayList;
import java.util.UUID;

public class FilteredRentalService extends RentalService{
    private final AbstractFilter<Rental> filter;

    public FilteredRentalService(IRepository<UUID, Rental> rentalRepo, IRepository<UUID, Car> carRepo,
                                 AbstractFilter<Rental> filter) {
        super(rentalRepo, carRepo);
        this.filter = filter;
    }

    @Override
    public ArrayList<Rental> getAllRentals() {
        ArrayList<Rental> rentals = super.getAllRentals();
        ArrayList<Rental> filteredRentals = new ArrayList<>();
        for(Rental rental : rentals){
            if (filter.accept(rental)){
                filteredRentals.add(rental);
            }
        }
        return filteredRentals;
    }
}
