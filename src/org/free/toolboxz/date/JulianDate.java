/**
 * 
 */
package org.free.toolboxz.date;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.free.toolboxz.enumPatterns.Patterns;

/**
 * JulianDate.java.
 * <p>
 * 
 * @author Vincent Cephirins
 * @version 1.0, 12 févr. 2011 <li>Creation</>
 */

public class JulianDate {

    public static TYPE defaultType = TYPE.UJD;

    /**
     * 
     * JulianDate.java.
     * <p>
     * 
     * @author Vincent Cephirins
     * @version 1.0, 12 févr. 2011 <li>Creation</> The number of days between julian date (Monday 01/01/4713 BC at
     *          12:00:00) and specific date
     */
    public enum TYPE {
        /**
         * Astronomic Julian Day : January 1, 4713 12:00:00 BC.
         */
        AJD(0L, 0),
        /**
         * Modified Julian Day : 17/11/1858 00:00:00.000
         */
        MJD(2400000L, MILLIS_IN_HALF_DAY),
        /**
         * Cnes Julian day : 01/01/1950 00:00:00.000
         */
        CJD(2433282L, MILLIS_IN_HALF_DAY),
        /**
         * Unix Julian day : 01/01/1970 00:00:00.000
         */
        UJD(2440587L, MILLIS_IN_HALF_DAY),
        /**
         * Tai Julian day : 01/01/1958 00:00:00.000
         */
        TAI(2436204L, MILLIS_IN_HALF_DAY),
        /**
         * no days between : 04/10/1582 > and < 15/10/1582
         */
        HOPEBEG(2299149L, MILLIS_IN_HALF_DAY), HOPEEND(2299160L, MILLIS_IN_HALF_DAY),

        /**
         * Gregorian Epoch : 01/01/0001
         */
        GREGORIAN_EPOCH(1721425, MILLIS_IN_HALF_DAY),

        PERSONNALIZED(UJD.days, MILLIS_IN_HALF_DAY);

        private long days;
        private int millis;

        TYPE(long days, int millis) {
            this.days = days;
            this.millis = millis;
        }

        public long getDays() {
            return days;
        }

        public int getMillis() {
            return millis;
        }

        @Override
        public String toString() {
            return name() + "(" + days + "d " + millis + "ms)";
        }

        void setPersonnalized(long days, int millis) {
            this.days = days;
            this.millis = millis;
        }
    }

    /**
     * The seconds within one hour
     */
    public static final int SEC_IN_HOUR = 60 * 60;
    /**
     * The millisecondes within one hour
     */
    public static final int MILLIS_IN_HOUR = SEC_IN_HOUR * 1000;

    /**
     * The seconds within one day
     */
    public static final int SEC_IN_DAY = 24 * 60 * 60;
    /**
     * The millisecondes within one day
     */
    public static final int MILLIS_IN_DAY = SEC_IN_DAY * 1000;

    /**
     * The seconds within 12 hours
     */
    public static final int SEC_IN_HALF_DAY = 12 * 60 * 60;
    /**
     * The millisecondes within 12 hours
     */
    public static final int MILLIS_IN_HALF_DAY = SEC_IN_HALF_DAY * 1000;

    /**
     * julian days since date ref
     */
    private long days;

    /**
     * The millis within the day
     */
    private int millis;
    /**
     * The fraction within the millis in picosecond.
     */
    private int submillis;

    /**
     * Leap year.
     */
    private boolean isLeapYear = false;

    /**
     * Default Type.
     */
    private TYPE type = defaultType;

    /**
     * Allocates a Date object and initializes it to represent namely January 1, 1970, 00:00:00 GMT.
     * <p>
     * 
     * @throws DateException
     */
    public JulianDate() throws DateException {
        GregorianCalendar cal = new GregorianCalendar();
        set(cal, 0);
    }

    /**
     * Duplicates a Date object.
     * <p>
     * <p>
     * 
     * @param jd julian date to duplicate
     * @throws DateException
     */
    public JulianDate(JulianDate jd) throws DateException {
        set(jd.type, jd.days, jd.millis, jd.submillis);
    }

    /**
     * Allocates a Date object and initializes it.<p<
     * 
     * @param year
     * @param month
     * @param day
     * @param sec
     * @param millis
     * @throws DateException
     */
    public JulianDate(int year, int month, int day, int sec, int millis) throws DateException {
        set(year, month, day, sec, millis, 0);
    }

    /**
     * Allocates a Date object and initializes it to represent the specified number of days since the Julian base time,
     * namely January 1, 1970, 00:00:00 GMT.
     * <p>
     * 
     * @param days
     * @throws DateException
     */
    public JulianDate(long days) throws DateException {
        set(defaultType, days, 0, 0);
    }

    /**
     * Allocates a Date object and initializes it to represent the specified number of days since the reference base
     * time.
     * <p>
     * 
     * @param type reference base time
     * @param days
     * @throws DateException
     */
    public JulianDate(TYPE type, long days) throws DateException {
        this.type = type;
        set(type, days, 0, 0);
    }

    /**
     * Allocates a Date object and initializes it to represent the specified number of days and millisecondes since the
     * Julian base time, namely January 1, 1970, 00:00:00 GMT.
     * <p>
     * 
     * @param days
     * @param millis
     * @throws DateException
     */
    public JulianDate(long days, int millis) throws DateException {
        set(defaultType, days, millis, 0);
    }

    /**
     * Allocates a Date object and initializes it to represent the specified number of days and millisecondes since the
     * reference base time.
     * <p>
     * 
     * @param type reference base time
     * @param days
     * @param millis
     * @throws DateException
     */
    public JulianDate(TYPE type, long days, int millis) throws DateException {
        this.type = type;
        set(type, days, millis, 0);
    }

    /**
     * Allocates a Date object and initializes it to represent the specified number of days and millisecondes since the
     * Julian base time, namely January 1, 1970, 00:00:00 GMT.
     * <p>
     * 
     * @param days
     * @param millis
     * @param submillis precision under milliseconde
     * @throws DateException
     */
    public JulianDate(long days, int millis, int prec) throws DateException {
        set(defaultType, days, millis, prec);
    }

    /**
     * Allocates a Date object and initializes it to represent the specified number of days and millisecondes since the
     * reference base time.
     * <p>
     * 
     * @param type reference base time
     * @param days
     * @param millis
     * @param submillis precision under milliseconde
     * @throws DateException
     */
    public JulianDate(TYPE type, long days, int millis, int prec) throws DateException {
        this.type = type;
        set(type, days, millis, prec);
    }

    /**
     * Allocates a Date object and initializes it.
     * <p>
     * 
     * @param cal Calendar date to initialize.
     * @throws DateException
     */
    public JulianDate(Calendar cal) throws DateException {
        set(cal);
    }

    /**
     * Conversion d'un jour julien spécifique en jour julien de référence.
     * <p>
     * 
     * @param type Type du jour julien source
     * @param days jour julien
     * @param millis millisecondes du dernier jour
     * @param submillis précision à la picoseconde de la dernière milliseconde
     * @return
     * @throws DateException
     */
    private JulianDate toAJD(TYPE type, long days, int millis, int prec) throws DateException {
        if (millis < 0 || millis >= MILLIS_IN_DAY) throw new DateException("exception.invalidParameter", "millis");
        if (prec < 0 || prec >= 1000000000) throw new DateException("exception.invalidParameter", "precision");
        long deltaDays = (TYPE.AJD.days + days) - type.days;
        int deltaMillis = (TYPE.AJD.millis + millis) - type.millis;
        if (deltaMillis > MILLIS_IN_DAY) {
            deltaDays += 1;
            deltaMillis -= MILLIS_IN_DAY;
        }
        else if (deltaMillis < 0) {
            deltaDays -= 1;
            deltaMillis += MILLIS_IN_DAY;
        }

        this.type = TYPE.AJD;
        this.days = deltaDays;
        this.millis = deltaMillis;
        this.submillis = prec;
        return this;
    }

    public void set(Calendar cal) throws DateException {
        set(cal, 0);
    }

    public void set(Calendar cal, int prec) throws DateException {
        int secondes = (cal.get(Calendar.HOUR_OF_DAY) * SEC_IN_HOUR) + (cal.get(Calendar.MINUTE) * 60)
            + cal.get(Calendar.SECOND);
        toAJD(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), secondes,
            cal.get(Calendar.MILLISECOND), prec);
    }

    public void set(int year, int month, int day, int sec) throws DateException {
        set(year, month, day, sec, 0, 0);
    }

    public void set(int year, int month, int day, int sec, int millis) throws DateException {
        set(year, month, day, sec, millis, 0);
    }

    public void set(int year, int month, int day, int sec, int millis, int prec) throws DateException {
        toAJD(year, month, day, sec, millis, prec);
    }

    public void set(JulianDate jd) throws DateException {
        set(jd.getType(), jd.getDays(), jd.getMillis(), jd.getPrecision());
    }

    public void set(long days) throws DateException {
        set(this.type, days, 0, 0);
    }

    public void set(long days, int millis) throws DateException {
        set(this.type, days, millis, 0);
    }

    public void set(long days, int millis, int prec) throws DateException {
        set(this.type, days, millis, prec);
    }

    public void set(TYPE type, long days, int millis, int prec) throws DateException {
        toAJD(type, days, millis, prec);
    }

    public TYPE getType() {
        return type;
    }

    /**
     * Retourne le nombre de jours depuis la date de référence.
     * <p>
     * 
     * @return
     */
    public long getDays() {
        return days;
    }

    /**
     * Retourne le nombre de jours depuis la date Julian Astronomique.
     * <p>
     * 
     * @return
     */
    public long getAJDDays() {
        long deltaDays = days + type.days;
        int deltaMillis = millis + type.millis;
        if (deltaMillis >= MILLIS_IN_DAY) {
            deltaDays++;
        }
        return deltaDays;
    }

    /**
     * Retourne le nombre de millisecondes du dernier jour depuis la date Julian Astronomique.
     * <p>
     * 
     * @return
     */
    public int getMillis() {
        return millis;
    }

    public int getAJDMillis() {
        int deltaMillis = millis + type.millis;
        if (deltaMillis >= MILLIS_IN_DAY) {
            deltaMillis -= MILLIS_IN_DAY;
        }
        return deltaMillis;
    }

    /**
     * Retourne le nombre de millisecondes depuis la date de référence.
     * <p>
     * 
     * @return
     */
    public long getTimeInMillis() {
        return (days * MILLIS_IN_DAY) + millis;
    }

    /**
     * Retourne la précision en picosecondes depuis la dernière milliseconde.
     * <p>
     * 
     * @return
     */
    public int getPrecision() {
        return submillis;
    }

    /**
     * Positionne une date de référence.
     * <p>
     * 
     * @param year année.
     * @param month mois.
     * @param day jour.
     * @param sec secondes du dernier jour.
     * @param millis millisecondes de la dernière seconde.
     * @throws DateException
     */
    public void setRef(int year, int month, int day, int sec, int millis) throws DateException {
        // Calcul de la date Julienne Astronomique
        JulianDate jd = new JulianDate(year, month, day, sec, millis);
        int deltaMillis = jd.type.millis + millis;
        if (deltaMillis >= MILLIS_IN_DAY)
            setRef(jd.type.days + jd.days + 1, deltaMillis - MILLIS_IN_DAY);
        else setRef(jd.type.days + jd.days, deltaMillis);
    }

    /**
     * Positionne une date de référence.
     * <p>
     * 
     * @param cal jour de référence.
     * @throws DateException
     */
    public void setRef(Calendar cal) throws DateException {
        int secondes = (cal.get(Calendar.HOUR_OF_DAY) * SEC_IN_HOUR) + (cal.get(Calendar.MINUTE) * 60)
            + cal.get(Calendar.SECOND);
        setRef(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), secondes,
            cal.get(Calendar.MILLISECOND));
    }

    /**
     * Positionne une date de référence.
     * <p>
     * 
     * @param days jour julien astronomique de référence.
     * @throws DateException
     */
    public void setRef(long days) throws DateException {
        setRef(days, 0);
    }

    /**
     * Positionne une date de référence.
     * <p>
     * 
     * @param days jour julien astronomique de référence.
     * @param millis millisecondes de la dernière seconde.
     * @throws DateException
     */
    public void setRef(long days, int millis) throws DateException {
        if (millis < 0 || millis >= MILLIS_IN_DAY) throw new DateException("exception.invalidParameter", "millis");
        type = TYPE.PERSONNALIZED;
        type.setPersonnalized(days, millis);
    }

    /**
     * Positionne une date de référence précalculée.
     * <p>
     * 
     * @param type Type de la date julienne choisi.
     * @throws DateException
     */
    public void setRef(TYPE type) {
        this.type = type;
    }

    /* fonctions de gestion des dates */

    /**
     * Retourne vrai si l'année est bissextile.
     * <p>
     * Le calendrier Gregorien debute au 15/10/1582. Avant cette date, toutes les annees multiples de 4 sont
     * bissextiles. Pour qu'une année soit bissextile, il suffit qu'elle soit un multiple de 4, sans être un multiple de
     * 100 sauf si elle est multiple de 400. Les années 1700, 1800 et 1900 ne sont pas bissextiles alors que 2000 est
     * bissextile.
     * 
     * @return true si l'année est bissextile.
     */
    public boolean isInLeapYear() {
        return isLeapYear;
    }

    /**
     * Retourne vrai si l'année est bissextile.
     * <p>
     * Le calendrier Gregorien debute au 15/10/1582. Avant cette date, toutes les annees multiples de 4 sont
     * bissextiles. Pour qu'une année soit bissextile, il suffit qu'elle soit un multiple de 4, sans être un multiple de
     * 100 sauf si elle est multiple de 400. Les années 1700, 1800 et 1900 ne sont pas bissextiles alors que 2000 est
     * bissextile.
     * 
     * @param year
     * @return true si l'année est bissextile.
     */
    static public boolean isInLeapYear(int year) {

        if (year <= 1582) {
            if ((year % 4) == 0) return true;
        }
        else {
            if ((year % 4) == 0) {
                if ((year % 100) == 0 && (year % 400) != 0) return false;
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne le Jour Julien Astronomique (AJD) qui est le temps qui s'est écoulé depuis midi du 27 novembre 4713 av.
     * J.-C (27/11/-4713). Le calendirier Gregorien debute au 15/10/1582. Avant cette date, toutes les annees multiples
     * de 4 sont bissextiles. Il manque donc un décalage de 10 jours entre les deux calendriers. On ne peut pas calculer
     * les jours Juliens des dates du 5 au 14 octobre 1582 puisqu'elles n'existent pas => on ramene la date au
     * 04/10/1582.
     * 
     * @param year
     * @return
     * @throws DateException
     */
    private JulianDate toAJD(int year, int month, int day, int sec, int millis, int prec) throws DateException {

        if (millis < 0 || millis >= 1000) throw new DateException("exception.invalidParameter", "millis");
        if (prec < 0 || prec >= 1000000000) throw new DateException("exception.invalidParameter", "precision");

        long leapYear = 0;
        long result = 0L;

        this.isLeapYear = isInLeapYear(year);

        /* si date > 04/10/1582 et date < 15/10/1582 */
        if (year == 1582 && month == 10 && (day > 4 && day < 15)) day = 4;

        // Nombre d'annees bissextiles des annees passees à partir de -4713
        if (year <= 1582)
            leapYear = (long) (Math.floor(((year - 1) + 4716) / 4));
        else leapYear = (long) (Math.floor(((year - 1) - 1580) / 4) - Math.floor(((year - 1) - 1600) / 100)
            + Math.floor(((year - 1) - 1600) / 400) + 1574);

        /* Nombre de jours des annees passees */
        result = (((year - 1) + 4713) * 365) + leapYear;

        /* Si la date est >= au 15/10/1582, on supprime 10 jours */
        if ((year > 1582) || (year == 1582 && month > 10) || (year == 1582 && month == 10 && day >= 15)) result -= 10;

        // Nombre de jours des mois passes de l'annee en cours
        if (month < 8)
            result += ((month - 1) * 30) + Math.floor(month / 2);
        else result += ((month - 1) * 30) + Math.floor((month + 1) / 2);

        /* -1 si l'annee est bissextile et le mois > 2 */
        /* -2 si l'annee n'est pas bissextile et le mois > 2 */
        if (month > 2) result -= ((this.isLeapYear) ? 1 : 2);

        /* Nombre de jours passes du mois en cours */
        result += day - 1;

        /* Cale la date à 12H00 */
        int millisec = (sec * 1000) + millis;
        if (millisec < MILLIS_IN_HALF_DAY) {
            result -= 1;
            millisec += MILLIS_IN_HALF_DAY;
        }
        else millisec -= MILLIS_IN_HALF_DAY;

        this.type = TYPE.AJD;
        this.days = result;
        this.millis = millisec;
        this.submillis = prec;
        return this;
    }

    /**
     * Calcul de la date AJD en date Gregorian.
     * <p>
     * 
     * @return
     */
    public Calendar getCalendar() {
        double jours = getAJDDays();
        int millis = getAJDMillis();
        int year;

        GregorianCalendar cal = new GregorianCalendar(0, 0, 1);

        // Recale la date à 00h00
        if (millis >= MILLIS_IN_HALF_DAY) {
            jours += 1;
            millis -= MILLIS_IN_HALF_DAY;
        }
        else millis += MILLIS_IN_HALF_DAY;

        /* +10 jours si date > 04/10/1582 et date < 15/10/1582 */
        if (jours > TYPE.HOPEBEG.getDays() && jours < TYPE.HOPEEND.getDays()) jours += 10;

        /* Calcul du nombre d'annees */
        if (jours >= TYPE.HOPEEND.getDays()) { /* >= 15/10/1582 */
            jours = jours - TYPE.HOPEEND.getDays() + 285;
            year = (int) (1582 + (Math.floor(jours / 365.2425)));
            cal.set(Calendar.YEAR, year);
            /* Nombre de jours restants */
            jours = Math.floor((double) ((jours % 365.2425)));
        }
        else {
            year = (int) (-4712 + Math.floor(jours / 365.25));
            cal.set(Calendar.YEAR, year);
            /* Nombre de jours restants */
            jours = (double) (Math.floor(jours % 365.25));
        }

        /* Calcul du nombre de mois */
        /* Pour simplifier, on ramene tous les mois à 31 jours */
        if (isInLeapYear(year)) {
            if (jours > 59) jours += 2;
        }
        else {
            if (jours > 58) jours += 3;
        }
        if (jours > 122) jours++;
        if (jours > 184) jours++;
        if (jours > 277) jours++;
        if (jours > 339) jours++;

        cal.set(Calendar.MONTH, (int) Math.floor((jours) / 31));

        /* Nombre de jours restant */
        cal.set(Calendar.DAY_OF_MONTH, (int) (1 + (jours % 31)));

        /* Millisecondes */
        int millisec = millis % 1000;
        cal.set(Calendar.MILLISECOND, millisec);
        int secondes = (millis - millisec) / 1000;

        /* Heures */
        cal.set(Calendar.HOUR, secondes / SEC_IN_HOUR);
        secondes %= SEC_IN_HOUR;

        /* Minutes */
        cal.set(Calendar.MINUTE, secondes / 60);
        secondes %= 60;

        /* Nombre de secondes */
        cal.set(Calendar.SECOND, secondes);

        return cal;
    }

    public static JulianDate parse(String dateIso) throws DateException {

        try {
            int year, month, day, millis;
            int hour, min, sec;

            String[] gps = Patterns.DATE_ISO_A.groups(dateIso);

            year = Integer.parseInt(gps[0]);
            month = Integer.parseInt(gps[1]);
            day = Integer.parseInt(gps[2]);
            hour = (gps[4] == null) ? 0 : Integer.parseInt(gps[4]) * SEC_IN_HOUR;
            min = (gps[5] == null) ? 0 : Integer.parseInt(gps[5]) * 60;
            sec = (gps[6] == null) ? 0 : Integer.parseInt(gps[6]);
            millis = (gps[8] == null) ? 0 : Integer.parseInt(gps[8]);

            JulianDate jd = new JulianDate(year, month, day, hour + min + sec, millis);

            return jd;
        }
        catch (Exception e) {
            throw new DateException("exception.invalidParameter", Patterns.DATE_ISO_A.toString());
        }
    }

    public static void setDefaultType(JulianDate.TYPE type) {
        defaultType = type;
    }

    public String toString() {
        Calendar cal = getCalendar();
        return String.format("%04d-%02d-%02dT%02d:%02d:%02d.%03d",
            (cal.get(Calendar.ERA) == 0) ? 1 - cal.get(Calendar.YEAR) : cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
    }
}
