package repository.json_repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.Rental;
import repository.RentalRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JSONRentalRepository extends RentalRepository {

    private final String repoPath;

    public JSONRentalRepository(String repoPath) {
        this.repoPath = repoPath;
        readFromFile();
    }

    @Override
    public void add(UUID idToAdd, Rental objectToAdd) {
        super.add(idToAdd, objectToAdd);
        writeToFile();
    }

    @Override
    public void delete(UUID idToDelete) {
        super.delete(idToDelete);
        writeToFile();
    }

    public void readFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TypeReference<List<Rental>> typeReference = new TypeReference<>() {};
        File file = new File(repoPath);
        if (file.length() == 0) {
            return;
        }
        try {
            List<Rental> rentals = mapper.readValue(file, typeReference);
            for (Rental rental : rentals) {
                super.add(rental.getID(), rental);
            }
        } catch (IOException exception) {
            System.err.println("Error reading rentals from file: " + exception.getMessage());
            throw new RuntimeException("Unable to read rentals: " + exception.getMessage(), exception);
        }
    }

    public void writeToFile() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            List<Rental> rentals = new ArrayList<>(super.map.values());
            mapper.writeValue(new File(repoPath), rentals);
        } catch (IOException exception) {
            System.err.println("Error writing rentals to file: " + exception.getMessage());
            throw new RuntimeException("Unable to write rentals: " + exception.getMessage(), exception);
        }
    }
}
