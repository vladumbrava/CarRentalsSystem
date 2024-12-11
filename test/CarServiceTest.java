import domain.Car;
import domain.Colour;
import domain.FuelType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CarRepository;
import service.CarService;
import utils.FakeCarRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTest {
    private CarRepository carRepo;
    private CarService carService;

    @BeforeEach
    public void setUp() {
        carRepo = new CarRepository();
        carService = new CarService(carRepo);
    }

    @Test
    public void testGetCarRepo() {
        assertEquals(carRepo, carService.getCarRepo());
    }

    @Test
    public void givenValidCar_whenAddCar_thenCarIsAdded() {
        Car car = new Car("honda civic", 170, 5, FuelType.hybrid, Colour.blue);
        carService.getCarRepo().add(car.getID(),car);
        assertEquals(car, carService.getCarRepo().findByID(car.getID()));
    }

    @Test
    public void givenInvalidCarId_whenDeleteCar_thenThrowException() {
        FakeCarRepository fakeCarRepo = new FakeCarRepository();
        fakeCarRepo.deleteShouldThrowException = true;
        carService = new CarService(fakeCarRepo);

        UUID carId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> carService.deleteCar(carId));
    }

    @Test
    public void givenValidCarId_whenDeleteCar_thenCarIsDeleted() {
        Car car = new Car("vw passat", 130, 5, FuelType.diesel, Colour.black);
        carRepo.add(car.getID(), car);
        carService.deleteCar(car.getID());
        assertNull(carRepo.findByID(car.getID()));
    }

    @Test
    public void givenModelName_whenFindCarIDbyModelName_thenReturnCarId() {
        Car car = new Car("vw passat", 130, 5, FuelType.diesel, Colour.black);
        carRepo.add(car.getID(), car);
        UUID foundCarId = carService.findCarIDbyModelName(car.getModelName());
        assertEquals(car.getID(), foundCarId);
    }

    @Test
    public void whenGetAllCars_thenReturnAllCars() {
        Car car1 = new Car("vw passat", 130, 5, FuelType.diesel, Colour.black);
        Car car2 = new Car("honda civic", 170, 5, FuelType.hybrid, Colour.blue);
        carRepo.add(car1.getID(), car1);
        carRepo.add(car2.getID(), car2);

        ArrayList<Car> allCars = carService.getAllCars();
        Set<Car> allCarsSet = new HashSet<>(allCars);
        Set<Car> expectedCarsSet = new HashSet<>(Arrays.asList(car1, car2));
        assertEquals(expectedCarsSet, allCarsSet);
    }
}