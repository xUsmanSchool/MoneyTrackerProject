package View.frames;

import Database.*;
import DatabaseController.*;
import View.others.CustomColors;
import View.panels.AddTicketsPanel.AddTicketsPanel;
import View.panels.AddUserWindow.*;
import View.panels.RecentTickets.RecentTicketsPanel;
import ViewController.*;
import ViewController.AddTicketsPanel.AddTicketsViewController;
import ViewController.AddUserWindow.UserCreationPanelController;
import ViewController.AddUserWindow.UserListPanelController;
import ViewController.RecentTickets.RecentTicketPanelController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainViewFrame extends JFrame {
    private FinalJLayeredPane finalUserCreationPanel, finalRecentTicketPanel;
    private JPanel activePanel;

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

        // Combine panels
        AlignPanelSouth alignPanelSouth_RecentTicketsPanel = new AlignPanelSouth(userListPanel_RecentTicketsPanel);
        CombineJPanelGridLayoutPanel combinedRecentTicket_and_userCreationPanel = new CombineJPanelGridLayoutPanel(recentTicketsPanel, alignPanelSouth_RecentTicketsPanel);
        CombineBannerPanel combinedRecentTicketUserCreationWithBanner = new CombineBannerPanel(combinedRecentTicket_and_userCreationPanel);

        //////////////////////////////////////////// ADD TICKET PANEL //////////////////////////////////////////////////
        AddTicketsPanel addTicketsPanel = new AddTicketsPanel();
        AddTicketsViewController addTicketsViewController = new AddTicketsViewController(personDatabaseController, ticketDatabaseController, addTicketsPanel);
        addTicketsViewController.init();
        addTicketsViewController.activateActionListeners();

        ////////////////////////////////////////// SPLIT SELECTION PANEL ///////////////////////////////////////////////
        // todo

        /////////////////////////////////////////// GLOBAL BILL PANEL //////////////////////////////////////////////////
        // todo

        /////////////////////////////////////// PANEL SWITCHING LISTENERS //////////////////////////////////////////////
        userCreationPanel.getGotoGlobalBillButton().addActionListener(e -> goToRecentTicketsActionListener(combinedRecentTicketUserCreationWithBanner));
        recentTicketsPanel.getAddTicketButton().addActionListener(e -> goToTicketCreationPanelActionListener(addTicketsPanel));
        recentTicketsPanel.getCheckoutButton().addActionListener(e -> goToUserCreationPanelActionListener(combinedUserList_and_userCreationPanelWithBanner));

        // add observers
        personsDB.addObserver(userListPanelController_UserCreationPanel);
        personsDB.addObserver(userListPanelController_RecentTicketsPanel);
        personsDB.addObserver(userCreationPanelController);

        // start with user creation panel
        goToUserCreationPanelActionListener(combinedUserList_and_userCreationPanelWithBanner);

        // todo - temp testing new panel
        //this.add(addTicketsPanel);

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

    private void goToTicketCreationPanelActionListener(AddTicketsPanel panel) {
        if (finalRecentTicketPanel != null) {
            this.remove(finalRecentTicketPanel);
            finalRecentTicketPanel = null;
        }

        // create new panel
        AlignPanelCenter p = new AlignPanelCenter(panel);

        // get panel info
        int frameWidth = this.getWidth();
        int frameHeight = this.getHeight();
        int panelWidth = panel.getPreferredSize().width;
        int panelHeight = panel.getPreferredSize().height;

        // change this if needed, value 0->1
        double leftRightPercentage = 0.40;
        double topDownPercentage = 0.65;

        // do not change
        int frameMarginLeftRight = (int) Math.floor((((double) frameWidth - panelWidth) / 2) * leftRightPercentage);
        int frameMarginUpDown = (int) Math.floor((((double) frameHeight - panelHeight) / 2) * topDownPercentage);

        panel.setBorder(BorderFactory.createEmptyBorder(frameMarginUpDown, 50, frameMarginUpDown, 50));
        p.setBorder(BorderFactory.createEmptyBorder(0, frameMarginLeftRight, 0, frameMarginLeftRight));
        this.add(p);

        this.validate();
        this.repaint();
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
