package org.free.toolboxz.date;

import org.free.toolboxz.exceptions.SimpleException;


/**
 *  Classe d'exception pour les traitements de dates. <p>
 * <br>
 * History:     1.0 creation du 20/02/2009<br>
 * <br>
 * @author  Vincent Cephirins
 * @version 1.0 du 20/02/2009
 * @class
 */
public class DateException extends SimpleException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur
     * 
     * @param message L'identifiant du message à afficher.
     */
    public DateException(String message) {
        super(message);
    }

    /**
     * Constructeur
     * 
     * @param message L'identifiant du message à afficher.
     * @param arguments les arguments du message d'erreur.
     */
    public DateException(String message, Object... arguments) {
        super(message, arguments);
    }

    /**
     * Constructeur
     * 
     * @param e La cause de l'exception
     * @param message L'identifiant du message à afficher.
     */
    public DateException(Exception e, String message) {
        super(e, message);
    }

    /**
     * Constructeur
     * 
     * @param e La cause de l'exception
     * @param message L'identifiant du message à afficher.
     * @param arguments les arguments du message d'erreur.
     */
    public DateException(Exception e, String message, Object... arguments) {
        super(e, message, arguments);
    }
}
