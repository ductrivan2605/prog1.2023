package main;

import java.awt.*;
import java.util.*;
import java.util.List;

import main.entities.*;

public class Main {

    private static boolean isLoggedIn = false;
    private static List<User> users = new ArrayList<>();
    private static User loggedInUser = null; // Store the logged-in user object

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create PortManager users
        PortManager portManager1 = new PortManager("portmanager1", "password1", "PortA");
        PortManager portManager2 = new PortManager("portmanager2", "password2", "PortB");
        PortManager portManager3 = new PortManager("portmanager3", "password3", "PortC");
        PortManager portManager4 = new PortManager("portmanager4", "password4", "PortD");
        PortManager portManager5 = new PortManager("portmanager5", "password5", "PortE");

        Admin adminUser = new Admin("admin", "admin");

        users.add(portManager1);
        users.add(portManager2);
        users.add(portManager3);
        users.add(portManager4);
        users.add(portManager5);
        users.add(adminUser);

        while (true) {
            if (!isLoggedIn) {
                // If not logged in, ask the user to log in
                loggedInUser = login(scanner);
                if (loggedInUser != null) {
                    isLoggedIn = true;
                    System.out.println("Logged in successfully as " + loggedInUser.getRole());
                }
            } else {
                // If logged in, display the main menu and handle menu choices
                displayMainMenu();
                int choice = getUserChoice(scanner);

                switch (choice) {
                    case 1: //Daily Fuel Consumption calculation.
                        if (isLoggedIn) {
                            System.out.println("1. Calculate Daily Fuel Consumption");
                            System.out.println("2. Calculate Daily Fuel Consumption for a Ship");
                            System.out.println("3. Calculate Daily Fuel Consumption for a Basic Truck");
                            System.out.println("4. Calculate Daily Fuel Consumption for a Reefer Truck");
                            System.out.println("5. Calculate Daily Fuel Consumption for a Tanker Truck");
                            System.out.print("Enter your choice: ");

                            int fuelChoice = getUserChoice(scanner);

                            switch (fuelChoice) {
                                case 1:
                                    double dailyFuelConsumption = loggedInUser.calculateDailyFuelConsumption();
                                    System.out.println("Total daily fuel consumption: " + dailyFuelConsumption + " gallons");
                                    break;
                                case 2:
                                    // Instantiate a Ship object with the new constructor
                                    Ship myShip = Ship.loadShip("ship.csv");
                                    if (myShip != null) {
                                        double shipFuelConsumption = myShip.calculateDailyFuelConsumptionForShip();
                                        System.out.println("Daily fuel consumption for a Ship: " + shipFuelConsumption + " gallons");
                                    } else  {
                                        System.out.println("Failed to load ship data from CSV.");
                                    }
                                     break;
                                case 3:
                                    // Instantiate a Basic Truck object with the new constructor
                                    Truck basicTruck = (Truck) Truck.loadTrucks("truck.csv");
                                    if (basicTruck != null) {
                                        double basicTruckFuelConsumption = basicTruck.calculateDailyFuelConsumptionForBasicTruck();
                                        System.out.println("Daily fuel consumption for a Basic Truck: " + basicTruckFuelConsumption + " gallons");
                                    } else {
                                        System.out.println("Failed to load basic truck data from CSV");
                                    }
                                    break;
                                case 4:
                                    // Instantiate a Reefer Truck object with the new constructor
                                    Truck reeferTruck = (Truck) Truck.loadTrucks("truck.csv");
                                    if (reeferTruck != null) {
                                        double reeferTruckFuelConsumption = reeferTruck.calculateDailyFuelConsumptionForReeferTruck();
                                        System.out.println("Daily fuel consumption for a Reefer Truck: " + reeferTruckFuelConsumption + " gallons");
                                    } else {
                                        System.out.println("Failed to load reefer truck data from CSV");
                                    }
                                    break;
                                case 5:
                                    // Instantiate a Tanker Truck object with the new constructor
                                    Truck tankerTruck = (Truck) Truck.loadTrucks("truck.csv");
                                    if (tankerTruck != null) {
                                        double tankerTruckFuelConsumption = tankerTruck.calculateDailyFuelConsumptionForTankerTruck();
                                        System.out.println("Daily fuel consumption for a Tanker Truck: " + tankerTruckFuelConsumption + " gallons");
                                    } else {
                                        System.out.println("Failed to load tanker truck data from CSV");
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    break;
                            }
                        } else {
                            System.out.println("You do not have permission to access this feature.");
                        }
                        break;
                    case 2:
                        // Weight of each type of containers calculation - logged in required
                        if (isLoggedIn) {
                            List<Containers> containersList = Containers.loadContainerFromFile("container.csv");
                            Map<String, Double> typeToTotalWeightMap = new HashMap<>();

                            // Initialize typeToTotalWeightMap with 0.0 for each container type
                            for (Containers.ContainerType type : Containers.ContainerType.values()) {
                                typeToTotalWeightMap.put(type.toString(), 0.0);
                            }

                            // Calculate the total weight for each container type
                            for (Containers container : containersList) {
                                double currentWeight = typeToTotalWeightMap.get(container.getType().toString());
                                typeToTotalWeightMap.put(container.getType().toString(), currentWeight + container.getWeight());
                            }

                            // Print the total weight for each container type
                            for (Map.Entry<String, Double> entry : typeToTotalWeightMap.entrySet()) {
                                System.out.println("Total Weight of " + entry.getKey() + " containers: " + entry.getValue());
                            }
                        } else {
                            System.out.println("You do not have permission to access this feature.");
                        }
                        break;
                    case 3:
                        // Entities Operation - logged in required
                        // Implement this functionality
                        break;
                    case 4:
                        // Load/Unload container from a vehicle - logged in required
                        if (isLoggedIn) {
                            System.out.println("1. Load container to a Vehicle");
                            System.out.println("2. Unload container from a Vehicle");
                            System.out.print("Enter your choice: ");

                            int loadUnloadChoice = getUserChoice(scanner);

                            switch (loadUnloadChoice) {
                                case 1:
                                    // Load container to a Vehicle
                                    System.out.println("Choose a Vehicle to Load:");
                                    System.out.println("1. Ship");
                                    System.out.println("2. Truck");
                                    System.out.print("Enter your choice: ");
                                    int vehicleChoice = getUserChoice(scanner);

                                    switch (vehicleChoice) {
                                        case 1:
                                            // Load container to a Ship
                                            if (loggedInUser instanceof PortManager) {
                                                Ship ship = Ship.loadShip("ship.csv");
                                                if (ship != null) {
                                                    // Load container to the ship
                                                    Containers containerToLoad = selectContainerToLoad(scanner);
                                                    ship.loadContainer(containerToLoad);
                                                    Ship.saveShip(ship, "ship.csv"); // Save the updated ship data
                                                    System.out.println("Container loaded to the Ship.");
                                                } else {
                                                    System.out.println("Failed to load ship data from CSV.");
                                                }
                                            } else {
                                                System.out.println("You do not have permission to load containers to the Ship.");
                                            }
                                            break;
                                        case 2:
                                            // Load container to a Truck
                                            if (loggedInUser instanceof PortManager) {
                                                Truck truck = (Truck) Truck.loadTrucks("truck.csv");
                                                if (truck != null) {
                                                    // Load container to the truck
                                                    Containers containerToLoad = selectContainerToLoad(scanner);
                                                    truck.loadContainer(containerToLoad); // Corrected method call
                                                    Truck.saveTrucks((List<Truck>) truck, "truck.csv"); // Save the updated truck data
                                                    System.out.println("Container loaded to the Truck.");
                                                } else {
                                                    System.out.println("Failed to load truck data from CSV.");
                                                }
                                            } else {
                                                System.out.println("You do not have permission to load containers to the Truck.");
                                            }
                                            break;
                                        default:
                                            System.out.println("Invalid choice. Please try again.");
                                            break;
                                    }
                                    break;
                                case 2:
                                    // Unload container from a Vehicle
                                    System.out.println("Choose a Vehicle to Unload:");
                                    System.out.println("1. Ship");
                                    System.out.println("2. Truck");
                                    System.out.print("Enter your choice: ");
                                    int vehicleChoiceUnload = getUserChoice(scanner);

                                    switch (vehicleChoiceUnload) {
                                        case 1:
                                            // Unload container from a Ship
                                            if (loggedInUser instanceof PortManager) {
                                                Ship ship = Ship.loadShip("ship.csv");
                                                if (ship != null) {
                                                    // Unload container from the ship
                                                    Containers containerToUnload = selectContainerToUnload(scanner, ship.getLoadedContainers());
                                                    ship.unloadContainer(containerToUnload);
                                                    Ship.saveShip(ship, "ship.csv"); // Save the updated ship data
                                                    System.out.println("Container unloaded from the Ship.");
                                                } else {
                                                    System.out.println("Failed to load ship data from CSV.");
                                                }
                                            } else {
                                                System.out.println("You do not have permission to unload containers from the Ship.");
                                            }
                                            break;
                                        case 2:
                                            // Unload container from a Truck
                                            if (loggedInUser instanceof PortManager) {
                                                Truck truck = (Truck) Truck.loadTrucks("truck.csv");
                                                if (truck != null) {
                                                    // Unload container from the truck
                                                    Containers containerToUnload = selectContainerToUnload(scanner, truck.getLoadedContainers());
                                                    truck.unloadContainer(containerToUnload); // Corrected method call
                                                    Truck.saveTrucks((List<Truck>) truck, "truck.csv"); // Save the updated truck data
                                                    System.out.println("Container unloaded from the Truck.");
                                                } else {
                                                    System.out.println("Failed to load truck data from CSV.");
                                                }
                                            } else {
                                                System.out.println("You do not have permission to unload containers from the Truck.");
                                            }
                                            break;
                                        default:
                                            System.out.println("Invalid choice. Please try again.");
                                            break;
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    break;
                            }
                        } else {
                            System.out.println("You do not have permission to access this feature.");
                        }
                        break;
                    case 5:
                        // Move a vehicle to a port - logged in required
                        // Implement this functionality
                        break;
                    case 6:
                        // Refuel a vehicle - logged in required
                        // Implement this functionality
                        break;
                    case 7:
                        // Statistics operations - can only be accessed if logged in
                        if ("Admin".equals(loggedInUser.getRole())) {
                            // Implement admin statistics functionality here
                            System.out.println("Accessing admin statistics operations...");
                        } else if ("PortManager".equals(loggedInUser.getRole())) {
                            // Implement port manager statistics functionality here
                            System.out.println("Accessing port manager statistics operations...");
                        }
                        break;
                    case 8:
                        // Exit the program
                        System.out.println("Exiting the Container Port Management System.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
    private static void displayMainMenu() {
        System.out.println("Container Port Management System - Main Menu");
        System.out.println("1. Calculate Daily Fuel Consumption");
        System.out.println("2. Calculate weight of each type of all containers");
        System.out.println("3. Entity Operation");
        System.out.println("4. Load/Unload Container to Vehicles");
        System.out.println("5. Move Vehicle to another Port");
        System.out.println("6. Re");
        System.out.println("7. Refuel Vehicle");
        System.out.println("8. Statistics Operations");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }
    //System authentication
    private static User login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        System.out.println("Login failed. Please check your username and password.");
        return null; // Return null to indicate login failure
    }
    // Method to load a container onto a vehicle (ship or truck)
    private static Containers selectContainerToLoad(Scanner scanner) {
        List<Containers> containersList = Containers.loadContainerFromFile("container.csv");

        System.out.println("List of Available Containers:");
        for (int i = 0; i < containersList.size(); i++) {
            System.out.println((i + 1) + ". " + containersList.get(i).getContainerId());
        }

        System.out.print("Enter the number of the container to load: ");
        int selection = getUserChoice(scanner);

        if (selection >= 1 && selection <= containersList.size()) {
            return containersList.get(selection - 1); // Return the selected container
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectContainerToLoad(scanner); // Recursively call the method for a valid selection
        }
    }

    private static Containers selectContainerToUnload(Scanner scanner, List<Containers> loadedContainers) {
        if (loadedContainers.isEmpty()) {
            System.out.println("No containers loaded in the vehicle.");
            return null;
        }

        System.out.println("List of Loaded Containers:");
        for (int i = 0; i < loadedContainers.size(); i++) {
            System.out.println((i + 1) + ". " + loadedContainers.get(i).getContainerId());
        }

        System.out.print("Enter the number of the container to unload: ");
        int selection = getUserChoice(scanner);

        if (selection >= 1 && selection <= loadedContainers.size()) {
            return loadedContainers.get(selection - 1); // Return the selected container
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectContainerToUnload(scanner, loadedContainers); // Recursively call the method for a valid selection
        }
    }
}
