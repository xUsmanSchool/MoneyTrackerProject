package View.others;

import View.panels.AddTicketsPanel.AddTicketsPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class TicketPanelAction extends AbstractAction {
    private final AddTicketsPanel panel;

    public TicketPanelAction(AddTicketsPanel panel){
        super();

        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component[] components = panel.getComponents();
        boolean canGoBack = false;
        boolean underTookAction = false;

        for (Component c:components) {
            if (c.hasFocus()) {
                // for description textfield
                if (c instanceof JTextField) {
                    JTextField t = (JTextField) c;

                    if (Objects.equals("", t.getText())) canGoBack = true;
                    else {
                        t.setText("");
                        underTookAction = true;
                    }
                }

                // for payment amount textfield
                if (c instanceof JFormattedTextField) {
                    JFormattedTextField t = (JFormattedTextField) c;

                    if (Objects.equals("0.00", t.getText())) canGoBack = true;
                    else {
                        t.setText("0.00");
                        underTookAction = true;
                    }
                }

                if (!underTookAction) canGoBack = true;
            }
        }
        if (canGoBack) {
            canGoBack = false;
            Router router = Router.getInstance();
            router.goBack();
        }
    }
}
