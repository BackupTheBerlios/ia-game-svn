/*
 * Created on Jan 1, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

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
	private Mouvement lastMvt;
	private int tour;
	private int nbDepLeft;
	private int nbPassLeft;
	private int winner;
	private boolean valide;
	public String errMsg;
    
    // Divers
//  public ArrayList<Mouvement> bestMoves; // meilleurs Mvt � faire
    
	/**
	 * Le premier Player est à spécifier.
	 * Attention, les Joueurs ne sont pas créés!
	 * (comment faire autrement).
	 */
	public EtatJeu()
	{
	    zePlateau = new Plateau();
		lastMvt = new Mouvement();
		tour = -1;
		nbDepLeft = 2;
		nbPassLeft = 1;
		winner = -1;
		
		zeJoueurs = null;
		//bestMoves = new ArrayList<Mouvement>();
 
		valide = true;
		errMsg = null;
		
		super.setChanged();
	}
	public EtatJeu( EtatJeu zeEtat) 
	{
        copy( zeEtat );
	}
    public void copy( EtatJeu etat)
    {
        zePlateau = new Plateau( etat.zePlateau);
        lastMvt = new Mouvement ( etat.lastMvt);
        tour = etat.tour;
        nbDepLeft = etat.nbDepLeft;
        nbPassLeft = etat.nbPassLeft;
        winner = etat.winner;
        
        if( etat.zeJoueurs != null ) {
            zeJoueurs = new Joueur[etat.zeJoueurs.length];
            for( int i=0; i < etat.zeJoueurs.length; i++ ) {
                zeJoueurs[i] = new Joueur( etat.zeJoueurs[i]);
            }
            
        }
        
        //bestMoves = new ArrayList<Mouvement>( etat.bestMoves );
        
        valide = etat.valide;
        if( etat.errMsg != null ) {
            errMsg = new String(etat.errMsg);
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
		lastMvt = new Mouvement();
		
		tour = -1;
		nbDepLeft = 2;
		nbPassLeft = 1;
		winner = -1;
		
		//bestMoves = new ArrayList<Mouvement>();
		
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
            	&& (nbDepLeft == etat.nbDepLeft)
            	&& (nbPassLeft == etat.nbPassLeft)
                && (winner == winner);
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
//	public String displayBestMoves()
//	{
//	    StringBuffer strbuf = new StringBuffer();
//	    strbuf.append( "best = {");
//	    for (Mouvement tmpMvt : bestMoves) {
//            strbuf.append( tmpMvt.toString()+"; ");
//        }
//	    strbuf.append( "}\n");
//	    return strbuf.toString();
//	}
	
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( lastMvt.toString());
        strbuf.append("\n");
        if( tour >= 0 ) {
            strbuf.append( " ["+zeJoueurs[tour].toString()+ " doit jouer]");
        }
        else {
            strbuf.append( " [personne ne doit jouer]");
        }
		strbuf.append("\n Il reste "+nbDepLeft+" mouvement(s) et "+nbPassLeft+" passe\n");
		if( valide == false ) {
            strbuf.append( "\n *** POSITION NON VALIDE : ");
            strbuf.append( errMsg+'\n' );
        }
		
		strbuf.append( zePlateau.toString());
		
		//strbuf.append( "derJ = " + dernierMvt.zeJoueur.couleur + " "+ dernierMvt.zeJoueur.displayReserve()+"\n"); // debug
		
        //strbuf.append("Bloquant ? \n");
        for (Joueur player : zeJoueurs) {
            strbuf.append(player.debugString()+"\n");
        }
        if( winner < 0 ) {
        	strbuf.append( "No Winner\n");
        }
        else {
        	strbuf.append( "Winner = "+Joueur.toString(winner));
        }
        
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
		return lastMvt;
	}
	public void setLastMove( Mouvement mvt)
	{
		lastMvt = mvt;
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
	public int getNbDepLeft()
	{
		return nbDepLeft;
	}
	public int getNbPassLeft()
	{
		return nbPassLeft;
	}
	public void setNbDepLeft(int nbDepLeft)
	{
		this.nbDepLeft = nbDepLeft;
		super.setChanged();
	}
	public void setNbPassLeft(int nbPassLeft)
	{
		this.nbPassLeft = nbPassLeft;
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
	
    public void setWinner(int couleur)
    {
        winner = couleur;
    }
    public int getWinner()
    {
    	return winner;
    }
    public boolean isEndGame()
    {
        return winner >= 0;
    }
}
