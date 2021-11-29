package Database;

import Events.Event;
import java.util.Date;
import java.util.HashMap;

public class Ticket extends DatabaseItem {
    private Person createdBy;
    private Date creationDate;
    private HashMap<Person, Double> paymentSplits;
    private Event eventType;

    public Ticket() {
        //todo
    }
}
