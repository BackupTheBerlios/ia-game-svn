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
        EtatJeu etat = myJeu.getInitState(50,5);
        EtatJeu apres = null;
        EtatJeu apres1 = null;
        
        //paris identiques
        try {
            etat.getJoueur(Constantes.ROUGE ).mana.setBet( 7 );
            etat.getJoueur( Constantes.VERT ).mana.setBet( 7 );
            etat.getJoueur(Constantes.ROUGE ).etat = Constantes.DOIT_ATTENDRE; 
            etat.getJoueur( Constantes.VERT ).etat = Constantes.DOIT_ATTENDRE;
        }
        catch (GameException e) {
            System.out.println( "OK : " + e.getMessage());
            Assert.fail();
        }
        Assert.assertEquals( etat.getJoueur( Constantes.ROUGE).mana, 
                etat.getJoueur( Constantes.VERT ).mana );
        apres = new EtatJeu( etat ); //avant
        myJeu.update(etat);
        System.out.println( etat.toString());
        Assert.assertEquals( etat.zePlateau, apres.zePlateau);
        
        //paris tjs identiques
        try {
            etat.getJoueur( Constantes.ROUGE ).mana.setBet( 17 );
            etat.getJoueur( Constantes.VERT ).mana.setBet( 17 );
            etat.getJoueur(Constantes.ROUGE ).etat = Constantes.DOIT_ATTENDRE; 
            etat.getJoueur( Constantes.VERT ).etat = Constantes.DOIT_ATTENDRE;
        }
        catch (GameException e) {
            System.out.println( "OK : " + e.getMessage());
            Assert.fail();
        }
        Assert.assertEquals( etat.getJoueur( Constantes.ROUGE).mana, 
                etat.getJoueur( Constantes.VERT ).mana );
        apres1 = new EtatJeu( etat );
        myJeu.update(etat);
        System.out.println( etat.toString() );
        Assert.assertEquals( etat.zePlateau, apres1.zePlateau);
        Assert.assertEquals( apres.zePlateau, apres1.zePlateau);
        
//      paris plus identiques
        try {
            etat.getJoueur( Constantes.ROUGE ).mana.setBet( 17 );
            etat.getJoueur( Constantes.VERT ).mana.setBet( 7 );
            etat.getJoueur(Constantes.ROUGE ).etat = Constantes.DOIT_ATTENDRE; 
            etat.getJoueur( Constantes.VERT ).etat = Constantes.DOIT_ATTENDRE;
        }
        catch (GameException e) {
            System.out.println( "OK : " + e.getMessage());
            Assert.fail();
        }
        Assert.assertFalse( etat.getJoueur( Constantes.ROUGE).mana.equals( etat.getJoueur( Constantes.VERT ).mana ) );
        apres1 = new EtatJeu( etat ); // jeu avant!
        myJeu.update(etat);
        System.out.println( "--etat--\n"+etat.toString() );
        Assert.assertFalse( etat.zePlateau.equals(apres1.zePlateau));
        Assert.assertTrue( apres.zePlateau.equals( apres1.zePlateau));
        
    }

    public void testCartes()
    {
        EtatJeu etat = myJeu.getInitState(50,5);
        Joueur rouge = etat.getJoueur( Constantes.ROUGE );
        Joueur vert = etat.getJoueur( Constantes.VERT );
        
        // fait piocher les deux joueurs
        for( int iJoueur = 0; iJoueur < etat.zeJoueurs.length; iJoueur++) {
            while (etat.zeJoueurs[iJoueur].piocheCarte()) {
            }
        }
        
        // choissisent des cartes
        
        try {
            Assert.assertTrue( rouge.joueCarte( 7 ));
            rouge.mana.setBet( 3 );
            //Assert.assertTrue( vert.joueCarte( 7 ));
            Assert.assertTrue( vert.joueCarte( 1 ));
            vert.mana.setBet( 10 );
            //etat.getJoueur(Constantes.ROUGE ).etat = Constantes.DOIT_ATTENDRE; 
            //etat.getJoueur( Constantes.VERT ).etat = Constantes.DOIT_ATTENDRE;
            System.out.println( "--après mises--\n"+etat.toString() );
        } catch (GameException e) {
            e.printStackTrace();
            System.out.println( e.getMessage());
            Assert.fail();
        }
        
        EtatJeu etatBis = new EtatJeu( etat );
        Joueur rougeBis = etatBis.getJoueur( Constantes.ROUGE );
        Joueur vertBis = etatBis.getJoueur( Constantes.VERT );
        
        try {
            myJeu.addCartesToPlay( etat, rouge );
            System.out.println( "--après ROUGE--\n"+etat.toString() );
            myJeu.addCartesToPlay( etat, vert );
            System.out.println( "--après VERT--\n"+etat.toString() );
            //etat = myJeu.etat;
            
        } catch (GameException e1) {
            e1.printStackTrace();
            System.out.println( e1.getMessage());
            Assert.fail();
        }        
        
        try {
            //myJeu.setState( etatBis );
            System.out.println( "##### Change ordre des cartes #####");
            myJeu.addCartesToPlay( etatBis, vertBis );
            System.out.println( "--après VERT--\n"+etatBis.toString() );
            myJeu.addCartesToPlay( etatBis, rougeBis );
            System.out.println( "--après ROUGE--\n"+etatBis.toString() );
            //etatBis = myJeu.etat;

        } catch (GameException e2) {
            e2.printStackTrace();
            System.out.println( e2.getMessage());
        }        
        Assert.assertTrue( etat.equals( etatBis ));
        
        //myJeu.setState( etat );
        Assert.assertTrue( myJeu.applyCartes( etat ));
        
        
        EtatJeu etatAvant = new EtatJeu( etat );
        etat.getJoueur(Constantes.ROUGE ).etat = Constantes.DOIT_ATTENDRE; 
        etat.getJoueur( Constantes.VERT ).etat = Constantes.DOIT_ATTENDRE;

        myJeu.update( etat );
        System.out.println( "-- AVANT --\n"+etatAvant.toString() );
        System.out.println( "-- FIN --\n"+etat.toString() );
        //Il faudrait maintenant finir le tour de jeu et faire d'autres cartes
        //Et gérer les états des joueurs...
        
        
    }
}
