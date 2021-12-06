package ViewController.AddUserWindow;

import DatabaseController.*;
import Observers.*;
import Model.*;
import View.others.CustomColors;
import View.panels.AddUserWindow.UserListPanel;
import ViewController.ViewController;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
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
        this.userListPanel.setTitle("Members");
        addPersonListToListModel(personsDatabaseController.getAll());

        userListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 15));
        userListPanel.getTitleLabel().setForeground(Color.WHITE);
        userListPanel.getTitleLabel().setFont(new Font("", Font.PLAIN, 16));
        userListPanel.getJList().setBackground(CustomColors.getDarkGrey());
        userListPanel.setBackground(CustomColors.getMidGrey());
        userListPanel.getJList().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
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
        // DB update
        PersonDBObservableEntry e = (PersonDBObservableEntry) arg;
        if (e.isAdded()) addPersonToListModel(e.getPerson());
        else removePersonFromListModel(e.getPerson());

        // visual update

        // select user and scroll down
        // userListPanel.getJList().setSelectedIndex(userListPanel.getListModel().getSize()-1);
        //userListPanel.getJList().ensureIndexIsVisible(userListPanel.getJList().getSelectedIndex());

        // just scroll down
        userListPanel.getJList().ensureIndexIsVisible(userListPanel.getListModel().getSize()-1);
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