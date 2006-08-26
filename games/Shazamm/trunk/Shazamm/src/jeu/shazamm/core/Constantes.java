/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

/**
 * @author dutech
 */
public class Constantes {
    /* Définition des constantes */
    public final static int VIDE = 0;
    public final static int BRULE = -1;
    public final static int FEU = 10;
    
    public final static String VIDE_STR = ".";
    public final static String BRULE_STR = "X";
    public final static String FEU_STR = "|";
    
    /* Couleurs --------------------------------------------------------- */
    public final static int NO_COLOR = -1;
    public final static int VERT = 1;
    public final static int ROUGE = 2;
    public final static String VERT_STR = "V";
    public final static String ROUGE_STR = "R";
    
    final static String[] type_str = {VERT_STR, ROUGE_STR};
	public final static String type_nul = "-";
	
    /**
     * La String de chaque couleur.
     * @param couleur
     */
    static public String strCoul( int couleur )
    {
        switch( couleur ) {
        case ROUGE:
            return ROUGE_STR;
        case VERT:
            return VERT_STR;
         case NO_COLOR:
             return type_nul;
        }
        return "?";
    }

    /**
     * Transforme un String dans l'entier correspondant.
     * @param token
     * @return int de la String ou '-1'.
     */
    public static int decodeCoul( String token )
	{
	    for( int i=0; i < type_str.length; i++ ) {
	        if( token.equals(type_str[i]) ) return i+1;
	    }
	    return NO_COLOR;
	}

    
    /* Etat du Jeu pour chaque joueur --------------------------------------- */
    public final static int DOIT_MISER = 100;
    public final static int DOIT_CLONER = 101;
    public final static int DOIT_VOLER = 102;
    public final static int DOIT_RECYCLER = 103;
    public final static int DOIT_ATTENDRE = 104;
    public final static int FIN_MISE = 105;
    public final static int PERDU = 200;
    public final static int GAGNE = 201;
    
    public final static String DOIT_MISER_STR = "doit miser";
    public final static String DOIT_CLONER_STR = "choix carte à cloner";
    public final static String DOIT_VOLER_STR = "choix pour larcin";
    public final static String DOIT_RECYCLER_STR = "choix recylage";
    public final static String DOIT_ATTENDRE_STR = "attente";
    public final static String FIN_MISE_STR = "fin mise";
    public final static String PERDU_STR = "Perdu";
    public final static String GAGNE_STR = "Gagné";
    public final static String UNKNOWN_STR = "???";
    
    /**
     * Pour afficher les état en langage courant.
     * @todo Utiliser une Map pour automatiser? 
     */
    static public String etatStr( int p_etat )
    {
        switch( p_etat ) {
        case DOIT_MISER :
            return DOIT_MISER_STR;
        case DOIT_CLONER :
            return DOIT_CLONER_STR;
        case DOIT_VOLER :
            return DOIT_VOLER_STR;
        case DOIT_RECYCLER :
            return DOIT_RECYCLER_STR;
        case DOIT_ATTENDRE :
            return DOIT_ATTENDRE_STR;
        case FIN_MISE :
            return FIN_MISE_STR;
        case PERDU :
            return PERDU_STR;
        case GAGNE :
            return GAGNE_STR;
        default:
            return UNKNOWN_STR;
        }
    }
}
