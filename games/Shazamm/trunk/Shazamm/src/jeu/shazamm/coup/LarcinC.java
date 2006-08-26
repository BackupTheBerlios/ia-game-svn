/*
 * Created on Nov 19, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.coup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import jeu.shazamm.core.Carte;
import jeu.shazamm.core.Constantes;
import jeu.shazamm.core.Coup;
import jeu.shazamm.core.EtatJeu;
import jeu.shazamm.core.Jeu;
import jeu.shazamm.core.Joueur;
import jeu.utils.ExtractException;
import jeu.utils.GameException;

/**
 * @author dutech
 */
public class LarcinC extends Coup {

    public int coulJoueur;
    public int sorts[]; // volés
    
    /**
     * Un larcin est constitué d'une liste de sorts volés.
     * Les autres sorts sont détruits.
     * Il est représenté et stockée sous la forme :  
     * j:v([xx])	
     */
    public LarcinC()
    {
        super();
    }
    /**
     * Crée avec un/des sorts volés.
     * @param p_coul
     * @param p_sorts Liste de priorité de sorts.
     */
    public LarcinC( int p_coul, List p_sorts )
    {
        coulJoueur = p_coul;
        sorts = null;
        if (p_sorts != null ) {
            sorts = new int[p_sorts.size()];
            int i = 0;
            for (Iterator iSort = p_sorts.iterator(); iSort.hasNext(); i++) {
                Integer sort_priority = (Integer) iSort.next();
                sorts[i] = sort_priority.intValue();
            }
        }
    } 
    /**
     * Une copie conforme.
     */
    public Coup copy()
    {
        LarcinC result = new LarcinC();
        result.coulJoueur = coulJoueur;
        if( sorts != null ) {
            result.sorts = (int[]) sorts.clone();
        }
        
        return result;
    }

    /**
     * Remet tout à zéro
     */
    public void reset()
    {
        coulJoueur = Constantes.VIDE;;
        sorts = null;    }

    /** 
     * Vol les sorts volé (ajout) et détruits les autres...
     * Puis, le joueur attend de nouveau.
     */
    public void apply(Jeu p_jeu, EtatJeu p_etat) 
    throws GameException
    {
        ArrayList aDetruire = new ArrayList();
        
        Joueur zeJoueur = p_etat.getJoueur( coulJoueur );
        if( zeJoueur.etat == Constantes.DOIT_VOLER ) {
            // parcourt les sorts joués qui sont à l'adversaire
            int otherCoul = p_etat.getOtherJoueur( zeJoueur).coul;
            
            for (ListIterator itCarte = p_etat.cartesJouees.listIterator(); itCarte.hasNext();) {
                Carte currCarte = (Carte) itCarte.next();
                if( currCarte.beneficiaire == otherCoul ) {
                    // a priori on détruit
   
                    // change la couleur de son beneficiaire si vole, sinon détruit
                    boolean volFound = false;
                    if( sorts.length > 0 ) {
                        for (int iVol = 0; iVol < sorts.length; iVol++) {
                            if( sorts[iVol] == currCarte.priority) {
                                volFound = true;
                                // on vole
                                System.out.println( "Vol de "+currCarte.toString()); // DEBUG
                                currCarte.beneficiaire = zeJoueur.coul;
                                zeJoueur.cartesMisees.add( currCarte );
                                itCarte.set(currCarte);
                            }
                        }
                    }
                    if( volFound == false ) {
                        // pas trouvé => détruit
                        itCarte.remove();
                    }
                }
            }
            
            
            // change le statut du joueur
            zeJoueur.etat = Constantes.DOIT_ATTENDRE;
        }
        else {
            throw new GameException( Constantes.strCoul(zeJoueur.coul)+" ne peut ni voler ni détruire!");
        }
    }

    /**
     * Extrait le coup en vérifiant que le code du coup est le bon.
     * @param buff
     */
    public void extractFrom(String buff) 
    throws ExtractException
    {
        StringTokenizer st = new StringTokenizer( buff, ":(,+ \t\n\r\f");
	    String token;
	    
	    // joueur
	    token = st.nextToken();
	    coulJoueur = Constantes.decodeCoul( token );
	    if( coulJoueur == Constantes.NO_COLOR ) {
	        throw new ExtractException(">"+token+"< n'est pas une couleur valide!");
	    }
	   
	    // type de coup
	    token = st.nextToken();
	    if( !token. equals("v") ) {
	        // c'est pas un vol
	        throw new ExtractException(">"+token+"< n'annonce pas un larcin!");
	    }
	    
	    // mise
	    sorts = extractListFrom( st );
    }

    /**
     * Format = "j:v([xx,yy]).
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        
        strbuf.append( Constantes.strCoul( coulJoueur ) + ":");
        strbuf.append( "v(");
        if( sorts != null ) {
            for (int i = 0; i < sorts.length; i++) {
                strbuf.append( sorts[i]+", ");
            }
        }
        strbuf.append(")");
        return strbuf.toString();
    }
}
