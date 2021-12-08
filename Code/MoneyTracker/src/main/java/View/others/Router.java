package View.others;

import javax.swing.*;
import java.util.Stack;

public class Router {
    private static Router instance;
    private Stack<JComponent> panelStack;
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

    public void gotToPanel(JComponent p) {
        if (!panelStack.isEmpty()) {
            JComponent lastPanel = panelStack.peek();
            this.frame.remove(lastPanel);
        }

        panelStack.push(p);
        this.frame.add(p);
        p.setVisible(true);
        // todo - fix request focus bug otherwise esc doesnt work on startup

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
