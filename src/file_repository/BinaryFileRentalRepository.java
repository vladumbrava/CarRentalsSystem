package file_repository;

import domain.Rental;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class BinaryFileRentalRepository extends FileRepository<UUID, Rental>{

    public BinaryFileRentalRepository(String fileName) {
        super(fileName);
    }

    @Override
    void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.fileName))) {
            this.map = (java.util.HashMap<UUID, Rental>) ois.readObject();
        } catch (EOFException e) {
            this.map = new HashMap<>();
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    @Override
    void writeToFile() {
        if (this.map == null) {
            this.map = new HashMap<>();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.fileName))) {
            oos.writeObject(this.map);
        } catch (IOException ioException) {
            System.out.println("I/O Error: " + ioException.getMessage());
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}
