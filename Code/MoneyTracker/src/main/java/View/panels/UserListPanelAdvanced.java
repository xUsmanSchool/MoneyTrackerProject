package View.panels;

import Database.Person;
import Database.PersonsDB;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class UserListPanelAdvanced extends JPanel implements ListSelectionListener {
    PersonsDB personsDB = PersonsDB.getInstance();

    public UserListPanelAdvanced() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        ArrayList<Person> personList = personsDB.getAll();
        // todo - is this allowed without controller?

        JLabel title = new JLabel("Existing user list");
        title.setAlignmentX(CENTER_ALIGNMENT);

        DefaultListModel<Person> listModel = new DefaultListModel<Person>();
        for (Person person : personList) listModel.addElement(person);
        JList<Person> list = new JList<Person>(listModel);
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