package main.entities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.entities.Containers.ContainerType;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
        super.setUserRole("Admin");
    }

    @Override
    public boolean hasPermission(Operation operation) {
        // Admin has permission for all operations
        return true;
    }

    @Override
    public double calculateDailyFuelConsumption() {
        double totalFuelConsumption = 0.0;
        for (Vehicles vehicles : super.getVehiclesUnderControl()) {
            totalFuelConsumption += vehicles.calculateDailyFuelConsumption();
        }
        return totalFuelConsumption;
    }

    @Override
    public List<Trip> listTripsInDay(Date date) {
        List<Trip> tripsInDay = new ArrayList<>();

        LocalDate inputDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (Trip trip : super.getTrips()) {
            if (isSameDay(trip.getDepartureDate(), inputDate) || isSameDay(trip.getArrivalDate(), inputDate)) {
                tripsInDay.add(trip);
            }
        }

        return tripsInDay;
    }

    private boolean isSameDay(Date date1, LocalDate inputDate) {
        // Convert the Date to LocalDate
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Check if the LocalDate and the Date objects represent the same day
        return localDate1.isEqual(inputDate);
    }
}
