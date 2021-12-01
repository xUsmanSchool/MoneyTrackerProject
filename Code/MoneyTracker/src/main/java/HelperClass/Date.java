package HelperClass;

import java.time.LocalDate;
import java.time.Period;
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

    public int getAge(Date b) {
        LocalDate birthDate = LocalDate.of(b.getYear(), b.getMonth(), b.getDay());
        LocalDate currentDate = LocalDate.now();
        return Date.calculateAge(birthDate, currentDate);
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

    // from https://stackoverflow.com/questions/1116123/how-do-i-calculate-someones-age-in-java
    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Date{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
