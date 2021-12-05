package View.panels.AddUserWindow;

import javax.swing.*;
import java.awt.*;

public class CombineJPanelGridLayoutPanel extends JPanel {
    public CombineJPanelGridLayoutPanel(JPanel p1, JPanel p2) {
        GridLayout experimentLayout = new GridLayout(0,2);
        this.setLayout(experimentLayout);

        this.add(p1);
        this.add(p2);
    }
}
