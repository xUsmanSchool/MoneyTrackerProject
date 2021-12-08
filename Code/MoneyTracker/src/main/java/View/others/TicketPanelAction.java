package View.others;

import View.panels.AddTicketsPanel.AddTicketsPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicketPanelAction extends AbstractAction {
    private final AddTicketsPanel panel;
    private Component lastComponentInFocus;
    private Component currentComponentInFocus;

    public TicketPanelAction(AddTicketsPanel panel, JPanel goToPanel){
        super();

        this.currentComponentInFocus = null;
        this.lastComponentInFocus = null;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component[] components = panel.getComponents();
        boolean doubleFocus = false;
        for (Component c:components) {
            if (c.hasFocus()) {
                // for description textfield
                if (c instanceof JTextField) {
                    JTextField t = (JTextField) c;
                    t.setText("");
                    currentComponentInFocus = c;
                }

                // for payment amount textfield
                if (c instanceof JFormattedTextField) {
                    JFormattedTextField t = (JFormattedTextField) c;
                    t.setText("0.00");
                    currentComponentInFocus = c;
                }
            }
        }
        if (currentComponentInFocus == lastComponentInFocus) {
            lastComponentInFocus = null;
            Router router = Router.getInstance();
            router.goBack();
        }
        lastComponentInFocus = currentComponentInFocus;
    }
}
