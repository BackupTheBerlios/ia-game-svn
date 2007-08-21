/*
 * Created on Dec 24, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

import java.util.Arrays;

/**
 * @author dutech
 */
public class Plateau {
	public final static int tailleL = 5;
	public final static int tailleC = 6;
	public final static int nbRegion = 6;
	/**
	 * Chaque region est une liste de coordonnées.
	 */
	public static int[][][] regions = {
			{{0,0}, {1,0}, {2,0}, {2,1}, {3,1}},
			{{0,1}, {1,1}, {1,2}, {2,2}, {3,2}, {3,3}, {3,4}},
			{{0,2}, {0,3}, {0,4}, {0,5}, {1,3}, {2,3}, {2,4}},
			{{1,4}, {1,5}, {2,5}},
			{{3,0}, {4,0}, {4,1}},
			{{3,5}, {4,2}, {4,3}, {4,4}, {4,5}}
	};
	public static int[][] indRegion = null;
	/**
	 * Pour chaque region, on a l'indice du joueur majoritaire (ou -1) et la valeur.
	 */
	public int[][] valRegions;
	public final static int indJ = 0;
	public final static int indV = 1;
	
	public Piece[][] cases;
	public int posIndiana;
	public final static int maxIndiana = 2*(tailleL+tailleC);
	
	public Plateau()
	{
		posIndiana = -1;
		cases = new Piece[tailleL][tailleC];
		for( int ligne=0; ligne<tailleL; ligne++) {
			for( int col=0; col<tailleC; col++) {
				cases[ligne][col] = null;
			}
		}
		
		// on peut essayer de construire le tableau des indices de région
		if( indRegion == null) {
		    indRegion = new int[tailleL][tailleC];
		    for( int indR = 0; indR < nbRegion; indR++) {
		        int tailleR = regions[indR].length;
		        for( int i=0; i < tailleR; i++) {
		            indRegion[regions[indR][i][0]][regions[indR][i][1]] = indR;
		        }
		    }
		}
		
		valRegions = new int[nbRegion][2];
		for( int indR = 0; indR < nbRegion; indR++) {
			valRegions[indR][0] = -1; // ind joueur
			valRegions[indR][1] = 0; // valeur
		}
	}
	public Plateau( Plateau zePlateau )
	{
	    posIndiana = zePlateau.posIndiana;
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
	    
	    // normalement, indRegion est toujours là
	    
	    valRegions = new int[nbRegion][2];
	    for( int indR = 0; indR < nbRegion; indR++) {
	        valRegions[indR][0] = zePlateau.valRegions[indR][0]; // ind joueur
	        valRegions[indR][1] = zePlateau.valRegions[indR][1]; // valeur
		}
	}
	
	/**
     * @return true si tous les membres sont les mêmes
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Plateau) {
            Plateau pla= (Plateau)obj;
            boolean result = (posIndiana == pla.posIndiana);
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
	 * Chaque case occupe 3 char.
	 * Il faut aussi placer Indiana. 
	 * @return
	 */
	public String toString()
	{
		StringBuffer strbuf = new StringBuffer();
		int countIndiana = 0;
		// 1ere ligne : Indiana
		strbuf.append( "  " );
		for( int i=0; i<tailleC; i++) {
			if( posIndiana == countIndiana ) {
				strbuf.append( " X  " );
			}
			else {
				strbuf.append( "    " );
			}
			countIndiana ++;
		}
		strbuf.append( " \n" );
		// ligne bord
		strbuf.append( " +---" );
		for( int i=1; i<tailleC; i++) {
			strbuf.append( "----" );
		}
		strbuf.append( "+\n" );
		// lignes
		for( int ligne = 0; ligne < tailleL; ligne++ ) {
			//ligne pions
			if( (countIndiana - 3*ligne + 15) == posIndiana) {
				strbuf.append( "X|");
			}
			else {
				strbuf.append( " |");
			}
			countIndiana ++;
			strbuf.append( displayCaseStr(ligne,0));
			for( int col = 1; col < tailleC; col++ ) {
				strbuf.append( " " + displayCaseStr(ligne,col));
			}
			if( (countIndiana - ligne - 1) == posIndiana) {
				strbuf.append( "|X\n");
			}
			else {
				strbuf.append( "| \n");
			}
			countIndiana ++;
			if( ligne < tailleL-1 ) {
				//ligne vide
				strbuf.append( " |   ");
				for( int col = 1; col < tailleC; col++ ) {
					strbuf.append( "    " );
				}
				strbuf.append( "| \n");
			}
		}
        
		//ligne bord
		strbuf.append( " +---" );
		for( int i=1; i<tailleC; i++) {
			strbuf.append( "----" );
		}
		strbuf.append( "+\n" );
		//derniere ligne : Indiana
		strbuf.append( "  " );
		for( int i=0; i<tailleC; i++) {
			if( (countIndiana - i*2) == posIndiana ) {
				strbuf.append( " X  " );
			}
			else {
				strbuf.append( "    " );
			}
			countIndiana ++;
		}
		strbuf.append( " \n" );
		
		
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
		return new String( " . ");
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
	 * @return null si Empty ou deja en haut
	 */
	public Piece getCaseUp( int ligne, int col)
	{
		if( ligne == 0) return null;
		return cases[ligne-1][col];
	}
	/**
	 * @return null si Empty ou deja en bas
	 */
	public Piece getCaseDown( int ligne, int col)
	{
		if( (ligne+1) < tailleL) return cases[ligne+1][col];
		return null;
	}
	/**
	 * @return null si Empty ou deja à gauche
	 */
	public Piece getCaseLeft( int ligne, int col)
	{
		if( col == 0) return null;
		return cases[ligne][col-1];
	}
	/**
	 * @return null si Empty ou deja à droite
	 */
	public Piece getCaseRight( int ligne, int col)
	{
		if( (col+1) < tailleC) return cases[ligne][col+1];
		return null;
	}

	/**
	 * Indiana est positionné sans sortir de ses limites.
	 * @param position autour du jeu
	 */
	public void setIndiana( int position)
	{
		posIndiana = position % maxIndiana;
	}
	/**
	 * Indianan est avancé le long du plateau dans le sens des aiguilles d'une montre.
	 * @param nbCase avance
	 */
	public void advanceIndiana( int nbCase )
	{
		posIndiana += nbCase;
		posIndiana = posIndiana % maxIndiana;
	}
	/**
	 * Renvoie l'indice de la ligne d'Indiana.
	 * @return ligne ou -1 si pas sur une ligne
	 */
	public int getLineFromIndiana()
	{
		// Indiana est au dessus ou au dessous
		if( (posIndiana < 6) || 
			((posIndiana > 10) && (posIndiana < 17))) {
			return -1;
		}
		// a droite
		if( (posIndiana - tailleC) < tailleL) {
			return (posIndiana - tailleC);
		}
		// a gauche
		if( (21 - posIndiana) < tailleL) {
			return (21 - posIndiana);
		}
		return -1;
	}
	/**
	 * Renvoie l'indice de la col d'Indiana.
	 * @return col ou -1 si pas sur une colonne
	 */
	public int getColFromIndiana()
	{
		// Indiana est a gauche ou a droite
		if( ((posIndiana > 5) && (posIndiana < 11)) ||
			(posIndiana >16)) {
			return -1;
		}
		// Indiana est au dessus
		if( posIndiana < tailleC ) {
			return posIndiana;
		}
		// Indiana est au dessous
		if( (16 - posIndiana) < tailleC ) {
			return (16 - posIndiana);
		}
		return -1;
	}
	
	/**
	 * Une ligne est-elle déjà pleine.
	 * @param ligne du JPlateau
	 * @return true si la ligne est pleine
	 */
	public boolean isLineFull( int ligne )
	{
	    if( (ligne < 0) || (ligne >= tailleL)) return false;
		for( int col = 0; col < tailleC; col++ ) {
			if( cases[ligne][col] == null ) return false;
		}
		return true;
	}
	/**
	 * Une colonne est-elle déjà pleine.
	 * @param col du JPlateau
	 * @return true si la colonne est pleine
	 */
	public boolean isColFull( int col )
	{
	    if( (col < 0) || (col > tailleC)) return false;
		for( int ligne = 0; ligne < tailleL; ligne++) {
			if( cases[ligne][col] == null ) return false;
		}
		return true;
	}
	/**
	 * Regarde si une des régions est complètement occupée.
	 * @return true si une région au moins est occupée
	 */
	public boolean isAnyOneRegionFull()
	{
		for( int indR=0; indR < nbRegion; indR++) {
			boolean regionFull = true;
			for( int indC = 0; indC < regions[indR].length; indC++) {
				if( cases[regions[indR][indC][0]][regions[indR][indC][1]] == null ) {
					regionFull = false;
				}
			}
			if( regionFull ) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Calcule la valeur de chaque region et son actuel possesseur.
	 */
	public void computeValRegions()
	{
		int[] nbJoueur = new int[2];
		for( int indR=0; indR < nbRegion; indR++) {
			nbJoueur[Joueur.rouge] = 0;
			nbJoueur[Joueur.vert] = 0;
			valRegions[indR][indV] = 0;
			
			for( int indC = 0; indC < regions[indR].length; indC++) {
				Piece zePiece = cases[regions[indR][indC][0]][regions[indR][indC][1]]; 
				if( zePiece != null ) {
					valRegions[indR][indV] += zePiece.val;
					nbJoueur[zePiece.m_joueur.couleur] ++;
				}
			}
			
			valRegions[indR][indJ] = -1;
			if( nbJoueur[Joueur.rouge] > nbJoueur[Joueur.vert] )
				valRegions[indR][indJ] = Joueur.rouge;
			if( nbJoueur[Joueur.rouge] < nbJoueur[Joueur.vert] )
				valRegions[indR][indJ] = Joueur.vert;
		}
	}
	
	/**
	 * Echange deux Piece de place.
	 */
	public void switchPieces( int ligne_src, int col_src, int ligne_dest, int col_dest )
	{
		Piece src = cases[ligne_src][col_src];
		Piece dest = cases[ligne_dest][col_dest];
		cases[ligne_src][col_src] = dest;
		cases[ligne_dest][col_dest] = src;
	}

}
