package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Tickets;
import ba.unsa.etf.rpr.exceptions.FlightBookingException;
import ba.unsa.etf.rpr.controllers.UserPanelController;

import java.util.List;

public class TicketsManager {

    public Tickets add(Tickets ticket)throws FlightBookingException{
        if(ticket.getId() != 0){
            throw new FlightBookingException("Can't add category with ID. ID is autogenerated");
        }
        try {
            return  DaoFactory.ticketsDao().add(ticket);
        }catch (FlightBookingException t){
            throw t;
        }
    }

    public void delete(int ticketId)throws FlightBookingException{
        try {
            DaoFactory.ticketsDao().delete(ticketId);
        }catch(FlightBookingException f){
            if(f.getMessage().contains("FOREIGN KEY")){
                throw new FlightBookingException("Cannot delete category which is related to quotes. First delete related quotes before deleting category.");
            }
            throw f;
        }
    }


    public Tickets update(Tickets ticket)throws FlightBookingException{
        return DaoFactory.ticketsDao().update(ticket);
    }

    public List<Tickets> getAll() throws FlightBookingException {
        return DaoFactory.ticketsDao().getAll();
    }

    public Tickets getById(int id) throws FlightBookingException {
        return DaoFactory.ticketsDao().getById(id);
    }

}
