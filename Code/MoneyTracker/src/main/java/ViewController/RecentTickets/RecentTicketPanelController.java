package ViewController.RecentTickets;

import DatabaseController.TicketsDBController;
import Model.Ticket;
import View.others.CustomColors;
import View.panels.RecentTickets.RecentTicketsPanel;
import ViewController.ViewController;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

public class RecentTicketPanelController extends ViewController {
    private final TicketsDBController ticketsDBController;
    private final RecentTicketsPanel recentTicketsPanel;

    public RecentTicketPanelController(TicketsDBController ticketsDBController, RecentTicketsPanel recentTicketsPanel) {
        this.recentTicketsPanel = recentTicketsPanel;
        this.ticketsDBController = ticketsDBController;
    }

    @Override
    public void init() {
        recentTicketsPanel.setTitle("Recent ticket list");
        addTicketListToListModel(ticketsDBController.getAll());

        recentTicketsPanel.setBorder(BorderFactory.createEmptyBorder(80, 20, 15, 5));
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
        recentTicketsPanel.getJList().setFixedCellHeight(50);
    }

    @Override
    public void activateActionListeners() {
        this.recentTicketsPanel.getJList().addListSelectionListener(this::listSelectionActionListener);
    }

    private void listSelectionActionListener(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) return;
        System.out.println(this.recentTicketsPanel.getJList().getSelectedValue().getPayedByValue().getFirstNameValue() + " selected.");
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private void addTicketListToListModel(ArrayList<Ticket> ticketList) {
        for (Ticket ticket : ticketList) this.recentTicketsPanel.getListModel().addElement(ticket);
    }
}
