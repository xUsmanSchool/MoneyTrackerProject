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
import java.util.Objects;

public class UserCreationPanel extends JPanel {
    private final JButton createButton;
    private final JTextField firstNameTextField, lastNameTextField;
    private final JFormattedTextField phoneNumberTextField;
    private final JComboBox<String> jComboBoxGender, jComboBoxD, jComboBoxM, jComboBoxY;

    PersonsDB personsDB = PersonsDB.getInstance();
    RegistrationController registrationController = new RegistrationController(personsDB);

    public UserCreationPanel() {
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        this.setLayout(layout);

        JLabel firstNameLabel = new JLabel("First name: ");
        JLabel lastNameLabel = new JLabel("Last name: ");
        JLabel phoneNumberLabel = new JLabel("Phone number*: ");
        JLabel genderLabel = new JLabel("Gender*: ");
        JLabel birthdateLabel = new JLabel("Date of birth*: ");

        firstNameTextField = new JTextField("");
        lastNameTextField = new JTextField("");

        MaskFormatter numberFormatter = null;
        try {
            numberFormatter = new MaskFormatter("####-##-##-##");
            numberFormatter.setPlaceholderCharacter('#');
            numberFormatter.setValidCharacters("0123456789");
            numberFormatter.setOverwriteMode(true);
        } catch (Exception e) { System.err.println(e.toString()); }

        phoneNumberTextField = new JFormattedTextField(numberFormatter);
        phoneNumberTextField.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);

        String[] o = EnumConverter.enumToString(Gender.values());
        jComboBoxGender = new JComboBox<>(o);

        int days = 31, months = 12, years = 100;
        String[] dayOptions = new String[days];
        String[] monthOptions = new String[months];
        String[] yearOptions = new String[years];

        for (int i=0; i<days; i++) dayOptions[i] = Integer.toString(i+1);
        jComboBoxD = new JComboBox<>(dayOptions);

        for (int i=0; i<months; i++) monthOptions[i] = Integer.toString(i+1);
        jComboBoxM = new JComboBox<>(monthOptions);

        for (int i=0; i<years; i++) yearOptions[i] = Integer.toString(i+1960);
        jComboBoxY = new JComboBox<>(yearOptions);

        this.createButton = new JButton("Create user");

        // voor layout uitleg, zie:
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

    private Person createPerson() {
        String firstNameText = firstNameTextField.getText();
        String lastNameText = lastNameTextField.getText();
        String phoneNumberText = phoneNumberTextField.getText();
        Gender genderObject = Gender.valueOf((String)jComboBoxGender.getSelectedItem());
        int days = Integer.parseInt((String) Objects.requireNonNull(jComboBoxD.getSelectedItem()));
        int months = Integer.parseInt((String) Objects.requireNonNull(jComboBoxM.getSelectedItem()));
        int years = Integer.parseInt((String) Objects.requireNonNull(jComboBoxY.getSelectedItem()));

        Person p = new Person(firstNameText, lastNameText);
        p.setPhoneNumber(phoneNumberText);
        p.setGender(genderObject);
        p.setBirthDate(new Date().getDate(days, months, years));
        return p;
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
