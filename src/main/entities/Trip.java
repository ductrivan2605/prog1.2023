package main.entities;
// import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
// import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
// import java.util.Locale;

// import main.entities.Vehicles;

public class Trip {
    private static TripStatus status;
    private String tripId;
    private Vehicles vehicle;
    private Date departureDate;
    private Date arrivalDate;
    private Port departurePort;
    private Port arrivalPort;

    public Trip(String tripId, Vehicles vehicle, Date departureDate, Date arrivalDate, Port departurePort, Port arrivalPort, TripStatus status) {
        this.tripId = tripId;
        this.vehicle = vehicle;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        Trip.status = status;
    }

    //methods
    // Load Trips from trips.data
    public static List<Trip> loadTripsFromFile(String filename) {
        List<Trip> trips = new ArrayList<>();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
            trips = (List<Trip>) objectInputStream.readObject();
            System.out.println("Trips loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return trips;
    }

     // Save a list of Trip objects to a file
     public static void saveTripsToFile(List<Trip> trips, String filename) {
         try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
             objectOutputStream.writeObject(trips);
             System.out.println("Trips saved to " + filename);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    // Update a trip in a list by its tripId
    public static boolean updateTripToFile(String filePath, String tripId, Trip updatedTrip) {
        List<Trip> trips = loadTripsFromFile(filePath);

        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i).getTripId().equals(tripId)) {
                trips.set(i, updatedTrip);
                saveTripsToFile(trips, "trip.dat"); // Save the updated list of trips to the file
                return true; // Trip updated successfully
            }
        }
        return false; // Trip not found
    }

    // Delete a trip from a list by its tripId
    public static boolean deleteTripFromFile(String filePath, String tripId) {
        List<Trip> trips = loadTripsFromFile(filePath);

        Trip tripToDelete = null;
        for (Trip trip : trips) {
            if (trip.getTripId().equals(tripId)) {
                tripToDelete = trip;
                break;
            }
        }

        if (tripToDelete != null) {
            trips.remove(tripToDelete);
            saveTripsToFile(trips, "trip.dat"); // Save the updated list of trips to the file
            return true; // Trip deleted successfully
        }
        return false; // Trip not found
    }


    //getters and setters
    public String getTripId() {
        return tripId;
    }

    public Vehicles getVehicle() {
        return vehicle;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        Trip.status = status;
    }

    //toString
    @Override
    public String toString() {
        return "Trip{" +
                "tripId='" + tripId + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", departurePort=" + departurePort +
                ", arrivalPort=" + arrivalPort +
                ", status=" + status +
                '}';
    }
    enum TripStatus {
        PLANNED,
        IN_PROGRESS,
        COMPLETED,
        CANCELED
    }
// This is AI generated solution for loading trips but it looks too messy and my teammate cant follow up
//     public static List<Trip> loadTrips(String filePath, List<Port> ports, List<Vehicles> vehicles) {
//     List<Trip> trips = new ArrayList<>();
//     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

//     try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//         String line;
//         while ((line = reader.readLine()) != null) {
//             String[] data = line.split(",");
//             if (data.length == 8) {
//                 String tripId = data[0].trim();
//                 String vehicleId = data[1].trim();
//                 Date departureDate = dateFormat.parse(data[2].trim());
//                 Date arrivalDate = dateFormat.parse(data[3].trim());
//                 String departurePortId = data[4].trim();
//                 String arrivalPortId = data[5].trim();
//                 String statusStr = data[6].trim();

//                 Vehicles vehicle = findVehicleById(vehicleId, vehicles);
//                 Port departurePort = findPortById(departurePortId, ports);
//                 Port arrivalPort = findPortById(arrivalPortId, ports);
//                 TripStatus status = TripStatus.valueOf(statusStr);

//                 if (vehicle != null && departurePort != null && arrivalPort != null) {
//                     trips.add(new Trip(tripId, vehicle, departureDate, arrivalDate, departurePort, arrivalPort, status));
//                 }
//             }
//         }
//     } catch (IOException | ParseException e) {
//         e.printStackTrace();
//     }
//     return trips;
// }

//     public static Port findPortById(String portId, List<Port> ports) {
//         for (Port port : ports) {
//             if (port.getId().equals(portId)) {
//                 return port;
//             }
//         }
//         return null; // Port with the specified ID not found
//     }
    

//     private static Vehicles findVehicleById(String vehicleId, List<Vehicles> vehicles) {
//         for (Vehicles vehicle : vehicles) {
//             if (vehicle.getVehicleId().equals(vehicleId)) {
//                 return vehicle;
//             }
//         }
//         return null;
//     }
}
