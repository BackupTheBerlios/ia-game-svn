package diabalik;

import static org.junit.Assert.*;

import org.junit.Test;

import drolesDZ.GameException;


public class T_Jeu {
	Jeu game;

	protected void setUp()
	{
       game = new Jeu();
    }
	
	@Test
	public void testJeu() {
		setUp();
		System.out.println(game.displayStr());
	}

	@Test
	public void testApplyMovesString() {
		fail("Not yet implemented");
	}

	@Test
	public void testApplyMovesStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testApplyMove() {
		setUp();
		
		Mouvement mvt;
		
		mvt = new Mouvement(game.getJoueur(Joueur.rouge), Mouvement.depl, new PositionGrid2D(0,0), new PositionGrid2D(0,1));
		System.out.println("Apply : " + mvt.toString());
		try {
			game.applyMove(mvt);
		} catch (GameException e) {
			System.out.println( e.getMessage() );
            System.out.println( game.displayStr() );
            fail();
		}
		System.out.println(game.displayStr());
	}

}
