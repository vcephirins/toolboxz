package org.free.toolboxz.goodies;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Classe utilitaire regroupant des outils génériques.
 * <p>
 * 
 * @author Vincent Cephirins
 * @version 1.0, 20/01/2009
 */
public abstract class Divers {

    // Inhibe l'instanciation de la classe.
    private Divers() {}

    public static String getHeapMemoryUsage() {
        return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().toString();
    }

    public boolean testUrl(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.connect();

            return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
        }
        catch (MalformedURLException e) {
            return false;
        }
        catch (IOException e) {
            return false;
        }
    }

    /**
     * tabToString.
     * <p>
     * Converti un tableau d'objets en une liste d'éléments dans une chaîne.
     * 
     * @param tab Le tableau d'objets à convertir
     * @param startDel Le délimiteur de début de chaîne.
     * @param endDel Le délimiteur de fin de cha�ne.
     * @param separator Le séparateurd'éléments.
     * @return La liste des éléments dans une cha�ne.
     */
    public static String tabToString(Object tab, String startDel, String endDel, String separator) {

        if (tab instanceof Object[]) {
            Object[] values = (Object[]) tab;
            StringBuffer result = new StringBuffer(values.length * 15);
            result.append(startDel);

            for (Object value : values) {
                result.append(value.toString()).append(separator);
            }
            if (values.length == 0) result.append(endDel);
            else {
                result.replace(result.length() - separator.length(), result.length(), endDel);
            }

            return result.toString();
        }
        else return tab.toString();

    }

    /**
     * Conversion d'un tableau de bytes en chaine hexadecimale.
     * 
     * @param bytes
     * @return
     */
    public static String toHexString(byte[] bytes) {
        final char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            int high = ((bytes[i] & 0xf0) >> 4);
            int low = (bytes[i] & 0x0f);
            sb.append(hexChars[high]).append(hexChars[low]);
        }
        return sb.toString();
    }
}
