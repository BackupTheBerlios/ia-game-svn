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
    public int lastPositionMiddle;
    public int nbBurned;
    
    /**
     * Creation d'un pont d'une taille donnée.
     * @param size (doit être impaire, supérieur à 7)
     */
    public Plateau( int size )
    {
        positionMur = 0;
        lastPositionMiddle = 0;
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
        lastPositionMiddle = p_plateau.lastPositionMiddle;
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
            (lastPositionMiddle == other.lastPositionMiddle) &&
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
            pont[i] = Constantes.VIDE;
        }
        positionMur = pont.length / 2 ;
        pont[positionMur] = Constantes.FEU;
        pont[positionMur-3] = Constantes.ROUGE;
        pont[positionMur+3] = Constantes.VERT;
        lastPositionMiddle = positionMur;
    }
    /**
     * Bouge le mur de 1 case.
     * @param direction Vers quel mage va-t-on.
     * @return true si un mage est touché.
     */
    public boolean moveFire( int direction )
    {
        boolean mageReached = false;
        switch (direction) {
        case Constantes.VERT:
            pont[positionMur] = Constantes.VIDE;
            positionMur++;
            if( pont[positionMur] == Constantes.VERT ) {
                mageReached = true;
            }
            pont[positionMur] = Constantes.FEU;
            break;
        case Constantes.ROUGE:
            pont[positionMur] = Constantes.VIDE;
            positionMur--;
            if( pont[positionMur] == Constantes.ROUGE ) {
                mageReached = true;
            }
            pont[positionMur] = Constantes.FEU;
            break;
        case Constantes.NO_COLOR:
            break;
            
        default:
            System.err.println( "moveFire : direction inconue ("+direction+"");
        	System.exit( -1 );
        }        
        return mageReached;
    }
    
    /**
     * Replace le mur entre les deux magiciens.
     */
    public void moveFireMiddle()
    {
        pont[positionMur] = Constantes.VIDE;
        pont[lastPositionMiddle] = Constantes.FEU;
        positionMur = lastPositionMiddle;
    }
    /**
     * Utilise la position courante du feu pour replacer les Mages
     * et fait brûler le pont.
     * @return Couleur du mage perdant ou NO_COLOR;
     */
    public int update()
    {
        int endGame = Constantes.NO_COLOR;
        
        // efface le pont
        for (int i = 0; i < pont.length; i++) {
            pont[i] = Constantes.VIDE;
        }
        
        // brule le pont
        nbBurned++;
        for (int i = 0; i < nbBurned; i++) {
            pont[i] = Constantes.BRULE;
            pont[pont.length -1 - i] = Constantes.BRULE;
        }
        
        // installe le mur
        pont[positionMur] = Constantes.FEU;
        lastPositionMiddle = positionMur;
        
        // les mages
        if( positionMur < 3 ) {
            // Vert a gagné
            return Constantes.ROUGE;
        }
        if( (positionMur+3) > (pont.length-1) ) {
            // Rouge a gagné
            return Constantes.VERT;
        }
        if( pont[positionMur - 3] != Constantes.VIDE ) {
            endGame = Constantes.ROUGE;
        }
        else {
            pont[positionMur - 3] = Constantes.ROUGE;
        }
        if( pont[positionMur + 3] != Constantes.VIDE ) {
            endGame = Constantes.VERT;
        }
        else {
            pont[positionMur + 3] = Constantes.VERT;
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
            case Constantes.VIDE:
                strbuf.append( Constantes.VIDE_STR );
                break;
            case Constantes.BRULE:
                strbuf.append( Constantes.BRULE_STR );
                break;
            case Constantes.FEU:
                strbuf.append( Constantes.FEU_STR );
                break;
            case Constantes.VERT:
                strbuf.append( Constantes.VERT_STR );
                break;
            case Constantes.ROUGE:
                strbuf.append( Constantes.ROUGE_STR );
                break;
            default:
                strbuf.append( "?" );
                break;
            }
        }
        return strbuf.toString();
    }
    
}
