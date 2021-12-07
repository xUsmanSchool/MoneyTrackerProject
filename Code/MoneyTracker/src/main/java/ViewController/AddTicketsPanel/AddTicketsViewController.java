package ViewController.AddTicketsPanel;

import DatabaseController.PersonsDBController;
import DatabaseController.TicketsDBController;
import Events.Event;
import Factory.EventFactory;
import HelperClass.EnumConverter;
import HelperClass.Events;
import HelperClass.SplitType;
import Model.*;
import View.others.CustomColors;
import View.panels.AddTicketsPanel.AddTicketsPanel;
import ViewController.ViewController;
import javax.swing.*;
import java.awt.*;
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
        //todo add margin offset

        // set icon
        Events selectedEvents = Events.valueOf((String)addTicketsPanel.getEventsJComboBox().getSelectedItem());
        EventFactory eventFactory = new EventFactory();
        Event selectedEvent = eventFactory.getEvent(selectedEvents);
        addTicketsPanel.setIconLabelImage(new ImageIcon(selectedEvent.getIcon()));

        // button
        // todo - escape to quit and go to previous panel
    }

    @Override
    public void activateActionListeners() {
        addTicketsPanel.getEventsJComboBox().addActionListener(e -> changeEventsIconActionListener());
        addTicketsPanel.getSplitTypeJComboBox().addActionListener(e -> splitTypeButtonRenameActionListener());
        addTicketsPanel.getCreateOrContinueTicketButton().addActionListener(e -> addTicketOrContinueActionListener(addTicketsPanel.getCreateOrContinueTicketButton().getText()));
    }

    private void changeEventsIconActionListener() {
        System.out.println("Changing icon to: " + addTicketsPanel.getEventsJComboBox().getSelectedItem());
        // todo - update icon
    }

    private void splitTypeButtonRenameActionListener() {
        SplitType splitType = SplitType.valueOf((String)addTicketsPanel.getSplitTypeJComboBox().getSelectedItem());
        switch (Objects.requireNonNull(splitType)) {
            case EQUAL:
                addTicketsPanel.setCreateOrContinueTicketButtonText(buttonCreateNowText);
                break;
            case UNEQUAL:
                addTicketsPanel.setCreateOrContinueTicketButtonText(buttonContinueText);
                break;
        }
    }

    private void addTicketOrContinueActionListener(String text) {
        if (text.equals(buttonCreateNowText)) {
            System.out.println("Button clicked - creating ticket now...but not really");
            // todo - missing action
        }
        else if (text.equals(buttonContinueText)) {
            System.out.println("Button clicked - continuing to split payment panel...but not really");
            // todo - missing action
        }
        else System.err.println("addTicketOrContinueActionListener - wrong text format");
    }

    @Override
    public void update(Observable o, Object arg) {}
}
