package Factory;

import Events.*;
import Model.*;
import HelperClass.*;

public class TicketFactory {
    public Ticket getTicket(Person createdBy, Event eventType, SplitType splitType) {
        switch (splitType) {
            case EQUAL: return new EqualTicket(createdBy, eventType);
            case UNEQUAL: return new UnEqualTicket(createdBy, eventType);
            default: return null;
        }
    };
}
