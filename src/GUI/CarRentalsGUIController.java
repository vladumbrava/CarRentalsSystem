package GUI;

import domain.Car;
import domain.Colour;
import domain.FuelType;
import domain.Rental;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.CarService;
import service.RentalService;

import java.util.ArrayList;
import java.util.UUID;

public class CarRentalsGUIController {

    CarService carService;
    RentalService rentalService;
    ObservableList<Car> carsList;
    ObservableList<Rental> rentalsList;

    @FXML
    private ListView<Car> carListView;

    @FXML
    private ListView<Rental> rentalListView;

    @FXML
    private TextField carModelNameTextField;

    @FXML
    private TextField carHorsePowerTextField;

    @FXML
    private ComboBox<Integer> carNumberSeatsComboBox;

    @FXML
    private ComboBox<FuelType> carFuelTypeComboBox;

    @FXML
    private ComboBox<Colour> carColourComboBox;

    @FXML
    private Button addCarButton;

    @FXML
    private Button deleteCarButton;

    @FXML
    private TextField carModelNameTextFieldToDelete;

    @FXML
    void addCarButtonHandler(ActionEvent event) {
        String modelName = carModelNameTextField.getText();
        int horsePower = Integer.parseInt(carHorsePowerTextField.getText());
        int numberSeats = carNumberSeatsComboBox.getValue();
        FuelType fuelType = carFuelTypeComboBox.getValue();
        Colour colour = carColourComboBox.getValue();
        carService.addCar(modelName, horsePower, numberSeats, fuelType, colour);
        initializeOrUpdateListViews();
    }

    @FXML
    void deleteCarButtonHandler(ActionEvent event) {
        String modelName = carModelNameTextFieldToDelete.getText();
        try {
            UUID carId = carService.findCarIDbyModelName(modelName);
            carService.deleteCar(carId);
            initializeOrUpdateListViews();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Car Not Found");
            alert.setContentText("The car model name '" + modelName + "' does not exist.");
            alert.showAndWait();
        }
    }

    public CarRentalsGUIController(CarService carService, RentalService rentalService) {
        this.carService = carService;
        this.rentalService = rentalService;
    }

    @FXML
    public void initialize() {
        carNumberSeatsComboBox.setItems(FXCollections.observableArrayList(2, 4, 5, 7));
        carFuelTypeComboBox.setItems(FXCollections.observableArrayList(FuelType.values()));
        carColourComboBox.setItems(FXCollections.observableArrayList(Colour.values()));
        initializeOrUpdateListViews();
    }

    public void initializeOrUpdateListViews() {
        ArrayList<Car> cars = this.carService.getAllCars();
        carsList = FXCollections.observableArrayList(cars);
        carListView.setItems(carsList);
        ArrayList<Rental> rentals = this.rentalService.getAllRentals();
        rentalsList = FXCollections.observableArrayList(rentals);
        rentalListView.setItems(rentalsList);
    }
}