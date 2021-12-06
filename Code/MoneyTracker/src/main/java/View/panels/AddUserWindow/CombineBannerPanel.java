package View.panels.AddUserWindow;

import View.others.CustomColors;
import javax.swing.*;
import java.awt.*;

public class CombineBannerPanel extends JPanel {
    private final JLabel banner;

    public CombineBannerPanel(JPanel mainPanel) {
        // set layout
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();

        // create items
        banner = createLabel("", CustomColors.getYellow());

        c.ipady = 65;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 5;
        c.gridheight = 0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(banner, c);

        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 80;
        c.gridheight = 0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        this.add(mainPanel, c);
    }

    private JLabel createLabel(String text, Color bg) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(bg);
        return label;
    }

    public JLabel getBanner() {
        return banner;
    }
}
