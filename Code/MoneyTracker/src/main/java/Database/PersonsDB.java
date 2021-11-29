package Database;

import Iterator.Container;
import Iterator.Iterator;
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
            notifyObservers();
        } else System.err.println("PersonsDB: item does not match the correct type");
    }

    @Override
    public void remove(DatabaseItem item) {
        if (item instanceof Person) {
            this.personList.remove((Person)item);
            setChanged();
            notifyObservers();
        } else System.err.println("PersonsDB: item does not match the correct type");
    }

    @Override
    public ArrayList<Person> getAll() {
        return personList;
    }

    @Override
    public Iterator getIterator() {
        return new PersonIterator();
    }

    class PersonIterator implements Iterator {
        int index;

        @Override
        public boolean hasNext() {
            return index < personList.size();
        }

        @Override
        public Object next() {
            return this.hasNext() ? personList.get(index++) : null;
        }
    }
}


