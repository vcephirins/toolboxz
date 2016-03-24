package org.free.toolboxz.applications;

import java.net.URISyntaxException;

import org.apache.log4j.Level;
import org.free.toolboxz.date.JulianDate;
import org.free.toolboxz.exceptions.Messages;
import org.free.toolboxz.exceptions.SimpleException;

import com.spinn3r.log5j.Logger;

/**
 * @author CEPHIRINS
 */
public class ConvertDate {

    // Initialise un logger (voir conf/xml/log4j.xml).
    private static final Logger LOGGER = Logger.getLogger();

    /**
     * @param args
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws URISyntaxException {
        int nbMandatoryArgs = 1;
        int indMand = 0;
        String[] mandatoryArgs = new String[nbMandatoryArgs];

        try {
            // Chargement des messages d'exception applicatifs
            Messages.load("org.free.toolboxz.exceptions.messagesException");

            JulianDate jd = new JulianDate();
            
            for (int indArg = 0; indArg < args.length; indArg++) {
                if (args[indArg].equals("-h") || args[indArg].equals("--help")) {
                    throw new SimpleException("exception.ConvertDate.syntax");
                }
                
                if (args[indArg].equals("-t") || args[indArg].equals("--trace")) {
                    LOGGER.setLevel(Level.DEBUG);
                    continue;
                }

                if (args[indArg].equals("-r") || args[indArg].equals("--ref")) {
                    // Positionne une date référence
                    jd.setRef(JulianDate.parse(args[++indArg]).getCalendar());
                    continue;
                }

                // Arguments obligatoires
                if (indMand < nbMandatoryArgs) {
                    if (args[indArg].startsWith("-")) {
                        throw new SimpleException("exception.ConvertDate.syntax");
                    }
                    mandatoryArgs[indMand] = args[indArg];
                    
//                    jd.setRef(JulianDate.TYPE.AJD);
//                    jd.set(JulianDate.parse(args[indArg]).getCalendar());
//                    
//                    System.out.println("Ref      : " + jd.getType().toString());
//                    JulianDate jdRef = new JulianDate(JulianDate.TYPE.AJD, jd.getType().getDays(), jd.getType().getMillis());
//                    System.out.println("Ref Date : " + jdRef.toString());
//                    System.out.println("date     : " + jd.toString());
//                    System.out.println("Julian   : " + jd.getDays() + "d " + jd.getMillis() + "ms");
//                    
//                    jd = new JulianDate(JulianDate.TYPE.AJD, 2436933, JulianDate.MILLIS_IN_HALF_DAY + 1);
//                    System.out.println("date     : " + jd.toString());
//                    jd = new JulianDate(JulianDate.TYPE.AJD, 2436934, JulianDate.MILLIS_IN_HALF_DAY + 1);
//                    System.out.println("date     : " + jd.toString());
//                    jd = new JulianDate(JulianDate.TYPE.AJD, 2436935, JulianDate.MILLIS_IN_HALF_DAY - 1);
//                    System.out.println("date     : " + jd.toString());
//                    jd = new JulianDate(JulianDate.TYPE.AJD, 2436936, JulianDate.MILLIS_IN_HALF_DAY + 1);
//                    System.out.println("date     : " + jd.toString());
//                    jd = new JulianDate(1960, 1, 1, 0, 0);
//                    System.out.println("date     : " + jd.toString());
//                    jd = new JulianDate(1961, 1, 1, 0, 0);
//                    System.out.println("date     : " + jd.toString());
                    
                    String sdate;
                    JulianDate.setDefaultType(JulianDate.TYPE.CJD);
                    JulianDate jd2;
                    for (int i = 1; i< 10; i++) {
                        jd = new JulianDate(i);
                        jd2 = JulianDate.parse(jd.toString());
//                        if(jd.getDays() != jd2.getDays()) {
                            System.out.println(jd.getType().toString());
                            System.out.println(jd2.getType().toString());
                            System.out.println(jd.getDays()  + " " + jd.getMillis() + " <> " + jd2.getDays() + " " + jd2.getMillis());
                            System.out.print(jd.toString() + " <> ");
                            System.out.println(jd2.toString());
//                            break;
//                        } 
                        
                    }
                }

                indMand++;
            }

            if (indMand != nbMandatoryArgs) {
                throw new SimpleException("exception.ConvertDate.syntax");
            }
            
            // Convertion de la date
//            System.out.println("-4712 bissextile ? : "+JulianDate.isInLeapYear(-4712));
//            System.out.println("-4 bissextile ? : "+JulianDate.isInLeapYear(-4));
//            System.out.println("0 bissextile ? : "+JulianDate.isInLeapYear(0));
//            System.out.println("1200 bissextile ? : "+JulianDate.isInLeapYear(1200));
//            System.out.println("1900 bissextile ? : "+JulianDate.isInLeapYear(1900));
//            System.out.println("1996 bissextile ? : "+JulianDate.isInLeapYear(1996));
//            System.out.println("1999 bissextile ? : "+JulianDate.isInLeapYear(1999));
//            System.out.println("2000 bissextile ? : "+JulianDate.isInLeapYear(2000));
//            System.out.println("2004 bissextile ? : "+JulianDate.isInLeapYear(2004));
//            System.out.println("2011 bissextile ? : "+JulianDate.isInLeapYear(2011));
//            System.out.println("DATE_ISO_A = " + Patterns.DATE_ISO_A.toString());
//            System.out.println(GregorianCalendar.getInstance().getTime().toString() + " => " + jd.toString() + ", " + jd.getMillisecondes());
//
//            jd = new JulianDate(365);
//            System.out.println("Ref Date => " + jd.getType().toString() + " => " + jd.getDays() + ", " + jd.getMillisecondes());
//            System.out.println("01/01/1971 00:00:00 => " + jd.toString());
//
//            jd = new JulianDate(JulianDate.TYPE.CJD, 366, 46800000);
//            System.out.println("Ref Date => " + jd.getType().toString() + " => " + jd.getDays() + ", " + jd.getMillisecondes());
//            System.out.println("02/01/1951 13:00:00 => " + jd.toString());
//            
//            jd.setType(JulianDate.TYPE.AJD);
//            System.out.println("Ref Date => " + jd.getType().toString() + " => " + jd.getDays() + ", " + jd.getMillisecondes());
//            System.out.println("02/01/1951 13:00:00 => " + jd.toString());
//            
//            jd = new JulianDate(JulianDate.TYPE.CJD, 35, 0);
//            System.out.println("Ref Date => " + jd.getType().toString() + " => " + jd.getDays() + ", " + jd.getMillisecondes());
//            System.out.println("05/02/1950 00:00:00 => " + jd.toString());
//
//            jd.setRef(1950,1,1,3600,1);
//            System.out.println("Ref Date => " + jd.getType().toString() + " => " + jd.getDays() + ", " + jd.getMillisecondes());
//            System.out.println("05/02/1950 00:00:00 => " + jd.toString());
//
//            jd.set(1950, 02, 05, 7200, 2);
//            System.out.println("Ref Date => " + jd.getType().toString() + " => " + jd.getDays() + ", " + jd.getMillisecondes());
//            System.out.println("05/02/1950 00:00:00 => " + jd.toString());
//
//            jd.setRef(JulianDate.TYPE.AJD);
//            jd.set(-4712, 01, 01, 3600*12);
//            System.out.println("Ref Date => " + jd.getType().toString() + " => " + jd.getDays() + ", " + jd.getMillisecondes());
//            System.out.println("01/01/-4712 12:00:00 => " + jd.toString());
//
//            jd.set(1858, 11, 17, 0);
//            System.out.println("Ref Date => " + jd.getType().toString() + " => " + jd.getDays() + ", " + jd.getMillisecondes());
//            System.out.println("17/11/1958 00:00:00 => " + jd.toString());

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
    }
}
