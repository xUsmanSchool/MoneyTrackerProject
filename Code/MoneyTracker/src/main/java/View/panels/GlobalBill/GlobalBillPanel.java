package View.panels.GlobalBill;

import Model.Ticket;
import Model.modelxd;
import View.others.CustomGlobalBillCellRenderer;
import View.others.CustomTicketCellRenderer;
import javax.swing.*;
import java.awt.*;

public class GlobalBillPanel extends JPanel {
    private final JLabel title;
    private final JPanel textButtonContainer;
    private final DefaultListModel<modelxd> listModel;
    private final JList<modelxd> list;

    public GlobalBillPanel() {
        // set layout
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        // create fields
        textButtonContainer = new JPanel();
        textButtonContainer.setLayout(new FlowLayout(FlowLayout.TRAILING));

        title = new JLabel();
        textButtonContainer.add(title);

        // create list
        listModel = new DefaultListModel<modelxd>();
        list = new JList<modelxd>(listModel);
        list.setCellRenderer(new CustomGlobalBillCellRenderer());
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

    public JPanel getTextButtonContainer() {
        return textButtonContainer;
    }

    public DefaultListModel<modelxd> getListModel() {
        return listModel;
    }

    public JList<modelxd> getJList() {
        return list;
    }
}
