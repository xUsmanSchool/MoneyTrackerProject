package View.panels;

import Database.PersonsDB;
import javax.swing.*;

public class UserCreationPanel extends JPanel {
    private JButton createButton;

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
        JLabel sexLabel = new JLabel("Sex: ");
        JLabel birthdateLabel = new JLabel("Date of birth: ");

        JTextField firstNameTextField = new JTextField("");
        JTextField lastNameTextField = new JTextField("");
        JTextField phoneNumberTextField = new JTextField("");

        String[] sexOptions = {"Male", "Female", "Rather not say"};
        JComboBox jComboBoxSex = new JComboBox(sexOptions);

        int days = 31;
        String[] dayOptions = new String[days];
        for (int i=0; i<days; i++) dayOptions[i] = Integer.toString(i+1);
        JComboBox jComboBoxD = new JComboBox(dayOptions);

        int months = 12;
        String[] monthOptions = new String[months];
        for (int i=0; i<months; i++) monthOptions[i] = Integer.toString(i+1);
        JComboBox jComboBoxM = new JComboBox(monthOptions);

        int years = 100;
        String[] yearOptions = new String[years];
        for (int i=0; i<years; i++) yearOptions[i] = Integer.toString(i+1960);
        JComboBox jComboBoxY = new JComboBox(yearOptions);

        this.createButton = new JButton("Create user");

        // voor layout uitleg, zie:
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/groupExample.html

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(firstNameLabel)
                        .addComponent(lastNameLabel)
                        .addComponent(phoneNumberLabel)
                        .addComponent(sexLabel)
                        .addComponent(birthdateLabel)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(firstNameTextField)
                        .addComponent(lastNameTextField)
                        .addComponent(phoneNumberTextField)
                        .addComponent(jComboBoxSex)
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
                        .addComponent(sexLabel)
                        .addComponent(jComboBoxSex)
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
