package ViewController;

import java.util.Observer;

public abstract class ViewController implements Observer {
    public abstract void init();
    public abstract void activateActionListeners();
}
