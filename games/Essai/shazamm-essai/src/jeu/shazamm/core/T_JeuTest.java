/*
 * Created on Apr 9, 2005
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
public class T_JeuTest extends TestCase {

    Jeu myJeu;
    
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(T_JeuTest.class);
    }

    protected void setUp() throws Exception
    {
        myJeu = new Jeu();
    }
    
    public void testUpdate()
    {
        EtatJeu etat = myJeu.getInitState();
        EtatJeu apres = null;
        EtatJeu apres1 = null;
        
        //paris identiques
        try {
            etat.getJoueur( Plateau.ROUGE ).mana.setBet( 7 );
            etat.getJoueur( Plateau.VERT ).mana.setBet( 7 );
        }
        catch (GameException e) {
            System.out.println( "OK : " + e.getMessage());
            Assert.fail();
        }
        Assert.assertEquals( etat.getJoueur( Plateau.ROUGE).mana, 
                etat.getJoueur( Plateau.VERT ).mana );
        apres = myJeu.update( etat );
        System.out.println( etat.toString());
        Assert.assertEquals( etat.zePlateau, apres.zePlateau);
        
        //paris tjs identiques
        try {
            etat.getJoueur( Plateau.ROUGE ).mana.setBet( 17 );
            etat.getJoueur( Plateau.VERT ).mana.setBet( 17 );
        }
        catch (GameException e) {
            System.out.println( "OK : " + e.getMessage());
            Assert.fail();
        }
        Assert.assertEquals( etat.getJoueur( Plateau.ROUGE).mana, 
                etat.getJoueur( Plateau.VERT ).mana );
        apres1 = myJeu.update( etat );
        System.out.println( etat.toString() );
        Assert.assertEquals( etat.zePlateau, apres1.zePlateau);
        Assert.assertEquals( apres.zePlateau, apres1.zePlateau);
        
//      paris plus identiques
        try {
            etat.getJoueur( Plateau.ROUGE ).mana.setBet( 17 );
            etat.getJoueur( Plateau.VERT ).mana.setBet( 7 );
        }
        catch (GameException e) {
            System.out.println( "OK : " + e.getMessage());
            Assert.fail();
        }
        Assert.assertFalse( etat.getJoueur( Plateau.ROUGE).mana.equals( etat.getJoueur( Plateau.VERT ).mana ) );
        apres1 = myJeu.update( etat );
        System.out.println( "--etat--\n"+etat.toString() );
        Assert.assertTrue( etat.zePlateau.equals(apres1.zePlateau));
        Assert.assertFalse( apres.zePlateau.equals( apres1.zePlateau));
        
    }

}
