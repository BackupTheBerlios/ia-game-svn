/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import jeu.utils.GameException;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author dutech
 */
public class T_MiseTest extends TestCase {

    Mana myMise;
    
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(T_MiseTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        myMise = new Mana(50);
    }

    /*
     * Class under test for boolean equals(Object)
     */
    public void testEqualsObject()
    {
        Mana meme = new Mana( myMise );
        Assert.assertEquals( myMise, meme );
        
        System.out.println( myMise.toString());
        Mana autre = new Mana( 49 );
        Assert.assertFalse( myMise.equals( autre ));
        autre = new Mana( 50 );
        Assert.assertTrue( myMise.equals( autre ));
    }

    public void testSetBet()
    {
        Mana autre = new Mana( 50 );
        try {
            myMise.setBet( 0 );
            Assert.fail();
        } 
        catch( GameException e) {
            System.out.println( "OK : " + e.getMessage());
        }
        try {
            myMise.setBet( 60 );
            Assert.fail();
        } 
        catch( GameException e) {
            System.out.println( "OK : " + e.getMessage());
        }
        try {
            myMise.setBet( 7 );
            Assert.assertFalse( myMise.equals( autre ));
            autre.setBet( 7 );
            Assert.assertTrue( myMise.equals( autre ));
        }
        catch( GameException e) {
            System.out.println( "OK : " + e.getMessage());
            Assert.fail();
        }
    }

    public void testUpdate()
    {
        System.out.println( myMise.toString());
        Mana autre = new Mana( 50 );
        try {
            myMise.setBet( 7 );
            System.out.println( myMise.toString());
            Assert.assertFalse( myMise.equals( autre ));
            autre.setBet( 7 );
            Assert.assertTrue( myMise.equals( autre ));
            myMise.update();
            System.out.println( myMise.toString());
            Assert.assertFalse( myMise.equals( autre ));
            autre.update();
            Assert.assertTrue( myMise.equals( autre ));
        }
        catch( GameException e) {
            System.out.println( "OK : " + e.getMessage());
            Assert.fail();
        }
    }
}
