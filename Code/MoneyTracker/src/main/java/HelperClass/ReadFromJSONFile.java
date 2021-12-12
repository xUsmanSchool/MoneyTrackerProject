package HelperClass;

import Database.*;
import DatabaseController.PersonsDBController;
import Model.*;
import Events.Event;
import com.google.inject.internal.util.ObjectArrays;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Factory.*;

public class ReadFromJSONFile {
    public static void readPersonFile(PersonsDB db) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("./persons.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray personsList = (JSONArray) obj;
            System.out.println(personsList);

            //Iterate over person array
            personsList.forEach(person -> parsePersonObject((JSONObject) person, db));

        } catch (IOException e) {
            System.err.printf("Unable to read file %s%s person.json\n", e);
        } catch (ParseException e) {
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

        //Get creation date
        int[] valuesCreation = ConvertDateObject.convertDateStringToInt(personObject.get("Account_creation_date").toString());
        tmpPerson.setCreationDateValue(LocalDate.of(valuesCreation[2], valuesCreation[1], valuesCreation[0]));

        //Get person birth date
        int[] valuesBirth = ConvertDateObject.convertDateStringToInt(personObject.get("Birth_date").toString());
        tmpPerson.setBirthDate(valuesBirth[0], valuesBirth[1], valuesBirth[2]);

        tmpPerson.updateAccountEditDate();

        db.add(tmpPerson);
    }

    public static void readTicketFile(TicketsDB db) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("./tickets.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray ticketsList = (JSONArray) obj;
            System.out.println(ticketsList);

            //Iterate over tickets array
            for (Object ticket : ticketsList) parseTicketObject((JSONObject) ticket, db);

        } catch (IOException e) {
            System.err.printf("Unable to read file %s%s tickets.json\n", e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseTicketObject(JSONObject ticketObject, TicketsDB db) {
        //total sum
        double total_sum = (double) ticketObject.get("TotalSum");

        //event type
        String event_type = (String) ticketObject.get("EventType");
        Events event = Events.valueOf(event_type.substring(0, event_type.length() - 5).toUpperCase());

        //Split type
        String split_type = (String) ticketObject.get("SplitType");
        SplitType splitType = SplitType.valueOf(split_type);

        //payed by
        String payed_by = (String) ticketObject.get("Payed_by");
        Person person = null;
        PersonsDB personsDB = PersonsDB.getInstance();
        ArrayList<Person> personsList = personsDB.getAll();

        for (int i = 0; i < personsList.size(); i++) {
            String testString = String.format("%s;%s", personsList.get(i).getFirstNameValue(), personsList.get(i).getLastNameValue());

            if (payed_by.equals(testString)) {
                person = personsList.get(i);
                break;
            }
        }

        //create ticket
        TicketFactory ticketFactory = new TicketFactory();
        EventFactory eventFactory = new EventFactory();
        Ticket ticket = ticketFactory.getTicket(person, total_sum, eventFactory.getEvent(event), splitType);

        //payment split
        JSONArray tmpObject = (JSONArray) ticketObject.get("Payment_splits");
        JSONParser parser = new JSONParser();
        try {
            JSONObject splits = (JSONObject) parser.parse(tmpObject.get(0).toString());

            for (int i = 0; i < personsList.size(); i++) {
                String testString = String.format("%s;%s", personsList.get(i).getFirstNameValue(), personsList.get(i).getLastNameValue());

                if (splits.get(testString) != null) {
                    System.out.println("IN IF STATEMENT FOUND NOT NULL FOR " + testString + "AND ADDING AMOUNT: " + splits.get(testString).toString());
                    ticket.addSCashSplit(personsList.get(i), (double) splits.get(testString));
                }
            }
        } catch (ParseException e) { e.printStackTrace(); }

        db.add(ticket);
    }
}
