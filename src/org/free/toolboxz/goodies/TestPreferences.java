package org.free.toolboxz.goodies;

import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.JFrame;

public class TestPreferences extends JFrame {

    Preferences prefs = Preferences.userNodeForPackage(TestPreferences.class);

    public TestPreferences() {
        super("Test");
        setEtatInitial();
        addListeners();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setEtatInitial() {
        int x = prefs.getInt("x", 200);
        int y = prefs.getInt("y", 200);
        setLocation(x, y);
        int w = prefs.getInt("w", 200);
        int h = prefs.getInt("h", 200);
        setSize(w, h);
        int state = prefs.getInt("state", Frame.NORMAL);
        setExtendedState(state);
    }

    public void addListeners() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                prefs.putInt("state", getExtendedState());
                setExtendedState(Frame.NORMAL);
                Rectangle bounds = getBounds();
                prefs.putInt("x", (int) bounds.getX());
                prefs.putInt("y", (int) bounds.getY());
                prefs.putInt("w", (int) bounds.getWidth());
                prefs.putInt("h", (int) bounds.getHeight());
            }
        });
    }

    public static void main(String[] args) {
        new TestPreferences();
    }
}
