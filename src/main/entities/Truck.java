package main.entities;

import java.util.ArrayList;
import java.util.List;

public class Truck implements Vehicles {
    private String vehicleId;
    private String name;
    private double currentFuel;
    private double carryingCapacity;
    private double fuelCapacity;
    private Port currentPort;
    private int totalContainers;
    private VehicleType vehicleType;
    private List<Containers> loadedContainers;

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

    public List<Containers> getLoadedContainers() {
        return loadedContainers;
    }
    public List<Containers.ContainerType> getLoadedContainerTypes() {
        List<Containers.ContainerType> containerTypes = new ArrayList<>();
        for (Containers container : loadedContainers) { 
            containerTypes.add(container.getType());
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
            default:
                break;
        }

        return fuelConsumptionRate * distance;
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

    @Override
    public void loadContainer(Containers container) {
        if (loadedContainers.size() < carryingCapacity) {
        loadedContainers.add(container);
        totalContainers++;
    }
}

    @Override
    public void unloadContainer(Containers container) {
        if (loadedContainers.contains(container)) {
            loadedContainers.remove(container);
            totalContainers--;
    }
    }
    public boolean canMoveToPort(Port port) {
        // Check if the truck can move to the specified port based on its current load and the port's landing ability
        if (port.hasLandingAbilityFor(getVehicleType())) {
            // Check if the total weight of loaded containers is within the carrying capacity
            double totalWeight = calculateTotalWeightOfLoadedContainers();
            return totalWeight <= getCarryingCapacity();
        }
        return false; // The port doesn't have landing ability for this type of truck
    }

    private double calculateTotalWeightOfLoadedContainers() {
        double totalWeight = 0.0;
        for (Containers container : loadedContainers) {
            totalWeight += container.getWeight();
        }
        return totalWeight;
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
    private double calculateDailyFuelConsumptionForBasicTruck() {
        // Calculate daily fuel consumption for basic trucks
        return 50.0;
    }

    private double calculateDailyFuelConsumptionForReeferTruck() {
        // Calculate daily fuel consumption for reefer trucks
        return 60.0; 
    }

    private double calculateDailyFuelConsumptionForTankerTruck() {
        // Calculate daily fuel consumption for tanker trucks
        return 70.0;
    }


}
