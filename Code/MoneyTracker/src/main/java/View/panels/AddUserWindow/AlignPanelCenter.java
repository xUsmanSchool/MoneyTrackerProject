package View.panels.AddUserWindow;

import View.others.CustomColors;
import javax.swing.*;

public class AlignPanelCenter extends JPanel {
    public AlignPanelCenter(JComponent p1) {
        // set layout
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);

        // add styling
        this.setBackground(CustomColors.getMidGrey());
        p1.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        // add items
        this.add(Box.createVerticalGlue() );
        this.add(p1 );
        this.add(Box.createVerticalGlue() );
    }
}
