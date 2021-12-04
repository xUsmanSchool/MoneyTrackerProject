package DatabaseController;

import Database.*;
import HelperClass.WriteToJSONFile;
import Model.*;
import java.util.ArrayList;

public class TicketsDBController {
    private final TicketsDB db;

    public TicketsDBController(TicketsDB db)
    {
        this.db = db;
    }

    public void add(Ticket ticket) {
        db.add(ticket);
        WriteToJSONFile.updateTicketFile(this.db);
    }

    public void remove(Ticket ticket) {
        db.remove(ticket);
        WriteToJSONFile.updateTicketFile(this.db);
    }

    public ArrayList<Ticket> getAll() {
        return new ArrayList<>(db.getAll());
    }
}
