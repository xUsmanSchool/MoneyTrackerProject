package View.viewController;

import java.util.Observer;

public abstract class vController implements Observer {
    public abstract void init();
    public abstract void activateActionListeners();
}
