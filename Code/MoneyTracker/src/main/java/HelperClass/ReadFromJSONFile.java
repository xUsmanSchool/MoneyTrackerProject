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
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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
        int[] values = ConvertDateObject.convertDateStringToInt(personObject.get("Birth_date").toString());
        tmpPerson.setBirthDate(values[0], values[1], values[2]);

        db.add(tmpPerson);
    }
}
