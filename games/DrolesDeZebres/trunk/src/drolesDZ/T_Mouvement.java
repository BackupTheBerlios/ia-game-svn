/*
 * Created on Dec 27, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author dutech
 */
public class T_Mouvement extends TestCase {
    Jeu zeJeu;
    
    Mouvement m_null;
    Mouvement m_rZ11I2;
    Mouvement m_vZ23s24I1;
    Mouvement m_vnopiece;
    Mouvement m_rnopieceI3;
    
    int nbExemple = 5;
    Mouvement[] exemple;
    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        zeJeu = new Jeu();
        
        int indice = 0;
        exemple = new Mouvement[nbExemple];
        
        m_null = new Mouvement();
        exemple[indice++] = m_null;
        
        m_rZ11I2 = new Mouvement( zeJeu.getJoueur(Joueur.rouge), Piece.zebre, 1, 1);
        m_rZ11I2.set( 2 );
        exemple[indice++] = m_rZ11I2;
        
        m_vZ23s24I1 = new Mouvement( zeJeu.getJoueur(Joueur.vert), Piece.zebre, 2, 3);
        m_vZ23s24I1.addEchange( 2, 4);
        m_vZ23s24I1.set(1);
        exemple[indice++] = m_vZ23s24I1;
        
        m_vnopiece = new Mouvement( zeJeu.getJoueur(Joueur.vert), Piece.zebre, 2, 3);
        m_vnopiece.setNoPiece();
        exemple[indice++] = m_vnopiece;
        
        m_rnopieceI3 = new Mouvement( zeJeu.getJoueur(Joueur.vert), Piece.zebre, 2, 3);
        m_rnopieceI3.setNoPiece();
        m_rnopieceI3.set(3);
        exemple[indice++] = m_rnopieceI3;
            
    }
    
    public void testEquals()
    {
        Assert.assertTrue(!m_rZ11I2.equals(null));
        Assert.assertEquals(m_rZ11I2, m_rZ11I2);
        Mouvement move = new Mouvement( zeJeu.getJoueur(Joueur.rouge), Piece.zebre, 1, 1);
        move.set( 2 );
        Assert.assertEquals( m_rZ11I2, move);
        Assert.assertTrue(!m_rZ11I2.equals(m_vZ23s24I1));
        Assert.assertTrue(!m_rZ11I2.equals(m_rnopieceI3));
    }
    /**
     * Cloner un mouvement.
     */
    public void testMouvement()
    {
        for( int i=0; i < nbExemple; i++ ) {
            sub1_Mouvement( exemple[i] );
        }
    }
    public void sub1_Mouvement( Mouvement mvt)
    {
        Mouvement move = new Mouvement(mvt);
        System.out.println("Equals avec "+mvt.toString());
        Assert.assertTrue( mvt.equals(move));
        Assert.assertEquals( mvt, move );
    }
    /**
     * Extraire un mouvement d'une String...
     */
    public void testExtractFrom()
    {
        for( int i=0; i < nbExemple; i++ ) {
            sub1_ExtractFrom( exemple[i] );
        }
    }
    public void sub1_ExtractFrom( Mouvement mvt)
    {
        String str = mvt.toString();
        Mouvement move = new Mouvement();
        System.out.println( "Extract from "+str);
        move.extractFrom( str, zeJeu );
        System.out.println( "Result is "+move.toString());
        Assert.assertEquals( mvt, move );
    }
}
