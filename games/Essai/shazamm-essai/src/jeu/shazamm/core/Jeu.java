/*
 * Created on Apr 9, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

/**
 * @author dutech
 */
public class Jeu {
    
    /**
     * Creation avec rien de spécial.
     *
     */
    public Jeu()
    {
    }
    
    /**
     * L'EtatJeu initial.
     * @return
     */
    public EtatJeu getInitState()
    {
        return new EtatJeu();
    }
    
    /**
     * Ne s'intéresse qu'aux points de Mana.
     * @param etat
     * @return nouvel EtatJeu
     */
    public EtatJeu update( EtatJeu etat)
    {
        
        boolean manaVide = false;
        
        // fait se déplacer le mur.
        if( etat.getJoueur( Plateau.ROUGE ).mana.strikeForce >
                etat.getJoueur( Plateau.VERT).mana.strikeForce ) {
            etat.zePlateau.moveFire( Plateau.VERS_VERT);
        }
        else if( etat.getJoueur( Plateau.ROUGE ).mana.strikeForce <
                etat.getJoueur( Plateau.VERT).mana.strikeForce ) {
            etat.zePlateau.moveFire( Plateau.VERS_ROUGE);
        }
        
        // on met à jour le Mana
        for( int i=0; i < etat.zeJoueurs.length; i++) {
            manaVide = manaVide || etat.zeJoueurs[i].mana.update();
        }
        
        // si le Mana est vide
        if( etat.getJoueur( Plateau.ROUGE ).mana.manaAfter == 0 ) {
            while( etat.getJoueur( Plateau.VERT).mana.manaAfter > 0 ) {
                etat.zePlateau.moveFire( Plateau.VERS_ROUGE);
                etat.getJoueur( Plateau.VERT ).mana.manaAfter--;
            }
        }
        else if( etat.getJoueur( Plateau.VERT ).mana.manaAfter == 0 ) {
            while( etat.getJoueur( Plateau.ROUGE).mana.manaAfter > 0 ) {
                etat.zePlateau.moveFire( Plateau.VERS_VERT);
                etat.getJoueur( Plateau.ROUGE ).mana.manaAfter--;
            }
        }
        // le nouvel EtatJeu
        EtatJeu result = new EtatJeu( etat );
        for( int i=0; i < result.zeJoueurs.length; i++) {
            result.zeJoueurs[i].mana.prepareNewBet();
        }
        return result;
    }

}
