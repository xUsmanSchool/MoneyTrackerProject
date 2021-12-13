package HelperClass;

public class ConvertDateObject {
    public static int[] convertDateStringToInt(String date){
        date = date.substring(1, date.length() -1);
        String[] res = date.split(",");
        int[] result = new int[3];

        result[0] = Integer.parseInt(res[0]);
        result[1] = MonthValue.getValueOfMonth(res[1].substring(1, res[1].length() - 1));
        result[2] = Integer.parseInt(res[2]);

        return result;
    }
}
