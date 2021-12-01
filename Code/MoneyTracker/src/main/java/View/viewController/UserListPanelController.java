package View.viewController;

import DatabaseController.PersonRegistrationDBController;
import Model.Person;
import Observers.PersonDBObservableEntry;
import View.panels.UserListPanel;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.Observable;

public class UserListPanelController extends vController {
    private final PersonRegistrationDBController databaseController;
    private final UserListPanel userListPanel;

    public UserListPanelController(PersonRegistrationDBController databaseController, UserListPanel userListPanel) {
        this.userListPanel = userListPanel;
        this.databaseController = databaseController;
    }

    @Override
    public void init() {
        this.userListPanel.setTitle("Existing user list");
        addPersonListToListModel(databaseController.getAll());
    }

    @Override
    public void activateActionListeners() {
        this.userListPanel.getJList().addListSelectionListener(this::listSelectionActionListener);
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

    private void listSelectionActionListener(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) return;
        System.out.println(userListPanel.getJList().getSelectedValue().getFirstNameValue() + " selected.");
    }
}
