package org.free.toolboxz.applications;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.log4j.Level;
import org.free.toolboxz.exceptions.Messages;
import org.free.toolboxz.exceptions.SimpleException;
import org.free.toolboxz.goodies.Divers;
import org.free.toolboxz.ihm.CryptWin;

import com.spinn3r.log5j.Logger;

/**
 * @author CEPHIRINS
 */
public class AppWin {

    // Initialise un logger (voir log4j.xml).
    static final Logger LOGGER = Logger.getLogger();

    /**
     * @param args
     * @throws URISyntaxException
     * @throws AppException
     * @throws IOException
     */
    public static void main(String[] args) {
        // Chargement des messages d'exception applicatifs
        Messages.load("org.free.toolboxz.exceptions.messagesException");

        LOGGER.setLevel(Level.ERROR);

        // Prévention des interruption de programme
        class ShutdownHook extends Thread {
            public ShutdownHook() {
            }

            // traitement qui sera effectue suite à une interruption du programme
            public void run() {
                if (LOGGER.isDebugEnabled()) {
                    System.out.println("Memory used : " + Divers.getHeapMemoryUsage());
                }
            }
        }
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        // Appel de l'interface graphique
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    CryptWin win = new CryptWin(LOGGER);
                }
                catch (SimpleException ae) {
                    if (LOGGER.isDebugEnabled()) {
                        ae.printStackTrace();
                    }
                    else {
                        ae.printMessages();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
