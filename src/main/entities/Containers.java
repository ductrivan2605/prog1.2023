package main.entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Containers {
    private String containerId;
    private double weight;
    private ContainerType type;

    private String loadedOnVehicleId;  // Add a property to track the vehicle ID where the container is loaded

    public Containers(String containerId, double weight, ContainerType type) {
        this.containerId = containerId;
        this.weight = weight;
        this.type = type;
        this.loadedOnVehicleId = null; // Initially, the container is not loaded on any vehicle
    }

    // Getters and setters for properties
    public String getContainerId() {
        return containerId;
    }

    public double getWeight() {
        return weight;
    }

    public ContainerType getType() {
        return type;
    }
    // Setter for weight
    public void setWeight(double weight) {
        this.weight = weight;
    }

    // Setter for type
    public void setType(ContainerType type) {
        this.type = type;
    }
    public String getLoadedOnVehicleId() {
        return loadedOnVehicleId;
    }
    public void setLoadedOnVehicleId(String loadedOnVehicleId) {
        this.loadedOnVehicleId = loadedOnVehicleId;
    }
    @Override
    public String toString() {
        return "Container ID: " + containerId +
                "\nWeight: " + weight +
                "\nType: " + type;
    }
    public enum ContainerType {
        DRY_STORAGE,
        OPEN_TOP,
        OPEN_SIDE,
        REFRIGERATED,
        LIQUID
    }
    public static double calculateTotalWeight(List<Containers> containersList) {
        double totalWeight = 0.0;

        for (Containers container : containersList) {
            totalWeight += container.getWeight();
        }

        return totalWeight;
    }
    // Load data from a file and return a list of Containers objects
    public static List<Containers> loadContainerFromFile(String filePath) {
        List<Containers> containersList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String containerId = parts[0];
                    double weight = Double.parseDouble(parts[1]);
                    Containers.ContainerType type = Containers.ContainerType.valueOf(parts[2]);

                    Containers container = new Containers(containerId, weight, type);
                    containersList.add(container);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return containersList;
    }

    // Save a list of Containers objects to a file
    public static void saveContainerToFile(String filePath, List<Containers> containersList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Containers container : containersList) {
                String line = container.getContainerId() + "," + container.getWeight() + "," + container.getType().toString();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Delete a container from data fikle
    public static void deleteContainer(List<Containers> containersList, String containerIdToDelete) {
        containersList.removeIf(container -> container.getContainerId().equals(containerIdToDelete));
    }
    //Update a container in the data file  
    public static void updateContainer(List<Containers> containersList, String containerIdToUpdate, double newWeight, ContainerType newType) {
        for (Containers container : containersList) {
            if (container.getContainerId().equals(containerIdToUpdate)) {
                container.setWeight(newWeight);
                container.setType(newType);
                break; // Exit the loop after updating the container
            }
        }
    }

    
    // Load data from a file
    // List<Containers> loadedContainers = loadDataFromFile("containers_data.txt");

    //  Modify the loaded data or create new Containers objects as needed

    // Save the modified or new data back to a file
    // saveDataToFile("containers_data.csv", loadedContainers);
}
