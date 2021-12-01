package DatabaseController;

import Database.TicketsDB;
import Model.Ticket;

import java.util.ArrayList;

public class TicketRegistrationDBController {
    private final TicketsDB db;

    public TicketRegistrationDBController(TicketsDB db)
    {
        this.db = db;
    }

    public void add(Ticket ticket) {
        db.add(ticket);
    }

    public void remove(Ticket ticket) {
        db.remove(ticket);
    }

    public ArrayList<Ticket> getAll() {
        return new ArrayList<>(db.getAll());
    }
}
