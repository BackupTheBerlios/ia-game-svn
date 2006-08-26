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
 * Mutisme : on ne peut plus jeter de sorts.
 * @author dutech
 */
public class Mutisme extends Carte {

    /**
     * @param p_coul de la carte.
     */
    public Mutisme(int p_coul)
    {
        super( 1, "Mut", p_coul, Constantes.NO_COLOR);
    }

    /**
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu,jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {
       etat.autoriseSort = false;
       return true;
    }

}
