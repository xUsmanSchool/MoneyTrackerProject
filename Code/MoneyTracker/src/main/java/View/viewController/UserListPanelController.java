package View.viewController;

import DatabaseController.DatabaseController;
import View.panels.UserListPanel;

public class UserListPanelController extends vController {
    private final DatabaseController databaseController;
    private final UserListPanel userListPanel;

    public UserListPanelController(DatabaseController databaseController, UserListPanel userListPanel) {
        this.userListPanel = userListPanel;
        this.databaseController = databaseController;
    }

    @Override
    public void init() {
        this.userListPanel.setTitle("Existing user list");
        this.userListPanel.addPersonToListModel(databaseController.getAll());
    }

    @Override
    public void activateActionListeners() {
        // none
    }
}
