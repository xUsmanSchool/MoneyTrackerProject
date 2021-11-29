package Controller;

import Database.*;

public class RegistrationController implements Controller {
    private final Database db;

    public RegistrationController(Database db)
    {
        this.db = db;
    }

    @Override
    public void register(Person person) {
        db.add(person);
    }

    @Override
    public void remove(Person person) {
        db.remove(person);
    }
}
