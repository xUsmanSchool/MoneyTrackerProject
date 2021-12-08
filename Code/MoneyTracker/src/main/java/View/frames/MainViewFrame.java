package View.frames;

import Database.*;
import DatabaseController.*;
import View.others.CustomColors;
import View.others.Router;
import View.others.TicketPanelAction;
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
import java.awt.event.*;

public class MainViewFrame extends JFrame {
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

        // Add extra styling
        this.getContentPane().setBackground(CustomColors.getMidGrey());

        // Router
        Router router = Router.getInstance();
        router.setFrame(this);


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
        AlignPanelSouth alignPanelSouth_UserListPanel = new AlignPanelSouth(userListPanel_UserCreationPanel);
        AlignPanelCenter alignPanelCenter_UserCreationPanel = new AlignPanelCenter(userCreationPanel);
        CombineJPanelGridLayoutPanel combinedUserList_and_userCreationPanel = new CombineJPanelGridLayoutPanel(alignPanelCenter_UserCreationPanel, alignPanelSouth_UserListPanel);
        CombineBannerPanel combinedUserList_and_userCreationPanelWithBanner = new CombineBannerPanel(combinedUserList_and_userCreationPanel);

        FinalJLayeredPane finalUserCreationPanel = new FinalJLayeredPane(combinedUserList_and_userCreationPanelWithBanner);


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

        FinalJLayeredPane finalRecentTicketPanel = new FinalJLayeredPane(combinedRecentTicketUserCreationWithBanner);


        //////////////////////////////////////////// ADD TICKET PANEL //////////////////////////////////////////////////
        // Panel 1
        AddTicketsPanel addTicketsPanel = new AddTicketsPanel();
        AddTicketsViewController addTicketsViewController = new AddTicketsViewController(personDatabaseController, ticketDatabaseController, addTicketsPanel);
        addTicketsViewController.init();
        addTicketsViewController.activateActionListeners();

        // Center panel
        AlignPanelCenter singleAlignedPanelCenter = new AlignPanelCenter(addTicketsPanel);
        //doubleAlignedTicketCreationPanel = new AlignPanelCenter(singleAlignedPanelCenter);    // possible UI consideration
        //doubleAlignedPanelCenter.setBackground(CustomColors.getYellow());                     // possible UI consideration

        // Activate keybindings
        InputMap inMap = singleAlignedPanelCenter.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clearOrGoBack");
        singleAlignedPanelCenter.getActionMap().put("clearOrGoBack", new TicketPanelAction(addTicketsPanel));

        // get panel info
        int frameWidth = this.getWidth();
        int frameHeight = this.getHeight();
        int panelWidth = addTicketsPanel.getPreferredSize().width;
        int panelHeight = addTicketsPanel.getPreferredSize().height;

        // change this if needed, value 0->1
        double leftRightPercentage = 0.40;
        double topDownPercentage = 0.65;

        // do not change
        int frameMarginLeftRight = (int) Math.floor((((double) frameWidth - panelWidth) / 2) * leftRightPercentage);
        int frameMarginUpDown = (int) Math.floor((((double) frameHeight - panelHeight) / 2) * topDownPercentage);

        addTicketsPanel.setBorder(BorderFactory.createEmptyBorder(frameMarginUpDown, 50, frameMarginUpDown, 50));
        singleAlignedPanelCenter.setBorder(BorderFactory.createEmptyBorder(0, frameMarginLeftRight, 0, frameMarginLeftRight));


        ////////////////////////////////////////// SPLIT SELECTION PANEL ///////////////////////////////////////////////
        // todo


        /////////////////////////////////////////// GLOBAL BILL PANEL //////////////////////////////////////////////////
        // todo


        /////////////////////////////////////// PANEL SWITCHING LISTENERS //////////////////////////////////////////////
        userCreationPanel.getGotoGlobalBillButton().addActionListener(e -> router.gotToPanel(finalRecentTicketPanel));
        recentTicketsPanel.getAddTicketButton().addActionListener(e -> router.gotToPanel(singleAlignedPanelCenter, addTicketsPanel.getDescriptionTextField()));
        recentTicketsPanel.getCheckoutButton().addActionListener(e -> router.goBack()); // todo - temp

        // add observers
        personsDB.addObserver(userListPanelController_UserCreationPanel);   // pop up in the list when user is created
        personsDB.addObserver(userListPanelController_RecentTicketsPanel);  // update list when panel is switched
        personsDB.addObserver(userCreationPanelController);                 // reset form and receive updated Icon
        ticketsDB.addObserver(recentTicketPanelController);                 // adds a new ticket in the list
        ticketsDB.addObserver(addTicketsViewController);                    // reset form and stuff

        // start with user creation panel
        // focus doesn't work for some reason
        router.gotToPanel(finalUserCreationPanel, userCreationPanel.getFirstNameTextField());

        this.setLocationRelativeTo(null);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                //Component c = (Component)evt.getSource();
                //combinedSomePanelWithBanner.setBounds(0, 0, c.getWidth(), c.getHeight());
                //finalJLayeredPaneWithSomePanel.setBoundsMainIcon(35, 35, combinedSomePanelWithBanner.getBanner().getHeight(), combinedSomePanelWithBanner.getBanner().getHeight());
                //finalJLayeredPaneWithSomePanel.setBoundsMainLabel(45, 30, 200, 50);
            }
        });
    }
}
