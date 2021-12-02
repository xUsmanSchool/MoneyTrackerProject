package View;

import Database.*;
import DatabaseController.*;
import Model.*;
import View.panels.*;
import ViewController.*;
import ViewController.AddUserWindow.UserCreationPanelController;
import ViewController.AddUserWindow.UserListPanelController;

import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
    public ViewFrame(String title) {
        super(title);
    }

    public void initialize() {
        this.setSize(720, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // optional 1: change app icon
        //this.setIconImage(new ImageIcon("src/main/icons/imageName.png").getImage());

        // optional 2: add menu bar
        /*JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("MenuOption1"), menu2 = new JMenu("MenuOption2"), menu3 = new JMenu("MenuOption3");
        menuBar.add(menu1); menuBar.add(menu2); menuBar.add(menu3);
        JMenuItem menuItem1 = new JMenuItem("menuItem1"), menuItem2 = new JMenuItem("menuItem2");
        JMenuItem menuItem3 = new JMenuItem("menuItem1"), menuItem4 = new JMenuItem("menuItem2");
        menu1.add(menuItem1); menu1.add(menuItem2);
        menu2.add(menuItem3); menu2.addSeparator(); menu2.add(menuItem4);
        this.setJMenuBar(menuBar);*/

        GridLayout experimentLayout = new GridLayout(0,2);
        this.setLayout(experimentLayout);

        // Get database instances
        PersonsDB personsDB = PersonsDB.getInstance();
        TicketsDB ticketsDB = TicketsDB.getInstance();

        // Create database controllers
        DatabaseController<Person> personDatabaseController = new PersonRegistrationDBController(personsDB);
        DatabaseController<Ticket> ticketDatabaseControllers = new TicketRegistrationDBController(ticketsDB);

        // create panel: userListPanel
        UserListPanel userListPanel = new UserListPanel();
        ViewController userListPanelController = new UserListPanelController(personDatabaseController, userListPanel);
        userListPanelController.init();
        userListPanelController.activateActionListeners();

        // create panel: userCreationPanel
        UserCreationPanel userCreationPanel = new UserCreationPanel();
        ViewController userCreationPanelController = new UserCreationPanelController(personDatabaseController, userCreationPanel);
        userCreationPanelController.init();
        userCreationPanelController.activateActionListeners();

        // add observers
        personsDB.addObserver(userListPanelController);
        personsDB.addObserver(userCreationPanelController);

        // add panels to view
        this.add(userCreationPanel);
        this.add(userListPanel);
        this.setVisible(true);
    }
}
