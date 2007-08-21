/*
 * Created on Dec 28, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author dutech
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for drolesDZ");
        //$JUnit-BEGIN$
        suite.addTestSuite(T_Mouvement.class);
        suite.addTestSuite(T_EtatJeu.class);
        suite.addTestSuite(T_AlphaBeta.class);
        suite.addTestSuite(T_Piece.class);
        suite.addTestSuite(T_Jeu.class);
        suite.addTestSuite(T_Joueur.class);
        suite.addTestSuite(T_Plateau.class);
        //$JUnit-END$
        return suite;
    }
}