package ViewController.AddUserWindow;

import DatabaseController.*;
import HelperClass.*;
import Model.*;
import Observers.ImageFrameIconObservableEntry;
import Observers.PersonDBObservableEntry;
import View.frames.IconSelectorFrame;
import View.others.CustomColors;
import View.panels.AddUserWindow.UserCreationPanel;
import ViewController.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Objects;
import java.util.Observable;

public class UserCreationPanelController extends ViewController {
    private final UserCreationPanelController userCreationPanelController;
    private final PersonsDBController personsDatabaseController;
    private final UserCreationPanel userCreationPanel;
    public static boolean ImageSelectorFrameOpen = false;
    private String setImageIconName, setImageButtonText;

    public UserCreationPanelController(PersonsDBController personsDatabaseController, UserCreationPanel userCreationPanel) {
        this.userCreationPanel = userCreationPanel;
        this.personsDatabaseController = personsDatabaseController;
        this.userCreationPanelController = this;
    }

    @Override
    public void init() {
        this.setImageIconName = "add_picture.png";
        this.setImageButtonText = "Select an icon";

        // JComboBox
        for(String s : EnumConverter.enumToString(Gender.values())) userCreationPanel.getJComboBoxGender().addItem(s);
        for(String s : createDayOptions(31)) userCreationPanel.getJComboBoxDay().addItem(s);
        for(String s : createMonthOptions(12)) userCreationPanel.getJComboBoxMonth().addItem(s);
        for(String s : createYearOptions(100)) userCreationPanel.getJComboBoxYear().addItem(s);

        // text
        userCreationPanel.setImage(Paths.iconPath + setImageIconName, setImageButtonText);
        userCreationPanel.getFirstNameLabel().setText("First name: ");
        userCreationPanel.getLastNameLabel().setText("Last name: ");
        userCreationPanel.getPhoneNumberLabel().setText("Phone number*: ");
        userCreationPanel.getGenderLabel().setText("Gender*: ");
        userCreationPanel.getBirthdateLabel().setText("Date of birth*: ");
        userCreationPanel.getCreateButton().setText("Add user");
        userCreationPanel.getGotoGlobalBillButton().setText("View tickets");

        // styling
        userCreationPanel.setBackground(CustomColors.getMidGrey());
        userCreationPanel.getFirstNameLabel().setForeground(Color.WHITE);
        userCreationPanel.getLastNameLabel().setForeground(Color.WHITE);
        userCreationPanel.getPhoneNumberLabel().setForeground(Color.WHITE);
        userCreationPanel.getGenderLabel().setForeground(Color.WHITE);
        userCreationPanel.getBirthdateLabel().setForeground(Color.WHITE);
        userCreationPanel.getCreateButton().setBackground(CustomColors.getYellow());
        userCreationPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        userCreationPanel.getGotoGlobalBillButton().setBackground(CustomColors.getYellow());
        userCreationPanel.getImageButton().setBackground(CustomColors.getYellow());
        userCreationPanel.getImageButton().setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        userCreationPanel.getImageButton().setContentAreaFilled(true);

        // focus
        userCreationPanel.getFirstNameTextField().setFocusable(true);
    }

    @Override
    public void activateActionListeners() {
        userCreationPanel.getCreateButton().addActionListener(e -> addAccountCreatedActionListener());
        userCreationPanel.getJComboBoxMonth().addActionListener(e -> adjustDayForMonthAndYearActionListener());
        userCreationPanel.getJComboBoxYear().addActionListener(e -> adjustDayForMonthAndYearActionListener());
        userCreationPanel.getImageButton().addMouseListener(iconSelectorMouseListener());

        userCreationPanel.getFirstNameTextField().addFocusListener(
                getPlaceHolderFocusListener(userCreationPanel.getFirstNameTextField(), "Type firstname here", "Dialog"));
        userCreationPanel.getLastNameTextField().addFocusListener(
                getPlaceHolderFocusListener(userCreationPanel.getLastNameTextField(), "Type lastname here", "Dialog"));
        enablePlaceholder(userCreationPanel.getFirstNameTextField(), "Type firstname here", "Dialog");
        enablePlaceholder(userCreationPanel.getLastNameTextField(), "Type lastname here", "Dialog");
    }

    private void addAccountCreatedActionListener() {
        if (checkFieldsForValidity()) personsDatabaseController.add(readFormAndCreateUser());
    }

    private void adjustDayForMonthAndYearActionListener() {
        // read selected options
        int selectedMonth = readMonths()-1;
        int selectedYear = readYears();
        int maxDaysInCurrentSelectedMonth = 0;

        // get actual calendar info
        Calendar cal = Calendar.getInstance();
        cal.set(selectedYear, selectedMonth, 1);
        maxDaysInCurrentSelectedMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

        // check for errors - total days exceed actual max days in a month
        if (userCreationPanel.getJComboBoxDay().getItemCount() != maxDaysInCurrentSelectedMonth) {
            // save current index
            int currentSelectedIndex = userCreationPanel.getJComboBoxDay().getSelectedIndex();

            // update list
            userCreationPanel.getJComboBoxDay().setModel(userCreationPanel.getDefaultComboBoxModel(createDayOptions(maxDaysInCurrentSelectedMonth)));

            // restore index
            if (currentSelectedIndex >= maxDaysInCurrentSelectedMonth) userCreationPanel.getJComboBoxDay().setSelectedIndex(maxDaysInCurrentSelectedMonth-1);
            else userCreationPanel.getJComboBoxDay().setSelectedIndex(currentSelectedIndex);
        }
    }

    private MouseListener iconSelectorMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!UserCreationPanelController.ImageSelectorFrameOpen) {
                    IconSelectorFrame iconSelectorFrame = new IconSelectorFrame(new Rectangle(userCreationPanel.getLocationOnScreen()));
                    IconSelectorFrameController iconSelectorFrameController = new IconSelectorFrameController(iconSelectorFrame, userCreationPanelController);
                    iconSelectorFrameController.activateActionListeners();
                    UserCreationPanelController.ImageSelectorFrameOpen = true;
                }
            }
        };
    }

    private FocusListener getPlaceHolderFocusListener(JTextField component, String textToDisplay, String font) {
         return new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(component.getText().trim().equals(textToDisplay) &&
                        Objects.equals(component.getFont(), new Font(font, Font.ITALIC, 12))) {
                    component.setText("");
                }

                component.setForeground(Color.BLACK);
                component.setFont(new Font("Dialog", Font.PLAIN, 12));
            }
            @Override
            public void focusLost(FocusEvent e) {
                enablePlaceholder(component, textToDisplay, font);
            }
        };
    }

    private void enablePlaceholder(JTextField component, String textToDisplay, String font) {
        if(component.getText().trim().equals("")) {
            component.setText(textToDisplay);
            component.setFont(new Font(font, Font.ITALIC, 12));
        }
        component.setForeground(CustomColors.getMidGrey());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ImageFrameIconObservableEntry) {
            if (((ImageFrameIconObservableEntry) arg).isAdded())
            userCreationPanel.setImage(((ImageFrameIconObservableEntry) arg).getIcon().toString(), "Image selected");
        }

        // if we received something from Person DB, this means our query was successful
        if (arg instanceof PersonDBObservableEntry) {
            clearForm();
        }
    }

    private String[] createDayOptions(int maxDays) {
        String[] dayOptions = new String[maxDays];
        for (int i=0; i<maxDays; i++) dayOptions[i] = Integer.toString(i+1);
        return dayOptions;
    }

    private String[] createMonthOptions(int maxMonths) {
        String[] monthOptions = new String[maxMonths];
        for (int i=0; i<maxMonths; i++) monthOptions[i] = Integer.toString(i+1);
        return monthOptions;
    }

    private String[] createYearOptions(int years) {
        String[] yearOptions = new String[years];
        for (int i=0; i<years; i++) yearOptions[i] = Integer.toString(i+1960);
        return yearOptions;
    }

    private Person readFormAndCreateUser() {
        String firstNameText = userCreationPanel.getFirstNameTextField().getText();
        String lastNameText = userCreationPanel.getLastNameTextField().getText();
        String phoneNumberText = userCreationPanel.getPhoneNumberTextField().getText();
        Gender genderObject = Gender.valueOf((String)userCreationPanel.getJComboBoxGender().getSelectedItem());
        String tempIconName = userCreationPanel.getIconName().replace(Paths.iconPath, "");
        String iconName = tempIconName.equals(setImageIconName) ? "" : tempIconName; // assign icon: lazy fixes for dayz

        int days = readDays();
        int months = readMonths();
        int years = readYears();

        Person p = new Person(firstNameText, lastNameText);
        p.setPhoneNumber(phoneNumberText);
        p.setGender(genderObject);
        p.setBirthDateLocal(Date.getLocalDate(years, months, days));
        p.setIcon(iconName);
        return p;
    }

    private int readDays() {
        return Integer.parseInt((String) Objects.requireNonNull(userCreationPanel.getJComboBoxDay().getSelectedItem()));
    }

    private int readMonths() {
        return Integer.parseInt((String) Objects.requireNonNull(userCreationPanel.getJComboBoxMonth().getSelectedItem()));
    }

    private int readYears() {
        return Integer.parseInt((String) Objects.requireNonNull(userCreationPanel.getJComboBoxYear().getSelectedItem()));
    }

    private boolean checkFieldsForValidity() {
        boolean fieldsNotEmpty = true;

        if (userCreationPanel.getFirstNameTextField().getText().length() == 0) {
            userCreationPanel.getFirstNameTextField().setBorder(userCreationPanel.getRedBorder()); fieldsNotEmpty = false;
        } else userCreationPanel.getFirstNameTextField().setBorder(userCreationPanel.getGreyBorder());

        if (userCreationPanel.getLastNameTextField().getText().length() == 0) {
            userCreationPanel.getLastNameTextField().setBorder(userCreationPanel.getRedBorder()); fieldsNotEmpty = false;
        } else userCreationPanel.getLastNameTextField().setBorder(userCreationPanel.getGreyBorder());

        return fieldsNotEmpty;
    }

    private void clearForm() {
        userCreationPanel.getFirstNameTextField().requestFocus();
        userCreationPanel.getFirstNameTextField().setText("");
        userCreationPanel.getLastNameTextField().setText("");
        userCreationPanel.getPhoneNumberTextField().setText("");
        userCreationPanel.getJComboBoxGender().setSelectedIndex(0);
        userCreationPanel.getJComboBoxDay().setSelectedIndex(0);
        userCreationPanel.getJComboBoxMonth().setSelectedIndex(0);
        userCreationPanel.getJComboBoxYear().setSelectedIndex(0);
        userCreationPanel.setImage(Paths.iconPath + setImageIconName, setImageButtonText);
    }
}