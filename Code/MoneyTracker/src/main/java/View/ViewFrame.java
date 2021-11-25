package View;

import View.panels.*;
import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
    public ViewFrame() {}

    public void initialize() {
        this.setSize(720, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        UserCreationPanel userCreationPanel = new UserCreationPanel();
        //JLabel title = new JLabel("Registeration screen");

        this.add(userCreationPanel);
        this.setVisible(true);
    }
}
