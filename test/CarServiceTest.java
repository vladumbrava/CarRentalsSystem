import domain.Car;
import domain.Colour;
import domain.FuelType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import service.CarService;
import utils.FakeCarRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTest {
    private IRepository<UUID, Car> carRepo;
    private CarService carService;

    @BeforeEach
    public void setUp() {
        carRepo = new FakeCarRepository();
        carService = new CarService(carRepo);
    }

    @Test
    public void testGetCarRepo() {
        assertEquals(carRepo, carService.getCarRepo());
    }

    @Test
    public void givenValidCar_whenAddCar_thenCarIsAdded() {
        carRepo = new FakeCarRepository() {
            @Override
            public void add(UUID id, Car car) {
                super.add(id, car);
            }
            @Override
            public Car findByID(UUID id) {
                return new Car("honda civic", 170, 5, FuelType.hybrid, Colour.blue);
            }
        };
        carService = new CarService(carRepo);

        Car car = new Car("honda civic", 170, 5, FuelType.hybrid, Colour.blue);
        carService.addCar(car.getModelName(), car.getHorsePower(), car.getNumberSeats(), car.getFuelType(), car.getColour());
        assertEquals(car.getModelName(), carRepo.findByID(car.getID()).getModelName());
    }

    @Test
    public void givenInvalidCarId_whenDeleteCar_thenThrowException() {
        carRepo = new FakeCarRepository() {
            @Override
            public Car findByID(UUID id) {
                return null;
            }
        };
        carService = new CarService(carRepo);

        UUID carId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> carService.deleteCar(carId));
    }

    @Test
    public void givenValidCarId_whenDeleteCar_thenCarIsDeleted() {
        carRepo = new FakeCarRepository() {
            @Override
            public void add(UUID id, Car car) {
                super.add(id, car);
            }

            @Override
            public Car findByID(UUID id) {
                return super.findByID(id);
            }

            @Override
            public void delete(UUID id) {
                super.delete(id);
            }
        };
        carService = new CarService(carRepo);

        Car car = new Car("vw passat", 130, 5, FuelType.diesel, Colour.black);
        carRepo.add(car.getID(), car);
        carService.deleteCar(car.getID());
        assertNull(carRepo.findByID(car.getID()));
    }

    @Test
    public void givenModelName_whenFindCarIDbyModelName_thenReturnCarId() {
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
        carService = new CarService(carRepo);

        Car car = new Car("vw passat", 130, 5, FuelType.diesel, Colour.black);
        carRepo.add(car.getID(), car);
        UUID foundCarId = carService.findCarIDbyModelName(car.getModelName());
        assertEquals(car.getID(), foundCarId);
    }

    @Test
    public void whenGetAllCars_thenReturnAllCars() {
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
        carService = new CarService(carRepo);

        Car car1 = new Car("vw passat", 130, 5, FuelType.diesel, Colour.black);
        Car car2 = new Car("honda civic", 170, 5, FuelType.hybrid, Colour.blue);
        carRepo.add(car1.getID(), car1);
        carRepo.add(car2.getID(), car2);

        ArrayList<Car> allCars = carService.getAllCars();
        assertEquals(2, allCars.size());
        assertTrue(allCars.contains(car1));
        assertTrue(allCars.contains(car2));
    }
}