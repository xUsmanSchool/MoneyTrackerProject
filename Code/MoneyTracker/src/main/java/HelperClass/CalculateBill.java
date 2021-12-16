package HelperClass;

import DatabaseController.PersonsDBController;
import DatabaseController.TicketsDBController;
import Model.Person;
import Model.Ticket;
import Model.modelxd;

import java.util.ArrayList;
import java.util.Iterator;

public class CalculateBill {
    PersonsDBController personsDBController;
    TicketsDBController ticketsDBController;

    public CalculateBill(PersonsDBController personsDBController, TicketsDBController ticketsDBController) {
        this.personsDBController = personsDBController;
        this.ticketsDBController = ticketsDBController;
    }

    public void calculate() {
        System.out.println("WIP calculating global bill");

        // users * (users -1)
        // A -> B      B -> A      C -> A      D -> A
        // A -> C      B -> C      C -> B      D -> B
        // A -> D      B -> D      C -> D      D -> C
        ArrayList<modelxd> combinationList = new ArrayList<>();

        ArrayList<Person> personArrayList = personsDBController.getAll();
        ArrayList<Ticket> ticketArrayList = ticketsDBController.getAll();

        // fill up with empty values
        for (Person p1: personArrayList) {
            for (Person p2: personArrayList)
                if (!(p1 == p2)) combinationList.add(new modelxd(p1, p2, 0.00));
        }

        // update values
        for (Ticket t: ticketArrayList) {
            for (Person p1: personArrayList) {
                Double amount = t.getAmountForPerson(p1);
                Person payedBy = t.getPayedByValue();

                if (!(p1 == payedBy)) {
                    boolean match = false;
                    for (modelxd m: combinationList) {
                        if (m.getPersonFrom() == p1 && m.getPersonTo() == payedBy) match = true;
                        if (match) {
                            m.setAmount(Math.abs(m.getAmount()) + Math.abs(amount));
                            match = false;
                            break;
                        }
                    }
                }
            }
        }

        // remove duplicates
        ArrayList<modelxd> combinationListShortened = new ArrayList<>();
        for (modelxd m: combinationList) {
            for (modelxd s: combinationList) {
                if ((m.getPersonTo() == s.getPersonFrom() && m.getPersonFrom() == s.getPersonTo())) {

                    boolean alreadyExists = false;
                    for (modelxd y: combinationListShortened)
                        if ((y.getPersonFrom() == m.getPersonTo() && y.getPersonTo() == m.getPersonFrom())) {
                            alreadyExists = true;
                            break;
                        }

                    if (!alreadyExists) combinationListShortened.add(new modelxd(m.getPersonFrom(), m.getPersonTo(), m.getAmount()-s.getAmount()));
                }
            }
        }

        int i = 0;
        for (modelxd m: combinationList) {
            System.out.println("Entry " + i + " from: " + m.getPersonFrom().getFirstNameValue() + " to " + m.getPersonTo().getFirstNameValue() + " is " + m.getAmount());
            i++;
        }

        System.out.println("\nSimplified list");
        for (modelxd m: combinationListShortened) {
            if (m.getAmount() > 0) System.out.println("Match from " + m.getPersonFrom().getFirstNameValue() + " owes " + m.getPersonTo().getFirstNameValue() +  " $ " + m.getAmount());
            else System.out.println("Match from " + m.getPersonTo().getFirstNameValue() + " owes " + m.getPersonFrom().getFirstNameValue() +  " $ " + Math.abs(m.getAmount()));
        }
    }
}
