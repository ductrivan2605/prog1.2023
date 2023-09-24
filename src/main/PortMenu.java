package main;

import java.util.Scanner;
import main.entities.Port;
import main.entities.Vehicles.VehicleType;
public class PortMenu {
    public void displayPortMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Port Details");
        System.out.println("2. Trip Management");
        System.out.println("3. Container and Vehicle Management");
        System.out.println("4. Allowed Vehicle Types Management");
        System.out.println("5. Landing Ability Check");
        System.out.println("6. Distance Calculation");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        Port otherPort = new Port();

        switch (choice) {
            case 1 ->
                // A method for Port Details
                displayPortDetails();
            case 2 ->
                // A method for Trip Management
                    manageTrips();
            case 3 ->
                // A method for Container and Vehicle Management
                    manageContainersAndVehicles();
            case 4 ->
                // A method for Allowed Vehicle Types Management
                    manageAllowedVehicleTypes();
            case 5 ->
                // A method for Landing Ability Check
                checkLandingAbility();
            case 6 ->
                // A method for Distance Calculation
                calculateDistance();

            case 0 -> {
            }
            // Exit the Port Menu
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void displayPortDetails(Port port) {
        // Implement Port Details functionality
        System.out.println("Accessing Port Details...");
        System.out.println("Port Name: " + port.getName());
        System.out.println("Storing Capacity: " + port.getStoringCapacity());
        System.out.println("Landing Ability: " + port.isLandingAbility());
        System.out.println("Container Count: " + port.getContainerCount());
        System.out.println("Vehicle Count: " + port.getVehicleCount());
    }

    private void manageTrips() {
        // Implement Trip Management functionality
        System.out.println("Accessing Trip Management...");
    }

    private void manageContainersAndVehicles() {
        // Implement Container and Vehicle Management functionality
        System.out.println("Accessing Container and Vehicle Management...");
    }

    private void manageAllowedVehicleTypes() {
        // Implement Allowed Vehicle Types Management functionality
        System.out.println("Accessing Allowed Vehicle Types Management...");
    }

    private void checkLandingAbility(Port port) {
        // Implement Landing Ability Check functionality
        System.out.println("Accessing Landing Ability Check...");
        System.out.println("Landing ability for ship: " + port.hasLandingAbilityFor(VehicleType.SHIP));
        System.out.println("Landing ability for basic truck: " + port.hasLandingAbilityFor(VehicleType.BASIC_TRUCK));
        System.out.println("Landing ability for reefer truck: " + port.hasLandingAbilityFor(VehicleType.REEFER_TRUCK));
        System.out.println("Landing ability for tanker truck: " + port.hasLandingAbilityFor(VehicleType.TANKER_TRUCK));
    }

    private void calculateDistance(Port port, Port otherPort) {
        // Implement Distance Calculation functionality
        System.out.println("Accessing Distance Calculation...");
        System.out.println("Distance between Ports: " + port.calculateDistanceTo(otherPort));
    }
}



