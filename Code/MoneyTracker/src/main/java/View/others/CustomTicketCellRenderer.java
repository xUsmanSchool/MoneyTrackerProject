package View.others;


import Model.*;
import javax.swing.*;
import java.awt.*;

public class CustomTicketCellRenderer extends JLabel implements ListCellRenderer<Ticket> {
    public CustomTicketCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Ticket> list, Ticket value, int index, boolean isSelected, boolean cellHasFocus) {
        // fill in text in cell
        setVerticalTextPosition(CENTER);
        String textLine1 = value.getEventTypeValue().getEventName();
        String textLine2 = value.getTotalSum() + "$ was payed by " + value.getPayedByValue().getFirstNameValue() + " on " + value.getCreationDateValue().getDayOfMonth() + "/" + value.getCreationDateValue().getMonthValue();
        setText("<html><b><font size=+1>" +
                textLine1 +
                "</font></b><br/>" +
                "<font color=#FFD369>" +
                textLine2 +
                "</font></html>");

        //<html>textLine1<br/><font color=#FFD369>textLine2</font></html>

        // process image or set default
        if (value.getIconValue().length() == 0) {
            ImageIcon imageIcon = new ImageIcon("src/main/icons/user_icon_small.png");
            setIcon(imageIcon);
        } else {
            ImageIcon imageIcon = new ImageIcon("src/main/eventIcons/" + value.getIconValue());
            Image image = imageIcon.getImage();
            Image newImg = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newImg);
            setIcon(imageIcon);
        }

        Color background = CustomColors.getDarkGrey();
        Color foreground = Color.WHITE;

        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {
        } else if (isSelected) {
            background = Color.lightGray;
        } else {
            //
        }

        setBackground(background);
        setForeground(foreground);

        return this;
    }
}


    /*private JPanel p;
    private JPanel iconPanel;
    private JLabel l;
    private JTextArea ta1, ta2, ta3;

    public CustomTicketCellRenderer () {
        p = new JPanel();
        p.setLayout(new BorderLayout());

        // icon
        iconPanel = new JPanel(new BorderLayout());
        ImageIcon icon = new ImageIcon("src/main/eventIcons/event_plane.png");
        JLabel labelForImage = new JLabel("");
        labelForImage.setIcon(icon);
        iconPanel.add(labelForImage, BorderLayout.NORTH);
        p.add(iconPanel, BorderLayout.WEST);

        ta1 = new JTextArea();
        ta1.setLineWrap(true);
        ta1.setWrapStyleWord(true);
        ta2 = new JTextArea();
        ta2.setLineWrap(true);
        ta2.setWrapStyleWord(true);
        ta3 = new JTextArea();
        ta3.setLineWrap(true);
        ta3.setWrapStyleWord(true);

        JPanel cellPanel = new JPanel();
        cellPanel.setLayout(new BoxLayout(cellPanel, BoxLayout.Y_AXIS));
        cellPanel.add(ta1);
        cellPanel.add(ta2);
        cellPanel.add(ta3);

        p.add(cellPanel, BorderLayout.CENTER);

        p.setBackground(CustomColors.getDarkGrey());
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Ticket> list, Ticket value, int index, boolean isSelected, boolean cellHasFocus) {

        ta1.setText("SomeTEXT north");
        ta2.setText("SomeTEXT mid");
        ta3.setText("SomeTEXT south");
        int width = list.getWidth();
        // this is just to lure the ta's internal sizing mechanism into action
        if (width > 0) {
            ta1.setSize(width, Short.MAX_VALUE);
            ta2.setSize(width, Short.MAX_VALUE);
            ta3.setSize(width, Short.MAX_VALUE);
        }

        return p;
    }
}*/
