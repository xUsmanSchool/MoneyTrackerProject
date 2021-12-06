package View.frames;

import Database.*;
import DatabaseController.*;
import View.others.CustomColors;
import View.panels.AddUserWindow.*;
import View.panels.RecentTickets.RecentTicketsPanel;
import ViewController.*;
import ViewController.AddUserWindow.UserCreationPanelController;
import ViewController.AddUserWindow.UserListPanelController;
import ViewController.RecentTickets.RecentTicketPanelController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainViewFrame extends JFrame {
    private FinalJLayeredPane finalUserCreationPanel, finalRecentTicketPanel;

    public MainViewFrame(String title) {
        super(title);
    }

    public void initialize() {
        this.setSize(735, 515);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Set layout
        BorderLayout experimentLayout = new BorderLayout();
        this.setLayout(experimentLayout);

        // Get database instances
        PersonsDB personsDB = PersonsDB.getInstance();
        TicketsDB ticketsDB = TicketsDB.getInstance();

        // Create database controllers
        PersonsDBController personDatabaseController = new PersonsDBController(personsDB);
        TicketsDBController ticketDatabaseController = new TicketsDBController(ticketsDB);

        // add extra styling
        this.getContentPane().setBackground(CustomColors.getMidGrey());


        /////////////////////////////////////////// USER CREATION PANEL ////////////////////////////////////////////////
        // Sub panel 1
        UserCreationPanel userCreationPanel = new UserCreationPanel();
        ViewController userCreationPanelController = new UserCreationPanelController(personDatabaseController, userCreationPanel);
        userCreationPanelController.init();
        userCreationPanelController.activateActionListeners();

        // Sub panel 2
        UserListPanel userListPanel_UserCreationPanel = new UserListPanel();
        ViewController userListPanelController_UserCreationPanel = new UserListPanelController(personDatabaseController, userListPanel_UserCreationPanel);
        userListPanelController_UserCreationPanel.init();
        userListPanelController_UserCreationPanel.activateActionListeners();

        // Combine sub-panels
        AlignPanelSouth alignPanelSouth_UserCreationPanel = new AlignPanelSouth(userListPanel_UserCreationPanel);
        AlignPanelCenter alignPanelCenter_UserCreationPanel = new AlignPanelCenter(userCreationPanel);
        CombineJPanelGridLayoutPanel combinedUserList_and_userCreationPanel = new CombineJPanelGridLayoutPanel(alignPanelCenter_UserCreationPanel, alignPanelSouth_UserCreationPanel);
        CombineBannerPanel combinedUserList_and_userCreationPanelWithBanner = new CombineBannerPanel(combinedUserList_and_userCreationPanel);


        ////////////////////////////////////////// RECENT TICKETS PANEL ////////////////////////////////////////////////
        // Sub panel 1
        RecentTicketsPanel recentTicketsPanel = new RecentTicketsPanel();
        RecentTicketPanelController recentTicketPanelController = new RecentTicketPanelController(ticketDatabaseController, recentTicketsPanel);
        recentTicketPanelController.init();
        recentTicketPanelController.activateActionListeners();

        // Sub panel 2
        UserListPanel userListPanel_RecentTicketsPanel = new UserListPanel();
        UserListPanelController userListPanelController_RecentTicketsPanel = new UserListPanelController(personDatabaseController, userListPanel_RecentTicketsPanel);
        userListPanelController_RecentTicketsPanel.init();
        userListPanelController_RecentTicketsPanel.activateActionListeners();

        // Combine panels
        AlignPanelSouth alignPanelSouth_RecentTicketsPanel = new AlignPanelSouth(userListPanel_RecentTicketsPanel);
        CombineJPanelGridLayoutPanel combinedRecentTicket_and_userCreationPanel = new CombineJPanelGridLayoutPanel(recentTicketsPanel, alignPanelSouth_RecentTicketsPanel);
        CombineBannerPanel combinedRecentTicketUserCreationWithBanner = new CombineBannerPanel(combinedRecentTicket_and_userCreationPanel);


        /////////////////////////////////////// PANEL SWITCHING LISTENERS //////////////////////////////////////////////
        recentTicketsPanel.getAddTicketButton().addActionListener(e -> goToUserCreationPanelActionListener(combinedUserList_and_userCreationPanelWithBanner));
        userCreationPanel.getGotoGlobalBillButton().addActionListener(e -> goToRecentTicketsActionListener(combinedRecentTicketUserCreationWithBanner));

        // add observers
        personsDB.addObserver(userListPanelController_UserCreationPanel);
        personsDB.addObserver(userListPanelController_RecentTicketsPanel);
        personsDB.addObserver(userCreationPanelController);

        // start with user creation panel
        goToUserCreationPanelActionListener(combinedUserList_and_userCreationPanelWithBanner);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                //Component c = (Component)evt.getSource();
                //combinedSomePanelWithBanner.setBounds(0, 0, c.getWidth(), c.getHeight());
                //finalJLayeredPaneWithSomePanel.setBoundsMainIcon(35, 35, combinedSomePanelWithBanner.getBanner().getHeight(), combinedSomePanelWithBanner.getBanner().getHeight());
                //finalJLayeredPaneWithSomePanel.setBoundsMainLabel(45, 30, 200, 50);
            }
        });
    }

    private void goToUserCreationPanelActionListener(JPanel panel) {
        if (finalRecentTicketPanel != null) {
            this.remove(finalRecentTicketPanel);
            finalRecentTicketPanel = null;
        }

        finalUserCreationPanel = new FinalJLayeredPane(panel);
        this.add(finalUserCreationPanel);

        this.validate();
        this.repaint();
    }

    private void goToRecentTicketsActionListener(JPanel panel) {
        if (finalUserCreationPanel != null) {
            this.remove(finalUserCreationPanel);
            finalUserCreationPanel = null;
        }

        finalRecentTicketPanel = new FinalJLayeredPane(panel);
        this.add(finalRecentTicketPanel);

        this.validate();
        this.repaint();
    }
}
