package Observers;

import javax.swing.*;

public class ImageFrameIconObservableEntry {
    private final Icon icon;
    private final boolean added;

    public ImageFrameIconObservableEntry(Icon person, boolean added){
        this.icon = person;
        this.added = added;
    }

    public Icon getIcon() {
        return icon;
    }

    public boolean isAdded() {
        return added;
    }
}
