package View.others;

import View.panels.AddTicketsPanel.AddTicketsPanel;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class TicketPanelAction extends AbstractAction {
    private final AddTicketsPanel panel;
    private String prevText;

    public TicketPanelAction(AddTicketsPanel panel, JPanel goToPanel){
        super();

        this.prevText = "";
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component[] components = panel.getComponents();
        boolean canGoBack = false;
        for (Component c:components) {
            if (c.hasFocus()) {
                // for description textfield
                if (c instanceof JTextField) {
                    JTextField t = (JTextField) c;

                    if (Objects.equals(prevText, t.getText())) canGoBack = true;

                    this.prevText = "";
                    t.setText(prevText);
                }

                // for payment amount textfield
                if (c instanceof JFormattedTextField) {
                    JFormattedTextField t = (JFormattedTextField) c;

                    if (Objects.equals(prevText, t.getText())) canGoBack = true;

                    this.prevText = "0.00";
                    t.setText(prevText);
                }
            }
        }
        if (canGoBack) {
            canGoBack = false;
            Router router = Router.getInstance();
            router.goBack();
        }
    }
}
