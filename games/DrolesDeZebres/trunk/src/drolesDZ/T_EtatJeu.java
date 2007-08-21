/*
 * Created on Jan 2, 2005
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
public class T_EtatJeu extends TestCase {
    Joueur j_v, j_r;
    Piece p_rZ3, p_vZ3;
    Plateau b_first;
    Mouvement m_rZ11I2;
    
    EtatJeu e_basic;
    EtatJeu e_first;
    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
 
        j_v = new Joueur();
        j_v.couleur = Joueur.vert;
        initReserve( j_v );
        j_r = new Joueur();
        j_r.couleur = Joueur.rouge;
        initReserve( j_r );
        
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
        
        b_first = new Plateau();
        for( int line=0; line<Plateau.tailleL; line++) {
            b_first.cases[line][1] = p_rZ3;
        }
        for( int col=0; col < Plateau.tailleC; col++) {
            b_first.cases[1][col] = p_vZ3;
        }
        
        m_rZ11I2 = new Mouvement( j_r, Piece.zebre, 1, 1);
        m_rZ11I2.set( 2 );
        
        e_basic = new EtatJeu();
        
        e_first = new EtatJeu();
        e_first.zeJoueurs = new Joueur[2];
        e_first.zeJoueurs[0] = j_v;
        e_first.zeJoueurs[1] = j_r;
        e_first.zePlateau = b_first;
        e_first.dernierMvt = m_rZ11I2;
        e_first.tour = Joueur.vert;
        
    }

    /*
     * Class under test for void EtatJeu(EtatJeu)
     */
    public void testEtatJeuEtatJeu()
    {
        EtatJeu e_tmp = new EtatJeu( e_basic);
        Assert.assertTrue( e_basic.equals( e_tmp));
        Assert.assertEquals( e_basic, e_tmp);
        e_tmp = new EtatJeu( e_first);
        e_first.equals( e_tmp);
        Assert.assertTrue( e_first.equals( e_tmp));
        Assert.assertEquals( e_first, e_tmp);
    }

    /*
     * Class under test for boolean equals(Object)
     */
    public void testEqualsObject()
    {
        Assert.assertTrue(!e_basic.equals(null));
        Assert.assertEquals(e_basic, e_basic);
        
        EtatJeu e_tmp = new EtatJeu();
        Assert.assertEquals(e_basic, e_tmp);
        Assert.assertTrue(!e_first.equals(e_tmp));
        e_tmp.zeJoueurs = new Joueur[2];
        Assert.assertTrue(!e_first.equals(e_tmp));
        e_tmp.zeJoueurs[0] = j_v;
        e_tmp.zeJoueurs[1] = j_r;
        Assert.assertTrue(!e_first.equals(e_tmp));
        e_tmp.zePlateau = b_first;
        Assert.assertTrue(!e_first.equals(e_tmp));
        e_tmp.dernierMvt = m_rZ11I2;
        Assert.assertTrue(!e_first.equals(e_tmp));
        e_tmp.tour = Joueur.vert;
        Assert.assertTrue(e_first.equals(e_tmp));
        
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

}
