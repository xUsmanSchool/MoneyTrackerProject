package View.others;

import Model.Person;
import javax.swing.*;
import java.awt.*;

public class CustomPersonCellRenderer extends JLabel implements ListCellRenderer<Person> {
    public CustomPersonCellRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList<? extends Person> list, Person value, int index, boolean isSelected, boolean cellHasFocus) {
        // fill in text in cell
        setText(value.getFirstNameValue().replace("_", " ") + " " + value.getLastNameValue().replace("_", " "));

        // process image or set default
        if (value.getIconValue().length() == 0) {
            ImageIcon imageIcon = new ImageIcon("src/main/icons/user_icon_small.png");
            setIcon(imageIcon);
        } else {
            ImageIcon imageIcon = new ImageIcon("src/main/icons/" + value.getIconValue());
            Image image = imageIcon.getImage();
            Image newImg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newImg);
            setIcon(imageIcon);
        }

        Color background = CustomColors.getDarkGrey();
        Color foreground;

        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {
            foreground = Color.WHITE;
        } else if (isSelected) {
            background = Color.lightGray;
            foreground = Color.WHITE;
        } else {
            foreground = Color.WHITE;
        }


        setBackground(background);
        setForeground(foreground);

        return this;
    }
}
