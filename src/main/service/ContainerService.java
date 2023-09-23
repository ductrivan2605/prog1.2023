package main.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.entities.Containers;

public class ContainerService {
    private Map<String, Containers> containers;

    public ContainerService() {
        this.containers = new HashMap<>();
    }

    // Method to add a container
    public void addContainer(Containers container) {
        containers.put(container.getContainerId(), container);
    }

    // Method to get a container by container ID
    public Containers getContainerById(String containerId) {
        return containers.get(containerId);
    }

    // Method to check if a container exists
    public boolean containerExists(String containerId) {
        return containers.containsKey(containerId);
    }

    // Method to remove a container
    public void removeContainer(String containerId) {
        containers.remove(containerId);
    }

    // Method to retrieve a list of all containers
    public List<Containers> getAllContainers() {
        return new ArrayList<>(containers.values());
    }

    // Additional methods for container management
    public void updateContainer(Containers container) {
        if (containerExists(container.getContainerId())) {
            containers.put(container.getContainerId(), container);
        } else {
            // Handle the case when the container does not exist
            throw new IllegalArgumentException("Container does not exist.");
        }
    }
}

