package DatabaseController;

import Database.*;
import Model.*;
import java.util.ArrayList;

public class PersonRegistrationDBController implements DatabaseController<Person> {
    private final PersonsDB db;

    public PersonRegistrationDBController(PersonsDB db)
    {
        this.db = db;
    }

    @Override
    public void add(Person person) {
        db.add(person);
    }

    @Override
    public void remove(Person person) {
        db.remove(person);
    }

    @Override
    public ArrayList<Person> getAll() {
        return new ArrayList<>(db.getAll());
    }
}
