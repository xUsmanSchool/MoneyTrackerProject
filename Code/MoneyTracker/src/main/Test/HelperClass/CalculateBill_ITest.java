package HelperClass;

import Database.*;
import DatabaseController.*;
import Factory.*;
import HelperClass.*;
import Model.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CalculateBill_ITest {
    CalculateBill calculateBill;
    TicketFactory ticketFactory;
    EventFactory eventFactory;
    Person person1;
    Person person2;
    Person person3;
    PersonsDBController personsDBController;
    TicketsDBController ticketsDBController;

    @BeforeEach
    void setUp() {
        System.out.println(" ");
        System.out.println("===SET UP===");
        PersonsDB personDatabase = PersonsDB.getInstance();
        TicketsDB ticketDatabase = TicketsDB.getInstance();

        personsDBController = new PersonsDBController(personDatabase);
        ticketsDBController = new TicketsDBController(ticketDatabase);

        person1 = new Person("Usman", "The Admin");                        // create person 1
        person2 = new Person("Vladimir", "The Admin");                     // create person 2
        person3 = new Person("Vlods", "Mom");                              // create person 3

        personsDBController.add(person1);                                         // add person 1 to database
        personsDBController.add(person2);                                         // add person 2 to database
        personsDBController.add(person3);                                         // add person 3 to database
        //personDatabase.printNames();

        eventFactory = new EventFactory();
        ticketFactory = new TicketFactory();

        calculateBill = new CalculateBill(personsDBController, ticketsDBController);
    }

    @Test
    void calculateBill_default() {
        Ticket ticket1 = ticketFactory.getTicket(person1, 30.00, eventFactory.getEvent(Events.RESTAURANT), SplitType.EQUAL);
        ticket1.autoCalculate(personsDBController.getAll());                // create ticket 1

        Ticket ticket2 = ticketFactory.getTicket(person2, 100.00, eventFactory.getEvent(Events.AIRPLANE), SplitType.UNEQUAL);
        ticket2.addSCashSplit(person1, 25.00);                        // create ticket 2
        ticket2.addSCashSplit(person2, 50.00);                       // create ticket 2
        ticket2.addSCashSplit(person3, 25.00);                        // create ticket 2

        Ticket ticket3 = ticketFactory.getTicket(person3, 5.00, eventFactory.getEvent(Events.TAXI), SplitType.UNEQUAL);
        ticket3.addPercentageSplit(person1, 0.10);                   // create ticket 3
        ticket3.addPercentageSplit(person2, 0.10);                   // create ticket 3
        ticket3.addPercentageSplit(person3, 0.80);                   // create ticket 3

        ticketsDBController.add(ticket1);                                   // add ticket 1 to database
        ticketsDBController.add(ticket2);                                   // add ticket 2 to database
        ticketsDBController.add(ticket3);                                   // add ticket 3 to database

        System.out.println("TEST 1 ==============================");
        ArrayList<modelxd> resultList = calculateBill.calculate();

        ticketsDBController.remove(ticket1);
        ticketsDBController.remove(ticket2);
        ticketsDBController.remove(ticket3);

        modelxd expected1 = new modelxd(person2, person1, 8.5);
        modelxd expected2 = new modelxd(person3, person1, 10);

        assertEquals(expected1.getAmount(), resultList.get(0).getAmount());
        assertEquals(expected2.getAmount(), resultList.get(1).getAmount());


    }
/*
    @Test
    void calculateBill_allEquals() {
        Ticket ticket1 = ticketFactory.getTicket(person1, 30.00, eventFactory.getEvent(Events.RESTAURANT), SplitType.EQUAL);
        ticket1.autoCalculate(personsDBController.getAll());                // create ticket 1

        Ticket ticket2 = ticketFactory.getTicket(person1, 30.00, eventFactory.getEvent(Events.RESTAURANT), SplitType.EQUAL);
        ticket2.autoCalculate(personsDBController.getAll());

        ticketsDBController.add(ticket1);                                   // add ticket 1 to database
        ticketsDBController.add(ticket2);                                   // add ticket 2 to database

        System.out.println("TEST 2 ==============================");
        ArrayList<modelxd> resultList = calculateBill.calculate();

        ticketsDBController.remove(ticket1);
        ticketsDBController.remove(ticket2);
    }*/

    @Test
    void calculateBill_equalsZero() {
        Ticket ticket1 = ticketFactory.getTicket(person1, 100.00, eventFactory.getEvent(Events.RESTAURANT), SplitType.EQUAL);
        ticket1.autoCalculate(personsDBController.getAll());
        Ticket ticket2 = ticketFactory.getTicket(person2, 100.00, eventFactory.getEvent(Events.RESTAURANT), SplitType.EQUAL);
        ticket2.autoCalculate(personsDBController.getAll());
        Ticket ticket3 = ticketFactory.getTicket(person3, 100.00, eventFactory.getEvent(Events.RESTAURANT), SplitType.EQUAL);
        ticket3.autoCalculate(personsDBController.getAll());

        ticketsDBController.add(ticket1);                                   // add ticket 1 to database
        ticketsDBController.add(ticket2);                                   // add ticket 2 to database
        ticketsDBController.add(ticket3);                                   // add ticket 3 to database

        System.out.println("TEST 3 ==============================");
        ArrayList<modelxd> resultList = calculateBill.calculate();

        ticketsDBController.remove(ticket1);
        ticketsDBController.remove(ticket2);
        ticketsDBController.remove(ticket3);

        for (modelxd m : resultList){
            assertEquals(0, m.getAmount());
        }
    }
}