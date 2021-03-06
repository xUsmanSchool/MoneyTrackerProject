package View.panels.AddUserWindow;

import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;
import javax.swing.*;
import java.awt.*;

public class UserCreationPanel extends JPanel {
    private final JButton createButton, gotoGlobalBillButton;
    private final JLabel firstNameLabel, lastNameLabel, phoneNumberLabel, genderLabel, birthdateLabel;
    private final JTextField firstNameTextField, lastNameTextField;
    public JFormattedTextField phoneNumberTextField;
    private final JComboBox<String> jComboBoxGender, jComboBoxD, jComboBoxM, jComboBoxY;
    private final static String[] EMPTY_STRING = new String[0];
    private ImageIcon icon;
    private String iconName;
    private final JButton imageButton;

    public UserCreationPanel() {
        // set layout
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        this.setLayout(layout);

        // icons
        icon = new ImageIcon();

        // create fields
        firstNameLabel = new JLabel();
        lastNameLabel = new JLabel();
        phoneNumberLabel = new JLabel();
        genderLabel = new JLabel();
        birthdateLabel = new JLabel();

        firstNameTextField = new JTextField();
        lastNameTextField = new JTextField();
        phoneNumberTextField = new JFormattedTextField(createMaskFormatter());
        phoneNumberTextField.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);

        // selection box
        jComboBoxGender = new JComboBox<>(EMPTY_STRING);
        jComboBoxD = new JComboBox<>(EMPTY_STRING);
        jComboBoxM = new JComboBox<>(EMPTY_STRING);
        jComboBoxY = new JComboBox<>(EMPTY_STRING);

        // buttons
        this.imageButton = new JButton();
        this.imageButton.setIcon(icon);
        this.createButton = new JButton();
        this.gotoGlobalBillButton = new JButton();

        // more layouts, for explanation see: https://docs.oracle.com/javase/tutorial/uiswing/layout/groupExample.html
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(firstNameLabel)
                        .addComponent(lastNameLabel)
                        .addComponent(phoneNumberLabel)
                        .addComponent(genderLabel)
                        .addComponent(birthdateLabel)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(imageButton)
                        .addComponent(firstNameTextField)
                        .addComponent(lastNameTextField)
                        .addComponent(phoneNumberTextField)
                        .addComponent(jComboBoxGender)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBoxD)
                                .addComponent(jComboBoxM)
                                .addComponent(jComboBoxY))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(createButton)
                                .addComponent(gotoGlobalBillButton)
                        )
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(imageButton)
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
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(createButton)
                        .addComponent(gotoGlobalBillButton)
                )
        );
    }

    // cannot seem to update at runtime due to javas doo-doo code
    private MaskFormatter createMaskFormatter() {
        MaskFormatter numberFormatter = null;

        try {
            numberFormatter = new MaskFormatter("####-##-##-##");
            numberFormatter.setPlaceholderCharacter('#');
            numberFormatter.setValidCharacters("0123456789");
            numberFormatter.setOverwriteMode(true);
            numberFormatter.setValueContainsLiteralCharacters(false);
        } catch (Exception e) { System.err.println(e.toString()); }

        return numberFormatter;
    }

    public void setImage(String imagePath, String text) {
        iconName = imagePath;
        icon = new ImageIcon(imagePath);
        imageButton.setText(text);
        imageButton.setIcon(icon);
    }

    public JButton getImageButton() {
        return imageButton;
    }

    public String getIconName() {
        return iconName;
    }

    public DefaultComboBoxModel<String> getDefaultComboBoxModel(String[] options) {
        return new DefaultComboBoxModel<String>(options);
    }

    public JButton getGotoGlobalBillButton() {
        return gotoGlobalBillButton;
    }

    public Border getRedBorder() {
        return BorderFactory.createLineBorder(Color.RED);
    }

    public Border getGreyBorder() {
        return BorderFactory.createLineBorder(Color.GRAY);
    }

    public JLabel getFirstNameLabel() {
        return firstNameLabel;
    }

    public JLabel getLastNameLabel() {
        return lastNameLabel;
    }

    public JLabel getPhoneNumberLabel() {
        return phoneNumberLabel;
    }

    public JLabel getGenderLabel() {
        return genderLabel;
    }

    public JLabel getBirthdateLabel() {
        return birthdateLabel;
    }

    public JTextField getFirstNameTextField() {
        return firstNameTextField;
    }

    public JTextField getLastNameTextField() {
        return lastNameTextField;
    }

    public JFormattedTextField getPhoneNumberTextField() {
        return phoneNumberTextField;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JComboBox<String> getJComboBoxDay() {
        return jComboBoxD;
    }

    public JComboBox<String> getJComboBoxMonth() {
        return jComboBoxM;
    }

    public JComboBox<String> getJComboBoxYear() {
        return jComboBoxY;
    }

    public JComboBox<String> getJComboBoxGender() {
        return jComboBoxGender;
    }
}