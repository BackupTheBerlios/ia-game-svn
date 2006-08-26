/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.coup;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import jeu.shazamm.core.Constantes;
import jeu.shazamm.core.Coup;
import jeu.shazamm.core.EtatJeu;
import jeu.shazamm.core.Jeu;
import jeu.shazamm.core.Joueur;
import jeu.utils.ExtractException;
import jeu.utils.GameException;

/**
 * Une MiseC est un Coup constitué d'un nb de point et,
 * éventuellement d'un tableau de priorité de Cartes.
 * Elle est représentée et stockée sous la forme :  
 * j:m(xx)[,s(xx, yy, ...)]
 * @author dutech
 */
public class MiseC extends Coup {

    public int coulJoueur;
    public int mise;
    public int sorts[];
    
    /**
     * Crée en faisant un reset. 
     */
    public MiseC()
    {
        super();
    }

    /**
     * Crée avec une mise et des sorts.
     * @param p_coul du Joueur
     * @param p_mise Mana joué
     * @param p_sorts liste des priorité, ou null.
     */
    public MiseC( int p_coul, int p_mise, List p_sorts)
    {
        coulJoueur = p_coul;
        mise = p_mise;
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
     * Remet tout à zéro.
     */
    public void reset()
    {
        coulJoueur = Constantes.VIDE;;
        mise = 0;
        sorts = null;
    }
    /**
     * Une copie conforme.
     */
    public Coup copy()
    {
        MiseC result = new MiseC();
        result.coulJoueur = coulJoueur;
        result.mise = mise;
        if( sorts != null ) {
            result.sorts = (int[]) sorts.clone();
        }
        
        return result;
    }
    /**
     * Applique le Coup au jeu.
     * @param p_jeu
     */
    public void apply( Jeu p_jeu, EtatJeu p_etat )
    throws GameException
    {
        Joueur zeJoueur = p_etat.getJoueur( coulJoueur );
        if( zeJoueur.etat == Constantes.DOIT_MISER ) {
            //prépare la mise
            zeJoueur.mana.setBet( mise );
            
            //Joue les cartes
            if( sorts != null ) {
                for (int i = 0; i < sorts.length; i++) {
                    zeJoueur.joueCarte( sorts[i] );
                }
            }
            p_jeu.addCartesToPlay( p_etat, zeJoueur );
            // change le statut du joueur
            zeJoueur.etat = Constantes.DOIT_ATTENDRE;
        }
        else {
            throw new GameException( Constantes.strCoul(zeJoueur.coul)+" ne peut miser!");
        }
    }
 
    /**
     * Extrait le coup en vérifiant que le code du coup est le bon.
     * @param buff
     */
    public void extractFrom( String buff )
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
	    if( !token. equals("m") ) {
	        // c'est pas une mise
	        throw new ExtractException(">"+token+"< n'annonce pas une mise!");
	    }
	    
	    // mise
	    int nbPoint[] = extractListFrom( st );
	    if( nbPoint.length != 1 ) {
	        throw new ExtractException( "Pas de mise valide!");
	    }
	    mise = nbPoint[0];
	    
	    // cartes
	    if( st.hasMoreTokens() ) {
	        token = st.nextToken();
	        if( !token.equals("s") ) {
	            // c'est pas une liste de sorts
	            throw new ExtractException(">"+token+"< n'annonce pas une liste de sorts!");
	        }
	        sorts = extractListFrom( st );
	    }
    }
    
    /**
     * Format = "j:m(xx)[,s(xx, yy, ...)].
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        
        strbuf.append( Constantes.strCoul( coulJoueur ) + ":");
        strbuf.append( "m("+mise+")" );
        if( sorts != null ) {
            strbuf.append( ",s(");
        
            for (int i = 0; i < sorts.length; i++) {
                strbuf.append( sorts[i]+", ");
            }
            strbuf.append(")");
        }
        return strbuf.toString();
    }
}
