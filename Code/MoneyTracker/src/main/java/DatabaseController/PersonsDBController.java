package DatabaseController;

import Database.*;
import HelperClass.WriteToJSONFile;
import Iterator.Container;
import Iterator.Iterator;
import Model.*;
import java.util.ArrayList;

public class PersonsDBController implements Container {
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

    @Override
    public Iterator<Person> getIterator() {
        return new PersonIterator();
    }

    class PersonIterator implements Iterator<Person> {
        int index;

        @Override
        public boolean hasNext() {
            return index < getAll().size();
        }

        @Override
        public Person next() {
            return this.hasNext() ? getAll().get(index++) : null;
        }
    }
}
