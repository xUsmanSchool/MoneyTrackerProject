package View.panels.AddUserWindow;

import View.others.CustomColors;
import javax.swing.*;

public class CenteredUserCreationPanel extends JPanel {
    public CenteredUserCreationPanel(JPanel p1) {
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);

        p1.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        this.add(Box.createVerticalGlue() );
        this.add(p1 );
        this.add(Box.createVerticalGlue() );

        this.setBackground(CustomColors.getMidGrey());
    }
}
