package ba.unsa.etf.rpr.domain;

import java.util.Date;
import java.util.Objects;


public class Tickets implements Idable{
    private int ticketID;
    private int flightID;
    private int passengerID;
    private String travelClass;
    private int price;

    public int getId() {
        return ticketID;
    }

    public void setId(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Tickets{" +
                "ticketID=" + ticketID +
                ", flightID=" + flightID +
                ", passengerID=" + passengerID +
                ", class:'" + travelClass + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tickets ticket = (Tickets) o;
        return ticketID == ticket.ticketID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketID, flightID, passengerID, travelClass,price);
    }

}
