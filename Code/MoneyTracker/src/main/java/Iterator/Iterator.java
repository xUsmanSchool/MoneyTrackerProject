package Iterator;

import Database.DatabaseItem;

public interface Iterator<T> {
    boolean hasNext();
    T next();
}
