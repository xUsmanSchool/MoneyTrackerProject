package HelperClass;

import Database.PersonsDB;
import Model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import Database.Database;

public class WriteToJSONFile {
    public static void createEmptyFile(String fileName) {
        try {
            FileWriter file = new FileWriter("./" + fileName,true);
            file.close();
        } catch (IOException e) { System.err.printf("Unable to create file %s%s\n", fileName, e); }
    }

    public static void writeSingleObjectToFile(String fileName, JSONObject object) {
        try {
            FileWriter file = new FileWriter("./" + fileName,true);
            file.write(object.toJSONString());
            file.close();
        } catch (IOException e) { System.err.printf("Unable to create file %s%s\n", fileName, e); }
    }

    public static void writeMultipleObjectsToFile(String fileName, JSONArray objects) {
        try {
            FileWriter file = new FileWriter("./" + fileName,true);
            file.write(objects.toJSONString());
            file.close();
        } catch (IOException e) { System.err.printf("Unable to create file %s%s\n", fileName, e); }
    }

    public static void updatePersonFile(PersonsDB db){
        //todo: convert to JsonArray ofzo en write naar die file

        try {
            FileWriter file = new FileWriter("./person",true);
            file.write(JSONObjectConvert.JSONifyAllPersons(db).toJSONString());
            file.close();
        } catch (IOException e) { System.err.printf("Unable to create file %s%s person.json\n", e); }
    }
}
