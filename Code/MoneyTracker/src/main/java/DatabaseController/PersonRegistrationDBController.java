package DatabaseController;

import Database.*;
import Model.Person;

import java.util.ArrayList;

public class PersonRegistrationDBController {
    private final PersonsDB db;

    public PersonRegistrationDBController(PersonsDB db)
    {
        this.db = db;
    }

    public void add(Person person) {
        db.add(person);
    }

    public void remove(Person person) {
        db.remove(person);
    }

    public ArrayList<Person> getAll() {
        return new ArrayList<>(db.getAll());
    }
}
