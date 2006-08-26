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
public class T_EtatJeuTest extends TestCase {

    EtatJeu myEtat;
    
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(T_EtatJeuTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        myEtat = new EtatJeu();
    }
    /*
     * Class under test for boolean equals(Object)
     */
    public void testEqualsObject()
    {
        EtatJeu meme = new EtatJeu( myEtat );
        Assert.assertEquals( myEtat, meme );
        
        EtatJeu autre = new EtatJeu();
        
        System.out.println( myEtat.toString());
        Assert.assertTrue( myEtat.equals( autre ));
    }

}
