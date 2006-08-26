/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author dutech
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for jeu.shazamm.core");
        //$JUnit-BEGIN$
        suite.addTestSuite(T_EtatJeuTest.class);
        suite.addTestSuite(T_PlateauTest.class);
        suite.addTestSuite(T_JoueurTest.class);
        suite.addTestSuite(T_MiseTest.class);
        suite.addTestSuite(T_CarteTest.class);
        suite.addTestSuite(T_JeuTest.class);
        //$JUnit-END$
        return suite;
    }
}