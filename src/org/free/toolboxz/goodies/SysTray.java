package org.free.toolboxz.goodies;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

public class SysTray {
    private TrayIcon trayIcon;

    /**
     * Creation d'une icone dans le system tray.
     * <p>
     * On vérifie que le "systray" est supporté par la JVM pour ce système d'exploitation.
     * 
     * @param image L'image qui sera utilisé comme icône.
     * @param tooltip Le texte affiché lors du survol de la souris.
     * @param popup Le PopupMenu qui s'affichera lors du clic droit.
     * @throws AWTException
     */
    public SysTray(Image image, String tooltip, PopupMenu popup) throws AWTException {
        this(image, tooltip, popup, null, null);
    }

    /**
     * Creation d'une icone dans le system tray.
     * <p>
     * On vérifie que le "systray" est supporté par la JVM pour ce système d'exploitation.
     * 
     * @param image L'image qui sera utilisé comme icône.
     * @param tooltip Le texte affiché lors du survol de la souris.
     * @param popup Le PopupMenu qui s'affichera lors du clic droit.
     * @param title Titre de l'info bulle
     * @param message Contenu de l'info bulle.
     * @throws AWTException
     */
    public SysTray(Image image, String tooltip, PopupMenu popup, String title, String message) throws AWTException {

        // On vérifie que le "systray" est supporté
        // par la JVM pour ce système d'exploitation
        if (SystemTray.isSupported()) {

            trayIcon = new TrayIcon(image, tooltip, popup);

            // On active le redimensionnement automatique de
            // l'icône, afin qu'elle s'adapte au système
            // (sinon l'icône peut être tronqué ou disproportionné)
            trayIcon.setImageAutoSize(true);

            // On récupère l'instance du SystemTray
            SystemTray systemTray = SystemTray.getSystemTray();

            // Et on ajoute notre TrayIcon dans le system tray
            systemTray.add(trayIcon);

            if (title != null) {
                trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
            }
        }
    }

    /**
     * @return the trayIcon
     */
    public final TrayIcon getTrayIcon() {
        return trayIcon;
    }
}
