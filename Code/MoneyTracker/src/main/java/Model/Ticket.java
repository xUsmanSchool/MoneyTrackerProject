package Model;

import Events.*;
import HelperClass.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Ticket extends DatabaseItem {
    private final HashMep<String, HashMap<Person, Double>> paymentSplits;
    private final HashMep<String, Person> createdBy;
    private final HashMep<String, Event>  eventType;
    private final HashMep<String, SplitType> splitType;

    // for quick access
    private Person payedBy;
    private Double payedAmount;

    public Ticket(Person createdBy, Event eventType, SplitType splitType) {
        // init
        this.payedAmount = null;
        this.payedBy = null;
        this.createdBy = new HashMep<>();
        this.eventType = new HashMep<>();
        this.splitType = new HashMep<>();
        this.paymentSplits = new HashMep<>();

        // get dates
        Date tempTodaysDate = new Date().getTodaysDate();

        // fill in values
        this.createdBy.put("Created_by", createdBy);
        this.eventType.put("EventType", eventType);
        this.splitType.put("SplitType", splitType);
        this.paymentSplits.put("Payment_splits", new HashMap<>());
        this.creationDate.put("Account_creation_date", tempTodaysDate);
        this.editDate.put("Account_edit_date", tempTodaysDate);
        this.icon.put("Icon_url", "");
    }

    public void addPayedBy(Person person, Double amount) {
        if (payedBy == null) {
            payedBy = person;
            payedAmount = amount;
            addSCashSplit(person, amount);
            updateAccountEditDate();
        }
    }

    public void addPercentageSplit(Person person, Double percentage) {
        addSCashSplit(person, payedAmount*percentage*(-1));
        updateAccountEditDate();
    }

    // will be overridden
    public void addSCashSplit(Person person, Double amount) {
        updatePaymentSplitsHashMap(paymentSplits.getKey(), person, amount);
        updateAccountEditDate();
    }

    // will be overridden
    public void autoCalculate(ArrayList<Person> personArrayList) {
        //
    };

    public String getPersonArrayListKey() {
        return paymentSplits.getKey();
    }

    public ArrayList<Person> getPersonArrayList() {
        return new ArrayList<Person>(paymentSplits.getValue().keySet());
    }

    public Double getAmountForPerson(Person person) {
        return paymentSplits.getValue().get(person);
    }

    public Double getPayedAmount() {
        return payedAmount;
    }

    public Person getPayedBy() {
        return payedBy;
    }

    public String getCreatedByKey() { return createdBy.getKey(); }
    public Person getCreatedByValue() { return createdBy.getValue(); }

    public String getSplitTypeKey() { return splitType.getKey(); }
    public SplitType getSplitTypeValue() { return splitType.getValue(); }

    public String getEventTypeKey() { return eventType.getKey(); }
    public Event getEventTypeValue() { return eventType.getValue(); }

    @Override
    protected void updateAccountEditDate() {
        this.creationDate.put(creationDate.getKey(), new Date().getTodaysDate());
    }

    private void updatePaymentSplitsHashMap(String outerKey, Person innerKey, Double innerValue) {
        HashMap<Person, Double> innerMap = paymentSplits.getValue();
        innerMap.put(innerKey, innerValue);
        paymentSplits.put(outerKey, innerMap);
    }
}
