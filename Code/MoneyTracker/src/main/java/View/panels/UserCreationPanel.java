package View.panels;

import Database.PersonsDB;
import HelperClass.EnumConverter;
import HelperClass.Gender;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class UserCreationPanel extends JPanel {
    private final JButton createButton;

    PersonsDB personsDB = PersonsDB.getInstance();
    // todo

    public UserCreationPanel() {
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        this.setLayout(layout);

        JLabel firstNameLabel = new JLabel("First name: ");
        JLabel lastNameLabel = new JLabel("Last name: ");
        JLabel phoneNumberLabel = new JLabel("Phone number: ");
        JLabel genderLabel = new JLabel("Gender: ");
        JLabel birthdateLabel = new JLabel("Date of birth: ");

        JTextField firstNameTextField = new JTextField("");
        JTextField lastNameTextField = new JTextField("");
        JTextField phoneNumberTextField = new JTextField("");

        String[] o = EnumConverter.enumToString(Gender.values());
        JComboBox<String> jComboBoxGender = new JComboBox<>(o);

        int days = 31;
        String[] dayOptions = new String[days];
        for (int i=0; i<days; i++) dayOptions[i] = Integer.toString(i+1);
        JComboBox<String> jComboBoxD = new JComboBox<>(dayOptions);

        int months = 12;
        String[] monthOptions = new String[months];
        for (int i=0; i<months; i++) monthOptions[i] = Integer.toString(i+1);
        JComboBox<String> jComboBoxM = new JComboBox<>(monthOptions);

        int years = 100;
        String[] yearOptions = new String[years];
        for (int i=0; i<years; i++) yearOptions[i] = Integer.toString(i+1960);
        JComboBox<String> jComboBoxY = new JComboBox<>(yearOptions);

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
            System.out.println("create account -- this button doesn't do anything");
            // todo
        });
    }
}
