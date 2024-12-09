package UI;

import domain.Car;
import domain.Colour;
import domain.FuelType;
import domain.Rental;
import filters.*;
import service.CarService;
import service.FilteredCarService;
import service.FilteredRentalService;
import service.RentalService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class UI {
    private final CarService carService;
    private final RentalService rentalService;
    private final Scanner scanner;

    private static final int ADD_CAR_OPTION = 1;
    private static final int REMOVE_CAR_OPTION = 2;
    private static final int SHOW_ALL_CARS_OPTION = 3;
    private static final int SHOW_AVAILABLE_CARS_OPTION = 4;
    private static final int SHOW_RENTED_CARS_OPTION = 5;
    private static final int SHOW_FILTERED_CARS_OPTION = 6;
    private static final int SHOW_CARS_BY_SEATS_OPTION = 1;
    private static final int SHOW_CARS_BY_HORSEPOWER_OPTION = 2;
    private static final int SHOW_CARS_BY_FUEL_TYPE_OPTION = 3;
    private static final int SHOW_FILTERED_RENTALS_OPTION = 7;
    private static final int SHOW_RENTALS_IN_PROGRESS_OPTION = 1;
    private static final int SHOW_RENTALS_TIME_MORE_THAN_DURATION = 2;
    private static final int RENT_CAR_OPTION = 8;
    private static final int CANCEL_RENTAL_OPTION = 9;
    private static final int REPORTS_OPTION = 10;
    private static final int REPORT_1_OPTION = 1;
    private static final int REPORT_2_OPTION = 2;
    private static final int REPORT_3_OPTION = 3;
    private static final int EXIT_OPTION = 0;

    public UI(CarService carService, RentalService rentalService){
        this.carService = carService;
        this.rentalService = rentalService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu(){
        System.out.println("1. Add a car");
        System.out.println("2. Remove a car");
        System.out.println("3. Show all cars");
        System.out.println("4. Show available cars");
        System.out.println("5. Show rented cars");
        System.out.println("6. Show filtered cars");
        System.out.println("7. Show filtered rentals");
        System.out.println("8. Rent a car");
        System.out.println("9. Cancel a rental");
        System.out.println("10. Show reports");
        System.out.println("0. Exit");
    }

    public void runAddCarOption(){
        System.out.println("Enter model name: ");
        String modelName = scanner.nextLine();
        System.out.println("Enter horse power: ");
        int horsePower = scanner.nextInt();
        System.out.println("Enter number of seats: ");
        int numberSeats = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter fuel type: ");
        String fuelTypeStr = scanner.nextLine();
        FuelType fuelType = FuelType.valueOf(fuelTypeStr.toLowerCase());
        System.out.println("Enter colour: ");
        String colourStr = scanner.nextLine();
        Colour colour = Colour.valueOf(colourStr.toLowerCase());
        carService.addCar(modelName,horsePower,numberSeats,fuelType,colour);
    }

    public void runRemoveCarOption(){
        System.out.println("Enter car's model name: ");
        String carModelName = scanner.nextLine();
        UUID carID = carService.findCarIDbyModelName(carModelName);
        carService.deleteCar(carID);
    }

    public void runShowAllCarsOption(){
        System.out.println(carService.getAllCars());
    }

    public void runShowAvailableCarsOption(){
        ArrayList<Car> availableCars = new ArrayList<>();
        for (Car car : carService.getAllCars()){
            if (rentalService.findRentedCarByCarID(car.getID()) == null){
                availableCars.add(car);
            }
        }
        System.out.println(availableCars);
    }

    public void runShowRentedCarsOption(){
        ArrayList<Car> rentedCars = new ArrayList<>();
        for (Rental rental : rentalService.getAllRentals()){
            rentedCars.add(carService.getCarRepo().findByID(rental.getCarID()));
        }
        System.out.println(rentedCars);
    }

    public void runRentCarOption(){
        System.out.println("Enter car's model name: ");
        String modelName = scanner.nextLine();
        UUID carID = carService.findCarIDbyModelName(modelName);
        System.out.println("Enter return date: ");
        System.out.println("Day: ");
        int day = scanner.nextInt();
        System.out.println("Month: ");
        int month = scanner.nextInt();
        System.out.println("Year: ");
        int year = scanner.nextInt();
        LocalDate returnDate = LocalDate.of(year,month,day);
        if(returnDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Invalid date. Please enter a date in the future.");
        }
        rentalService.addRental(carID,returnDate);
    }

    public void runCancelRentalOption(){
        System.out.println("Enter car's model name: ");
        String modelName = scanner.nextLine();
        UUID carID = carService.findCarIDbyModelName(modelName);
        for (Rental rental : rentalService.getAllRentals()){
            if (rental.getCarID().equals(carID)){
                rentalService.deleteRental(rental.getID());
            }
            else {
                throw new IllegalArgumentException("There is no rented car with chosen model name");
            }
        }
    }

    public void runShowFilteredCarsOption(){
        System.out.println("Filtered cars options: ");
        System.out.println("1. Having specified number of seats");
        System.out.println("2. Having HP higher than specified value");
        System.out.println("3. Having specified fuel type");
        System.out.println("Select filter: ");
    }

    public void runShowCarsBySeatsOption(){
        System.out.println("Enter number of seats: ");
        int numberSeats = scanner.nextInt();
        FilterCarByNumSeats filterCarByNumSeats = new FilterCarByNumSeats(numberSeats);
        FilteredCarService filteredCarService = new FilteredCarService(carService.getCarRepo(), filterCarByNumSeats);
        System.out.println(filteredCarService.getAllCars());
    }

    public void runShowCarsByHorsePowerOption(){
        System.out.println("Enter horse power: ");
        int horsePower = scanner.nextInt();
        FilterCarByHorsePower filterCarByHorsePower = new FilterCarByHorsePower(horsePower);
        FilteredCarService filteredCarService = new FilteredCarService(carService.getCarRepo(), filterCarByHorsePower);
        System.out.println(filteredCarService.getAllCars());
    }

    public void runShowCarsByFuelTypeOption(){
        System.out.println("Enter fuel type: ");
        scanner.nextLine();
        String fuelTypeStr = scanner.nextLine();
        FuelType fuelType = FuelType.valueOf(fuelTypeStr.toLowerCase());
        FilterCarByFuelType filterCarByFuelType = new FilterCarByFuelType(fuelType);
        FilteredCarService filteredCarService = new FilteredCarService(carService.getCarRepo(), filterCarByFuelType);
        System.out.println(filteredCarService.getAllCars());
    }

    public void runShowFilteredRentalsOption(){
        System.out.println("Filtered rentals options: ");
        System.out.println("1. In progress at given date");
        System.out.println("2. With interval of time more than given duration");
        System.out.println("Select filter: ");
    }

    public void runShowRentalsInProgressAtDateOption(){
        System.out.println("Enter date: ");
        System.out.println("Day: ");
        int day = scanner.nextInt();
        System.out.println("Month: ");
        int month = scanner.nextInt();
        System.out.println("Year: ");
        int year = scanner.nextInt();
        LocalDate date = LocalDate.of(year,month,day);
        if(date.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Invalid date. Please enter a date in the future.");
        }
        FilterRentalInProgressAtDate filterRentalInProgressAtDate =
                new FilterRentalInProgressAtDate(date);
        FilteredRentalService filteredRentalService =
                new FilteredRentalService(rentalService.getRentalRepo(),
                        carService.getCarRepo(), filterRentalInProgressAtDate);
        System.out.println(filteredRentalService.getAllRentals());
    }

    public void runShowRentalsTimeMoreThanDurationOption(){
        System.out.println("Enter duration in days: ");
        int days = scanner.nextInt();
        Duration duration = Duration.ofDays(days);
        FilterRentalTimeMoreThanDuration filterRentalTimeMoreThanDuration =
                new FilterRentalTimeMoreThanDuration(duration);
        FilteredRentalService filteredRentalService =
                new FilteredRentalService(rentalService.getRentalRepo(),
                        carService.getCarRepo(), filterRentalTimeMoreThanDuration);
        System.out.println(filteredRentalService.getAllRentals());
    }

    public void runShowReportsOption() {
        System.out.println("1. Show given make diesel cars descending by horsePower");
        System.out.println("2. Show given makes gasoline cars that are two-seated");
        System.out.println("3. Show the modelName of cars with given fuelType and colour");

        System.out.println("Select report: ");
    }

    public void runShowReportOption1() {
        System.out.println("Enter make: ");
        String make = scanner.nextLine();
        carService.printGivenMakeDieselCarsDescendingByHorsePower(make);
    }

    public void runShowReportOption2() {
        int count = 1;
        boolean inputStop = false;
        ArrayList<String> makes = new ArrayList<>();
        while (!inputStop){
            System.out.println("Enter make " + count + ": ");
            System.out.println("Enter '0' when enough makes.");
            String keyboardInput = scanner.nextLine();
            if (keyboardInput.equals("0"))
                inputStop = true;
            else {
                count++;
                makes.add(keyboardInput);
            }
        }
        carService.printGivenMakesTwoSeatedGasolineCars(makes.toArray(new String[0]));
    }

    public void runShowReportOption3() {
        System.out.println("Enter fuelType: ");
        String fuelTypeStr = scanner.nextLine();
        FuelType fuelType = FuelType.valueOf(fuelTypeStr.toLowerCase());
        System.out.println("Enter colour: ");
        String colourStr = scanner.nextLine();
        Colour colour = Colour.valueOf(colourStr.toLowerCase());
        carService.printCarsModelNameOfGivenFuelTypeAndColourByHorsePower(fuelType,colour);
    }

    public void run(){
        while (true){
            showMenu();
            System.out.println("Choose an option: ");
            int userOption = scanner.nextInt();
            scanner.nextLine();
            if (userOption == EXIT_OPTION) return;
            switch (userOption){
                case ADD_CAR_OPTION:{
                    runAddCarOption();
                    break;
                }
                case REMOVE_CAR_OPTION:{
                    runRemoveCarOption();
                    break;
                }
                case SHOW_ALL_CARS_OPTION:{
                    runShowAllCarsOption();
                    break;
                }
                case SHOW_FILTERED_CARS_OPTION:{
                    runShowFilteredCarsOption();
                    int userFilterCarOption = scanner.nextInt();
                    switch (userFilterCarOption){
                        case SHOW_CARS_BY_SEATS_OPTION:{
                            runShowCarsBySeatsOption();
                            break;
                        }
                        case SHOW_CARS_BY_HORSEPOWER_OPTION:{
                            runShowCarsByHorsePowerOption();
                            break;
                        }
                        case SHOW_CARS_BY_FUEL_TYPE_OPTION:{
                            runShowCarsByFuelTypeOption();
                            break;
                        }
                        default:{
                            System.out.println("Car filter option not matched.");
                        }
                    }
                    break;
                }
                case SHOW_FILTERED_RENTALS_OPTION:{
                    runShowFilteredRentalsOption();
                    int userFilterRentalOption = scanner.nextInt();
                    switch (userFilterRentalOption){
                        case SHOW_RENTALS_IN_PROGRESS_OPTION:{
                            runShowRentalsInProgressAtDateOption();
                            break;
                        }
                        case SHOW_RENTALS_TIME_MORE_THAN_DURATION:{
                            runShowRentalsTimeMoreThanDurationOption();
                            break;
                        }
                        default:{
                            System.out.println("Rental filter option not matched.");
                        }
                    }
                    break;
                }
                case SHOW_AVAILABLE_CARS_OPTION:{
                    runShowAvailableCarsOption();
                    break;
                }
                case SHOW_RENTED_CARS_OPTION:{
                    runShowRentedCarsOption();
                    break;
                }
                case RENT_CAR_OPTION:{
                    runRentCarOption();
                    break;
                }
                case CANCEL_RENTAL_OPTION:{
                    runCancelRentalOption();
                    break;
                }
                case REPORTS_OPTION:{
                    runShowReportsOption();
                    int userReportOption = scanner.nextInt();
                    scanner.nextLine();
                    switch (userReportOption){
                        case REPORT_1_OPTION:{
                            runShowReportOption1();
                            break;
                        }
                        case REPORT_2_OPTION:{
                            runShowReportOption2();
                            break;
                        }
                        case REPORT_3_OPTION:{
                            runShowReportOption3();
                            break;
                        }
                        default:{
                            System.out.println("Report option not matched.");
                        }
                    }
                    break;
                }
                default:{
                    System.out.println("Option not matched.");
                }
            }
        }
    }
}
