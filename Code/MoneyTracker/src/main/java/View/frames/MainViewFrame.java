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
    private FinalJLayeredPane finalJLayeredPane;
    private FinalJLayeredPane finalJLayeredPaneWithSomePanel;
    private PersonsDBController personDatabaseController;
    private TicketsDBController ticketDatabaseController;

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
        personDatabaseController = new PersonsDBController(personsDB);
        ticketDatabaseController = new TicketsDBController(ticketsDB);

        // create panel: userListPanel
        UserListPanel userListPanel = new UserListPanel();
        ViewController userListPanelController = new UserListPanelController(personDatabaseController, userListPanel);
        userListPanelController.init();
        userListPanelController.activateActionListeners();

        // extra styling for userListPanel
        userListPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 15));
        userListPanel.getTitleLabel().setForeground(Color.WHITE);
        userListPanel.getTitleLabel().setFont(new Font("", Font.PLAIN, 16));
        userListPanel.getJList().setBackground(CustomColors.getDarkGrey());
        userListPanel.setBackground(CustomColors.getMidGrey());
        userListPanel.getJList().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

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
        userCreationPanel.getGotoGlobalBillButton().setBackground(CustomColors.getYellow());
        userCreationPanel.getGotoGlobalBillButton().setText("View tickets");
        userCreationPanel.getGotoGlobalBillButton().addActionListener(e -> goToBillPanelActionListener());

        // add observers
        personsDB.addObserver(userListPanelController);
        personsDB.addObserver(userCreationPanelController);

        this.getContentPane().setBackground(CustomColors.getMidGrey());

        // create panels
        CenteredUserCreationPanel centeredUserCreationPanel = new CenteredUserCreationPanel(userCreationPanel);
        CombineJPanelGridLayoutPanel combinedUserList_and_userCreationPanel = new CombineJPanelGridLayoutPanel(centeredUserCreationPanel, userListPanel);
        CombineBannerPanel combinedWithBanner = new CombineBannerPanel(combinedUserList_and_userCreationPanel);
        finalJLayeredPane = new FinalJLayeredPane(combinedWithBanner);

        this.add(finalJLayeredPane);
        //this.add(finalJLayeredPaneWithSomePanel);
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

    private void goToBillPanelActionListener() {
        this.remove(finalJLayeredPane);
        //finalJLayeredPane = null;
        finalJLayeredPaneWithSomePanel = getGlobalBillPanel();
        this.add(finalJLayeredPaneWithSomePanel);
        this.validate();
        this.repaint();
    }

    private void createTicketActionListener() {
        this.remove(finalJLayeredPaneWithSomePanel);
        finalJLayeredPaneWithSomePanel = null;
        this.add(finalJLayeredPane);
        this.validate();
        this.repaint();
    }

    private FinalJLayeredPane getGlobalBillPanel() {
        RecentTicketsPanel recentTicketsPanel = new RecentTicketsPanel();
        RecentTicketPanelController recentTicketPanelController = new RecentTicketPanelController(ticketDatabaseController, recentTicketsPanel);
        recentTicketPanelController.init();
        recentTicketPanelController.activateActionListeners();

        recentTicketsPanel.setBorder(BorderFactory.createEmptyBorder(8, 20, 15, 5));
        recentTicketsPanel.getTextButtonContainer().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        recentTicketsPanel.getTitleLabel().setForeground(Color.WHITE);
        recentTicketsPanel.getTitleLabel().setFont(new Font("", Font.PLAIN, 16));
        recentTicketsPanel.getJList().setBackground(CustomColors.getDarkGrey());
        recentTicketsPanel.setBackground(CustomColors.getMidGrey());
        recentTicketsPanel.getJList().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        recentTicketsPanel.getAddTicketButton().setBackground(CustomColors.getYellow());
        recentTicketsPanel.getCheckoutButton().setBackground(CustomColors.getYellow());
        recentTicketsPanel.getAddTicketButton().setText("Add");
        recentTicketsPanel.getCheckoutButton().setText("Checkout");
        recentTicketsPanel.getAddTicketButton().setBorder(BorderFactory.createEmptyBorder(5,6,5,6));
        recentTicketsPanel.getCheckoutButton().setBorder(BorderFactory.createEmptyBorder(5,6,5,6));
        recentTicketsPanel.getTextButtonContainer().setBackground(CustomColors.getMidGrey());
        recentTicketsPanel.getAddTicketButton().addActionListener(e -> createTicketActionListener());

        UserListPanel userListPanel2 = new UserListPanel();
        UserListPanelController userListPanelController2 = new UserListPanelController(personDatabaseController, userListPanel2);
        userListPanelController2.init();
        userListPanelController2.activateActionListeners();

        // extra styling for userListPanel2
        userListPanel2.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 15));
        userListPanel2.getTitleLabel().setForeground(Color.WHITE);
        userListPanel2.getTitleLabel().setFont(new Font("", Font.PLAIN, 16));
        userListPanel2.getJList().setBackground(CustomColors.getDarkGrey());
        userListPanel2.setBackground(CustomColors.getMidGrey());
        userListPanel2.getJList().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        CombineJPanelGridLayoutPanel combinedNothing_and_userCreationPanel = new CombineJPanelGridLayoutPanel(recentTicketsPanel, userListPanel2);
        CombineBannerPanel combinedSomePanelWithBanner = new CombineBannerPanel(combinedNothing_and_userCreationPanel);
        finalJLayeredPaneWithSomePanel = new FinalJLayeredPane(combinedSomePanelWithBanner);

        return finalJLayeredPaneWithSomePanel;
    }
}
