package GUI;

import domain.Car;
import domain.Colour;
import domain.FuelType;
import domain.Rental;
import filters.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.CarService;
import service.RentalService;

import java.time.Duration;
import java.time.LocalDate;
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
    private TextField carModelNameTextFieldToRent;

    @FXML
    private TextField rentalReturnDayTextField;

    @FXML
    private TextField rentalReturnMonthTextField;

    @FXML
    private TextField rentalReturnYearTextField;

    @FXML
    Button rentCarButton;

    @FXML
    Button cancelRentalButton;

    @FXML
    private TextField carModelNameTextFieldToCancelRental;

    @FXML
    void rentCarButtonHandler(ActionEvent event) {
        String modelName = carModelNameTextFieldToRent.getText();
        UUID carId = carService.findCarIDbyModelName(modelName);
        int day = Integer.parseInt(rentalReturnDayTextField.getText());
        int month = Integer.parseInt(rentalReturnMonthTextField.getText());
        int year = Integer.parseInt(rentalReturnYearTextField.getText());
        LocalDate returnDate = LocalDate.of(year, month, day);
        rentalService.addRental(carId,returnDate);
        initializeOrUpdateListViews();
    }

    @FXML
    void cancelRentalButtonHandler(ActionEvent event) {
        String modelName = carModelNameTextFieldToCancelRental.getText();
        UUID carId = carService.findCarIDbyModelName(modelName);
        for (Rental rental : rentalService.getAllRentals()) {
            if (rental.getCarID().equals(carId)) {
                rentalService.deleteRental(rental.getID());
            }
        }
        initializeOrUpdateListViews();
    }

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
        carFilterComboBox.setItems(FXCollections.observableArrayList(
                new FilterCarByNumSeats(0),
                new FilterCarByHorsePower(0),
                new FilterCarByFuelType(null)
        ));
        rentalFilterComboBox.setItems(FXCollections.observableArrayList(
                new FilterRentalInProgressAtDate(LocalDate.now()),
                new FilterRentalTimeMoreThanDuration(Duration.ZERO)
        ));
        carReportsComboBox.setItems(FXCollections.observableArrayList(1,2));
        stringReportsComboBox.setItems(FXCollections.observableArrayList(3,4,5));
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

    @FXML
    private ListView<Car> filteredCarsListView;

    @FXML
    private ListView<Rental> filteredRentalsListView;

    @FXML
    private TextField carNumberOfSeatsTextFieldToFilter;

    @FXML
    private TextField carHorsePowerTextFieldToFilter;

    @FXML
    private TextField carFuelTypeTextFieldToFilter;

    @FXML
    private ComboBox<AbstractFilter<Car>> carFilterComboBox;

    @FXML
    private Button filterCarsButton;

    @FXML
    private Button filterRentalsButton;

    @FXML
    private TextField rentalDayTextFieldToFilter;

    @FXML
    private TextField rentalMonthTextFieldToFilter;

    @FXML
    private TextField rentalYearTextFieldToFilter;

    @FXML
    private TextField rentalDurationTextFieldToFilter;

    @FXML
    private ComboBox<AbstractFilter<Rental>> rentalFilterComboBox;

    @FXML
    public void filterCarsButtonHandler() {
        AbstractFilter<Car> selectedFilter = carFilterComboBox.getValue();
        if (selectedFilter instanceof FilterCarByNumSeats) {
            int numberSeats = Integer.parseInt(carNumberOfSeatsTextFieldToFilter.getText());
            selectedFilter = new FilterCarByNumSeats(numberSeats);
        } else if (selectedFilter instanceof FilterCarByHorsePower) {
            int horsePower = Integer.parseInt(carHorsePowerTextFieldToFilter.getText());
            selectedFilter = new FilterCarByHorsePower(horsePower);
        } else if (selectedFilter instanceof FilterCarByFuelType) {
            FuelType fuelType = FuelType.valueOf(carFuelTypeTextFieldToFilter.getText().toLowerCase());
            selectedFilter = new FilterCarByFuelType(fuelType);
        }

        ArrayList<Car> filteredCars = new ArrayList<>();
        for (Car car : carService.getAllCars()) {
            if (selectedFilter.accept(car)) {
                filteredCars.add(car);
            }
        }
        filteredCarsListView.setItems(FXCollections.observableArrayList(filteredCars));
    }

    @FXML
    public void filterRentalsButtonHandler() {
        AbstractFilter<Rental> selectedFilter = rentalFilterComboBox.getValue();
        if (selectedFilter instanceof FilterRentalInProgressAtDate) {
            int day = Integer.parseInt(rentalDayTextFieldToFilter.getText());
            int month = Integer.parseInt(rentalMonthTextFieldToFilter.getText());
            int year = Integer.parseInt(rentalYearTextFieldToFilter.getText());
            LocalDate date = LocalDate.of(year, month, day);
            selectedFilter = new FilterRentalInProgressAtDate(date);
        } else if (selectedFilter instanceof FilterRentalTimeMoreThanDuration) {
            int durationDays = Integer.parseInt(rentalDurationTextFieldToFilter.getText());
            selectedFilter = new FilterRentalTimeMoreThanDuration(Duration.ofDays(durationDays));
        }

        ArrayList<Rental> filteredRentals = new ArrayList<>();
        for (Rental rental : rentalService.getAllRentals()) {
            if (selectedFilter.accept(rental)) {
                filteredRentals.add(rental);
            }
        }
        filteredRentalsListView.setItems(FXCollections.observableArrayList(filteredRentals));
    }

    @FXML
    private ListView<Car> reportedCarsListView;

    @FXML
    private ListView<String> reportedStringsListView;

    @FXML
    private Button reportCarsButton;

    @FXML
    private ComboBox<Integer> carReportsComboBox;

    @FXML
    private TextField carMakeTextFieldToReport;

    @FXML
    private TextField carMultipleMakesTextFieldToReport;

    @FXML
    private TextField carFuelTypeTextFieldToReport;

    @FXML
    private TextField carColourTextFieldToReport;

    @FXML
    private Button reportStringsButton;

    @FXML
    private ComboBox<Integer> stringReportsComboBox;

    @FXML
    private TextField rentalDayTextFieldToReport;

    @FXML
    private TextField rentalMonthTextFieldToReport;

    @FXML
    private TextField rentalYearTextFieldToReport;

    @FXML
    private TextField availableCarFuelTypeTextFieldToReport;

    @FXML
    public void reportCarsButtonHandler() {
        int selectedReport = carReportsComboBox.getValue();
        ArrayList<Car> reportedCars = new ArrayList<>();

        switch (selectedReport) {
            case 1:
                String make = carMakeTextFieldToReport.getText();
                reportedCars = carService.getGivenMakeDieselCarsDescendingByHorsePower(make);
                break;
            case 2:
                String[] makes = carMultipleMakesTextFieldToReport.getText().split(",");
                reportedCars = carService.getGivenMakesTwoSeatedGasolineCars(makes);
                break;
            default:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Report Type");
                alert.setContentText("Please select a valid report type.");
                alert.showAndWait();
                return;
        }
        reportedCarsListView.setItems(FXCollections.observableArrayList(reportedCars));
    }

    @FXML
    public void reportStringsButtonHandler() {
        int selectedReport = stringReportsComboBox.getValue();
        ArrayList<String> reportedStrings = new ArrayList<>();

        switch (selectedReport) {
            case 3:
                FuelType fuelType = FuelType.valueOf(carFuelTypeTextFieldToReport.getText().toLowerCase());
                Colour colour = Colour.valueOf(carColourTextFieldToReport.getText().toLowerCase());
                reportedStrings = carService.getCarsModelNameOfGivenFuelTypeAndColourByHorsePower(fuelType,colour);
                break;
            case 4:
                int day = Integer.parseInt(rentalDayTextFieldToReport.getText());
                int month = Integer.parseInt(rentalMonthTextFieldToReport.getText());
                int year = Integer.parseInt(rentalYearTextFieldToReport.getText());
                LocalDate date = LocalDate.of(year, month, day);
                reportedStrings = rentalService.getAllRentedCarsThatAreReturnedAtGivenDate(date);
                break;
            case 5:
                FuelType fuelTypeReport5 = FuelType.valueOf(availableCarFuelTypeTextFieldToReport.getText().toLowerCase());
                reportedStrings = rentalService.getAllAvailableCarsOfFuelTypeSortedAlphabetically(fuelTypeReport5);
                break;
            default:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Report Type");
                alert.setContentText("Please select a valid report type.");
                alert.showAndWait();
                return;
        }
        reportedStringsListView.setItems(FXCollections.observableArrayList(reportedStrings));
    }
}