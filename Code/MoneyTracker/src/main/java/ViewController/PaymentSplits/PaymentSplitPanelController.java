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
import javax.swing.text.*;
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

    private PaymentSplitSubPanelCASH paymentSplitSubPanelCASH;
    private PaymentSplitSubPanelPERCENTAGE paymentSplitSubPanelPERCENTAGE;

    public PaymentSplitPanelController(PersonsDBController personsDBController, TicketsDBController ticketsDBController, PaymentSplitPanelWithBorder paymentSplitPanel) {
        this.paymentSplitPanel = paymentSplitPanel;
        this.ticketsDBController = ticketsDBController;
        this.personsDBController = personsDBController;
    }

    @Override
    public void init() {
        createCashPanelFields();
        createPercentagePanelFields();
    }

    @Override
    public void activateActionListeners() {
        paymentSplitSubPanelCASH.getDoneButton().addActionListener(e -> cashDoneButtonActionListener());
        paymentSplitSubPanelPERCENTAGE.getDoneButton().addActionListener(e -> percentageDoneButtonActionListener());
    }

    private void cashDoneButtonActionListener() {
        System.out.println("Cash done button pressed");

        for (JFormattedTextField f:paymentSplitSubPanelCASH.getAmounts()) {
            Double value = (Double) f.getValue();
            System.out.println(value);
        }

        System.out.println("Redrawing...");

        // remove all, recreate and add that single action listener (todo - separate out)
        paymentSplitPanel.removeContentPanel(0);
        createCashPanelFields();
        paymentSplitSubPanelCASH.getDoneButton().addActionListener(e -> cashDoneButtonActionListener());

        // todo: read all fields
        // todo: validate fields on change and change button to unavailable
        // todo: add label with remaining amount/percentage
    }

    private void percentageDoneButtonActionListener() {
        System.out.println("Percentage done button pressed");
    }

    @Override
    public void update(Observable o, Object arg) {}

    private void createCashPanelFields() {
        // get info for panel generation
        ArrayList<Person> persons = personsDBController.getAll();

        // left
        ArrayList<JLabel> iconLabels1 = new ArrayList<>();
        ArrayList<JLabel> userNames1 = new ArrayList<>();
        ArrayList<JLabel> moneyIcons1 = new ArrayList<>();
        ArrayList<JFormattedTextField> amounts_toPay1 = new ArrayList<>();

        Person ticketPayedBy = currentTicket.getPayedByValue();
        Double ticketValueToPay = currentTicket.getTotalSum();

        for (Person person : persons) {
            // create
            JLabel iconLabel1 = new JLabel(new ImageIcon(Paths.iconPath + (person.getIconValue().length() == 0 ? "user_icon_small.png" : person.getIconValue())));
            JLabel userName1 = new JLabel(person.getFirstNameValue() + " " + person.getLastNameValue());
            JLabel moneyIcon = new JLabel("$");

            JFormattedTextField amount_toPay = new JFormattedTextField();
            ((AbstractDocument) amount_toPay.getDocument()).setDocumentFilter(new MyDocumentFilter());
            amount_toPay.setText("0.00");

            // style
            iconLabel1.setForeground(Color.WHITE);
            userName1.setForeground(person == ticketPayedBy ? CustomColors.getYellow() : Color.WHITE);
            moneyIcon.setForeground(Color.WHITE);
            userName1.setMinimumSize(new Dimension(300,30));

            // add to list
            iconLabels1.add(iconLabel1);
            userNames1.add(userName1);
            moneyIcons1.add(moneyIcon);
            amounts_toPay1.add(amount_toPay);
        }

        // add sub panel 1
        paymentSplitSubPanelCASH = new PaymentSplitSubPanelCASH(iconLabels1, userNames1, moneyIcons1, amounts_toPay1);
        paymentSplitPanel.setContentPanel(0, paymentSplitSubPanelCASH, iconLabels1.size() > 5);
    }

    private void createPercentagePanelFields() {
        // get info for panel generation
        ArrayList<Person> persons = personsDBController.getAll();

        // right
        ArrayList<JLabel> iconLabels2 = new ArrayList<>();
        ArrayList<JLabel> userNames2 = new ArrayList<>();
        ArrayList<JLabel> percentageIcons2 = new ArrayList<>();
        ArrayList<JSpinner> percentages_toPay2 = new ArrayList<>();
        ArrayList<JLabel> amounts_converted2 = new ArrayList<>();

        Person payedBy = currentTicket.getPayedByValue();

        for (Person person : persons) {
            // create
            JLabel iconLabel2 = new JLabel(new ImageIcon(Paths.iconPath + (person.getIconValue().length() == 0 ? "user_icon_small.png" : person.getIconValue())));
            JLabel userName2 = new JLabel(person.getFirstNameValue() + " " + person.getLastNameValue());
            JLabel percentageIcon = new JLabel("%");

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
            iconLabel2.setForeground(Color.WHITE);
            userName2.setForeground(person == payedBy ? CustomColors.getYellow() : Color.WHITE);

            percentageIcon.setForeground(Color.WHITE);
            amount_converted.setForeground(CustomColors.getYellow());
            userName2.setMinimumSize(new Dimension(255,30));

            // add to list
            iconLabels2.add(iconLabel2);
            userNames2.add(userName2);
            percentageIcons2.add(percentageIcon);
            percentages_toPay2.add(percentage_toPay);
            amounts_converted2.add(amount_converted);
        }

        // add sub panel 2
        paymentSplitSubPanelPERCENTAGE = new PaymentSplitSubPanelPERCENTAGE(iconLabels2, userNames2, percentages_toPay2, percentageIcons2, amounts_converted2);
        paymentSplitPanel.setContentPanel(1, paymentSplitSubPanelPERCENTAGE, iconLabels2.size() > 5);
    }

    public void setTicket(Ticket t) {
        this.currentTicket = t;
    }

    // https://stackoverflow.com/questions/8658205/format-currency-without-currency-symbol
    /*private NumberFormatter createValueMaskFormatter(Double limit) {
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
            numberFormatter1.setMaximum(100.05);
        } catch (Exception e) { System.err.println(e.toString()); }

        return numberFormatter1;
    }*/

    private NumberFormatter createMaskFormatter() {
        // https://stackoverflow.com/questions/8658205/format-currency-without-currency-symbol
        // https://stackoverflow.com/questions/12806278/double-decimal-formatting-in-java

        DecimalFormat formatter = new DecimalFormat("##0.00");
        NumberFormatter numberFormatter1 = null;
        try {
            numberFormatter1 = new NumberFormatter(formatter);
            numberFormatter1.setAllowsInvalid(false);
            numberFormatter1.setOverwriteMode(true);
            numberFormatter1.setCommitsOnValidEdit(true);
            numberFormatter1.setMaximum(99.99);
        } catch (Exception e) { System.err.println(e.toString()); }

        return numberFormatter1;
    }

    private NumberFormatter test1() {
        NumberFormatter numberFormatter1 = null;
        try {
            numberFormatter1 = new NumberFormatter(new DecimalFormat());
            numberFormatter1.setAllowsInvalid(false);
            numberFormatter1.setOverwriteMode(true);
            numberFormatter1.setCommitsOnValidEdit(true);
            numberFormatter1.setMaximum(99.99);
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

        /*private void reCreateFields() {
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
        paymentSplitSubPanelCASH = new PaymentSplitSubPanelCASH(iconLabels1, userNames1, moneyIcons1, amounts_toPay1);
        paymentSplitPanel.setContentPanel(0, paymentSplitSubPanelCASH, iconLabels1.size() > 5);

        // add sub panel 2
        paymentSplitSubPanelPERCENTAGE = new PaymentSplitSubPanelPERCENTAGE(iconLabels2, userNames2, percentages_toPay2, percentageIcons2, amounts_converted2);
        paymentSplitPanel.setContentPanel(1, paymentSplitSubPanelPERCENTAGE, iconLabels2.size() > 5);
    }*/
}

class MyDocumentFilter extends DocumentFilter {

    @Override
    public void replace(FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
        // don't allow pasting
        if (string.length() > 1) return;

        // get a single character of the string
        char c = string.charAt(0);

        System.out.println("Character: " + c + "; offset/position: " + i + "; length: " + i1 + "; full string: " + string);

        // if its a numetic character or a dot
        if (Character.isDigit(c) || c == '.') {


            // allow update to take place for the given character
            super.replace(fb, i, i1, String.valueOf(c), as);
        } else { // it not
            System.out.println("Not allowed");
        }
    }

    @Override
    public void remove(FilterBypass fb, int i, int i1) throws BadLocationException {
        super.remove(fb, i, i1);
    }

    @Override
    public void insertString(FilterBypass fb, int i, String string, AttributeSet as) throws BadLocationException {
        super.insertString(fb, i, string, as);
    }
}