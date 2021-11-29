package HelperClass;

import Database.Person;
import Database.PersonsDB;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class JSONObjectConvert {
    public static JSONObject JSONifyPerson(Person person) {
        Map<String,String> jsonMap = new HashMap<>();
        jsonMap.put(person.getFirstNameKey(), person.getFirstNameValue());
        jsonMap.put(person.getLastNameKey(), person.getLastNameValue());
        jsonMap.put(person.getPhoneNumberKey(), person.getPhoneNumberValue());
        jsonMap.put(person.getGenderKey(), person.getGenderValue().toString());
        jsonMap.put(person.getBirthDateKey(), DateToJSONArray(person.getBirthDateValue()).toJSONString());
        jsonMap.put(person.getAccountCreationDateKey(), DateToJSONArray(person.getAccountCreationDateValue()).toJSONString());
        jsonMap.put(person.getAccountEditDateKey(), DateToJSONArray(person.getAccountEditDateValue()).toJSONString());
        jsonMap.put(person.getIconKey(), person.getIconValue());
        return new JSONObject(jsonMap);
    }

    public static JSONArray JSONifyAllPersons(PersonsDB personsDB) {
        JSONArray jsonObjectArrayList = new JSONArray();
        for (Person person : personsDB.getAll()) jsonObjectArrayList.add(JSONObjectConvert.JSONifyPerson(person));
        return jsonObjectArrayList;
    }

    private static JSONArray DateToJSONArray(Date date) {
        JSONArray temp = new JSONArray();
        temp.add(0, date.getDay().toString());
        temp.add(1, date.getMonth().toString());
        temp.add(2, date.getYear().toString());

        return temp;
    }
}
