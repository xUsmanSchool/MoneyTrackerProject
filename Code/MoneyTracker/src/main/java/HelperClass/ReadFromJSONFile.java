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
            ticketsList.forEach(ticket -> parseTicketObject( (JSONObject) ticket , db) );

        }  catch (IOException e) {
            System.err.printf("Unable to read file %s%s tickets.json\n", e);
        }  catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseTicketObject(JSONObject ticketObject, TicketsDB db) {
        String payed_by = (String) ticketObject.get("Payed_by");

        PersonsDB personsDB = PersonsDB.getInstance();
        PersonsDBController personsDBController = new PersonsDBController(personsDB);
        ArrayList<Person> personsList = personsDBController.getAll();
        System.out.println("==========STARTING FOR LOOP=========");
        for (int i = 0; i < personsList.size(); i++){
            System.out.println("==========STARTING IF STATEMENT=========");
            if (payed_by == personsList.get(i).toString()){
                System.out.println("==========FOUND MATCH=========");
            }
        }

        //create ticket
        //Ticket ticket = TicketFactory.getTicket()

        //db.add(tmpPerson);
    }
}
