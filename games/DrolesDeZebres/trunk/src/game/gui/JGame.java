package game.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import diabalik.Jeu;

public class JGame extends JPanel
implements Observer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JGameState guiState;
	JGameHistory guiHistory;
	
	/** Model */
	Jeu game;
	
	public JGame( Jeu game )
	{
		super();
		
		this.game = game;
		guiState = new JGameState( this.game.getState() );
		guiHistory = new JGameHistory( this.game.getHistorique());
		
		build();
        //this.game.addObserver(this);
	}
	
	void build()
	{
		this.setLayout( new BorderLayout());
		
		// Game State
		JPanel statePanel = new JPanel();
		statePanel.setBorder( BorderFactory.createTitledBorder("Etat du Jeu"));
		statePanel.add( guiState );
		this.add( statePanel, BorderLayout.CENTER );
		
		// History State
		JPanel historyPanel = new JPanel();
		historyPanel.setBorder( BorderFactory.createTitledBorder("Historique"));
		historyPanel.add( guiHistory );
		this.add( historyPanel, BorderLayout.LINE_END );
		
		
	}

	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
		
	}

}
