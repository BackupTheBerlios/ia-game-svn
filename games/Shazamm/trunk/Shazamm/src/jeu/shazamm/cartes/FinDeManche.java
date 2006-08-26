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
 * FinDeManche : on replace les mages et on fait une nouvelle manche.
 * @author dutech
 */
public class FinDeManche extends Carte {

    /**
     * @param p_coul de la carte.
     */
    public FinDeManche(int p_coul)
    {
        super( 4, "FDM", p_coul, Constantes.NO_COLOR);
    }

    /**
     * @see jeu.shazamm.core.Carte#effet(jeu.shazamm.core.EtatJeu,jeu.shazamm.core.Joueur)
     */
    public boolean effet(EtatJeu etat)
    {
       // replace les mages
       int perdant = etat.zePlateau.update();
       // on met à jour le Mana
       for( int i=0; i < etat.zeJoueurs.length; i++) {
           etat.zeJoueurs[i].mana.update();
       }
       if( perdant != Constantes.NO_COLOR ) {
           etat.getJoueur( perdant ).etat = Constantes.PERDU;
           etat.getOtherJoueur( perdant ).etat = Constantes.GAGNE;
       }
       else {
           etat.prepareForNewManche( 50, 3 );
       }
       return false;
    }

}
