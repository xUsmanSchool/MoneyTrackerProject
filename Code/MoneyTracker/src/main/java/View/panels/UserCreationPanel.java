package View.panels;

import Controller.RegistrationController;
import Database.Person;
import Database.PersonsDB;
import HelperClass.Date;
import HelperClass.EnumConverter;
import HelperClass.Gender;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.util.Calendar;
import java.util.Objects;

public class UserCreationPanel extends JPanel {
    private final JButton createButton;
    private final JTextField firstNameTextField, lastNameTextField;
    private final JFormattedTextField phoneNumberTextField;
    private final JComboBox<String> jComboBoxGender, jComboBoxD, jComboBoxM, jComboBoxY;
    private final Calendar cal;
    private int currentMaxDays = 31;

    PersonsDB personsDB = PersonsDB.getInstance();
    RegistrationController registrationController = new RegistrationController(personsDB);

    public UserCreationPanel() {
        // set layout
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        this.setLayout(layout);

        // fill in labels
        JLabel firstNameLabel = new JLabel("First name: ");
        JLabel lastNameLabel = new JLabel("Last name: ");
        JLabel phoneNumberLabel = new JLabel("Phone number*: ");
        JLabel genderLabel = new JLabel("Gender*: ");
        JLabel birthdateLabel = new JLabel("Date of birth*: ");

        // create formatter for phone number field
        MaskFormatter numberFormatter = null;
        try {
            numberFormatter = new MaskFormatter("####-##-##-##");
            numberFormatter.setPlaceholderCharacter('#');
            numberFormatter.setValidCharacters("0123456789");
            numberFormatter.setOverwriteMode(true);
        } catch (Exception e) { System.err.println(e.toString()); }

        // create all other fields
        firstNameTextField = new JTextField("");
        lastNameTextField = new JTextField("");
        phoneNumberTextField = new JFormattedTextField(numberFormatter);
        phoneNumberTextField.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);

        String[] o = EnumConverter.enumToString(Gender.values());
        jComboBoxGender = new JComboBox<>(o);

        cal = Calendar.getInstance();
        cal.set(1960, 0, 1);
        int currentMaxMonths = 12, currentYears = 100;

        jComboBoxD = new JComboBox<>(createDayOptions(currentMaxDays));
        jComboBoxM = new JComboBox<>(createMonthOptions(currentMaxMonths));
        jComboBoxY = new JComboBox<>(createYearOptions(currentYears));

        // buttons
        this.createButton = new JButton("Create user");

        // more layouts, for explanation see:
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/groupExample.html
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(firstNameLabel)
                        .addComponent(lastNameLabel)
                        .addComponent(phoneNumberLabel)
                        .addComponent(genderLabel)
                        .addComponent(birthdateLabel)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(firstNameTextField)
                        .addComponent(lastNameTextField)
                        .addComponent(phoneNumberTextField)
                        .addComponent(jComboBoxGender)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBoxD)
                                .addComponent(jComboBoxM)
                                .addComponent(jComboBoxY))
                        .addComponent(createButton)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(firstNameLabel)
                        .addComponent(firstNameTextField)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lastNameLabel)
                        .addComponent(lastNameTextField)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(phoneNumberLabel)
                        .addComponent(phoneNumberTextField)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(genderLabel)
                        .addComponent(jComboBoxGender)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(birthdateLabel)
                        .addComponent(jComboBoxD)
                        .addComponent(jComboBoxM)
                        .addComponent(jComboBoxY)
                )
                .addComponent(createButton)
        );

        dayChangedActionListener();
        monthChangedActionListener();
        yearChangedActionListener();
        addAccountCreatedActionListener();
    }

    public void addAccountCreatedActionListener()
    {
        this.createButton.addActionListener(listener ->
        {
            if (checkFieldsForValidity()) {
                registrationController.register(createPerson());
                clearForm();
            }
        });
    }

    public void dayChangedActionListener()
    {
        this.jComboBoxD.addActionListener(listener ->
        {
            //
        });
    }

    public void monthChangedActionListener()
    {
        this.jComboBoxM.addActionListener(listener ->
        {
            adjustDayForMonthAndYear();
        });
    }

    public void yearChangedActionListener()
    {
        this.jComboBoxY.addActionListener(listener ->
        {
            adjustDayForMonthAndYear();
        });
    }

    private void adjustDayForMonthAndYear() {
        cal.set(readYears(), readMonths()-1, 1);
        int maxDays = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        System.out.println(maxDays);

        if (readDays() > maxDays) {
            jComboBoxD.setModel(new DefaultComboBoxModel<>(createDayOptions(maxDays)));
            jComboBoxD.setSelectedIndex(maxDays -1);
        } else if (currentMaxDays != maxDays) {
            int currentSelectedIndex = jComboBoxD.getSelectedIndex();
            jComboBoxD.setModel(new DefaultComboBoxModel<>(createDayOptions(maxDays)));
            jComboBoxD.setSelectedIndex(currentSelectedIndex);
        }
        currentMaxDays = maxDays;
    }

    private Person createPerson() {
        String firstNameText = firstNameTextField.getText();
        String lastNameText = lastNameTextField.getText();
        String phoneNumberText = phoneNumberTextField.getText();
        Gender genderObject = Gender.valueOf((String)jComboBoxGender.getSelectedItem());
        int days = readDays();
        int months = readMonths();
        int years = readYears();

        Person p = new Person(firstNameText, lastNameText);
        p.setPhoneNumber(phoneNumberText);
        p.setGender(genderObject);
        p.setBirthDate(new Date().getDate(days, months, years));
        return p;
    }

    private int readDays() {
        return Integer.parseInt((String) Objects.requireNonNull(jComboBoxD.getSelectedItem()));
    }

    private String[] createDayOptions(int maxDays) {
        String[] dayOptions = new String[maxDays];
        for (int i=0; i<maxDays; i++) dayOptions[i] = Integer.toString(i+1);
        return dayOptions;
    }

    private int readMonths() {
        return Integer.parseInt((String) Objects.requireNonNull(jComboBoxM.getSelectedItem()));
    }

    private String[] createMonthOptions(int maxMonths) {
        String[] monthOptions = new String[maxMonths];
        for (int i=0; i<maxMonths; i++) monthOptions[i] = Integer.toString(i+1);
        return monthOptions;
    }

    private int readYears() {
        return Integer.parseInt((String) Objects.requireNonNull(jComboBoxY.getSelectedItem()));
    }

    private String[] createYearOptions(int years) {
        String[] yearOptions = new String[years];
        for (int i=0; i<years; i++) yearOptions[i] = Integer.toString(i+1960);
        return yearOptions;
    }

    private boolean checkFieldsForValidity() {
        boolean ok = true;

        if (firstNameTextField.getText().length() == 0) {
            firstNameTextField.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED)); ok = false;
        } else firstNameTextField.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));

        if (lastNameTextField.getText().length() == 0) {
            lastNameTextField.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED)); ok = false;
        } else lastNameTextField.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));

        return ok;
    }

    private void clearForm() {
        firstNameTextField.requestFocus();
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        phoneNumberTextField.setText("");
        jComboBoxGender.setSelectedIndex(0);
        jComboBoxD.setSelectedIndex(0);
        jComboBoxM.setSelectedIndex(0);
        jComboBoxY.setSelectedIndex(0);
    }
}
