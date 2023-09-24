package main.entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.util.logging.Logger;

public class Truck extends Vehicles {
    private static final Logger logger = Logger.getLogger(Truck.class.getName());

    // Constructor
    public Truck(String vehicleId, String name, double currentFuel, double carryingCapacity, double fuelCapacity, Port currentPort) {
        super(vehicleId, name, currentFuel, carryingCapacity, fuelCapacity, currentPort);
    }
    //Getters and setters

    public static void deleteTruck(List<Truck> trucks, String vehicleId, String fileName) {
        // Find the truck to delete by vehicleId
        Truck truckToDelete = null;
        for (Truck truck : trucks) {
            if (truck.getVehicleId().equals(vehicleId)) {
                truckToDelete = truck;
                break;
            }
        }

        if (truckToDelete != null) {
            // Remove the truck from the list
            trucks.remove(truckToDelete);
            // Save the updated list of trucks
            saveTrucks(trucks, fileName);
            System.out.println("Truck with vehicleId " + vehicleId + " deleted successfully.");
        } else {
            System.out.println("Truck with vehicleId " + vehicleId + " not found.");
        }
    }

    public static void updateTruck(List<Truck> trucks, String vehicleId, Truck updatedTruck, String fileName) {
        // Find the truck to update by vehicleId
        Truck truckToUpdate = null;
        for (Truck truck : trucks) {
            if (truck.getVehicleId().equals(vehicleId)) {
                truckToUpdate = truck;
                break;
            }
        }

        if (truckToUpdate != null) {
            // Update the truck with new data
            truckToUpdate.setName(updatedTruck.getName());
            truckToUpdate.setCurrentFuel(updatedTruck.getCurrentFuel());
            truckToUpdate.setCarryingCapacity(updatedTruck.getCarryingCapacity());
            truckToUpdate.setFuelCapacity(updatedTruck.getFuelCapacity());
            truckToUpdate.setCurrentPort(updatedTruck.getCurrentPort());
            truckToUpdate.setTotalContainers(updatedTruck.getTotalContainers());
            truckToUpdate.setVehicleType(updatedTruck.getVehicleType());
            truckToUpdate.setLoadedContainers(updatedTruck.getLoadedContainers());

            // Save the updated list of trucks
            saveTrucks(trucks, fileName);
            System.out.println("Truck with vehicleId " + vehicleId + " updated successfully.");
        } else {
            System.out.println("Truck with vehicleId " + vehicleId + " not found.");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Truck> loadTrucks(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            List<Truck> trucks = (List<Truck>) inputStream.readObject();
            logger.info("Trucks loaded successfully from " + fileName); // Log the success message
            return trucks;
        } catch (IOException | ClassNotFoundException e) {
            logger.severe("Failed to load trucks from " + fileName); // Log the error message
            e.printStackTrace();
            return null;
        }
    }

    //    Save the list of all Trucks to a file
    public static void saveTrucks(List<Truck> trucks, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(trucks);
            logger.info("Trucks saved successfully to " + fileName); // Log the success message
        } catch (IOException e) {
            logger.severe("Failed to save trucks to " + fileName); // Log the error message
            e.printStackTrace();
        }
    }

    public List<Containers.ContainerType> getLoadedContainerTypes() {
        List<Containers.ContainerType> containerTypes = new ArrayList<>();
        for (Containers container : super.getLoadedContainers()) {
            containerTypes.add(container.getType());
        }
        return containerTypes;
    }

    //Fuel calculation for each type of truck
    @Override
    public double calculateRequiredFuel(double distance) {
        double fuelConsumptionRate = 0.0;

        switch (getVehicleType()) {
            case BASIC_TRUCK:
                fuelConsumptionRate = calculateFuelConsumptionForBasicTruck(getLoadedContainerTypes());
                break;
            case REEFER_TRUCK:
                fuelConsumptionRate = calculateFuelConsumptionForReeferTruck(getLoadedContainerTypes());
                break;
            case TANKER_TRUCK:
                fuelConsumptionRate = calculateFuelConsumptionForTankerTruck(getLoadedContainerTypes());
                break;
            default:
                break;
        }

        return fuelConsumptionRate * distance;
    }

    //Fuel consumption of each type of truck based on Container Type
    private double calculateFuelConsumptionForBasicTruck(List<Containers.ContainerType> containerTypes) {
        double fuelConsumptionRate = 0.0;

        for (Containers.ContainerType type : containerTypes) {
            switch (type) {
                case DRY_STORAGE:
                    fuelConsumptionRate += 4.6;
                    break;
                case OPEN_TOP, OPEN_SIDE:
                    fuelConsumptionRate += 3.2;
                    break;
                default:
                    break;
            }
        }

        return fuelConsumptionRate;
    }

    private double calculateFuelConsumptionForReeferTruck(List<Containers.ContainerType> containerTypes) {
        double fuelConsumptionRate = 0.0;

        for (Containers.ContainerType type : containerTypes) {
            switch (type) {
                case DRY_STORAGE:
                    fuelConsumptionRate += 4.6;
                    break;
                case REFRIGERATED:
                    fuelConsumptionRate += 5.4;
                    break;
                default:
                    break;
            }
        }

        return fuelConsumptionRate;
    }

    private double calculateFuelConsumptionForTankerTruck(List<Containers.ContainerType> containerTypes) {
        double fuelConsumptionRate = 0.0;

        for (Containers.ContainerType type : containerTypes) {
            if (type == Containers.ContainerType.LIQUID) {
                fuelConsumptionRate += 5.3;
            }
        }

        return fuelConsumptionRate;
    }

    //Checking if a truck can move to a port based on its condition
    public boolean canMoveToPort(Port port) {
        // Check if the truck can move to the specified port based on its current load and the port's landing ability
        if (port.hasLandingAbilityFor(getVehicleType())) {
            // Check if the total weight of loaded containers is within the carrying capacity
            double totalWeight = calculateTotalWeightOfLoadedContainers();
            return totalWeight <= getCarryingCapacity();
        }
        return false; // The port doesn't have landing ability for this type of truck
    }

    @Override
    public void moveToPort(Port targetPort) {
        if (getCurrentPort() == null) {
            // The truck is currently not at any port (sailaway)
            System.out.println("Truck " + getVehicleId() + " is not at any port.");
            return;
        }

        if (!targetPort.hasLandingAbilityFor(getVehicleType())) {
            // The target port doesn't have landing ability for this type of vehicle
            System.out.println("Truck " + getVehicleId() + " cannot move to " + targetPort.getName() + ".");
            return;
        }

        if (canMoveToPort(targetPort)) {
            setCurrentPort(targetPort);
            System.out.println("Truck " + getVehicleId() + " has moved to " + targetPort.getName() + ".");
        } else {
            System.out.println("Truck " + getVehicleId() + " cannot move to " + targetPort.getName() + " due to load capacity.");
        }
    }

    //Refuelling Trucks
    @Override
    public void refuel(double amount) {
        if (amount > 0) {
            double newFuelLevel = getCurrentFuel() + amount;
            if (newFuelLevel <= getFuelCapacity()) {
                setCurrentFuel(newFuelLevel);
                System.out.println("Truck refueled successfully. Current fuel level: " + newFuelLevel);
            } else {
                System.out.println("Refueling failed. Fuel tank capacity exceeded.");
            }
        } else {
            System.out.println("Invalid refueling amount.");
        }
    }

    @Override
    public double calculateDailyFuelConsumption() {
        double dailyFuelConsumption = 0.0;

        switch (getVehicleType()) {
            case BASIC_TRUCK:
                // Calculate daily fuel consumption for basic trucks
                dailyFuelConsumption = calculateDailyFuelConsumptionForBasicTruck();
                break;
            case REEFER_TRUCK:
                // Calculate daily fuel consumption for reefer trucks
                dailyFuelConsumption = calculateDailyFuelConsumptionForReeferTruck();
                break;
            case TANKER_TRUCK:
                // Calculate daily fuel consumption for tanker trucks
                dailyFuelConsumption = calculateDailyFuelConsumptionForTankerTruck();
                break;
            default:
                break;
        }

        return dailyFuelConsumption;
    }

    //Illusion daily fuel consumption number for each trucks
    public double calculateDailyFuelConsumptionForBasicTruck() {
        // Calculate daily fuel consumption for basic trucks
        return 50.0;
    }

    public double calculateDailyFuelConsumptionForReeferTruck() {
        // Calculate daily fuel consumption for reefer trucks
        return 60.0;
    }

    public double calculateDailyFuelConsumptionForTankerTruck() {
        // Calculate daily fuel consumption for tanker trucks
        return 70.0;
    }
    //  // Save the Truck object to a file
    //  saveTruckToFile(truck, "truck.ser");

    // Load the Truck object from the file
    //  Truck loadedTruck = loadTruckFromFile("truck.ser");
    // Verify that the loadedTruck object is not null and print its details
    //  if (loadedTruck != null) {
    //     System.out.println("Truck Details:");
    //     System.out.println("Vehicle ID: " + vehicleId);
    //     System.out.println("Name: " + name);
    //     System.out.println("Current Fuel: " + currentFuel);
    //     System.out.println("Carrying Capacity: " + carryingCapacity);
    //     System.out.println("Fuel Capacity: " + fuelCapacity);
    //     System.out.println("Current Port: " + currentPort.getName()); // Assuming Port has a getName() method
    //     System.out.println("Total Containers: " + totalContainers);
    //     System.out.println("Vehicle Type: " + vehicleType);
    //     System.out.println("Loaded Containers: " + loadedContainers);
    // }
}
