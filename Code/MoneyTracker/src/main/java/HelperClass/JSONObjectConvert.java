package HelperClass;

import Database.TicketsDB;
import Model.Person;
import Model.Ticket;
import Database.PersonsDB;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JSONObjectConvert {
    public static JSONObject JSONifyPerson(Person person) {
        JSONObject jsonMap = new JSONObject();
        jsonMap.put(person.getFirstNameKey(), person.getFirstNameValue());
        jsonMap.put(person.getLastNameKey(), person.getLastNameValue());
        jsonMap.put(person.getPhoneNumberKey(), person.getPhoneNumberValue());
        jsonMap.put(person.getGenderKey(), person.getGenderValue().toString());
        jsonMap.put(person.getBirthDateKey(), DateToJSONArray(person.getBirthDateValue()).toJSONString());
        jsonMap.put(person.getCreationDateKey(), DateToJSONArray(person.getCreationDateValue()).toJSONString());
        jsonMap.put(person.getEditDateKey(), DateToJSONArray(person.getEditDateValue()).toJSONString());
        jsonMap.put(person.getIconKey(), person.getIconValue());
        return new JSONObject(jsonMap);
    }

    public static JSONArray JSONifyAllPersons(PersonsDB personsDB) {
        JSONArray jsonObjectArrayList = new JSONArray();
        for (Person person : personsDB.getAll()) jsonObjectArrayList.add(JSONObjectConvert.JSONifyPerson(person));
        return jsonObjectArrayList;
    }

    public static JSONObject JSONifyTicket(Ticket ticket) {

        JSONObject jsonMap = new JSONObject();
        //Event type
        jsonMap.put(ticket.getEventTypeKey(), ticket.getEventTypeValue().getEventName());
        //Payed by - shows person object
        jsonMap.put(ticket.getPayedByKey(), JSONifyPerson(ticket.getPayedByValue()));
        //split type
        jsonMap.put(ticket.getSplitTypeKey(), ticket.getSplitTypeValue().toString());
        //total sum
        jsonMap.put("TotalSum", ticket.getTotalSum());
        //payment splits
        JSONArray tempJsonArray = new JSONArray();
        JSONObject tempObject = new JSONObject();
        for (Person person : ticket.getPersonArrayList()){
            tempObject.put(person, ticket.getAmountForPerson(person));
        }
        tempJsonArray.add(tempObject);

        jsonMap.put(ticket.getPersonArrayListKey(), tempJsonArray);


        return jsonMap;
    }

    public static JSONArray JSONifyAllTickets(TicketsDB ticketsDB) {
        JSONArray jsonObjectArrayList = new JSONArray();
        for (Ticket ticket : ticketsDB.getAll()) jsonObjectArrayList.add(JSONObjectConvert.JSONifyTicket(ticket));
        return jsonObjectArrayList;
    }

    private static JSONArray DateToJSONArray(LocalDate date) {
        JSONArray temp = new JSONArray();
        temp.add(0, Integer.valueOf(date.getDayOfMonth()));
        temp.add(1, date.getMonth().toString());
        temp.add(2, Integer.valueOf(date.getYear()));

        return temp;
    }
}
