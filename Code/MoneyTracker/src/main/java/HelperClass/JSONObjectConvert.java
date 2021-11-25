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
        jsonMap.put("firstName", person.getFirstName());
        jsonMap.put("lastName", person.getLastName());
        jsonMap.put("phoneNumber", person.getPhoneNumber());
        jsonMap.put("sex", person.getSex().toString());
        jsonMap.put("birthDate", DateToJSONArray(person.getBirthDate()).toJSONString());
        jsonMap.put("accountCreationDate", DateToJSONArray(person.getAccountCreationDate()).toJSONString());
        jsonMap.put("accountEditDate", DateToJSONArray(person.getAccountEditDate()).toJSONString());
        return new JSONObject(jsonMap);
    }

    public static JSONArray JSONifyAllPersons(PersonsDB personsDB) {
        JSONArray jsonObjectArrayList = new JSONArray();
        for (Person person : personsDB.getPersons()) jsonObjectArrayList.add(JSONObjectConvert.JSONifyPerson(person));
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
