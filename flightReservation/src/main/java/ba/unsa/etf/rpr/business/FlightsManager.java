package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Flights;
import ba.unsa.etf.rpr.exceptions.FlightBookingException;
import javafx.scene.control.Alert;

import java.util.List;
/**
 * The FlightsManager class provides functionality for managing flights.
 * @author Safet Čomor
 */
public class FlightsManager {

    /**
     * Validate the source name for a flight.
     * @param name the name of the source
     * @throws FlightBookingException if source name is not between 3 and 30 characters
     */
    public void validateSourceName(String name)throws FlightBookingException{
        if(name == null || name.length() > 30 || name.length() < 3){
            throw new FlightBookingException("Source name must be between 3 and 30 characters");
        }
    }
    /**
     * Validate the destination name for a flight.
     * @param name the name of the destination
     * @throws FlightBookingException if destination name is not between 3 and 30 characters
     */
    public void validateDestinationName(String name)throws FlightBookingException{
        if(name == null || name.length() > 30 || name.length() < 3){
            throw new FlightBookingException("Destination name must be between 3 and 30 characters");
        }
    }
    /**
     * Validate the number of seats for a flight.
     * @param numberOfSeats the number of seats for the flight
     * @throws FlightBookingException if number of seats is not between 20 and 853
     */
    public void validateSeats(int numberOfSeats)throws FlightBookingException{
        if(numberOfSeats < 20 || numberOfSeats > 853){
            throw new FlightBookingException("Number of seats must be between 20 and 853");
        }
    }
    /**
     * Add a new flight to the system.
     * @param flight the flight to add
     * @return the added flight
     * @throws FlightBookingException if the flight has an ID or if adding the flight fails
     */
    public Flights add(Flights flight)throws FlightBookingException{
        if(flight.getId() != 0){
            throw new FlightBookingException("Can't add flight with ID. ID is autogenerated");
        }
        try {
            return  DaoFactory.flightsDao().add(flight);
        }catch (FlightBookingException f){
            throw f;
        }
    }
    /**
     * Delete a flight from the system.
     * @param flightId the ID of the flight to delete
     * @throws FlightBookingException if deleting the flight fails or if it is related to quotes
     */
    public void delete(int flightId)throws FlightBookingException{
    try {
        DaoFactory.flightsDao().delete(flightId);
    }catch(FlightBookingException f){
        if(f.getMessage().contains("FOREIGN KEY")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Flight undeleted");
            alert.setContentText("You have to delete all tickets on this flight!");
            alert.showAndWait();
        }
    }
    }
    /**
     * Update a flight in the system.
     * @param flight the updated flight
     * @return the updated flight
     * @throws FlightBookingException if updating the flight fails
     */
    public Flights update(Flights flight)throws FlightBookingException{
        return DaoFactory.flightsDao().update(flight);
    }
    /**
     * Get a list of all flights in the system.
     * @return a list of all flights
     * @throws FlightBookingException if getting the flights fails
     */
    public List<Flights> getAll() throws FlightBookingException {
        return DaoFactory.flightsDao().getAll();
    }
    /**
     * Get a flight by its ID.
     * @param id the ID of the flight to get
     * @return the flight with the specified ID
     * @throws FlightBookingException if getting the flight fails
     */
    public Flights getById(int id) throws FlightBookingException {
        return DaoFactory.flightsDao().getById(id);
    }
    /**
     * @param source the source of the flight
     * @param destination the destination of the flight
     * @return a list of flights with matching source and destination
     * @throws FlightBookingException if an error occurs while searching
     */
    public List<Flights>searchBySourceAndDestination(String source,String destination) throws FlightBookingException {
        return DaoFactory.flightsDao().searchBySourceAndDestination(source,destination);
    }
    /**
     * @param destination the destination of the flight
     * @return a list of flights with matching destination
     * @throws FlightBookingException if an error occurs while searching
     */
    public List <Flights>searchByDestination(String destination) throws FlightBookingException {
        return DaoFactory.flightsDao().searchByDestination(destination);
    }
    /**
     @param passengerID the ID of the passenger
     @return a list of flights the passenger is booked for
     @throws FlightBookingException if an error occurs while searching
     */

}