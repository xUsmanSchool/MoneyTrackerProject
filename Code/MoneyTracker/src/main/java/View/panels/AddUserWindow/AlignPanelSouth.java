package View.panels.AddUserWindow;

import View.others.CustomColors;
import javax.swing.*;
import java.awt.*;

public class AlignPanelSouth extends JPanel {
    public AlignPanelSouth(JPanel p1) {
        // set layout
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        // add styling
        this.setBackground(CustomColors.getMidGrey());

        // add items
        this.add(p1, BorderLayout.PAGE_END);
    }
}
