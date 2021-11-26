package Database;

import HelperClass.Date;
import HelperClass.Sex;
import java.util.HashMap;

public class Person {
    private HashMap<String, String> firstName;
    private HashMap<String, String> lastName;
    private HashMap<String, String> phoneNumber;
    private HashMap<String, Sex> sex;
    private HashMap<String, Date> birthDate;
    private HashMap<String, Date> accountCreationDate;
    private HashMap<String, Date> accountEditDate;

    public Person(String firstName) {
        // init
        this.firstName = new HashMap<>();
        this.lastName = new HashMap<>();
        this.phoneNumber = new HashMap<>();
        this.sex = new HashMap<>();
        this.birthDate = new HashMap<>();
        this.accountCreationDate = new HashMap<>();
        this.accountEditDate = new HashMap<>();

        // fill in values
        this.firstName.put("First_name", firstName);
        this.lastName.put("Last_name", "");
        this.phoneNumber.put("Phone_number", "");
        this.sex.put("Sex", Sex.NONE);
        this.birthDate.put("Birth_date", new Date().getDate());
        this.accountCreationDate.put("Account_creation_date", new Date().getTodaysDate());
        this.accountEditDate.put("Account_edit_date", new Date().getTodaysDate());
    }

    public String getFirstNameKey() {
        return this.firstName.keySet().toArray()[0].toString();
    }
    public String getFirstNameValue() {
        return this.firstName.get(getFirstNameKey());
    }
    public void setFirstName(String firstName) {
        this.firstName.put(getFirstNameKey(), firstName);
    }

    public String getLastNameKey() {
        return this.lastName.keySet().toArray()[0].toString();
    }
    public String getLastNameValue() {
        return this.lastName.get(getLastNameKey());
    }
    public void setLastName(String lastName) {
        this.lastName.put(getLastNameKey(), lastName);
    }

    public String getPhoneNumberKey() {
        return this.phoneNumber.keySet().toArray()[0].toString();
    }
    public String getPhoneNumberValue() {
        return this.phoneNumber.get(getPhoneNumberKey());
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.put(getPhoneNumberKey(), phoneNumber);
    }

    public String getSexKey() {
        return this.sex.keySet().toArray()[0].toString();
    }
    public Sex getSexValue() {
        return this.sex.get(getSexKey());
    }
    public void setSex(Sex sex) {
        this.sex.put(getSexKey(), sex);
    }

    public String getBirthDateKey() {
        return this.birthDate.keySet().toArray()[0].toString();
    }
    public Date getBirthDateValue() {
        return this.birthDate.get(getBirthDateKey());
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate.put(getBirthDateKey(), birthDate);
    }

    public String getAccountCreationDateKey() {
        return this.accountCreationDate.keySet().toArray()[0].toString();
    }
    public Date getAccountCreationDateValue() {
        return this.accountCreationDate.get(getAccountCreationDateKey());
    }
    private void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate.put(getAccountCreationDateKey(), accountCreationDate);
    }

    public String getAccountEditDateKey() {
        return this.accountEditDate.keySet().toArray()[0].toString();
    }
    public Date getAccountEditDateValue() {
        return this.accountEditDate.get(getAccountEditDateKey());
    }
    public void setAccountEditDate(Date accountEditDate) {
        this.accountEditDate.put(getAccountEditDateKey(), accountEditDate);
    }
}
