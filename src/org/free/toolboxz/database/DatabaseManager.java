package org.free.toolboxz.database;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Classe de gestion de l'acces a la base.
 * <p>
 * <br>
 * Last modification Date: 2009 <br>
 * History: 1.0 creation <br>
 * 
 * @author Vincent Cephirins - Akka is
 * @version 1.0
 * @copyright (c) Cnes 2009. All rights reserved.
 * @class
 */
public class DatabaseManager {
    // Initialise un logger (voir log4j.xml).
    private static final Logger LOGGER = Logger.getLogger("database");

    // Valeurs par d�faut
    private String driver = null;
    private String url = null;
    private String user = null;
    private String password = null;

    /**
     * Constructeur.
     * <p>
     * 
     * @param propertiesFile le fichier des proprietes de connexion a la base
     * @throws DatabaseException en cas d'erreur
     */
    public DatabaseManager(String propertiesFile) throws DatabaseException {
        Properties props = null;
        try {
            InputStream is = null;
            try {
                props = new Properties();
                is = new FileInputStream(propertiesFile);
                props.load(is);
            }
            finally {
                if (is != null) is.close();
            }

            // Database properties
            driver = props.getProperty("jdbc.driver");
            url = props.getProperty("jdbc.url");
            user = props.getProperty("jdbc.user");
            password = props.getProperty("jdbc.password");
        }
        catch (Exception e) {
            DatabaseException de = new DatabaseException(e, "error.database.properties");
            throw de;
        }
    }

    /**
     * Constructeur.
     * <p>
     * 
     * @param driver le pilote de connexion a la base
     * @param url la chaine de connexion a la base
     * @param user l'identifiant de connexion a la base
     * @param password le mot de passe de l'identifiant
     * @throws DatabaseException
     */
    public DatabaseManager(String driver, String url, String user, String password) {
        // Database properties
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Connexion a la base.
     * <p>
     * 
     * @return La connexion etablie avec la base pour le compte par d�faut
     * @throws DatabaseException en cas d'erreur.
     */
    public DatabaseConnection connect() throws DatabaseException {
        return connect(user, password);
    }

    /**
     * Connexion a la base.
     * <p>
     * 
     * @param user l'identifiant du compte
     * @param password le mot de passe du compte
     * @return Connection La connexion etablie avec la base avec le compte sp�cifi�
     * @throws DatabaseException en cas d'erreur.
     */
    public DatabaseConnection connect(String user, String password) throws DatabaseException {
        try {
            try {
                Class.forName(driver);
                Connection connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false);
                DatabaseConnection con = new DatabaseConnection(connection, url, user);
                LOGGER.info("Connected database (" + user + ", " + url + ")");
                return con;
            }
            catch (ClassNotFoundException ce) {
                throw new DatabaseException(ce, "error.database.driver", driver);
            }
            catch (Exception e) {
                throw new DatabaseException(e, "error.database.connect");
            }
        }
        catch (DatabaseException de) {
            // Traces dans la log de la database
            throw new DatabaseException(de, "error.database.info", driver, user, url);
        }
    }

    /**
     * Deconnexion de la base.
     * <p>
     * @param con la connexion � fermer
     */
    public static void disconnect(DatabaseConnection con) {
        try {
            if (con != null) {
                con.rollback();
                con.close();
                LOGGER.info("Disconnected database (" + con.getUser() + ", " + con.getUrl() + ")");
            }
        }
        catch (DatabaseException e) {
            new DatabaseException(e, "error.database.disconnect", con.getUser(), con.getUrl());
        }
        catch (Exception e) {
            new DatabaseException(e, "error.database.disconnect", con.getUser(), con.getUrl());
        }
    }
}
