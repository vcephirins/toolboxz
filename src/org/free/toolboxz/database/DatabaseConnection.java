/**
 * 
 */
package org.free.toolboxz.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe d'impl�mentation des m�thodes simplifi�es de gestion d'acc�s aux donn�es.
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

public class DatabaseConnection {
    private String url = null;
    private String user = null;
    private Connection con = null;
    private Statement sql = null;

    /**
     * Constructeur.
     * <p>
     * 
     * @throws DatabaseException
     */
    protected DatabaseConnection(Connection con, String url, String user) throws DatabaseException {
        try {
            this.url = url;
            this.user = user;
            this.con = con;
            this.sql = this.con.createStatement();
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "error.database.createStatement");
        }
    }

    /**
     * @return Retourne la connection r�elle � la base.
     */
    public final Connection getConnection() {
        return con;
    }

    /**
     * @return Returns the url.
     */
    public final String getUrl() {
        return url;
    }

    /**
     * @return Returns the user.
     */
    public final String getUser() {
        return user;
    }

    /**
     * Ferme la connexion � la base.<p>
     * @throws DatabaseException en cas d'erreur
     */
    public void close() throws DatabaseException {
        try {
            if (sql != null) sql.close();
            if (con != null) con.close();
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "error.database.disconnect");
        }
    }

    /**
     * Valide les modifications en base.<p>
     * @throws DatabaseException en cas d'erreur
     */
    public void commit() throws DatabaseException {
        try {
            con.commit();
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "error.database.commit");
        }
    }

    /**
     * Annule les modifications en base.<p>
     * @throws DatabaseException en cas d'erreur
     */
    public void rollback() throws DatabaseException {
        try {
            con.rollback();
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "error.database.rollback");
        }
    }

    /**
     * Compile une requ�te et la pr�pare � son ex�cution.<p>
     * @param sql La requ�te lit�rale.
     * @return la requ�te compil�e.
     * @throws DatabaseException en cas d'erreur
     */
    public PreparedStatement prepareStatement(String sql) throws DatabaseException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            return ps;
        }
        catch (SQLException e) {
            rollback();
            DatabaseException de = new DatabaseException(e, "error.database.prepareStatement");
            throw new DatabaseException(de, "error.free", sql);
        }
    }

    /**
     * Ex�cution d'une requ�te dynamique de type Select.<p>
     * @param select La requ�te lit�rale.
     * @return Le r�sultat de la requ�te.
     * @throws DatabaseException en cas d'erreur
     */
    public ResultSet executeQuery(String select) throws DatabaseException {
        return executeQuery(100, select);
    }

    /**
     * Ex�cution d'une requ�te dynamique de type Select.<p>
     * @param fetchSize Nombre de lignes du fetch.
     * @param select La requ�te lit�rale.
     * @return Le r�sultat de la requ�te limit� par fetchSize.
     * @throws DatabaseException en cas d'erreur
     */
    public ResultSet executeQuery(int fetchSize, String select) throws DatabaseException {
        try {
            sql.setFetchSize(fetchSize);
            return sql.executeQuery(select);
        }
        catch (SQLException e) {
            rollback();
            DatabaseException de = new DatabaseException(e, "error.database.executeQuery");
            throw new DatabaseException(de, "error.free", select);
        }
    }

    /**
     * Ex�cution d'une requ�te pr�-compil�e de type Select.<p>
     * @param preparedStatement La requ�te pr�-compil�e.
     * @return Le r�sultat de la requ�te.
     * @throws DatabaseException en cas d'erreur
     */
    public ResultSet executeQuery(PreparedStatement preparedStatement) throws DatabaseException {
        return executeQuery(100, preparedStatement);
    }
    
    /**
     * Ex�cution d'une requ�te pr�-compil�e de type Select.<p>
     * @param fetchSize Nombre de lignes du fetch.
     * @param preparedStatement La requ�te pr�-compil�e.
     * @return Le r�sultat de la requ�te limit� par fetchSize.
     * @throws DatabaseException en cas d'erreur
     */
    public ResultSet executeQuery(int fetchSize, PreparedStatement preparedStatement) throws DatabaseException {
        try {
            preparedStatement.setFetchSize(fetchSize);
            return preparedStatement.executeQuery();
        }
        catch (SQLException e) {
            rollback();
            DatabaseException de = new DatabaseException(e, "error.database.executeQuery");
            throw new DatabaseException(de, "error.free", preparedStatement.toString());
        }
    }

    /**
     * Ex�cution d'une requ�te dynamique de type Mise � jour.<p>
     * @param update La requ�te lit�rale.
     * @return le nombre de lignes mises � jour.
     * @throws DatabaseException en cas d'erreur
     */
    public int executeUpdate(String update) throws DatabaseException {
        try {
            return sql.executeUpdate(update);
        }
        catch (SQLException e) {
            rollback();
            DatabaseException de = new DatabaseException(e, "error.database.executeUpdate");
            throw new DatabaseException(de, "error.free", update);
        }
    }

    /**
     * Ex�cution d'une requ�te pr�-compil�e de type Mise � jour.<p>
     * @param preparedStatement La requ�te pr�-compil�e.
     * @return le nombre de lignes mises � jour.
     * @throws DatabaseException en cas d'erreur
     */
    public int executeUpdate(PreparedStatement preparedStatement) throws DatabaseException {
        try {
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            rollback();
            DatabaseException de = new DatabaseException(e, "error.database.executeUpdate");
            throw new DatabaseException(de, "error.free", preparedStatement.toString());
        }
    }

    /**
     * Trace sur la sortie standard le r�sultat de la requ�te.<p>
     * @param result Le r�sultat de la requ�te � afficher
     */
    public final void traceResult(ResultSet result) {
        try {
            ResultSetMetaData md = result.getMetaData();
            int nbCol = md.getColumnCount();
            for (int colPos = 1; colPos <= nbCol; colPos++) {
                System.out.print(md.getColumnName(colPos) + "(" + md.getColumnTypeName(colPos) + ":");
                System.out.println(md.getPrecision(colPos) + "." + md.getScale(colPos) + ")");
            }
            while (result.next()) {
                for (int colPos = 1; colPos <= nbCol; colPos++) {
                    String valeur = result.getString(colPos);
                    System.out.print(result.wasNull()? "<NULL>" : valeur);
                    if (colPos < nbCol) System.out.print(", ");
                }
                System.out.println("");
            }
        }
        catch (NullPointerException npe) {
            // N/A
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
