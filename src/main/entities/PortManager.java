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

public class PortManager extends User {
    private String portName; // Additional property for PortManager
    private List<String> managedPortNames; // List of port names that this PortManager can manage vehicles for
    private List<String> allowedViewHistoryPorts; // List of ports where this PortManager can view history

    public PortManager(String username, String password, String portName) {
        super(username, password);
        this.portName = portName;
        this.managedPortNames = new ArrayList<>();
        this.allowedViewHistoryPorts = new ArrayList<>();
        allowedViewHistoryPorts.add("PortA");
        allowedViewHistoryPorts.add("PortC");
        super.setUserRole("PortManager");
    }

    @Override
    public boolean hasPermission(Operation operation) {
        // Implement permission logic based on portName and operation
        return switch (operation) {
            case MANAGE_VEHICLES ->
                // Check if the PortManager can manage vehicles for the specific port
                    canManageVehicles();
            case MANAGE_CONTAINERS ->
                // Check if the PortManager can manage containers for the specific port
                    canManageContainers();
            case MANAGE_PORTS ->
                // PortManagers generally do not have permission to manage ports themselves
                    false;
            case VIEW_HISTORY ->
                // Check if the PortManager can view history for the specific port
                    canViewHistory();
        };
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

    // Method to calculate daily's fuel consumption
    @Override
    public double calculateDailyFuelConsumption() {
        double totalFuelConsumption = 0.0;
        for (Vehicles vehicles : super.getVehiclesUnderControl()) {
            totalFuelConsumption += vehicles.calculateDailyFuelConsumption();
        }
        return totalFuelConsumption;
    }

    @Override
    public List<Trip> listTripsInDay(Date date) {
        List<Trip> tripsInDay = new ArrayList<>();

        // Create a Scanner to input the desired departure date from the user
        Scanner scanner = new Scanner(System.in);

        try (scanner) {
            System.out.print("Enter the departure date (yyyy-MM-dd): ");
            String inputDateStr = scanner.nextLine();
            LocalDate inputDate = LocalDate.parse(inputDateStr);

            for (Trip trip : super.getTrips()) {
                if (isSameDay(trip.getDepartureDate(), inputDate) || isSameDay(trip.getArrivalDate(), inputDate)) {
                    tripsInDay.add(trip);
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
        }
        // Close the scanner when done

        return tripsInDay;
    }

    private boolean isSameDay(Date date1, LocalDate inputDate) {
        // Convert the Date to LocalDate
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Check if the LocalDate and the Date objects represent the same day
        return localDate1.isEqual(inputDate);
    }
}