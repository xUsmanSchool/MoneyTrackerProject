package View;

import View.panels.*;
import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
    public ViewFrame() {}

    public void initialize() {
        this.setSize(720, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridLayout experimentLayout = new GridLayout(0,2);
        this.setLayout(experimentLayout);

        UserCreationPanel userCreationPanel = new UserCreationPanel();
        UserListPanelAdvanced userListPanelAdvanced = new UserListPanelAdvanced();

        this.add(userCreationPanel);
        this.add(userListPanelAdvanced);
        this.setVisible(true);
    }
}
