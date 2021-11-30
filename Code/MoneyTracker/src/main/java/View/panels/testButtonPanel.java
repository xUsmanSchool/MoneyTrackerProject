package View.panels;

import javax.swing.*;
import java.awt.*;

public class testButtonPanel extends JPanel {
    public testButtonPanel() {
        /**
        3 knoppen naast elkaar
         */

        JButton b = new JButton("nuffing1");
        this.add(b);

        b = new JButton("nuffing2");
        this.add(b);

        b = new JButton("nuffing3");
        this.add(b);


        /**
         3 knoppen onder, full width
         */
        /*
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);

        JButton button = new JButton("Button 1 (PAGE_START)");
        this.add(button, BorderLayout.PAGE_START);

        button = new JButton("Button 3 (CENTER)");
        this.add(button, BorderLayout.CENTER);


        button = new JButton("Long-Named Button 5 (PAGE_END)");
        this.add(button, BorderLayout.PAGE_END);
        */
    }
}
