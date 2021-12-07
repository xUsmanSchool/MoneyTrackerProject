package Factory;

import Events.*;
import Model.*;
import HelperClass.*;

public class TicketFactory {
    public Ticket getTicket(Person payedBy, Double totalSum, Event eventType, SplitType splitType) {
        switch (splitType) {
            case EQUAL: return new EqualTicket(payedBy, totalSum, eventType);
            case UNEQUAL: return new UnEqualTicket(payedBy, totalSum, eventType);
            default: return null;
        }
    };
}
