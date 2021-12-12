package View.panels.PaymentSplits;

import View.others.CustomColors;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class PaymentSplitSubPanelCASH extends JPanel {
    private final JButton doneButton;
    private ArrayList<JLabel> iconLabels;
    private ArrayList<JLabel> userNames;
    private ArrayList<JLabel> moneyIcons;
    private ArrayList<JFormattedTextField> amounts;
    private JLabel remainingAmount;

    private final GroupLayout layout;
    private GroupLayout.ParallelGroup parallel;
    private GroupLayout.SequentialGroup sequential;

    public PaymentSplitSubPanelCASH(ArrayList<JLabel> iconLabels, ArrayList<JLabel> userNames, ArrayList<JLabel> moneyIcons, ArrayList<JFormattedTextField> amounts) {
        // set layout
        layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        this.setLayout(layout);

        // init
        this.iconLabels = iconLabels;
        this.userNames = userNames;
        this.moneyIcons = moneyIcons;
        this.amounts = amounts;
        this.remainingAmount = new JLabel();

        // auto-generate row layout
        generateLayout();

        // buttons
        doneButton = new JButton();

        // style
        doneButton.setText("DONE");
        doneButton.setBackground(CustomColors.getYellow());
        remainingAmount.setForeground(Color.WHITE);

        // add button and "remaining amount" to layout
        parallel.addComponent(doneButton, GroupLayout.Alignment.CENTER);
        parallel.addComponent(remainingAmount, GroupLayout.Alignment.TRAILING);

        sequential.addComponent(doneButton);
        sequential.addComponent(remainingAmount);

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
                            .addComponent(moneyIcons.get(i))
                    )
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(amounts.get(i))
                    ));

            sequential.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(iconLabels.get(i))
                    .addComponent(userNames.get(i))
                    .addComponent(moneyIcons.get(i))
                    .addComponent(amounts.get(i))
            );
        }
    }

    public JButton getDoneButton() {
        return doneButton;
    }

    public ArrayList<JLabel> getIconLabels() {
        return iconLabels;
    }

    public ArrayList<JLabel> getUserNames() {
        return userNames;
    }

    public ArrayList<JFormattedTextField> getAmounts() {
        return amounts;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount.setText("Remaining: $" + String. format("%.2f", remainingAmount));
        if (remainingAmount == 0.00)  {
            this.remainingAmount.setForeground(Color.GREEN);
            this.doneButton.setEnabled(true);
        }
        else {
            this.remainingAmount.setForeground(Color.RED);
            this.doneButton.setEnabled(false);
        }
    }
}
