package main.data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import main.entities.Port;
import main.entities.Trip;
import main.entities.User;
import main.entities.Vehicles;

public class DataLoader {
    private Trip TripStatus = tripStatus;

    public static List<Port> loadPorts(String filePath) {
        List<Port> ports = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length == 7) {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    double latitude = Double.parseDouble(data[2].trim());
                    double longitude = Double.parseDouble(data[3].trim());
                    double storingCapacity = Double.parseDouble(data[4].trim());
                    boolean landingAbility = Boolean.parseBoolean(data[5].trim());
                    String managerUsername = data[6].trim();

                    User portManager = findUserByUsername(managerUsername, null);
                    if (portManager != null) {
                        Port port = new Port(id, name, latitude, longitude, storingCapacity, landingAbility);
                        ports.add(port);
                    } else {
                        System.err.println("Port manager not found for port: " + name);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ports;
    }
    public static User findUserByUsername(String username, List<User> users) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }
    // Load Users from file user.dat
//    public static List<User> loadUsers(String filePath) {
//        List<User> users = new ArrayList<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] data = line.split(",");
//                if (data.length == 3) {
//                    String id = data[0].trim();
//                    String username = data[1].trim();
//                    String password = data[2].trim();
//                    users.add(new User(id, username, password));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return users;
//    }
    // Load Trips from trips.dat
    public static List<Trip> loadTrips(String filePath, List<Port> ports, List<Vehicles> vehicles) {
        List<Trip> trips = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) {
                    String id = data[0].trim();
                    String vehicleId = data[1].trim();
                    String departureDateString = data[2].trim();
                    String arrivalDateString = data[3].trim();
                    String departurePortId = data[4].trim();
                    String arrivalPortId = data[5].trim();
                    String statusString = data[6].trim();

                    // Find the vehicle by its ID
                    Vehicles vehicle = findVehicleById(vehicleId, vehicles);

                    // Parse departure and arrival dates
                    Date departureDate = dateFormat.parse(departureDateString);
                    Date arrivalDate = dateFormat.parse(arrivalDateString);

                    // Find the departure and arrival ports by their IDs
                    Port departurePort = findPortById(departurePortId, ports);
                    Port arrivalPort = findPortById(arrivalPortId, ports);

                    if (vehicle != null && departurePort != null && arrivalPort != null) {
                        tripStatus status = tripStatus.valueOf(statusString); // Assuming TripStatus is an enum
                        trips.add(new Trip(id, vehicle, departureDate, arrivalDate, departurePort, arrivalPort, status));
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return trips;
    }
    // Utility method to find a vehicle by its ID
    private static Vehicles findVehicleById(String vehicleId, List<Vehicles> vehicles) {
        for (Vehicles vehicle : vehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                return vehicle;
            }
        }
        return null;
    }
}
