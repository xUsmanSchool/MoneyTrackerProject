package View.others;

import View.panels.GlobalBill.GlobalBillPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class GlobalBillPanelAction extends AbstractAction {
    private final GlobalBillPanel panel;

    public GlobalBillPanelAction(GlobalBillPanel panel){
        super();

        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Router router = Router.getInstance();
        router.goBack();
    }
}
