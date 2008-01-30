/*
 * Created on Jan 29, 2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

import java.util.Arrays;

/**
 * @author dutech
 */
public class Plateau {
	public final static int tailleL = 7;
	public final static int tailleC = 7;
	
	/** 
	 * Une Grille de {@link Piece}.
	 */
	public Piece[][] cases;
	
	public Plateau()
	{
		cases = new Piece[tailleL][tailleC];
		for( int ligne=0; ligne<tailleL; ligne++) {
			for( int col=0; col<tailleC; col++) {
				cases[ligne][col] = null;
			}
		}
	}
	public Plateau( Plateau zePlateau )
	{
	    cases = new Piece[tailleL][tailleC];
	    for( int ligne=0; ligne<tailleL; ligne++) {
			for( int col=0; col<tailleC; col++) {
			    if( zePlateau.cases[ligne][col] != null) {
			        cases[ligne][col] = new Piece( zePlateau.cases[ligne][col] );
			    }
			    else {
			        cases[ligne][col] = null;
			    }
			}
		}
	}
	
	/**
     * @return true si tous les membres sont les mêmes
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Plateau) {
            Plateau pla= (Plateau)obj;
            boolean result = true;
            if( cases == null ) {
                if( pla.cases != null ) return false;
            }
            else {
                if( cases.length != pla.cases.length ) return false;
                for( int i=0; i<cases.length; i++) {
                    if( cases[i] == null ) {
                        if( pla.cases[i] != null ) return false;
                    }
                    else {
                        result= (result && Arrays.equals( cases[i], pla.cases[i]));
                    }
                }
            }
            if( result ) return true;
        }
        return false;
    }
	
	/**
	 * Chaque case occupe 2 char. 
	 * @return
	 */
	public String toString()
	{
		StringBuffer strbuf = new StringBuffer();
		
		// ligne bord
		strbuf.append( " +--" );
		for( int i=1; i<tailleC; i++) {
			strbuf.append( "+--" );
		}
		strbuf.append( "+\n" );
		// lignes
		for( int ligne = 0; ligne < tailleL; ligne++ ) {
			strbuf.append(" |");
			strbuf.append( displayCaseStr(ligne,0));
			for( int col = 1; col < tailleC; col++ ) {
				strbuf.append( "|" + displayCaseStr(ligne,col));
			}
			strbuf.append( "| \n");
			
			if( ligne < tailleL ) {
				// ligne bord
				strbuf.append( " +--" );
				for( int i=1; i<tailleC; i++) {
					strbuf.append( "+--" );
				}
				strbuf.append( "+\n" );
			}
		}	
		return strbuf.toString();
	}
	
	/**
	 * 
	 * @param ligne d'une case
	 * @param col d'une case
	 * @return String de la case
	 */
	private String displayCaseStr( int ligne, int col)
	{
		if( !isCaseEmpty(ligne, col) ) {
			return cases[ligne][col].displayStr();
		}
		return new String( "  ");
	}
	
	/**
	 * @return true si different de null
	 */
	public boolean isCaseEmpty( int ligne, int col)
	{
		if( cases[ligne][col] != null ) {
			return false;
		}
		return true;
	}
	/**
	 * Check a given position
	 * @param pos
	 * @return true si different de null
	 */
	public boolean isCaseEmpty( PositionGrid2D pos)
	{
		return isCaseEmpty(pos.y, pos.x);
	}
	/**
	 * Check if every Case between 2 position is free or occupied by friends.
	 * @param src Starting position
	 * @param other End position
	 * @return true or false
	 */
	public boolean isLineFreeBetween( PositionGrid2D src, PositionGrid2D other ) 
	{
		PositionGrid2D dir = src.getDirectionTo(other);
		if( dir == PositionGrid2D.HERE ) return false;
		
		PositionGrid2D toCheck = src.add(dir);
		while( toCheck.equals(other ) == false ) {
			if( isCaseEmpty(toCheck) == false ) {
				if( cases[toCheck.y][toCheck.x].m_joueur.sameColor(cases[src.y][src.x].m_joueur) == false) {
					return false;
				}
			}
			toCheck = toCheck.add(dir);
		}
		return true;
	}
	/**
	 * Get either null or a {@link Piece}.
	 * @param pos
	 * @return
	 */
	public Piece getCase( PositionGrid2D pos)
	{
		if( isCaseEmpty(pos)) return null;
		return cases[pos.y][pos.x];
	}
	public void setCase( PositionGrid2D pos, Piece pion)
	{
		cases[pos.y][pos.x] = pion;
	}
	
//	/**
//	 * @return null si Empty ou deja en haut
//	 */
//	public Piece getCaseUp( int ligne, int col)
//	{
//		if( ligne == 0) return null;
//		return cases[ligne-1][col];
//	}
//	/**
//	 * @return null si Empty ou deja en bas
//	 */
//	public Piece getCaseDown( int ligne, int col)
//	{
//		if( (ligne+1) < tailleL) return cases[ligne+1][col];
//		return null;
//	}
//	/**
//	 * @return null si Empty ou deja à gauche
//	 */
//	public Piece getCaseLeft( int ligne, int col)
//	{
//		if( col == 0) return null;
//		return cases[ligne][col-1];
//	}
//	/**
//	 * @return null si Empty ou deja à droite
//	 */
//	public Piece getCaseRight( int ligne, int col)
//	{
//		if( (col+1) < tailleC) return cases[ligne][col+1];
//		return null;
//	}

}
