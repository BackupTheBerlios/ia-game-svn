/*
 * Created on Jan 1, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Rassemble toutes les données du Jeu. Fait essentiellement pour être 
 * "léger" et ne pas prendre trop de place en mémoire.
 * @author dutech
 */
public class EtatJeu {
    public Plateau zePlateau;
	public Joueur[] zeJoueurs;
	public Mouvement dernierMvt;
	public boolean bonusDonne;
	public int tour;
	public int[] validMoveIndiana = {-1, -1, -1, -1};
	public boolean finJeu;
	public int nbPotential;
	public ArrayList bestMoves; // meilleurs Mvt à faire
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
		bonusDonne = false;
		tour = -1;
		finJeu = false;
		nbPotential = 0;
		
		zeJoueurs = null;
		bestMoves = new ArrayList();
		
		valide = true;
		errMsg = null;
	}
	public EtatJeu( EtatJeu zeEtat) 
	{
	    zePlateau = new Plateau( zeEtat.zePlateau);
	    dernierMvt = new Mouvement ( zeEtat.dernierMvt);
	    bonusDonne = zeEtat.bonusDonne;
	    tour = zeEtat.tour;
	    finJeu = zeEtat.finJeu;
	    nbPotential = zeEtat.nbPotential;
	    
	    if( zeEtat.zeJoueurs != null ) {
	        zeJoueurs = new Joueur[zeEtat.zeJoueurs.length];
	        for( int i=0; i < zeEtat.zeJoueurs.length; i++ ) {
	            zeJoueurs[i] = new Joueur( zeEtat.zeJoueurs[i]);
	        }
	    }
	    for( int i=0; i<4; i++) {
			validMoveIndiana[i] = zeEtat.validMoveIndiana[i];
		}
	    
	    bestMoves = new ArrayList( zeEtat.bestMoves );
	    
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
		bonusDonne = false;
		tour = -1;
		finJeu = false;
		nbPotential = 0;
		
		bestMoves = new ArrayList();
		
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
            boolean result = (bonusDonne == etat.bonusDonne
                && tour == etat.tour  
                && finJeu == etat.finJeu);
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
	    for (Iterator iterMvt = bestMoves.iterator(); iterMvt.hasNext();) {
            Mouvement tmpMvt = (Mouvement) iterMvt.next();
            strbuf.append( tmpMvt.toString()+"; ");
        }
	    strbuf.append( "}\n");
	    return strbuf.toString();
	}
	
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( dernierMvt.toString());
        
        if( tour >= 0 ) {
            strbuf.append( " ["+zeJoueurs[tour].toString()+ " doit jouer]");
        }
        else {
            strbuf.append( " [personne ne doit jouer]");
        }
		strbuf.append( "  "+nbPotential+" move\n");
		
		if( valide == false ) {
            strbuf.append( "\n *** POSITION NON VALIDE : ");
            strbuf.append( errMsg+'\n' );
        }
		
		strbuf.append( zePlateau.toString());
		
		//strbuf.append( "derJ = " + dernierMvt.zeJoueur.couleur + " "+ dernierMvt.zeJoueur.displayReserve()+"\n"); // debug
		
		// présente les valeurs des régions
		strbuf.append( "\n valRegions = ");
		for( int indR = 0; indR < Plateau.nbRegion; indR++ ) {
			if( zePlateau.valRegions[indR][Plateau.indJ] != -1 ) {
				strbuf.append( zeJoueurs[zePlateau.valRegions[indR][Plateau.indJ]].toString());
			}
			else {
				strbuf.append( "-");
			}
			strbuf.append( zePlateau.valRegions[indR][Plateau.indV]+"|");
		}
		strbuf.append( "\n\n");
		// présente les reserves
		strbuf.append( "reserve: Z|G|E|L|C|\n");
		for( int coul=0; coul<2; coul++) {
			strbuf.append( "  -> "+zeJoueurs[coul].toString()+" : "
					+zeJoueurs[coul].displayReserve()
					+"  bonus:" + zeJoueurs[coul].bonus 
					+ "("+zeJoueurs[coul].score+")\n");
		}
        return strbuf.toString();
    }
    
}
