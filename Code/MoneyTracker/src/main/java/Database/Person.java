package Database;

import Balance.Balance;
import HelperClass.Date;
import HelperClass.Gender;
import HelperClass.HashMep;

import java.util.ArrayList;

public class Person extends DatabaseItem {
    private final HashMep<String, String> firstName;
    private final HashMep<String, String> lastName;
    private final HashMep<String, String> phoneNumber;
    private final HashMep<String, Gender> gender;
    private final HashMep<String, Date> birthDate;
    private final HashMep<String, Date> accountCreationDate;
    private final HashMep<String, Date> accountEditDate;
    private final HashMep<String, String> icon;
    private final Balance balance;

    public Person(String firstName, String lastName) {
        // init
        this.firstName = new HashMep<>();
        this.lastName = new HashMep<>();
        this.phoneNumber = new HashMep<>();
        this.gender = new HashMep<>();
        this.birthDate = new HashMep<>();
        this.accountCreationDate = new HashMep<>();
        this.accountEditDate = new HashMep<>();
        this.icon = new HashMep<>();
        this.balance = new Balance(this);

        // fill in values
        this.firstName.put("First_name", firstName);
        this.lastName.put("Last_name", lastName);
        this.phoneNumber.put("Phone_number", "");
        this.gender.put("Gender", Gender.NONE);
        Date tempDefaultDate = new Date().getDate();
        Date tempTodaysDate = new Date().getTodaysDate();
        this.birthDate.put("Birth_date", tempDefaultDate);
        this.accountCreationDate.put("Account_creation_date", tempTodaysDate);
        this.accountEditDate.put("Account_edit_date", tempTodaysDate);
        this.icon.put("Icon_url", "");
    }

    public String getFirstNameKey() {
        return this.firstName.getKey();
    }
    public String getFirstNameValue() {
        return this.firstName.getValue();
    }
    public void setFirstName(String firstName) {
        this.firstName.put(getFirstNameKey(), firstName);
        this.updateAccountEditDate();
    }

    public String getLastNameKey() {
        return this.lastName.getKey();
    }
    public String getLastNameValue() {
        return this.lastName.getValue();
    }
    public void setLastName(String lastName) {
        this.lastName.put(getLastNameKey(), lastName);
        this.updateAccountEditDate();
    }

    public String getPhoneNumberKey() {
        return this.phoneNumber.getKey();
    }
    public String getPhoneNumberValue() {
        return this.phoneNumber.getValue();
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.put(getPhoneNumberKey(), phoneNumber);
        this.updateAccountEditDate();
    }

    public String getGenderKey() {
        return this.gender.getKey();
    }
    public Gender getGenderValue() {
        return this.gender.getValue();
    }
    public void setGender(Gender gender) {
        this.gender.put(getGenderKey(), gender);
        this.updateAccountEditDate();
    }

    public String getBirthDateKey() {
        return this.birthDate.getKey();
    }
    public Date getBirthDateValue() {
        return this.birthDate.getValue();
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate.put(getBirthDateKey(), birthDate);
        this.updateAccountEditDate();
    }

    public String getIconKey() {
        return this.icon.getKey();
    }
    public String getIconValue() {
        return this.icon.getValue();
    }
    public void setIcon(String iconUrl) {
        this.icon.put(getIconKey(), iconUrl);
        this.updateAccountEditDate();
    }

    public String getAccountCreationDateKey() {
        return this.accountCreationDate.getKey();
    }
    public Date getAccountCreationDateValue() {
        return this.accountCreationDate.getValue();
    }


    public String getAccountEditDateKey() {
        return this.accountEditDate.getKey();
    }
    public Date getAccountEditDateValue() {
        return this.accountEditDate.getValue();
    }

    private void updateAccountEditDate() {
        this.accountCreationDate.put("Account_creation_date", new Date().getTodaysDate());
    }

    public void addToBalance(double amount, Ticket ticket, Date date) {
        balance.add(amount, ticket, date);
    }

    public double getBalance() {
        return balance.getOutstandingAmount();
    }

    public ArrayList<String> getHistory() {
        return balance.getHistory();
    }
}
