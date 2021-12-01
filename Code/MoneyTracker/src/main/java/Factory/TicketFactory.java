package Factory;

import Events.Event;
import model.EqualTicket;
import model.Person;
import model.Ticket;
import HelperClass.SplitType;
import model.UnEqualTicket;

public class TicketFactory {
    public Ticket getTicket(Person createdBy, Event eventType, SplitType splitType) {
        switch (splitType) {
            case EQUAL: return new EqualTicket(createdBy, eventType);
            case UNEQUAL: return new UnEqualTicket(createdBy, eventType);
            default: return null;
        }
    };
}
