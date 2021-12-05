package Observers;

import Model.Ticket;

public class TicketDBObservableEntry {
    private final Ticket ticket;
    private final boolean added;

    public TicketDBObservableEntry(Ticket ticket, boolean added){
        this.ticket = ticket;
        this.added = added;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public boolean isAdded() {
        return added;
    }
}
