package repository.database_repository;

import domain.Car;
import domain.Colour;
import domain.FuelType;
import repository.CarRepository;

import java.sql.*;
import java.util.UUID;

public class DBCarRepository extends CarRepository {

    public String URL;

    public DBCarRepository(String repoPath) {
        this.URL = "jdbc:sqlite:" + repoPath;
        readFromDB();
    }

    private void readFromDB() {
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM Cars;"))
        {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID carID = UUID.fromString(resultSet.getString(1));
                String modelName = resultSet.getString(2);
                int horsePower = resultSet.getInt(3);
                int numberSeats = resultSet.getInt(4);
                String fuelTypeString = resultSet.getString(5);
                FuelType fuelType = FuelType.valueOf(fuelTypeString.toLowerCase());
                String colourString = resultSet.getString(6);
                Colour colour = Colour.valueOf(colourString.toLowerCase());
                Car newCar = new Car(carID, modelName, horsePower, numberSeats, fuelType, colour);
                newCar.setCarID(carID);
                this.map.put(carID,newCar);
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void add(UUID idToAdd, Car objectToAdd) {
        super.add(idToAdd, objectToAdd);

        try (Connection connection = DriverManager.getConnection(URL);
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Cars VALUES (?,?,?,?,?,?)")) {
            statement.setString(1,objectToAdd.getID().toString());
            statement.setString(2,objectToAdd.getModelName());
            statement.setInt(3,objectToAdd.getHorsePower());
            statement.setInt(4,objectToAdd.getNumberSeats());
            statement.setString(5,objectToAdd.getFuelType().toString());
            statement.setString(6,objectToAdd.getColour().toString());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void delete(UUID idToDelete) {
        super.delete(idToDelete);
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Cars WHERE id = ?")) {
            statement.setString(1, idToDelete.toString());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Car findByID(UUID idUsedToFindObject) {
        return super.findByID(idUsedToFindObject);
    }
}
