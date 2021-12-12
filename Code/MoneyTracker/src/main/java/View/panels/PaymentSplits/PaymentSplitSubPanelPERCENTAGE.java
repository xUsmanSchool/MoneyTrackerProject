package View.panels.PaymentSplits;

import View.others.CustomColors;
import javax.swing.*;
import java.util.ArrayList;

public class PaymentSplitSubPanelPERCENTAGE extends JPanel {
    private final JButton doneButton;
    public ArrayList<JLabel> iconLabels;
    public ArrayList<JLabel> userNames;
    public ArrayList<JSpinner> percentages_toPay;
    public ArrayList<JLabel> percentageIcons;
    public ArrayList<JLabel> amounts_converted;
    public ArrayList<Integer> maxima;

    private GroupLayout layout;
    private GroupLayout.ParallelGroup parallel;
    private GroupLayout.SequentialGroup sequential;

    public PaymentSplitSubPanelPERCENTAGE(ArrayList<JLabel> iconLabels, ArrayList<JLabel> userNames, ArrayList<JSpinner> percentages_toPay, ArrayList<JLabel> percentageIcons, ArrayList<JLabel> amounts_converted) {
        // set layout
        layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        this.setLayout(layout);

        // init
        this.iconLabels = iconLabels;
        this.userNames = userNames;
        this.percentages_toPay = percentages_toPay;
        this.percentageIcons = percentageIcons;
        this.amounts_converted = amounts_converted;

        // auto-generate row layout
        generateLayout();

        // buttons
        doneButton = new JButton();

        // button style
        doneButton.setText("DONE");
        doneButton.setBackground(CustomColors.getYellow());

        // add button to layout
        parallel.addComponent(doneButton, GroupLayout.Alignment.CENTER);
        sequential.addComponent(doneButton);

        layout.setHorizontalGroup(parallel);
        layout.setVerticalGroup(sequential);
    }

    private void generateLayout() {
        parallel = layout.createParallelGroup();
        sequential = layout.createSequentialGroup();

        for (int i = 0; i < iconLabels.size(); i++) {
            parallel.addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(iconLabels.get(i))
                    )
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(userNames.get(i))
                    )
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(percentages_toPay.get(i))
                    )
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(percentageIcons.get(i))
                    )
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(amounts_converted.get(i))
                    )
            );

            sequential.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(iconLabels.get(i))
                    .addComponent(userNames.get(i))
                    .addComponent(percentages_toPay.get(i))
                    .addComponent(percentageIcons.get(i))
                    .addComponent(amounts_converted.get(i))
            );
        }
    }

    public JButton getDoneButton() {
        return doneButton;
    }

    public ArrayList<JLabel> getUserNames() {
        return userNames;
    }

    public ArrayList<JLabel> getIconLabels() {
        return iconLabels;
    }

    public ArrayList<JLabel> getPercentageIcons() {
        return percentageIcons;
    }

    public ArrayList<JLabel> getAmounts_converted() {
        return amounts_converted;
    }

    public ArrayList<JSpinner> getPercentages_toPay() {
        return percentages_toPay;
    }

    public ArrayList<Integer> getMaxima() {
        return maxima;
    }

    public void redraw() {
        validate();
        repaint();
    }
}
