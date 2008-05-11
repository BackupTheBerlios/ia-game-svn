/*
 * Created on Jan 29, 2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

/**
 * Un Player pour Diabalik.
 * Une couleur.
 * Il sait aussi s'afficher.
 * 
 * @author dutech
 */
public class Joueur implements game.Player {
	public static final int jaune = 0;
	public static final int rouge = 1;
	public final static String[] type_str = {"j", "r"};
	public final static String type_nul = "-";
	
	public int couleur;
	
	
	/**
	 * Player vide sans couleur.
	 */
	public Joueur()
	{
	    couleur = -1;
	}
	/**
	 * Copie d'un autre Player.
	 * @param zeJoueur
	 */
	public Joueur( Joueur zeJoueur )
	{
	    couleur = zeJoueur.couleur;
	}
	/* (non-Javadoc)
	 * @see diabalik.IJoueur#equals(java.lang.Object)
	 */
    public boolean equals(Object obj)
    {
        if (obj instanceof Joueur) {
            Joueur jou= (Joueur)obj;
            boolean result = (couleur == jou.couleur);
            if( result ) return true;
        }
        return false;
    }
    /* (non-Javadoc)
	 * @see diabalik.IJoueur#sameColor(diabalik.Joueur)
	 */
    public boolean sameColor( game.Player jou)
    {
    	if( jou == null ) return false;
    	if (jou instanceof Joueur) {
			Joueur player = (Joueur)jou;
			
	        if (couleur == player.couleur) {
	            return true;
	        }
		}
        
        return false;
    }
	
	/* (non-Javadoc)
	 * @see diabalik.IJoueur#toString()
	 */
	public String toString()
	{
	    return toString( couleur );
	}
	/**
	 * Cr�e un String a partir d'un indice de couleur.
	 * @param couleur
	 * @return 
	 */
	public static String toString(int couleur)
	{
	    if( couleur >= 0 ) {
	        return type_str[couleur];
	    }
	    else {
	        return type_nul;
	    }
	}
	/**
	 * D�code un indice de couleur � partir d'un String.
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
