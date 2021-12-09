package View.others;

import View.panels.PaymentSplits.PaymentSplitPanel;

import javax.swing.*;
import java.util.Stack;

public class Router {
    private static Router instance;
    private final Stack<JComponent> panelStack;
    private JFrame frame;

    public static Router getInstance() {
        if (instance == null) instance = new Router();
        return instance;
    }

    private Router() {
        this.panelStack = new Stack<>();
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    private void removePanelFromStack() {
        if (!panelStack.isEmpty()) {
            JComponent lastPanel = panelStack.peek();
            this.frame.remove(lastPanel);
        }
    }

    public void gotToPanel(JComponent p) {
        removePanelFromStack();

        panelStack.push(p);
        this.frame.add(p);
        p.setVisible(true);
        this.frame.setVisible(true);

        repaint();
    }

    public void gotToPanel(JComponent p, JTextField field) {
        removePanelFromStack();

        panelStack.push(p);
        this.frame.add(p);

        // set both to visible otherwise focus will not work
        p.setVisible(true);
        this.frame.setVisible(true);

        // set focus
        field.grabFocus();
        field.requestFocusInWindow();

        repaint();
    }

    public void goBack() {
        if (!panelStack.isEmpty()) {
            JComponent currentPanel = panelStack.pop();

            if (!panelStack.isEmpty()) {
                this.frame.remove(currentPanel);

                JComponent lastPanel = panelStack.peek();
                this.frame.add(lastPanel);

                repaint();
            } else {
                // only 1 panel remaining
                panelStack.push(currentPanel);
            }
        }
    }

    private void repaint() {
        this.frame.validate();
        this.frame.repaint();
    }
}
