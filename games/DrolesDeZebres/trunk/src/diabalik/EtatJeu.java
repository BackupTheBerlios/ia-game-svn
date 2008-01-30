/*
 * Created on Jan 1, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Rassemble toutes les données du Jeu. Fait essentiellement pour être 
 * "léger" et ne pas prendre trop de place en mémoire.
 * @author dutech
 */
public class EtatJeu {
    public Plateau zePlateau;
	public Joueur[] zeJoueurs;
	public Mouvement dernierMvt;
	public int tour;
	public int nbMvtLeft;
	public boolean finJeu;
	public ArrayList<Mouvement> bestMoves; // meilleurs Mvt à faire
	public boolean valide;
	public String errMsg;
	
	/**
	 * Le premier Joueur est à spécifier.
	 * Attention, les Joueurs ne sont pas créés!
	 * (comment faire autrement).
	 */
	public EtatJeu()
	{
	    zePlateau = new Plateau();
		dernierMvt = new Mouvement();
		tour = -1;
		nbMvtLeft = 3;
		finJeu = false;
		
		zeJoueurs = null;
		bestMoves = new ArrayList<Mouvement>();
		
		valide = true;
		errMsg = null;
	}
	public EtatJeu( EtatJeu zeEtat) 
	{
	    zePlateau = new Plateau( zeEtat.zePlateau);
	    dernierMvt = new Mouvement ( zeEtat.dernierMvt);
	    tour = zeEtat.tour;
	    nbMvtLeft = zeEtat.nbMvtLeft;
	    finJeu = zeEtat.finJeu;
	    
	    if( zeEtat.zeJoueurs != null ) {
	        zeJoueurs = new Joueur[zeEtat.zeJoueurs.length];
	        for( int i=0; i < zeEtat.zeJoueurs.length; i++ ) {
	            zeJoueurs[i] = new Joueur( zeEtat.zeJoueurs[i]);
	        }
	    }
	    
	    bestMoves = new ArrayList<Mouvement>( zeEtat.bestMoves );
	    
	    valide = zeEtat.valide;
	    if( zeEtat.errMsg != null ) {
	        errMsg = new String(zeEtat.errMsg);
	    }
	    else {
	        errMsg = null;
	    }
	}
	
	/**
	 * Tout est remis à zéro (sauf les Joueurs).
	 */
	public void reset()
	{
	    zePlateau = new Plateau();
		dernierMvt = new Mouvement();
		
		tour = -1;
		nbMvtLeft = 3;
		finJeu = false;
		
		bestMoves = new ArrayList<Mouvement>();
		
		valide = true;
		errMsg = null;
	}
	
	/**
	 * Si ils sont tous les deux valides, on ne regarde que l'état,
	 * pas le dernier Mouvement.
     * @return true si tous les membres sont les mêmes
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof EtatJeu) {
            EtatJeu etat= (EtatJeu)obj;
            if( valide != etat.valide ) {
                return false;
            }
            if( valide == false ) {
                return errMsg.equals( etat.errMsg );
            }
            boolean result = (tour == etat.tour) 
            	&& (nbMvtLeft == etat.nbMvtLeft)
                && (finJeu == etat.finJeu);
            if( zePlateau == null ) {
                if( etat.zePlateau != null ) return false;
            }
            else {
                result = (result && zePlateau.equals( etat.zePlateau ));
            }
            if( zeJoueurs == null ) {
                if( etat.zeJoueurs != null ) return false;
            }
            else {
                result = (result && Arrays.equals( zeJoueurs, etat.zeJoueurs ));
            }
//            if( dernierMvt == null ) {
//                if( etat.dernierMvt != null ) return false;
//            }
//            else {
//                result = (result && dernierMvt.equals( etat.dernierMvt ));
//            }
            if( result ) return true;
        }
        return false;
    }
	public String displayBestMoves()
	{
	    StringBuffer strbuf = new StringBuffer();
	    strbuf.append( "best = {");
	    for (Mouvement tmpMvt : bestMoves) {
            strbuf.append( tmpMvt.toString()+"; ");
        }
	    strbuf.append( "}\n");
	    return strbuf.toString();
	}
	
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( dernierMvt.toString());
        strbuf.append("\n");
        if( tour >= 0 ) {
            strbuf.append( " ["+zeJoueurs[tour].toString()+ " doit jouer]");
        }
        else {
            strbuf.append( " [personne ne doit jouer]");
        }
		strbuf.append("\n Il reste "+nbMvtLeft+" mouvement(s)\n");
		if( valide == false ) {
            strbuf.append( "\n *** POSITION NON VALIDE : ");
            strbuf.append( errMsg+'\n' );
        }
		
		strbuf.append( zePlateau.toString());
		
		//strbuf.append( "derJ = " + dernierMvt.zeJoueur.couleur + " "+ dernierMvt.zeJoueur.displayReserve()+"\n"); // debug
		
        return strbuf.toString();
    }
    
}
