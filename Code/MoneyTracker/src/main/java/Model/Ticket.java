package Model;

import Events.*;
import HelperClass.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Ticket extends DatabaseItem {
    private final HashMep<String, HashMap<Person, Double>> paymentSplits;
    private final HashMep<String, Event>  eventType;
    private final HashMep<String, SplitType> splitType;
    private final HashMep<String, Person> payedBy;
    private final Double totalSum;

    public Ticket(Person payedBy, Double totalSum, Event forEventType, SplitType splitType) {
        // init
        this.paymentSplits = new HashMep<>();
        this.eventType = new HashMep<>();
        this.splitType = new HashMep<>();
        this.payedBy = new HashMep<>();
        this.totalSum = totalSum;

        // get dates
        LocalDate tempTodaysDate = Date.getTodaysDate();

        // fill in values
        this.paymentSplits.put("Payment_splits", new HashMap<>());
        this.eventType.put("EventType", forEventType);
        this.splitType.put("SplitType", splitType);
        this.payedBy.put("Payed_by", payedBy);
        this.creationDate.put("Account_creation_date", tempTodaysDate);
        this.editDate.put("Account_edit_date", tempTodaysDate);
        this.icon.put("Icon_url", forEventType.getIcon());
    }

    public void addPercentageSplit(Person person, Double amount) {
        addSCashSplit(person, amount);
    }

    // will be overridden
    public void addSCashSplit(Person person, Double amount) {
        updatePaymentSplitsHashMap(paymentSplits.getKey(), person, amount);
        updateAccountEditDate();
    }

    // will be overridden
    public abstract void autoCalculate(ArrayList<Person> personArrayList);

    public String getPersonArrayListKey() {
        return paymentSplits.getKey();
    }

    public ArrayList<Person> getPersonArrayList() {
        return new ArrayList<Person>(paymentSplits.getValue().keySet());
    }

    public void setPersonArrayList(ArrayList<Person> personArrayList) {
        for (Person person : personArrayList) updatePaymentSplitsHashMap(paymentSplits.getKey(), person, null);
    }

    public Double getAmountForPerson(Person person) {
        return paymentSplits.getValue().get(person);
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public String getPayedByKey() {
        return payedBy.getKey();
    }
    public Person getPayedByValue() {
        return payedBy.getValue();
    }

    public String getSplitTypeKey() { return splitType.getKey(); }
    public SplitType getSplitTypeValue() { return splitType.getValue(); }

    public String getEventTypeKey() { return eventType.getKey(); }
    public Event getEventTypeValue() { return eventType.getValue(); }

    @Override
    protected void updateAccountEditDate() {
        this.creationDate.put(creationDate.getKey(), Date.getTodaysDate());
    }

    private void updatePaymentSplitsHashMap(String outerKey, Person innerKey, Double innerValue) {
        HashMap<Person, Double> innerMap = paymentSplits.getValue();
        innerMap.put(innerKey, innerValue);
        paymentSplits.put(outerKey, innerMap);
    }
}
