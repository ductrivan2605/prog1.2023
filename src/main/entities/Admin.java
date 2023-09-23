package main.entities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.entities.Containers.ContainerType;

public class Admin implements User {
    private String username;
    private String password;
     private List<Vehicles> vehiclesUnderControl;
     private List<Ship> shipsUnderControl;
     private List<Trip> trips;
     private List<Trip> tripsUnderControl;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public UserRole getRole() {
        return UserRole.ADMIN;
    }

    @Override
    public boolean hasPermission(Operation operation) {
        // Admin has permission for all operations
        return true;
    }
    @Override
    public double calculateDailyFuelConsumption() {
        double totalFuelConsumption = 0.0;
        for (Vehicles vehicles : vehiclesUnderControl) {
            totalFuelConsumption += vehicles.calculateDailyFuelConsumption();
        }
        return totalFuelConsumption;
    }
    @Override
    public Map<ContainerType, Double> calculateTotalWeightByContainerType() {
        Map<ContainerType, Double> totalWeightByContainerType = new HashMap<>();

        for (Vehicles vehicle : vehiclesUnderControl) {
            List<Containers> loadedContainers = vehicle.getLoadedContainers();
            for (Containers container : loadedContainers) {
                ContainerType containerType = container.getType();
                double containerWeight = container.getWeight();

                totalWeightByContainerType.put(
                    containerType,
                    totalWeightByContainerType.getOrDefault(containerType, 0.0) + containerWeight
                );
            }
        }

        return totalWeightByContainerType;
    }
    @Override
    public List<Ship> listAllShipsInPort(Port port) {
        List<Ship> shipsInPort = new ArrayList<>();

        for (Ship ship : shipsUnderControl) {
            if (ship.getCurrentPort() != null && ship.getCurrentPort().equals(port)) {
                shipsInPort.add(ship);
            }
        }

        return shipsInPort;
    }
    @Override
    public List<Trip> listTripsInDay(Date date) {
        List<Trip> tripsInDay = new ArrayList<>();
        
        LocalDate inputDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    
        for (Trip trip : trips) {
            if (isSameDay(trip.getDepartureDate(), inputDate) || isSameDay(trip.getArrivalDate(), inputDate)) {
                tripsInDay.add(trip);
            }
        }
    
        return tripsInDay;
    }
    private boolean isSameDay(Date date1, LocalDate inputDate) {
        // Convert the Date to LocalDate
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    
        // Check if the LocalDate and the Date objects represent the same day
        return localDate1.isEqual(inputDate);
    }
    
    @Override
    public List<Trip> listTripsBetweenDays(LocalDate dayA, LocalDate dayB) {
        List<Trip> tripsBetweenDays = new ArrayList<>();

        for (Trip trip : tripsUnderControl) {
            Date departureDate = trip.getDepartureDate();
            LocalDate tripDate = departureDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            if (!tripDate.isBefore(dayA) && !tripDate.isAfter(dayB)) {
                tripsBetweenDays.add(trip);
            }
        }

        return tripsBetweenDays;
    }

    public List<Trip> listTripsBetweenUserInputDates() {
        List<Trip> tripsBetweenDays = new ArrayList<>();

        // Create a Scanner to input the desired departure dates from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first date (yyyy-MM-dd): ");
        String inputDateStrA = scanner.nextLine();
        System.out.print("Enter the second date (yyyy-MM-dd): ");
        String inputDateStrB = scanner.nextLine();

        try {
            LocalDate dateA = LocalDate.parse(inputDateStrA);
            LocalDate dateB = LocalDate.parse(inputDateStrB);

            for (Trip trip : tripsUnderControl) {
                Date departureDate = trip.getDepartureDate();
                LocalDate tripDate = departureDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                if (!tripDate.isBefore(dateA) && !tripDate.isAfter(dateB)) {
                    tripsBetweenDays.add(trip);
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
        } finally {
            scanner.close(); // Close the scanner when done
        }

        return tripsBetweenDays;
    }
}
