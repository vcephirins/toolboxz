package org.free.toolboxz.ihm;

import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.free.toolboxz.exceptions.NotFoundException;

public final class UIHelper {
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(true);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    public static JButton createButton(String text, String icon) throws NotFoundException {
        return createButton(text, icon, false);
    }

    public static JButton createButton(String text, String icon, boolean flat) throws NotFoundException {
        ImageIcon iconNormal = readImageIcon(icon);
        ImageIcon iconHighlight;
        ImageIcon iconPressed;

        String iconExt = icon.substring(icon.length() - 4);
        String iconName = icon.substring(0, icon.length() - 4);
        try {
            iconHighlight = readImageIcon(iconName + "_highlight" + iconExt);
        }
        catch (NotFoundException npe) {
            iconHighlight = iconNormal;
        }
        try {
            iconPressed = readImageIcon(iconName + "_pressed" + iconExt);
        }
        catch (NotFoundException npe) {
            iconPressed = iconNormal;
        }

        JButton button = new JButton(text, iconNormal);
        button.setFocusPainted(!flat);
        button.setBorderPainted(!flat);
        button.setContentAreaFilled(!flat);
        if (iconHighlight != null) {
            button.setRolloverEnabled(true);
            button.setRolloverIcon(iconHighlight);
        }
        if (iconPressed != null) button.setPressedIcon(iconPressed);
        return button;
    }

    public static JLabel createLabel(String text, String icon) throws NotFoundException {
        ImageIcon iconNormal = readImageIcon(icon);
        JLabel label = new JLabel(text, iconNormal, JLabel.LEFT);
        return label;
    }

    public static ImageIcon readImageIcon(String filename) throws NotFoundException {
        URL url = UIHelper.class.getResource(filename);
        if (url == null) url = UIHelper.class.getResource("/org/free/toolboxz/images/" + filename);
        if (url == null) throw new NotFoundException("/org/free/toolboxz/images/" + filename);
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
    }
}
