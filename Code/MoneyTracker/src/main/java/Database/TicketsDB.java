package Database;

import java.util.ArrayList;

public class TicketsDB extends Database {
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
            notifyObservers();
        } else System.err.println("PersonsDB: item does no match the correct type");
    }

    @Override
    public void remove(DatabaseItem item) {
        if (item instanceof Ticket) {
            this.ticketList.remove((Ticket)item);
            setChanged();
            notifyObservers();
        } else System.err.println("PersonsDB: item does no match the correct type");
    }

    @Override
    public ArrayList<Ticket> getAll() {
        return ticketList;
    }
}
