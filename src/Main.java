
import domain.Car;
import domain.Rental;
import file_repository.BinaryFileCarRepository;
import file_repository.BinaryFileRentalRepository;
import file_repository.TextFileCarRepository;
import file_repository.TextFileRentalRepository;
import repository.CarRepository;
import repository.IRepository;
import repository.RentalRepository;
import service.CarService;
import UI.UI;
import service.RentalService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        IRepository<UUID, Car> carRepo = setCarRepositoryImplementation();
        IRepository<UUID, Rental> rentalRepo = setRentalRepositoryImplementation();
        
        CarService carService = new CarService(carRepo);
        RentalService rentalService = new RentalService(rentalRepo,carRepo);
        UI ui = new UI(carService,rentalService);
        ui.run();
    }

    private static IRepository<UUID, Car> setCarRepositoryImplementation () {
        Properties prop = new Properties();
        IRepository<UUID, Car> carRepository = null;

        try {
            prop.load(new FileReader("configs/settings.properties"));

            String repoType = prop.getProperty("repositoryType");
            String repoPath = prop.getProperty("carFilePath");

            if (repoType.equals("text"))
                carRepository = new TextFileCarRepository(repoPath);
            if (repoType.equals("binary"))
                carRepository = new BinaryFileCarRepository(repoPath);
            if (repoType.equals("memory"))
                carRepository = new CarRepository();

            return carRepository;

        } catch (IOException e) {
            System.out.println("Error: Settings file not implemented properly" + e.getMessage());
        }
        System.out.println("Choosing the default repository implementation for Car entity");
        return new CarRepository();
    }

    private static IRepository<UUID, Rental> setRentalRepositoryImplementation () {
        Properties prop = new Properties();
        IRepository<UUID, Rental> rentalRepository = null;

        try {
            prop.load(new FileReader("configs/settings.properties"));

            String repoType = prop.getProperty("repositoryType");
            String repoPath = prop.getProperty("rentalFilePath");

            if (repoType.equals("text"))
                rentalRepository = new TextFileRentalRepository(repoPath);
            if (repoType.equals("binary"))
                rentalRepository = new BinaryFileRentalRepository(repoPath);
            if (repoType.equals("memory"))
                rentalRepository = new RentalRepository();

            return rentalRepository;

        } catch (IOException e) {
            System.out.println("Error: Settings file not implemented properly");
        }
        System.out.println("Choosing the default repository implementation for Rental entity");
        return new RentalRepository();
    }
}
