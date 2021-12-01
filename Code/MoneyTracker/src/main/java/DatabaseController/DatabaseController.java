package DatabaseController;

import Database.DatabaseItem;
import java.util.ArrayList;

public interface DatabaseController<T extends DatabaseItem> {
    void add(T person);
    void remove(T person);
    ArrayList<T> getAll();
}
