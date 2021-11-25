package View;

import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JPanel{
    // Get your controller in this private field


    public void initialize()
    {
        this.setSize(500, 300);

        this.add(new JLabel("TEST"));

        this.setVisible(true);
    }
}
