/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import java.io.BufferedReader;
import java.io.FileReader;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author dutech
 */
public class T_CoupTest extends TestCase {

    Jeu myJeu;
    EtatJeu etat;
    
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(T_CoupTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
     
        myJeu = new Jeu();
        etat = myJeu.getInitState(50,5);
        
//      fait piocher les deux joueurs
        for( int iJoueur = 0; iJoueur < etat.zeJoueurs.length; iJoueur++) {
            while (etat.zeJoueurs[iJoueur].piocheCarte()) {
            }
        }
        
    }

    public void testExtractFrom()
    {
        
        // ouvre fichier en lecture
        try {
            FileReader myFile = new FileReader( "data/test_coups.sha" );
            BufferedReader myReader = new BufferedReader( myFile );
            
            String lineRead = myReader.readLine();
            CoupFactory coupReader = new CoupFactory();
            
            int nbLu = 0;
            while(lineRead != null) {
                nbLu ++;
                System.out.println( ">>>"+lineRead);
                Coup aCoup = coupReader.extractFrom( lineRead );
                System.out.println( nbLu + ">>" + aCoup.toString());
                
                EtatJeu etatBis = new EtatJeu( etat );
                aCoup.apply( myJeu, etatBis );
                System.out.println( "---> aCoup.apply()\n" );
                System.out.println( etatBis.toString() );
                
                EtatJeu etatTer = myJeu.apply( etat, aCoup );
                System.out.println( "---> myJeu.apply()\n" );
                System.out.println( etatTer.toString() );
                
                Assert.assertEquals(etatBis, etatTer);
                
                lineRead = myReader.readLine();
            }
            
            myFile.close();
        }
        catch (Exception e) {
            System.out.println( e.getMessage() );
            Assert.fail();
        }
    }

}
