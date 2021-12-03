package View.frames;

import Database.*;
import DatabaseController.*;
import View.others.CustomColors;
import View.panels.AddUserWindow.*;
import ViewController.*;
import ViewController.AddUserWindow.UserCreationPanelController;
import ViewController.AddUserWindow.UserListPanelController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainViewFrame extends JFrame {
    public MainViewFrame(String title) {
        super(title);
    }

    public void initialize() {
        this.setSize(720, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout
        BorderLayout experimentLayout = new BorderLayout();
        this.setLayout(experimentLayout);

        // Get database instances
        PersonsDB personsDB = PersonsDB.getInstance();
        TicketsDB ticketsDB = TicketsDB.getInstance();

        // Create database controllers
        PersonsDBController personDatabaseController = new PersonsDBController(personsDB);
        TicketsDBController ticketDatabaseControllers = new TicketsDBController(ticketsDB);

        // create panel: userListPanel
        UserListPanel userListPanel = new UserListPanel();
        ViewController userListPanelController = new UserListPanelController(personDatabaseController, userListPanel);
        userListPanelController.init();
        userListPanelController.activateActionListeners();

        // extra styling for userListPanel
        userListPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 15));
        userListPanel.getTitleLabel().setForeground(Color.WHITE);
        userListPanel.getTitleLabel().setFont(new Font("", Font.PLAIN, 16));
        userListPanel.getList().setBackground(CustomColors.getDarkGrey());
        userListPanel.setBackground(CustomColors.getMidGrey());

        // create panel: userCreationPanel
        UserCreationPanel userCreationPanel = new UserCreationPanel();
        ViewController userCreationPanelController = new UserCreationPanelController(personDatabaseController, userCreationPanel);
        userCreationPanelController.init();
        userCreationPanelController.activateActionListeners();

        // extra styling for userCreationPanel
        userCreationPanel.setBackground(CustomColors.getMidGrey());
        userCreationPanel.getFirstNameLabel().setForeground(Color.WHITE);
        userCreationPanel.getLastNameLabel().setForeground(Color.WHITE);
        userCreationPanel.getPhoneNumberLabel().setForeground(Color.WHITE);
        userCreationPanel.getGenderLabel().setForeground(Color.WHITE);
        userCreationPanel.getBirthdateLabel().setForeground(Color.WHITE);
        userCreationPanel.getCreateButton().setBackground(CustomColors.getYellow());
        userCreationPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        // add observers
        personsDB.addObserver(userListPanelController);
        personsDB.addObserver(userCreationPanelController);

        this.getContentPane().setBackground(CustomColors.getMidGrey());

        // create panels
        CenteredUserCreationPanel centeredUserCreationPanel = new CenteredUserCreationPanel(userCreationPanel);
        CombineJPanelGridLayoutPanel combinedUserList_and_userCreationPanel = new CombineJPanelGridLayoutPanel(centeredUserCreationPanel, userListPanel);
        CombineBannerPanel combinedWithBanner = new CombineBannerPanel(combinedUserList_and_userCreationPanel);
        FinalJLayeredPane finalJLayeredPane = new FinalJLayeredPane(combinedWithBanner);

        this.add(finalJLayeredPane);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent evt) {
                Component c = (Component)evt.getSource();
                combinedWithBanner.setBounds(0, 0, c.getWidth(), c.getHeight());
            }
        });
    }
}
