package View.panels;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class UserListPanel extends JPanel implements ListSelectionListener {
    JLabel title;
    private JList list;
    private DefaultListModel listModel;

    public UserListPanel() {
        BoxLayout layout = new BoxLayout(this,  BoxLayout.Y_AXIS);
        this.setLayout(layout);

        title = new JLabel("Existing user list");
        title.setAlignmentX(CENTER_ALIGNMENT);

        listModel = new DefaultListModel<>();
        listModel.addElement("List item 1");
        listModel.addElement("List item 2");
        listModel.addElement("List item 3");
        listModel.addElement("List item 4");
        listModel.addElement("List item 5");
        list = new JList<>(listModel);
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
