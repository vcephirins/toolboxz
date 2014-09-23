package org.free.toolboxz.ihm;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int limit;

    public JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }

    public void replace(int offset, int length, String str, AttributeSet s) throws BadLocationException {
        if (str == null) return;

        if (offset + str.length() <= limit) {
            super.replace(offset, length, str, s);
        }
    }
}
