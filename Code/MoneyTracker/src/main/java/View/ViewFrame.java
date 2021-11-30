package View;

import Database.PersonsDB;
import DatabaseController.RegistrationDatabaseController;
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
        RegistrationDatabaseController registrationDatabaseController = new RegistrationDatabaseController(personsDB);

        // userListPanel
        UserListPanel userListPanel = new UserListPanel();
        vController userListPanelController = new UserListPanelController(registrationDatabaseController, userListPanel);
        userListPanelController.init();
        userListPanelController.activateActionListeners();

        // userCreationPanel
        UserCreationPanel userCreationPanel = new UserCreationPanel();
        vController userCreationPanelController = new UserCreationPanelController(registrationDatabaseController, userCreationPanel);
        userCreationPanelController.init();
        userCreationPanelController.activateActionListeners();

        // testPanel
        testPanel2 testPanel = new testPanel2(userCreationPanel, new testButtonPanel());

        this.add(testPanel);
        //this.add(userCreationPanel);
        this.add(userListPanel);
        this.setVisible(true);
    }
}
