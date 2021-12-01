package Database;

import Model.*;
import java.util.ArrayList;
import java.util.Observable;

public abstract class Database extends Observable {
    public Database() { }

    public abstract void add(DatabaseItem item);
    public abstract void remove(DatabaseItem item);
    public abstract ArrayList<?> getAll();
}
