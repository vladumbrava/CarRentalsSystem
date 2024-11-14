package file_repository;

import domain.Rental;

import java.io.*;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.UUID;

public class TextFileRentalRepository extends FileRepository<UUID, Rental> {

    public TextFileRentalRepository(String fileName, String writeFileName) {
        super(fileName,writeFileName);
    }

    @Override
    void readFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String [] tokens = currentLine.split(",");
                if (tokens.length != 4) {
                    continue;
                }
                Rental rentalToRead = buildRentalFromTokens(tokens);
                super.add(rentalToRead.getID(),rentalToRead);
            }
        } catch (IOException ioException){
            throw new RuntimeException(ioException);
        }
    }

    private static Rental buildRentalFromTokens(String[] tokens) {
        String carIDString = tokens[0];
        UUID carID = UUID.fromString(carIDString);
        int dayOfReturnDate = Integer.parseInt(tokens[1]);
        int monthOfReturnDate = Integer.parseInt(tokens[2]);
        int yearOfReturnDate = Integer.parseInt(tokens[3]);
        LocalDate returnDate = LocalDate.of(yearOfReturnDate,monthOfReturnDate,dayOfReturnDate);
        return new Rental(carID,returnDate);
    }

    @Override
    void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.writeFileName))) {
            Iterator<Rental> rentalIterator = super.iterator();
            while (rentalIterator.hasNext()) {
                Rental rentalToWrite = rentalIterator.next();
                bufferedWriter.write("Rental{" +
                        "rentalID='" + rentalToWrite.getID() +
                        ", carID='" + rentalToWrite.getCarID() +
                        ", returnDate=" + rentalToWrite.getReturnDate() +
                        "}"+"\n");
            }
        } catch (IOException ioException) {
            System.out.println("Error: " + ioException.getMessage());
        }
    }
}
