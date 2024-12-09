package domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Rental implements Identifiable<UUID>, Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    private UUID rentalID;
    private final UUID carID;
    private LocalDate returnDate;

    public void setRentalID(UUID rentalID) {
        this.rentalID = rentalID;
    }

    public Rental(UUID carID, LocalDate returnDate) {
        this.rentalID = UUID.randomUUID();
        this.carID = carID;
        this.returnDate = returnDate;
    }

    @Override
    public UUID getID() {
        return rentalID;
    }

    public UUID getCarID() {
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
