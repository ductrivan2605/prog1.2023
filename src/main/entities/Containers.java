package main.entities;

import java.io.*;
import java.util.*;

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

    public static double calculateTotalWeight(List<Containers> containersList) {
        double totalWeight = 0.0;

        for (Containers container : containersList) {
            totalWeight += container.getWeight();
        }

        return totalWeight;
    }

    // Load data from a file and return a list of Containers objects
    public static List<Containers> loadContainersFromFile(String filePath) {
        List<Containers> containersList = new ArrayList<>();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    Containers container = (Containers) objectInputStream.readObject();
                    containersList.add(container);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // IOException will be thrown when we reach the end of the file
            // We can ignore it in this case
        }

        return containersList;
    }

    public static void saveContainersToFile(String filePath, List<Containers> containersList) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Containers container : containersList) {
                objectOutputStream.writeObject(container);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteContainer(String filePath, String containerIdToDelete) {
        List<Containers> updatedContainersList = new ArrayList<>();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    Containers container = (Containers) objectInputStream.readObject();
                    if (!container.getContainerId().equals(containerIdToDelete)) {
                        updatedContainersList.add(container);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated list to a new .dat file
        saveContainersToFile(filePath, updatedContainersList);
    }


    public static void updateContainer(String filePath, String containerIdToUpdate, double newWeight, ContainerType newType) {
        List<Containers> updatedContainersList = new ArrayList<>();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    Containers container = (Containers) objectInputStream.readObject();
                    if (container.getContainerId().equals(containerIdToUpdate)) {
                        container.setWeight(newWeight);
                        container.setType(newType);
                    }
                    updatedContainersList.add(container);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated list to a new .dat file
        saveContainersToFile(filePath, updatedContainersList);
    }


    // Getters and setters for properties
    public String getContainerId() {
        return containerId;
    }

    public double getWeight() {
        return weight;
    }

    // Setter for weight
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ContainerType getType() {
        return type;
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

    
    // Load data from a file
    // List<Containers> loadedContainers = loadDataFromFile("containers_data.txt");

    //  Modify the loaded data or create new Containers objects as needed

    // Save the modified or new data back to a file
    // saveDataToFile("container.dat", loadedContainers);
}
