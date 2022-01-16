package View.others;

import HelperClass.Paths;
import Model.Ticket;
import Model.modelxd;

import javax.swing.*;
import java.awt.*;

public class CustomGlobalBillCellRenderer extends JLabel implements ListCellRenderer<modelxd> {
    public CustomGlobalBillCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends modelxd> list, modelxd value, int index, boolean isSelected, boolean cellHasFocus) {
        // fill in text in cell
        setVerticalTextPosition(CENTER);
        String textLine1 = value.getPersonFrom().getFirstNameValue() + " owes " + value.getPersonTo().getFirstNameValue();
        String payedSumFormatted = String.format("%.2f", value.getAmount());
        String textLine2 = "$" + payedSumFormatted;
        setText("<html><b><font>" + textLine1 +
                "</font>" +
                "<font color=#FFD369>" + " " +
                textLine2 +
                "</font></b></html>");

        // ^ same thing on one line:
        //<html>textLine1<br/><font color=#FFD369>textLine2</font></html>

        // process image or set default
        if (value.getPersonFrom().getIconValue().length() == 0) {
            ImageIcon imageIcon = new ImageIcon(Paths.iconPath + "user_icon_small.png");
            setIcon(imageIcon);
        } else {
            ImageIcon imageIcon = new ImageIcon(Paths.iconPath + value.getPersonFrom().getIconValue());
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
