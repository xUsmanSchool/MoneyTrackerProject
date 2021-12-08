package View.panels.AddTicketsPanel;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AddTicketsPanel extends JPanel {
    private final JLabel descriptionLabel, ticketTypeLabel, payedAmountLabel, payedByLabel, splitTypeLabel, iconLabel;
    private ImageIcon icon;
    private JButton createOrContinueTicketButton;
    private JTextField descriptionTextField;
    private JFormattedTextField payedAmountTextField;
    private JComboBox<String> eventsJComboBox, personJComboBox, splitTypeJComboBox;
    private final static String[] EMPTY_STRING = new String[0];

    public AddTicketsPanel() {
        // set layout
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        this.setLayout(layout);

        // icons
        icon = new ImageIcon();

        // create fields
        iconLabel = new JLabel(icon);
        descriptionLabel = new JLabel();
        ticketTypeLabel = new JLabel();
        payedAmountLabel = new JLabel();
        payedByLabel = new JLabel();
        splitTypeLabel = new JLabel();

        descriptionTextField = new JTextField();
        payedAmountTextField = new JFormattedTextField(createMaskFormatter());
        payedAmountTextField.setValue(0.00);

        // selection box
        eventsJComboBox = new JComboBox<>(EMPTY_STRING);
        personJComboBox = new JComboBox<>(EMPTY_STRING);
        splitTypeJComboBox = new JComboBox<>(EMPTY_STRING);

        // buttons
        createOrContinueTicketButton = new JButton();

        // more layouts, for explanation see: https://docs.oracle.com/javase/tutorial/uiswing/layout/groupExample.html
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(descriptionLabel)
                                        .addComponent(ticketTypeLabel)
                                        .addComponent(payedAmountLabel)
                                        .addComponent(payedByLabel)
                                        .addComponent(splitTypeLabel)
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(descriptionTextField)
                                        .addComponent(eventsJComboBox)
                                        .addComponent(payedAmountTextField)
                                        .addComponent(personJComboBox)
                                        .addComponent(splitTypeJComboBox)
                                )
                        )
                        .addComponent(createOrContinueTicketButton, GroupLayout.Alignment.CENTER)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(descriptionLabel)
                                .addComponent(descriptionTextField)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ticketTypeLabel)
                                .addComponent(eventsJComboBox)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(payedAmountLabel)
                                .addComponent(payedAmountTextField)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(payedByLabel)
                                .addComponent(personJComboBox)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(splitTypeLabel)
                                .addComponent(splitTypeJComboBox)
                        )
                        .addComponent(createOrContinueTicketButton)
        );
    }

    // cannot seem to update at runtime due to javas doo-doo code
    private NumberFormatter createMaskFormatter() {
        // https://stackoverflow.com/questions/8658205/format-currency-without-currency-symbol
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

    public JLabel getIconLabel() {
        return iconLabel;
    }

    public void setIconLabelImage(Icon icon) {
        iconLabel.setIcon(icon);
    }

    public JLabel getDescriptionLabel() {
        return descriptionLabel;
    }

    public void setDescriptionLabelText(String descriptionLabelText) {
        this.descriptionLabel.setText(descriptionLabelText);
    }

    public JTextField getDescriptionTextField() {
        return descriptionTextField;
    }

    public JLabel getTicketTypeLabel() {
        return ticketTypeLabel;
    }

    public void setTicketTypeLabelText(String ticketTypeLabelText) {
        this.ticketTypeLabel.setText(ticketTypeLabelText);
    }

    public JLabel getPayedAmountLabel() {
        return payedAmountLabel;
    }

    public void setPayedAmountLabelText(String payedAmountLabelText) {
        this.payedAmountLabel.setText(payedAmountLabelText);
    }

    public Double getPayedAmountTextFieldValue() {
        return (Double)payedAmountTextField.getValue();
    }

    public JFormattedTextField getPayedAmountTextField() {
        return payedAmountTextField;
    }

    public JLabel getPayedByLabel() {
        return payedByLabel;
    }

    public void setPayedByLabelText(String payedByLabelText) {
        this.payedByLabel.setText(payedByLabelText);
    }

    public JLabel getSplitTypeLabel() {
        return splitTypeLabel;
    }

    public void setSplitTypeLabelText(String splitTypeLabelText) {
        this.splitTypeLabel.setText(splitTypeLabelText);
    }

    public JButton getCreateOrContinueTicketButton() {
        return createOrContinueTicketButton;
    }

    public void setCreateOrContinueTicketButtonText(String createOrContinueTicketButtonText) {
        this.createOrContinueTicketButton.setText(createOrContinueTicketButtonText);
    }

    public JComboBox<String> getEventsJComboBox() {
        return eventsJComboBox;
    }

    public void setEventsJComboBox(JComboBox<String> eventsJComboBox) {
        this.eventsJComboBox = eventsJComboBox;
    }

    public JComboBox<String> getPersonJComboBox() {
        return personJComboBox;
    }

    public void setPersonJComboBox(JComboBox<String> personJComboBox) {
        this.personJComboBox = personJComboBox;
    }

    public JComboBox<String> getSplitTypeJComboBox() {
        return splitTypeJComboBox;
    }

    public void setSplitTypeJComboBox(JComboBox<String> splitTypeJComboBox) {
        this.splitTypeJComboBox = splitTypeJComboBox;
    }
}
