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

public class Ship extends Vehicles {
    //constructor
    public Ship(String vehicleId, String name, double currentFuel, double carryingCapacity, double fuelCapacity, Port currentPort) {
        super(vehicleId, name, currentFuel, carryingCapacity,fuelCapacity, currentPort);
        super.setVehicleType(VehicleType.SHIP);
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

    @Override
    public double calculateRequiredFuel(double distance) {
        // Calculate required fuel for a ship based on distance
        // For simplicity, we assume a constant fuel consumption rate per kilometer
        double fuelConsumptionRate = 2.5; // Adjust this value as needed
        return fuelConsumptionRate * distance;
    }

    @Override
    public void moveToPort(Port targetPort) {
        if (!targetPort.hasLandingAbilityFor(getVehicleType())) {
            // The target port doesn't have landing ability for this type of vehicle
            System.out.println("Ship " + getVehicleId() + " cannot move to " + targetPort.getName() + ".");
            return;
        }

        if (canMoveToPort(targetPort)) {
            // Calculate the distance to the target port using Haversine
            double distance = distanceToTargetPort(targetPort);

            // Check if the ship has enough fuel for the journey
            double requiredFuel = calculateRequiredFuel(distance);
            if (getCurrentFuel() >= requiredFuel) {
                // Update the ship's current port to the target port
                setCurrentPort(targetPort);
                System.out.println("Ship " + getVehicleId() + " has moved to " + targetPort.getName() + ".");
                System.out.println("Distance traveled: " + distance + " kilometers");
                System.out.println("Fuel consumed: " + requiredFuel + " units");
                setCurrentFuel(getCurrentFuel() - requiredFuel); // Deduct fuel
            } else {
                System.out.println("Ship " + getVehicleId() + " cannot move to " + targetPort.getName() + " due to insufficient fuel.");
            }
        } else {
            System.out.println("Ship " + getVehicleId() + " cannot move to " + targetPort.getName() + " due to load capacity.");
        }
    }

    private boolean canMoveToPort(Port targetPort) {
        // Calculate the total weight of loaded containers
        double loadedWeight = calculateLoadedContainerWeight();

        // Check if the target port has sufficient storing capacity
        return !(loadedWeight > targetPort.getStoringCapacity());
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

    @Override
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

    private double distanceToTargetPort(Port targetPort) {
        double earthRadius = 6371; // Earth's radius in kilometers (you can use 3959 for miles)

        double lat1 = Math.toRadians(getCurrentPort().getLatitude());
        double lon1 = Math.toRadians(getCurrentPort().getLongitude());
        double lat2 = Math.toRadians(targetPort.getLatitude());
        double lon2 = Math.toRadians(targetPort.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }
// Saving a Ship object
// Ship myShip = new Ship("SHIP001", "My Ship", 1000.0, 2000.0, 5000.0, myPort);
// saveShipToFile(myShip, "ship.dat");

// Loading a Ship object
// Ship loadedShip = loadShipFromFile("ship.dat");
// if (loadedShip != null) {
//     System.out.println("Loaded Ship ID: " + loadedShip.getVehicleId());
//     System.out.println("Loaded Ship Name: " + loadedShip.getName());
// Other necessary properties printing here
// }
// Delete a ship by vehicleId
// String vehicleIdToDelete = "12345";
// deleteShip("ship.dat", vehicleIdToDelete);

// Update a ship record
// Ship updatedShip = new Ship("12345", "Updated Ship Name", 200.0, 150.0, 300.0, new Port("New Port"));
// updateShip("ship.dat", updatedShip);

}
