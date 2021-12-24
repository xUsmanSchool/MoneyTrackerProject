import Database.*;
import DatabaseController.*;
import Factory.*;
import HelperClass.*;
import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CeateTicket_ITest {

    @BeforeEach
    void setUp() {
        PersonsDB personDatabase = PersonsDB.getInstance();
        TicketsDB ticketDatabase = TicketsDB.getInstance();

        PersonsDBController personsDBController = new PersonsDBController(personDatabase);
        TicketsDBController ticketsDBController = new TicketsDBController(ticketDatabase);

        Person person1 = new Person("Usman", "The Admin");                        // create person 1
        person1.setGender(Gender.MALE);                                           // update person 1
        Person person2 = new Person("Vladimir", "The Admin");                     // create person 2
        person2.setGender(Gender.MALE);                                           // update person 2
        person2.setBirthDate(1,6,1995);                                           // update person 2
        Person person3 = new Person("Homeless", "Dingus");                        // create person 3
        person3.setIcon("user_icon6.png");                                        // update person 3

        personsDBController.add(person1);                                         // add person 1 to database
        personsDBController.add(person2);                                         // add person 2 to database
        personsDBController.add(person3);                                         // add person 3 to database

        EventFactory eventFactory = new EventFactory();
        TicketFactory ticketFactory = new TicketFactory();

        Ticket ticket1 = ticketFactory.getTicket(person1, 30.00, eventFactory.getEvent(Events.RESTAURANT), SplitType.EQUAL);
        ticket1.autoCalculate(personsDBController.getAll());                // create ticket 1
        ticket1.addSCashSplit(null, 0.00);                    // fake add
        ticket1.addSCashSplit(null, 0.00);                    // fake add
        ticket1.addSCashSplit(null, 0.00);                    // fake add

        Ticket ticket2 = ticketFactory.getTicket(person2, 100.00, eventFactory.getEvent(Events.AIRPLANE), SplitType.UNEQUAL);
        ticket2.autoCalculate(null);                           // fake add
        ticket2.addSCashSplit(person1, 1.00);                       // create ticket 2
        ticket2.addSCashSplit(person2, 98.00);                      // create ticket 2
        ticket2.addSCashSplit(person3, 1.00);                       // create ticket 2

        Ticket ticket3 = ticketFactory.getTicket(person3, 5.00, eventFactory.getEvent(Events.TAXI), SplitType.UNEQUAL);
        ticket3.autoCalculate(null);                           // fake add
        ticket3.addPercentageSplit(person1, 0.10);                  // create ticket 3
        ticket3.addPercentageSplit(person2, 0.10);                  // create ticket 3
        ticket3.addPercentageSplit(person3, 0.80);                  // create ticket 3
    }

    @Test
    void add() {

    }
}