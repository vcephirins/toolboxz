package org.free.toolboxz.database;

import java.sql.SQLException;

import org.free.toolboxz.exceptions.LoggerException;


import com.spinn3r.log5j.Logger;

/**
 * Exception standard.
 * <p>
 * Last modification Date: 2009 <br>
 * History: 1.0 creation <br>
 * 
 * @author Vincent Cephirins
 * @version 1.0
 * @copyright (c) Cnes 2009. All rights reserved.
 * @class
 */
public class DatabaseException extends LoggerException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Initialise le logger database(voir log4j.xml).
    private static final Logger LOGGER = Logger.getLogger();
    
    /**
     * Error code for user application.
     */
    public static final String ERROR_USER = "60000";
    private String sqlState = ERROR_USER;

    /**
     * Constructeur
     * 
     * @param message l'identifiant de l'error � afficher
     * @param arguments les arguments du message d'erreur.
     */
    public DatabaseException(String message, Object... arguments) {
        super(message, arguments);
        logger = LOGGER;
    }

    /**
     * Constructeur
     * 
     * @param e La cause de l'exception
     */
    public DatabaseException(Throwable e) {
        super(e);
        logger = LOGGER;
        if (e instanceof SQLException) {
            sqlState = ((SQLException) e).getSQLState();
        }
        else if (e instanceof DatabaseException) {
            sqlState = ((DatabaseException) e).getSqlState();
        }
    }

    /**
     * Constructeur
     * 
     * @param e La cause de l'exception
     * @param message l'identifiant de l'error � afficher
     * @param arguments les arguments du message d'erreur.
     */
    public DatabaseException(Throwable e, String message, Object... arguments) {
        super(e, message, arguments);
        logger = LOGGER;
        if (e instanceof SQLException) {
            sqlState = ((SQLException) e).getSQLState();
        }
        else if (e instanceof DatabaseException) {
            sqlState = ((DatabaseException) e).getSqlState();
        }
    }

    public String getSqlState() {
        return sqlState;
    }
}
