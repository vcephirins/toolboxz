package org.free.toolboxz.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

/**
 * This pane searches for a word and displays it highlighted Source made to reply on a newsgroup - 2006
 * 
 * @author glitch
 */
public class JSearchPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private int pos;

    public JSearchPanel(URL url, final String searchedWord) {
        final JEditorPane viewer = new JEditorPane();
        pos = 0;

        // Mode editable
        viewer.setEditable(true);

        try {
            // Chargement du document
            viewer.setPage(url);
            final String text = viewer.getText();
            JButton jb = new JButton("search " + searchedWord);
            jb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    viewer.getHighlighter().removeAllHighlights();
                    int start = 0;
                    start = text.indexOf(searchedWord, pos);
                    if (start < 0 && pos != 0) {
                        // Wrap
                        start = text.indexOf(searchedWord, 0);
                    }

                    if (start >= 0) {
                        int stop = start + searchedWord.length();
                        try {
                            viewer.getHighlighter().addHighlight(start, stop, DefaultHighlighter.DefaultPainter);
                            viewer.setCaretPosition(start);
                            start += 1;
                        }
                        catch (BadLocationException e1) {
                            viewer.setCaretPosition(text.length());
                            start = 0;
                        }
                        pos = start;
                    }
                }
            });

            this.setLayout(new BorderLayout());
            this.add(new JScrollPane(viewer));
            this.add(jb, BorderLayout.SOUTH);

        }
        catch (IOException ex) {
            viewer.setText("Acces impossible a " + url);
            System.err.println("Acces impossible a " + url);
        }

    }
}
