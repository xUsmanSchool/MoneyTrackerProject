package ViewController.RecentTickets;

import DatabaseController.TicketsDBController;
import Model.Ticket;
import View.panels.RecentTickets.RecentTicketsPanel;
import ViewController.ViewController;
import javax.swing.event.ListSelectionEvent;
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
