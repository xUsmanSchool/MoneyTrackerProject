package DatabaseController;

import Database.*;
import HelperClass.WriteToJSONFile;
import Iterator.Container;
import Iterator.Iterator;
import Model.*;
import java.util.ArrayList;

public class TicketsDBController implements Container {
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

    @Override
    public Iterator<Ticket> getIterator() {
        return new TicketIterator();
    }

    class TicketIterator implements Iterator<Ticket> {
        int index;

        @Override
        public boolean hasNext() {
            return index < getAll().size();
        }

        @Override
        public Ticket next() {
            return this.hasNext() ? getAll().get(index++) : null;
        }
    }
}
