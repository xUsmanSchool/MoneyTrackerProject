package Database;

import HelperClass.Sex;
import java.util.Calendar;

public class Person {
    private String firstName;
    private String lastName;
    private String phoneNumber = null;
    private Sex sex = Sex.NONE;
    private Calendar birthDate = null;
    private Calendar accountCreationDate = null;
    private Calendar accountEditDate = null;

    public Person(String firstName) {
        this.firstName = firstName;

        this.setAccountCreationDate(Calendar.getInstance());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public Calendar getAccountCreationDate() {
        return accountCreationDate;
    }

    private void setAccountCreationDate(Calendar accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public Calendar getAccountEditDate() {
        return accountEditDate;
    }

    public void setAccountEditDate(Calendar accountEditDate) {
        this.accountEditDate = accountEditDate;
    }
}
