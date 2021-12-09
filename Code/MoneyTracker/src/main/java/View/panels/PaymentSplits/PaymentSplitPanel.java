package View.panels.PaymentSplits;

import View.others.CustomColors;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PaymentSplitPanel extends JPanel {
    JLabel tabObject1, tabObject2;
    JTabbedPane tabbedPane;

    public PaymentSplitPanel() {
        GridLayout layout = new GridLayout(1, 1);
        this.setLayout(layout);

        // init tabs
        tabObject1 = new JLabel("Split - cash");
        tabObject2 = new JLabel("Split - percentage");

        // tab styling
        tabObject1.setPreferredSize(new Dimension(235, 30));
        tabObject2.setPreferredSize(new Dimension(235, 30));
        tabObject1.setBackground(CustomColors.getDarkGrey());
        tabObject2.setBackground(CustomColors.getDarkGrey());
        tabObject1.setForeground(Color.WHITE);
        tabObject2.setForeground(Color.WHITE);
        tabObject1.setHorizontalAlignment(SwingConstants.CENTER);
        tabObject2.setHorizontalAlignment(SwingConstants.CENTER);
        tabObject1.setFont(new Font("Arial", Font.BOLD, 16));
        tabObject2.setFont(new Font("Arial", Font.BOLD, 16));

        // UIManager styling
        UIManager.put("TabbedPane.selected", CustomColors.getDarkGrey());

        // init tab content panels
        PaymentSplitSubPanelCASH contentPanel1 = new PaymentSplitSubPanelCASH();
        contentPanel1.setBackground(CustomColors.getDarkGrey());
        contentPanel1.setBorder(BorderFactory.createEmptyBorder(15,15,15,50));

        PaymentSplitSubPanelPERCENTAGE contentPanel2 = new PaymentSplitSubPanelPERCENTAGE();
        contentPanel2.setBackground(CustomColors.getDarkGrey());

        // auto-create rest
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("", contentPanel1);
        tabbedPane.setTabComponentAt(0, tabObject1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab("", contentPanel2);
        tabbedPane.setTabComponentAt(1, tabObject2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        // prep for color change
        tabbedPane.setBackground(Color.GRAY);
        tabbedPane.setSelectedIndex(0);
        setSelectedColors(0);

        // changes color on tab switch
        tabbedPane.addChangeListener( new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                setSelectedColors(selectedIndex);
            }
        });

        add(tabbedPane);

        // The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    private void setSelectedColors(int selectedIndex) {
        if (selectedIndex == 0) tabObject1.setForeground(CustomColors.getYellow());
        else tabObject1.setForeground(Color.WHITE);

        if (selectedIndex == 1) tabObject2.setForeground(CustomColors.getYellow());
        else tabObject2.setForeground(Color.WHITE);
    }
}