package View.panels;

import javax.swing.*;
import java.awt.*;

public class testPanel2 extends JPanel {
    public testPanel2(JPanel addOn1, JPanel addon2) {
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);

        //JButton button = new JButton("Button 1 (PAGE_START)");
        //this.add(button, BorderLayout.PAGE_START);

        JLabel titel = new JLabel("WOOOOOOOOOOOOO");
        titel.setHorizontalAlignment(JLabel.CENTER);
        this.add(titel, BorderLayout.PAGE_START);

        this.add(addOn1, BorderLayout.CENTER);


        this.add(addon2, BorderLayout.PAGE_END);
        //button = new JButton("Long-Named Button 5 (PAGE_END)");
        //this.add(button, BorderLayout.PAGE_END);
    }
}
