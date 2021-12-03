package View.panels.AddUserWindow;

import View.others.CustomColors;

import javax.swing.*;
import java.awt.*;

public class FinalJLayeredPane extends JLayeredPane {
    public FinalJLayeredPane(JPanel layer0) {
        JButton mainIcon = new JButton();
        ImageIcon icon2 = new ImageIcon("src/main/icons/test.png");
        Image image2 = icon2.getImage();
        Image newImg = image2.getScaledInstance(70, 70,  java.awt.Image.SCALE_SMOOTH);
        mainIcon.setIcon(new ImageIcon(newImg));
        mainIcon.setBounds(35, 35, 70, 70);
        mainIcon.setBorder(BorderFactory.createBevelBorder(0, CustomColors.getMidGrey(), CustomColors.getMidGrey()));
        this.add(mainIcon, new Integer(2));

        JLabel mainLabel = new JLabel();
        mainLabel.setText("Future Engineers");
        mainLabel.setFont(new Font("Courier", Font.BOLD,20));
        mainLabel.setBounds(45+mainIcon.getWidth(), 30, 200, 50);
        this.add(mainLabel, new Integer(2));

        layer0.setBounds(0, 0, 720, 480);
        this.add(layer0, new Integer(0));
    }
}
