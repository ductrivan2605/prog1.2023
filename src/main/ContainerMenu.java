package main;

import java.util.List;
import java.util.Scanner;
import main.entities.Containers;
import main.entities.Containers.ContainerType;

public class ContainerMenu {
    private static void addContainer(Scanner scanner) {
        System.out.print("Enter Container ID: ");
        String containerId = scanner.nextLine();
        System.out.print("Enter Container Weight: ");
        double weight = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.println("Select Container Type:");
        int typeIndex = 1;
        for (ContainerType type : ContainerType.values()) {
            System.out.println(typeIndex + ". " + type);
            typeIndex++;
        }
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (typeChoice >= 1 && typeChoice <= ContainerType.values().length) {
            ContainerType selectedType = ContainerType.values()[typeChoice - 1];
            Containers.addContainer(containerId, weight, selectedType);
            System.out.println("Container added successfully.");
        } else {
            System.out.println("Invalid container type choice.");
        }
    }

    private static void deleteContainer(Scanner scanner) {
        System.out.print("Enter Container ID to delete: ");
        String containerIdToDelete = scanner.nextLine();
        Containers.deleteContainer(containerIdToDelete);
        System.out.println("Container deleted successfully.");
    }

    private static void updateContainer(Scanner scanner) {
        System.out.print("Enter Container ID to update: ");
        String containerIdToUpdate = scanner.nextLine();
        System.out.print("Enter new Container Weight: ");
        double newWeight = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.println("Select new Container Type:");
        int typeIndex = 1;
        for (ContainerType type : ContainerType.values()) {
            System.out.println(typeIndex + ". " + type);
            typeIndex++;
        }
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (typeChoice >= 1 && typeChoice <= ContainerType.values().length) {
            ContainerType newType = ContainerType.values()[typeChoice - 1];
            Containers.updateContainer(containerIdToUpdate, newWeight, newType);
            System.out.println("Container updated successfully.");
        } else {
            System.out.println("Invalid container type choice.");
        }
    }


    public void displayContainerMenu() {
        Scanner scanner = new Scanner(System.in);
            System.out.println("Container Management Menu");
            System.out.println("1. Add Container");
            System.out.println("2. Delete Container");
            System.out.println("3. Update Container");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Add Container
                    addContainer(scanner);
                    break;
                case 2:
                    // Delete Container
                    deleteContainer(scanner);
                    break;
                case 3:
                    // Update Container
                    updateContainer(scanner);
                    break;
                case 0:
                    // Exit the Container Menu
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
}
