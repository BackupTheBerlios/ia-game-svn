/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import java.lang.ClassCastException;

/**
 * Les cartes qui modifient les mises.
 * @author dutech
 */
abstract public class Carte implements Comparable {

    
    public int priority;
    public String shortName;
    public int couleur;
    
    public Carte( int p_priority, String p_short, int p_coul)
    {
        priority = p_priority;
        shortName = p_short;
        couleur = p_coul;
    }
    
    /**
     * Compare deux cartes.
     * Attention, compare == 0 ne veut pas dire equals. La couleur entre
     * en jeu!
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object arg0)
    {
        if (arg0 instanceof Carte) {
            Carte autre = (Carte) arg0;
            return Integer.signum( priority - autre.priority); 
        }
        throw new ClassCastException();
    }
    
    /**
     * Effet d'une Carte jouée sur EtatJeu.
     * @param etat Le Jeu courant
     * @param beneficiaire Le Joueur qui en bénéficie
     */
    abstract public void effet( EtatJeu etat, Joueur benificiaire);
    
    /**
     * Classique.
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( Plateau.strCoul(couleur) + "-" + priority+"-"+ shortName);
        return strbuf.toString();
    }
}
