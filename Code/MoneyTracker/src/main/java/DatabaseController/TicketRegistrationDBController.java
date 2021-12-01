package DatabaseController;

import Database.*;
import Model.*;
import java.util.ArrayList;

public class TicketRegistrationDBController implements DatabaseController<Ticket> {
    private final TicketsDB db;

    public TicketRegistrationDBController(TicketsDB db)
    {
        this.db = db;
    }

    @Override
    public void add(Ticket ticket) {
        db.add(ticket);
    }

    @Override
    public void remove(Ticket ticket) {
        db.remove(ticket);
    }

    @Override
    public ArrayList<Ticket> getAll() {
        return new ArrayList<>(db.getAll());
    }
}
