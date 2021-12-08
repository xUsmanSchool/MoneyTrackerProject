package ViewController.PaymentSplits;

import DatabaseController.TicketsDBController;
import View.panels.PaymentSplits.PaymentSplitPanel;
import ViewController.ViewController;
import java.util.Observable;

public class PaymentSplitPanelController extends ViewController {
    private final TicketsDBController ticketsDBController;
    private final PaymentSplitPanel paymentSplitPanel;

    public PaymentSplitPanelController(TicketsDBController ticketsDBController, PaymentSplitPanel paymentSplitPanel) {
        this.paymentSplitPanel = paymentSplitPanel;
        this.ticketsDBController = ticketsDBController;
    }

    @Override
    public void init() {

    }

    @Override
    public void activateActionListeners() {

    }

    @Override
    public void update(Observable o, Object arg) {
        // todo - insert Ticket in DB
        // todo - go back
    }
}
