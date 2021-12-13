package View.panels.AddUserWindow;

import Model.*;
import View.others.*;
import javax.swing.*;
import java.awt.*;

public class UserListPanel extends JPanel {
    private final JLabel title;
    private final JButton button;
    private final DefaultListModel<Person> listModel;
    private final JList<Person> list;

    public UserListPanel() {
        // set layout
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        // create fields
        title = new JLabel();
        button = new JButton();

        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.X_AXIS));
        titleContainer.add(Box.createHorizontalGlue());
        titleContainer.add(title);
        titleContainer.add(Box.createHorizontalGlue());
        titleContainer.add(button);

        // styling
        button.setAlignmentX(RIGHT_ALIGNMENT);
        title.setAlignmentX(CENTER_ALIGNMENT);
        titleContainer.setBackground(CustomColors.getMidGrey());
        button.setBackground(CustomColors.getYellow());
        button.setVisible(false);

        // create list
        listModel = new DefaultListModel<Person>();
        list = new JList<Person>(listModel);
        list.setCellRenderer(new CustomPersonCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        JScrollPane listScrollPane = new JScrollPane(list);

        // add items
        this.add(titleContainer);
        this.add(listScrollPane, BorderLayout.CENTER);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public JLabel getTitleLabel() {
        return title;
    }

    public DefaultListModel<Person> getListModel() {
        return listModel;
    }

    public JList<Person> getJList() {
        return list;
    }

    public JButton getButton() {
        return button;
    }
}