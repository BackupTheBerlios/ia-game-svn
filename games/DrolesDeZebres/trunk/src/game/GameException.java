/*
 * Created on Dec 26, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package game;

/**
 * Ces exceptions expliquent des droles de trucs qui peuvent arriver
 * dans le Jeu.
 * @author dutech
 */
public class GameException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameException() {
        super();
    }
   
    public GameException(String message) {
        super(message);
    }

}
