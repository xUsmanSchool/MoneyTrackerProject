package HelperClass;

import java.time.Month;

public class MonthValue {
    public static int getValueOfMonth(String month){
        int value = -1;
        for (int i = 0; i < Month.values().length; i++) {
            if (month.toUpperCase().equals(Month.values()[i].toString())){
                value = i+1;
                break;
            }
        }
        return value;
    }
}
