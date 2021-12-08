package ViewController.AddUserWindow;

import Model.Person;
import Observers.ImageFrameIconObservableEntry;
import Observers.PersonDBObservableEntry;
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
    }

    private MouseListener test(JLabel l) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setChanged();
                //notifyObservers(l.getIcon().toString());
                notifyObservers(new ImageFrameIconObservableEntry((Icon) l.getIcon(), true));
                UserCreationPanelController.ImageSelectorFrameOpen = false;
                iconSelectorFrame.dispose();
            }
        };
    }
}
