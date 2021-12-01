package Model;

import Database.DatabaseItem;
import Events.Event;
import HelperClass.Date;
import HelperClass.SplitType;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Ticket extends DatabaseItem {
    private final HashMap<Person, Double> paymentSplits;
    private final Person createdBy;
    private final Date creationDate;
    private final Date editDate;
    private final Event eventType;
    private final SplitType splitType;

    // for quick access
    private Person payedBy;
    private Double payedAmount;

    public Ticket(Person createdBy, Event eventType, SplitType splitType) {
        this.paymentSplits = new HashMap<Person, Double>();
        this.payedAmount = null;
        this.payedBy = null;

        this.createdBy = createdBy;
        this.eventType = eventType;
        this.splitType = splitType;

        Date tempTodaysDate = new Date().getTodaysDate();
        this.creationDate = tempTodaysDate;
        this.editDate = tempTodaysDate;
    }

    public void addPayedBy(Person person, Double amount) {
        if (payedBy == null) {
            payedBy = person;
            payedAmount = amount;
            addSCashSplit(person, amount);
        }
    }

    // will be overridden
    public void addSCashSplit(Person person, Double amount) {
        paymentSplits.put(person, amount);
    }

    public void addPercentageSplit(Person person, Double percentage) {
        addSCashSplit(person, payedAmount*percentage*(-1));
    }

    // will be overridden
    public void autoCalculate(ArrayList<Person> personArrayList) {};

    public ArrayList<Person> getPersonArrayList() {
        return new ArrayList<Person>(paymentSplits.keySet());
    }

    public Double getAmountForPerson(Person person) {
        return paymentSplits.get(person);
    }

    public Double getPayedAmount() {
        return payedAmount;
    }

    public Person getPayedBy() {
        return payedBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public Event getEventType() {
        return eventType;
    }

    public Person getCreatedBy() {
        return createdBy;
    }
}
