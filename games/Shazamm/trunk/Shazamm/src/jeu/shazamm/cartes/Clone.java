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
 * On peut éventuellement cloner des sorts précédents.
 * @author dutech
 */
public class Clone extends Carte {

    /**
     * @param p_coul de la carte.
     */
    public Clone(int p_coul)
    {
        super( 2, "Clo", p_coul, Constantes.NO_COLOR);
    }

    /**
     * Aucun effet si pas de sort à cloner, sinon le joueur devra indiquer
     * quel sort il clone. Il entre dans l'état DOIT_CLONER.
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu,jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {
       if( etat.getOtherJoueur( beneficiaire ).mainPrecedente.isEmpty() ) {
           // il ne se passe rien
           System.out.println( "Clone sans effet!"); // DEBUG
           return true;
       }
       etat.getJoueur(beneficiaire).etat = Constantes.DOIT_CLONER;
       return false;
    }

}
