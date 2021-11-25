package Database;

import java.util.ArrayList;

public class TicketsDB extends Database {
    private static TicketsDB databaseInstance;
    private ArrayList<Ticket> ticketList;

    private TicketsDB() { this.ticketList = new ArrayList<Ticket>(); }

    public static TicketsDB getInstance(){
        if (databaseInstance == null) { databaseInstance = new TicketsDB(); }
        return databaseInstance;
    }

    public void add(Ticket ticket) {
        this.ticketList.add(ticket);
        setChanged();
        notifyObservers();
    }

    public void remove(Ticket ticket) {
        this.ticketList.remove(ticket);
        setChanged();
        notifyObservers();
    }

    public ArrayList<Ticket> getTickets() {
        return ticketList;
    }
}
