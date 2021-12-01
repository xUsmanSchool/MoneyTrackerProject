package Observers;

import Database.PersonsDB;
import HelperClass.PrintInfo;

import java.util.Observable;
import java.util.Observer;

public class DatabaseObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof PersonDBObservableEntry) {
            PersonDBObservableEntry p = (PersonDBObservableEntry)arg;
            System.out.println("OBSERVED: PERSON DB HAS BEEN UPDATED. " +
                    p.getPerson().getFirstNameValue() + " " + p.getPerson().getLastNameValue() +
                    (p.isAdded() ? " got added. " : " was removed. ") +
                    "Now there are " + PersonsDB.getInstance().getAll().size() + " users in the database.");
            PrintInfo.printPersonInfo(p.getPerson());
        } else if (arg instanceof TicketDBObservableEntry) {
            TicketDBObservableEntry t = (TicketDBObservableEntry)arg;
            System.out.println("OBSERVED: TICKET DB HAS BEEN UPDATED. ");
            PrintInfo.printTicketInfo(t.getTicket());
        } else System.out.println("OBSERVED: UNKNOWN DB HAS BEEN UPDATED.");
    }
}
