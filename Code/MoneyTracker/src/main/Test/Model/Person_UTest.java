package Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Person_UTest {
    Person person = new Person("Usman", "Shani");

    public Person_UTest(){}

    @Before
    public void initialize(){

    }

    @Test
    void getFirstName() {
        Assert.assertEquals("Usman", person.getFirstNameValue());
    }

    @Test
    void getLastName() {
        Assert.assertEquals("Shani", person.getLastNameValue());
    }

    @Test
    void setBirthDate() {
        person.setBirthDate(10,1,1999);
        Assert.assertEquals("1999-01-10", person.getBirthDateValue().toString());
    }
}