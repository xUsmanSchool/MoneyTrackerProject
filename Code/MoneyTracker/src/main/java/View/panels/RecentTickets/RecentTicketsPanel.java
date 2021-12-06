package View.panels.RecentTickets;

import Model.Ticket;
import View.others.CustomTicketCellRenderer;
import javax.swing.*;
import java.awt.*;

public class RecentTicketsPanel extends JPanel {
    private final JLabel title;
    private final JPanel textButtonContainer;
    private final JButton addTicketButton, checkout;
    private final DefaultListModel<Ticket> listModel;
    private final JList<Ticket> list;

    public RecentTicketsPanel() {
        // set layout
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        // create fields
        textButtonContainer = new JPanel();
        textButtonContainer.setLayout(new FlowLayout(FlowLayout.TRAILING));

        title = new JLabel();
        checkout = new JButton();
        addTicketButton = new JButton();

        textButtonContainer.add(title);
        textButtonContainer.add(checkout);
        textButtonContainer.add(addTicketButton);

        // create list
        listModel = new DefaultListModel<Ticket>();
        list = new JList<Ticket>(listModel);
        list.setCellRenderer(new CustomTicketCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        JScrollPane listScrollPane = new JScrollPane(list);

        // add items
        this.add(textButtonContainer);
        this.add(listScrollPane, BorderLayout.CENTER);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public JLabel getTitleLabel() {
        return title;
    }

    public JButton getAddTicketButton() {
        return addTicketButton;
    }

    public JButton getCheckoutButton() {
        return checkout;
    }

    public JPanel getTextButtonContainer() {
        return textButtonContainer;
    }

    public DefaultListModel<Ticket> getListModel() {
        return listModel;
    }

    public JList<Ticket> getJList() {
        return list;
    }
}
