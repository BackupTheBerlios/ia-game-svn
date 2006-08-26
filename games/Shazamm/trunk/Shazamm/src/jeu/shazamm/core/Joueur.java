/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

import jeu.shazamm.cartes.Aspiration;
import jeu.shazamm.cartes.BoostAttaque;
import jeu.shazamm.cartes.BoostReserve;
import jeu.shazamm.cartes.Brasier;
import jeu.shazamm.cartes.Clone;
import jeu.shazamm.cartes.DoubleDose;
import jeu.shazamm.cartes.FinDeManche;
import jeu.shazamm.cartes.Harpagon;
import jeu.shazamm.cartes.Larcin;
import jeu.shazamm.cartes.Milieu;
import jeu.shazamm.cartes.Mutisme;
import jeu.shazamm.cartes.QuiPerdGagne;
import jeu.shazamm.cartes.Recyclage;
import jeu.shazamm.cartes.Resistance;

/**
 * Les infos à stocker pour un Joueur.
 * Un joueur peut être dans un des états suivants:<br>
 * <li><code>DOIT_MISER</code> : faire une mise</li>
 * <li><code>DOIT_CLONER</code> : choix parmi une liste de Carte</li>
 * <li><code>DOIT_VOLER</code> : choix mutliples dans une liste de Carte</li>
 * <li><code>DOIT_RECYCLER</code> : changer la valeur du nb de mana</li>
 * <li><code>DOIT_ATTENDRE</code> : attend action de l'autre joueur</li>
 * <li><code>FIN_MISE</code> : la mise est faite, il faut résoudre</li>
 * <li><code>PERDU</code> : fin du jeu</li>
 * <li><code>GAGNE</code> : fin du jeu</li>
 * @author dutech
 */
public class Joueur {
    
    public int coul; // couleur
    public Mana mana; // réserve et gestion de la mana
    public int etat; // un des état décrit dans la doc
    
    public TreeSet pioche;            // Ens. trié de Cartes
    public TreeMap main;              // Coll. triées de (priorité; Carte)
    public ArrayList mainPrecedente;  // List. de Cartes
    public ArrayList cartesMisees;    // List. de Cartes Jouées, clonées ou volées.
    
    private Random alea;
    
    /**
     * Sans aucun piège.
     * @param manaStart
     * @param couleur
     */
    public Joueur( int manaStart, int couleur )
    {
        coul = couleur;
        mana = new Mana( manaStart );
        etat = Constantes.DOIT_MISER;
        
        pioche = new TreeSet();
        initCartes();
        
        main = new TreeMap();
        mainPrecedente = null;
        cartesMisees = new ArrayList();
        cartesMisees.clear();
        
        alea = new Random();
    }
    /**
     * Clone.
     */
    public Joueur( Joueur p_joueur) 
    {
        coul = p_joueur.coul;
        mana = new Mana( p_joueur.mana );
        etat = p_joueur.etat;
        
        pioche = new TreeSet( p_joueur.pioche );
        main = new TreeMap( p_joueur.main );
        if( p_joueur.mainPrecedente != null ) {
            mainPrecedente = new ArrayList( p_joueur.mainPrecedente );
        }
        cartesMisees = new ArrayList( p_joueur.cartesMisees );
        
        alea = p_joueur.alea;
    }

    /** 
     * Remplit la pioche.
     */
    public void initCartes() 
    {
        pioche.add( new Mutisme( coul ));
        pioche.add( new Clone( coul ));
        pioche.add( new Larcin( coul));
        pioche.add( new FinDeManche( coul ));
        pioche.add( new Milieu( coul ));
        pioche.add( new Recyclage( coul));
        pioche.add( new BoostAttaque( coul ));
        pioche.add( new DoubleDose( coul ));
        pioche.add( new QuiPerdGagne( coul ));
        pioche.add( new Brasier( coul ));
        pioche.add( new Resistance( coul ));
        pioche.add( new Harpagon( coul ));
        pioche.add( new BoostReserve( coul ));
        pioche.add( new Aspiration( coul ));
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
            main.put( new Integer(zeCarte.priority), zeCarte );
            iterP.remove();    
            return true;
        }
        return false;
    }

    /**
     * Essaye de miser une carte donnée par sa priorité.
     * @param cartePriority
     * @return false si la carte n'est pas dans la main.
     */
    public boolean joueCarte( int cartePriority )
    {
        Carte zeCarte = (Carte) main.remove( new Integer(cartePriority));
        if( zeCarte != null ) {
            cartesMisees.add( zeCarte );
            return true;
        }
        return false;
    }

    /**
     * Mémorise les cartes jouées précédemment et met à jour la mise.
     */
    public void prepareForNewTour()
    {
        // copie dans la main précédente
        mainPrecedente = new ArrayList( cartesMisees );
        cartesMisees.clear();
        
        mana.prepareNewBet();
        etat = Constantes.DOIT_MISER;
    }
    /**
     * Mémorise les cartes jouées précédemment et met a jour le mana.
     * @param nbPoints
     * @param nbPioche
     */
    public void prepareForNewManche( int nbPoints, int nbPioche )
    {
        // copie dans la main précédente
        mainPrecedente = new ArrayList( cartesMisees );
        cartesMisees.clear();
        
        mana.reset(nbPoints);
        for( int i=0; i<nbPioche; i++ ) {
            piocheCarte();
        }
        etat = Constantes.DOIT_MISER;
    }
    
    /**
     * Egalité des couleurs, des mises et de la pioche?.
     * @todo A-t-on vraiment besoin de cette égalité?
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
     * Affiche la mise, la main, la pioche...
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( mana.toString() );
        
        strbuf.append( "\ncartes misées = ");
        if( cartesMisees != null ) {
            for (Iterator iterC = cartesMisees.iterator(); iterC.hasNext();) {
                Carte zeCarte = (Carte) iterC.next();
                strbuf.append( zeCarte.toString()+", " );
            }
        }
        
        strbuf.append( "\ncartes précédentes = ");
        if( mainPrecedente != null ) {
            for (Iterator iterC = mainPrecedente.iterator(); iterC.hasNext();) {
                Carte zeCarte = (Carte) iterC.next();
                strbuf.append( zeCarte.toString()+", " );
            }
        }
        
        strbuf.append( "\nEtat = " + Constantes.etatStr( etat));
        
        // main
        strbuf.append( "\nMain = ");
        Collection mainOrdonnee = main.values();
        for (Iterator iterP = mainOrdonnee.iterator(); iterP.hasNext();) {
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
