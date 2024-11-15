package file_repository;

import domain.Car;
import domain.Colour;
import domain.FuelType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.UUID;

public class BinaryFileCarRepository extends  FileRepository<UUID, Car> {

    public BinaryFileCarRepository(String readFileName, String writeFileName) {
        super(readFileName, writeFileName);
    }

    @Override
    void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.readFileName))) {
            this.map = (java.util.HashMap<UUID, Car>) ois.readObject();
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
        HashMap<UUID, Car> cars = new HashMap<>();
        cars.put(UUID.randomUUID(), new Car("vw passat", 130, 5, FuelType.diesel, Colour.black));
        cars.put(UUID.randomUUID(), new Car("renou", 120, 2, FuelType.diesel, Colour.red));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/cars.bin"))) {
            oos.writeObject(cars);
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}

