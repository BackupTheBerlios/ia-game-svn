/*
 * Created on Dec 24, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

/**
 * Une Piece possède une valeur, un type, une façon de s'afficher et appartient à
 * un Joueur. 
 * On peut aussi l'afficher (displayStr)
 * @author dutech
 */
public class Piece {
	public final static int zebre = 0;
	public final static int gazelle = 1;
	public final static int elephant = 2;
	public final static int lion = 3;
	public final static int crocodile = 4;
	public final static int flipped = 5;
	public final static int nbType = 6;
	public final static String[] type_str = {"Z", "G", "E", "L", "C", "F"};
	public final static String type_nul = "-";
	
	public int val;
	public int type;
	public Joueur m_joueur;
	
	public String m_displayStr;
	
	public Piece()
	{
	    val = -1;
	    type = -1;
	    m_joueur = null;
	    m_displayStr = null;
	}
	public Piece( Piece zePiece )
	{
	    val = zePiece.val;
	    type = zePiece.type;
	    m_joueur = zePiece.m_joueur;
	    m_displayStr = zePiece.m_displayStr;
	}
	
	/**
     * @return true si tous les membres sont les mêmes
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Piece) {
            Piece pie= (Piece)obj;
            boolean result = (type == pie.type
                && val == pie.val );  
            if( m_joueur == null ) {
                if( pie.m_joueur != null ) return false;
            }
            else {
                result = (result && m_joueur.couleur == pie.m_joueur.couleur );
            }
            if( result ) return true;
        }
        return false;
    }
	
	/**
	 * Prépare une chaine de format joueur+type+val.
	 */
	public void setDisplayStr()
	{
		StringBuffer strbuf = new StringBuffer();
		if( m_joueur != null ) {
		    strbuf.append(m_joueur.toString());
		}
		else {
		    strbuf.append( Joueur.toString(-1));
		}
		strbuf.append(toString()+val);
		
		m_displayStr = strbuf.toString();
	}
	/**
	 * Le format long pour une piece, en ayant appelé setDisplayStr auparavant.
	 * @return String de la forme joueur+type+val
	 */
	public String displayStr()
	{
		return m_displayStr; 
	}
	
	/**
	 * Juste un caractère qui dépend du type.
	 * @return String
	 */
	public String toString()
	{
	    return toString( type );
	}
	public static String toString(int p_type)
	{
	    if( p_type >= 0 ) {
	        return type_str[p_type];
	    }
	    else {
	        return type_nul;
	    }
	}
	/**
	 * Renvoie le type de la piece codée par ce token.
	 * @param token
	 * @return le type de la piece.
	 */
	public static int decode( String token )
	{
	    for( int i=0; i < type_str.length; i++ ) {
	        if( token.equals(type_str[i]) ) return i;
	    }
	    return -1;
	}
}
