import domain.Car;
import domain.Colour;
import domain.FuelType;
import filters.AbstractFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import service.FilteredCarService;
import utils.FakeCarRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FilteredCarServiceTest {
    private IRepository<UUID, Car> carRepo;
    private AbstractFilter<Car> filter;
    private FilteredCarService filteredCarService;

    @BeforeEach
    public void setUp() {
        carRepo = new FakeCarRepository();
        filter = car -> car.getHorsePower() > 150;
        filteredCarService = new FilteredCarService(carRepo, filter);
    }

    @Test
    public void whenGetAllCars_thenReturnFilteredCars() {
        carRepo = new FakeCarRepository() {
            @Override
            public void add(UUID id, Car car) {
                super.add(id, car);
            }

            @Override
            public Iterator<Car> iterator() {
                return super.iterator();
            }
        };
        filteredCarService = new FilteredCarService(carRepo, filter);

        Car car1 = new Car("vw passat", 130, 5, FuelType.diesel, Colour.black);
        Car car2 = new Car("honda civic", 170, 5, FuelType.hybrid, Colour.blue);
        carRepo.add(car1.getID(), car1);
        carRepo.add(car2.getID(), car2);

        ArrayList<Car> filteredCars = filteredCarService.getAllCars();
        assertEquals(1, filteredCars.size());
        assertTrue(filteredCars.contains(car2));
        assertFalse(filteredCars.contains(car1));
    }
}