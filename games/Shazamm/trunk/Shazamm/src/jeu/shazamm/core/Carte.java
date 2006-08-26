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
    public int beneficiaire; // couleur du joueur qui en bénéficie
    
    /**
     * Cree une carte avec sa priorité, son nom court et une couleur.
     */
    public Carte( int p_priority, String p_short, int p_coul, int p_benef)
    {
        priority = p_priority;
        shortName = p_short;
        couleur = p_coul;
        beneficiaire = p_benef;
    }
    
    /**
     * Compare deux cartes.
     * Attention, compare == 0 ne veut pas dire equals. La couleur et le
     * bénéficiaire entrent en jeu!
     * @return (priority - arg0.priority)
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
     * @return true si on peut passer à la carte suivante, ou false
     * si le Joueur doit donner un complément d'information.
     */
    abstract public boolean effet( EtatJeu etat );
    
    /**
     * Classique.
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( Constantes.strCoul(couleur) + "["+Constantes.strCoul(beneficiaire)
                +"]-" + priority+"-"+ shortName);
        return strbuf.toString();
    }
}
