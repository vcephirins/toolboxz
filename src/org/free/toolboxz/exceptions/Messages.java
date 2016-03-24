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
public abstract class Messages {

    /**
     * Affecte la locale par défaut à celle de l'environnement
     */
    private static Locale localeDefault = Locale.getDefault();
    
    /**
     * Le gestionnaire des messages utilisateur
     */
    private static ResourceBundle resource = null;
    private static ResourceBundle resourceDefault = null; 

    /**
     * Le gestionnaire des messages interne par défaut
     */
    private static ResourceBundle resourceInterne = ResourceBundle.getBundle("org.free.toolboxz.exceptions.messagesException");
    private static ResourceBundle resourceInterneDefault = ResourceBundle.getBundle("org.free.toolboxz.exceptions.messagesException", Locale.ENGLISH);

    /**
     * Constructeur privé
     */
    private Messages() {
    }

    /**
     * Charge en mémoire les messages.
     * <p>
     * La langue sélectionnée est la locale par défaut.
     * 
     * @param file
     */
    public static void load(String file) {
        load(file, Locale.getDefault());
    }

    /**
     * Charge en mémoire les messages.
     * <p>
     * La langue sélectionnée est la locale fournie.
     * 
     * @param basenameBundle 
     * @param locale
     * @throws NullPointerException si le nom de base ou la locale est null
     * @throws MissingResourceException si la resource basée sur nom du bundle est non trouvé
     */
    public static void load(String basenameBundle, Locale locale) {
        try {
            resource = ResourceBundle.getBundle(basenameBundle, locale);
        }
        catch (MissingResourceException mre) {
            resourceDefault = ResourceBundle.getBundle(basenameBundle, localeDefault);
        }
    }

    public static void setLocale(Locale locale) {
        localeDefault = locale;
        
        // Recharge le fichier dans la locale demandée
        if (resource != null) load(resource.getBaseBundleName(), locale);
    }
    
    /**
     * Récupère le message dans le fichier de configuration
     * 
     * @param ident le code du message
     * @param args les arguments du message
     * @return le message format�
     */
    public static String getMessage(String ident, Object... args) {
        String message = "";

        try {
            if (resource != null) message = Messages.resource.getString(ident);
            else message = Messages.resourceDefault.getString(ident);
        }
        catch (MissingResourceException mre) {
            // recherche dans les messages par défaut
            try {
                message = Messages.resourceDefault.getString(ident);
            }
            catch (MissingResourceException e) {
                // Si on ne trouve pas le message
                message = "Message ident ''" + ident + "'' not found in message files.";
            }
        }
        return MessageFormat.format(message, args);
    }
}
