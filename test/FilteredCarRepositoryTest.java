import domain.Car;
import domain.Colour;
import domain.FuelType;
import filters.AbstractFilter;
import filters.FilterCarByFuelType;
import filters.FilterCarByHorsePower;
import filters.FilterCarByNumSeats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.FilteredCarRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FilteredCarRepositoryTest {
    
    private FilteredCarRepository carFilteredRepository;
    private AbstractFilter<Car> carFilter;
    
    @BeforeEach
    public void setUp() {
        carFilteredRepository = new FilteredCarRepository(carFilter);
    }

    @Test
    public void testAddCarFilterFuelType() {
        FuelType fuelType = FuelType.diesel;
        carFilter = new FilterCarByFuelType(fuelType);
        carFilteredRepository = new FilteredCarRepository(carFilter);
        Car car = new Car("vw passat",130,5,FuelType.diesel, Colour.black);
        UUID carID = UUID.randomUUID();
        carFilteredRepository.add(carID,car);
        assertEquals(car,carFilteredRepository.findByID(carID));
    }

    @Test
    public void testAddCarFilterFuelTypeForInvalid() {
        FuelType fuelType = FuelType.gasoline;
        carFilter = new FilterCarByFuelType(fuelType);
        carFilteredRepository = new FilteredCarRepository(carFilter);
        Car car = new Car("vw passat",130,5,FuelType.diesel, Colour.black);
        UUID carID = UUID.randomUUID();
        carFilteredRepository.add(carID,car);
        assertNotEquals(car,carFilteredRepository.findByID(carID));
    }

    @Test
    public void testAddCarFilterHorsePower() {
        int horsePower = 100;
        carFilter = new FilterCarByHorsePower(horsePower);
        carFilteredRepository = new FilteredCarRepository(carFilter);
        Car car = new Car("vw passat",130,5,FuelType.diesel, Colour.black);
        UUID carID = UUID.randomUUID();
        carFilteredRepository.add(carID,car);
        assertEquals(car,carFilteredRepository.findByID(carID));
    }

    @Test
    public void testAddCarFilterHorsePowerInvalid() {
        int horsePower = 150;
        carFilter = new FilterCarByHorsePower(horsePower);
        carFilteredRepository = new FilteredCarRepository(carFilter);
        Car car = new Car("vw passat",130,5,FuelType.diesel, Colour.black);
        UUID carID = UUID.randomUUID();
        carFilteredRepository.add(carID,car);
        assertNotEquals(car,carFilteredRepository.findByID(carID));
    }

    @Test
    public void testAddCarFilterNumberSeats() {
        int numberSeats = 5;
        carFilter = new FilterCarByNumSeats(numberSeats);
        carFilteredRepository = new FilteredCarRepository(carFilter);
        Car car = new Car("vw passat",130,5,FuelType.diesel, Colour.black);
        UUID carID = UUID.randomUUID();
        carFilteredRepository.add(carID,car);
        assertEquals(car,carFilteredRepository.findByID(carID));
    }

    @Test
    public void testAddCarFilterNumberSeatsInvalid() {
        int numberSeats = 2;
        carFilter = new FilterCarByNumSeats(numberSeats);
        carFilteredRepository = new FilteredCarRepository(carFilter);
        Car car = new Car("vw passat",130,5,FuelType.diesel, Colour.black);
        UUID carID = UUID.randomUUID();
        carFilteredRepository.add(carID,car);
        assertNotEquals(car,carFilteredRepository.findByID(carID));
    }
}
