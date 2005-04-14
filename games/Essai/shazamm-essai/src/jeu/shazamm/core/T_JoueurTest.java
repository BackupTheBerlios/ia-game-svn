/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author dutech
 */
public class T_JoueurTest extends TestCase {

    Joueur myJoueur;
    
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(T_JoueurTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        
        myJoueur = new Joueur( 50, Plateau.VERT);
    }

    /*
     * Class under test for boolean equals(Object)
     */
    public void testEqualsObject()
    {
        System.out.println( myJoueur.toString());
        
        Joueur meme = new Joueur( myJoueur );
        Assert.assertEquals( myJoueur, meme );
        
        Joueur autre = new Joueur( 49, Plateau.VERT );
        
        //System.out.println( myJoueur.toString());
        Assert.assertFalse( myJoueur.equals( autre ));
        
        autre = new Joueur( 50, Plateau.VERT );
        Assert.assertTrue( myJoueur.equals( autre ));
    }
    
    public void testPioche()
    {
        System.out.println( "Test Pioche");
        Joueur autre = new Joueur( myJoueur );
        while (autre.piocheCarte()) {
            Assert.assertFalse( myJoueur.equals( autre ));
            System.out.println( "--Pioche Carte--\n"+autre.toString());
        }
        Assert.assertTrue( autre.pioche.isEmpty() );
    }

}
