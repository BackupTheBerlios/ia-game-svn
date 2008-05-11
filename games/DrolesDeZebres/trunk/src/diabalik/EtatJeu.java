/*
 * Created on Jan 1, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

/**
 * Rassemble toutes les donn�es du Jeu. Fait essentiellement pour �tre 
 * "l�ger" et ne pas prendre trop de place en m�moire.
 * @author dutech
 */
public class EtatJeu extends Observable 
{
    public Plateau zePlateau;
	public Joueur[] zeJoueurs;
	private Mouvement dernierMvt;
	private int tour;
	private int nbMvtLeft;
	private boolean finJeu;
	public ArrayList<Mouvement> bestMoves; // meilleurs Mvt � faire
	private boolean valide;
	public String errMsg;
	
	/**
	 * Le premier Player est à spécifier.
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
		
		super.setChanged();
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
	    super.setChanged();
	}
	
	/**
	 * Tout est remis � z�ro (sauf les Joueurs).
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
		super.setChanged();
	}
	
	/**
	 * Si ils sont tous les deux valides, on ne regarde que l'�tat,
	 * pas le dernier Mouvement.
     * @return true si tous les membres sont les m�mes
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
	/** (non-Javadoc)
	 * @see java.util.Observable#hasChanged()
	 */
	@Override
	public synchronized boolean hasChanged()
	{
		if( super.hasChanged() ) {
			return super.hasChanged();
		}
		else {
			//TODO return avec BestMoves ou Joueur ???
			return zePlateau.hasChanged();
		}
	}
	@Override
	protected synchronized void setChanged()
	{
		super.setChanged();
	}
	public Mouvement getLastMove()
	{
		return dernierMvt;
	}
	public void setLastMove( Mouvement mvt)
	{
		dernierMvt = mvt;
		super.setChanged();
	}
	public int getTurn()
	{
		return tour;
	}
	public void setTurn(int tour)
	{
		this.tour = tour;
		super.setChanged();
	}
	public int getNbMvtLeft()
	{
		return nbMvtLeft;
	}
	public void setNbMvtLeft(int nbMvtLeft)
	{
		this.nbMvtLeft = nbMvtLeft;
		super.setChanged();
	}
	public boolean isValid()
	{
		return valide;
	}
	public void setValid(boolean valide)
	{
		this.valide = valide;
		super.setChanged();
	}
	
    
}
