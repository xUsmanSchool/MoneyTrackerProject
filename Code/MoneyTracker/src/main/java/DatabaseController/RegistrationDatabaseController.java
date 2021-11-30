package DatabaseController;

import Database.*;

import java.util.ArrayList;

public class RegistrationDatabaseController implements DatabaseController {
    private final PersonsDB db;

    public RegistrationDatabaseController(PersonsDB db)
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
        return db.getAll();
    }
}
