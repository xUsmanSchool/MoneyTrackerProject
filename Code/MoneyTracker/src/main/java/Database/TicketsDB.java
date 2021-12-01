package Database;

import Iterator.Container;
import Iterator.Iterator;
import Model.Ticket;
import Observers.TicketDBObservableEntry;
import java.util.ArrayList;

public class TicketsDB extends Database implements Container {
    private static TicketsDB databaseInstance;
    private final ArrayList<Ticket> ticketList;

    private TicketsDB() { this.ticketList = new ArrayList<>(); }

    public static TicketsDB getInstance(){
        if (databaseInstance == null) { databaseInstance = new TicketsDB(); }
        return databaseInstance;
    }

    @Override
    public void add(DatabaseItem item) {
        if (item instanceof Ticket) {
            this.ticketList.add((Ticket)item);
            setChanged();
            notifyObservers(new TicketDBObservableEntry((Ticket) item, true));
        } else System.err.println("TicketDB: item does not match the correct type");
    }

    @Override
    public void remove(DatabaseItem item) {
        if (item instanceof Ticket) {
            this.ticketList.remove((Ticket)item);
            setChanged();
            notifyObservers(new TicketDBObservableEntry((Ticket) item, false));
        } else System.err.println("TicketDB: item does not match the correct type");
    }

    @Override
    public ArrayList<Ticket> getAll() {
        return ticketList;
    }

    @Override
    public Iterator<Ticket> getIterator() {
        return new TicketIterator();
    }

    class TicketIterator implements Iterator<Ticket> {
        int index;

        @Override
        public boolean hasNext() {
            return index < ticketList.size();
        }

        @Override
        public Ticket next() {
            return this.hasNext() ? ticketList.get(index++) : null;
        }
    }
}
