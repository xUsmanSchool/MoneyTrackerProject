package View.panels.AddUserWindow;

import View.others.CustomColors;

import javax.swing.*;
import java.awt.*;

public class AlignPanelSouth extends JPanel {
    public AlignPanelSouth(JPanel p1) {
        //BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        //this.setLayout(boxlayout);
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        //p1.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);

        //this.add(Box.createHorizontalGlue() );
        this.add(p1, BorderLayout.PAGE_END);
        //this.add(Box.createHorizontalGlue() );

        this.setBackground(CustomColors.getMidGrey());
    }
}
