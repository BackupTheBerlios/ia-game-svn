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
 * Double la force d'attaque.
 * @author dutech
 */
public class DoubleDose extends Carte {
    
    /**
     * @param p_coul de la carte.
     */
    public DoubleDose(int p_coul)
    {
        super( 8, "x2", p_coul, Constantes.NO_COLOR);
    }


    /**
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu,jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {
        etat.getJoueur(beneficiaire).mana.strikeForce *= 2;
        return true;
    }

}
