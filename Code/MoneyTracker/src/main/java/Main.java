import DatabaseController.*;
import Database.*;
import Factory.*;
import HelperClass.*;
import Model.Person;
import Model.Ticket;
import Observers.*;
import View.frames.MainViewFrame;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public Main() {
        //--------------------------------------------------------------------------------------------------------------
        //---------------------------------------- /* Singleton pattern */ ---------------------------------------------
        // Database initialisation
        PersonsDB personDatabase = PersonsDB.getInstance();
        TicketsDB ticketDatabase = TicketsDB.getInstance();

        // Controllers for Database
        PersonsDBController personsDBController = new PersonsDBController(personDatabase);
        TicketsDBController ticketsDBController = new TicketsDBController(ticketDatabase);


        //--------------------------------------------------------------------------------------------------------------
        //----------------------------------------- /* Observer pattern */ ---------------------------------------------
        // Validating data in cmd
        DatabaseObserver dbObserver = new DatabaseObserver();
        personDatabase.addObserver(dbObserver);
        ticketDatabase.addObserver(dbObserver);

        // UI updates:
        // adding observers in mainViewFrame
        // each View controller is an Observer and has an update() method
        // for example: only updating button state when an update was received from DB

        //--------------------------------------------------------------------------------------------------------------
        //----------------------------------------- /* Factory pattern */ ----------------------------------------------
        // Different types of tickets
        EventFactory eventFactory = new EventFactory();
        TicketFactory ticketFactory = new TicketFactory();


        //--------------------------------------------------------------------------------------------------------------
        //---------------------------------------------- /* MVC */ -----------------------------------------------------
        // todo - insert explanation


        //--------------------------------------------------------------------------------------------------------------
        //---------------------------------------- /* Iterator pattern */ ----------------------------------------------
        // universal iterator
        // no need to know which kind of list the user was saved in
        // used in UserListPanelController, line 35: adding users to the user list Panel


        //--------------------------------------------------------------------------------------------------------------
        //---------------------------------------------- /* Other */ ---------------------------------------------------
        // Reading JSON files
        ReadFromJSONFile.readPersonFile(personDatabase);
        ReadFromJSONFile.readTicketFile(ticketDatabase);

        // Start app
        MainViewFrame view = new MainViewFrame("Money Tracker Application");
        view.initialize();
    }

    public void run() {}
}
