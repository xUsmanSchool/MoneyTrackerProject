package Database;

import Iterator.*;
import Model.Person;
import Observers.PersonDBObservableEntry;
import java.util.ArrayList;

public class PersonsDB extends Database implements Container {
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

    @Override
    public Iterator<Person> getIterator() {
        return new PersonIterator();
    }

    class PersonIterator implements Iterator<Person> {
        int index;

        @Override
        public boolean hasNext() {
            return index < personList.size();
        }

        @Override
        public Person next() {
            return this.hasNext() ? personList.get(index++) : null;
        }
    }
}


