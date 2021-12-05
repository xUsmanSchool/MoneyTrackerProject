package DatabaseController;

import Database.*;
import HelperClass.WriteToJSONFile;
import Model.*;
import java.util.ArrayList;

public class PersonsDBController {
    private final PersonsDB db;

    public PersonsDBController(PersonsDB db)
    {
        this.db = db;
    }

    public void add(Person person) {
        this.db.add(person);
        WriteToJSONFile.updatePersonFile(this.db);
    }

    public void remove(Person person) {
        db.remove(person);
        WriteToJSONFile.updatePersonFile(this.db);
    }

    public ArrayList<Person> getAll() {
        return new ArrayList<>(db.getAll());
    }
}
