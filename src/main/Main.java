package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.entities.Admin;
import main.entities.Port;
import main.entities.PortManager;
import main.entities.User;

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

        // Create Admin user
        Admin adminUser = new Admin("admin", "adminpassword");

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
                    case 1:
                        // Manage Vehicles
                        if ("PortManager".equals(loggedInUser.getRole())) {
                            // Implement Port Manager's Manage Vehicles functionality here
                            System.out.println("Accessing Port Manager's Manage Vehicles...");
                        } else if ("Admin".equals(loggedInUser.getRole())) {
                            // Implement Admin's Manage Vehicles functionality here
                            System.out.println("Accessing Admin's Manage Vehicles...");
                        }
                        break;
                    case 2:
                        // Manage Containers
                        if ("PortManager".equals(loggedInUser.getRole())) {
                            manageContainers(); // Implementation of Container Management Menu
                            System.out.println("Accessing Port Manager's Manage Containers...");
                        } else if ("Admin".equals(loggedInUser.getRole())) {
                            manageContainers(); // Implementation of Container Management Menu
                            System.out.println("Accessing Admin's Manage Containers...");
                        }
                        break;
                    case 3:
                        // Manage Ports
                        if ("PortManager".equals(loggedInUser.getRole())) {
                            System.out.println("Accessing Port Manager's Manage Ports...");
                            managePorts(); // Implementation of Port Management Menu
                        } else if ("Admin".equals(loggedInUser.getRole())) {
                            System.out.println("Accessing Admin's Manage Ports...");
                            managePorts(); // Implementation of Port Management Menu
                        }
                        break;
                    case 4:
                        // View History
                        if ("PortManager".equals(loggedInUser.getRole())) {
                            // Implement Port Manager's View History functionality here
                            System.out.println("Accessing Port Manager's View History...");
                        } else if ("Admin".equals(loggedInUser.getRole())) {
                            // Implement Admin's View History functionality here
                            System.out.println("Accessing Admin's View History...");
                        }
                        break;
                    case 5:
                        // Statistics Operations
                        if ("PortManager".equals(loggedInUser.getRole())) {
                            // Implement Port Manager's Statistics Operations functionality here
                            System.out.println("Accessing Port Manager's Statistics Operations...");
                        } else if ("Admin".equals(loggedInUser.getRole())) {
                            // Implement Admin's Statistics Operations functionality here
                            System.out.println("Accessing Admin's Statistics Operations...");
                        }
                        break;
                    case 0:
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

        if ("PortManager".equals(loggedInUser.getRole())) {
            System.out.println("1. Manage Vehicles");
            System.out.println("2. Manage Containers");
            System.out.println("3. Manage Ports");
            System.out.println("4. View History");
            System.out.println("5. Statistics Operations");
        } else if ("Admin".equals(loggedInUser.getRole())) {
            System.out.println("1. Manage Vehicles");
            System.out.println("2. Manage Containers");
            System.out.println("3. Manage Ports");
            System.out.println("4. Manage Port Managers");
            System.out.println("5. View History");
            System.out.println("6. Statistics Operations");
        }

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
    private static void managePorts() {
        PortMenu portMenu = new PortMenu();
        Port port1 = new Port();
        portMenu.displayPortMenu();
    }
    private static void manageContainers() {
        ContainerMenu containerMenu = new ContainerMenu();
        containerMenu.displayContainerMenu();
    }
}
