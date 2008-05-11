/**
 * 
 */
package diabalik;

import static org.junit.Assert.fail;
import game.GameException;
import game.gui.JGame;

import java.util.Scanner;

import javax.swing.JFrame;



/**
 * @author alain
 *
 */
public class TestJEtatJeu {

	JFrame window;
	JGame guiGame;
	
	Jeu game;
	/**
	 * @throws java.lang.Exception
	 */
	public TestJEtatJeu()
	{
		game = new Jeu();
		window = new JFrame( "TestJEtatJeu");
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setVisible(true);
	}
	
	public void run()
	{
		
		guiGame = new JGame( game );
		game.getState().notifyObservers();
		window.getContentPane().add(guiGame);
		window.pack();
		
		Scanner keyboard = new Scanner(System.in);
		int reponse;
		Mouvement mvt;
		
		System.out.println( "next> ");
		reponse = keyboard.nextInt();
		mvt = new Mouvement(game.getJoueur(Joueur.rouge), Mouvement.depl, new PositionGrid2D(0,0), new PositionGrid2D(0,1));
		System.out.println("Apply : " + mvt.toString());
		try {
			game.applyMove(mvt);
			game.getState().notifyObservers();
		} catch (GameException e) {
			System.out.println( e.getMessage() );
            System.out.println( game.displayStr() );
            fail();
		}
		System.out.println(game.displayStr());
		
		System.out.println( "next> ");
		reponse = keyboard.nextInt();
		mvt = new Mouvement(game.getJoueur(Joueur.rouge), Mouvement.depl, new PositionGrid2D(0,1), new PositionGrid2D(0,2));
		System.out.println("Apply : " + mvt.toString());
		try {
			game.applyMove(mvt);
			game.getState().notifyObservers();
		} catch (GameException e) {
			System.out.println( e.getMessage() );
            System.out.println( game.displayStr() );
            fail();
		}
		System.out.println(game.displayStr());
		
		System.out.println( "next> ");
		reponse = keyboard.nextInt();
		mvt = new Mouvement(game.getJoueur(Joueur.rouge), Mouvement.pass, new PositionGrid2D(3,0), new PositionGrid2D(2,0));
		System.out.println("Apply : " + mvt.toString());
		try {
			game.applyMove(mvt);
			game.getState().notifyObservers();
		} catch (GameException e) {
			System.out.println( e.getMessage() );
            System.out.println( game.displayStr() );
            fail();
		}
	}

	public static void main(String[] args)
	{
		TestJEtatJeu myTest = new TestJEtatJeu();
		myTest.run();
	}
}
