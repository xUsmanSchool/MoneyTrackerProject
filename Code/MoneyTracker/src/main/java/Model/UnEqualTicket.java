package Model;

import Events.Event;
import HelperClass.SplitType;
import java.util.ArrayList;

public class UnEqualTicket extends Ticket {
    public UnEqualTicket(Person payedBy, Double totalSum, Event eventType) {
        super(payedBy, totalSum, eventType, SplitType.UNEQUAL);
    }

    @Override
    public void autoCalculate(ArrayList<Person> personArrayList) {
        if (personArrayList != null) setPersonArrayList(personArrayList);
        // autoCalculate cannot be applied to this type of ticket
    }

    @Override
    public void addSCashSplit(Person person, Double amount) {
        if (person.equals(getPayedByValue())) super.addSCashSplit(person, amount);
        else super.addSCashSplit(person, amount*-1);
    }

    @Override
    public void addPercentageSplit(Person person, Double amount) {
        if (person.equals(getPayedByValue())) super.addPercentageSplit(person, getTotalSum()-getTotalSum()*amount);
        else super.addPercentageSplit(person, getTotalSum()*amount);
    }

    @Override
    public void setIcon(String iconUrl) {
        this.icon.put(getIconKey(), iconUrl);
        updateAccountEditDate();
    }
}
