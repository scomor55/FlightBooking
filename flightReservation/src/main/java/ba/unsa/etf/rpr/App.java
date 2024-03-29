package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.domain.Flights;
import ba.unsa.etf.rpr.domain.Passengers;
import ba.unsa.etf.rpr.business.FlightsManager;
import ba.unsa.etf.rpr.business.PassengersManager;
import org.apache.commons.cli.*;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

/**
 * CLI (Command Line Interface) implementation in following class.
 * Even though this type of presentation layer (called CLI) is becoming past tense for GUI apps,
 * it's good to see how you can manipulate data through command line and database also.
 * @author Safet Čomor
 * Main method that starts the application.
 */

public class App
{
    /**
     * Option for adding a new flight to the Flights database
     */
    private static final Option addFlight = new Option("newFlight","add-flight",false,"\"Adding new flight to Flights database\"");
    /**
     * Option for adding a new passenger to the Passengers database
     */
    private static final Option addPassenger = new Option("newPassenger","add-passenger",false,"\"Adding new passenger to Passenger database\"");
    /**
     * Option for printing all flights from the Flights database
     */
    private static final Option getFlights = new Option("getFlights","get-flights",false,"\"Printing all flights from Flights database\"");
    /**
     * Option for defining flight for next added flight
     */
    private static final Option getPassengers = new Option("getPassengers","get-passengers",false,"\"Printing all passengers from Passengers database\"");
    /**
     * Option for defining flight for next added flight
     */
    private static final Option passengerDefinition = new Option(null,"flight",false,"Defining flight for next added flight");
    /**
     * Method for printing formatted options
     * @param options list of options
     */
    public static void printFormattedOptions(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(System.out);
        helpFormatter.printUsage(printWriter, 150, "java -jar flightBooking.jar [option] 'something else if needed' ");
        helpFormatter.printOptions(printWriter, 150, options, 2, 7);
        printWriter.close();
    }
    /**
     * Method for adding options
     * @return list of options
     */
    public static Options addOptions() {
        Options options = new Options();
        options.addOption(addFlight);
        options.addOption(addPassenger);
        options.addOption(getFlights);
        options.addOption(getPassengers);
        options.addOption(passengerDefinition);
        return options;
    }
    /**
     * Method for searching through Passengers database
     * @param listOfPassengers list of passengers in the database
     * @param name name of the passenger to be searched for
     * @return Passengers object if found, null otherwise
     */
    public static Passengers searchThroughPassengers(List<Passengers> listOfPassengers, String name) {
        Passengers passenger = null;
        passenger = listOfPassengers.stream().filter(cat -> cat.getName().toLowerCase().equals(name.toLowerCase())).findAny().get();
        return passenger;
    }
    /**
     * Main method which takes command line arguments and performs different operations based on options provided.
     * @param args command line arguments
     * @throws Exception
     */
    public static void main( String[] args )throws Exception
    {
    Options options = addOptions();
    CommandLineParser commandLineParser = new DefaultParser();
    CommandLine cl = commandLineParser.parse(options,args);

    if((cl.hasOption(addFlight.getOpt())||cl.hasOption(addFlight.getLongOpt())) /*&& cl.hasOption((passengerDefinition.getLongOpt()))*/){
        FlightsManager flightsManager = new FlightsManager();
        Flights flight = new Flights();
        flight.setSource("Sarajevo");
        flight.setDestination(cl.getArgList().get(0));
        flight.setDeparture(LocalDate.parse("2023-01-01"));
        flight.setDepartureTime("12:00 AM");
        flight.setArrival(LocalDate.parse("2023-01-01"));
        flight.setArrivalTime("12:00 PM");
        flight.setSeats(156);
        flight.setPriceEconomy(100);
        flight.setPriceBusiness(200);
        flightsManager.add(flight);
        System.out.println("You successfully added flight to database!");
    }else if(cl.hasOption(getFlights.getOpt())|| cl.hasOption(getFlights.getLongOpt())){
        FlightsManager flightsManager = new FlightsManager();
        flightsManager.getAll().forEach(q -> System.out.println(q.getDestination()));
    }else if(cl.hasOption(addPassenger.getOpt())|| cl.hasOption(addPassenger.getLongOpt())){
        try {
        PassengersManager passengersManager = new PassengersManager();
        Passengers passenger = new Passengers();
        passenger.setName(cl.getArgList().get(0));
        passenger.setSurname("Doe");
        passenger.setDateOfBirth(LocalDate.parse("2023-01-01"));
        passenger.setAdress("Nowhere");
        passenger.setEmail("a@doe.gmail.com");
        passengersManager.add(passenger);
        System.out.println("Passenger been added successfully");
        }catch(Exception e){
            System.out.println("There is already passenger with same name in database! Try again");
            System.exit(1);
        }
    }else if(cl.hasOption(getPassengers.getOpt())|| cl.hasOption(getPassengers.getLongOpt())){
        PassengersManager passengersManager = new PassengersManager();
        passengersManager.getAll().forEach(newPassenger -> System.out.println(newPassenger.getName()));
    }else{
        printFormattedOptions(options);
        System.exit(-1);
    }
    }


}


