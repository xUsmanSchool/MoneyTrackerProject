package View;

import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
    public ViewFrame() {}

    public void initialize() {
        this.setSize(720, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        this.add(new JLabel("TEST"));

        this.setVisible(true);
    }
}
