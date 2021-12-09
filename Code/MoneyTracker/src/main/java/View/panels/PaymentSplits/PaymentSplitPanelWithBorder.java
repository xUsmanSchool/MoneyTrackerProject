package View.panels.PaymentSplits;

import View.others.CustomColors;

import javax.swing.*;

public class PaymentSplitPanelWithBorder extends PaymentSplitPanel {
    public PaymentSplitPanelWithBorder() {
        // set layout
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        // styling
        this.setBackground(CustomColors.getMidGrey());
        this.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        this.setBorder(BorderFactory.createEmptyBorder(40,100,40,100));
    }
}
