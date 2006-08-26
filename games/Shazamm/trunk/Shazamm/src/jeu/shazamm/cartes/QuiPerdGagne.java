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
 * C'est celui qui pensait gagner qui va perdre.
 * @author dutech
 */
public class QuiPerdGagne extends Carte {

    /**
     * @param p_coul
     */
    public QuiPerdGagne( int p_coul)
    {
        super( 9, "QPG", p_coul, Constantes.NO_COLOR);
    }

    /**
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu, jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {
        etat.qpgPlayed = true;
        return true;
    }

}
