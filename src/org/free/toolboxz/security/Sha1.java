/**
 * 
 */
package org.free.toolboxz.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.free.toolboxz.goodies.Divers;

/**
 * @author CEPHIRINS
 *
 */
public abstract class Sha1 {

    // Inhibe l'instanciation de la classe.
    private Sha1() {
    }
    
    public static String encode(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] uniqueKey = key.getBytes("UTF-8");
        byte[] hash = null;

        hash = MessageDigest.getInstance("SHA-1").digest(uniqueKey);
        return Divers.toHexString(hash);
    }

    public static String encode(InputStream source) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] buf = new byte[8096];
        int len;

        while ((len = source.read(buf)) >= 0) {
            md.update(buf, 0, len);
        }
        return Divers.toHexString(md.digest());
    }
}
