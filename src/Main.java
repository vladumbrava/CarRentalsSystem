
import repository.CarRepository;
import repository.RentalRepository;
import service.CarService;
import UI.UI;
import service.RentalService;

public class Main {
    public static void main(String[] args) {
        CarRepository carRepo = new CarRepository();
        RentalRepository rentalRepo = new RentalRepository();
        CarService carService = new CarService(carRepo);
        RentalService rentalService = new RentalService(rentalRepo,carRepo);
        UI ui = new UI(carService,rentalService);
        ui.run();
    }
}
