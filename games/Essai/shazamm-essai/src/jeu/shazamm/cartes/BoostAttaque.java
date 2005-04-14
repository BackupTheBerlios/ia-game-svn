/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.cartes;

import jeu.shazamm.core.Carte;
import jeu.shazamm.core.EtatJeu;
import jeu.shazamm.core.Joueur;

/**
 * Augmente la force d'attaque du joueur de 7.
 * @author dutech
 */
public class BoostAttaque extends Carte {
    
    /**
     * @param p_coul de la carte.
     */
    public BoostAttaque(int p_coul)
    {
        super( 7, "BoA", p_coul);
    }


    /**
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu)
     */
    public void effet(EtatJeu etat, Joueur beneficiaire)
    {
        beneficiaire.mana.strikeForce += 7;
    }

}
