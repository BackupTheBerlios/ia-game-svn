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
 * Si on se déplace, on le fait de 2 cases.
 * @author dutech
 */
public class Brasier extends Carte {

    /**
     * @param p_coul
     */
    public Brasier( int p_coul)
    {
        super( 10, "Bra", p_coul, Constantes.NO_COLOR);
    }

    /**
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu, jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {
        etat.brasierPlayed = true;
        return true;
    }

}
