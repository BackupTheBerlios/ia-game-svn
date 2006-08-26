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
public class T_PlateauTest extends TestCase {

    Plateau myPlateau;
    
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(T_PlateauTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        myPlateau = new Plateau(19);
    }

    public void testPlateau()
    {
        System.out.println( "Création" );
        System.out.println( myPlateau.toString() );
    }
    
    public void testEquals()
    {
        System.out.println( "Equals" );
        Plateau autre = new Plateau(19);
        Assert.assertEquals( myPlateau, autre);
        Assert.assertFalse( myPlateau.moveFire( Constantes.VERT ));
        Assert.assertFalse( myPlateau.equals( autre ));
        Assert.assertFalse( myPlateau.moveFire( Constantes.ROUGE ));
        Assert.assertTrue( myPlateau.equals( autre ));
        Assert.assertTrue( (myPlateau.update() == Constantes.NO_COLOR));
        Assert.assertFalse( myPlateau.equals( autre ));
    }

    public void testMoveFire()
    {
        System.out.println( "Move Fire");
        Assert.assertFalse( myPlateau.moveFire( Constantes.VERT ));
        System.out.println( myPlateau.toString() );
        Assert.assertFalse( myPlateau.moveFire( Constantes.VERT ));
        System.out.println( myPlateau.toString() );
        Assert.assertTrue( myPlateau.moveFire( Constantes.VERT ));
        System.out.println( myPlateau.toString() );
    }

    public void testUpdate()
    {
        System.out.println( "Update Plateau");
        Assert.assertTrue( myPlateau.update() == Constantes.NO_COLOR );
        System.out.println( myPlateau.toString() );
        Assert.assertTrue( myPlateau.update()== Constantes.NO_COLOR);
        System.out.println( myPlateau.toString() );
        Assert.assertFalse( myPlateau.moveFire( Constantes.VERT ));
        Assert.assertFalse( myPlateau.moveFire( Constantes.VERT ));
        Assert.assertTrue( myPlateau.moveFire( Constantes.VERT ));
        Assert.assertTrue( myPlateau.update()== Constantes.NO_COLOR);
        System.out.println( myPlateau.toString() );
        //Assert.assertFalse( myPlateau.moveFire( Plateau.VERS_VERT ));
        Assert.assertFalse( myPlateau.update()== Constantes.NO_COLOR);
        System.out.println( myPlateau.toString() );
    }

}
