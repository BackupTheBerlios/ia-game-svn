/*
 * Created on Dec 24, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

import java.util.Arrays;

/**
 * Un Player a une r�serve de Piece � placer, un eventuel bonus, un score,
 * une couleur.
 * Il sait aussi s'afficher.
 * 
 * @author dutech
 */
public class Joueur {
	public static final int rouge = 0;
	public static final int vert = 1;
	public final static String[] type_str = {"r", "v"};
	public final static String type_nul = "-";
	
	public int couleur;
	public int bonus;
	public int score;
	
	/** 
	 * Une reserve de piece � placer.
	 */
	public int[] reserve;
	
	public Joueur()
	{
	    couleur = -1;
	    bonus = 0;
	    score = 0;
	    
	    reserve = null;
	}
	public Joueur( Joueur zeJoueur )
	{
	    couleur = zeJoueur.couleur;
	    bonus = zeJoueur.bonus;
	    score = zeJoueur.score;
	    
	    if( zeJoueur.reserve != null ) {
	        reserve = new int[zeJoueur.reserve.length];
	        for( int type=0; type<zeJoueur.reserve.length; type++) {
	            reserve[type] = zeJoueur.reserve[type];
	        }
	    }
	    else {
	        reserve = null;
	    }
	}
	/**
     * @return true si tous les membres sont les m�mes
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Joueur) {
            Joueur jou= (Joueur)obj;
            boolean result = (couleur == jou.couleur
                && bonus == jou.bonus  
                && score == jou.score);
            if( reserve == null ) {
                if( jou.reserve != null ) return false;
            }
            else {
                result = (result && Arrays.equals( reserve, jou.reserve ));
            }
            if( result ) return true;
        }
        return false;
    }
    /**
     * @return true s'ils ont la m�me couleur.
     */
    public boolean same( Joueur jou)
    {
        if( jou == null ) return false;
        if (couleur == jou.couleur) {
            return true;
        }
        return false;
    }
    
	/**
	 * Prendre une Piece de la r�serve pour la jouer.
	 * @param type de la Piece
	 * @return true si on peut prendre cette Piece
	 */
	public boolean prendrePiece( int type )
	{
		if( reserve[type] > 0 ) {
			reserve[type] --;
			return true;
		}
		return false;
	}
	/**
	 * Reprendre dans son jeu une des Piece.
	 * @param type de la Piece
	 */
	public void reprendrePiece( int type )
	{
		reserve[type] +=1;
	}
	
	public String displayReserve()
	{
		StringBuffer strbuf = new StringBuffer();
		for( int type=0; type<reserve.length; type++) {
			strbuf.append( reserve[type] + "|");
		}
		
		return strbuf.toString();
	}
	

	public String toString()
	{
	    return toString( couleur );
	}
	public static String toString(int couleur)
	{
	    if( couleur >= 0 ) {
	        return type_str[couleur];
	    }
	    else {
	        return type_nul;
	    }
	}
	public static int decode( String token )
	{
	    for( int i=0; i < type_str.length; i++ ) {
	        if( token.equals(type_str[i]) ) return i;
	    }
	    return -1;
	}

}
