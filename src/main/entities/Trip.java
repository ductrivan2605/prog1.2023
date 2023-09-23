package main.entities;
import java.util.Date;

public class Trip {
    private String tripId;
    private Vehicles vehicle;
    private Date departureDate;
    private Date arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private TripStatus status;

    public Trip(String tripId, Vehicles vehicle, Date departureDate, Date arrivalDate, Port departurePort, Port arrivalPort, TripStatus status) {
        this.tripId = tripId;
        this.vehicle = vehicle;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = status;
    }

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
        this.status = status;
    }
    enum TripStatus {
        PLANNED,
        IN_PROGRESS,
        COMPLETED,
        CANCELED
    }
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
}
