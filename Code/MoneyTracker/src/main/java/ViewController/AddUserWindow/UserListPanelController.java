package ViewController.AddUserWindow;

import DatabaseController.*;
import Observers.*;
import Model.*;
import View.panels.UserListPanel;
import ViewController.ViewController;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.Observable;

public class UserListPanelController extends ViewController {
    private final PersonsDBController personsDatabaseController;
    private final UserListPanel userListPanel;

    public UserListPanelController(PersonsDBController personsDatabaseController, UserListPanel userListPanel) {
        this.userListPanel = userListPanel;
        this.personsDatabaseController = personsDatabaseController;
    }

    @Override
    public void init() {
        this.userListPanel.setTitle("Existing user list");
        addPersonListToListModel(personsDatabaseController.getAll());
    }

    @Override
    public void activateActionListeners() {
        this.userListPanel.getJList().addListSelectionListener(this::listSelectionActionListener);
    }

    private void listSelectionActionListener(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) return;
        System.out.println(userListPanel.getJList().getSelectedValue().getFirstNameValue() + " selected.");
    }

    @Override
    public void update(Observable o, Object arg) {
        PersonDBObservableEntry e = (PersonDBObservableEntry) arg;
        if (e.isAdded()) addPersonToListModel(e.getPerson());
        else removePersonFromListModel(e.getPerson());
    }

    private void addPersonListToListModel(ArrayList<Person> personList) {
        for (Person person : personList) userListPanel.getListModel().addElement(person);
    }

    private void addPersonToListModel(Person person){
        userListPanel.getListModel().addElement(person);
    }

    private void removePersonFromListModel(Person person) {
        userListPanel.getListModel().removeElement(person);
    }
}