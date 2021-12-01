package Model;

import Events.Event;
import HelperClass.SplitType;
import java.util.ArrayList;

public class EqualTicket extends Ticket {
    public EqualTicket(Person createdBy, Event eventType) {
        super(createdBy, eventType, SplitType.EQUAL);
    }

    @Override
    public void autoCalculate(ArrayList<Person> personArrayList) {
        if (addPayByWarning(getPayedAmount())) return;

        double ea = getPayedAmount() / personArrayList.size();
        personArrayList.remove(getPayedBy());
        for (Person p : personArrayList) addSCashSplit(p, ea*(-1));
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

    @Override
    public void setIcon(String iconUrl) {
        this.icon.put(getIconKey(), iconUrl);
        updateAccountEditDate();
    }
}
