/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.cartes;

import jeu.shazamm.core.Carte;
import jeu.shazamm.core.Constantes;
import jeu.shazamm.core.EtatJeu;;

/**
 * Le mur ne bougera pas... Sauf sous l'effet d'un sort.
 * @author dutech
 */
public class Resistance extends Carte {

    /**
     * @param p_coul
     */
    public Resistance( int p_coul)
    {
        super( 11, "Res", p_coul, Constantes.NO_COLOR);
    }

    /**
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu, jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {
        etat.resistPlayed = beneficiaire;
        return true;
    }

}
