package View.panels.PaymentSplits;

import View.others.CustomColors;
import javax.swing.*;
import java.util.ArrayList;

public class PaymentSplitSubPanelCASH extends JPanel {
    private final JButton doneButton;
    public ArrayList<JLabel> iconLabels;
    public ArrayList<JLabel> userNames;
    public ArrayList<JLabel> moneyIcons;
    public ArrayList<JFormattedTextField> amounts;

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
}
