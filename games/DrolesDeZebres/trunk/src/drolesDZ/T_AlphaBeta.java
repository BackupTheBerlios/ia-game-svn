/*
 * Created on Jan 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author dutech
 */
public class T_AlphaBeta extends TestCase {
    Jeu g_oneLeft, g_twoLeft;
    Jeu g_complete, g_incomplete;
    AlphaBeta ab_basic;
    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        
        ab_basic = new AlphaBeta( new GenerateurMouvement());
        
        try {
            g_oneLeft = new Jeu();
            g_oneLeft.setFirstJoueur( Joueur.vert );
            g_oneLeft.applyMoves( "T_AB_oneLeft.mvt");
        } 
        catch (GameException e) {
            System.out.println(  e.getMessage() );
            System.out.println( g_oneLeft.displayStr());
            Assert.fail();
        }
        try {
            g_twoLeft = new Jeu();
            g_twoLeft.setFirstJoueur( Joueur.vert );
            g_twoLeft.applyMoves( "T_AB_twoLeft.mvt");
        } 
        catch (GameException e) {
            System.out.println(  e.getMessage() );
            System.out.println( g_twoLeft.displayStr());
            Assert.fail();
        }
        try {
            g_complete = new Jeu();
            g_complete.setFirstJoueur( Joueur.vert );
            g_complete.applyMoves( "T_AB_complete.mvt");
        } 
        catch (GameException e) {
            System.out.println(  e.getMessage() );
            System.out.println( g_complete.displayStr());
            Assert.fail();
        }
        try {
            g_incomplete = new Jeu();
            g_incomplete.setFirstJoueur( Joueur.vert );
            g_incomplete.applyMoves( "T_AB_incomplete.mvt");
        } 
        catch (GameException e) {
            System.out.println(  e.getMessage() );
            System.out.println( g_incomplete.displayStr());
            Assert.fail();
        }
    }

//    public void testAddEvalues()
//    {
//        // rien d'evalué
//        AlphaBeta tmpAB = new AlphaBeta( new GenerateurMouvement() );
//        Assert.assertTrue( tmpAB.listeJeuEvalues.isEmpty() );
//        tmpAB.addEvalues( g_complete );
//        Assert.assertTrue(!tmpAB.listeJeuEvalues.isEmpty() );
//        //System.out.println( tmpAB.displayEvalues() );
//        ArrayList tmpEvalues = (ArrayList) tmpAB.listeJeuEvalues.clone();
//        tmpAB.addEvalues( g_complete );
//        Assert.assertTrue( tmpAB.listeJeuEvalues.equals( tmpEvalues) );
//        tmpAB.addEvalues( g_oneLeft );
//        Assert.assertTrue( !tmpAB.listeJeuEvalues.equals( tmpEvalues) );
//        //System.out.println( tmpAB.displayEvalues() );
//    }

    public void testLookMax()
    throws Exception
    {
        // valeur de g_complete
        g_complete.computeBonusScore();
        //System.out.println( g_complete.displayStr());
        int val_complete = g_complete.getValue( g_complete.getJoueur( Joueur.rouge) );
        int val_max = ab_basic.lookForMax( g_complete.getJoueur( Joueur.rouge), g_complete, 2, Integer.MIN_VALUE, Integer.MAX_VALUE);
        int val_maxMvt = ab_basic.lookForMaxMoves( g_complete.getJoueur( Joueur.rouge), g_complete, 2, Integer.MIN_VALUE, Integer.MAX_VALUE);
        //System.out.println("complete = "+val_complete+", max = "+val_max);
        Assert.assertTrue( val_complete == val_max );
        Assert.assertTrue( val_max == val_maxMvt);
        ab_basic.displayEvalues();
        System.out.println( g_complete.getState().displayBestMoves());
        
        subABTwoLeft( val_complete );
        subABIncomplete( val_complete );
 
        System.out.println( "Test final ");
        System.out.println( "max = "+ab_basic.findBestMoves( g_incomplete.getJoueur( Joueur.vert), g_incomplete, 20 ));
        System.out.println( g_incomplete.getState().displayBestMoves());
        System.out.println( "nbVisitedState = " + ab_basic.nbVisitedStates);
    }
    void subABTwoLeft( int val_complete )
     throws GameException
     {
         System.out.println( "Test sur g_oneLeft");
         // valeur de g_oneLeft
         g_oneLeft.computeBonusScore();
         int val_oneLeft = g_oneLeft.getValue( g_oneLeft.getJoueur( Joueur.rouge) );
         int val_max = ab_basic.lookForMax( g_complete.getJoueur( Joueur.rouge), g_oneLeft, 2, Integer.MIN_VALUE, Integer.MAX_VALUE);
         int val_maxMvt = ab_basic.lookForMaxMoves( g_complete.getJoueur( Joueur.rouge), g_oneLeft, 2, Integer.MIN_VALUE, Integer.MAX_VALUE);
         //System.out.println("complete = "+val_complete+", oneLeft = "+val_oneLeft+", max = "+val_max);
         Assert.assertTrue( val_complete == val_max );
         Assert.assertTrue( val_max == val_maxMvt);
         System.out.println( g_oneLeft.getState().displayBestMoves());
         
         System.out.println( "Test sur g_twoLeft");
         //valeur de g_twoLeft
         g_twoLeft.computeBonusScore();
         //System.out.println( g_twoLeft.displayStr() );
         int val_twoLeft = g_twoLeft.getValue( g_twoLeft.getJoueur( Joueur.rouge) );
         val_max = ab_basic.lookForMax( g_complete.getJoueur( Joueur.rouge), g_twoLeft, 2, Integer.MIN_VALUE, Integer.MAX_VALUE);
         val_maxMvt = ab_basic.lookForMaxMoves( g_complete.getJoueur( Joueur.rouge), g_twoLeft, 2, Integer.MIN_VALUE, Integer.MAX_VALUE);
         System.out.println("complete = "+val_complete+", oneLeft = "+val_oneLeft+", twoLeft = "+val_twoLeft+", max = "+val_max);
         Assert.assertTrue( val_complete <= val_max );
         Assert.assertTrue( val_max == val_maxMvt);
         System.out.println( g_twoLeft.getState().displayBestMoves());
         //si on va pas assez loin
         val_max = ab_basic.lookForMax( g_complete.getJoueur( Joueur.rouge), g_twoLeft, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
         val_maxMvt = ab_basic.lookForMaxMoves( g_complete.getJoueur( Joueur.rouge), g_twoLeft, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
         System.out.println("complete = "+val_complete+", oneLeft = "+val_oneLeft+", twoLeft = "+val_twoLeft+", max = "+val_max);
         Assert.assertTrue( val_oneLeft <= val_max );
         Assert.assertTrue( val_max == val_maxMvt);
         System.out.println( g_twoLeft.getState().displayBestMoves());
     }
    void subABIncomplete( int val_complete )
    throws GameException
    {
        System.out.println( "Test sur g_incomplete");
        //valeur de g_incomplete
        g_incomplete.computeBonusScore();
        //System.out.println( g_twoLeft.displayStr() );
        int val_incomplete = g_incomplete.getValue( g_twoLeft.getJoueur( Joueur.rouge) );
        int val_max = ab_basic.lookForMax( g_complete.getJoueur( Joueur.rouge), g_incomplete, 9, Integer.MIN_VALUE, Integer.MAX_VALUE);
        int val_maxMvt = ab_basic.lookForMaxMoves( g_complete.getJoueur( Joueur.rouge), g_incomplete, 9, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println("incomplete = "+val_incomplete+", max = "+val_max+", val_maxMvt = "+val_maxMvt);
        Assert.assertTrue( val_complete <= val_max );
        Assert.assertTrue( val_max == val_maxMvt);
        System.out.println( g_incomplete.getState().displayBestMoves());
       
        val_max = ab_basic.lookForMax( g_complete.getJoueur( Joueur.vert), g_incomplete, 9, Integer.MIN_VALUE, Integer.MAX_VALUE);
        val_maxMvt = ab_basic.lookForMaxMoves( g_complete.getJoueur( Joueur.vert), g_incomplete, 9, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println("incomplete = "+val_incomplete+", max = "+val_max+", val_maxMvt = "+val_maxMvt);
        Assert.assertTrue( val_complete <= val_max );
        Assert.assertTrue( val_max == val_maxMvt);
        System.out.println( g_incomplete.getState().displayBestMoves());
    }
}
