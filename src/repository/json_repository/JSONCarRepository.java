package repository.json_repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Car;
import repository.CarRepository;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JSONCarRepository extends CarRepository {

    private final String repoPath;

    public JSONCarRepository(String repoPath) {
        this.repoPath = repoPath;
        readFromFile();
    }

    @Override
    public void add(UUID idToAdd, Car objectToAdd) {
        super.add(idToAdd, objectToAdd);
        writeToFile();
    }

    @Override
    public void delete(UUID idToDelete) {
        super.delete(idToDelete);
        writeToFile();
    }

    @Override
    public Car findByID(UUID idUsedToFindObject) {
        return super.findByID(idUsedToFindObject);
    }

    @Override
    public Iterator<Car> iterator() {
        return super.iterator();
    }

    public void readFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Car>> typeReference = new TypeReference<>() {};
        File file = new File(repoPath);
        if (file.length() == 0) {
            return;
        }
        try {
            List<Car> cars = mapper.readValue(file, typeReference);
            for (Car car : cars) {
                super.add(car.getID(), car);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Unable to read cars: " + exception.getMessage(), exception);
        }
    }

    public void writeToFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Car> cars = new ArrayList<>(super.map.values());
            mapper.writeValue(new File(repoPath), cars);
        } catch (IOException exception) {
            throw new RuntimeException("Unable to write cars: " + exception.getMessage(), exception);
        }
    }

}
