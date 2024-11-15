package file_repository;

import domain.Car;
import domain.Rental;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BinaryFileRentalRepository extends FileRepository<UUID, Rental>{

    public BinaryFileRentalRepository(String readFileName, String writeFileName) {
        super(readFileName, writeFileName);
    }

    @Override
    void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.readFileName))) {
            this.map = (java.util.HashMap<UUID, Rental>) ois.readObject();
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    @Override
    void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.writeFileName))) {
            oos.writeObject(this.map);
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    @Override
    public void addInitialObjects() {
        Map<UUID, Car> cars = readCarsFromFile("data/cars.bin");
        if (cars.isEmpty()) {
            System.out.println("No cars available to create rentals.");
            return;
        }

        HashMap<UUID, Rental> rentals = new HashMap<>();
        for (UUID carId : cars.keySet()) {
            rentals.put(UUID.randomUUID(), new Rental(carId, LocalDate.of(2025, 12, 25)));
            rentals.put(UUID.randomUUID(), new Rental(carId, LocalDate.of(2025, 1, 15)));
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/rentals.bin"))) {
            oos.writeObject(rentals);
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private Map<UUID, Car> readCarsFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (HashMap<UUID, Car>) ois.readObject();
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
            return new HashMap<>();
        }
    }

}
