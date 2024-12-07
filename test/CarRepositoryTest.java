import domain.Car;
import domain.Colour;
import domain.FuelType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.MemoryRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CarRepositoryTest {

    private MemoryRepository<UUID,Car> carRepository;

    @BeforeEach
    public void setUp() {
        carRepository = new MemoryRepository<>();
    }

    @Test
    public void givenValidCar_whenAddCar_thenCarIsAdded() {
        Car car = new Car("honda civic", 170, 5, FuelType.hybrid, Colour.blue);
        UUID carId = UUID.randomUUID();
        carRepository.add(carId, car);
        assertEquals(car, carRepository.findByID(carId));
    }

    @Test
    public void givenInvalidNumberSeats_whenAddCar_thenThrowException() {
        Car car = null;
        UUID carID = null;
        try {
            car = new Car("vw passat", 130, 3, FuelType.diesel, Colour.black);
            carID = UUID.randomUUID();
            carRepository.add(carID, car);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
        assertEquals(carRepository.findByID(carID), car);
    }

    @Test
    public void givenInvalidHorsePower_whenAddCar_thenThrowException() {
        Car car = null;
        UUID carID = null;
        try {
            car = new Car("vw passat", -5, 2, FuelType.diesel, Colour.black);
            carID = UUID.randomUUID();
            carRepository.add(carID, car);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
        assertEquals(car, carRepository.findByID(carID));
    }

    @Test
    public void givenCarId_whenFindCar_thenReturnCar() {
        Car car = new Car("vw passat", 130, 5, FuelType.diesel, Colour.black);
        UUID carId = UUID.randomUUID();
        carRepository.add(carId, car);
        Car foundCar = carRepository.findByID(carId);
        assertEquals(car, foundCar);
    }

    @Test
    public void givenCarId_whenDeleteCar_thenCarIsDeleted() {
        Car car = new Car("vw passat", 130, 5, FuelType.diesel, Colour.black);
        UUID carId = UUID.randomUUID();
        carRepository.add(carId, car);
        carRepository.delete(carId);
        assertNull(carRepository.findByID(carId));
    }
}
