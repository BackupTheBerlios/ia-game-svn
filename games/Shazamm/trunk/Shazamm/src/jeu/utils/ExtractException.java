package jeu.utils;

/**
 * Ces exceptions expliquent des droles de trucs qui peuvent arriver quand
 * on essaie d'extraire des Coups à partir de String.
 * @author dutech
 */
public class ExtractException extends Exception {
    public ExtractException() {
        super();
    }
   
    public ExtractException(String message) {
        super(message);
    }
}
