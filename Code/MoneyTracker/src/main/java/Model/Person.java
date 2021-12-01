package Model;

import HelperClass.*;

public class Person extends DatabaseItem {
    private final HashMep<String, String> firstName;
    private final HashMep<String, String> lastName;
    private final HashMep<String, String> phoneNumber;
    private final HashMep<String, Gender> gender;
    private final HashMep<String, Date> birthDate;

    public Person(String firstName, String lastName) {
        // init
        this.firstName = new HashMep<>();
        this.lastName = new HashMep<>();
        this.phoneNumber = new HashMep<>();
        this.gender = new HashMep<>();
        this.birthDate = new HashMep<>();

        // get dates
        Date tempDefaultDate = new Date().getDate();
        Date tempTodaysDate = new Date().getTodaysDate();

        // fill in values
        this.firstName.put("First_name", firstName);
        this.lastName.put("Last_name", lastName);
        this.phoneNumber.put("Phone_number", "");
        this.gender.put("Gender", Gender.NONE);
        this.birthDate.put("Birth_date", tempDefaultDate);
        this.creationDate.put("Account_creation_date", tempTodaysDate);
        this.editDate.put("Account_edit_date", tempTodaysDate);
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

    @Override
    public void setIcon(String iconUrl) {
        this.icon.put(getIconKey(), iconUrl);
        this.updateAccountEditDate();
    }

    @Override
    public void updateAccountEditDate() {
        this.creationDate.put(creationDate.getKey(), new Date().getTodaysDate());
    }
}
