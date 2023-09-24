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
                                    Ship myShip = Ship.loadShip("ship.dat");
                                    if (myShip != null) {
                                        double shipFuelConsumption = myShip.calculateDailyFuelConsumptionForShip();
                                        System.out.println("Daily fuel consumption for a Ship: " + shipFuelConsumption + " gallons");
                                    } else  {
                                        System.out.println("Failed to load ship data from File.");
                                    }
                                     break;
                                case 3:
                                    // Instantiate a Basic Truck object with the new constructor
                                    Truck basicTruck = (Truck) Truck.loadTrucks("truck.dat");
                                    if (basicTruck != null) {
                                        double basicTruckFuelConsumption = basicTruck.calculateDailyFuelConsumptionForBasicTruck();
                                        System.out.println("Daily fuel consumption for a Basic Truck: " + basicTruckFuelConsumption + " gallons");
                                    } else {
                                        System.out.println("Failed to load basic truck data from File");
                                    }
                                    break;
                                case 4:
                                    // Instantiate a Reefer Truck object with the new constructor
                                    Truck reeferTruck = (Truck) Truck.loadTrucks("truck.dat");
                                    if (reeferTruck != null) {
                                        double reeferTruckFuelConsumption = reeferTruck.calculateDailyFuelConsumptionForReeferTruck();
                                        System.out.println("Daily fuel consumption for a Reefer Truck: " + reeferTruckFuelConsumption + " gallons");
                                    } else {
                                        System.out.println("Failed to load reefer truck data from File");
                                    }
                                    break;
                                case 5:
                                    // Instantiate a Tanker Truck object with the new constructor
                                    Truck tankerTruck = (Truck) Truck.loadTrucks("truck.dat");
                                    if (tankerTruck != null) {
                                        double tankerTruckFuelConsumption = tankerTruck.calculateDailyFuelConsumptionForTankerTruck();
                                        System.out.println("Daily fuel consumption for a Tanker Truck: " + tankerTruckFuelConsumption + " gallons");
                                    } else {
                                        System.out.println("Failed to load tanker truck data from File");
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
                        // CRUD Operation - logged in required
                        if (loggedInUser instanceof PortManager) {
                            System.out.println("Port Manager Menu");
                            System.out.println("1. Port");
                            System.out.println("2. Container");
                            System.out.println("3. Trip");
                            System.out.print("Enter your choice: ");

                            int pmCRUDChoice = getUserChoice();
                            switch (pmCRUDChoice) {
                                case 1:
                                    // Handle Port operations (CRUD)
                                    System.out.println("Update Port");
                                    break;
                                case 2:
                                    // Handle Container operations (CRUD)
                                    System.out.println("Update Container");
                                    updateContainer();
                                    break;
                                case 3:
                                    // Handle Trip operations (CRUD)
                                    System.out.println("Update Trip");
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        }
                        if (loggedInUser instanceof Admin) {
                            System.out.println("Admin Menu");
                            System.out.println("1. Port");
                            System.out.println("2. Vehicle");
                            System.out.println("3. Container");
                            System.out.println("4. Trip");
                            System.out.println("5. Port Manager");
                            System.out.print("Enter your choice: ");

                            int adCRUDChoice = getUserChoice();

                            switch (adCRUDChoice) {
                                case 1:
                                    // Handle Port operations (CRUD)
                                    System.out.println("1. Update Port");
                                    System.out.println("2. Add Port");
                                    System.out.println("3. Remove Port");
                                    System.out.print("Enter your choice: ");
                                    int portCRUDChoice = getUserChoice();
                                    break;
                                case 2:
                                    // Handle Vehicle operations (CRUD)
                                    System.out.println("1. Update Ship");
                                    System.out.println("2. Update Truck");
                                    System.out.println("3. Add Ship");
                                    System.out.println("4. Add Truck");
                                    System.out.println("5. Remove Ship");
                                    System.out.println("6. Remove Truck");
                                    System.out.print("Enter your choice: ");
                                    int vehicleCRUDChoice = getUserChoice();
                                    break;
                                case 3:
                                    // Handle Container operations (CRUD)
                                    System.out.println("1. Update Container");
                                    System.out.println("2. Add Container");
                                    System.out.println("3. Remove Container");
                                    System.out.print("Enter your choice: ");
                                    int containerCRUDChoice = getUserChoice();
                                    switch (containerCRUDChoice) {
                                        case 1:
                                            updateContainer();
                                        break;
                                        case 2:
                                            addContainerToFile();
                                        break;
                                        case 3:
                                            deleteContainer();
                                        break;
                                    }
                                    break;
                                case 4:
                                    // Handle Trip operations
                                    System.out.println("1. Update Trip");
                                    System.out.println("2. Add Trip");
                                    System.out.println("3. Remove Trip");
                                    System.out.print("Enter your choice: ");
                                    int tripCRUDChoice = getUserChoice();
                                    break;
                                case 5:
                                    // Handle Port Manager operations (CRUD)
                                    System.out.println("1. Add Port Manager");
                                    System.out.println("2. Remove Port Manager");
                                    System.out.print("Enter your choice: ");
                                    int portMCRUDChoice = getUserChoice();
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        }
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
                                                Ship ship = Ship.loadShip("ship.dat");
                                                if (ship != null) {
                                                    // Load container to the ship
                                                    Containers containerToLoad = selectContainerToLoad(scanner);
                                                    ship.loadContainer(containerToLoad);
                                                    Ship.saveShip(ship, "ship.dat"); // Save the updated ship data
                                                    System.out.println("Container loaded to the Ship.");
                                                } else {
                                                    System.out.println("Failed to load ship data from File.");
                                                }
                                            } else {
                                                System.out.println("You do not have permission to load containers to the Ship.");
                                            }
                                            break;
                                        case 2:
                                            // Load container to a Truck
                                            if (loggedInUser instanceof PortManager) {
                                                Truck truck = (Truck) Truck.loadTrucks("truck.dat");
                                                if (truck != null) {
                                                    // Load container to the truck
                                                    Containers containerToLoad = selectContainerToLoad(scanner);
                                                    truck.loadContainer(containerToLoad); // Corrected method call
                                                    Truck.saveTrucks((List<Truck>) truck, "truck.dat"); // Save the updated truck data
                                                    System.out.println("Container loaded to the Truck.");
                                                } else {
                                                    System.out.println("Failed to load truck data from File.");
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
                                                Ship ship = Ship.loadShip("ship.dat");
                                                if (ship != null) {
                                                    // Unload container from the ship
                                                    Containers containerToUnload = selectContainerToUnload(scanner, ship.getLoadedContainers());
                                                    ship.unloadContainer(containerToUnload);
                                                    Ship.saveShip(ship, "ship.dat"); // Save the updated ship data
                                                    System.out.println("Container unloaded from the Ship.");
                                                } else {
                                                    System.out.println("Failed to load ship data from File.");
                                                }
                                            } else {
                                                System.out.println("You do not have permission to unload containers from the Ship.");
                                            }
                                            break;
                                        case 2:
                                            // Unload container from a Truck
                                            if (loggedInUser instanceof PortManager) {
                                                Truck truck = (Truck) Truck.loadTrucks("truck.dat");
                                                if (truck != null) {
                                                    // Unload container from the truck
                                                    Containers containerToUnload = selectContainerToUnload(scanner, truck.getLoadedContainers());
                                                    truck.unloadContainer(containerToUnload); // Corrected method call
                                                    Truck.saveTrucks((List<Truck>) truck, "truck.dat"); // Save the updated truck data
                                                    System.out.println("Container unloaded from the Truck.");
                                                } else {
                                                    System.out.println("Failed to load truck data from File.");
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
                        if (isLoggedIn) {
                            System.out.println("Choose a Vehicle to Move:");
                            System.out.println("1. Ship");
                            System.out.println("2. Truck");
                            System.out.print("Enter your choice: ");
                            int vehicleChoice = getUserChoice();

                            switch (vehicleChoice) {
                                case 1:
                                    // Move a Ship to a Port
                                    if (loggedInUser instanceof PortManager) {
                                        Ship ship = Ship.loadShip("ship.dat");
                                        if (ship != null) {
                                            List<Port> availablePorts = Port.loadPorts("port.dat", portManager); // Pass the users list
                                            Port targetPort = selectTargetPort(portManager);
                                            ship.moveToPort(targetPort);
                                            Ship.saveShip(ship, "ship.dat"); // Save the updated ship data
                                        } else {
                                            System.out.println("Failed to load ship data from File.");
                                        }
                                    } else {
                                        System.out.println("You do not have permission to move ships to ports.");
                                    }
                                    break;
                                case 2:
                                    // Move a Truck to a Port
                                    if (loggedInUser instanceof PortManager) {
                                        Truck truck = (Truck) Truck.loadTrucks("truck.dat");
                                        if (truck != null) {
                                            List<Port> availablePorts = Port.loadPorts("port.dat", portManager); // Pass the users list
                                            Port targetPort = selectTargetPort(portManager);
                                            truck.moveToPort(targetPort);
                                            Truck.saveTrucks((List<Truck>) truck, "truck.dat"); // Save the updated truck data
                                        } else {
                                            System.out.println("Failed to load truck data from File.");
                                        }
                                    } else {
                                        System.out.println("You do not have permission to move trucks to ports.");
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
                    case 6:
                        // Refuel a vehicle - logged in required
                        // Implement this functionality
                        break;
                    case 7:
                        // List all ships in the port
                        if (isLoggedIn) {
                            Ship ship = Ship.loadShip("ship.dat"); // Assuming the ship data is stored in "ship.ser" using the provided method

                            if (ship != null) {
                                System.out.println("List of all ships in the port:");
                                System.out.println("Ship ID: " + ship.getVehicleId());
                                System.out.println("Ship Name: " + ship.getName());
                                // Add more ship information as needed
                            } else {
                                System.out.println("No ships found in the port.");
                            }
                        } else {
                            System.out.println("You do not have permission to list ships in the port.");
                        }
                        break;
                    case 8:
                        // List all the trips on a user-inputted date
                        System.out.print("Enter the date (YYYY-MM-DD) to list trips: ");
                        String dateToSearch = scanner.next();

                        if (isLoggedIn) {
                            List<Trip> allTrips = Trip.loadTripsFromFile("trip.dat"); // Load trips from a serialized file

                            if (allTrips != null) {
                                System.out.println("Trips on " + dateToSearch + ":");

                                boolean found = false; // Flag to check if any trips were found on the entered date

                                for (Trip trip : allTrips) {
                                    if (trip.getDepartureDate().equals(dateToSearch)) {
                                        found = true;
                                        System.out.println("Trip ID: " + trip.getTripId());
                                        System.out.println("Date: " + trip.getDepartureDate());
                                        System.out.println("Origin Port: " + trip.getDeparturePort());
                                        System.out.println("Destination Port: " + trip.getArrivalPort());
                                        System.out.println("Cargo Type: " + trip.getVehicle());
                                        System.out.println("------------");
                                    }
                                }

                                if (!found) {
                                    System.out.println("No trips found on " + dateToSearch);
                                }
                            } else {
                                System.out.println("Failed to load trip data.");
                            }
                        } else {
                            System.out.println("You do not have permission to access this feature.");
                        }
                        break;
                    case 9:
                        // List all trips between 2 inputted days
                        if (isLoggedIn) {
                            Scanner tripBetweenDaysScanner = new Scanner(System.in);

                            try (tripBetweenDaysScanner) {
                                System.out.print("Enter the start date (yyyy-MM-dd): ");
                                String startDateStr = tripBetweenDaysScanner.nextLine();
                                LocalDate startDate = LocalDate.parse(startDateStr);

                                System.out.print("Enter the end date (yyyy-MM-dd): ");
                                String endDateStr = tripBetweenDaysScanner.nextLine();
                                LocalDate endDate = LocalDate.parse(endDateStr);

                                List<Trip> tripsBetweenDays = loggedInUser.listTripsBetweenDays(startDate, endDate);

                                if (!tripsBetweenDays.isEmpty()) {
                                    System.out.println("Trips between the specified dates:");
                                    for (Trip trip : tripsBetweenDays) {
                                        System.out.println(trip.toString()); // Assuming you have implemented a toString method in the Trip class.
                                    }
                                } else {
                                    System.out.println("No trips found between the specified dates.");
                                }
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
                            }
                        } else {
                            System.out.println("You do not have permission to access this feature.");
                        }
                        break;
                    case 10:
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
        System.out.println("3. CRUD Operation");
        System.out.println("4. Load/Unload Container to Vehicles");
        System.out.println("5. Move Vehicle to another Port");
        System.out.println("6. Refuel Vehicle");
        System.out.println("7. Listing all ships in a port");
        System.out.println("8. List all the trips in a day");
        System.out.println("9. List all trips between 2 inputted days");
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
    private static Containers selectContainerToLoad() {
        Scanner scanner = new Scanner(System.in);
        List<Containers> containersList = Containers.loadContainersFromFile("container.dat");

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

    // Port targeting selection
    private static Port selectTargetPort(List<PortManager> portManager) {
        Scanner scanner = new Scanner(System.in);
        List<Port> availablePorts = Port.loadPorts("port.dat", portManager);

        if (availablePorts.isEmpty()) {
            System.out.println("No available ports to select.");
            return null;
        }

        System.out.println("List of Available Ports:");
        for (int i = 0; i < availablePorts.size(); i++) {
            Port port = availablePorts.get(i);
            System.out.println((i + 1) + ". " + port.getName() + " (Manager: " + port.getPortManager().getUsername() + ")");
        }

        System.out.print("Enter the number of the target port: ");
        int selection = getUserChoice();

        if (selection >= 1 && selection <= availablePorts.size()) {
            return availablePorts.get(selection - 1); // Return the selected port
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectTargetPort(portManager); // Recursively call the method for a valid selection
        }
    }

    private static double promptForFuelAmount() {
        double fuelToAdd = 0.0;
        boolean validInput = false;
        Scanner scanner = new Scanner(System.in);

        while (!validInput) {
            System.out.print("Enter the amount of fuel to add (in gallons): ");
            try {
                fuelToAdd = scanner.nextDouble();
                validInput = true;
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine(); // Consume invalid input
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        scanner.close();
        return fuelToAdd;
    }

    private static void updateContainer() {
        Scanner scanner = new Scanner(System.in);
        String filePath = "container.dat";
        List<Containers> containersList = Containers.loadContainersFromFile(filePath);
        List<Containers> updatedContainersList = new ArrayList<>();
        System.out.println("Enter Container ID to update: ");
        String containerIdToUpdate = scanner.nextLine();

        System.out.println("Enter new Container Weight: ");
        double newWeight = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Select new Container Type:");
        int typeIndex = 1;
        for (Containers.ContainerType type : Containers.ContainerType.values()) {
            System.out.println(typeIndex + ". " + type);
            typeIndex++;
        }
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (typeChoice >= 1 && typeChoice <= Containers.ContainerType.values().length) {
            Containers.ContainerType newType = Containers.ContainerType.values()[typeChoice - 1];
            Containers.updateContainer(filePath, containerIdToUpdate, newWeight, newType);
            System.out.println("Container updated successfully.");
            Containers.saveContainersToFile("data/container.data", updatedContainersList);
        } else {
            System.out.println("Invalid container type choice.");
        }

        scanner.close();
    }

    private static void addContainerToFile() {
        Scanner scanner = new Scanner(System.in);
        String filePath = "container.dat";

        List<Containers> containersList = Containers.loadContainersFromFile(filePath);
        System.out.print("Enter Container ID: ");
        String containerId = scanner.nextLine();
        System.out.print("Enter Container Weight: ");
        double weight = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.println("Select Container Type:");

        int typeIndex = 1;
        for (Containers.ContainerType type : Containers.ContainerType.values()) {
            System.out.println(typeIndex + ". " + type);
            typeIndex++;
        }
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (typeChoice >= 1 && typeChoice <= Containers.ContainerType.values().length) {
            Containers.ContainerType getType = Containers.ContainerType.values()[typeChoice - 1];
            Containers.saveContainersToFile(filePath, containersList);
            System.out.println("Container added successfully.");
        } else {
            System.out.println("Invalid container type choice.");
        }

        scanner.close();
    }

    private static void deleteContainer() {
        Scanner scanner = new Scanner(System.in);
        String filePath = "container.dat";

        List<Containers> containersList = Containers.loadContainersFromFile(filePath);
        System.out.print("Enter Container ID to delete: ");
        String containerIdToDelete = scanner.nextLine();
        Containers.deleteContainer(filePath, containerIdToDelete);
        System.out.println("Container deleted successfully.");

        scanner.close();
    }
        public List<Trip> listTripsBetweenDays(LocalDate dayA, LocalDate dayB) {
            List<Trip> tripsBetweenDays = new ArrayList<>();

            for (Trip trip : User.getTripsUnderControl()) {
                Date departureDate = trip.getDepartureDate();
                LocalDate tripDate = departureDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (!tripDate.isBefore(dayA) && !tripDate.isAfter(dayB)) {
                    tripsBetweenDays.add(trip);
                }
            }

            return tripsBetweenDays;
        }
}

