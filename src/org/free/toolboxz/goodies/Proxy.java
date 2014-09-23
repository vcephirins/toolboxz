package org.free.toolboxz.goodies;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.free.toolboxz.security.Base64Coder;

/**
 * Gestion de la connexion par proxy.
 * <p>
 * 
 * @author Vincent Cephirins
 * @version 1.0, 20/01/2009
 */

public class Proxy {

    private String host;
    private String port;
    private String user;
    private String password;
    private String epassword;

    private HttpURLConnection ctx;

    /**
     * Cr�ation et configuration d'un proxy.
     * <p>
     * 
     * @param proxyHost le nom du serveur proxy.
     * @param proxyPort Le port du serveur proxy.
     */
    public Proxy(String proxyHost, String proxyPort) {

        host = proxyHost;
        port = proxyPort;
        epassword = "";

        System.getProperties().put("proxySet", "true");
        System.getProperties().put("host", host);
        System.getProperties().put("port", port);
    }

    /**
     * Cr�ation et configuration d'un proxy avec authentification en clair.
     * <p>
     * 
     * @param proxyHost le nom du serveur proxy.
     * @param proxyPort Le port du serveur proxy.
     * @param proxyUser l'identifiant de connexion au proxy.
     * @param proxyPassword la mot de passe de connexion au proxy.
     */
    public Proxy(String proxyHost, String proxyPort, String proxyUser, String proxyPassword) {

        host = proxyHost;
        port = proxyPort;
        user = proxyUser;
        password = proxyPassword;

        System.getProperties().put("proxySet", "true");
        System.getProperties().put("proxyHost", host);
        System.getProperties().put("proxyPort", port);
        String pw = user + ':' + password;
        epassword = Base64Coder.encodeString(pw);
    }

    /**
     * Cr�ation et configuration d'un proxy avec authentification crypt�.
     * <p>
     * 
     * @param proxyHost le nom du serveur proxy.
     * @param proxyPort Le port du serveur proxy.
     * @param proxyAuth user + password crypt� selon le code :
     *            String pw = user + ':' + password
     *            authentification = (new BASE64Encoder()).encode(pw.getBytes())
     */
    public Proxy(String proxyHost, String proxyPort, String proxyAuth) {

        host = proxyHost;
        port = proxyPort;

        System.getProperties().put("proxySet", "true");
        System.getProperties().put("proxyHost", host);
        System.getProperties().put("proxyPort", port);
        epassword = proxyAuth;
    }

    /**
     * Lib�re la connexion � la destruction du proxy.
     */
    protected final void finalize() throws Throwable {
        ctx.disconnect();
    }

    /**
     * openConnection.
     * <p>
     * 
     * @param url adresse de l'objet distant par son URL.
     * @return la connexion ouverte via le proxy.
     * @throws IOException si la connexion �choue ou la page est inaccessible.
     */
    public final HttpURLConnection openConnection(URL url) throws IOException {
        ctx = (HttpURLConnection) url.openConnection();
        ctx.setRequestProperty("Proxy-Authorization", "Basic " + epassword);
        return ctx;
    }

    /**
     * disconnect.
     * <p>
     * Ferme la connexion du proxy.
     */
    public final void disconnect() {
        if (ctx != null) ctx.disconnect();
    }

    /**
     * @return Returns the host.
     */
    public final String getHost() {
        return host;
    }

    /**
     * @return Returns the port.
     */
    public final String getPort() {
        return port;
    }

    /**
     * @return Returns the user.
     */
    public final String getUser() {
        return user;
    }

}
