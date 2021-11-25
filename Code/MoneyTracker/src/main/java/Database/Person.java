package Database;

import HelperClass.Date;
import HelperClass.Sex;

public class Person {
    private String firstName = "";
    private String lastName = "";
    private String phoneNumber = "";
    private Sex sex = Sex.NONE;
    private Date birthDate = null;
    private Date accountCreationDate = null;
    private Date accountEditDate = null;

    public Person(String firstName) {
        this.firstName = firstName;

        this.setBirthDate(new Date().getDate());
        this.setAccountCreationDate(new Date().getTodaysDate());
        this.setAccountEditDate(new Date().getTodaysDate());
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    private void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public Date getAccountEditDate() {
        return accountEditDate;
    }

    public void setAccountEditDate(Date accountEditDate) {
        this.accountEditDate = accountEditDate;
    }
}
