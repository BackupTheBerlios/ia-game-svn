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
 * Place le pont au milieu.
 * @author dutech
 */
public class Milieu extends Carte {

    /**
     * @param p_coul
     */
    public Milieu( int p_coul)
    {
        super( 5, "Mil", p_coul, Constantes.NO_COLOR);
    }

    /**
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu, jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {
        etat.zePlateau.moveFireMiddle();
        return true;
    }

}
