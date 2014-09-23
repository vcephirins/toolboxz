package org.free.toolboxz.exceptions;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Gestionnaire des message d'erreurs contenues dans un fichier.
 * <p>
 * 
 * @author Vincent Cephirins
 * @version 1.0, 20/01/2009
 *          <li>Creation</li>
 */
public class Messages {
    /**
     * L'instance du singleton
     */
    private static Messages instance = null;

    /**
     * Le gestionnaire de message par défaut
     */
    private static ResourceBundle resourceDefault = null;
    private static ResourceBundle resource = null;

    /**
     * Constructeur
     */
    protected Messages() {
        try {
            resourceDefault = ResourceBundle.getBundle("org.free.toolboxz.exceptions.messagesException");
        }
        catch (MissingResourceException mre) {
            resourceDefault = ResourceBundle
                .getBundle("org.free.toolboxz.exceptions.messagesException", Locale.ENGLISH);
        }
    }

    public void load(String file) {
        try {
            resource = ResourceBundle.getBundle(file);
        }
        catch (MissingResourceException mre) {
            resourceDefault = ResourceBundle.getBundle(file, Locale.ENGLISH);
        }
    }

    public void load(String file, Locale locale) {
        try {
            resource = ResourceBundle.getBundle(file, locale);
        }
        catch (MissingResourceException mre) {
            resourceDefault = ResourceBundle.getBundle(file, Locale.ENGLISH);
        }
    }

    /**
     * @return l'instance unique du singleton
     */
    public static Messages getInstance() {
        if (instance == null) {
            instance = new Messages();
        }
        return instance;
    }

    /**
     * Récupère le message dans le fichier de configuration
     * 
     * @param ident le code du message
     * @param args les arguments du message
     * @return le message format�
     */
    public String getMessage(String ident, Object... args) {
        String message = "";

        try {
            if (resource != null) message = Messages.resource.getString(ident);
            else message = Messages.resourceDefault.getString(ident);
        }
        catch (MissingResourceException mre) {
            // recherche dans les messages par d�faut
            try {
                message = Messages.resourceDefault.getString(ident);
            }
            catch (MissingResourceException e) {
                // Si on ne trouve pas le message
                message = "Message ident ''" + ident + "'' not found in messages file.";
            }
        }
        return MessageFormat.format(message, args);
    }
}
