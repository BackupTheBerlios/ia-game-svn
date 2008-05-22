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
 * Passer essaie de faire une passe entre un 'passeur' et un 'coureur'.
 *
 * @author dutech
 */
public class PasserMove extends Mouvement {
	
	
	public PasserMove()
	{
		super();
	}
	public PasserMove( Joueur p_joueur, PositionGrid2D pStart, PositionGrid2D pEnd ) 
	{
        super();
		set( p_joueur, pass, pStart, pEnd);
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
        
//      il existe un pion passeur dans la case de départ
        Piece pionStart = etat.zePlateau.getCase(posDebut);
        if( pionStart == null ) {
            throw new MoveException("Pas de pion pour passer");
        }
        if( pionStart.m_joueur.sameColor(zeJoueur) == false ) {
            throw new MoveException("Pion de départ n'est pas de la bonne couleur");
        }
        if( pionStart.type != Piece.passeur) {
            throw new MoveException("Le pion de départ n'est pas un passeur");
        }
        
        // il existe un pion coureur dans la case d'arriv�e
        Piece pionEnd = etat.zePlateau.getCase(posFin);
        if( pionEnd == null ) {
            throw new MoveException("Pas de pion pour réceptionner");
        }
        if( pionEnd.m_joueur.sameColor(zeJoueur) == false ) {
            throw new MoveException("Pion d'arrivée n'est pas de la bonne couleur");
        }
        if( pionEnd.type != Piece.coureur) {
            throw new MoveException("Le pion d'arrivée n'est pas un coureur");
        }
        
        if( posDebut.isAligned(posFin) == false) {
            throw new MoveException("Les cases ne sont pas align�es");
        }
        PositionGrid2D dir = posDebut.getDirectionTo(posFin);
        PositionGrid2D place = posDebut.add(dir);
        while( place.equals(posFin) == false ) {
            if( etat.zePlateau.getCase(place) != null ) {
                if( etat.zePlateau.getCase(place).m_joueur.sameColor(zeJoueur) == false) {
                    System.err.println("case interm�diaire : "+place.toString() + " posFin : "+ posFin.toString());
                    throw new MoveException("Case interm�diaire pas libre");
                }
            }
            place = place.add(dir);
        }
        
        //pionStart.type = Piece.coureur;
        etat.zePlateau.setCase(posDebut, pionEnd);
        //pionEnd.type = Piece.passeur;
        etat.zePlateau.setCase(posFin, pionStart);
        if( isVictory(posFin, zeJoueur)) {
            etat.setWinner(zeJoueur.couleur);
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
     * Check for game end for Joueur
     */
	private boolean isVictory(PositionGrid2D passEnding, Joueur joueur)
    {
        if ((joueur.couleur == Joueur.rouge) && (passEnding.y == 0)) {
            return true;
        }
        else if ((joueur.couleur == Joueur.jaune) && (passEnding.y == Plateau.tailleL-1)) {
            return true;
        }
        return false;
    }
}
