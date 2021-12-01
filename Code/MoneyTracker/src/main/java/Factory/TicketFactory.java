package Factory;

import Events.Event;
import Model.EqualTicket;
import Model.Person;
import Model.Ticket;
import HelperClass.SplitType;
import Model.UnEqualTicket;

public class TicketFactory {
    public Ticket getTicket(Person createdBy, Event eventType, SplitType splitType) {
        switch (splitType) {
            case EQUAL: return new EqualTicket(createdBy, eventType);
            case UNEQUAL: return new UnEqualTicket(createdBy, eventType);
            default: return null;
        }
    };
}
