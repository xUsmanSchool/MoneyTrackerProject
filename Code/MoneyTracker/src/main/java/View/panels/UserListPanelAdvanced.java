package View.panels;

import Database.Person;
import Database.PersonsDB;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class UserListPanelAdvanced extends JPanel implements ListSelectionListener {
    JLabel title;
    private JList list;
    private DefaultListModel listModel;
    private ArrayList<Person> personList;

    PersonsDB personsDB = PersonsDB.getInstance();

    public UserListPanelAdvanced() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        personList = personsDB.getPersons();

        title = new JLabel("Existing user list");
        title.setAlignmentX(CENTER_ALIGNMENT);

        listModel = new DefaultListModel<>();
        for (Person person : personList) listModel.addElement(person);
        list = new JList<>(listModel);
        list.setCellRenderer(new CustomCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(list);

        this.add(title);
        add(listScrollPane, BorderLayout.CENTER);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}