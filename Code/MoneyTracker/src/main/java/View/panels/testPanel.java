package View.panels;

import javax.swing.*;
import java.awt.*;

public class testPanel extends JPanel {
    public testPanel() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(layout);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        JButton button1 = new JButton("aaaaaaa");
        layout.setConstraints(button1, c);
        add(button1);
        JButton button3= new JButton("bbbbbbbb");
        layout.setConstraints(button3, c);
        add(button3);
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        JButton button2 = new JButton("ccccccc");
        layout.setConstraints(button2, c);
        add(button2);
        c.weightx = 0.0;                //reset to the default
        JButton button4 = new JButton("dddddddd");
        layout.setConstraints(button4, c);
        add(button4);

        c.weightx = 2.0;
        c.fill = GridBagConstraints.SOUTH;
        JButton button10 = new JButton("end?");
        layout.setConstraints(button10, c);
        add(button10);


        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
    }
}
