/*
 * Created on Jan 2, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

import game.GameException;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author dutech
 */
public class T_Jeu extends TestCase {
    Jeu g_basic;
    
    Mouvement m_null;
    Mouvement m_rZ11I2;
    Mouvement m_vZ23s24I1;
    Mouvement m_vnopiece;
    Mouvement m_rnopieceI3;
    Mouvement m_rnopiece;
    
    Mouvement m_vG02I1, m_rZ13I3, m_vL03I1, m_rG14I3, m_vG43I3, m_rC33s43I2;
    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        
        g_basic = new Jeu();
        
        m_null = new Mouvement();
        
        m_rZ11I2 = new Mouvement( g_basic.getJoueur(Joueur.rouge), Piece.zebre, 1, 1);
        m_rZ11I2.set( 2 );
        
        m_vZ23s24I1 = new Mouvement( g_basic.getJoueur(Joueur.vert), Piece.zebre, 2, 3);
        m_vZ23s24I1.addEchange( 2, 4);
        m_vZ23s24I1.set(1);
        
        m_vnopiece = new Mouvement( g_basic.getJoueur(Joueur.vert), Piece.zebre, 2, 3);
        m_vnopiece.setNoPiece();
        
        m_rnopieceI3 = new Mouvement( g_basic.getJoueur(Joueur.rouge), Piece.zebre, 2, 3);
        m_rnopieceI3.setNoPiece();
        m_rnopieceI3.set(3);
    
        m_rnopiece = new Mouvement( g_basic.getJoueur(Joueur.rouge), Piece.zebre, 2, 3);
        m_rnopiece.setNoPiece();
    
        // pour test Moves
        m_vG02I1 = new Mouvement( g_basic.getJoueur(Joueur.vert), Piece.gazelle, 0, 2);
        m_vG02I1.set(1);
        m_rZ13I3 = new Mouvement( g_basic.getJoueur(Joueur.rouge), Piece.zebre, 1, 3);
        m_rZ13I3.set(3);
        m_vL03I1 = new Mouvement( g_basic.getJoueur(Joueur.vert), Piece.lion, 0, 3);
        m_vL03I1.set(1);
        m_rG14I3 = new Mouvement( g_basic.getJoueur(Joueur.rouge), Piece.gazelle, 1, 4);
        m_rG14I3.set(3);
        m_vG43I3 = new Mouvement( g_basic.getJoueur(Joueur.vert), Piece.gazelle, 4, 3);
        m_vG43I3.set(3);
        m_rC33s43I2 = new Mouvement( g_basic.getJoueur(Joueur.rouge), Piece.crocodile, 3, 3);
        m_rC33s43I2.addEchange( 4, 3);
        m_rC33s43I2.set(2);    
    }

    public void testApplyMoves()
    {
        // d'abord d'apr�s interne
        Jeu g_interne = new Jeu();
        try {
            FileWriter myFile = new FileWriter( "T_Jeu_2.mvt" );
            g_interne.applyMove( m_rnopieceI3 );
            myFile.write( m_rnopieceI3.toString()+"\n" );
            g_interne.applyMove( m_vG02I1 );
            myFile.write( m_vG02I1.toString()+"\n" );
            g_interne.applyMove( m_rZ13I3 );
            myFile.write( m_rZ13I3.toString()+"\n" );
            g_interne.applyMove( m_vL03I1 );
            myFile.write( m_vL03I1.toString()+"\n" );
            g_interne.applyMove( m_rG14I3 );
            myFile.write( m_rG14I3.toString()+"\n" );
            g_interne.applyMove( m_vG43I3 );
            myFile.write( m_vG43I3.toString()+"\n" );
            g_interne.applyMove( m_rC33s43I2 );
            myFile.write( m_rC33s43I2.toString()+"\n" );
            myFile.close();
        } catch (Exception e) {
            System.out.println( e.getMessage() );
            System.out.println( g_interne.displayStr() );
            Assert.fail();
        }
        
        // puis avec le fichier de test
        Jeu g_file = new Jeu();
        try {
            g_file.applyMoves( "T_Jeu_1.mvt");
        } catch (Exception e) {
            System.out.println( e.getMessage() );
            System.out.println( g_file.displayStr() );
            Assert.fail();
        }
//        System.out.println("Deux jeux egaux...\n"+g_interne.displayStr()
//                +"\n"+g_file.displayStr());
//        System.out.println( "Egaux = " + g_interne.equals(g_file));
//        System.out.println("Deux jeux egaux...\n"+g_interne.displayStr()
//                +"\n"+g_file.displayStr());
        Assert.assertTrue( g_interne.equals(g_file));
        
//      puis avec le fichier sauvegard�
        Jeu g_save = new Jeu();
        try {
            g_save.applyMoves( "T_Jeu_2.mvt");
        } catch (Exception e) {
            System.out.println( e.getMessage() );
            System.out.println( g_save.displayStr() );
            Assert.fail();
        }
//        System.out.println("Deux jeux egaux...\n"+g_interne.displayStr()
//                +"\n"+g_save.displayStr());
//        System.out.println( "Egaux = " + g_interne.equals(g_save));
//        System.out.println("Deux jeux egaux...\n"+g_interne.displayStr()
//                +"\n"+g_save.displayStr());
        Assert.assertTrue( g_interne.equals(g_save));
    }

    public void testApplyMove()
    {
        Jeu g_start = new Jeu( g_basic );
        g_start.setFirstJoueur( Joueur.rouge );
        // doit bouger Impala
        try {
            g_start.applyMove( m_null );
            Assert.fail();
        }
        catch (GameException e) {
            System.out.println(  e.getMessage() );
            Assert.assertTrue( e.getMessage().startsWith( "Pas de Player"));
        }
        try {
            g_start.applyMove( m_rZ11I2 );
            Assert.fail();
        }
        catch (GameException e) {
            System.out.println(  e.getMessage() );
            Assert.assertTrue( e.getMessage().startsWith( "Pose de Piece"));
        }
        try {
            g_start.applyMove( m_rnopiece );
            System.out.println(  g_start.displayStr() );
            Assert.fail();
        }
        catch (GameException e) {
            System.out.println(  e.getMessage() );
            Assert.assertTrue( e.getMessage().startsWith( "Mvt Impala"));
        }
        try {
            g_start.applyMove( m_rnopieceI3 );
            Jeu g_tmp = new Jeu( g_basic );
            g_tmp.setFirstJoueur( Joueur.rouge );
            Assert.assertTrue(!g_tmp.equals(g_start));
            g_tmp.getPlateau().setIndiana(2);
            g_tmp.nextJoueur();
            //System.out.println(g_start.displayStr());
            //System.out.println(g_tmp.displayStr());
            Assert.assertTrue(g_tmp.equals(g_start));
            g_tmp.setDernierMvt( m_rnopieceI3 );
            Assert.assertTrue(g_tmp.equals(g_start));
        }
        catch (GameException e) {
            System.out.println(  e.getMessage() );
            Assert.fail();
        }
        // a Vert de jouer dans g_start
    }

    public void testLoadHistory()
    {
        //avec le fichier sauvegard�
        Jeu g_save = new Jeu();
        ArrayList historique;
        // puis d'apr�s interne
        Jeu g_interne = new Jeu();

        EtatJeu fromHisto;
        try {
            historique = g_save.loadHistory( "T_Jeu_2.mvt");
        
            Iterator iter = historique.iterator();
            
            fromHisto = (EtatJeu) iter.next();
            //System.out.println( "INTERNE----------\n"+g_interne.displayStr() );
            //System.out.println( "HISTORY----------\n"+fromHisto.toString() );
            Assert.assertEquals( g_interne.getState(), fromHisto );
            
            g_interne.applyMove( m_rnopieceI3 );
            fromHisto = (EtatJeu) iter.next();
            //System.out.println( "INTERNE----------\n"+g_interne.displayStr() );
            //System.out.println( "HISTORY----------\n"+fromHisto.toString() );
            Assert.assertEquals( g_interne.getState(), fromHisto );
            g_interne.applyMove( m_vG02I1 );
            fromHisto = (EtatJeu) iter.next();
            //System.out.println( "INTERNE----------\n"+g_interne.displayStr() );
            //System.out.println( "HISTORY----------\n"+fromHisto.toString() );
            Assert.assertEquals( g_interne.getState(), fromHisto );
            g_interne.applyMove( m_rZ13I3 );
            fromHisto = (EtatJeu) iter.next();
            Assert.assertEquals( g_interne.getState(), fromHisto );
            g_interne.applyMove( m_vL03I1 );
            fromHisto = (EtatJeu) iter.next();
            Assert.assertEquals( g_interne.getState(), fromHisto );
            g_interne.applyMove( m_rG14I3 );
            fromHisto = (EtatJeu) iter.next();
            Assert.assertEquals( g_interne.getState(), fromHisto );
            g_interne.applyMove( m_vG43I3 );
            fromHisto = (EtatJeu) iter.next();
            Assert.assertEquals( g_interne.getState(), fromHisto );
            g_interne.applyMove( m_rC33s43I2 );
            fromHisto = (EtatJeu) iter.next();
            Assert.assertEquals( g_interne.getState(), fromHisto );
            
        } catch (Exception e) {
            System.out.println( "EXCEPTION : "+e.getMessage() );
            System.out.println( g_interne.displayStr() );
            Assert.fail();
        }
    }
    
}
