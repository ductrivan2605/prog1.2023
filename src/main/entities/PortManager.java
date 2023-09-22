package main.entities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.entities.Containers.ContainerType;

public class PortManager implements User {
    private String username;
    private String password;
    private String portName; // Additional property for PortManager
    private List<String> managedPortNames; // List of port names that this PortManager can manage vehicles for
    private List<String> allowedViewHistoryPorts; // List of ports where this PortManager can view history
    private List<Vehicles> vehiclesUnderControl;
    private List<Ship> shipsUnderControl;
    private List<Trip> trips;
    private List<Trip> tripsUnderControl;
    private Scanner scanner;

    public PortManager(String username, String password, String portName) {
        this.username = username;
        this.password = password;
        this.portName = portName;
        this.scanner = scanner;
        this.managedPortNames = new ArrayList<>();
        this.allowedViewHistoryPorts = new ArrayList<>();
        allowedViewHistoryPorts.add("PortA");
        allowedViewHistoryPorts.add("PortC");
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
        return UserRole.PORT_MANAGER;
    }

    @Override
    public boolean hasPermission(Operation operation) {
        // Implement permission logic based on portName and operation
        switch (operation) {
            case MANAGE_VEHICLES:
                // Check if the PortManager can manage vehicles for the specific port
                return canManageVehicles();
            case MANAGE_CONTAINERS:
                // Check if the PortManager can manage containers for the specific port
                return canManageContainers();
            case MANAGE_PORTS:
                // PortManagers generally do not have permission to manage ports themselves
                return false;
            case VIEW_HISTORY:
                // Check if the PortManager can view history for the specific port
                return canViewHistory();
            default:
                return false; // Unknown operation
        }
    }
    private boolean canManageVehicles() {
        // Check if the portName matches any of the managedPortNames
        return managedPortNames.contains(portName);
    }
    // Method to add a port to the list of managed ports
    public void addManagedPort(String portName) {
        managedPortNames.add(portName);
    }

    // Method to remove a port from the list of managed ports
    public void removeManagedPort(String portName) {
        managedPortNames.remove(portName);
    }
    private boolean canManageContainers() {
    // Define a list of port names that this PortManager can manage containers for
    List<String> allowedPorts = Arrays.asList("PortA", "PortC");

    // Check if the portName matches any of the allowed ports
        return allowedPorts.contains(portName);
    }
    private boolean canViewHistory() {
        return allowedViewHistoryPorts.contains(portName);
    }
    //Method to calculate daily's fuel connsumpion
    @Override
    public double calculateDailyFuelConsumption() {
        double totalFuelConsumption = 0.0;
        for (Vehicles vehicles : vehiclesUnderControl) {
            totalFuelConsumption += vehicles.calculateDailyFuelConsumption();
        }
        return totalFuelConsumption;
    }
    //Method to calculate how much weight of each type of all containers
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
    
    // Create a Scanner to input the desired departure date from the user
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the departure date (yyyy-MM-dd): ");
    String inputDateStr = scanner.nextLine();

    try {
        LocalDate inputDate = LocalDate.parse(inputDateStr);
        
        for (Trip trip : trips) {
            if (isSameDay(trip.getDepartureDate(), inputDate) || isSameDay(trip.getArrivalDate(), inputDate)) {
                tripsInDay.add(trip);
            }
        }
    } catch (Exception e) {
        System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
    } finally {
        scanner.close(); // Close the scanner when done
    }

    return tripsInDay;
}
    private boolean isSameDay(Date date1, LocalDate inputDate) {
    // Convert the Date objects to LocalDate
    LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate localDate2 = inputDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    // Check if the LocalDate objects represent the same day
    return localDate1.isEqual(localDate2);
    }
    @Override
    public List<Trip> listTripsBetweenDays(LocalDate dayA, LocalDate dayB) {
        List<Trip> tripsBetweenDays = new ArrayList<>();

        for (Trip trip : tripsUnderControl) {
            LocalDate tripDate = trip.getDepartureDate();
            if (tripDate != null && !tripDate.isBefore(dayA) && !tripDate.isAfter(dayB)) {
                tripsBetweenDays.add(trip);
            }
        }

        return tripsBetweenDays;
    }
}