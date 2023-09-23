package main.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.entities.Vehicles;

public class VehicleService {
    private Map<String, Vehicles> vehicles;

    public VehicleService() {
        this.vehicles = new HashMap<>();
    }

    // Method to add a vehicle
    public void addVehicle(Vehicles vehicle) {
        vehicles.put(vehicle.getVehicleId(), vehicle);
    }

    // Method to get a vehicle by vehicle ID
    public Vehicles getVehicleById(String vehicleId) {
        return vehicles.get(vehicleId);
    }

    // Method to check if a vehicle exists
    public boolean vehicleExists(String vehicleId) {
        return vehicles.containsKey(vehicleId);
    }

    // Method to remove a vehicle
    public void removeVehicle(String vehicleId) {
        vehicles.remove(vehicleId);
    }

    // Method to retrieve a list of all vehicles
    public List<Vehicles> getAllVehicles() {
        return new ArrayList<>(vehicles.values());
    }
    // Additional methods for vehicle management
    public void updateVehicle(Vehicles vehicle) {
        if (vehicleExists(vehicle.getVehicleId())) {
            vehicles.put(vehicle.getVehicleId(), vehicle);
        } else {
            // Handle the case when the vehicle does not exist
            throw new IllegalArgumentException("Vehicle does not exist.");
        }
    }
}

