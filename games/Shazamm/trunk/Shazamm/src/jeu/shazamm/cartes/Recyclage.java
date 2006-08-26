/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.cartes;

import jeu.shazamm.core.Carte;
import jeu.shazamm.core.EtatJeu;
import jeu.shazamm.core.Constantes;

/**
 * Possible d'altérer sa mise de plus ou moins 5 points.
 * @author dutech
 */
public class Recyclage extends Carte {

    /**
     * @param p_coul de la carte.
     */
    public Recyclage(int p_coul)
    {
        super( 6, "Rec", p_coul, Constantes.NO_COLOR);
    }

    /**
     * Le joueur peut recycler entre -5 et +5.
     * Il entre dans l'état DOIT_RECYCLER.
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu,jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {
       etat.getJoueur(beneficiaire).etat = Constantes.DOIT_RECYCLER;
       return false;
    }

}
