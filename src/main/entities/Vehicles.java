package main.entities;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicles {
    private static int totalContainers;
    private static List<Containers> loadedContainers;
    private String vehicleId;
    private String name;
    private double currentFuel;
    private double carryingCapacity;
    private double fuelCapacity;
    private Port currentPort;
    private VehicleType vehicleType;

    public Vehicles(String vehicleId, String name, double currentFuel, double carryingCapacity, double fuelCapacity, Port currentPort) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.currentFuel = currentFuel;
        this.carryingCapacity = carryingCapacity;
        this.fuelCapacity = fuelCapacity;
        this.currentPort = currentPort;
        totalContainers = 0;
        this.vehicleType = null;
        loadedContainers = new ArrayList<>();
    }

    //getters and setters
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

    public void setTotalContainers(int totalContainer) {
        totalContainers = totalContainer;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<Containers> getLoadedContainers() {
        return loadedContainers;
    }

    public void setLoadedContainers(List<Containers> loadedConatiners){
        loadedContainers = loadedConatiners;
    }

    //methods

    abstract void moveToPort(Port targetPort);

    abstract boolean canMoveToPort(Port port);

    abstract void refuel(double amount);

    // Method to calculate required fuel for a trip
    abstract double calculateRequiredFuel(double distance);

    //Algorithms for load and unload containers
    // Method to load containers onto the vehicle
    public void loadContainer(Containers container) {
        // Add the container to the list of loaded containers
        loadedContainers.add(container);
        // Update the total container count
        totalContainers++;
    }

    // Method to unload containers from the vehicle
    public void unloadContainer(Containers container) {
        // Remove the container from the list of loaded containers
        loadedContainers.remove(container);
        // Update the total container count
        totalContainers--;
    }

    abstract double calculateDailyFuelConsumption();

    // Enum to represent vehicle types
    public enum VehicleType {
        SHIP,
        BASIC_TRUCK, REEFER_TRUCK, TANKER_TRUCK
    }

    
}
