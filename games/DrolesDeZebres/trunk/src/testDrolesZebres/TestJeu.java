/*
 * Created on Dec 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package testDrolesZebres;

import game.GameException;

import java.io.FileWriter;
import java.io.IOException;

import drolesDZ.GenerateurMouvement;
import drolesDZ.Jeu;
import drolesDZ.Joueur;
import drolesDZ.Mouvement;
import drolesDZ.Piece;
import drolesDZ.Plateau;

/**
 * @author dutech
 * 
 * Todo : Attention au premier Mvt.
 */
public class TestJeu {
    
    static GenerateurMouvement genMvt;
    
	public static void main(String[] args)
	throws GameException, IOException
	{
		Jeu myJeu = new Jeu();
		myJeu.setFirstJoueur( Joueur.rouge );
		genMvt = new GenerateurMouvement();
		
		testRandJeu( myJeu );
		//testCroco( myJeu );
//		testDeepClone( myJeu );
		
//		testGenPos( myJeu );
//		testGenPiece( myJeu );
//		testGenJeu( myJeu );
	}
	
	public static void testRandJeu( Jeu myJeu )
	throws GameException, IOException
	{
	    FileWriter myFile = new FileWriter( "zebre.mvt" );
	    
	    Mouvement zeMove;
	    
	    zeMove = genMvt.initIndianaRandom( myJeu );
	    System.out.println( myJeu.displayStr());
	    myFile.write( zeMove.toString()+"\n" );
	    
	    while( !myJeu.getFinJeu() ) {
	        myJeu = genMvt.nextMoveRandom( myJeu );
	        zeMove = myJeu.getDernierMvt();
	        myFile.write( zeMove.toString()+"\n" );
	        System.out.println( myJeu.displayStr());
	    }
	    myFile.close();
	}
	public static void testGenJeu( Jeu myJeu )
	throws GameException
	{
	    genMvt.potentialJeu( myJeu );
	    System.out.println( "JeuMvt = "+genMvt.displayListeJeu() );
	}
	public static void testGenMvt( Jeu myJeu )
	{
	    genMvt.potentialMvt( myJeu );
		System.out.println( "Mvt = "+genMvt.displayListeMvt() );
	}
	public static void testGenPiece( Jeu myJeu )
	{
	    genMvt.potentialPiece( myJeu.getJoueur(Joueur.rouge) );
		System.out.println( "Rouge = "+genMvt.displayListePiece() );
		
		genMvt.potentialPiece( myJeu.getJoueur(Joueur.vert) );
		System.out.println( "Vert = "+genMvt.displayListePiece() );
		
	}
	public static void testGenPos( Jeu myJeu )
	{
	    myJeu.poserPiece( Joueur.rouge, Piece.lion, 0, 0);
		myJeu.poserPiece( Joueur.vert, Piece.elephant, 0, 1);
		myJeu.poserPiece( Joueur.rouge, Piece.crocodile, 1, 0);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 0, 2);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 0, 3);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 0, 4);
		myJeu.poserIndianaRegles(6);
		myJeu.computeValidMoveIndiana();
		System.out.println( myJeu.displayStr());
		displayValid( myJeu );
		
		genMvt.potentialCases( myJeu.getPlateau() );
		System.out.println( genMvt.displayListePos() );
		
		System.out.println( "aI+1" + myJeu.bougerIndianaRegles(1) );
		myJeu.computeValidMoveIndiana();
		System.out.println( myJeu.displayStr());
		displayValid( myJeu );
		genMvt.potentialCases( myJeu.getPlateau() );
		System.out.println( genMvt.displayListePos() );
		
//		myJeu.poserIndianaRegles(16);
//		myJeu.computeValidMoveIndiana();
//		System.out.println( myJeu.displayStr());
//		displayValid( myJeu );
//		System.out.println( "aI+1" + myJeu.bougerIndianaRegles(1) );
//		
//		genMvt.potentialCases( myJeu.zePlateau );
//		System.out.println( genMvt.displayListePos() );
		
	}
	public static void testDeepClone( Jeu myJeu )
	{
	    myJeu.poserPiece( Joueur.rouge, Piece.lion, 0, 0);
		myJeu.poserPiece( Joueur.vert, Piece.elephant, 0, 1);
		myJeu.poserPiece( Joueur.rouge, Piece.crocodile, 1, 0);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 0, 2);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 0, 3);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 0, 4);
		
		myJeu.poserIndianaRegles(16);
		myJeu.computeValidMoveIndiana();
		System.out.println( myJeu.displayStr());
		displayValid( myJeu );
		
		Jeu newJeu = new Jeu(myJeu);
		System.out.println( newJeu.displayStr());
		//newJeu.computeValidMoveIndiana();
		displayValid( newJeu );
	}
	public static void testIndiana1( Jeu myJeu )
	{
		myJeu.poserPiece( Joueur.rouge, Piece.lion, 0, 0);
		myJeu.poserPiece( Joueur.rouge, Piece.elephant, 0, 1);
		myJeu.poserPiece( Joueur.rouge, Piece.crocodile, 1, 0);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 0, 2);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 0, 3);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 0, 4);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 0, 5);
		System.out.println( myJeu.displayStr());
		
		System.out.println( "pI0=>" + myJeu.poserIndianaRegles( 0 ) );
		System.out.println( myJeu.displayStr());
		System.out.println( "pI5=>" + myJeu.poserIndianaRegles( 5 ) );
		System.out.println( myJeu.displayStr());
		System.out.println( "pI6=>" + myJeu.poserIndianaRegles( 6 ) );
		System.out.println( myJeu.displayStr());
		System.out.println( "pI7=>" + myJeu.poserIndianaRegles( 7 ) );
		System.out.println( myJeu.displayStr());
		System.out.println( "pI21=>" + myJeu.poserIndianaRegles( 21 ) );
		System.out.println( myJeu.displayStr());
		
	}
	public static void testIndiana2( Jeu myJeu )
	{
		myJeu.poserPiece( Joueur.rouge, Piece.lion, 0, 0);
		myJeu.poserPiece( Joueur.rouge, Piece.elephant, 0, 1);
		myJeu.poserPiece( Joueur.rouge, Piece.crocodile, 1, 0);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 0, 2);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 0, 3);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 0, 4);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 0, 5);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 2, 0);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 2, 1);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 2, 2);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 2, 3);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 2, 4);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 2, 5);
		myJeu.poserPiece( Joueur.vert, Piece.crocodile, 3, 0);
		myJeu.poserPiece( Joueur.vert, Piece.crocodile, 4, 0);
		myJeu.poserPiece( Joueur.vert, Piece.lion, 4, 1);
		myJeu.poserPiece( Joueur.vert, Piece.elephant, 3, 1);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 1, 1);
		myJeu.poserPiece( Joueur.rouge, Piece.crocodile, 4, 2);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 3, 2);
		myJeu.poserPiece( Joueur.rouge, Piece.zebre, 1, 2);
		myJeu.poserPiece( Joueur.rouge, Piece.zebre, 4, 3);
		myJeu.poserPiece( Joueur.rouge, Piece.zebre, 4, 4);
		myJeu.poserPiece( Joueur.rouge, Piece.zebre, 4, 5);
		myJeu.poserPiece( Joueur.rouge, Piece.zebre, 1, 5);
		
		System.out.println( "pI4=>" + myJeu.poserIndianaRegles( 4 ) );
		System.out.println( myJeu.displayStr());
		myJeu.computeValidMoveIndiana();
		myJeu.getPlateau().computeValRegions();
		displayValid( myJeu );
		
		System.out.println( "aI+5" + myJeu.bougerIndianaRegles(5) );
		System.out.println( "aI+2" + myJeu.bougerIndianaRegles(2) );
		System.out.println( "aI+1" + myJeu.bougerIndianaRegles(1) );
		System.out.println( myJeu.displayStr());
		myJeu.computeValidMoveIndiana();
		displayValid( myJeu );
		
		System.out.println( "pI13=>" + myJeu.poserIndianaRegles( 13) );
		System.out.println( myJeu.displayStr());
		myJeu.computeValidMoveIndiana();
		displayValid( myJeu );
		
		System.out.println( "aI+3" + myJeu.bougerIndianaRegles(3) );
		System.out.println( myJeu.displayStr());
		myJeu.computeValidMoveIndiana();
		displayValid( myJeu );
		
		System.out.println( "aI+4" + myJeu.bougerIndianaRegles(4) );
		System.out.println( myJeu.displayStr());
		myJeu.computeValidMoveIndiana();
		displayValid( myJeu );
		
		myJeu.poserPiece( Joueur.vert, Piece.zebre, 1, 3);
		myJeu.poserPiece( Joueur.vert, Piece.zebre, 1, 4);
		myJeu.poserPiece( Joueur.vert, Piece.zebre, 3, 3);
		myJeu.poserPiece( Joueur.vert, Piece.zebre, 3, 4);
		myJeu.poserPiece( Joueur.vert, Piece.zebre, 3, 5);
		myJeu.getPlateau().computeValRegions();
		System.out.println( myJeu.displayStr());
		myJeu.computeValidMoveIndiana();
		displayValid( myJeu );
		
		
	}
	public static void displayValid( Jeu myJeu )
	{
		StringBuffer strbuf = new StringBuffer();
		for( int i=0; i<4; i++) {
			strbuf.append( myJeu.getValidMoveIndiana(i) + "|");
		}
		System.out.println( strbuf.toString());
	}
	public static void testPose( Jeu myJeu )
	{
		System.out.println( myJeu.displayStr());
		
		System.out.println( "rE35 =>" + myJeu.poserPiece( Joueur.rouge, Piece.elephant, 3, 5));
		System.out.println( "vE35 =>" + myJeu.poserPiece( Joueur.vert, Piece.elephant, 3, 5));
		System.out.println( "vZ34 =>" + myJeu.poserPiece( Joueur.vert, Piece.zebre, 3, 4));
	
		System.out.println( myJeu.displayStr());
		
		
	}
	public static void testBonus( Jeu myJeu )
	{
		System.out.println( "rG30 =>" + myJeu.poserPieceRegles( Joueur.rouge, Piece.gazelle, 3, 0));
		System.out.println( myJeu.displayStr());
		
		System.out.println( "vL40 =>" + myJeu.poserPieceRegles( Joueur.vert, Piece.lion, 4, 0));
		System.out.println( myJeu.displayStr());
		
		System.out.println( "rE30 =>" + myJeu.poserPieceRegles( Joueur.rouge, Piece.elephant, 3, 0));
		System.out.println( myJeu.displayStr());
		
		System.out.println( "vG41 =>" + myJeu.poserPieceRegles( Joueur.vert, Piece.gazelle, 4, 1));
		System.out.println( myJeu.displayStr());
	}
	public static void testLion( Jeu myJeu )
	{
		myJeu.poserPiece( Joueur.rouge, Piece.elephant, 2, 2);
		myJeu.poserPiece( Joueur.vert, Piece.zebre, 3, 3);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 3, 1);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 1, 2);
		System.out.println( "Avant de jouer le lion...\n"+myJeu.displayStr());
		
		System.out.println( "rL32 =>" + myJeu.poserPiece( Joueur.rouge, Piece.lion, 3, 2));
		myJeu.changeAroundLion( 3, 2 );
		System.out.println( "Apr�s avoir jou� le lion...\n"+myJeu.displayStr());

		System.out.println( "vG42 =>" + myJeu.poserPiece( Joueur.vert, Piece.gazelle, 4, 2));
		myJeu.changeIfLionAround( 4, 2);
		System.out.println( "Apr�s avoir jou� le gazelle...\n"+myJeu.displayStr());
		
		System.out.println( "rZ31 =>" + myJeu.poserPiece( Joueur.rouge, Piece.zebre, 3, 1));
		myJeu.changeIfLionAround( 3, 1 );
		System.out.println( "Apr�s avoir jou� le zebre...\n"+myJeu.displayStr());


	}
	public static void testCroco( Jeu myJeu )
	{
	    myJeu.poserPiece( Joueur.rouge, Piece.elephant, 2, 2);
		myJeu.poserPiece( Joueur.vert, Piece.zebre, 3, 3);
		myJeu.poserPiece( Joueur.rouge, Piece.gazelle, 3, 1);
		myJeu.poserPiece( Joueur.vert, Piece.gazelle, 1, 2);
		System.out.println( "Avant de jouer le croco...\n"+myJeu.displayStr());
		
		System.out.println( "Croco en 3, 3");
		int[][] result = myJeu.possibleSwitchAroundCroco( 3, 2);
		for (int i = 0; i < result.length; i++) {
		    System.out.println("("+result[i][0]+", "+result[i][1]+")");
        }
		
		System.out.println( "Croco en 2, 3");
		result = myJeu.possibleSwitchAroundCroco( 2, 3);
		for (int i = 0; i < result.length; i++) {
		    System.out.println("("+result[i][0]+", "+result[i][1]+")");
        }
		
		
		System.out.println( "Croco en 1, 1");
		result = myJeu.possibleSwitchAroundCroco( 1, 1);
		for (int i = 0; i < result.length; i++) {
		    System.out.println("("+result[i][0]+", "+result[i][1]+")");
        }
		
		System.out.println( "Croco en 2, 1");
		result = myJeu.possibleSwitchAroundCroco( 2, 1);
		for (int i = 0; i < result.length; i++) {
		    System.out.println("("+result[i][0]+", "+result[i][1]+")");
        }
		
		System.out.println( "Croco en 3, 0");
		result = myJeu.possibleSwitchAroundCroco( 3, 0);
		for (int i = 0; i < result.length; i++) {
		    System.out.println("("+result[i][0]+", "+result[i][1]+")");
        }
		System.out.println( "Croco en 3,0 = "+myJeu.poserPieceRegles(Joueur.rouge, Piece.crocodile, 3, 0));
		for (int i = 0; i < result.length; i++) {
		    if( result[i][0] != -1 ) {
		        System.out.println( "Echange : "+myJeu.switchCrocoGazelleRegles(3,0, result[i][0], result[i][1]));
		        System.out.println( "Apr�s echange avec le croco...\n"+myJeu.displayStr());
		        return;
		    }
        }
		
	}
	public static void testRegions( Jeu p_jeu)
	{
		Plateau myPlateau = p_jeu.getPlateau();
		for( int indR=0; indR < Plateau.nbRegion; indR++) {
			// le Str d'une Piece doit avoir 3 caract�res de long
			StringBuffer strbuf = new StringBuffer();
			strbuf.append( " "+indR+" " );
			
			for( int indC = 0; indC < Plateau.regions[indR].length; indC++) {
				Piece pieceR = new Piece();
				pieceR.m_displayStr = strbuf.toString();
				myPlateau.cases[Plateau.regions[indR][indC][0]][Plateau.regions[indR][indC][1]] = pieceR;
			}
		}
		System.out.println( myPlateau.toString() );
		
		for( int ligne = 0; ligne < Plateau.tailleL; ligne++) {
		    StringBuffer strbuf = new StringBuffer();
		    for( int col = 0; col < Plateau.tailleC; col++) {
		        strbuf.append( Plateau.indRegion[ligne][col] + " ");
		    }
		    System.out.println( strbuf );
		}
		
		Plateau newPlateau = new Plateau( myPlateau );
		
		System.out.println( "Nouvelle versionn" );
		for( int ligne = 0; ligne < Plateau.tailleL; ligne++) {
		    StringBuffer strbuf = new StringBuffer();
		    for( int col = 0; col < Plateau.tailleC; col++) {
		        strbuf.append( Plateau.indRegion[ligne][col] + " ");
		    }
		    System.out.println( strbuf );
		}
	}
}
