/**
 * Liste des patterns utilisés dans les expressions régulières
 */
package org.free.toolboxz.enumPatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.free.toolboxz.exceptions.NotFoundException;

/**
 * Description des Patterns utilisés dans les expressions régulières.
 * <p>
 * 
 * @author Vincent Cephirins
 * @version 1.0 du 20/01/2099 <li>Creation</li>
 * @copyright (c) Vincent Cephirins
 * @class
 * @see java.util.regex.Pattern regex.Pattern for details.
 */

public enum Patterns {

    /**
     * Pattern d'un sérateur de liste.<br>
     */
    SEP("(\\s*[;,]\\s*)"),

    /**
     * Pattern d'une fin de ligne.<br>
     */
    EOL("(\n)"),

    /**
     * Pattern de début d'un tableau.<br>
     */
    BOUND_BEGIN("(\\s*\\[\\s*)"),

    /**
     * Pattern de séparateur des valeurs d'un tableau.<br>
     */
    BOUND_SEP("(\\s*,\\s*)"),

    /**
     * Pattern de fin d'un tableau.<br>
     */
    BOUND_END("(\\s*\\]\\s*)"),

    /**
     * Pattern d'un entier.<br>
     */
    INTEGER("(\\d+)"),

    /**
     * Pattern d'un double avec notation scientifique.<br>
     */
    DOUBLE("([\\-+]?\\d+(?:\\.\\d+)?(?:[eE][\\-+]?\\d+)?)"),

    /**
     * Pattern d'un nombre hexa.<br>
     */
    HEXA("(0[xX][a-fA-F0-9]+)"),

    /**
     * Pattern d'un commentaire C.<br>
     */
    COMMENTC("((/[*]).*([*]/))"),

    /**
     * Pattern de l'authority.<br>
     * [login[:pwd]@]host[:port]
     */
    AUTHORITY("(([^:]*)(:([^@]*))?@)?([^:]*)(:(.*))?"),

    /**
     * Pattern de version.<br>
     */
    VERSION("(v\\d+(.[0-9])?(.[0-9])?)", Pattern.CASE_INSENSITIVE),

    /**
     * Pattern des dates ISO
     */
    FMT_YEAR("(-?[0-9]{4})"),
    FMT_MONTH("(0[1-9]|1[012])"),
    FMT_DAY("(0[1-9]|[12][0-9]|3[01])"),
    FMT_DAYQ("([0-2][0-9][1-9]|3[0-5][0-9]|36[0-6])"),
    FMT_HOUR("([01][0-9]|2[0-4])"),
    FMT_MIN("([0-5][0-9])"),
    FMT_SEC("([0-5][0-9])"),
    FMT_TIME("(" + FMT_HOUR + ":" + FMT_MIN + ":" + FMT_SEC + "(\\.([0-9]+))?)"),
    DATE_ISO_A("(" + FMT_YEAR + "[-/]" + FMT_MONTH + "[-/]" + FMT_DAY + "([ tT]" + FMT_TIME + "Z?)?" + ")"),
    DATE_ISO_B("(" + FMT_YEAR + "[-/]" + FMT_DAYQ + "([ tT]" + FMT_TIME + "Z?)?" + ")"),
    DATE_ISO("(" + DATE_ISO_A + " | " + DATE_ISO_B + ")"),
    DATE_JULIAN("(-?[0-9]+(\\.5)?)"),

    /**
     * Pattern d'une adresse mail
     */
    MAIL_ADDRESS(
        "([a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?)",
        Pattern.CASE_INSENSITIVE);

    private String regex;
    private Pattern pattern;

    Patterns(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }

    Patterns(String regex, int flags) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex, flags);
    }

    /**
     * Retourne l'expression régulière associé au type
     * 
     * @return l'expression régulière du type demandé
     */
    public String toString() {
        return regex;
    }

    /**
     * Retourne l'expression régulière compilé
     * 
     * @return l'expression régulière compilée du type demandé
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Retourne un tableau d'éléments de l'entrée découpée en groupes.
     * 
     * @return un tableau d'éléments
     * @see Pattern.group
     */
    public String[] groups(String charSequence) {
        Matcher matcher = this.pattern.matcher(charSequence);
        matcher.find();
        String[] result = new String[matcher.groupCount()];
        for (int ind = 0; ind < matcher.groupCount(); ind++) {
            result[ind] = matcher.group(ind + 1);
        }
        return result;
    }

    /**
     * Retourne un tableau d'éléments de l'entrée découpée par l'expression régulière.
     * 
     * @param charSequence Chaine à analyser.
     * @return Le tableau des groupes
     * @see Pattern.split
     */
    public String[] split(String charSequence) {
        return this.pattern.split(charSequence);
    }

    /**
     * Retourne un Matcher sur la séquence en entrée.
     * 
     * @param charSequence Chaine à analyser.
     * @return un Matcher
     * @see java.util.regex.Matcher
     */
    public Matcher matcher(String charSequence) {
        return this.pattern.matcher(charSequence);
    }

    /**
     * Vérifie si la séquence en entrée valide l'expression régulière.
     * 
     * @param charSequence Chaine à vérifier.
     * @return true si et seulement si la séquence est égale à l'expression régulière.
     * @see java.util.regex.Matcher
     */
    public boolean matches(String charSequence) {
        return this.pattern.matcher(charSequence).matches();
    }

    /**
     * Vérifie si l'expression régulière fait tout ou partie de la séquence.
     * 
     * @param charSequence Chaine à analyser.
     * @return true si l'expression régulière fait tout ou partie de la séquence.
     * @see java.util.regex.Matcher
     */
    public boolean in(String charSequence) {
        return this.pattern.matcher(charSequence).find();
    }

    /**
     * Recherche d'une expression régulière dans une séquence de caratères.
     * 
     * @param charSequence Chaine à analyser.
     * @return l'expression trouvée.
     * @throws NotFoundException si l'expression n'est pas trouvée.
     * @see java.util.regex.Matcher
     */
    public String find(String charSequence) throws NotFoundException {
        Matcher matcher = this.pattern.matcher(charSequence);
        if (matcher.find()) { return matcher.group(); }
        throw new NotFoundException();
    }

    /**
     * Recherche de toutes les occurences d'une expression régulière dans une séquence de caratères.
     * 
     * @param charSequence Chaine à analyser.
     * @return un tableau de toutes les occurences de l'expression trouvée.
     * @throws NotFoundException si l'expression n'est pas trouvée.
     * @see java.util.regex.Matcher
     */
    public String[] finds(String charSequence) throws NotFoundException {
        Matcher matcher = this.pattern.matcher(charSequence);
        List<String> result = new ArrayList<String>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        if (result.size() == 0) throw new NotFoundException();
        String[] list = new String[result.size()];
        return result.toArray(list);
    }

    /**
     * Retourne le type correspondant à l'expression régulière.
     * 
     * @param regex le libellé recherché
     * @return le type
     */
    public static Patterns getType(String regex) {
        for (Patterns type : Patterns.values()) {
            if (type.regex != null && type.regex.equals(regex)) { return type; }
        }
        // Si non trouvé alors exception
        return valueOf(regex);
    }

    /**
     * Mise à jour d'un Pattern.
     * <p>
     * 
     * @param regex l'expression régulière.
     * @see Pattern
     */
    public void set(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }

    /**
     * Mise à jour d'un Pattern.
     * <p>
     * 
     * @param regex l'expression régulière.
     * @param flags options de compilation de l'expression régulière.
     * @see Pattern
     */
    public void set(String regex, int flags) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex, flags);
    }
}
