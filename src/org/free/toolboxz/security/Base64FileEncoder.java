package org.free.toolboxz.security;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;


/**
 * Sample program to encode a binary file into a Base64 text file.<p>
 * <br>
 * @author  Christian d'Heureuse (www.source-code.biz)
 */
public final class Base64FileEncoder {

    /**
     * Fonction principale.
     * @param args :
     * <li>le fichier � encoder</li>
     * <li>le fichier r�sultat</li>
     * <br>
     * @throws IOException en cas d'erreur sur les fichiers.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Command line parameters: inputFileName outputFileName");
            System.exit(9);
        }
        encodeFile(args[0], args[1]);
    }

    private static void encodeFile(String inputFileName, String outputFileName) throws IOException {
        BufferedInputStream in = null;
        BufferedWriter out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(inputFileName));
            out = new BufferedWriter(new FileWriter(outputFileName));
            encodeStream(in, out);
            out.flush();
        }
        finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }

    private static void encodeStream(InputStream in, BufferedWriter out) throws IOException {
        int lineLength = 72;
        byte[] buf = new byte[lineLength / 4 * 3];
        while (true) {
            int len = in.read(buf);
            if (len <= 0) break;
            out.write(Base64Coder.encode(buf, len));
            out.newLine();
        }
    }

    private Base64FileEncoder() {
    }
    
} // end class Base64FileEncoder
