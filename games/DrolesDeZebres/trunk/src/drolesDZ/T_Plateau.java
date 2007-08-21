/*
 * Created on Jan 1, 2005
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
public class T_Plateau extends TestCase {
    Joueur j_v, j_r;
    Piece p_rZ3, p_vZ3;
    Plateau b_basic;
    Plateau b_first;
    Plateau b_second;
    
    int nbExemple = 3;
    Plateau[] exemple;
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        
        j_v = new Joueur();
        j_v.couleur = Joueur.vert;
        j_r = new Joueur();
        j_r.couleur = Joueur.rouge;
        
        p_rZ3 = new Piece();
        p_rZ3.type = Piece.zebre;
        p_rZ3.m_joueur = j_r;
        p_rZ3.val = 3;
        p_rZ3.setDisplayStr();
        
        p_vZ3 = new Piece();
        p_vZ3.type = Piece.zebre;
        p_vZ3.m_joueur = j_v;
        p_vZ3.val = 3;
        p_vZ3.setDisplayStr();
    
        int indice = 0;
        exemple = new Plateau[nbExemple];
        
        b_basic = new Plateau();
        exemple[indice++] = b_basic;
        
        b_first = new Plateau();
        for( int line=0; line<Plateau.tailleL; line++) {
            b_first.cases[line][1] = p_rZ3;
        }
        for( int col=0; col < Plateau.tailleC; col++) {
            b_first.cases[1][col] = p_vZ3;
        }
        exemple[indice++] = b_first;
        
        b_second = new Plateau();
        for( int line=0; line<Plateau.tailleL; line++) {
            b_second.cases[line][1] = p_rZ3;
        }
        for( int col=0; col < Plateau.tailleC; col++) {
            b_second.cases[1][col] = p_vZ3;
        }
        exemple[indice++] = b_second;
    }

    /*
     * Clonage
     */
    public void testPlateauPlateau()
    {
        for( int i=0; i < nbExemple; i++ ) {
            sub1_Plateau( exemple[i] );
        }
    }
    public void sub1_Plateau( Plateau exemple)
    {
        Plateau tmp = new Plateau(exemple);
        System.out.println("Clone avec "+exemple.toString());
        Assert.assertTrue( exemple.equals(tmp));
        Assert.assertEquals( exemple, tmp );
    }

    public void testAdvanceIndiana()
    {
        b_first.setIndiana(0);
        Assert.assertTrue(!b_first.equals( b_second ));
        b_second.setIndiana(0);
        Assert.assertTrue(b_first.equals( b_second ));
        
        for( int i=1; i< 30; i++ ) {
            b_first.setIndiana(i);
            b_second.advanceIndiana(1);
            System.out.println( "avance "+ i + " first="+b_first.posIndiana+" second="+b_second.posIndiana);
            Assert.assertTrue(b_first.equals( b_second ));
            
            int line = b_first.getLineFromIndiana();
            int col = b_first.getColFromIndiana();
            if( b_first.isLineFull(line)) {
                System.out.println( "ligne pleine");
                Assert.assertTrue( line == 1);
            }
            if( b_first.isColFull(col)) {
                System.out.println( "col pleine");
                Assert.assertTrue( col == 1);
            }
            
        }
    }
    public void testSwitchPieces()
    {
        b_first.switchPieces( 0,0,0,0);
        Assert.assertTrue(b_first.equals( b_second ));
        b_first.switchPieces( 0,0,3,3);
        Assert.assertTrue(b_first.equals( b_second ));
        b_first.switchPieces( 1,3,1,4);
        Assert.assertTrue(b_first.equals( b_second ));
        b_first.switchPieces( 1,1,2,1);
        Assert.assertTrue(!b_first.equals( b_second ));
        b_first.switchPieces( 1,1,2,1);
        Assert.assertTrue(b_first.equals( b_second ));
    }

    public void testEquals()
    {
        Assert.assertTrue(!b_basic.equals(null));
        Assert.assertEquals(b_basic, b_basic);
        Plateau tmp = new Plateau();
        Assert.assertEquals(b_basic, tmp);
        
        Assert.assertEquals( b_first, b_first);
        Assert.assertEquals( b_first, b_second);
    }

}
