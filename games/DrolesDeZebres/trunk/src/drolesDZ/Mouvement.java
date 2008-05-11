/*
 * Created on Dec 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Un Mouvement doit permettre de refaire automatiquement le dernier coup.
 * Se compose d'un Player, d'un type de Piece, de la position jou�e,
 * d'un �ventuel switch avec une gazelle et du d�placement d'Indiana.
 * <br><b>Attention</b> : si il y a un echange, echL[0] et echC[0] contiennent 
 * les coordonn�es de d�part de l'�change (pour 1 echanche, nbEchange == 2).
 * @author dutech
 */
public class Mouvement {
    public static int NB_ECH_MAX = 6;
	public Joueur zeJoueur;
	public int zeType;
	public int posL, posC;
	public int nbIndiana;
	public int nbEchange;
	public int echL[], echC[];
	
	public Mouvement()
	{
		init();
	}
	public Mouvement( Joueur p_joueur, int p_type, int p_ligne, int p_col ) 
	{
	    init();
		set( p_joueur, p_type, p_ligne, p_col);
	}
	public Mouvement( Mouvement zeMouvement )
	{
	    init();
	    set( zeMouvement.zeJoueur, zeMouvement.zeType, zeMouvement.posL, zeMouvement.posC );
	    set( zeMouvement.nbIndiana);
	    for (int ind = 1; ind < zeMouvement.nbEchange; ind++) {
            addEchange( zeMouvement.echL[ind], zeMouvement.echC[ind]);
        }
	    //if( zeMouvement.echange ) setEchange( zeMouvement.echL, zeMouvement.echC );
	}
	void init()
	{
	    zeJoueur = null;
		zeType = -1;
		posL = -1;
		posC = -1;
		nbIndiana = 0;
		nbEchange = 0;
		echL = new int[NB_ECH_MAX];
		echC = new int[NB_ECH_MAX];
		for (int ind = 0; ind < echL.length; ind++) {
		    echL[ind] = -1;
		    echC[ind] = -1;
        }
	}
	void reset()
	{
	    zeJoueur = null;
	    zeType = -1;
	    posL = -1;
		posC = -1;
		nbIndiana = 0;
		nbEchange = 0;
		for (int ind = 0; ind < echL.length; ind++) {
		    echL[ind] = -1;
		    echC[ind] = -1;
        }
	}
    /**
     * @return true si les membres sont les m�mes
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Mouvement) {
            Mouvement mvt= (Mouvement)obj;
            if( mvt != null ) {
                if(zeJoueur == null ) {
                    if( mvt.zeJoueur != null ) return false;
                }
                else {
                    if( !zeJoueur.equals(mvt.zeJoueur)) return false; 
                }
                boolean result = (zeType == mvt.zeType
                	&& posL == mvt.posL && posC == mvt.posC
                	&& nbIndiana == mvt.nbIndiana
                	&& nbEchange == mvt.nbEchange);
                result = (result && Arrays.equals( echL, mvt.echL));
                result = (result && Arrays.equals( echC, mvt.echC));
      
                if( result ) return true;
            }
        }
        return false;
    }
	public void set( Joueur p_joueur, int p_type, int p_ligne, int p_col) 
	{
		zeJoueur = p_joueur;
		zeType = p_type;
		posL = p_ligne;
		posC = p_col;
	}
	public void set( int p_nbIndiana )
	{
	    nbIndiana = p_nbIndiana;
	}
	/**
	 * Ajoute un nouvel �change au Mouvement sauf si on atteint la taille
	 * limite (renvoie false).
	 * @param p_ligne
	 * @param p_col
	 * @return false sir NB_ECH_MAX atteint
	 */
	public boolean addEchange( int p_ligne, int p_col )
	{
	    if( nbEchange == 0 ) {
	        echL[nbEchange] = posL;
	        echC[nbEchange] = posC;
	        nbEchange++;
	    }
	    if( nbEchange < NB_ECH_MAX ) {
	        echL[nbEchange] = p_ligne;
	        echC[nbEchange] = p_col;
	        nbEchange++;
	        return true;
	    }
	    return false;
	}
	public void setNoPiece()
	{
	    zeType = -1;
	}
	
	/**
	 * Format :<br>
	 * j:P(x, y)s(x, y)I+x<br>
	 * -:-I+x
	 */
	public String toString() 
	{
		StringBuffer strbuf = new StringBuffer();
		if( zeJoueur == null ) {
			strbuf.append("-:-");
		}
		else {
			strbuf.append( zeJoueur.toString());
			strbuf.append( ":"+ Piece.toString(zeType));
			strbuf.append( "("+posL+", "+posC+")");
			for( int ind=1; ind < nbEchange; ind++ ) {
			    strbuf.append( "s("+echL[ind]+", "+echC[ind]+")");
			}
		}
		strbuf.append( "I+"+nbIndiana);
		return strbuf.toString();
	}
	/**
	 * Extrait et met � jour un Mouvement � partir d'une String.
	 * @param buff contient la descritpion du Mouvement
	 * @param zeJeu le jeu associ�
	 */
	public void extractFrom( String buff , Jeu zeJeu)
	{
	    StringTokenizer st = new StringTokenizer( buff, ":(,)+ \t\n\r\f");
	    String token;
	    
	    reset();
	    // Player
	    token = st.nextToken();
	    zeJoueur = zeJeu.getJoueur( token );
	    // debug
//	    if( zeJoueur != null ) {
//	        System.out.println( "(J)  "+token+" : "+ zeJoueur.toString());
//	    }
//	    else {
//	        System.out.println( "(J)  "+token+" : "+ Player.type_nul);   
//	    };
	    // end-debug
	    // Piece
	    token = st.nextToken();
	    zeType = Piece.decode( token );
	    
	    // case
	    if( zeJoueur != null ) {
	        token = st.nextToken();
	        posL = Integer.decode( token ).intValue();
	        
	        token = st.nextToken();
	        posC = Integer.decode( token ).intValue();
	        
	        // echange ou suite
	        token = st.nextToken();
	        
	        while( token.equals("s") ) {
	            token = st.nextToken();
		        int tmpEchL = Integer.decode( token ).intValue();
		        
		        token = st.nextToken();
		        int tmpEchC = Integer.decode( token ).intValue();
		        addEchange( tmpEchL, tmpEchC );
		        // il faut lire pour la suite
		        token = st.nextToken();
	        }
	        // devrait �tre Indiana
	        // debug
	        // System.out.println( "(I)  "+token );
	        if( token.equals("I") ) {
	            token = st.nextToken();
	            nbIndiana = Integer.decode( token ).intValue();
	            // debug
	            // System.out.println( "(nb)  "+token+"  "+nbIndiana);
	        }
	    }
	}
	
}
