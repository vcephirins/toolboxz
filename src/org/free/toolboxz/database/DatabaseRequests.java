package org.free.toolboxz.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Classe de gestion des requetes pr�d�finies.
 * <p>
 * <br>
 * Last modification Date: 2008 <br>
 * History: 1.0 creation <br>
 * 
 * @author Vincent Cephirins - Akka iss
 * @version 1.0
 * @copyright (c) Cnes 2008. All rights reserved.
 * @class
 */
public class DatabaseRequests extends DatabaseConnection {

    private PreparedStatement[] ps;

    /**
     * Constructeur.
     * <p>
     * 
     * @param dbc la connection � la base.
     * @throws DatabaseException
     * @throws PaisException Si la base n'est pas connect�e.
     */
    protected DatabaseRequests(Class<? extends Enum> enumType, DatabaseConnection dbc) throws DatabaseException {
        super(dbc.getConnection(), dbc.getUrl(), dbc.getUser());
        this.ps = new PreparedStatement[enumType.getEnumConstants().length];
    }

    /**
     * Renvoi une requete pr�-compil�e.
     * <p>
     * Au premier appel, la requete est pr�compil�e puis stock�e pour les appels suivants.
     * 
     * @param request l'identifiant de la requete
     * @return la requete pr�-copmpil�e
     * @throws DatabaseException en cas d'erreur
     */
    protected PreparedStatement getRequest(Class<? extends Enum> enumType, String request) throws DatabaseException {
        int indice;
        try {
            Enum<?> rq = Enum.valueOf(enumType, request);
            indice = rq.ordinal();
            if (ps[indice] == null) {
                // Pr�-compilation de la requete
                ps[indice] = this.prepareStatement(rq.toString());
            }
        }
        catch (IllegalArgumentException iae) {
            DatabaseException de = new DatabaseException(iae, "error.database.prepareStatement");
            throw new DatabaseException(de, "error.free", request);
        }
        return ps[indice];
    }

    /**
     * Renvoi le litt�ral de la requ�te.
     * <p>
     * 
     * @param request l'identifiant de la requete
     * @return la requete
     */
    protected String getString(Class<? extends Enum> enumType, String request) {
        return Enum.valueOf(enumType, request).toString();
    }

    protected final void finalize() {
        this.closeAllRequests();
    }

    /**
     * Lib�re les ressources de la requete.
     * <p>
     * 
     * @param request l'identifiant de la requete
     * @throws DatabaseException en cas d'erreur
     */
    protected void closeRequest(Class<? extends Enum> enumType, String request) throws DatabaseException {
        try {
            Enum<?> rq = Enum.valueOf(enumType, request);
            int indice = rq.ordinal();
            if (ps[indice] != null) {
                // Lib�ration des ressources
                try {
                    ps[indice].close();
                    ps[indice] = null;
                }
                catch (SQLException e) {
                    throw new DatabaseException(e, "error.database.closeStatement");
                }
            }
        }
        catch (IllegalArgumentException iae) {
            throw new DatabaseException(iae);
        }
    }

    /**
     * Lib�re les ressources de toutes les requetes.
     * <p>
     */
    public final void closeAllRequests() {
        for (int indice = 0; indice < ps.length; indice++) {
            if (ps[indice] != null) {
                // Lib�ration des ressources
                try {
                    ps[indice].close();
                    ps[indice] = null;
                }
                catch (SQLException e) {
                    new DatabaseException(e, "error.database.closeStatement");
                }
            }
        }
    }

}
