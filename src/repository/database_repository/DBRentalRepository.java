package repository.database_repository;

import domain.Rental;
import repository.RentalRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;

public class DBRentalRepository extends RentalRepository {

    public String URL;

    public DBRentalRepository(String repoPath) {
        this.URL = "jdbc:sqlite:" + repoPath;
        readFromDB();
    }

    //make adjustments to return date, in the database or in the code
    //in line 30 and in line 50
    private void readFromDB() {
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM Rentals;"))
        {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID rentalID = UUID.fromString(resultSet.getString(1));
                UUID carID = UUID.fromString(resultSet.getString(2));
                LocalDate returnDate = resultSet.getDate(3).toLocalDate();
                Rental newRental = new Rental(carID,returnDate);
                newRental.setRentalID(rentalID);
                this.map.put(rentalID,newRental);
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void add(UUID idToAdd, Rental objectToAdd) {
        super.add(idToAdd, objectToAdd);

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Rentals VALUES (?,?,?)")) {
            statement.setString(1,objectToAdd.getID().toString());
            statement.setString(2,objectToAdd.getCarID().toString());
            statement.setString(3,objectToAdd.getReturnDate().toString());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void delete(UUID idToDelete) {
        super.delete(idToDelete);
    }

    @Override
    public Rental findByID(UUID idUsedToFindObject) {
        return super.findByID(idUsedToFindObject);
    }
}
