package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.FlightsDaoSQLImpl;
import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Flights;
import ba.unsa.etf.rpr.exceptions.FlightBookingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

public class FlightsManagerTest {

    private FlightsManager flightsManager;
    private Flights flight;
    private FlightsDaoSQLImpl flightsDaoSQLMock;
    private List<Flights> flights;

    @BeforeEach
    public void initializeObjectsWeNeed(){
        flightsManager = Mockito.mock(FlightsManager.class);
        flight = new Flights();
        flight.setSource("Sarajevo");
        flight.setDestination("Zagreb");
        flight.setDeparture(LocalDate.parse("2022-12-22"));
        flight.setDepartureTime("12:00 PM");
        flight.setArrival(LocalDate.parse("2022-12-22"));
        flight.setArrivalTime("12:00 PM");
        flight.setSeats(120);
        flight.setPriceEconomy(200);
        flight.setPriceBusiness(400);

        flight.setId(1);

        flightsDaoSQLMock = Mockito.mock(FlightsDaoSQLImpl.class);
        flights = new ArrayList<>();
        flights.addAll(Arrays.asList(new Flights("Dortmund"),new Flights("Dortmund"),new Flights("Plzen"),new Flights("Olomuc"),new Flights("Pribram")));
    }

    @Test
    public void add2()throws FlightBookingException{
        Flights newFlight = new Flights("Teplice");
        flightsManager.add(newFlight);

        Assertions.assertTrue(true);
        Mockito.verify(flightsManager).add(newFlight);
    }

    /**
     * We are testing add() method. An explanation will be covered in the comments below
     */

    @Test
    void add() throws FlightBookingException{
        MockedStatic<DaoFactory> daoFactoryMockedStatic = Mockito.mockStatic(DaoFactory.class);

        daoFactoryMockedStatic.when(DaoFactory::flightsDao).thenReturn(flightsDaoSQLMock);
        when(DaoFactory.flightsDao().getAll()).thenReturn(flights);
        Mockito.doCallRealMethod().when(flightsManager).add(flight);


        FlightBookingException flightBookingException = Assertions.assertThrows(FlightBookingException.class, () ->{
            flightsManager.add(flight);
        },"Can't add category with ID. ID is autogenerated");
        Assertions.assertEquals("Can't add category with ID. ID is autogenerated",flightBookingException.getMessage());

       Assertions.assertEquals("Can't add category with ID. ID is autogenerated",flightBookingException.getMessage());

        daoFactoryMockedStatic.verify(DaoFactory::flightsDao);
        Mockito.verify(flightsManager).add(flight);
        daoFactoryMockedStatic.close();
    }


}
