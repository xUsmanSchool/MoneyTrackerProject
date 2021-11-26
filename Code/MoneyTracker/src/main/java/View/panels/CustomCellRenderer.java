package View.panels;

import Database.Person;
import javax.swing.*;
import java.awt.*;

public class CustomCellRenderer extends JLabel implements ListCellRenderer<Object> {
    public CustomCellRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Person person = (Person) value;
        setText(person.getFirstNameValue().replace("_", " ") + " " + person.getLastNameValue().replace("_", " "));

        // Scale down an image
        /*
            ImageIcon imageIcon = new ImageIcon("./user_icon.png");
            Image image = imageIcon.getImage();
            Image newImg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newImg);
        */

        ImageIcon imageIcon = new ImageIcon("./user_icon_small.png");
        setIcon(imageIcon);

        Color background;
        Color foreground;

        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.WHITE;
        } else if (isSelected) {
            background = Color.GRAY;
            foreground = Color.WHITE;
        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        }

        setBackground(background);
        setForeground(foreground);

        return this;
    }
}
