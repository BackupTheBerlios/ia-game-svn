/*
 * Created on Jan 12, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package testDrolesZebres;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

import drolesDZ.AlphaBeta;
import drolesDZ.GenerateurMouvement;
import drolesDZ.Jeu;

/**
 * @author dutech
 */
public class TestAlphaBeta {

    AlphaBeta searchEngine = new AlphaBeta( new GenerateurMouvement() );
    
    public static void main(String[] args)
    throws Exception
    {
        // nb arguments == 2
        if( args.length != 2) {
            System.err.println( "usage : TestAlphaBeta fileName nbEssaiMax");
            System.exit( -1 );
        }
        String fileName = new String( args[0]);
        int nbEssaiMax = Integer.parseInt( args[1] );
        TestAlphaBeta prog = new TestAlphaBeta();
        
        // cherche le nombre de mouvement max
        int nbMoveMax = prog.getNbMoveMax( fileName );
        
        long lastSearchTime = 0;
        int nbSearch = 0;
        while( (nbSearch < nbEssaiMax) && (lastSearchTime < Long.MAX_VALUE)) {
            lastSearchTime = prog.search( fileName, nbMoveMax - nbSearch );
            nbSearch++;
        }
    }

    /**
     * Cherche à résoudre le Jeu après avoir joué nbMove coups.
     * @param fileName
     * @param nbMove
     * @return Temps de recherche en ms.
     * @throws Exception
     */
    public long search( String fileName, int nbMove )
    throws Exception
    {
        // prépare le Jeu
        Jeu zeJeu = new Jeu();
        zeJeu.applyMoves( fileName, nbMove );
        
        System.out.println( "Recherche après "+nbMove+" mouvements");
        
        // Date de début -- Best
//        System.out.println("--- Recherche de la valeur ---");
//        Date dateDebut = new Date();
//        int val = searchEngine.findBest( zeJeu.getJoueur(zeJeu.getTour()), zeJeu, 50);
//        Date dateFin = new Date();
//        long timeSpent = dateFin.getTime() - dateDebut.getTime();
//        long nbMin = timeSpent / (1000*60);
//        System.out.println( "   "+searchEngine.nbVisitedStates+ " mouvements évalués en "+timeSpent+"ms ("+nbMin+" min)");
//        System.out.println( "   val = " + val);
        
        
        // Date de début -- BestMoves
        System.out.println( "--- Recherche de la suite de meilleurs Mvt ---");
        Date dateDebutMvt = new Date();
        int valMvt = searchEngine.findBestMoves( zeJeu.getJoueur(zeJeu.getTour()), zeJeu, 50);
        Date dateFinMvt = new Date();
        long timeSpentMvt = dateFinMvt.getTime() - dateDebutMvt.getTime();
        long nbMinMvt = timeSpentMvt / (1000*60);
        System.out.println( "   "+searchEngine.nbVisitedStates+ " mouvements évalués en "+timeSpentMvt+"ms ("+nbMinMvt+" min)");
        System.out.println( "   val = " + valMvt);
        System.out.println( zeJeu.getState().displayBestMoves() );
// 
        // Date de début -- BestMovesArray
//        System.out.println( "--- Recherche de la suite de meilleurs Mvt Array ---");
//        Date dateDebutMvtArray = new Date();
//        int valMvtArray = searchEngine.findBestMovesArray( zeJeu.getJoueur(zeJeu.getTour()), zeJeu, 50);
//        Date dateFinMvtArray = new Date();
//        long timeSpentMvtArray = dateFinMvtArray.getTime() - dateDebutMvtArray.getTime();
//        long nbMinMvtArray = timeSpentMvtArray / (1000*60);
//        System.out.println( "   "+searchEngine.nbVisitedStates+ " mouvements évalués en "+timeSpentMvtArray+"ms ("+nbMinMvtArray+" min)");
//        System.out.println( "   val = " + valMvtArray);
//        for (int ind = 0; ind < searchEngine.bestMoves.length; ind++) {
//            if( searchEngine.bestMoves[ind] != null ) {
//                System.out.println( ind + " = " + searchEngine.bestMoves[ind].toString());
//            }
//        }
        
        
        return 0;
    }
    /**
     * Cherche le nb max de Mouvement contenus dans un fichier.
     * @param fileName
     * @return nombre de Mouvement
     * @throws Exception
     */
    public int getNbMoveMax( String fileName )
    throws Exception
    {
        FileReader myFile = new FileReader( fileName );
	    BufferedReader myReader = new BufferedReader( myFile );
	    
	    String lineRead = myReader.readLine();
	    
	    int nbLu = 0;
	    while( lineRead != null ) {
	        nbLu ++;
	        lineRead = myReader.readLine();
	    }
	    
	    myFile.close();
	    return nbLu;
    }
}
