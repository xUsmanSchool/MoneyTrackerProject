package DatabaseController;

import Database.*;

import java.util.ArrayList;

public interface DatabaseController {
    void add(Person person);
    void remove(Person person);
    ArrayList<Person> getAll();
}
