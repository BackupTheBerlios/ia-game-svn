/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.cartes;

import java.util.ListIterator;

import jeu.shazamm.core.Carte;
import jeu.shazamm.core.EtatJeu;
import jeu.shazamm.core.Constantes;

/**
 * On peut voler ou détruire des sorts.
 * @author dutech
 */
public class Larcin extends Carte {

    /**
     * @param p_coul de la carte.
     */
    public Larcin(int p_coul)
    {
        super( 3, "Lar", p_coul, Constantes.NO_COLOR);
    }

    /**
     * Aucun effet si pas de sort à voler/détruire, sinon le joueur devra indiquer
     * quel sort il vole (les autres seront détruits.
     * Il entre dans l'état DOIT_VOLER.
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu,jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {	
        
        // L'autre a joué des cartes qui doivent faire effet ?
        boolean isAbleLarcin = false;
        int otherCoul = etat. getOtherJoueur(beneficiaire).coul;
        for (ListIterator itCarte = etat.cartesJouees.listIterator(); itCarte.hasNext();) {
            Carte currCarte = (Carte) itCarte.next();
            if( currCarte.beneficiaire == otherCoul ) {
                isAbleLarcin = true;
                break;
            }
        }
        if( isAbleLarcin == false ) {
            // il ne se passe rien
            System.out.println( "Larcin sans effet!"); // DEBUG
            return true;
        }
        etat.getJoueur(beneficiaire).etat = Constantes.DOIT_VOLER;
        return false;
    }

}
