package main.entities;

import java.util.ArrayList;
import java.util.List;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Ship implements Vehicles, Serializable {
    private String vehicleId;
    private String name;
    private double currentFuel;
    private double carryingCapacity;
    private double fuelCapacity;
    private Port currentPort;
    private static int totalContainers;
    private VehicleType vehicleType;
    private static List<Containers> loadedContainers;

    public Ship(String vehicleId, String name, double currentFuel, double carryingCapacity, double fuelCapacity, Port currentPort) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.currentFuel = currentFuel;
        this.carryingCapacity = carryingCapacity;
        this.fuelCapacity = fuelCapacity;
        this.currentPort = currentPort;
        this.totalContainers = 0;
        this.vehicleType = VehicleType.SHIP;
        this.loadedContainers = new ArrayList<>();
    }

    @Override
    public double calculateRequiredFuel(double distance) {
        // Calculate required fuel for a ship based on distance
        // For simplicity, we assume a constant fuel consumption rate per kilometer
        double fuelConsumptionRate = 2.5; // Adjust this value as needed
        return fuelConsumptionRate * distance;
    }

    // Getters and setters for Ship properties
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
    @Override
    public void loadContainer(Containers container) {
        // Add the container to the list of loaded containers
        loadedContainers.add(container);
        // Update the total container count
        totalContainers++;
    }
    @Override
    public void unloadContainer(Containers container) {
        // Remove the container from the list of loaded containers
        loadedContainers.remove(container);
        // Update the total container count
        totalContainers--;
    }
    @Override
    public void moveToPort(Port targetPort) {
        if (!targetPort.hasLandingAbilityFor(getVehicleType())) {
            // The target port doesn't have landing ability for this type of vehicle
            System.out.println("Ship " + getVehicleId() + " cannot move to " + targetPort.getName() + ".");
            return;
        }

        if (canMoveToPort(targetPort)) {
            // Implement the logic to move the ship to the target port
            // You can update the current port and other relevant information
            setCurrentPort(targetPort);
            System.out.println("Ship " + getVehicleId() + " has moved to " + targetPort.getName() + ".");
        } else {
            System.out.println("Ship " + getVehicleId() + " cannot move to " + targetPort.getName() + " due to load capacity.");
        }
    }

    private boolean canMoveToPort(Port targetPort) {
        // Calculate the total weight of loaded containers
        double loadedWeight = calculateLoadedContainerWeight();

        // Check if the target port has sufficient storing capacity
        if (loadedWeight > targetPort.getStoringCapacity()) {
            return false;
        }

        // You can add additional conditions specific to ships if needed

        return true;
    }

    private double calculateLoadedContainerWeight() {
        // Calculate and return the total weight of loaded containers
        double loadedWeight = 0.0;
        for (Containers container : loadedContainers) {
            loadedWeight += container.getWeight();
        }
        return loadedWeight;
    }
    @Override
    public void refuel(double amount) {
        if (amount > 0) {
            double newFuelLevel = getCurrentFuel() + amount;
            if (newFuelLevel <= getFuelCapacity()) {
                setCurrentFuel(newFuelLevel);
                System.out.println("Ship refueled successfully. Current fuel level: " + newFuelLevel);
            } else {
                System.out.println("Refueling failed. Fuel tank capacity exceeded.");
            }
        } else {
            System.out.println("Invalid refueling amount.");
        }
    }
    public double calculateDailyFuelConsumption() {
        double dailyFuelConsumption = 0.0;

        // Calculate daily fuel consumption for ships
        dailyFuelConsumption = calculateDailyFuelConsumptionForShip();

        return dailyFuelConsumption;
    }

    public double calculateDailyFuelConsumptionForShip() {
        // Calculate daily fuel consumption for ships
        return 100.0;
    } 
    //Ship data loader
    public static Ship loadShip(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            Ship ship = (Ship) inputStream.readObject();
            System.out.println("Ship data loaded from " + filename);
            return ship;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading Ship data: " + e.getMessage());
            return null;
        }
    }
    //Ship data saver
    public static void saveShip(Ship ship, String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(ship);
            System.out.println("Ship data saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving Ship data: " + e.getMessage());
        }
    }
    //Delete Ship in data file
    public static void deleteShip(String filename, String vehicleIdToDelete) {
        List<Ship> ships = loadAllShips(filename);
    
        // Find the ship with the specified vehicleId and remove it
        ships.removeIf(ship -> ship.getVehicleId().equals(vehicleIdToDelete));
    
        // Save the updated list back to the data file
        saveAllShips(ships, filename);
    }
    //Update Ship data file
    public static void updateShip(String filename, Ship updatedShip) {
        List<Ship> ships = loadAllShips(filename);
    
        // Find the ship with the same vehicleId as the updatedShip and replace it
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).getVehicleId().equals(updatedShip.getVehicleId())) {
                ships.set(i, updatedShip);
                break;
            }
        }
    
        // Save the updated list back to the data file
        saveAllShips(ships, filename);
    }
    public static List<Ship> loadAllShips(String filename) {
    List<Ship> ships = new ArrayList<>();

    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
        while (true) {
            try {
                Ship ship = (Ship) inputStream.readObject();
                ships.add(ship);
            } catch (EOFException e) {
                break; // End of file reached
            }
        }
    } catch (IOException | ClassNotFoundException e) {
        System.err.println("Error loading Ship data: " + e.getMessage());
    }

    return ships;
}
public static void saveAllShips(List<Ship> ships, String filename) {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
        for (Ship ship : ships) {
            outputStream.writeObject(ship);
        }
        System.out.println("All ships data saved to " + filename);
    } catch (IOException e) {
        System.err.println("Error saving Ship data: " + e.getMessage());
    }
}
// Saving a Ship object
// Ship myShip = new Ship("SHIP001", "My Ship", 1000.0, 2000.0, 5000.0, myPort);
// saveShipToFile(myShip, "ship.csv");

// Loading a Ship object
// Ship loadedShip = loadShipFromFile("ship.csv");
// if (loadedShip != null) {
//     System.out.println("Loaded Ship ID: " + loadedShip.getVehicleId());
//     System.out.println("Loaded Ship Name: " + loadedShip.getName());
// Other necessary properties printing here
// }
// Delete a ship by vehicleId
// String vehicleIdToDelete = "12345";
// deleteShip("ship.csv", vehicleIdToDelete);

// Update a ship record
// Ship updatedShip = new Ship("12345", "Updated Ship Name", 200.0, 150.0, 300.0, new Port("New Port"));
// updateShip("ship.csv", updatedShip);

}
