/*
 * Created on Dec 28, 2004
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
public class T_Joueur extends TestCase {
    Joueur j_basic;
    Joueur j_vZ5;
    Joueur j_vG5;
    Joueur j_vZ;
    Joueur j_rZ;
    Joueur j_v;
  
    int nbExemple = 6;
    Joueur[] exemple;
    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        
        int indice = 0;
        exemple = new Joueur[nbExemple];
        
        j_basic = new Joueur();
        exemple[indice++] = j_basic;
        
        j_vZ5 = new Joueur();
        initReserve( j_vZ5 );
        j_vZ5.couleur = Joueur.vert;
        j_vZ5.reprendrePiece( Piece.zebre );
        j_vZ5.bonus = 5;
        exemple[indice++] = j_vZ5;
        
        j_vG5 = new Joueur();
        initReserve( j_vG5 );
        j_vG5.couleur = Joueur.vert;
        j_vG5.reprendrePiece( Piece.gazelle );
        j_vG5.bonus = 5;
        exemple[indice++] = j_vG5;
        
        j_vZ = new Joueur();
        initReserve( j_vZ );
        j_vZ.couleur = Joueur.vert;
        j_vZ.reprendrePiece( Piece.zebre );
        exemple[indice++] = j_vZ;
        
        j_v = new Joueur();
        j_v.couleur = Joueur.vert;
        exemple[indice++] = j_v;
        
        j_rZ = new Joueur();
        initReserve( j_rZ );
        j_rZ.couleur = Joueur.rouge;
        j_rZ.reprendrePiece( Piece.zebre );
        exemple[indice++] = j_rZ;
    }
    private void initReserve( Joueur p_joueur )
	{
		p_joueur.reserve = new int[Piece.nbType-1];
		p_joueur.reserve[Piece.zebre] = 5;
		p_joueur.reserve[Piece.gazelle] = 6;
		p_joueur.reserve[Piece.elephant] = 1;
		p_joueur.reserve[Piece.lion] = 1;
		p_joueur.reserve[Piece.crocodile] = 2;
	}

    /**
     * Cloner un joueur.
     */
    public void testJoueurJoueur()
    {
        for( int i=0; i < nbExemple; i++ ) {
            sub1_Joueur( exemple[i] );
        }
    }
    public void sub1_Joueur( Joueur exemple)
    {
        Joueur tmp = new Joueur(exemple);
        System.out.println("Clone avec "+exemple.toString());
        Assert.assertTrue( exemple.equals(tmp));
        Assert.assertEquals( exemple, tmp );
    }

   
    public void testEqualsObject()
    {
        Assert.assertTrue(!j_basic.equals(null));
        Assert.assertEquals(j_basic, j_basic);
        Joueur tmp = new Joueur();
        tmp.couleur = Joueur.vert;
        Assert.assertEquals( j_v, tmp);
        Assert.assertTrue(!j_basic.equals(j_v));
        tmp.couleur = Joueur.rouge;
        Assert.assertTrue(!j_v.equals(tmp));
        Assert.assertTrue(!j_rZ.equals(j_vZ));
        Assert.assertTrue(!j_vZ5.equals(j_vZ));
        Assert.assertTrue(!j_vZ5.equals(j_vG5));
    }

    public void testSame()
    {
        Assert.assertTrue(!j_basic.same(null));
        Assert.assertTrue(j_basic.same(j_basic));
        Joueur tmp = new Joueur();
        tmp.couleur = Joueur.vert;
        Assert.assertTrue( j_v.same(tmp));
        Assert.assertTrue(!j_basic.same(j_v));
        tmp.couleur = Joueur.rouge;
        Assert.assertTrue(!j_v.same(tmp));
        Assert.assertTrue(!j_rZ.same(j_vZ));
        Assert.assertTrue(j_vZ5.same(j_vZ));
        Assert.assertTrue(j_vZ5.same(j_vG5));
    }

    public void testDecode()
    {
        for( int i=0; i < nbExemple; i++ ) {
            sub1_Decode( exemple[i] );
        }
    }
    public void sub1_Decode( Joueur exemple)
    {
        String str = exemple.toString();
        System.out.println( "Decode from "+str);
        System.out.println( "Result is "+Joueur.toString(Joueur.decode(str)));
        Assert.assertEquals( str, Joueur.toString(Joueur.decode(str)));
    }
    
    public void testPrendreReprendre() 
    {
        Joueur j_bis = new Joueur( j_vZ5 );
        j_bis.prendrePiece( Piece.zebre ); // j_vZ5-Z
        Assert.assertFalse( j_vZ5.equals(j_bis));
        Assert.assertTrue( j_vZ5.same( j_bis ));
        Assert.assertTrue( !j_bis.equals( j_vZ));
        Joueur j_ter = new Joueur( j_bis );
        j_bis.reprendrePiece( Piece.zebre ); // j_vZ
        Assert.assertTrue( j_vZ5.equals(j_bis));
        Assert.assertTrue( j_vZ5.same( j_bis ));
        Assert.assertTrue( !j_bis.equals( j_vZ));
        j_ter.reprendrePiece( Piece.gazelle ); // j_vG5
        Assert.assertFalse( j_vZ5.equals(j_ter));
        Assert.assertTrue( j_vZ5.same( j_ter ));
        Assert.assertTrue( !j_ter.equals( j_vZ));
        Assert.assertTrue( j_ter.equals(j_vG5));
        j_ter.prendrePiece( Piece.zebre ); // j_vG5-Z
        Assert.assertFalse( j_vZ5.equals(j_ter));
        Assert.assertTrue( j_vZ5.same( j_ter ));
        Assert.assertTrue( !j_ter.equals( j_vZ));
        Assert.assertTrue( !j_ter.equals(j_vG5));
        
    }
}
