/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.utils;

/**
 *  Ces exceptions expliquent des droles de trucs qui peuvent arriver
 * dans le Jeu.
 * @author dutech
 */
public class GameException extends Exception {
    public GameException() {
        super();
    }
   
    public GameException(String message) {
        super(message);
    }
}
