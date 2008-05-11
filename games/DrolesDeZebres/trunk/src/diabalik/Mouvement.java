/*
 * Created on Dec 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

import java.util.StringTokenizer;

/**
 * Un Mouvement doit permettre de refaire automatiquement le dernier coup.
 * Se compose d'un Player, d'un type de mouvement, de la position de depart et de la
 * position d'arrivee.
 *
 * @author dutech
 */
public class Mouvement {
	public Joueur zeJoueur;
	public int zeType;
	public PositionGrid2D posDebut, posFin;
	
	public static final int pass = 0;
	public static final int depl = 1;
	public static final int none = 2;
	public final static String[] type_str = {"P", "D", "N"};
	public final static String type_nul = "-";
	
	
	public Mouvement()
	{
		init();
	}
	public Mouvement( Joueur p_joueur, int p_type, PositionGrid2D pStart, PositionGrid2D pEnd ) 
	{
	    init();
		set( p_joueur, p_type, pStart, pEnd);
	}
	public Mouvement( Mouvement zeMouvement )
	{
	    init();
	    set( zeMouvement.zeJoueur, zeMouvement.zeType, zeMouvement.posDebut, zeMouvement.posFin );
	}
	void init()
	{
	    zeJoueur = null;
		zeType = -1;
		posDebut = null;
		posFin = null;
	}
	void reset()
	{
	    init();
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
                boolean result = (zeType == mvt.zeType)
                	&& posDebut.equals( mvt.posDebut )
                	&& posFin.equals( mvt.posFin );
                if( result ) return true;
            }
        }
        return false;
    }
	public void set( Joueur p_joueur, int p_type, PositionGrid2D pStart, PositionGrid2D pEnd )
	{
		zeJoueur = p_joueur;
		zeType = p_type;
		posDebut = pStart;
		posFin = pEnd;
	}
	
	/**
	 * Format :<br>
	 * j:M(x, y)-(x, y)<br>
	 */
	public String toString() 
	{
		StringBuffer strbuf = new StringBuffer();
		if( zeJoueur == null ) {
			strbuf.append("-:-");
		}
		else {
			strbuf.append( zeJoueur.toString());
			strbuf.append( ":"+ toString(zeType));
			strbuf.append( posDebut.toString()+"-"+posFin.toString());
		}
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
	    zeType = decode( token );
	    
	    // case
	    if( zeJoueur != null ) {
	        token = st.nextToken();
	        int posX = Integer.decode( token ).intValue();
	        
	        token = st.nextToken();
	        int posY = Integer.decode( token ).intValue();
	        posDebut = new PositionGrid2D(posX,posY);
	        
	        // echange ou suite
	        token = st.nextToken();
	        
	        if( token.equals("-") ) {
	        	
	            token = st.nextToken();
	            posX = Integer.decode( token ).intValue();
		        
		        token = st.nextToken();
		        posY = Integer.decode( token ).intValue();
		        posFin = new PositionGrid2D(posX,posY);
		   
	        }
	    }
	}
	
	/**
	 * Cr�e un String a partir d'un type de mouvement.
	 * @param couleur
	 * @return 
	 */
	public static String toString(int type)
	{
	    if( type >= 0 ) {
	        return type_str[type];
	    }
	    else {
	        return type_nul;
	    }
	}
	/**
	 * D�code un type de mouvement � partir d'un String.
	 * @param token
	 * @return
	 */
	public static int decode( String token )
	{
	    for( int i=0; i < type_str.length; i++ ) {
	        if( token.equals(type_str[i]) ) return i;
	    }
	    return -1;
	}
	
}
