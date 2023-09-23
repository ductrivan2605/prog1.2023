package main.service;
import java.util.ArrayList;
import java.util.List;
import main.entities.Trip;

public class TripService {
    private List<Trip> trips;

    public TripService() {
        this.trips = new ArrayList<>();
    }

    // Method to add a trip
    public void addTrip(Trip trip) {
        trips.add(trip);
    }

    // Method to get a trip by trip ID
    public Trip getTripById(String tripId) {
        for (Trip trip : trips) {
            if (trip.getTripId().equals(tripId)) {
                return trip;
            }
        }
        return null; // Trip not found
    }

    // Method to check if a trip exists
    public boolean tripExists(String tripId) {
        return getTripById(tripId) != null;
    }

    // Method to remove a trip
    public void removeTrip(String tripId) {
        trips.removeIf(trip -> trip.getTripId().equals(tripId));
    }

    // Method to retrieve a list of all trips
    public List<Trip> getAllTrips() {
        return new ArrayList<>(trips);
    }

    // Additional methods for trip management
    public void updateTrip(Trip trip) {
        Trip existingTrip = getTripById(trip.getTripId());
        if (existingTrip != null) {
            // Replace the existing trip with the updated trip
            trips.remove(existingTrip);
            trips.add(trip);
        } else {
            // Handle the case when the trip does not exist
            throw new IllegalArgumentException("Trip does not exist.");
        }
    }
}
