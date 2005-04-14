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
 * Mutisme : on ne peut plus jeter de sorts.
 * @author dutech
 */
public class Mutisme extends Carte {

    /**
     * @param p_coul de la carte.
     */
    public Mutisme(int p_coul)
    {
        super( 1, "Mut", p_coul);
    }

    /**
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu)
     */
    public void effet(EtatJeu etat, Joueur beneficiaire)
    {
       etat.autoriseSort = false;
    }

}
