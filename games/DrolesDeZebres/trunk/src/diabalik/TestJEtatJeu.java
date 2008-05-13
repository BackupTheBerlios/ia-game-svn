/**
 * 
 */
package diabalik;

import static org.junit.Assert.fail;
import game.GameException;
import game.gui.JGame;

import java.io.IOException;
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
		
	}
	public void runFile()
	{
        guiGame = new JGame( game );
        game.getState().notifyObservers();
        window.getContentPane().add(guiGame);
        //window.pack();
        window.setSize(420, 400);
        window.setVisible(true);
        
	    try {
	        game.applyMoves("share/essai.dbk");
	        System.out.println( "Après lecture fichier\n"+game.displayStr() );
            game.notifyObservers();
	        
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
    
	public void run()
	{
		
		guiGame = new JGame( game );
		game.getState().notifyObservers();
		window.getContentPane().add(guiGame);
		window.pack();
        window.setSize(420, 400);
        window.setVisible(true);
        //System.out.println("ViewPort = " + guiGame.guiHistory.histoList.getPreferredScrollableViewportSize().toString());
		
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
           // System.out.println("ViewPort = " + guiGame.guiHistory.histoList.getPreferredScrollableViewportSize().toString());
		} catch (Exception e) {
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
		} catch (Exception e) {
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
            //System.out.println("ViewPort = " + guiGame.guiHistory.histoList.getPreferredScrollableViewportSize().toString());
            //guiGame.guiHistory.revalidate();
            //System.out.println("Revalidated ViewPort = " + guiGame.guiHistory.histoList.getPreferredScrollableViewportSize().toString());
		} catch (Exception e) {
			System.out.println( e.getMessage() );
            System.out.println( game.displayStr() );
            fail();
		}
	}

	public static void main(String[] args)
	{
		TestJEtatJeu myTest = new TestJEtatJeu();
		myTest.runFile();
	}
}
