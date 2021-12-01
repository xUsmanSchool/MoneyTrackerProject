package Observers;

import Database.PersonsDB;
import java.util.Observable;
import java.util.Observer;

public class DatabaseObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        PersonDBObservableEntry p = (PersonDBObservableEntry)arg;
        System.out.println("OBSERVED: DB HAS BEEN UPDATED. " +
                p.getPerson().getFirstNameValue() + " " + p.getPerson().getLastNameValue() +
                (p.isAdded() ? " got added. " : " was removed. ") +
                "Now there are " + PersonsDB.getInstance().getAll().size() + " users in the database."
        );
    }
}
