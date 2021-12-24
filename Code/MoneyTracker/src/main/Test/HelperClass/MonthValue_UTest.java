package HelperClass;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonthValue_UTest {

    public MonthValue_UTest(){}

    @Test
    void getValueOfMonth_Uppercase()
    {
        String month1 = "JANUARY";
        String month2 = "AUGUST";
        String month3 = "DECEMBER";

        Assert.assertEquals("Month value for " + month1 + " should be 1", 1, MonthValue.getValueOfMonth(month1));
        Assert.assertEquals("Month value for " + month2 + " should be 8", 8, MonthValue.getValueOfMonth(month2));
        Assert.assertEquals("Month value for " + month3 + " should be 12", 12, MonthValue.getValueOfMonth(month3));
    }

    @Test
    void getValueOfMonth_Lowercase() {
        String month1 = "January";
        String month2 = "August";
        String month3 = "December";

        Assert.assertEquals("Month value for " + month1 + " should be 1", 1, MonthValue.getValueOfMonth(month1));
        Assert.assertEquals("Month value for " + month2 + " should be 8", 8, MonthValue.getValueOfMonth(month2));
        Assert.assertEquals("Month value for " + month3 + " should be 12", 12, MonthValue.getValueOfMonth(month3));
    }

    @Test
    void getValueOfMonth_FakeValue() {
        String month1 = "Mayember";

        Assert.assertEquals("Month value for " + month1 + " should be -1", -1, MonthValue.getValueOfMonth(month1));
    }
}