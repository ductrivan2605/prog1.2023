package main.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.entities.Vehicles.VehicleType;

public class Port {
    private String id; // Unique ID (formatted as p-number)
    private String name;
    private double latitude;
    private double longitude;
    private int storingCapacity;
    private boolean landingAbility;
    private int containerCount;
    private int vehicleCount;
    private List<Trip> pastTrips;
    private List<Trip> currentTrips;
    private Set<VehicleType> allowedVehicleTypes = new HashSet<>();
    private static final double EARTH_RADIUS_KM = 6371.0;
    private PortManager portManager;

    public Port(String id, String name, double latitude, double longitude, int storingCapacity, boolean landingAbility) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.containerCount = 0;
        this.vehicleCount = 0;
        this.pastTrips = new ArrayList<>();
        this.currentTrips = new ArrayList<>();
        this.portManager = null;
    }

    // Getters and setters for properties
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getStoringCapacity() {
        return storingCapacity;
    }

    public boolean isLandingAbility() {
        return landingAbility;
    }

    public int getContainerCount() {
        return containerCount;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }
    // Get the list of past trips
    public List<Trip> getPastTrips() {
        return pastTrips;
    }

    // Get the list of current trips
    public List<Trip> getCurrentTrips() {
        return currentTrips;
    }
    public PortManager getPortManager() {
        return portManager;
    }
    // Add a trip to the list of past trips
    public void addPastTrip(Trip trip) {
        pastTrips.add(trip);
    }

    // Add a trip to the list of current trips
    public void addCurrentTrip(Trip trip) {
        currentTrips.add(trip);
    }
    // Add a container to the port
    public void addContainer() {
        containerCount++;
    }

    // Remove a container from the port
    public void removeContainer() {
        if (containerCount > 0) {
            containerCount--;
        }
    }

    // Add a vehicle to the port
    public void addVehicle() {
        vehicleCount++;
    }

    // Remove a vehicle from the port
    public void removeVehicle() {
        if (vehicleCount > 0) {
            vehicleCount--;
        }
    }
    public Set<VehicleType> getAllowedVehicleTypes() {
        return allowedVehicleTypes;
    }

    public void addAllowedVehicleType(VehicleType vehicleType) {
        allowedVehicleTypes.add(vehicleType);
    }

    public void removeAllowedVehicleType(VehicleType vehicleType) {
        allowedVehicleTypes.remove(vehicleType);
    }
    // Method to check if the port has landing ability for a specific vehicle type
    public boolean hasLandingAbilityFor(VehicleType vehicleType) {
        return allowedVehicleTypes.contains(vehicleType) && landingAbility;
    }
    // Calculate the distance between this port and another port using Haversine formula
    public double calculateDistanceTo(Port otherPort) {
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(otherPort.latitude);
        double lon2 = Math.toRadians(otherPort.longitude);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
    // Assign a PortManager to control this port
    public void assignPortManager(PortManager portManager) {
        if (this.portManager == null) {
            this.portManager = portManager;
        } else {
            System.out.println("This port is already managed by a Port Manager.");
        }
    }
    // Remove the PortManager from controlling this port
    public void removePortManager() {
        this.portManager = null;
    }

    // Check if this port is controlled by a Port Manager
    public boolean isManagedByPortManager() {
        return portManager != null;
    }
}
