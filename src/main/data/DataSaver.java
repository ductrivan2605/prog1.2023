package main.data;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.entities.Port;
import main.entities.User;
import main.entities.Vehicles;

public class DataSaver {
      public static List<Port> loadPorts(String filePath) {
        List<Port> ports = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length == 9) {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    double latitude = Double.parseDouble(data[2].trim());
                    double longitude = Double.parseDouble(data[3].trim());
                    double capacity = Double.parseDouble(data[4].trim());
                    boolean landingAbility = Boolean.parseBoolean(data[5].trim());
                    int containerCount = Integer.parseInt(data[6].trim());
                    int vehicleCount = Integer.parseInt(data[7].trim());
                    String managerUsername = data[8].trim();
                    User portManager = findUserByUsername(managerUsername, null);
                    Port port = new Port(id, name, latitude, longitude, capacity, landingAbility);
                    port.setContainerCount();
                    port.setVehicleCount();
                    port.setPortManager();
                    ports.add(port);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ports;
    }
    // Find a user by username
    public static User findUserByUsername(String username, List<User> users) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        // Return null if the user is not found
        return null;
    }
}
