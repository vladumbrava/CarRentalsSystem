package file_repository;

import domain.Car;
import domain.Colour;
import domain.FuelType;

import java.io.*;
import java.util.Iterator;
import java.util.UUID;

public class TextFileCarRepository extends FileRepository<UUID, Car> {
    public TextFileCarRepository(String fileName, String writeFileName) {
        super(fileName,writeFileName);
    }

    @Override
    void readFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String [] tokens = currentLine.split(",");
                if (tokens.length != 5) {
                    continue;
                }
                Car carToRead = buildCarFromTokens(tokens);
                super.add(carToRead.getID(),carToRead);
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    private static Car buildCarFromTokens(String[] tokens) {
        String modelName = tokens[0];
        int horsePower = Integer.parseInt(tokens[1]);
        int numberSeats = Integer.parseInt(tokens[2]);
        String fuelTypeString = tokens[3];
        FuelType fuelType = FuelType.valueOf(fuelTypeString.toLowerCase());
        String colourString = tokens[4];
        Colour colour = Colour.valueOf(colourString.toLowerCase());
        return new Car(modelName,horsePower,numberSeats,fuelType,colour);
    }

    @Override
    void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.writeFileName))) {
            Iterator<Car> carFileIterator = super.iterator();
            while (carFileIterator.hasNext()) {
                Car carToWrite = carFileIterator.next();
                bufferedWriter.write(carToWrite.getID()+","+carToWrite.getModelName()+","+carToWrite.getHorsePower()+","+
                        carToWrite.getNumberSeats()+","+carToWrite.getFuelType()+","+carToWrite.getColour());
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
