package View.panels.PaymentSplits;

import HelperClass.Paths;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PaymentSplitSubPanelCASH extends JPanel {
    JButton doneButton;
    ArrayList<JLabel> iconLabels;
    ArrayList<JLabel> userNames;
    ArrayList<JLabel> moneyIcons;
    ArrayList<JTextField> amounts;

    public PaymentSplitSubPanelCASH() {
        // set layout
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        this.setLayout(layout);

        // init
        iconLabels = new ArrayList<>();
        userNames = new ArrayList<>();
        moneyIcons = new ArrayList<>();
        amounts = new ArrayList<>();

        // icons
        Icon icon = new ImageIcon(Paths.iconPath + "user_icon_small.png");

        // attempt 1

        ArrayList<String> userList = new ArrayList<>();
        userList.add("Vladimir Kukharuk");
        userList.add("Usman Shani");
        userList.add("Usmans mom");
        userList.add("Bruce, Ayoubs boyfriend");

        // for loop create all fields
        for (int i = 0; i < 4; i++) {
            // create
            JLabel iconLabel = new JLabel(icon);
            JLabel userName = new JLabel(userList.get(i));
            JLabel moneyIcon = new JLabel("$");
            JTextField amount = new JTextField("0.00");

            // style
            iconLabel.setForeground(Color.WHITE);
            userName.setForeground(Color.WHITE);
            moneyIcon.setForeground(Color.WHITE);
            userName.setMinimumSize(new Dimension(300,30));

            // add to list
            iconLabels.add(iconLabel);
            userNames.add(userName);
            moneyIcons.add(moneyIcon);
            amounts.add(amount);
        }

        // attempt 2
        /*JPanel fieldContainer1 = new JPanel();
        fieldContainer1.setSize(720,30);
        fieldContainer1.setLayout(new FlowLayout());
        fieldContainer1.add(new JLabel(icon));
        fieldContainer1.add(new JLabel("Vladimir Kukharuk"));
        fieldContainer1.add(new JLabel("$"));
        fieldContainer1.add(new JTextField("20.00"));

        JPanel fieldContainer2 = new JPanel();
        fieldContainer2.setLayout(new FlowLayout());
        fieldContainer2.add(new JLabel(icon));
        fieldContainer2.add(new JLabel("Usman Shani"));
        fieldContainer2.add(new JLabel("$"));
        fieldContainer2.add(new JTextField("69.00"));

        JPanel fieldContainer3 = new JPanel();
        fieldContainer3.setLayout(new FlowLayout());
        fieldContainer3.add(new JLabel(icon));
        fieldContainer3.add(new JLabel("Temp person"));
        fieldContainer3.add(new JLabel("$"));
        fieldContainer3.add(new JTextField("00.00"));*/

        // buttons
        doneButton = new JButton();
        doneButton.setText("DONE");

        // more layouts, for explanation see: https://docs.oracle.com/javase/tutorial/uiswing/layout/groupExample.html
        // ATTEMPT 1
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(iconLabels.get(0))
                                        .addComponent(iconLabels.get(1))
                                        .addComponent(iconLabels.get(2))
                                        .addComponent(iconLabels.get(3))
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(userNames.get(0))
                                        .addComponent(userNames.get(1))
                                        .addComponent(userNames.get(2))
                                        .addComponent(userNames.get(3))
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(moneyIcons.get(0))
                                        .addComponent(moneyIcons.get(1))
                                        .addComponent(moneyIcons.get(2))
                                        .addComponent(moneyIcons.get(3))
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(amounts.get(0))
                                        .addComponent(amounts.get(1))
                                        .addComponent(amounts.get(2))
                                        .addComponent(amounts.get(3))
                                )
                        )
                        .addComponent(doneButton, GroupLayout.Alignment.CENTER)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(iconLabels.get(0))
                                .addComponent(userNames.get(0))
                                .addComponent(moneyIcons.get(0))
                                .addComponent(amounts.get(0))
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(iconLabels.get(1))
                                .addComponent(userNames.get(1))
                                .addComponent(moneyIcons.get(1))
                                .addComponent(amounts.get(1))
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(iconLabels.get(2))
                                .addComponent(userNames.get(2))
                                .addComponent(moneyIcons.get(2))
                                .addComponent(amounts.get(2))
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(iconLabels.get(3))
                                .addComponent(userNames.get(3))
                                .addComponent(moneyIcons.get(3))
                                .addComponent(amounts.get(3))
                        )
                        .addComponent(doneButton)
        );

        // ATTEMPT 2
        /*layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(fieldContainer1)
                        .addComponent(fieldContainer2)
                        .addComponent(fieldContainer3)
                        .addComponent(doneButton, GroupLayout.Alignment.CENTER)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(fieldContainer1)
                        .addComponent(fieldContainer2)
                        .addComponent(fieldContainer3)
                        .addComponent(doneButton)
        );*/
    }
}
