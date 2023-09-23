package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.entities.Admin;
import main.entities.PortManager;
import main.entities.User;

public class ManagementSystemInterface {

    private static boolean isLoggedIn = false;
    private static List<User> users = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create PortManager users
        PortManager portManager1 = new PortManager("portmanager1", "password1", "PortA");
        PortManager portManager2 = new PortManager("portmanager2", "password2", "PortB");
        PortManager portManager3 = new PortManager("portmanager3", "password3", "PortC");
        PortManager portManager4 = new PortManager("portmanager4", "password4", "PortD");
        PortManager portManager5 = new PortManager("portmanager5", "password5", "PortE");

        Admin adminUser = new Admin("admin", "adminpassword");

        users.add((User) portManager1);
        users.add((User) portManager2);
        users.add((User) portManager3);
        users.add((User) portManager4);
        users.add((User) portManager5);
        users.add((User) adminUser);

        while (true) {
            if (!isLoggedIn) {
                // If not logged in, ask the user to log in
                login(scanner);
            } else {
                // If logged in, display the main menu and handle menu choices
                displayMainMenu();
                int choice = getUserChoice(scanner);

                switch (choice) {
                    case 1:
                        // Add a new entity (Vehicle, Port, Container, Manager)
                        // Implement this functionality
                        break;
                    case 2:
                        // Remove an entity (Vehicle, Port, Container, Manager)
                        // Implement this functionality
                        break;
                    case 3:
                        // CRUD operations
                        // Implement this functionality
                        break;
                    case 4:
                        // Load/Unload container from a vehicle
                        // Implement this functionality
                        break;
                    case 5:
                        // Move a vehicle to a port
                        // Implement this functionality
                        break;
                    case 6:
                        // Refuel a vehicle
                        // Implement this functionality
                        break;
                    case 7:
                        // Statistics operations - can only be accessed if logged in
                        if (isLoggedIn) {
                            // Implement statistics functionality here
                            System.out.println("Accessing statistics operations...");
                        } else {
                            System.out.println("You must log in to access statistics operations.");
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
        System.out.println("1. Add an Entity");
        System.out.println("2. Remove an Entity");
        System.out.println("3. CRUD Operations");
        System.out.println("4. Load/Unload Container");
        System.out.println("5. Move Vehicle to Port");
        System.out.println("6. Refuel Vehicle");
        System.out.println("7. Statistics Operations");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }private static User UserRole = UserRole.PORT_MANAGER;

    private static int getUserChoice(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                isLoggedIn = true;
                loggedInUserRole = user.getRole();
                System.out.println("Logged in successfully as " + loggedInUserRole);
                return;
            }
        }

        System.out.println("Login failed. Please check your username and password.");
    }


}