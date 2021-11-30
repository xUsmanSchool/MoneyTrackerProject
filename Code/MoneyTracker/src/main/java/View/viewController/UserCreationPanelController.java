package View.viewController;

import Database.Person;
import DatabaseController.DatabaseController;
import HelperClass.Date;
import HelperClass.EnumConverter;
import HelperClass.Gender;
import View.panels.UserCreationPanel;
import java.util.Calendar;
import java.util.Objects;

public class UserCreationPanelController extends vController {
    private final DatabaseController databaseController;
    private final UserCreationPanel userCreationPanel;

    public UserCreationPanelController(DatabaseController databaseController, UserCreationPanel userCreationPanel) {
        this.userCreationPanel = userCreationPanel;
        this.databaseController = databaseController;
    }

    @Override
    public void init() {
        for(String s : EnumConverter.enumToString(Gender.values())) userCreationPanel.getJComboBoxGender().addItem(s);
        for(String s : createDayOptions(31)) userCreationPanel.getJComboBoxDay().addItem(s);
        for(String s : createMonthOptions(12)) userCreationPanel.getJComboBoxMonth().addItem(s);
        for(String s : createYearOptions(100)) userCreationPanel.getJComboBoxYear().addItem(s);

        userCreationPanel.getFirstNameLabel().setText("First name: ");
        userCreationPanel.getLastNameLabel().setText("Last name: ");
        userCreationPanel.getPhoneNumberLabel().setText("Phone number*: ");
        userCreationPanel.getGenderLabel().setText("Gender*: ");
        userCreationPanel.getBirthdateLabel().setText("Date of birth*: ");
        userCreationPanel.getCreateButton().setText("Create user");
    }

    @Override
    public void activateActionListeners() {
        userCreationPanel.getCreateButton().addActionListener(e -> addAccountCreatedActionListener());
        userCreationPanel.getJComboBoxMonth().addActionListener(e -> adjustDayForMonthAndYearActionListener());
        userCreationPanel.getJComboBoxYear().addActionListener(e -> adjustDayForMonthAndYearActionListener());
    }

    private void addAccountCreatedActionListener() {
        if (checkFieldsForValidity()) {
            databaseController.add(createPerson());
            clearForm();
        }
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
            if (currentSelectedIndex > maxDaysInCurrentSelectedMonth) userCreationPanel.getJComboBoxDay().setSelectedIndex(maxDaysInCurrentSelectedMonth-1);
            else userCreationPanel.getJComboBoxDay().setSelectedIndex(currentSelectedIndex);
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

    private Person createPerson() {
        String firstNameText = userCreationPanel.getFirstNameTextField().getText();
        String lastNameText = userCreationPanel.getLastNameTextField().getText();
        String phoneNumberText = userCreationPanel.getPhoneNumberTextField().getText();
        Gender genderObject = Gender.valueOf((String)userCreationPanel.getJComboBoxGender().getSelectedItem());
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
    }
}
