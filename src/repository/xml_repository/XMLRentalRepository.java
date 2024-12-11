package repository.xml_repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import domain.Rental;
import repository.RentalRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class XMLRentalRepository extends RentalRepository {

    private final String repoPath;

    public XMLRentalRepository(String repoPath) {
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

    @Override
    public Iterator<Rental> iterator() {
        return super.iterator();
    }

    @Override
    public Rental findByID(UUID idUsedToFindObject) {
        return super.findByID(idUsedToFindObject);
    }

    public void readFromFile() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        TypeReference<List<Rental>> typeReference = new TypeReference<>() {};
        File file = new File(repoPath);
        if (file.length() == 0) {
            return;
        }
        try {
            List<Rental> rentals = xmlMapper.readValue(file, typeReference);
            for (Rental rental : rentals) {
                super.add(rental.getID(), rental);
            }
        } catch (IOException exception) {
            System.err.println("Error reading rentals from file: " + exception.getMessage());
            throw new RuntimeException("Unable to read rentals: " + exception.getMessage(), exception);
        }
    }

    public void writeToFile() {
        XmlMapper xmlMapper = new XmlMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        xmlMapper.registerModule(javaTimeModule);
        try {
            List<Rental> rentals = new ArrayList<>(super.map.values());
            xmlMapper.writeValue(new File(repoPath), rentals);
        } catch (IOException exception) {
            System.err.println("Error writing rentals to file: " + exception.getMessage());
            throw new RuntimeException("Unable to write rentals: " + exception.getMessage(), exception);
        }
    }
}
