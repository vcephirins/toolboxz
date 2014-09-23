package org.free.toolboxz.exceptions;

/**
 * Exception g�n�rale d'une ressource ou objet non trouv�.
 * <p>
 * @author Vincent Cephirins
 * @version 1.0, 20/01/2009
 * <li>Creation</li>
 */
public class NotFoundException extends LoggerException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public NotFoundException() {
        super("exception.notFound");
    }

    /**
     * 
     */
    public NotFoundException(String arg) {
        super("exception.notFound", arg);
    }

    /**
     * 
     * Constructeur.<p>
     * @param e L'exception d'origine.
     */
    public NotFoundException(Throwable e) {
        super(e, "exception.notFound");
    }

    /**
     * 
     * Constructeur.<p>
     * @param e L'exception d'origine.
     */
    public NotFoundException(Throwable e, String arg) {
        super(e, "exception.notFound", arg);
    }
}
