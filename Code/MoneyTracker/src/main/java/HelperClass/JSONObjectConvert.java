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
        jsonMap.put("lastName", person.getLastName() == null ? "null" : person.getLastName().toString());
        jsonMap.put("phoneNumber", person.getPhoneNumber() == null ? "null" : person.getPhoneNumber().toString());
        jsonMap.put("sex", person.getSex() == null ? "null" : person.getSex().toString());
        jsonMap.put("birthDate", person.getBirthDate() == null ? "null" : person.getBirthDate().toString());
        jsonMap.put("accountCreationDate", person.getAccountCreationDate() == null ? "null" : person.getAccountCreationDate().toString());
        jsonMap.put("accountEditDate", person.getAccountEditDate() == null ? "null" : person.getAccountEditDate().toString());
        return new JSONObject(jsonMap);
    }

    public static JSONArray JSONifyAllPersons(PersonsDB personsDB) {
        JSONArray jsonObjectArrayList = new JSONArray();
        for (Person person : personsDB.getPersons()) jsonObjectArrayList.add(JSONObjectConvert.JSONifyPerson(person));
        return jsonObjectArrayList;
    }
}
