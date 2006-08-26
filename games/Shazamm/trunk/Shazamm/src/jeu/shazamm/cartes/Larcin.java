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
 * On peut voler ou d�truire des sorts.
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
     * Aucun effet si pas de sort � voler/d�truire, sinon le joueur devra indiquer
     * quel sort il vole (les autres seront d�truits.
     * Il entre dans l'�tat DOIT_VOLER.
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu,jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {	
        
        if( (etat.getOtherJoueur(beneficiaire).cartesMisees.isEmpty()) ||
            (etat.cartesJouees.isEmpty())   ) {
            // il ne se passe rien
            System.out.println( "Larcin sans effet!"); // DEBUG
            return true;
        }
        etat.getJoueur(beneficiaire).etat = Constantes.DOIT_VOLER;
        return false;
    }

}
