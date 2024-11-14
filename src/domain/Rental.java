package domain;

import java.time.LocalDate;
import java.util.UUID;

public class Rental implements Identifiable<String>{
    private final String rentalID;
    private final String carID;
    private LocalDate returnDate;

    public Rental(String carID, LocalDate returnDate) {
        this.rentalID = UUID.randomUUID().toString();
        this.carID = carID;
        this.returnDate = returnDate;
    }

    @Override
    public String getID() {
        return rentalID;
    }

    public String getCarID() {
        return carID;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "rentalID='" + rentalID + '\'' +
                ", carID='" + carID + '\'' +
                ", returnDate=" + returnDate +
                '}';
    }
}
