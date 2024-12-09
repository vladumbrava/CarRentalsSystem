package domain;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Car implements Identifiable<UUID>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID carID;
    private String modelName;
    private int horsePower;
    private int numberSeats;
    private FuelType fuelType;
    private Colour colour;

    public void setCarID(UUID carID) {
        this.carID = carID;
    }

    public static final ArrayList<Integer> VALID_NUMBER_SEATS = new ArrayList<>(Arrays.asList(2, 4, 5, 7));

    public Car(UUID carID, String modelName, int horsePower, int numberSeats, FuelType fuelType, Colour colour) {
        if (!VALID_NUMBER_SEATS.contains(numberSeats)){
            throw new IllegalArgumentException("Incorrect value for 'numberSeats'.");
        }
        if (horsePower <= 0){
            throw new IllegalArgumentException("Incorrect value for 'horsePower'");
        }
        this.carID = carID;
        this.modelName = modelName;
        this.horsePower = horsePower;
        this.numberSeats = numberSeats;
        this.fuelType = fuelType;
        this.colour = colour;
    }


    public Car(String modelName, int horsePower, int numberSeats, FuelType fuelType, Colour colour) {
        if (!VALID_NUMBER_SEATS.contains(numberSeats)){
            throw new IllegalArgumentException("Incorrect value for 'numberSeats'.");
        }
        if (horsePower <= 0){
            throw new IllegalArgumentException("Incorrect value for 'horsePower'");
        }
        this.carID = UUID.randomUUID();
        this.modelName = modelName;
        this.horsePower = horsePower;
        this.numberSeats = numberSeats;
        this.fuelType = fuelType;
        this.colour = colour;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        if (horsePower <= 0){
            throw new IllegalArgumentException("Incorrect value for 'horsePower'");
        }
        this.horsePower = horsePower;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        if (!VALID_NUMBER_SEATS.contains(numberSeats)){
            throw new IllegalArgumentException("Incorrect value for 'numberSeats'.");
        }
        this.numberSeats = numberSeats;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    @Override
    public UUID getID() {
        return carID;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + carID + '\'' +
                ", modelName='" + modelName + '\'' +
                ", horsePower=" + horsePower +
                ", numberSeats=" + numberSeats +
                ", fuelType=" + fuelType +
                ", colour=" + colour +
                '}';
    }
}
