import DatabaseController.PersonRegistrationDBController;
import Database.*;
import DatabaseController.TicketRegistrationDBController;
import Factory.*;
import HelperClass.*;
import Iterator.*;
import Observers.*;
import View.ViewFrame;
import model.Person;
import model.Ticket;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public Main() {
        PersonsDB personDatabase = PersonsDB.getInstance();                         /** Singleton pattern: databases */
        TicketsDB ticketDatabase = TicketsDB.getInstance();                         /** Singleton pattern: databases */
        //--------------------------------------------------------------------------------------------------------------
        PersonRegistrationDBController personRegistrationDBController = new PersonRegistrationDBController(personDatabase);
        TicketRegistrationDBController ticketRegistrationDBController = new TicketRegistrationDBController(ticketDatabase);
        //--------------------------------------------------------------------------------------------------------------
        DatabaseObserver dbObserver = new DatabaseObserver();                       /** Observer pattern */
        personDatabase.addObserver(dbObserver);                                     /** Observer pattern */
        ticketDatabase.addObserver(dbObserver);                                     /** Observer pattern */
        //--------------------------------------------------------------------------------------------------------------
        //todo - read database.json and update the personDatabase
        //--------------------------------------------------------------------------------------------------------------
        Person person1 = new Person("Usman", "The Ultimate Disappointment");        // create person 1
        Person person2 = new Person("Vladimir", "Kukh");                            // create person 2
        Person person3 = new Person("Dingus", "Without lastname");                  // create person 3

        personRegistrationDBController.add(person1);                                // add person 1 to database
        personRegistrationDBController.add(person2);                                // add person 2 to database
        personRegistrationDBController.add(person3);                                // add person 2 to database
        //--------------------------------------------------------------------------------------------------------------
        Iterator<Person> it = personDatabase.getIterator();                         /** Iterator pattern */
        while (it.hasNext()) System.out.println((it.next().getFirstNameValue()));   /** Iterator pattern */
        System.out.println();                                                       /** Iterator pattern */
        //--------------------------------------------------------------------------------------------------------------
        EventFactory eventFactory = new EventFactory();                             /** Factory pattern */
        TicketFactory ticketFactory = new TicketFactory();                          /** Factory pattern */
        //--------------------------------------------------------------------------------------------------------------
        Ticket ticket = null;
        ticket = ticketFactory.getTicket(person1, eventFactory.getEvent(Events.RESTAURANT), SplitType.EQUAL);
        ticket.addPayedBy(person3, 30.00);
        ticket.autoCalculate(personRegistrationDBController.getAll());
        printTicketInfo(ticket);

        ticket = ticketFactory.getTicket(person2, eventFactory.getEvent(Events.AIRPLANE), SplitType.UNEQUAL);
        ticket.addPayedBy(person2, 10.00);
        ticket.addSCashSplit(person1, -2.00);
        ticket.addSCashSplit(person3, -8.00);
        printTicketInfo(ticket);

        ticket = ticketFactory.getTicket(person3, eventFactory.getEvent(Events.TAXI), SplitType.UNEQUAL);
        ticket.addPayedBy(person1, 3.33);
        ticket.addPercentageSplit(person2, 0.38);
        ticket.addPercentageSplit(person3, 0.62);
        printTicketInfo(ticket);
        //--------------------------------------------------------------------------------------------------------------
        // todo - better write to JSON file code
        //WriteToJSONFile.writeMultipleObjectsToFile("database.json", JSONObjectConvert.JSONifyAllPersons(personDatabase));
        //--------------------------------------------------------------------------------------------------------------
        ViewFrame view = new ViewFrame();
        view.initialize();
    }

    public void run() {}

    public void printTicketInfo(Ticket t) {
        System.out.println("Ticket (Type - " + t.getSplitType().toString() + "): created by " + t.getCreatedBy().getFirstNameValue() + " on " + t.getCreationDate().getDay() + "/" + t.getCreationDate().getMonth());
        System.out.println("This ticket is for the " + t.getEventType().getEventName() + " with a total sum of " + t.getPayedAmount() + " which was payed by " + t.getPayedBy().getFirstNameValue());
        System.out.print("In detail: ");
        for (Person p:t.getPersonArrayList()) System.out.print(
                p.getFirstNameValue() +
                        (t.getAmountForPerson(p) > 0 ? " payed " : " owes " + t.getPayedBy().getFirstNameValue() + " ") +
                        t.getAmountForPerson(p) + ", ");
        System.out.println("\n");
    }
}
