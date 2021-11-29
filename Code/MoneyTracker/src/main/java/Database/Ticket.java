package Database;

import Events.Event;
import HelperClass.Date;
import HelperClass.Events;
import java.util.HashMap;

public class Ticket extends DatabaseItem {
    private HashMap<Person, Double> paymentSplits;
    private final Person createdBy;
    private final Date creationDate;
    private final Date editDate;
    private Event eventType;

    public Ticket(Person createdBy, Event eventType) {
        this.createdBy = createdBy;
        this.eventType = eventType;

        //todo - how to do the payment splitting

        Date tempTodaysDate = new Date().getTodaysDate();
        this.creationDate = tempTodaysDate;
        this.editDate = tempTodaysDate;
    }
}
