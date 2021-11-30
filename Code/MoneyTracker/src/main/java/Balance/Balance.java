package Balance;

import Database.Person;
import Database.Ticket;
import HelperClass.Date;

import java.util.ArrayList;
import java.util.HashMap;

public class Balance {
    private Person person;
    private double outstandingAmount;
    private HashMap<Date, Ticket> tickets;
    private HashMap<Date, String> history;

    public Balance(Person person) {
        this.person = person;
        this.outstandingAmount = 0;
        this.tickets = new HashMap<>();
        this.history = new HashMap<>();
    }

    public void add(double amount, Ticket ticket, Date date) {
        outstandingAmount += amount;
        tickets.put(date, ticket);
        history.put(date, createHistoryField(amount, date));
    }

    private String createHistoryField(double amount, Date date) {
        String s =  (amount > 0) ? amount + " got added on " : amount + " got removed on ";
        return s + date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
    }

    public Double getOutstandingAmount() {
        return outstandingAmount;
    }

    public ArrayList<String> getHistory() {
        ArrayList<String> s = new ArrayList<>();
        for (Date key:history.keySet()) s.add(history.get(key));
        return s;
    }
}
