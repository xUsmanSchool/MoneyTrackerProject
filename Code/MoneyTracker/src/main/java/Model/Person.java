package Model;

import HelperClass.*;
import java.time.LocalDate;

public class Person extends DatabaseItem {
    private final HashMep<String, String> firstName;
    private final HashMep<String, String> lastName;
    private final HashMep<String, String> phoneNumber;
    private final HashMep<String, Gender> gender;
    private final HashMep<String, LocalDate> birthDate;

    public Person(String firstName, String lastName) {
        // init
        this.firstName = new HashMep<>();
        this.lastName = new HashMep<>();
        this.phoneNumber = new HashMep<>();
        this.gender = new HashMep<>();
        this.birthDate = new HashMep<>();

        // get dates
        LocalDate tempDefaultDate = Date.getLocalDate(1960,1,1);
        LocalDate tempTodaysDate = Date.getLocalDate(1960,1,1);

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
    public LocalDate getBirthDateValue() {
        return this.birthDate.getValue();
    }

    public void setBirthDate(int day, int month, int year) {
        this.birthDate.put(getBirthDateKey(), Date.getLocalDate(year, month, day));
        this.updateAccountEditDate();
    }

    public void setBirthDateLocal(LocalDate birthDate) {
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
        this.creationDate.put(creationDate.getKey(), Date.getTodaysDate());
    }
}
