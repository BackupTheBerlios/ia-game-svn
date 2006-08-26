/*
 * Created on Apr 9, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import junit.framework.Assert;
import junit.framework.TestCase;
import jeu.shazamm.cartes.*;

/**
 * @author dutech
 */
public class T_CarteTest extends TestCase {

    Carte mu1, mu2, boa1, boa2, mil1;
    
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(T_CarteTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        mu1 = new Mutisme( Constantes.VERT );
        mu2 = new Mutisme( Constantes.ROUGE );
        boa1 = new BoostAttaque( Constantes.ROUGE );
        boa2 = new BoostAttaque( Constantes.ROUGE );
        mil1 = new Milieu( Constantes.VERT );
        
    }

    public void testCompareTo()
    {
        Assert.assertTrue( mu1.compareTo(mu2) == 0 );
        Assert.assertTrue( mu1.compareTo(boa1) < 0 );
        Assert.assertTrue( boa1.compareTo(mu2) > 0 );
        Assert.assertTrue( boa1.compareTo(boa2) == 0 );
        Assert.assertTrue( mil1.compareTo(boa2) < 0 );
    }

}
