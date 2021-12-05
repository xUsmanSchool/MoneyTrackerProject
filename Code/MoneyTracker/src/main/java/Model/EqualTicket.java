package Model;

import Events.Event;
import HelperClass.SplitType;
import java.util.ArrayList;

public class EqualTicket extends Ticket {
    public EqualTicket(Person payedBy, Double totalSum, Event eventType) {
        super(payedBy, totalSum, eventType, SplitType.EQUAL);
    }

    public void autoCalculate(ArrayList<Person> personArrayList) {
        double ea = getTotalSum() / (personArrayList.size());
        for (Person p : personArrayList) calcSplit(p, p == getPayedByValue() ? getTotalSum()-ea : -1*ea);
    }

    @Override
    public void addSCashSplit(Person person, Double amount) {
        // no need to use this method when its already being auto-calculated
    }

    @Override
    public void addPercentageSplit(Person person, Double amount) {
        // no need to use this method when its already being auto-calculated
    }

    private void calcSplit(Person person, Double amount) {
        super.addSCashSplit(person, amount);
    }

    @Override
    public void setIcon(String iconUrl) {
        this.icon.put(getIconKey(), iconUrl);
        updateAccountEditDate();
    }
}
