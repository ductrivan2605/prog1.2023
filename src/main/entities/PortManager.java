package main.entities;

import java.time.*;
import java.util.*;

import main.entities.Containers.ContainerType;

public class PortManager extends User {
    private String portName; // Additional property for PortManager
    private List<String> managedPortNames; // List of port names that this PortManager can manage vehicles for
    private List<String> allowedViewHistoryPorts; // List of ports where this PortManager can view history

    //constructor
    public PortManager(String username, String password, String portName) {
        super(username, password);
        this.portName = portName;
        super.setUserRole("PortManager");
        this.managedPortNames = new ArrayList<>();
        this.allowedViewHistoryPorts = new ArrayList<>();
    }

    @Override
    public boolean hasPermission(Operation operation) {
        // Implement permission logic based on portName and operation
        return switch (operation) {
            case MANAGE_VEHICLES ->
                // Check if the PortManager can manage vehicles for the specific port
                    canManageVehicles();
            case MANAGE_CONTAINERS ->
                // Check if the PortManager can manage containers for the specific port
                    canManageContainers();
            case MANAGE_PORTS ->
                // PortManagers generally do not have permission to manage ports themselves
                    false;
            case VIEW_HISTORY ->
                // Check if the PortManager can view history for the specific port
                    canViewHistory();
        };
    }

    private boolean canManageVehicles() {
        // Check if the portName matches any of the managedPortNames
        return managedPortNames.contains(portName);
    }

    private boolean canManageContainers() {
        // Define a list of port names that this PortManager can manage containers for
        List<String> allowedPorts = Arrays.asList("PortA", "PortC");

        // Check if the portName matches any of the allowed ports
        return allowedPorts.contains(portName);
    }

    private boolean canViewHistory() {
        return allowedViewHistoryPorts.contains(portName);
    }

    // Method to add a port to the list of managed ports
    public void addManagedPort(String portName) {
        managedPortNames.add(portName);
    }

    // Method to remove a port from the list of managed ports
    public void removeManagedPort(String portName) {
        managedPortNames.remove(portName);
    }
}