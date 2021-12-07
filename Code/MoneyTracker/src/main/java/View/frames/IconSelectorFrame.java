package View.frames;

import HelperClass.Paths;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IconSelectorFrame extends JFrame {
    ArrayList<JLabel> imageList;

    public IconSelectorFrame(Rectangle openPosition) {
        this.setSize(720, 480);
        FlowLayout layout = new FlowLayout();
        this.setLayout(layout);

        imageList = new ArrayList<>();

        ArrayList<String> iconNames = new ArrayList<>();
        for (int i = 1; i < 9; i++) iconNames.add("user_icon" + i + ".png");

        for (String iconName:iconNames) {
            ImageIcon icon = new ImageIcon(Paths.iconPath + iconName);
            JLabel labelForImage = new JLabel("");
            labelForImage.setIcon(icon);
            imageList.add(labelForImage);

            this.add(labelForImage);
        }

        this.setLocationRelativeTo(null);
        if (openPosition != null) this.setBounds(openPosition);
        this.pack();
        this.setVisible(true);
    }

    public ArrayList<JLabel> getImageList() {
        return imageList;
    }
}
