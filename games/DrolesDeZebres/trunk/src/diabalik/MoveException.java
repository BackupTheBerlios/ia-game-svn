/*
 * Created on Dec 26, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

/**
 * Ces exceptions expliquent des droles de trucs qui peuvent arriver
 * dans le Jeu.
 * @author dutech
 */
@SuppressWarnings("serial")
public class MoveException extends Exception {

    public MoveException() {
        super();
    }
   
    public MoveException(String message) {
        super(message);
    }

}
