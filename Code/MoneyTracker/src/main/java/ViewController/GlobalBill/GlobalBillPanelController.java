package ViewController.GlobalBill;

import DatabaseController.TicketsDBController;
import Model.Person;
import Model.Ticket;
import View.panels.GlobalBill.GlobalBillPanel;
import ViewController.ViewController;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

public class GlobalBillPanelController extends ViewController {
    private final TicketsDBController ticketsDBController;
    private final GlobalBillPanel globalBillPanel;

    public GlobalBillPanelController(TicketsDBController ticketsDBController, GlobalBillPanel globalBillPanel) {
        this.globalBillPanel = globalBillPanel;
        this.ticketsDBController = ticketsDBController;
    }

    @Override
    public void init() {
        globalBillPanel.setTitle("Global Bill");
        addTicketListToListModel(ticketsDBController.getAll());
    }

    @Override
    public void activateActionListeners() {
        this.globalBillPanel.getJList().addListSelectionListener(this::listSelectionActionListener);
    }

    private void listSelectionActionListener(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) return;
        System.out.println(this.globalBillPanel.getJList().getSelectedValue().getPayedByValue().getFirstNameValue() + " selected.");
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private void addTicketListToListModel(ArrayList<Ticket> ticketList) {
        for (Ticket ticket : ticketList) this.globalBillPanel.getListModel().addElement(ticket);
    }
}
