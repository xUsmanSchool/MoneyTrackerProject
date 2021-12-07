package ViewController.AddUserWindow;

import DatabaseController.*;
import HelperClass.Paths;
import Observers.*;
import Model.*;
import View.others.CustomColors;
import View.panels.AddUserWindow.UserListPanel;
import ViewController.ViewController;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        //this.userListPanel.getJList().addListSelectionListener(this::listSelectionActionListener);
        this.userListPanel.getJList().addMouseListener(listSelectionMouseAdapter());
    }

    // https://stackoverflow.com/questions/4344682/double-click-event-on-jlist-element
    private MouseAdapter listSelectionMouseAdapter() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList<Person> list = (JList<Person>)evt.getSource();
                if (evt.getClickCount() == 2) {

                    // Double-click detected
                    System.out.println(userListPanel.getJList().getSelectedValue().getFirstNameValue() + " double clicked.");

                    // Prep
                    ImageIcon icon = new ImageIcon(Paths.iconPath + userListPanel.getJList().getSelectedValue().getIconValue());
                    String username = userListPanel.getJList().getSelectedValue().getFirstNameValue() + " " + userListPanel.getJList().getSelectedValue().getLastNameValue();
                    String title = "Delete?";
                    String text = "Would you like to delete " + username + " ?";
                    Object[] options = {"Yes", "No", "Cancel"};
                    Object selectedAction = options[2];

                    // Display
                    int answer = JOptionPane.showOptionDialog(
                            null,
                            text,
                            title,
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            icon,
                            options,
                            selectedAction);

                    // Action
                    if (answer == 0) {
                        Person personToRemove = userListPanel.getJList().getSelectedValue();;
                        personsDatabaseController.remove(personToRemove);
                        System.out.println(personToRemove.getFirstNameValue() + " removed.");
                    } else {
                        System.out.println("Action cancelled.");
                    }
                }
            }
        };
    }

    private void listSelectionActionListener(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) return;
        System.out.println(userListPanel.getJList().getSelectedValue().getFirstNameValue() + " selected.");
    }

    @Override
    public void update(Observable o, Object arg) {
        // get current position
        int currentIndex = userListPanel.getJList().getSelectedIndex();

        // DB update
        PersonDBObservableEntry e = (PersonDBObservableEntry) arg;
        if (e.isAdded()) addPersonToListModel(e.getPerson());
        else removePersonFromListModel(e.getPerson());

        // visual update
        if (e.isAdded()) userListPanel.getJList().ensureIndexIsVisible(userListPanel.getListModel().getSize()-1);
        else userListPanel.getJList().ensureIndexIsVisible(currentIndex-1);
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