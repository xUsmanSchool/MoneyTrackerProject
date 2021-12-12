package ViewController.AddUserWindow;

import Observers.ImageFrameIconObservableEntry;
import View.frames.IconSelectorFrame;
import javax.swing.*;
import java.awt.event.*;
import java.util.Observable;

public class IconSelectorFrameController extends Observable {
    private final IconSelectorFrame iconSelectorFrame;

    public IconSelectorFrameController(IconSelectorFrame iconSelectorFrame, UserCreationPanelController userCreationPanelController) {
        this.iconSelectorFrame = iconSelectorFrame;

        this.addObserver(userCreationPanelController);
    }

    public void activateActionListeners() {
        for (JLabel label:iconSelectorFrame.getImageList()) label.addMouseListener(test(label));
        iconSelectorFrame.addWindowListener(windowClosingListener());
    }

    private MouseListener test(JLabel l) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setChanged();
                notifyObservers(new ImageFrameIconObservableEntry((Icon) l.getIcon(), true));
                UserCreationPanelController.ImageSelectorFrameOpen = false;
                iconSelectorFrame.dispose();
            }
        };
    }

    private WindowAdapter windowClosingListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setChanged();
                notifyObservers(new ImageFrameIconObservableEntry(null, false));
                UserCreationPanelController.ImageSelectorFrameOpen = false;
                e.getWindow().dispose();
            }
        };
    }
}
