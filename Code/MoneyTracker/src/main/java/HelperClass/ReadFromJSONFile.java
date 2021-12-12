package HelperClass;

import Database.PersonsDB;
import Database.TicketsDB;
import DatabaseController.PersonsDBController;
import Model.Person;
import Model.Ticket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileReader;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Factory.*;

public class ReadFromJSONFile {
    public static void readPersonFile(PersonsDB db){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("./persons.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray personsList = (JSONArray) obj;
            System.out.println(personsList);

            //Iterate over person array
            personsList.forEach(person -> parsePersonObject( (JSONObject) person , db) );

        }  catch (IOException e) {
            System.err.printf("Unable to read file %s%s person.json\n", e);
        }  catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parsePersonObject(JSONObject personObject, PersonsDB db) {
        //Get person first name
        String firstName = (String) personObject.get("First_name");

        //Get person last name
        String lastName = (String) personObject.get("Last_name");

        Person tmpPerson = new Person(firstName, lastName);

        //Get person gender
        tmpPerson.setGender(Gender.valueOf((String) personObject.get("Gender")));

        //Get person phone number
        tmpPerson.setPhoneNumber((String) personObject.get("Phone_number"));

        //Get person icon
        tmpPerson.setIcon((String) personObject.get("Icon_url"));

        //Get person birth date
        int[] values = ConvertDateObject.convertDateStringToInt(personObject.get("Birth_date").toString());
        tmpPerson.setBirthDate(values[0], values[1], values[2]);

        db.add(tmpPerson);
    }

    public static void readTicketFile(TicketsDB db){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("./tickets.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray ticketsList = (JSONArray) obj;
            System.out.println(ticketsList);

            //Iterate over tickets array
            for (Object ticket : ticketsList) parseTicketObject((JSONObject) ticket, db);

        }  catch (IOException e) {
            System.err.printf("Unable to read file %s%s tickets.json\n", e);
        }  catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseTicketObject(JSONObject ticketObject, TicketsDB db) {
        //total sum
        double total_sum = (double) ticketObject.get("TotalSum");

        //event type
        String event_type = (String) ticketObject.get("EventType");
        Events event = null;
        for (Events e : Events.values()) {
            if (e.name().equals(event_type)) {
                event = e;
                break;
            }
        }

        //Split type
        String split_type = (String) ticketObject.get("SplitType");
        SplitType splitType = null;
        for (SplitType s : SplitType.values()) {
            if (s.name().equals(split_type)) {
                splitType = s;
                break;
            }
        }

        //payed by
        String payed_by = (String) ticketObject.get("Payed_by");
        Person person = null;
        PersonsDB personsDB = PersonsDB.getInstance();
        ArrayList<Person> personsList = personsDB.getAll();

        for (int i = 0; i < personsList.size(); i++){
            String testString = String.format("%s;%s", personsList.get(i).getFirstNameValue(), personsList.get(i).getLastNameValue());

            if (payed_by.equals(testString)){
                person = personsList.get(i);
                break;
            }
        }

        //payment split

        //create ticket
        TicketFactory ticketFactory = new TicketFactory();
        EventFactory eventFactory = new EventFactory();

        Ticket ticket = ticketFactory.getTicket(person, total_sum, eventFactory.getEvent(event), splitType);
        db.add(ticket);
    }
}
