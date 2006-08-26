/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.cartes;

import jeu.shazamm.core.Carte;
import jeu.shazamm.core.Constantes;
import jeu.shazamm.core.EtatJeu;

/**
 * Ne perd du mana que si le mur avance vers le joueur.
 * @author dutech
 */
public class Harpagon extends Carte {
    
    /**
     * @param p_coul de la carte.
     */
    public Harpagon(int p_coul)
    {
        super( 12, "Har", p_coul, Constantes.NO_COLOR);
    }


    /**
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu,jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {
        if( etat.dirMove() == etat.getJoueur(beneficiaire).coul ) {
            etat.getJoueur(beneficiaire).mana.manaSpent = 0;
        }
        return true;
    }

}
