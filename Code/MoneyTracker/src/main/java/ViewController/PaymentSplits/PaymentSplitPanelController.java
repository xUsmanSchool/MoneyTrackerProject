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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;

public class PaymentSplitPanelController extends ViewController {
    private final TicketsDBController ticketsDBController;
    private final PersonsDBController personsDBController;
    private final PaymentSplitPanelWithBorder paymentSplitPanel;
    private Ticket currentTicket;
    private int lastFocusIn;
    private boolean justRefreshed;

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

        activatePercentageFocusListeners();
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
        System.out.println("Percentage done button pressed but nuffing happened... #magic");
    }

    private void fullRedrawPercentagePanel() {
        // !!! prevent endless looping
        justRefreshed = true;

        // get old fields and save the old field values
        ArrayList<Integer> oldValues = getOldPercentageSpinnerFields();

        // remove panel
        paymentSplitPanel.removeContentPanel(1);

        // recreate panel
        createPercentagePanelFields();

        // get new fields
        ArrayList<JSpinner> newjSpinners = paymentSplitSubPanelPERCENTAGE.getPercentages_toPay();

        // restore values to old field values
        int i = 0;
        //for (JSpinner spinner : newjSpinners) Objects.requireNonNull(getTextField(spinner)).setText(oldValues.get(i++));
        for (JSpinner spinner : newjSpinners) spinner.setValue(Integer.valueOf(oldValues.get(i++)));

        // reset focus so that the user doesn't notice anything
        Objects.requireNonNull(getTextField(newjSpinners.get(lastFocusIn))).grabFocus();
        Objects.requireNonNull(getTextField(newjSpinners.get(lastFocusIn))).requestFocus();
        Objects.requireNonNull(getTextField(newjSpinners.get(lastFocusIn))).requestFocusInWindow();
        lastFocusIn = 0;

        // activate listeners
        paymentSplitSubPanelPERCENTAGE.getDoneButton().addActionListener(e -> percentageDoneButtonActionListener());
        activatePercentageFocusListeners();
    }

    @Override
    public void update(Observable o, Object arg) {}

    private void activatePercentageFocusListeners() {
        ArrayList<JSpinner> jSpinners = paymentSplitSubPanelPERCENTAGE.percentages_toPay;
        int i = 0;
        for (JSpinner spinner:jSpinners) {
            JFormattedTextField txt = getTextField(spinner);
            txt.addFocusListener(getFocusListener(i));
            i++;
        }
    }

    private void disactivatePercentageFocusListeners() {
        ArrayList<JSpinner> jSpinners = paymentSplitSubPanelPERCENTAGE.percentages_toPay;
        for (JSpinner spinner:jSpinners) {
            JFormattedTextField txt = getTextField(spinner);
            FocusListener[] fls = txt.getFocusListeners();
            for (FocusListener fl : fls) txt.removeFocusListener(fl);
        }
    }

    private ArrayList<Integer> getOldPercentageSpinnerFields() {
        // return null if non existent
        if (paymentSplitSubPanelPERCENTAGE == null) return null;

        // get old fields
        ArrayList<JSpinner> oldjSpinners = paymentSplitSubPanelPERCENTAGE.getPercentages_toPay();
        ArrayList<String> oldValues = new ArrayList<>();

        // save the old field values as strings
        for (JSpinner spinner : oldjSpinners) oldValues.add(Objects.requireNonNull(getTextField(spinner)).getText());

        // convert to ints
        ArrayList<Integer> oldIntegerValues = new ArrayList<>();
        for (String s: oldValues) oldIntegerValues.add(Integer.valueOf(s));

        return oldIntegerValues;
    }

    private Integer getArraySum(ArrayList<Integer> list) {
        int sum = 0;
        for (Integer i:list) sum += i;

        return sum;
    }

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

            JFormattedTextField amount_toPay = new JFormattedTextField(createMaskFormatter());
            amount_toPay.setValue(0.00);

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
        ArrayList<Integer> maxima2 = new ArrayList<>();

        Person payedBy = currentTicket.getPayedByValue();

        int i = 0;
        for (Person person : persons) {
            // create
            JLabel iconLabel = new JLabel(new ImageIcon(Paths.iconPath + (person.getIconValue().length() == 0 ? "user_icon_small.png" : person.getIconValue())));
            JLabel userName = new JLabel(person.getFirstNameValue() + " " + person.getLastNameValue());
            JLabel percentageIcon = new JLabel("%");
            Integer spinnerMaximum = 100;

            ArrayList<Integer> list = getOldPercentageSpinnerFields();
            if (list != null) {
                Integer percentageSum = getArraySum(list);
                Integer remainingPercentage = 100 - percentageSum;
                spinnerMaximum = list.get(i) + remainingPercentage;
            }

            int convMaximum = spinnerMaximum.intValue();
            SpinnerModel model = new SpinnerNumberModel(
                    0,                //initial value
                    0,                      //min
                    convMaximum,            //max
                    1);                     //step

            JSpinner percentage_toPay = new JSpinner(model);
            percentage_toPay.setEditor(new JSpinner.NumberEditor(percentage_toPay,""));
            JFormattedTextField txt = getTextField(percentage_toPay);

            NumberFormatter nf = (NumberFormatter) txt.getFormatter();
            nf.setAllowsInvalid(false);
            nf.setOverwriteMode(true);
            nf.setCommitsOnValidEdit(true);

            JLabel amount_converted = new JLabel("$0.00");

            // style
            iconLabel.setForeground(Color.WHITE);
            userName.setForeground(person == payedBy ? CustomColors.getYellow() : Color.WHITE);

            int finalI = i;
            txt.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    calculateShareForPercentage(finalI);
                }
            });

            percentageIcon.setForeground(Color.WHITE);
            amount_converted.setForeground(CustomColors.getYellow());
            amount_converted.setMinimumSize(new Dimension(70,30));
            userName.setMinimumSize(new Dimension(230,30));
            percentage_toPay.setMinimumSize(new Dimension(53,20));

            // add to list
            iconLabels2.add(iconLabel);
            userNames2.add(userName);
            percentageIcons2.add(percentageIcon);
            percentages_toPay2.add(percentage_toPay);
            amounts_converted2.add(amount_converted);
            maxima2.add(spinnerMaximum);

            i++;
        }

        // add sub panel 2
        paymentSplitSubPanelPERCENTAGE = new PaymentSplitSubPanelPERCENTAGE(iconLabels2, userNames2, percentages_toPay2, percentageIcons2, amounts_converted2);
        paymentSplitPanel.setContentPanel(1, paymentSplitSubPanelPERCENTAGE, iconLabels2.size() > 5);
    }

    public void setTicket(Ticket t) {
        this.currentTicket = t;
    }

    private FocusListener getFocusListener(int i) {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (justRefreshed) {
                    justRefreshed = false;
                    return;
                }

                lastFocusIn = i;
                fullRedrawPercentagePanel();
            }

            @Override
            public void focusLost(FocusEvent e) {}
        };
    }

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

    private void calculateShareForPercentage(int i) {
        // get Spinner info
        ArrayList<JLabel> amounts_converted_list = paymentSplitSubPanelPERCENTAGE.amounts_converted;
        ArrayList<JSpinner> percentages_list = paymentSplitSubPanelPERCENTAGE.percentages_toPay;
        JSpinner selectedSpinner = percentages_list.get(i);

        // commitEdit
        try { selectedSpinner.commitEdit(); }
        catch (Exception e) {}

        // display
        Integer spinnerValue = (Integer)selectedSpinner.getValue();
        Double spinnerValueDouble = Double.valueOf(spinnerValue);
        Double calculation = spinnerValueDouble / 100 * currentTicket.getTotalSum();
        String showValue = String. format("%.2f", calculation);
        amounts_converted_list.get(i).setText("$" + showValue);
    }
}