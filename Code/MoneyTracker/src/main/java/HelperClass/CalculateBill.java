package HelperClass;

import DatabaseController.PersonsDBController;
import DatabaseController.TicketsDBController;
import Model.Person;
import Model.Ticket;
import Model.modelxd;
import java.util.ArrayList;

public class CalculateBill {
    PersonsDBController personsDBController;
    TicketsDBController ticketsDBController;

    public CalculateBill(PersonsDBController personsDBController, TicketsDBController ticketsDBController) {
        this.personsDBController = personsDBController;
        this.ticketsDBController = ticketsDBController;
    }

    public ArrayList<modelxd> calculate() {
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

        // read tickets and update values
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

        // debug
        int i = 0;
        for (modelxd m: combinationList) {
            System.out.println("Entry " + i + " from: " + m.getPersonFrom().getFirstNameValue() + " to " + m.getPersonTo().getFirstNameValue() + " is " + m.getAmount());
            i++;
        }

        // remove loops
        // time complexity of O((n*(n-1))^3) => 4 users: 1728 passes, 5 users: 8000 passes
        System.out.println("");
        Person p = null;

        for (modelxd m: combinationList) {
            p = m.getPersonFrom();
            for (modelxd s: combinationList) {
                if (s.getPersonTo() == p) {

                    // remove loop
                    double missingAmount = 0.00;
                    if (s.getAmount() >= m.getAmount()) {
                        missingAmount = m.getAmount();
                        s.setAmount(s.getAmount() - m.getAmount());
                        m.setAmount(0.00);
                    }
                    else {
                        missingAmount = s.getAmount();
                        m.setAmount(m.getAmount() - s.getAmount());
                        s.setAmount(0.00);
                    }

                    // find a different path directly to the source and add this missing amount from the loop we removed
                    for (modelxd d: combinationList) {
                        if (d.getPersonFrom() == s.getPersonFrom() && d.getPersonTo() == m.getPersonTo()) {
                            d.setAmount(d.getAmount() + missingAmount);
                            break;
                        }
                    }
                }
            }
        }

        // remove duplicates
        // A -> B & B -> A are the same
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

        // debug
        System.out.println("\nSimplified list after loop fixes");
        for (modelxd m: combinationListShortened) {
            System.out.println("Match from " + m.getPersonFrom().getFirstNameValue() + " owes " + m.getPersonTo().getFirstNameValue() +  " $ " + m.getAmount());
        }

        // switch - to +
        for (modelxd m: combinationListShortened) {
            if (m.getAmount() < 0) {
                m.swapPersons();
                m.setAmount(m.getAmount() * -1);
            }
        }

        System.out.println("\nSimplified list after negative switches & removal of 0's");
        for (modelxd m: combinationListShortened) {
            if (m.getAmount() != 0.00)
            System.out.println("Match from " + m.getPersonFrom().getFirstNameValue() + " owes " + m.getPersonTo().getFirstNameValue() +  " $ " + m.getAmount());
        }

        return combinationListShortened;
    }
}
