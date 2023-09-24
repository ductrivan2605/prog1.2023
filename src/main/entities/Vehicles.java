package main.entities;

import java.util.List;

public interface Vehicles {
    String getVehicleId();

    void setVehicleId(String vehicleId);

    String getName();

    void setName(String name);

    double getCurrentFuel();

    void setCurrentFuel(double currentFuel);

    double getCarryingCapacity();

    void setCarryingCapacity(double carryingCapacity);

    double getFuelCapacity();

    void setFuelCapacity(double fuelCapacity);

    Port getCurrentPort();

    void setCurrentPort(Port currentPort);

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
