package ViewController.AddTicketsPanel;

import DatabaseController.PersonsDBController;
import DatabaseController.TicketsDBController;
import Events.Event;
import Factory.EventFactory;
import Factory.TicketFactory;
import HelperClass.EnumConverter;
import HelperClass.Events;
import HelperClass.Paths;
import HelperClass.SplitType;
import Model.*;
import Observers.PersonDBObservableEntry;
import Observers.TicketDBObservableEntry;
import View.others.CustomColors;
import View.others.Router;
import View.panels.AddTicketsPanel.AddTicketsPanel;
import View.panels.PaymentSplits.PaymentSplitPanel;
import View.panels.PaymentSplits.PaymentSplitPanelWithBorder;
import ViewController.PaymentSplits.PaymentSplitPanelController;
import ViewController.ViewController;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;

public class AddTicketsViewController extends ViewController {
    private final PersonsDBController personsDBController;
    private final TicketsDBController ticketsDBController;
    private final AddTicketsPanel addTicketsPanel;
    private String buttonContinueText, buttonCreateNowText;

    public AddTicketsViewController(PersonsDBController personsDBController, TicketsDBController ticketsDBController, AddTicketsPanel addTicketsPanel) {
        this.personsDBController = personsDBController;
        this.addTicketsPanel = addTicketsPanel;
        this.ticketsDBController = ticketsDBController;
    }

    @Override
    public void init() {
        buttonContinueText = "Continue";
        buttonCreateNowText = "Create ticket";

        // JComboBox
        for(String e : EnumConverter.enumToString(Events.values())) addTicketsPanel.getEventsJComboBox().addItem(e);
        for(String s : EnumConverter.enumToString(SplitType.values())) addTicketsPanel.getSplitTypeJComboBox().addItem(s);
        for(Person p : personsDBController.getAll()) addTicketsPanel.getPersonJComboBox().addItem(p.getFirstNameValue() + " " + p.getLastNameValue());

        // text
        addTicketsPanel.setDescriptionLabelText("Enter a description: ");
        addTicketsPanel.setTicketTypeLabelText("Type of ticket: ");
        addTicketsPanel.setPayedAmountLabelText("Amount payed: ");
        addTicketsPanel.setPayedByLabelText("Payed by: ");
        addTicketsPanel.setSplitTypeLabelText("Split: ");
        addTicketsPanel.setCreateOrContinueTicketButtonText(buttonCreateNowText);

        // styling
        addTicketsPanel.getDescriptionLabel().setForeground(Color.WHITE);
        addTicketsPanel.getTicketTypeLabel().setForeground(Color.WHITE);
        addTicketsPanel.getPayedAmountLabel().setForeground(Color.WHITE);
        addTicketsPanel.getPayedByLabel().setForeground(Color.WHITE);
        addTicketsPanel.getSplitTypeLabel().setForeground(Color.WHITE);
        addTicketsPanel.setBackground(CustomColors.getDarkGrey());
        addTicketsPanel.getCreateOrContinueTicketButton().setBackground(CustomColors.getYellow());

        // set icon
        Event selectedEvent = getSelectedEvent();
        Icon icon = new ImageIcon(Paths.eventPath + selectedEvent.getIcon());
        addTicketsPanel.setIconLabelImage(icon);

        // button
        changeButtonVisibility();

        // focus
        addTicketsPanel.getDescriptionTextField().setFocusable(true);
    }

    @Override
    public void activateActionListeners() {
        addTicketsPanel.getEventsJComboBox().addActionListener(e -> changeEventsIconActionListener());
        addTicketsPanel.getSplitTypeJComboBox().addActionListener(e -> splitTypeButtonRenameActionListener());
        addTicketsPanel.getCreateOrContinueTicketButton().addActionListener(e -> addTicketOrContinueActionListener(addTicketsPanel.getCreateOrContinueTicketButton().getText()));
        addTicketsPanel.getPayedAmountTextField().getDocument().addDocumentListener(checkPayedAmountTextFieldValidDocumentListener());
    }

    private void changeEventsIconActionListener() {
        Event selectedEvent = getSelectedEvent();
        Icon icon = new ImageIcon(Paths.eventPath + selectedEvent.getIcon());
        addTicketsPanel.setIconLabelImage(icon);
    }

    private void splitTypeButtonRenameActionListener() {
        switch (Objects.requireNonNull(getSplitType())) {
            case EQUAL:
                addTicketsPanel.setCreateOrContinueTicketButtonText(buttonCreateNowText);
                break;
            case UNEQUAL:
                addTicketsPanel.setCreateOrContinueTicketButtonText(buttonContinueText);
                break;
        }
    }

    private void addTicketOrContinueActionListener(String text) {
        if (text.equals(buttonCreateNowText)) addTicketToDB();
        else if (text.equals(buttonContinueText)) openPaymentSplitScreen();
        else System.err.println("addTicketOrContinueActionListener - wrong text format");
    }

    private void addTicketToDB() {
        Ticket t = getTicket(getSplitType());
        ticketsDBController.add(t);

        System.out.println("Ticket successfully added");
    }

    private void openPaymentSplitScreen() {
        // get ticket
        Ticket t = getTicket(getSplitType());

        // create tabs
        PaymentSplitPanelWithBorder paymentSplitPanelWithBorder = new PaymentSplitPanelWithBorder();

        // do nothing yet with controller - watch order!
        PaymentSplitPanelController paymentSplitPanelController = new PaymentSplitPanelController(personsDBController, ticketsDBController, paymentSplitPanelWithBorder);

        // set the current state of the ticket - which isn't ready yet
        paymentSplitPanelController.setTicket(t);

        // fill up tabs and transfer this ticket to another inner controller
        paymentSplitPanelController.init();


        Router.getInstance().gotToPanel(paymentSplitPanelWithBorder);
    }

    private SplitType getSplitType() {
        return SplitType.valueOf((String)addTicketsPanel.getSplitTypeJComboBox().getSelectedItem());
    }

    private Event getSelectedEvent() {
        Events selectedEvents = Events.valueOf((String)addTicketsPanel.getEventsJComboBox().getSelectedItem());
        EventFactory eventFactory = new EventFactory();

        return eventFactory.getEvent(selectedEvents);
    }
    
    private Person getSelectedPerson() {
        // get person
        ArrayList<Person> personList = personsDBController.getAll();
        int listIndex = addTicketsPanel.getPersonJComboBox().getSelectedIndex();
        Person selectedPerson = personList.get(listIndex);

        // check
        String nameInList = (String)addTicketsPanel.getPersonJComboBox().getSelectedItem();
        String nameInPersonList = selectedPerson.getFirstNameValue() + " " + selectedPerson.getLastNameValue();
        if (!Objects.equals(nameInList, nameInPersonList)) {
            System.err.println("getSelectedPerson in AddTicketsViewController error: name in list and DB don't match");
            return null;
        }

        //else
        return selectedPerson;
    }

    private Ticket getTicket(SplitType splitType) {
        // get all info
        Person payedBy = getSelectedPerson();
        Double totalSum = addTicketsPanel.getPayedAmountTextFieldValue();
        Event event = getSelectedEvent();

        // ticket factory
        TicketFactory ticketFactory = new TicketFactory();
        Ticket t = ticketFactory.getTicket(payedBy, totalSum, event, splitType);
        t.autoCalculate(personsDBController.getAll());
        return t;
    }

    private DocumentListener checkPayedAmountTextFieldValidDocumentListener() {
        return new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeButtonVisibility();
            }
            public void removeUpdate(DocumentEvent e) {
                changeButtonVisibility();
            }
            public void insertUpdate(DocumentEvent e) {
                changeButtonVisibility();
            }
        };
    }

    private void changeButtonVisibility() {
        addTicketsPanel.getCreateOrContinueTicketButton().setEnabled(addTicketsPanel.getPayedAmountTextFieldValue() > 0);
    }

    private void clearForm() {
        addTicketsPanel.getDescriptionTextField().setText("");
        addTicketsPanel.getPayedAmountTextField().setValue(0.00);
        addTicketsPanel.getEventsJComboBox().setSelectedIndex(0);
        addTicketsPanel.getPersonJComboBox().setSelectedIndex(0);
        addTicketsPanel.getSplitTypeJComboBox().setSelectedIndex(0);
        // todo - set image?
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof TicketDBObservableEntry) {
            clearForm();

            Router.getInstance().goBack();
        }

        if (arg instanceof PersonDBObservableEntry) {
            // clear and update the dropdown list
            addTicketsPanel.getPersonJComboBox().removeAllItems();
            for(Person p : personsDBController.getAll()) addTicketsPanel.getPersonJComboBox().addItem(p.getFirstNameValue() + " " + p.getLastNameValue());
        }
    }
}
