package main.entities;
import java.util.Map;
import java.util.List;
import java.time.LocalDate;
import java.util.Date;

import main.entities.Containers.ContainerType;

public interface User {
    String getUsername();
    String getPassword();
    UserRole getRole();
    boolean hasPermission(Operation operation);
    double calculateDailyFuelConsumption();
    Map<ContainerType, Double> calculateTotalWeightByContainerType();
    List<Ship> listAllShipsInPort(Port port);
    List<Trip> listTripsInDay(Date date);
    List<Trip> listTripsBetweenDays(LocalDate dayA, LocalDate dayB);
}
enum UserRole {
    ADMIN,
    PORT_MANAGER
}

enum Operation {
    MANAGE_VEHICLES,
    MANAGE_CONTAINERS,
    MANAGE_PORTS,
    VIEW_HISTORY
}