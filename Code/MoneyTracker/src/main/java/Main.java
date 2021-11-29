import Database.Person;
import Database.PersonsDB;
import Database.TicketsDB;
import HelperClass.JSONObjectConvert;
import HelperClass.Gender;
import HelperClass.WriteToJSONFile;
import Iterator.Iterator;
import Observers.DatabaseObserver;
import View.ViewFrame;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public Main() {
        /** Singleton pattern: databases */
        PersonsDB personDatabase = PersonsDB.getInstance();
        TicketsDB ticketDatabase = TicketsDB.getInstance();

        /** Observer pattern: still todo */
        DatabaseObserver dbObserver = new DatabaseObserver();
        personDatabase.addObserver(dbObserver);

        // create person 1
        Person testPerson1 = new Person("Usman,", "The Ultimate Disappointment");
        testPerson1.setGender(Gender.FEMALE);
        testPerson1.setIcon("testIcon.jpg");

        // create person 2
        Person testPerson2 = new Person("Vlad", "Kukh");
        testPerson2.setPhoneNumber("0000000000");

        // add person to database
        personDatabase.add(testPerson1);
        personDatabase.add(testPerson2);

        /** Iterator pattern: going through a large database */
        System.out.println("Testing iterator pattern: ");
        Iterator it = personDatabase.getIterator();
        while (it.hasNext()) System.out.println(it.next());

        // write to file
        WriteToJSONFile.writeMultipleObjectsToFile("database.json", JSONObjectConvert.JSONifyAllPersons(personDatabase));

        // draw
        ViewFrame view = new ViewFrame();
        view.initialize();
    }

    public void run() {}
}
