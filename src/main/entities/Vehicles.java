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
    private int totalContainers;
    private VehicleType vehicleType;
    private List<Containers> loadedContainers;

    public Vehicles(String vehicleId, String name, double currentFuel, double carryingCapacity, double fuelCapacity, Port currentPort) {
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

    void setTotalContainers(int totalContainers);

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public List<Containers> getLoadedContainers() {
        return loadedContainers;
    }

    void moveToPort(Port targetPort);

    void refuel(double amount);

    // Method to calculate required fuel for a trip
    double calculateRequiredFuel(double distance);

    // Method to load containers onto the vehicle
    void loadContainer(Containers container);

    // Method to unload containers from the vehicle
    void unloadContainer(Containers container);

    double calculateDailyFuelConsumption();

    List<Containers> getLoadedContainers();

    // Enum to represent vehicle types
    enum VehicleType {
        SHIP,
        BASIC_TRUCK, REEFER_TRUCK, TANKER_TRUCK, TRUCK
    }

    
}
