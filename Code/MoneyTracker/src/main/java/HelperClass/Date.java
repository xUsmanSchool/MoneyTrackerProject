package HelperClass;

import java.time.LocalDate;
import java.time.Period;

public class Date {
    private Date() {}

    public static int getAge(int birthYear, int birthMonth, int birthDay) {
        LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);
        LocalDate currentDate = LocalDate.now();
        return Date.calculateAge(birthDate, currentDate);
    }

    public static int getAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Date.calculateAge(birthDate, currentDate);
    }

    public static LocalDate getLocalDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    public static LocalDate getTodaysDate() {
        return LocalDate.now();
    }

    // from https://stackoverflow.com/questions/1116123/how-do-i-calculate-someones-age-in-java
    private static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
