/**
 * 
 */
package org.free.toolboxz.ihm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.free.toolboxz.exceptions.SimpleException;
import org.free.toolboxz.security.Md5;

/**
 * CryptSwingWorker.java.
 * <p>
 * 
 * @author Vincent Cephirins
 * @version 1.0, 30 juin 2013 <li>Creation</>
 */

public class CryptSwingWorker extends SwingWorker<String, Integer> {
    private CryptWin win;

    private String result = "Error";

    public CryptSwingWorker(CryptWin content) {

        win = content;

        addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                /* On ajoute un écouteur sur la progression. */
                if ("progress".equals(evt.getPropertyName())) {
                    ((JWaitPanel) win.getDialogWait().getContentPane()).setProgressBar((Integer) evt.getNewValue());
                    return;
                }

                /* On ajoute un écouteur sur une fenetre modale d'attente. */
                if ("state".equals(evt.getPropertyName()) && SwingWorker.StateValue.DONE == evt.getNewValue()) {
                    win.getDialogWait().setVisible(false);
                    win.getDialogWait().dispose();
                }
            }
        });
    }

    @Override
    public String doInBackground() {
        try {
            int count = 1;
            setProgress(0);
            while (!isCancelled() && count < 10000000) {
                result = Md5.encode(win.getJtfInput().getText());
                if ((count++ % 1000) == 0) {
                    setProgress(count / 100000);
                    publish(count);
                    // System.out.println(result);
                }
            }
        }
        catch (UnsupportedEncodingException uee) {
            new SimpleException(uee).printMessages();
        }
        catch (NoSuchAlgorithmException nsae) {
            new SimpleException(nsae).printMessages();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void process(List<Integer> tabs) {
        /* Affichage des publications reçues. */
        // Seul le dernier reçu est affiché
        if (this.getState() != SwingWorker.StateValue.DONE) {
            win.getJtfHash().setText(Integer.toString(tabs.get(tabs.size() - 1)));
        }
    }

    @Override
    protected void done() {
        try {
            /* Le traitement est terminé. */
            setProgress(100);
            /* À la fin du traitement, affichage du résultat. */
            win.getJtfHash().setText(String.valueOf(get()));
        }
        catch (InterruptedException e) {
            win.getJtfHash().setText("Interrupted process !");
        }
        catch (ExecutionException e) {
            win.getJtfHash().setText("Execution error !");
        }
        catch (CancellationException e) {
            win.getJtfHash().setText(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
