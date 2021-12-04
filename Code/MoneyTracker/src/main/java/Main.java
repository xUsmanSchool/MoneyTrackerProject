import DatabaseController.*;
import Database.*;
import Factory.*;
import HelperClass.*;
import Iterator.*;
import Observers.*;
import Model.*;
import View.ViewFrame;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public Main() {
        PersonsDB personDatabase = PersonsDB.getInstance();                         /** Singleton pattern: databases */
        TicketsDB ticketDatabase = TicketsDB.getInstance();                         /** Singleton pattern: databases */
        //--------------------------------------------------------------------------------------------------------------
        PersonsDBController personsDBController = new PersonsDBController(personDatabase);
        TicketsDBController ticketsDBController = new TicketsDBController(ticketDatabase);
        //--------------------------------------------------------------------------------------------------------------
        DatabaseObserver dbObserver = new DatabaseObserver();                       /** Observer pattern: cmd & view */
        personDatabase.addObserver(dbObserver);                                     /** Observer pattern: cmd & view */
        ticketDatabase.addObserver(dbObserver);                                     /** Observer pattern: cmd & view */
        //--------------------------------------------------------------------------------------------------------------
        EventFactory eventFactory = new EventFactory();                             /** Factory pattern: events */
        TicketFactory ticketFactory = new TicketFactory();                          /** Factory pattern: tickets */
        //--------------------------------------------------------------------------------------------------------------
        //todo - read database.json and update the personDatabase
        ReadFromJSONFile.readPersonFile(personDatabase);
        //--------------------------------------------------------------------------------------------------------------
        Person person1 = new Person("Usman", "The Ultimate Disappointment");        // create person 1
        person1.setGender(Gender.FEMALE);                                               // update person 1
        person1.setPhoneNumber("1234567890");                                           // update person 1
        person1.setIcon("testIcon.jpg");                                                // update person 1
        Person person2 = new Person("Vladimir", "Kukh");                            // create person 2
        person2.setGender(Gender.MALE);                                                 // update person 2
        person2.setBirthDate(1,6,1995);                                 // update person 2
        Person person3 = new Person("Dingus", "Without Lastname");                  // create person 3

        personsDBController.add(person1);                                // add person 1 to database
        personsDBController.add(person2);                                // add person 2 to database
        personsDBController.add(person3);                                // add person 2 to database
        //--------------------------------------------------------------------------------------------------------------
        System.out.println("\nTesting iterator pattern on Person DB: ");            /** Iterator pattern through DB */
        Iterator<Person> itP = personDatabase.getIterator();                        /** Iterator pattern through DB */
        while (itP.hasNext()) System.out.println(itP.next().getFirstNameValue());   /** Iterator pattern through DB */
        System.out.println();                                                       /** Iterator pattern through DB */
        //--------------------------------------------------------------------------------------------------------------
        Ticket ticket1 = ticketFactory.getTicket(person1, 30.00, eventFactory.getEvent(Events.RESTAURANT), SplitType.EQUAL);
        ticket1.autoCalculate(personsDBController.getAll());                // create ticket 1
        ticket1.addSCashSplit(null, 0.00);                    // fake add
        ticket1.addSCashSplit(null, 0.00);                    // fake add
        ticket1.addSCashSplit(null, 0.00);                    // fake add

        Ticket ticket2 = ticketFactory.getTicket(person2, 10.00, eventFactory.getEvent(Events.AIRPLANE), SplitType.UNEQUAL);
        ticket2.autoCalculate(null);                           // fake add
        ticket2.addSCashSplit(person1, 1.50);                       // create ticket 2
        ticket2.addSCashSplit(person2, 2.00);                       // create ticket 2
        ticket2.addSCashSplit(person3, 8.50);                       // create ticket 2

        Ticket ticket3 = ticketFactory.getTicket(person3, 5.00, eventFactory.getEvent(Events.TAXI), SplitType.UNEQUAL);
        ticket3.autoCalculate(null);                             // fake add
        ticket3.addPercentageSplit(person1, 0.10);                  // create ticket 3
        ticket3.addPercentageSplit(person2, 0.10);                  // create ticket 3
        ticket3.addPercentageSplit(person3, 0.80);                  // create ticket 3
        //--------------------------------------------------------------------------------------------------------------
        ticketsDBController.add(ticket1);                                // add ticket 1 to database
        ticketsDBController.add(ticket2);                                // add ticket 2 to database
        ticketsDBController.add(ticket3);                                // add ticket 3 to database
        //--------------------------------------------------------------------------------------------------------------
        System.out.println("\nTesting iterator pattern on Ticket DB: ");            /** Iterator pattern through DB */
        Iterator<Ticket> itT = ticketDatabase.getIterator();                        /** Iterator pattern through DB */
        while (itT.hasNext()) System.out.println(itT.next().getEventTypeValue().getEventName());
        System.out.println();                                                       /** Iterator pattern through DB */
        //--------------------------------------------------------------------------------------------------------------
        // todo - better write to JSON file code
        //WriteToJSONFile.writeMultipleObjectsToFile("database.json", JSONObjectConvert.JSONifyAllPersons(personDatabase));
        //--------------------------------------------------------------------------------------------------------------
        ViewFrame view = new ViewFrame("Money Tracker Application");
        view.initialize();
    }

    public void run() {}
}
