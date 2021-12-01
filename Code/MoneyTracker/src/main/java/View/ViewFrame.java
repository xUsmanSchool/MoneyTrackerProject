package View;

import Database.PersonsDB;
import DatabaseController.PersonRegistrationDBController;
import View.panels.*;
import View.viewController.*;
import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
    public ViewFrame() {}

    public void initialize() {
        this.setSize(720, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridLayout experimentLayout = new GridLayout(0,2);
        this.setLayout(experimentLayout);

        // Database
        PersonsDB personsDB = PersonsDB.getInstance();
        PersonRegistrationDBController personRegistrationDBController = new PersonRegistrationDBController(personsDB);

        // userListPanel
        UserListPanel userListPanel = new UserListPanel();
        vController userListPanelController = new UserListPanelController(personRegistrationDBController, userListPanel);
        userListPanelController.init();
        userListPanelController.activateActionListeners();

        // userCreationPanel
        UserCreationPanel userCreationPanel = new UserCreationPanel();
        vController userCreationPanelController = new UserCreationPanelController(personRegistrationDBController, userCreationPanel);
        userCreationPanelController.init();
        userCreationPanelController.activateActionListeners();

        // added observers
        personsDB.addObserver(userListPanelController);
        personsDB.addObserver(userCreationPanelController);

        this.add(userCreationPanel);
        this.add(userListPanel);
        this.setVisible(true);
    }
}
