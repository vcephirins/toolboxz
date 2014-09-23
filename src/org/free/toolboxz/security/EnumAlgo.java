/**
 * 
 */
package org.free.toolboxz.security;

import java.util.regex.Pattern;

/**
 * EnumAlgo.java.
 * <p>
 * 
 * @author Vincent Cephirins
 * @version 1.0, 23 juin 2013 <li>Creation</>
 */

public enum EnumAlgo {
    MD5("md5"), SHA1("sha-?1"), ADLER32("adler-? ?32"), BASE64("base64");

    private String regex;
    private Pattern pattern;

    EnumAlgo(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }

    EnumAlgo(String regex, int flags) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public byte[] hash() {
        return new byte[3];
    }
}
