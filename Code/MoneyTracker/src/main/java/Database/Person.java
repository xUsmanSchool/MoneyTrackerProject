package Database;

import HelperClass.Date;
import HelperClass.Gender;
import HelperClass.HashMep;

public class Person extends DatabaseItem {
    //private final HashMep<String, String> nickName;
    private final HashMep<String, String> firstName;
    private final HashMep<String, String> lastName;
    private final HashMep<String, String> phoneNumber;
    private final HashMep<String, Gender> gender;
    private final HashMep<String, Date> birthDate;
    private final HashMep<String, Date> accountCreationDate;
    private final HashMep<String, Date> accountEditDate;
    private final HashMep<String, String> icon;

    public Person(String firstName, String lastName) {
        // init
        //this.nickName = new HashMep<>();
        this.firstName = new HashMep<>();
        this.lastName = new HashMep<>();
        this.phoneNumber = new HashMep<>();
        this.gender = new HashMep<>();
        this.birthDate = new HashMep<>();
        this.accountCreationDate = new HashMep<>();
        this.accountEditDate = new HashMep<>();
        this.icon = new HashMep<>();

        // fill in values
        //this.nickName.put("Nickname", nickName);
        this.firstName.put("First_name", firstName);
        this.lastName.put("Last_name", lastName);
        this.phoneNumber.put("Phone_number", "");
        this.gender.put("Gender", Gender.NONE);
        this.birthDate.put("Birth_date", new Date().getDate());
        this.accountCreationDate.put("Account_creation_date", new Date().getTodaysDate());
        this.accountEditDate.put("Account_edit_date", new Date().getTodaysDate());
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
    }

    public String getLastNameKey() {
        return this.lastName.getKey();
    }
    public String getLastNameValue() {
        return this.lastName.getValue();
    }
    public void setLastName(String lastName) {
        this.lastName.put(getLastNameKey(), lastName);
    }

    public String getPhoneNumberKey() {
        return this.phoneNumber.getKey();
    }
    public String getPhoneNumberValue() {
        return this.phoneNumber.getValue();
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.put(getPhoneNumberKey(), phoneNumber);
    }

    public String getGenderKey() {
        return this.gender.getKey();
    }
    public Gender getGenderValue() {
        return this.gender.getValue();
    }
    public void setGender(Gender gender) {
        this.gender.put(getGenderKey(), gender);
    }

    public String getBirthDateKey() {
        return this.birthDate.getKey();
    }
    public Date getBirthDateValue() {
        return this.birthDate.getValue();
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate.put(getBirthDateKey(), birthDate);
    }

    public String getAccountCreationDateKey() {
        return this.accountCreationDate.getKey();
    }
    public Date getAccountCreationDateValue() {
        return this.accountCreationDate.getValue();
    }
    private void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate.put(getAccountCreationDateKey(), accountCreationDate);
    }

    public String getAccountEditDateKey() {
        return this.accountEditDate.getKey();
    }
    public Date getAccountEditDateValue() {
        return this.accountEditDate.getValue();
    }
    public void setAccountEditDate(Date accountEditDate) {
        this.accountEditDate.put(getAccountEditDateKey(), accountEditDate);
    }

    public String getIconKey() {
        return this.icon.getKey();
    }
    public String getIconValue() {
        return this.icon.getValue();
    }
    public void setIcon(String iconUrl) {
        this.icon.put(getIconKey(), iconUrl);
    }
}
