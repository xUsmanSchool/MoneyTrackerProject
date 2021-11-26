package Database;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonsDB extends Database {
    private static PersonsDB databaseInstance;
    private final ArrayList<Person> personList;

    private PersonsDB() { this.personList = new ArrayList<>(); }

    public static PersonsDB getInstance(){
        if (databaseInstance == null) { databaseInstance = new PersonsDB(); }
        return databaseInstance;
    }

    public void add(Person person) {

        this.personList.add(person);
        setChanged();
        notifyObservers();
    }

    public void remove(Person person) {
        this.personList.remove(person);
        setChanged();
        notifyObservers();
    }

    public ArrayList<Person> getPersons() {
        return personList;
    }
}
