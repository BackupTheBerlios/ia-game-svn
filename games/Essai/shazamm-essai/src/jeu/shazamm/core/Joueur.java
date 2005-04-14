/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;
import jeu.shazamm.cartes.*;

/**
 * Les infos à stocker pour un Joueur.
 * @author dutech
 */
public class Joueur {
    
    public int coul; // couleur
    public Mise mana;
    
    public TreeSet pioche;
    public TreeSet main;
    public TreeSet mainPrecedente;
    
    private Random alea;
    
    /**
     * Sans aucun piège.
     * @param manaStart
     * @param couleur
     */
    public Joueur( int manaStart, int couleur )
    {
        coul = couleur;
        mana = new Mise( manaStart );
        
        pioche = new TreeSet();
        initCartes();
        
        main = new TreeSet();
        
        alea = new Random();
    }
    /**
     * Clone.
     */
    public Joueur( Joueur p_joueur) 
    {
        coul = p_joueur.coul;
        mana = new Mise( p_joueur.mana );
        
        pioche = (TreeSet) p_joueur.pioche.clone();
        main = (TreeSet) p_joueur.main.clone();
        
        alea = p_joueur.alea;
    }

    /** 
     * Remplit la pioche.
     */
    public void initCartes() 
    {
        pioche.add( new Mutisme( coul ));
        pioche.add( new BoostAttaque( coul ));
    }
    /**
     * Pioche une carte au hasard pour l'ajouter à la main.
     * @return false si plus de carte dans la pioche.
     */
    public boolean piocheCarte()
    {
        if( pioche.size() > 0 ) {
            int tirage = alea.nextInt( pioche.size());
            Iterator iterP = pioche.iterator();
            Carte zeCarte = null;
            for( int i = 0; i <= tirage; i++ ) {
                zeCarte = (Carte) iterP.next();
            }
            // ajoute à la main.
            main.add( zeCarte );
            iterP.remove();    
            return true;
        }
        return false;
    }
    
    /**
     * Egalité des mises?.
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Joueur) {
            Joueur other = (Joueur) obj;
            boolean result = (coul == other.coul) &&
            (mana.equals( other.mana));
            // cartes
            result = result && ( main.equals( other.main ));
            result = result && ( pioche.equals( other.pioche ));
            return result;
        }
        return false;
    }
    
    /** 
     * Affiche la mise.
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( mana.toString() );
        
        // main
        strbuf.append( "\nMain = ");
        for (Iterator iterP = main.iterator(); iterP.hasNext();) {
            Carte zeCarte = (Carte) iterP.next();
            strbuf.append( zeCarte.toString()+", ");
        }
        // pioche
        strbuf.append( "\nPioche = ");
        for (Iterator iterP = pioche.iterator(); iterP.hasNext();) {
            Carte zeCarte = (Carte) iterP.next();
            strbuf.append( zeCarte.toString()+", ");
        }
        return strbuf.toString();
    }
}
