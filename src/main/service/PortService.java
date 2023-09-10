package main.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.entities.Port;

public class PortService {
    private Map<String, Port> ports;

    public PortService() {
        this.ports = new HashMap<>();
    }

    // Method to add a port
    public void addPort(Port port) {
        ports.put(port.getId(), port);
    }

    // Method to get a port by port ID
    public Port getPortById(String portId) {
        return ports.get(portId);
    }

    // Method to check if a port exists
    public boolean portExists(String portId) {
        return ports.containsKey(portId);
    }

    // Method to remove a port
    public void removePort(String portId) {
        ports.remove(portId);
    }

    // Method to retrieve a list of all ports
    public List<Port> getAllPorts() {
        return new ArrayList<>(ports.values());
    }

    // Additional methods for port management
    public void updatePort(Port port) {
        if (portExists(port.getId())) {
            ports.put(port.getId(), port);
        } else {
            // Handle the case when the port does not exist
            throw new IllegalArgumentException("Port does not exist.");
        }
    }
}
