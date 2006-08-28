package jeu.utils;

/**
 * Ces exceptions expliquent des droles de trucs qui peuvent arriver quand
 * on essaie d'extraire des Coups � partir de String.
 * @author dutech
 */
public class ExtractException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1960264340113851537L;

	/**
	 * 
	 */

	public ExtractException() {
        super();
    }
   
    public ExtractException(String message) {
        super(message);
    }
}
