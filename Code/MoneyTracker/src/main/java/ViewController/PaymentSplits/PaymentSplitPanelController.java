package ViewController.PaymentSplits;

import DatabaseController.PersonsDBController;
import DatabaseController.TicketsDBController;
import HelperClass.Paths;
import Model.Person;
import View.others.CustomColors;
import View.panels.PaymentSplits.PaymentSplitPanelWithBorder;
import View.panels.PaymentSplits.PaymentSplitSubPanelCASH;
import View.panels.PaymentSplits.PaymentSplitSubPanelPERCENTAGE;
import ViewController.ViewController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

public class PaymentSplitPanelController extends ViewController {
    private final TicketsDBController ticketsDBController;
    private final PersonsDBController personsDBController;
    private final PaymentSplitPanelWithBorder paymentSplitPanel;

    public PaymentSplitPanelController(PersonsDBController personsDBController, TicketsDBController ticketsDBController, PaymentSplitPanelWithBorder paymentSplitPanel) {
        this.paymentSplitPanel = paymentSplitPanel;
        this.ticketsDBController = ticketsDBController;
        this.personsDBController = personsDBController;
    }

    @Override
    public void init() {
        // get info for panel generation
        ArrayList<Person> persons = personsDBController.getAll();

        // left
        ArrayList<JLabel> iconLabels1 = new ArrayList<>();
        ArrayList<JLabel> userNames1 = new ArrayList<>();
        ArrayList<JLabel> moneyIcons1 = new ArrayList<>();
        ArrayList<JTextField> amounts_toPay1 = new ArrayList<>();

        // right
        ArrayList<JLabel> iconLabels2 = new ArrayList<>();
        ArrayList<JLabel> userNames2 = new ArrayList<>();
        ArrayList<JLabel> percentageIcons2 = new ArrayList<>();
        ArrayList<JTextField> percentages_toPay2 = new ArrayList<>();
        ArrayList<JLabel> amounts_converted2 = new ArrayList<>();

        for (Person person : persons) {
            // create
            // todo - color person that payed in green?
            JLabel iconLabel1 = new JLabel(new ImageIcon(Paths.iconPath + (person.getIconValue().length() == 0 ? "user_icon_small.png" : person.getIconValue())));
            JLabel iconLabel2 = new JLabel(new ImageIcon(Paths.iconPath + (person.getIconValue().length() == 0 ? "user_icon_small.png" : person.getIconValue())));

            JLabel userName1 = new JLabel(person.getFirstNameValue() + " " + person.getLastNameValue());
            JLabel userName2 = new JLabel(person.getFirstNameValue() + " " + person.getLastNameValue());

            JLabel moneyIcon = new JLabel("$");
            JLabel percentageIcon = new JLabel("%");
            JTextField amount_toPay = new JTextField("0.00");
            JTextField percentage_toPay = new JTextField("0");
            JLabel amount_converted = new JLabel("$0.00");

            // style
            iconLabel1.setForeground(Color.WHITE);
            iconLabel2.setForeground(Color.WHITE);

            userName1.setForeground(Color.WHITE);
            userName2.setForeground(Color.WHITE);

            moneyIcon.setForeground(Color.WHITE);
            percentageIcon.setForeground(Color.WHITE);
            amount_converted.setForeground(CustomColors.getYellow());
            userName1.setMinimumSize(new Dimension(300,30));
            userName2.setMinimumSize(new Dimension(270,30));

            // add to list
            iconLabels1.add(iconLabel1);
            iconLabels2.add(iconLabel2);

            userNames1.add(userName1);
            userNames2.add(userName2);

            moneyIcons1.add(moneyIcon);
            percentageIcons2.add(percentageIcon);
            amounts_toPay1.add(amount_toPay);
            percentages_toPay2.add(percentage_toPay);
            amounts_converted2.add(amount_converted);
        }

        // add sub panel 1
        PaymentSplitSubPanelCASH paymentSplitSubPanelCASH = new PaymentSplitSubPanelCASH(iconLabels1, userNames1, moneyIcons1, amounts_toPay1);
        paymentSplitPanel.setContentPanel(0, paymentSplitSubPanelCASH, iconLabels1.size() > 5);

        // add sub panel 2
        PaymentSplitSubPanelPERCENTAGE paymentSplitSubPanelPERCENTAGE = new PaymentSplitSubPanelPERCENTAGE(iconLabels2, userNames2, percentages_toPay2, percentageIcons2, amounts_converted2);
        paymentSplitPanel.setContentPanel(1, paymentSplitSubPanelPERCENTAGE, iconLabels2.size() > 5);
    }

    @Override
    public void activateActionListeners() {}

    @Override
    public void update(Observable o, Object arg) {}
}
