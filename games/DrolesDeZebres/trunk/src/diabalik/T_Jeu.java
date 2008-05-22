package diabalik;

import static org.junit.Assert.fail;
import game.GameException;

import java.io.IOException;

import org.junit.Test;



public class T_Jeu {
	Jeu game;

	protected void setUp()
	{
       game = new Jeu();
    }
	
	@Test
	public void testJeu() {
		System.out.println("------------------ testJeu");
		setUp();
		System.out.println(game.displayStr());
		System.out.println(">>>>> Historique");
		System.out.println(game.getHistorique());
		System.out.println("<<<<< Historique");
	}

	@Test
	public void testApplyMovesString() {
		System.out.println("------------------ testApplyMovesString");
		setUp();
		
		try {
			game.applyMoves("share/essai.dbk");
			System.out.println( "Après lecture fichier\n"+game.displayStr() );
			
		} catch (GameException e) {
			System.err.println("ApplyMoves : " + e.getMessage());
			System.out.println( game.displayStr() );
			fail("GameException");
		} catch (IOException e) {
			System.err.println("ApplyMoves : " + e.getMessage());
			System.out.println( game.displayStr() );
			fail("IOException");
		}
		System.out.println(">>>>> Historique");
		System.out.println(game.getHistorique());
		System.out.println("<<<<< Historique");
		
		
	}
	@Test
	public void testApplyMove() {
		System.out.println("------------------ testMove");
		setUp();
		
		Mouvement mvt;
		
		mvt = new Mouvement(game.getJoueur(Joueur.rouge), Mouvement.depl, new PositionGrid2D(0,0), new PositionGrid2D(0,1));
		System.out.println("Apply : " + mvt.toString());
		try {
			game.applyMove(mvt);
		} catch (Exception e) {
			System.out.println( e.getMessage() );
            System.out.println( game.displayStr() );
            fail();
		}
		System.out.println(game.displayStr());
		mvt = new Mouvement(game.getJoueur(Joueur.rouge), Mouvement.depl, new PositionGrid2D(0,1), new PositionGrid2D(0,2));
		System.out.println("Apply : " + mvt.toString());
		try {
			game.applyMove(mvt);
		} catch (Exception e) {
			System.out.println( e.getMessage() );
            System.out.println( game.displayStr() );
            fail();
		}
		System.out.println(game.displayStr());
		mvt = new Mouvement(game.getJoueur(Joueur.rouge), Mouvement.pass, new PositionGrid2D(3,0), new PositionGrid2D(2,0));
		System.out.println("Apply : " + mvt.toString());
		try {
			game.applyMove(mvt);
		} catch (Exception e) {
			System.out.println( e.getMessage() );
            System.out.println( game.displayStr() );
            fail();
		}
		System.out.println(game.displayStr());
		System.out.println(">>>>> Historique");
		System.out.println(game.getHistorique());
		System.out.println("<<<<< Historique");
	}

}
