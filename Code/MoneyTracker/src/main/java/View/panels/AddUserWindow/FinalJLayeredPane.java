package View.panels.AddUserWindow;

import HelperClass.Paths;
import View.others.CustomColors;
import javax.swing.*;
import java.awt.*;

public class FinalJLayeredPane extends JLayeredPane {
    private final JButton mainIcon;
    private final JLabel mainLabel;
    private final Image image;

    public FinalJLayeredPane(JPanel layer0) {
        mainIcon = new JButton();
        ImageIcon icon = new ImageIcon(Paths.iconPath + "test.png");
        image = icon.getImage();
        mainIcon.setIcon(new ImageIcon(image.getScaledInstance(70, 70,  java.awt.Image.SCALE_SMOOTH)));
        mainIcon.setBounds(35, 35, 70, 70);
        mainIcon.setBorder(BorderFactory.createBevelBorder(0, CustomColors.getMidGrey(), CustomColors.getDarkGrey()));
        this.add(mainIcon, new Integer(2));

        mainLabel = new JLabel();
        mainLabel.setText("Future Engineers");
        mainLabel.setFont(new Font("Courier", Font.BOLD,20));
        mainLabel.setBounds(45+mainIcon.getWidth(), 30, 200, 50);
        this.add(mainLabel, new Integer(2));

        layer0.setBounds(0, 0, 720, 480);
        this.add(layer0, new Integer(0));
    }

    public void setBoundsMainIcon(int x, int y, int w, int h) {
        mainIcon.setBounds(x, y, w, h);
        updateScaling(w, h);
    }

    public void setBoundsMainLabel(int x, int y, int w, int h) {
        mainLabel.setBounds(x+mainIcon.getWidth(), y, w, h);
    }

    public void updateScaling(int w, int h) {
        if (w != 0 && h != 0) mainIcon.setIcon(new ImageIcon(image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH)));
    }
}
