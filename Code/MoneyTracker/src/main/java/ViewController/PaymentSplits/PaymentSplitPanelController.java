package ViewController.PaymentSplits;

import DatabaseController.PersonsDBController;
import DatabaseController.TicketsDBController;
import HelperClass.Paths;
import Model.Person;
import Model.Ticket;
import View.others.CustomColors;
import View.panels.PaymentSplits.PaymentSplitPanelWithBorder;
import View.panels.PaymentSplits.PaymentSplitSubPanelCASH;
import View.panels.PaymentSplits.PaymentSplitSubPanelPERCENTAGE;
import ViewController.ViewController;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Observable;

public class PaymentSplitPanelController extends ViewController {
    private final TicketsDBController ticketsDBController;
    private final PersonsDBController personsDBController;
    private final PaymentSplitPanelWithBorder paymentSplitPanel;
    private Ticket currentTicket;

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
        ArrayList<JFormattedTextField> amounts_toPay1 = new ArrayList<>();

        // right
        ArrayList<JLabel> iconLabels2 = new ArrayList<>();
        ArrayList<JLabel> userNames2 = new ArrayList<>();
        ArrayList<JLabel> percentageIcons2 = new ArrayList<>();
        ArrayList<JSpinner> percentages_toPay2 = new ArrayList<>();
        ArrayList<JLabel> amounts_converted2 = new ArrayList<>();

        Person payedBy = currentTicket.getPayedByValue();

        for (Person person : persons) {
            // create
            JLabel iconLabel1 = new JLabel(new ImageIcon(Paths.iconPath + (person.getIconValue().length() == 0 ? "user_icon_small.png" : person.getIconValue())));
            JLabel iconLabel2 = new JLabel(new ImageIcon(Paths.iconPath + (person.getIconValue().length() == 0 ? "user_icon_small.png" : person.getIconValue())));

            JLabel userName1 = new JLabel(person.getFirstNameValue() + " " + person.getLastNameValue());
            JLabel userName2 = new JLabel(person.getFirstNameValue() + " " + person.getLastNameValue());

            JLabel moneyIcon = new JLabel("$");
            JLabel percentageIcon = new JLabel("%");
            
            JFormattedTextField amount_toPay = new JFormattedTextField(createValueMaskFormatter());
            amount_toPay.setValue(0.00);

            SpinnerModel model = new SpinnerNumberModel(
                    0,        //initial value
                    0,              //min
                    100,            //max
                    1);             //step

            JSpinner percentage_toPay = new JSpinner(model);
            percentage_toPay.setEditor(new JSpinner.NumberEditor(percentage_toPay,"0"));
            JFormattedTextField txt = getTextField(percentage_toPay);
            NumberFormatter nf = (NumberFormatter) txt.getFormatter();
            nf.setAllowsInvalid(false);
            nf.setOverwriteMode(true);
            nf.setCommitsOnValidEdit(true);

            JLabel amount_converted = new JLabel("$0.00");

            // style
            iconLabel1.setForeground(Color.WHITE);
            iconLabel2.setForeground(Color.WHITE);

            userName1.setForeground(person == payedBy ? CustomColors.getYellow() : Color.WHITE);
            userName2.setForeground(person == payedBy ? CustomColors.getYellow() : Color.WHITE);

            moneyIcon.setForeground(Color.WHITE);
            percentageIcon.setForeground(Color.WHITE);
            amount_converted.setForeground(CustomColors.getYellow());
            userName1.setMinimumSize(new Dimension(300,30));
            userName2.setMinimumSize(new Dimension(255,30));

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

    public void setTicket(Ticket t) {
        this.currentTicket = t;
    }

    // https://stackoverflow.com/questions/8658205/format-currency-without-currency-symbol
    private NumberFormatter createValueMaskFormatter() {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        String pattern = ((DecimalFormat) format).toPattern();
        String newPattern = pattern.replace("\u00A4", "").trim();
        NumberFormat newFormat = new DecimalFormat(newPattern);

        NumberFormatter numberFormatter1 = null;
        try {
            numberFormatter1 = new NumberFormatter(newFormat);
            numberFormatter1.setAllowsInvalid(false);
            numberFormatter1.setOverwriteMode(true);
            numberFormatter1.setCommitsOnValidEdit(true);
            numberFormatter1.setMaximum(9999.99);
        } catch (Exception e) { System.err.println(e.toString()); }

        return numberFormatter1;
    }

    // https://stackoverflow.com/questions/8658205/format-currency-without-currency-symbol
    private NumberFormatter createIntegerMaskFormatter() {
        NumberFormat format = NumberFormat.getNumberInstance();

        NumberFormatter numberFormatter1 = null;
        try {
            numberFormatter1 = new NumberFormatter(format);
            numberFormatter1.setAllowsInvalid(false);
            numberFormatter1.setOverwriteMode(true);
            numberFormatter1.setCommitsOnValidEdit(true);
            numberFormatter1.setMaximum(100);
        } catch (Exception e) { System.err.println(e.toString()); }

        return numberFormatter1;
    }

    // https://docs.oracle.com/javase/tutorial/uiswing/components/spinner.html
    private JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor)editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                    + spinner.getEditor().getClass()
                    + " isn't a descendant of DefaultEditor");
            return null;
        }
    }
}
