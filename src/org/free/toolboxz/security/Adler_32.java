/**
 * 
 */
package org.free.toolboxz.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

/**
 * @author CEPHIRINS
 *
 */
public abstract class Adler_32 {

    // Inhibe l'instanciation de la classe.
    private Adler_32() {
    }
    
    public static String encode(String key) throws IOException {
        byte buffer[] = key.getBytes("UTF-8");
        InputStream bais = new ByteArrayInputStream(buffer);
        CheckedInputStream cis = new CheckedInputStream(bais, new Adler32());
        byte buf[] = new byte[8096];
        while (cis.read(buf) >= 0) {
        }
        return Long.toString(cis.getChecksum().getValue());
    }
}
