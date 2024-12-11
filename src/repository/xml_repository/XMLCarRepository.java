package repository.xml_repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import domain.Car;
import repository.CarRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class XMLCarRepository extends CarRepository {

    private final String repoPath;

    public XMLCarRepository(String repoPath) {
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
    public Iterator<Car> iterator() {
        return super.iterator();
    }

    @Override
    public Car findByID(UUID idUsedToFindObject) {
        return super.findByID(idUsedToFindObject);
    }

    public void readFromFile() {
        XmlMapper xmlMapper = new XmlMapper();
        TypeReference<List<Car>> typeReference = new TypeReference<>() {};
        File file = new File(repoPath);
        if (file.length() == 0) {
            return;
        }
        try {
            List<Car> cars = xmlMapper.readValue(file, typeReference);
            for (Car car : cars) {
                super.add(car.getID(), car);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Unable to read cars: " + exception.getMessage(), exception);
        }
    }

    public void writeToFile() {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            List<Car> cars = new ArrayList<>(super.map.values());
            xmlMapper.writeValue(new File(repoPath), cars);
        } catch (IOException exception) {
            throw new RuntimeException("Unable to write cars: " + exception.getMessage(), exception);
        }
    }
}
