package ViewController.PaymentSplits;

import DatabaseController.PersonsDBController;
import DatabaseController.TicketsDBController;
import HelperClass.Paths;
import Model.Person;
import Model.Ticket;
import View.others.CustomColors;
import View.others.Router;
import View.panels.PaymentSplits.PaymentSplitPanelWithBorder;
import View.panels.PaymentSplits.PaymentSplitSubPanelCASH;
import View.panels.PaymentSplits.PaymentSplitSubPanelPERCENTAGE;
import ViewController.ViewController;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
        // get field values
        ArrayList<Double> valueList = getOldCashAmountValues();
        ArrayList<Person> personList = currentTicket.getPersonArrayList();

        // update ticket
        if (valueList.size() == personList.size()) {
            for (int i = 0; i < valueList.size(); i++) currentTicket.addSCashSplit(personList.get(i), valueList.get(i));
            ticketsDBController.add(currentTicket);

            // go back
            Router.getInstance().goBack();
        }
    }

    private void percentageDoneButtonActionListener() {
        // get field values
        ArrayList<Integer> valueList = getOldPercentageSpinnerFields();
        ArrayList<Person> personList = currentTicket.getPersonArrayList();

        // update ticket
        if (valueList.size() == personList.size()) {
            for (int i = 0; i < valueList.size(); i++) currentTicket.addPercentageSplit(personList.get(i), Double.valueOf(valueList.get(i))/100);
            ticketsDBController.add(currentTicket);

            // go back
            Router.getInstance().goBack();
        }
    }

    private void fullRedrawPercentagePanel() {
        // !!! prevent endless looping
        justRefreshed = true;

        // not sure if needed or if they are automatically removed when panel is cleared
        // disactivatePercentageFocusListeners();

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

    private void updateSpinnerModels() {
        // get info for panel generation
        ArrayList<Person> persons = personsDBController.getAll();

        // return null if non existent
        if (paymentSplitSubPanelPERCENTAGE == null) System.out.println("is null");

        // get old fields
        ArrayList<JSpinner> oldjSpinners = paymentSplitSubPanelPERCENTAGE.getPercentages_toPay();
        ArrayList<Integer> list = getOldPercentageSpinnerFields();

        int spinnerMaximum = 100;
        int initialValue = 0;

        Integer percentageSum = getArraySum(list);
        if (percentageSum > 100) percentageSum = 100; // prevent crashes, fix below in stateChanged
        Integer remainingPercentage = 100 - percentageSum;

        for (int i = 0; i < persons.size(); i++) {
            initialValue = list.get(i);
            spinnerMaximum = 100;
            spinnerMaximum = initialValue + remainingPercentage;

            SpinnerModel model = new SpinnerNumberModel(
                    initialValue,           //initial value
                    0,                      //min
                    spinnerMaximum,         //max
                    1);                     //step

            int finalSpinnerMaximum = spinnerMaximum;
            model.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if ((int)((SpinnerNumberModel) e.getSource()).getValue() > finalSpinnerMaximum) ((SpinnerNumberModel) e.getSource()).setValue(finalSpinnerMaximum);
                }
            });

            // set model on spinner
            oldjSpinners.get(i).setModel(model);
        }

        // reset focus so that the user doesn't notice anything
        Objects.requireNonNull(getTextField(oldjSpinners.get(lastFocusIn))).grabFocus();
        Objects.requireNonNull(getTextField(oldjSpinners.get(lastFocusIn))).requestFocus();
        Objects.requireNonNull(getTextField(oldjSpinners.get(lastFocusIn))).requestFocusInWindow();
        lastFocusIn = 0;

        paymentSplitSubPanelPERCENTAGE.redraw();
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

    private ArrayList<Double> getOldCashAmountValues() {
        // return null if non existent
        if (paymentSplitSubPanelCASH == null) return null;

        // get old fields
        ArrayList<JFormattedTextField> oldTextFields = paymentSplitSubPanelCASH.getAmounts();
        ArrayList<Double> oldValues = new ArrayList<>();

        // save the old field values as doubles
        for (JFormattedTextField textField : oldTextFields) oldValues.add((Double)textField.getValue());

        return oldValues;
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

    private Double getDoubleArraySum(ArrayList<Double> list) {
        double sum = 0;
        for (Double d:list) sum += d;

        return sum;
    }

    private void createCashPanelFields() {
        // left
        ArrayList<JLabel> iconLabels1 = new ArrayList<>();
        ArrayList<JLabel> userNames1 = new ArrayList<>();
        ArrayList<JLabel> moneyIcons1 = new ArrayList<>();
        ArrayList<JFormattedTextField> amounts_toPay1 = new ArrayList<>();

        ArrayList<Person> persons = currentTicket.getPersonArrayList();
        Person payedBy = currentTicket.getPayedByValue();
        Double ticketValueToPay = currentTicket.getTotalSum();

        for (Person person : persons) {
            // create
            JLabel iconLabel1 = new JLabel(new ImageIcon(Paths.iconPath + (person.getIconValue().length() == 0 ? "user_icon_small.png" : person.getIconValue())));
            JLabel userName1 = new JLabel(person.getFirstNameValue() + " " + person.getLastNameValue());
            JLabel moneyIcon = new JLabel("$");

            JFormattedTextField amount_toPay = new JFormattedTextField(createFormatter2(1000.00));
            amount_toPay.setValue(0.00);

            // todo - could update limiter
            // amount_toPay.setFormatterFactory(new DefaultFormatterFactory(createFormatter2(50.00)));

            amount_toPay.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    paymentSplitSubPanelCASH.setRemainingAmount(currentTicket.getTotalSum()-getDoubleArraySum(Objects.requireNonNull(getOldCashAmountValues())));
                }
            });

            // style
            iconLabel1.setForeground(Color.WHITE);
            userName1.setForeground(person == payedBy ? CustomColors.getYellow() : Color.WHITE);
            moneyIcon.setForeground(Color.WHITE);
            userName1.setMinimumSize(new Dimension(280,30));

            // add to list
            iconLabels1.add(iconLabel1);
            userNames1.add(userName1);
            moneyIcons1.add(moneyIcon);
            amounts_toPay1.add(amount_toPay);
        }

        // add sub panel 1
        paymentSplitSubPanelCASH = new PaymentSplitSubPanelCASH(iconLabels1, userNames1, moneyIcons1, amounts_toPay1);
        paymentSplitPanel.setContentPanel(0, paymentSplitSubPanelCASH, iconLabels1.size() > 5);
        paymentSplitSubPanelCASH.setRemainingAmount(currentTicket.getTotalSum());
    }

    private void createPercentagePanelFields() {
        // right
        ArrayList<JLabel> iconLabels2 = new ArrayList<>();
        ArrayList<JLabel> userNames2 = new ArrayList<>();
        ArrayList<JLabel> percentageIcons2 = new ArrayList<>();
        ArrayList<JSpinner> percentages_toPay2 = new ArrayList<>();
        ArrayList<JLabel> amounts_converted2 = new ArrayList<>();

        Person payedBy = currentTicket.getPayedByValue();
        ArrayList<Person> persons = currentTicket.getPersonArrayList();

        int i = 0;
        for (Person person : persons) {
            // create
            JLabel iconLabel = new JLabel(new ImageIcon(Paths.iconPath + (person.getIconValue().length() == 0 ? "user_icon_small.png" : person.getIconValue())));
            JLabel userName = new JLabel(person.getFirstNameValue() + " " + person.getLastNameValue());
            JLabel percentageIcon = new JLabel("%");
            /*
            Integer spinnerMaximum = 100;

            // spinner maximum calculations
            ArrayList<Integer> list = getOldPercentageSpinnerFields();
            Integer percentageSum = 0;
            if (list != null) {
                percentageSum = getArraySum(list);
                Integer remainingPercentage = 100 - percentageSum;
                spinnerMaximum = list.get(i) + remainingPercentage;
            }

            // init spinner
            int convMaximum = spinnerMaximum.intValue();
            SpinnerModel model = new SpinnerNumberModel(
                    0,                //initial value
                    0,                      //min
                    convMaximum,            //max
                    1);                     //step
             */

            SpinnerModel model = new SpinnerNumberModel(
                    0,                //initial value
                    0,                      //min
                    100,                    //max
                    1);                     //step

            // set formatters on spinner
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

            // propertyChange vars
            int finalI = i;
            txt.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    calculateShareForPercentage(finalI);

                    // activate button if all percentages add up to 100
                    paymentSplitSubPanelPERCENTAGE.getDoneButton().setEnabled(percentagesAddUptoHundred());
                }
            });

            percentageIcon.setForeground(Color.WHITE);
            amount_converted.setForeground(CustomColors.getYellow());
            amount_converted.setMinimumSize(new Dimension(53,30));
            amount_converted.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
            userName.setMinimumSize(new Dimension(210,30));
            percentage_toPay.setMinimumSize(new Dimension(53,20));

            // add to list
            iconLabels2.add(iconLabel);
            userNames2.add(userName);
            percentageIcons2.add(percentageIcon);
            percentages_toPay2.add(percentage_toPay);
            amounts_converted2.add(amount_converted);

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
                lastFocusIn = i;
                updateSpinnerModels();
            }

            @Override
            public void focusLost(FocusEvent e) {}
        };
    }

    private NumberFormatter createFormatter1() {
        // https://stackoverflow.com/questions/8658205/format-currency-without-currency-symbol
        // https://stackoverflow.com/questions/12806278/double-decimal-formatting-in-java

        DecimalFormat formatter = new DecimalFormat("##0.00");
        NumberFormatter numberFormatter1 = null;
        try {
            numberFormatter1 = new NumberFormatter(formatter);
            numberFormatter1.setAllowsInvalid(false);
            numberFormatter1.setOverwriteMode(true);
            numberFormatter1.setCommitsOnValidEdit(true);
            numberFormatter1.setMinimum(0.00);
            numberFormatter1.setMaximum(100.00);
        } catch (Exception e) { System.err.println(e.toString()); }

        return numberFormatter1;
    }

    private NumberFormatter createFormatter2(Double maximum) {
        //https://www.tabnine.com/code/java/classes/javax.swing.text.NumberFormatter

        NumberFormat format = NumberFormat.getCurrencyInstance();
        String pattern = ((DecimalFormat) format).toPattern();
        String newPattern = pattern.replace("\u00A4", "").trim();
        NumberFormat newFormat = new DecimalFormat(newPattern);
        NumberFormatter formatter = new NumberFormatter(newFormat);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.00);
        formatter.setMaximum(maximum);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        return formatter;
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

    private boolean percentagesAddUptoHundred() {
        ArrayList<Integer> list = getOldPercentageSpinnerFields();
        if (list != null) return getArraySum(list) == 100;
        else return false;
    }
}