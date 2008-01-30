/*
 * Created on Jan 29, 2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;


/**
 * Un Joueur pour Diabalik.
 * Une couleur.
 * Il sait aussi s'afficher.
 * 
 * @author dutech
 */
public class Joueur {
	public static final int jaune = 0;
	public static final int rouge = 1;
	public final static String[] type_str = {"j", "r"};
	public final static String type_nul = "-";
	
	public int couleur;
	
	
	/**
	 * Joueur vide sans couleur.
	 */
	public Joueur()
	{
	    couleur = -1;
	}
	/**
	 * Copie d'un autre Joueur.
	 * @param zeJoueur
	 */
	public Joueur( Joueur zeJoueur )
	{
	    couleur = zeJoueur.couleur;
	}
	/**
     * @return true si tous les membres sont les mêmes
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
    /**
     * @return true s'ils ont la même couleur.
     */
    public boolean sameColor( Joueur jou)
    {
        if( jou == null ) return false;
        if (couleur == jou.couleur) {
            return true;
        }
        return false;
    }
	
	public String toString()
	{
	    return toString( couleur );
	}
	/**
	 * Crée un String a partir d'un indice de couleur.
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
	 * Décode un indice de couleur à partir d'un String.
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
