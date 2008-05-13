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
import diabalik.Piece;
import diabalik.Plateau;
import diabalik.PositionGrid2D;


/**
 * Deplacer essaie de déplacer un 'coureur' vers une case 'voisine' libre.
 *
 * @author dutech
 */
public class DeplacerMove extends Mouvement {
	
    PositionGrid2D leftSide[] = {PositionGrid2D.UPLEFT, PositionGrid2D.LEFT, PositionGrid2D.DOWNLEFT };
    PositionGrid2D rightSide[] = {PositionGrid2D.UPRIGHT, PositionGrid2D.RIGHT, PositionGrid2D.DOWNRIGHT };
	
	public DeplacerMove()
	{
		super();
	}
	public DeplacerMove( Joueur p_joueur, PositionGrid2D pStart, PositionGrid2D pEnd ) 
	{
        super();
		set( p_joueur, depl, pStart, pEnd);
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
        
        // il existe un pion coureur dans la case de départ
        Piece pion = etat.zePlateau.getCase(posDebut);
        if( pion == null ) {
            throw new MoveException("Pas de pion à déplacer");
        }
        if( pion.m_joueur.sameColor(zeJoueur) == false ) {
            throw new MoveException("Pion pas de la bonne couleur");
        }
        if( pion.type != Piece.coureur) {
            throw new MoveException("Le pion n'est pas un coureur");
        }
        
        // case d'arrivÃ©e vide
        if( etat.zePlateau.isCaseEmpty(posFin) == false ) {
            throw new MoveException("Case d'arrivée déjà  occupée");
        }
        
        // pas une case voisine
        if( areNeihbor( posDebut, posFin) == false ) {
            throw new MoveException("Cases pas voisines");
        }
        
        // check blocking line
        boolean blockBefore = checkBlocking(posDebut, etat);
        boolean contactBefore = checkContact(posDebut, etat);
        
        // bouge piÃ¨ce
        etat.zePlateau.setCase(posDebut, null);
        etat.zePlateau.setCase(posFin, pion);
        
        // update new blocking state
        if( blockBefore ) {
            if( checkBlocking(posFin, etat) == false) {
                etat.zeJoueurs[zeJoueur.couleur].nbBloqueur -=1;
            }
        }
        else {
            if( checkBlocking(posFin, etat) == true) {
                etat.zeJoueurs[zeJoueur.couleur].nbBloqueur +=1;
            }
        }
        if( contactBefore ) {
            if( checkContact(posFin, etat) == false ) {
                etat.zeJoueurs[zeJoueur.couleur].nbContact -=1;
            }
        }
        else {
            if( checkContact(posFin, etat) == true ) {
                etat.zeJoueurs[zeJoueur.couleur].nbContact +=1;
            }
        }
        //return etat;
        
        // nbMoveLeft -- a inclure dans les sous-classes
        etat.setNbMvtLeft(etat.getNbMvtLeft()-1);
        if( etat.getNbMvtLeft() == 0 ) {
            etat.setNbMvtLeft(3);
            etat.setTurn( (etat.getTurn() + 1) % 2 );
        }
    }
    
    /**
     * Check that two position are eligible for a runner to go from the 
     * first to the second.
     * @param pos1
     * @param pos2
     * @return
     */
    boolean areNeihbor( PositionGrid2D pos1, PositionGrid2D pos2)
    {
        if( pos1.hammingDistance( pos2) != 1) {
            return false;
        }
        return true;
    }
	/**
     * Check if a position is a blocking position.
     * @param pos
     * @param etat
     * @return
	 */
    boolean checkBlocking(PositionGrid2D pos, EtatJeu etat)
    {
        // si pote devant ou derrière : non bloquant
        if( pos.y < Plateau.tailleL-1 ) {
            if( etat.zePlateau.isCaseEmpty(pos.add(PositionGrid2D.UP)) == false) {
                if( etat.zePlateau.getCase(pos.add(PositionGrid2D.UP)).m_joueur.sameColor(zeJoueur)) {
                    return false;
                }
            }
        }
        if( pos.y > 0 ) {
            if( etat.zePlateau.isCaseEmpty(pos.add(PositionGrid2D.DOWN)) == false) {
                if( etat.zePlateau.getCase(pos.add(PositionGrid2D.DOWN)).m_joueur.sameColor(zeJoueur)) {
                    return false;
                }
            }
        }
        // sinon, pote à droite et à gauche?
        boolean left = false;
        if( pos.x > 0 ) {
            for (PositionGrid2D dir : leftSide) {
                PositionGrid2D checkPos = pos.add(dir);
                if( etat.zePlateau.isValidPosition(checkPos)) {
                    if( etat.zePlateau.isCaseEmpty(checkPos) == false) {
                        if( etat.zePlateau.getCase(checkPos).m_joueur.sameColor(zeJoueur)) {
                            left = true;
                        }
                    }
                }
            }
        }
        else {
            left = true;
        }
        if( left == false ) {
            return false;
        }
        boolean right = false;
        if( pos.x < Plateau.tailleC-1 ) {
            for (PositionGrid2D dir : rightSide) {
                PositionGrid2D checkPos = pos.add(dir);
                if( etat.zePlateau.isValidPosition(checkPos)) {
                    if( etat.zePlateau.isCaseEmpty(checkPos) == false) {
                        if( etat.zePlateau.getCase(checkPos).m_joueur.sameColor(zeJoueur)) {
                            right = true;
                        }
                    }
                }
            }
        }
        else {
            right = true;
        }
        return right;
    }
    /**
     * Check for contact UP or DOWN according to zeJoueur.couleur.
     * @param pos
     * @param etat
     * @return
     */
    boolean checkContact(PositionGrid2D pos, EtatJeu etat )
    {
        // suivant couleur, si ennemi devant ou derrière
        if( zeJoueur.couleur == Joueur.jaune ) {
            // check DOWN
            if( pos.y > 0 ) {
                if( etat.zePlateau.isCaseEmpty(pos.add(PositionGrid2D.DOWN)) == false) {
                    if( etat.zePlateau.getCase(pos.add(PositionGrid2D.DOWN)).m_joueur.sameColor(zeJoueur) == false) {
                        return true;
                    }
                }
            }
        }
        else {
            // check UP
            if( pos.y < Plateau.tailleL-1 ) {
                
                if( etat.zePlateau.isCaseEmpty(pos.add(PositionGrid2D.UP)) == false) {
                    if( etat.zePlateau.getCase(pos.add(PositionGrid2D.UP)).m_joueur.sameColor(zeJoueur) == false) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
