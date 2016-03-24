/**
 * 
 */
package org.free.toolboxz.ihm;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 * JWaitPanel.java.
 * <p>
 * 
 * @author Vincent Cephirins
 * @version 1.0, 29 juin 2013 <li>Creation</>
 */

public class JWaitPanel extends JPanel implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    SwingWorker<?, ?> sw;

    JButton jbnStop;
    private JProgressBar progressBar;

    /**
     * Constructeur.
     * <p>
     */
    public JWaitPanel(SwingWorker<?, ?> sw) {
        super();

        this.sw = sw;

        setLayout(new FlowLayout());

        progressBar = new JProgressBar();
        add(progressBar);

        jbnStop = new JButton("Stop");
        jbnStop.addActionListener(this);
        add(jbnStop);

        setVisible(true);
    }

    public void setProgressBar(Integer value) {
        progressBar.setValue(value);
    }

    public void actionPerformed(ActionEvent ae) {

        try {
            if (ae.getSource() == jbnStop) {
                sw.cancel(true);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
