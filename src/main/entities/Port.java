package main.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.entities.Vehicles.VehicleType;

public class Port {
    private String id; // Unique ID (formatted as p-number)
    private String name;
    private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility;
    private int containerCount;
    private int vehicleCount;
    private List<Trip> pastTrips;
    private List<Trip> currentTrips;
    private Set<VehicleType> allowedVehicleTypes = new HashSet<>();
    private static final double EARTH_RADIUS_KM = 6371.0;
    private PortManager portManager;

    public Port(String id, String name, double latitude, double longitude, double storingCapacity, boolean landingAbility) {
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

    public double getStoringCapacity() {
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
    //Setters 
    public void setId(String id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public void setStoringCapacity(double storingCapacity) {
        this.storingCapacity = storingCapacity;
    }
    
    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }    
    public void setAllowedVehicleTypes(Set<VehicleType> allowedVehicleTypes) {
        this.allowedVehicleTypes = allowedVehicleTypes;
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
    // Set the container count
    public void setContainerCount(int containerCount) {
        this.containerCount = containerCount;
    }

    // Set the vehicle count
    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    // Set the port manager
    public void setPortManager(PortManager portManager) {
        this.portManager = portManager;
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
    public static List<Port> loadPorts(String filePath, List<PortManager> portManagers) {
        List<Port> ports = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length == 9) {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    double latitude = Double.parseDouble(data[2].trim());
                    double longitude = Double.parseDouble(data[3].trim());
                    double storingCapacity = Double.parseDouble(data[4].trim());
                    boolean landingAbility = Boolean.parseBoolean(data[5].trim());
                    int containerCount = Integer.parseInt(data[6].trim());
                    int vehicleCount = Integer.parseInt(data[7].trim());
                    String managerUsername = data[8].trim();
                    PortManager portManager = findPortManagerByUsername(managerUsername, portManagers);
                    Port port = new Port(id, name,latitude,longitude,storingCapacity, landingAbility);
                    port.setContainerCount(containerCount);
                    port.setVehicleCount(vehicleCount);
                    port.setPortManager(portManager);
                    ports.add(port);
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        return ports;
    }

    // Save ports to a CSV file
    public static void savePorts(List<Port> ports, String filePath) {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            for (Port port : ports) {
                String data = String.format("%s,%s,%.6f,%.6f,%.2f,%b,%d,%d,%s",
                        port.getId(), port.getName(), port.getLatitude(), port.getLongitude(),
                        port.getStoringCapacity(), port.isLandingAbility(), port.getContainerCount(),
                        port.getVehicleCount(), port.getPortManager().getUsername());
                writer.println(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Find a PortManager by username
public static PortManager findPortManagerByUsername(String username, List<PortManager> portManagers) {
    for (PortManager portManager : portManagers) {
        if (portManager.getUsername().equals(username)) {
            return portManager;
        }
    }
    // Return null if the PortManager is not found
    return null;
    }
public static void deletePort(List<Port> ports, String portId, String filePath) {
    // Find the port with the given ID
    Port portToDelete = null;
    for (Port port : ports) {
        if (port.getId().equals(portId)) {
            portToDelete = port;
            break;
        }
    }

    // If the port is found, remove it from the list
    if (portToDelete != null) {
        ports.remove(portToDelete);

        // Save the updated list of ports to the data file
        savePorts(ports, filePath);

        System.out.println("Port with ID " + portId + " has been deleted.");
    } else {
        System.out.println("Port with ID " + portId + " not found.");
    }
    }
    public static void updatePort(List<Port> ports, String portId, String newName, double newLatitude,
                              double newLongitude, double newStoringCapacity, boolean newLandingAbility, String managerUsername,
                              String filePath) {
    // Find the port with the given ID
    Port portToUpdate = null;
    for (Port port : ports) {
        if (port.getId().equals(portId)) {
            portToUpdate = port;
            break;
        }
    }

    // If the port is found, update its properties
    if (portToUpdate != null) {
        portToUpdate.setName(newName);
        portToUpdate.setLatitude(newLatitude);
        portToUpdate.setLongitude(newLongitude);
        portToUpdate.setStoringCapacity(newStoringCapacity);
        portToUpdate.setLandingAbility(newLandingAbility);
        // Save the updated list of ports to the data file
        savePorts(ports, filePath);

        System.out.println("Port with ID " + portId + " has been updated.");
    } else {
        System.out.println("Port with ID " + portId + " not found.");
    }
}
}
