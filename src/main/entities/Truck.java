package main.entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import main.entities.Containers;

import static main.entities.Containers.ContainerType.*;

public class Truck implements Vehicles {
    private String vehicleId;
    private String name;
    private double currentFuel;
    private double carryingCapacity;
    private double fuelCapacity;
    private Port currentPort;
    private int totalContainers;
    private VehicleType vehicleType;
    private List<Container> loadedContainers;

    public Truck(String vehicleId, String name, double currentFuel, double carryingCapacity, double fuelCapacity, Port currentPort, VehicleType vehicleType) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.currentFuel = currentFuel;
        this.carryingCapacity = carryingCapacity;
        this.fuelCapacity = fuelCapacity;
        this.currentPort = currentPort;
        this.totalContainers = 0;
        this.vehicleType = vehicleType;
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

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<Container> getLoadedContainers() {
        return loadedContainers;
    }
    public List<Containers.ContainerType> getLoadedContainerTypes() {
        List<Containers.ContainerType> containerTypes = new ArrayList<>();
        for (Containers containers : loadedContainers) {
            containerTypes.add(containers.getType());
        }
        return containerTypes;
    }
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
        }

        return fuelConsumptionRate * distance;
    }

    public void loadContainer(Container container) {
        loadedContainers.add(container);
        totalContainers++;
    }

    public void unloadContainer(Container container) {
        loadedContainers.remove(container);
        totalContainers--;
    }

    private double calculateFuelConsumptionForBasicTruck(List<Containers.ContainerType> containerTypes) {
        double fuelConsumptionRate = 0.0;

        for (Containers.ContainerType type : containerTypes) {
            switch (type) {
                case DRY_STORAGE:
                    fuelConsumptionRate += 4.6;
                    break;
                case OPEN_TOP:
                    fuelConsumptionRate += 3.2;
                    break;
                case OPEN_SIDE:
                    fuelConsumptionRate += 3.2;
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
}
