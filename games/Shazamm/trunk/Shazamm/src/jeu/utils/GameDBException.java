package jeu.utils;

/**
 * Ces exceptions expliquent des droles de trucs qui peuvent arriver quand
 * on essaie d'extraire des Coups à partir de String.
 * @author dutech
 */
public class GameDBException extends Exception {

	/**
     * 
     */
    private static final long serialVersionUID = -4654000284157008600L;

    /**
	 * 
	 */

	public GameDBException() {
        super();
    }
   
    public GameDBException(String message) {
        super(message);
    }
}
