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
public class T_Piece extends TestCase {
    Joueur j_v, j_r;
    Piece p_basic;
    Piece p_rZ3, p_vZ3;
    Piece p_rZ1;
    Piece p_rG3;
    Piece p_v;
    
    int nbExemple = 6;
    Piece[] exemple;
    
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
        
        exemple = new Piece[nbExemple];
        int indice = 0;
        p_basic = new Piece();
        p_basic.setDisplayStr();
        exemple[indice++] = p_basic;
        
        p_rZ3 = new Piece();
        p_rZ3.type = Piece.zebre;
        p_rZ3.m_joueur = j_r;
        p_rZ3.val = 3;
        p_rZ3.setDisplayStr();
        exemple[indice++] = p_rZ3;
        
        p_vZ3 = new Piece();
        p_vZ3.type = Piece.zebre;
        p_vZ3.m_joueur = j_v;
        p_vZ3.val = 3;
        p_vZ3.setDisplayStr();
        exemple[indice++] = p_vZ3;
        
        p_rZ1 = new Piece();
        p_rZ1.type = Piece.zebre;
        p_rZ1.m_joueur = j_r;
        p_rZ1.val = 1;
        p_rZ1.setDisplayStr();
        exemple[indice++] = p_rZ1;
        
        p_rG3 = new Piece();
        p_rG3.type = Piece.gazelle;
        p_rG3.m_joueur = j_r;
        p_rG3.val = 3;
        p_rG3.setDisplayStr();
        exemple[indice++] = p_rG3;
        
        p_v = new Piece();
        p_v.m_joueur = j_v;
        p_v.setDisplayStr();
        exemple[indice++] = p_v;
    }

    /*
     * Clonage
     */
    public void testPiecePiece()
    {
        for( int i=0; i < nbExemple; i++ ) {
            sub1_Piece( exemple[i] );
        }
    }
    public void sub1_Piece( Piece exemple)
    {
        Piece tmp = new Piece(exemple);
        System.out.println("Clone avec "+exemple.displayStr());
        Assert.assertTrue( exemple.equals(tmp));
        Assert.assertEquals( exemple, tmp );
    }

    public void testDecode()
    {
        for( int i=0; i < nbExemple; i++ ) {
            sub1_Decode( exemple[i] );
        }
    }
    public void sub1_Decode( Piece exemple)
    {
        String str = exemple.toString();
        System.out.println( "Decode from "+str);
        System.out.println( "Result is "+Piece.toString(Piece.decode(str)));
        Assert.assertEquals( str, Piece.toString(Piece.decode(str)));
    }

    public void testEquals()
    {
        Assert.assertTrue(!p_basic.equals(null));
        Assert.assertEquals(p_basic, p_basic);
        Piece tmp = new Piece();
        tmp.m_joueur = j_v;
        Assert.assertEquals( tmp, p_v);
        Assert.assertTrue(!p_vZ3.equals(tmp));
        tmp.type = Piece.zebre;
        Assert.assertTrue(!p_vZ3.equals(tmp));
        Assert.assertTrue(!p_v.equals(tmp));
        tmp.val = 3;
        Assert.assertTrue(p_vZ3.equals(tmp));
        Assert.assertTrue(!p_v.equals(tmp));
        
        Assert.assertTrue(!p_rZ3.equals(p_vZ3));
        Assert.assertTrue(!p_rZ3.equals(p_rG3));
        Assert.assertTrue(!p_rZ3.equals(p_rZ1));
        Assert.assertTrue(!p_v.equals(p_vZ3));   
    }
}