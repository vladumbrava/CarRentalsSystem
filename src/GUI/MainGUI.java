package GUI;

import domain.Car;
import domain.Rental;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.CarRepository;
import repository.IRepository;
import repository.RentalRepository;
import repository.database_repository.DBCarRepository;
import repository.database_repository.DBRentalRepository;
import repository.file_repository.BinaryFileCarRepository;
import repository.file_repository.BinaryFileRentalRepository;
import repository.file_repository.TextFileCarRepository;
import repository.file_repository.TextFileRentalRepository;
import repository.json_repository.JSONCarRepository;
import repository.json_repository.JSONRentalRepository;
import repository.xml_repository.XMLCarRepository;
import repository.xml_repository.XMLRentalRepository;
import service.CarService;
import service.RentalService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class MainGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        IRepository<UUID, Car> carRepo = setCarRepositoryImplementation();
        IRepository<UUID, Rental> rentalRepo = setRentalRepositoryImplementation();

        CarService carService = new CarService(carRepo);
        RentalService rentalService = new RentalService(rentalRepo, carRepo);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CarRentalsGUI.fxml"));
        fxmlLoader.setControllerFactory(_ -> new CarRentalsGUIController(carService, rentalService));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static IRepository<UUID, Car> setCarRepositoryImplementation() {
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
            if (repoType.equals("database"))
                carRepository = new DBCarRepository(repoPath);
            if (repoType.equals("json"))
                carRepository = new JSONCarRepository(repoPath);
            if (repoType.equals("xml"))
                carRepository = new XMLCarRepository(repoPath);

            return carRepository;

        } catch (IOException e) {
            System.out.println("Error: Settings file not implemented properly" + e.getMessage());
        }
        System.out.println("Choosing the default repository implementation for Car entity");
        return new CarRepository();
    }

    private static IRepository<UUID, Rental> setRentalRepositoryImplementation() {
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
            if (repoType.equals("database"))
                rentalRepository = new DBRentalRepository(repoPath);
            if (repoType.equals("json"))
                rentalRepository = new JSONRentalRepository(repoPath);
            if (repoType.equals("xml"))
                rentalRepository = new XMLRentalRepository(repoPath);

            return rentalRepository;

        } catch (IOException e) {
            System.out.println("Error: Settings file not implemented properly");
        }
        System.out.println("Choosing the default repository implementation for Rental entity");
        return new RentalRepository();
    }
}