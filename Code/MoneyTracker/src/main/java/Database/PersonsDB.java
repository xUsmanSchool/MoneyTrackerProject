package Database;

import Iterator.*;
import Model.*;
import Observers.PersonDBObservableEntry;
import java.util.ArrayList;

public class PersonsDB extends Database  {
    private static PersonsDB databaseInstance;
    private final ArrayList<Person> personList;

    private PersonsDB() { this.personList = new ArrayList<>(); }

    public static PersonsDB getInstance(){
        if (databaseInstance == null) { databaseInstance = new PersonsDB(); }
        return databaseInstance;
    }

    @Override
    public void add(DatabaseItem item) {
        if (item instanceof Person) {
            this.personList.add((Person)item);
            setChanged();
            notifyObservers(new PersonDBObservableEntry((Person)item, true));
        } else System.err.println("PersonsDB: item does not match the correct type");
    }

    @Override
    public void remove(DatabaseItem item) {
        if (item instanceof Person) {
            this.personList.remove((Person)item);
            setChanged();
            notifyObservers(new PersonDBObservableEntry((Person)item, false));
        } else System.err.println("PersonsDB: item does not match the correct type");
    }

    @Override
    public ArrayList<Person> getAll() {
        return personList;
    }
}


