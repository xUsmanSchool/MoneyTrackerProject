package View.others;

import Model.Person;
import javax.swing.*;
import java.awt.*;

public class CustomPersonCellRenderer extends JLabel implements ListCellRenderer<Object> {
    public CustomPersonCellRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        // convert object to person
        Person personObject = (Person) value;

        // fill in text in cell
        setText(personObject.getFirstNameValue().replace("_", " ") + " " + personObject.getLastNameValue().replace("_", " "));

        // process image or set default
        if (personObject.getIconValue().length() == 0) {
            ImageIcon imageIcon = new ImageIcon("src/main/icons/user_icon_small.png");
            setIcon(imageIcon);
        } else {
            ImageIcon imageIcon = new ImageIcon("src/main/icons/" + personObject.getIconValue());
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
