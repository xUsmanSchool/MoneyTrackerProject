package View.panels.AddUserWindow;

import View.others.CustomColors;
import javax.swing.*;
import java.awt.*;

public class CombineBannerPanel extends JPanel {
    private JLabel banner;

    public CombineBannerPanel(JPanel mainPanel) {
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        GridBagConstraints c = new GridBagConstraints();
        banner = createLabel("", CustomColors.getYellow());

        c.ipady = 60;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.5;
        c.weighty = 3;
        c.gridheight = 1;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        this.add(banner, c);

        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 10;
        c.weightx = 10;
        c.weighty = 10;
        c.gridheight = 1;
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
