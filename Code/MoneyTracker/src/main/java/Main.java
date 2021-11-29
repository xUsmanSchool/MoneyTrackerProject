import Database.*;
import Factory.*;
import HelperClass.*;
import Iterator.*;
import Observers.*;
import Events.*;
import View.ViewFrame;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public Main() {
        PersonsDB personDatabase = PersonsDB.getInstance();                         /** Singleton pattern: databases */
        TicketsDB ticketDatabase = TicketsDB.getInstance();                         /** Singleton pattern: databases */

        DatabaseObserver dbObserver = new DatabaseObserver();                       /** Observer pattern */
        personDatabase.addObserver(dbObserver);                                     /** Observer pattern */
        ticketDatabase.addObserver(dbObserver);                                     /** Observer pattern */

        // todo - read database.json and update the personDatabase

        Person person1 = new Person("Usman,", "The Ultimate Disappointment");       // create person 1
        Person person2 = new Person("Vladimir", "Kukh");                            // create person 2

        personDatabase.add(person1);                                                // add person 1 to database
        personDatabase.add(person2);                                                // add person 2 to database

        System.out.println("\nTesting iterator pattern: ");                         /** Iterator pattern */
        Iterator it = personDatabase.getIterator();                                 /** Iterator pattern */
        while (it.hasNext()) System.out.println(it.next());                         /** Iterator pattern */

        EventFactory eventFactory = new EventFactory();                             /** Factory pattern */
        Event randomEvent = eventFactory.getEvent(Events.RESTAURANT);               /** Factory pattern */

        // write to JSON file
        //WriteToJSONFile.writeMultipleObjectsToFile("database.json", JSONObjectConvert.JSONifyAllPersons(personDatabase));

        // draw JFrame
        ViewFrame view = new ViewFrame();
        view.initialize();
    }

    public void run() {}
}
