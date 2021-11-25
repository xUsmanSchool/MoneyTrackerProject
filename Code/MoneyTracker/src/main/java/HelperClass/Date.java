package HelperClass;

import java.util.Calendar;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date() {
        this.getDate(1,1,1960);
    }

    public Date getTodaysDate() {
        Calendar c = Calendar.getInstance();
        return this.getDate(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR));
    }

    public Date getDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;

        return this;
    }

    public Date getDate() {
        return this;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }
}
