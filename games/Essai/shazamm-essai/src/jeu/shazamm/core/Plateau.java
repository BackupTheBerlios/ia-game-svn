/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import java.util.Arrays;

/**
 * Le plateau représente le pont qui brûle, le feu et les magiciens.
 * @author dutech
 */
public class Plateau {
    
    public int pont[];
    public int positionMur;
    public int nbBurned;
    
    /**
     * Creation d'un pont d'une taille donnée.
     * @param size (doit être impaire, supérieur à 7)
     */
    public Plateau( int size )
    {
        positionMur = 0;
        nbBurned = 0;
        pont = new int[size];
        init();
    }
    /**
     * Clone.
     */
    public Plateau( Plateau p_plateau )
    {
        positionMur = p_plateau.positionMur;
        nbBurned = p_plateau.nbBurned;
        pont = new int[p_plateau.pont.length];
        for (int i = 0; i < pont.length; i++) {
            pont[i] = p_plateau.pont[i];
        }
    }
    /**
     * Teste l'égalité des ponts et des positions.
     * @return true si égaux.
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Plateau) {
            Plateau other = (Plateau) obj;
            boolean result = Arrays.equals( pont, other.pont) &&
            (positionMur == other.positionMur) &&
            (nbBurned == other.nbBurned);
            return result;
        }
        return false;
    }
    
    /** 
     * Pont intact, mur au milieu, mages à trois cases.
     */
    public void init()
    {
        nbBurned = 0;
        for( int i = 0; i < pont.length; i++) {
            pont[i] = VIDE;
        }
        positionMur = pont.length / 2 ;
        pont[positionMur] = FEU;
        pont[positionMur-3] = ROUGE;
        pont[positionMur+3] = VERT;
    }
    /**
     * Bouge le mur de 1 case.
     * @param direction
     * @return true si un mage est touché.
     */
    public boolean moveFire( int direction )
    {
        boolean mageReached = false;
        switch (direction) {
        case VERS_VERT:
            pont[positionMur] = VIDE;
            positionMur++;
            if( pont[positionMur] == VERT ) {
                mageReached = true;
            }
            pont[positionMur] = FEU;
            break;
        case VERS_ROUGE:
            pont[positionMur] = VIDE;
            positionMur--;
            if( pont[positionMur] == ROUGE ) {
                mageReached = true;
            }
            pont[positionMur] = FEU;
            break;
        default:
            System.err.println( "moveFire : direction inconue ("+direction+"");
        	System.exit( -1 );
        }        
        return mageReached;
    }
    /**
     * Utilise la position courante du feu pour replacer les Mages
     * et fait brûler le pont.
     * @return true si c'est la fin du jeu.
     */
    public boolean update()
    {
        boolean endGame = false;
        
        // efface le pont
        for (int i = 0; i < pont.length; i++) {
            pont[i] = VIDE;
        }
        
        // brule le pont
        nbBurned++;
        for (int i = 0; i < nbBurned; i++) {
            pont[i] = BRULE;
            pont[pont.length -1 - i] = BRULE;
        }
        
        // installe le mur
        pont[positionMur] = FEU;
        
        // les mages
        if( pont[positionMur - 3] != VIDE ) {
            endGame = true;
        }
        else {
            pont[positionMur - 3] = ROUGE;
        }
        if( pont[positionMur + 3] != VIDE ) {
            endGame = true;
        }
        else {
            pont[positionMur + 3] = VERT;
        }
        return endGame;
    }
    
    
    /**
     * Affiche comme une ligne de caractères.
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        for (int i = 0; i < pont.length; i++) {
            switch ( pont[i] ) {
            case VIDE:
                strbuf.append( VIDE_STR );
                break;
            case BRULE:
                strbuf.append( BRULE_STR );
                break;
            case FEU:
                strbuf.append( FEU_STR );
                break;
            case VERT:
                strbuf.append( VERT_STR );
                break;
            case ROUGE:
                strbuf.append( ROUGE_STR );
                break;
            default:
                strbuf.append( "?" );
                break;
            }
        }
        return strbuf.toString();
    }
    
    /* Définition des constantes */
    final static int VIDE = 0;
    final static int BRULE = -1;
    final static int FEU = 10;
    final static int VERT = 1;
    final static int ROUGE = 2;
    final static int VERS_VERT = 11;
    final static int VERS_ROUGE = 12;
    
    final static String VIDE_STR = ".";
    final static String BRULE_STR = "X";
    final static String FEU_STR = "|";
    final static String VERT_STR = "V";
    final static String ROUGE_STR = "R";
    
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
        }
        return "?";
    }
}
