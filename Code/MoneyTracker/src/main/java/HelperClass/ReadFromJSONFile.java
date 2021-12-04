package HelperClass;

import Database.PersonsDB;
import Database.TicketsDB;
import Model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import Database.Database;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.time.LocalDate;

public class ReadFromJSONFile {
    public static void readPersonFile(PersonsDB db){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("./persons.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray personsList = (JSONArray) obj;
            System.out.println(personsList);

            //Iterate over employee array
            personsList.forEach( person -> parsePersonObject( (JSONObject) person , db) );

        }  catch (IOException e) {
            System.err.printf("Unable to read file %s%s person.json\n", e);
        }  catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parsePersonObject(JSONObject personObject, PersonsDB db)
    {

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
        Object object = personObject.get("Birth_date");
        JSONArray date = null;

        if(object instanceof JSONArray){ date = (JSONArray) object; }
        Local
        tmpPerson.setBirthDate((int)date.get(0), (int)date.get(1), (int)date.get(2));

        //tmpPerson.setBirthDate();

        System.out.println("PRINTING READ TEMP PERSON");
        System.out.println(personObject.get("Birth_date"));
    }
}
