package View.frames;

import Database.*;
import DatabaseController.*;
import View.others.CustomColors;
import View.others.GlobalBillPanelAction;
import View.others.Router;
import View.others.TicketPanelAction;
import View.panels.AddTicketsPanel.AddTicketsPanel;
import View.panels.AddUserWindow.*;
import View.panels.GlobalBill.GlobalBillPanel;
import View.panels.PaymentSplits.PaymentSplitPanel;
import View.panels.RecentTickets.RecentTicketsPanel;
import ViewController.*;
import ViewController.AddTicketsPanel.AddTicketsViewController;
import ViewController.AddUserWindow.UserCreationPanelController;
import ViewController.AddUserWindow.UserListPanelController;
import ViewController.GlobalBill.GlobalBillPanelController;
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
        ViewController userListPanelController_inUserCreationPanel = new UserListPanelController(personDatabaseController, userListPanel_UserCreationPanel);
        userListPanelController_inUserCreationPanel.init();
        userListPanelController_inUserCreationPanel.activateActionListeners();

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
        UserListPanelController userListPanelController_inRecentTicketsPanel = new UserListPanelController(personDatabaseController, userListPanel_RecentTicketsPanel);
        userListPanelController_inRecentTicketsPanel.init();
        userListPanelController_inRecentTicketsPanel.disableListSelection();

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
        InputMap ticketsPanelInputMap = singleAlignedPanelCenter.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ticketsPanelInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clearOrGoBack");
        singleAlignedPanelCenter.getActionMap().put("clearOrGoBack", new TicketPanelAction(addTicketsPanel));

        // get panel info
        int frameWidth = this.getWidth();
        int frameHeight = this.getHeight();
        int panelWidth = addTicketsPanel.getPreferredSize().width;
        int panelHeight = addTicketsPanel.getPreferredSize().height;

        // change this if needed, value between 0-1
        double leftRightPercentage = 0.40;
        double topDownPercentage = 0.65;

        // do not change
        int frameMarginLeftRight = (int) Math.floor((((double) frameWidth - panelWidth) / 2) * leftRightPercentage);
        int frameMarginUpDown = (int) Math.floor((((double) frameHeight - panelHeight) / 2) * topDownPercentage);

        addTicketsPanel.setBorder(BorderFactory.createEmptyBorder(frameMarginUpDown, 50, frameMarginUpDown, 50));
        singleAlignedPanelCenter.setBorder(BorderFactory.createEmptyBorder(0, frameMarginLeftRight, 0, frameMarginLeftRight));


        ////////////////////////////////////////// SPLIT SELECTION PANEL ///////////////////////////////////////////////
        PaymentSplitPanel paymentSplitPanel = new PaymentSplitPanel();

        JPanel paymentSplitPanelWithBorder = new JPanel();
        paymentSplitPanelWithBorder.setLayout(new BoxLayout(paymentSplitPanelWithBorder, BoxLayout.Y_AXIS));
        paymentSplitPanel.setBackground(CustomColors.getMidGrey());
        paymentSplitPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        paymentSplitPanel.setBorder(BorderFactory.createEmptyBorder(40,100,40,100));
        paymentSplitPanelWithBorder.add(paymentSplitPanel);



        /////////////////////////////////////////// GLOBAL BILL PANEL //////////////////////////////////////////////////
        GlobalBillPanel globalBillPanel = new GlobalBillPanel();
        CombineBannerPanel combineBannerPanel = new CombineBannerPanel(globalBillPanel);
        FinalJLayeredPane finalJLayeredPane = new FinalJLayeredPane(combineBannerPanel);
        GlobalBillPanelController globalBillPanelController = new GlobalBillPanelController(personDatabaseController, ticketDatabaseController, globalBillPanel);
        globalBillPanelController.init();

        // Activate keybindings
        InputMap globalBillInputMap = finalJLayeredPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        globalBillInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "globalBillBack");
        finalJLayeredPane.getActionMap().put("globalBillBack", new GlobalBillPanelAction(globalBillPanel));


        /////////////////////////////////////// PANEL SWITCHING LISTENERS //////////////////////////////////////////////
        userCreationPanel.getGotoGlobalBillButton().addActionListener(e -> router.gotToPanel(finalRecentTicketPanel));
        userListPanelController_inRecentTicketsPanel.getButton().addActionListener(e -> router.goBack());
        recentTicketsPanel.getAddTicketButton().addActionListener(e -> router.gotToPanel(singleAlignedPanelCenter, addTicketsPanel.getDescriptionTextField()));
        recentTicketsPanel.getCheckoutButton().addActionListener(e -> globalBillPanelController.calculate());
        recentTicketsPanel.getCheckoutButton().addActionListener(e -> router.gotToPanel(finalJLayeredPane));

        // add observers
        personsDB.addObserver(userListPanelController_inUserCreationPanel);     // pop up in the list when user is created
        personsDB.addObserver(userListPanelController_inRecentTicketsPanel);    // update list when panel is switched
        personsDB.addObserver(userCreationPanelController);                     // reset form and receive updated Icon
        personsDB.addObserver(addTicketsViewController);                        // update dropdown user list
        ticketsDB.addObserver(recentTicketPanelController);                     // adds a new ticket in the list
        ticketsDB.addObserver(addTicketsViewController);                        // reset form and stuff

        // start with user creation panel
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

        // EXTRA COMMANDS
        // to remove list & tab margins:
        // UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0,0,0,0));
    }
}
