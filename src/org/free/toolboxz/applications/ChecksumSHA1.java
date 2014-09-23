package org.free.toolboxz.applications;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.free.toolboxz.exceptions.Messages;
import org.free.toolboxz.exceptions.SimpleException;
import org.free.toolboxz.security.Sha1;

import com.spinn3r.log5j.Logger;

/**
 * @author Vincent Cephirins
 * @version 1.0, 10/10/2011
 *          <li>Creation</li>
 */

public class ChecksumSHA1 {

    // Initialise un logger (voir conf/xml/log4j.xml).
    private static final Logger LOGGER = Logger.getLogger();

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        int nbMandatoryArgs = 0;
        int indMand = 0;
        String[] mandatoryArgs = new String[nbMandatoryArgs];
        InputStream in = null;

        try {
            // Chargement des messages d'exception applicatifs
            Messages.getInstance().load("org.free.toolboxz.exceptions.messagesException");

            for (int indArg = 0; indArg < args.length; indArg++) {
                if (args[indArg].startsWith("-h") || args[indArg].equals("--help")) {
                    throw new SimpleException("exception.ChecksumMD5.syntax");
                }

                if (args[indArg].startsWith("-t") || args[indArg].equals("--text")) {
                    String value = Sha1.encode(args[indArg + 1]);
                    System.out.println(args[indArg + 1] + " : " + value);
                }

                if (args[indArg].startsWith("-f") || args[indArg].equals("--file")) {
                    in = new FileInputStream(new File(args[indArg + 1]));
                    String value = Sha1.encode(in);
                    System.out.println(args[indArg + 1] + " : " + value);
                    in.close();
                }

                // Arguments obligatoires
                if (indMand < nbMandatoryArgs) {
                    if (args[indArg].startsWith("-")) {
                        throw new SimpleException("exception.ChecksumSHA1.syntax");
                    }
                    mandatoryArgs[indMand] = args[indArg];

                    indMand++;
                }
            }

            if (indMand != nbMandatoryArgs) {
                throw new SimpleException("exception.ChecksumSHA1.syntax");
            }

        }
        catch (SimpleException se) {
            if (LOGGER.isDebugEnabled()) {
                se.printStackTrace();
            }
            else {
                se.printMessages();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) in.close();
        }
    }
}
