package DatabaseController;

import Database.*;
import Model.*;
import java.util.ArrayList;

public class PersonsDBController {
    private final PersonsDB db;

    public PersonsDBController(PersonsDB db)
    {
        this.db = db;
    }

    public void add(Person person) {
        db.add(person);
        //Write to JSON file here
    }

    public void remove(Person person) {
        db.remove(person);
    }

    public ArrayList<Person> getAll() {
        return new ArrayList<>(db.getAll());
    }
}
