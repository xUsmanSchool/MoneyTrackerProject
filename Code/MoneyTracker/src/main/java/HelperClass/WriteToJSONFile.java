package HelperClass;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToJSONFile {
    public static void createEmptyFile(String fileName) {
        try {
            FileWriter file = new FileWriter("./" + fileName,true);
            file.close();
        } catch (IOException e) { System.err.printf("Unable to create file %s%s\n", fileName, e); }
    }

    public static void writeObjectToFile(String fileName, JSONObject object) {
        try {
            FileWriter file = new FileWriter("./" + fileName,true);
            file.write(object.toJSONString());
            file.close();
        } catch (IOException e) { System.err.printf("Unable to create file %s%s\n", fileName, e); }
    }

    public static void writeObjectsToFile(String fileName, JSONArray objects) {
        try {
            FileWriter file = new FileWriter("./" + fileName,true);
            file.write(objects.toJSONString());
            file.close();
        } catch (IOException e) { System.err.printf("Unable to create file %s%s\n", fileName, e); }
    }
}
