package main.entities;

public class Containers {
    private String containerId;
    private double weight;
    private ContainerType type;

    public Containers(String containerId, double weight, ContainerType type) {
        this.containerId = containerId;
        this.weight = weight;
        this.type = type;
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
}
