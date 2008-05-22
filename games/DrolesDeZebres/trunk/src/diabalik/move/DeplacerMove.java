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
 * Deplacer essaie de d�placer un 'coureur' vers une case 'voisine' libre.
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
        
        // il existe un pion coureur dans la case de d�part
        Piece pion = etat.zePlateau.getCase(posDebut);
        if( pion == null ) {
            throw new MoveException("Pas de pion � d�placer");
        }
        if( pion.m_joueur.sameColor(zeJoueur) == false ) {
            throw new MoveException("Pion pas de la bonne couleur");
        }
        if( pion.type != Piece.coureur) {
            throw new MoveException("Le pion n'est pas un coureur");
        }
        
        // case d'arrivée vide
        if( etat.zePlateau.isCaseEmpty(posFin) == false ) {
            throw new MoveException("Case d'arriv�e d�j� occup�e");
        }
        
        // pas une case voisine
        if( areNeihbor( posDebut, posFin) == false ) {
            throw new MoveException("Cases pas voisines");
        }
        
        // check blocking line
        boolean leftBefore = checkLinkedLeft(posDebut, etat);
        boolean rightBefore = checkLinkedRight(posDebut, etat);
        boolean contactBefore = checkContact(posDebut, etat);
        
        // bouge pièce
        etat.zePlateau.setCase(posDebut, null);
        etat.zePlateau.setCase(posFin, pion);
        
        // update new blocking state
        if( leftBefore ) {
            if( checkLinkedLeft(posFin, etat) == false) {
                etat.zeJoueurs[zeJoueur.couleur].nbBloqueur -=1;
            }
        }
        else {
            if( checkLinkedLeft(posFin, etat) == true) {
                etat.zeJoueurs[zeJoueur.couleur].nbBloqueur +=1;
            }
        }
        if( rightBefore ) {
            if( checkLinkedRight(posFin, etat) == false) {
                etat.zeJoueurs[zeJoueur.couleur].nbBloqueur -=1;
            }
        }
        else {
            if( checkLinkedRight(posFin, etat) == true) {
                etat.zeJoueurs[zeJoueur.couleur].nbBloqueur +=1;
            }
        }
        
        if( contactBefore ) {
            if( checkContact(posFin, etat) == false ) {
                etat.zeJoueurs[zeJoueur.couleur].nbContact -=1;
                etat.zeJoueurs[Joueur.otherColor(zeJoueur.couleur)].nbContact -=1;
            }
        }
        else {
            if( checkContact(posFin, etat) == true ) {
                etat.zeJoueurs[zeJoueur.couleur].nbContact +=1;
                etat.zeJoueurs[Joueur.otherColor(zeJoueur.couleur)].nbContact +=1;
            }
        }
        //return etat;
        // joueur courant gagne si il est contact avec une ligne blocante
        for (Joueur player : etat.zeJoueurs) {
			if( (etat.zeJoueurs[player.couleur].nbContact >= 3 ) 
				&& (etat.zeJoueurs[Joueur.otherColor(player.couleur)].nbBloqueur == Plateau.tailleC)) {
				etat.setWinner(player.couleur);
			}
		}
        
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
    boolean checkLinkedLeft(PositionGrid2D pos, EtatJeu etat)
    {
    	if( pos.x > 0 ) {
            for (PositionGrid2D dir : leftSide) {
                PositionGrid2D checkPos = pos.add(dir);
                if( etat.zePlateau.isValidPosition(checkPos)) {
                    if( etat.zePlateau.isCaseEmpty(checkPos) == false) {
                        if( etat.zePlateau.getCase(checkPos).m_joueur.sameColor(zeJoueur)) {
                            return true;
                        }
                    }
                }
            }
        }
        else {
            return true;
        }
        return false;
    }
    boolean checkLinkedRight( PositionGrid2D pos, EtatJeu etat )
    {

        if( pos.x < Plateau.tailleC-1 ) {
            for (PositionGrid2D dir : rightSide) {
                PositionGrid2D checkPos = pos.add(dir);
                if( etat.zePlateau.isValidPosition(checkPos)) {
                    if( etat.zePlateau.isCaseEmpty(checkPos) == false) {
                        if( etat.zePlateau.getCase(checkPos).m_joueur.sameColor(zeJoueur)) {
                            return true;
                        }
                    }
                }
            }
        }
        else {
            return true;
        }
        return false;
    }
	
    /**
     * Check for contact UP or DOWN according to zeJoueur.couleur.
     * @param pos
     * @param etat
     * @return
     */
    boolean checkContact(PositionGrid2D pos, EtatJeu etat )
    {
        // suivant couleur, si ennemi devant ou derri�re
        if( zeJoueur.couleur == Joueur.rouge ) {
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
