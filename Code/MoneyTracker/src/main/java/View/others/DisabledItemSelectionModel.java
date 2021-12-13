package View.others;

import javax.swing.*;

//https://stackoverflow.com/questions/17863780/make-jlist-values-unselectable
public class DisabledItemSelectionModel extends DefaultListSelectionModel {

    @Override
    public void setSelectionInterval(int index0, int index1) {
        super.setSelectionInterval(-1, -1);
    }
}
