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
 * Aspiration : aspire le mana de l'autre.
 * @author dutech
 */
public class Aspiration extends Carte {
    
    /**
     * @param p_coul de la carte.
     */
    public Aspiration(int p_coul)
    {
        super( 14, "Asp", p_coul, Constantes.NO_COLOR);
    }


    /**
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu,jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {
        etat.getJoueur(beneficiaire).mana.manaSpent -= etat.getOtherJoueur(beneficiaire).mana.manaBet;
        return true;
    }

}
