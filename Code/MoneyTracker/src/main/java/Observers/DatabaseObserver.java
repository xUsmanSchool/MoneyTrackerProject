package Observers;

import java.util.Observable;
import java.util.Observer;

public class DatabaseObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("OBSERVED: PERSONS DB HAS BEEN UPDATED");
    }
}
