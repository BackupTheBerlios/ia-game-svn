/*
 * Created on Jan 29, 2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

/**
 * Une Piece pour Diabalik : un type, une façon de s'afficher et appartient à
 * un Joueur. 
 * On peut aussi l'afficher (displayStr)
 * @author dutech
 */
public class Piece {
	public final static int coureur = 0;
	public final static int passeur = 1;
	public final static int nbType = 2;
	public final static String[] type_str = {"c", "P"};
	public final static String type_nul = "-";
	
	public int type;
	public Joueur m_joueur;
	
	public String m_displayStr;
	
	/**
	 * Piece vide.
	 * Sans type, sans Joueur.
	 */
	public Piece()
	{
	    type = -1;
	    m_joueur = null;
	    m_displayStr = null;
	}
	/**
	 * Copie d'une autre Piece.
	 * @param zePiece
	 */
	public Piece( Piece zePiece )
	{
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
            boolean result = (type == pie.type);  
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
		strbuf.append( toString(type));
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
	/**
	 * Crée un String a partir d'un indice de type.
	 * @param p_type
	 * @return
	 */
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
