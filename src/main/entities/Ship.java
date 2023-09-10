package main.entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ship implements Vehicles {
    private String vehicleId;
    private String name;
    private double currentFuel;
    private double carryingCapacity;
    private double fuelCapacity;
    private Port currentPort;
    private int totalContainers;
    private VehicleType vehicleType;
    private List<Container> loadedContainers;

    public Ship(String vehicleId, String name, double currentFuel, double carryingCapacity, double fuelCapacity, Port currentPort) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.currentFuel = currentFuel;
        this.carryingCapacity = carryingCapacity;
        this.fuelCapacity = fuelCapacity;
        this.currentPort = currentPort;
        this.totalContainers = 0;
        this.vehicleType = VehicleType.SHIP;
        this.loadedContainers = new ArrayList<>();
    }

    @Override
    public double calculateRequiredFuel(double distance) {
        // Calculate required fuel for a ship based on distance
        // For simplicity, we assume a constant fuel consumption rate per kilometer
        double fuelConsumptionRate = 2.5; // Adjust this value as needed
        return fuelConsumptionRate * distance;
    }

    // Getters and setters for Ship properties
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public int getTotalContainers() {
        return totalContainers;
    }

    public void setTotalContainers(int totalContainers) {
        this.totalContainers = totalContainers;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<Container> getLoadedContainers() {
        return loadedContainers;
    }

    // Method to load containers onto the ship
    @Override
    public void loadContainer(Container container) {
        // Add the container to the list of loaded containers
        loadedContainers.add(container);
        // Update the total container count
        totalContainers++;
    }

    // Method to unload containers from the ship
    @Override
    public void unloadContainer(Container container) {
        // Remove the container from the list of loaded containers
        loadedContainers.remove(container);
        // Update the total container count
        totalContainers--;
    }
}
