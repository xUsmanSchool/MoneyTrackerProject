package Factory;

import Events.*;
import Model.*;
import HelperClass.*;

public class TicketFactory {
    public Ticket getTicket(Person createdBy, Double totalSum, Event eventType, SplitType splitType) {
        switch (splitType) {
            case EQUAL: return new EqualTicket(createdBy, totalSum, eventType);
            case UNEQUAL: return new UnEqualTicket(createdBy, totalSum, eventType);
            default: return null;
        }
    };
}
