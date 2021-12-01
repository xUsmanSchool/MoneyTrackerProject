package model;

import Events.Event;
import HelperClass.SplitType;
import java.util.ArrayList;

public class UnEqualTicket extends Ticket {
    public UnEqualTicket(Person createdBy, Event eventType) {
        super(createdBy, eventType, SplitType.UNEQUAL);
    }

    @Override
    public void autoCalculate(ArrayList<Person> personArrayList) {
        System.err.println("Output is ignored, autoCalculate cannot be applied to this type of ticket");
    }

    @Override
    public void addSCashSplit(Person person, Double amount) {
        if (addPayByWarning(getPayedAmount())) return;

        super.addSCashSplit(person, amount);
    }

    private boolean addPayByWarning(Double payedAmount) {
        if (payedAmount == null) System.err.println("Output is ignored, please add 'addPayedBy' first.");
        return payedAmount == null;
    }
}
