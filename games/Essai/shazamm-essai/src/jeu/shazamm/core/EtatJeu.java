/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import java.util.Arrays;

/**
 * @author dutech
 */
public class EtatJeu {
    
    public Joueur zeJoueurs[];
    public Plateau zePlateau;
    
    /// autorise-t-on les Sorts (Mutisme)?
    public boolean autoriseSort;
    
    /**
     * Creation des Joueurs et du Plateau.
     */
    public EtatJeu()
    {
        zeJoueurs = new Joueur[2];
        zeJoueurs[0] = new Joueur( 50, Plateau.ROUGE );
        zeJoueurs[1] = new Joueur( 50, Plateau.VERT );
        
        zePlateau = new Plateau(19);
        
        // les divers flags
        autoriseSort = true;
    }
    /**
     * Clone.
     */
    public EtatJeu( EtatJeu p_etat )
    {
        zeJoueurs = new Joueur[p_etat.zeJoueurs.length];
        for( int i=0; i < zeJoueurs.length; i++ ) {
            zeJoueurs[i] = new Joueur( p_etat.zeJoueurs[i]);
        }
        
        zePlateau = new Plateau( p_etat.zePlateau );
        
        autoriseSort = p_etat.autoriseSort;
    }
    
    /**
     * Egalité.
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof EtatJeu) {
            EtatJeu other = (EtatJeu) obj;
            boolean result = (Arrays.equals( zeJoueurs, other.zeJoueurs)) &&
            (zePlateau.equals( other.zePlateau ));
            return result;
        }
        return false;
    }
    
    /**
     * Trouve un joueur en fonction de sa couleur.
     * @param couleur
     * @return Joueur ou null si pas bonne couleur.
     */
    public Joueur getJoueur( int couleur )
    {
        if( couleur == Plateau.ROUGE ) {
            return zeJoueurs[0];
        }
        else if( couleur == Plateau.VERT ) {
            return zeJoueurs[1];
        }
        return null;
    }
    
    /**
     * Classique.
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( zePlateau.toString()+"\n");
        strbuf.append( "ROUGE : " + zeJoueurs[0].toString() + "\n");
        strbuf.append( "VERT : " + zeJoueurs[1].toString() + "\n");
        return strbuf.toString();
    }

}
