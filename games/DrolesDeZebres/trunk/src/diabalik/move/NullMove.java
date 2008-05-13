/*
 * Created on Dec 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik.move;

import game.GameException;
import diabalik.EtatJeu;
import diabalik.Joueur;
import diabalik.Mouvement;
import diabalik.MoveException;
import diabalik.PositionGrid2D;


/**
 * Deplacer essaie de déplacer un 'coureur' vers une case 'voisine' libre.
 *
 * @author dutech
 */
public class NullMove extends Mouvement {
	
	
	public NullMove()
	{
		super();
	}
	public NullMove( Joueur p_joueur ) 
	{
        super();
		set( p_joueur, Mouvement.none, new PositionGrid2D(0,0), new PositionGrid2D(0,0));
	}
    
    /**
     * Applique le Mouvement au jeu.
     * @param p_jeu
     */
	public void apply( EtatJeu etat )
	throws GameException, MoveException
	{
	    super.apply( etat );
	    
	    // local move
	    
	    
	    etat.setNbMvtLeft(0);
	    
	    // nbMoveLeft -- a inclure dans les sous-classes
	    if( etat.getNbMvtLeft() == 0 ) {
	        etat.setNbMvtLeft(3);
	        etat.setTurn( (etat.getTurn() + 1) % 2 );
	    }
	}
	
}
