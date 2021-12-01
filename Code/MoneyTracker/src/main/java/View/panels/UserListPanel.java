package View.panels;

import Model.*;
import View.others.*;
import javax.swing.*;
import java.awt.*;

public class UserListPanel extends JPanel {
    private final JLabel title;
    private final DefaultListModel<Person> listModel;
    private JList<Person> list;

    public UserListPanel() {
        // set layout
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        // create fields
        title = new JLabel();
        title.setAlignmentX(CENTER_ALIGNMENT);

        // create list
        listModel = new DefaultListModel<Person>();
        list = new JList<Person>(listModel);
        list.setCellRenderer(new CustomPersonCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        JScrollPane listScrollPane = new JScrollPane(list);

        // add items
        this.add(title);
        add(listScrollPane, BorderLayout.CENTER);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public DefaultListModel<Person> getListModel() {
        return listModel;
    }

    public JList<Person> getJList() {
        return list;
    }
}