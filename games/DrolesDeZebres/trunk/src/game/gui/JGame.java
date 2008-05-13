package game.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
		// TODO : ad-hoc to JList
        guiHistory.histoList.addListSelectionListener( new MyListSelectionListener());
        guiHistory.histoList.getModel().addListDataListener( new MyListDataListener());
		guiHistory.histoList.setSelectedIndex(0);
        
        getActionMap().put("generate", new GenerateMoveAction()); 
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0), "generate");
        
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
		guiHistory.histoList.setSelectedIndex( game.getIndexState() );
	}

    /**
     * Change State when selection is changed.
     * @author dutech
     */
	class MyListSelectionListener implements ListSelectionListener 
	{
	    public void valueChanged(ListSelectionEvent e)
	    {   
            for( int index = e.getFirstIndex(); index <= e.getLastIndex(); index++ ) {
                if( guiHistory.histoList.isSelectedIndex(index)) {
                    //System.out.println("ListSelection position = " + index);
                    game.setIndexState(index);
                    game.notifyObservers();
                    game.getState().notifyObservers();
                }
            }
	    }
	}
    
    /**
     * Only interested when current position is changed.
     * @author dutech
     */
    class MyListDataListener implements ListDataListener 
    {

        public void contentsChanged(ListDataEvent e)
        {
            if (e.getIndex0() == game.getIndexState()) {
                game.setIndexState(game.getIndexState());
                game.getState().notifyObservers();
            }
            
        }

        public void intervalAdded(ListDataEvent e)
        {   
        }

        public void intervalRemoved(ListDataEvent e)
        {   
        }
        
    }

    private class GenerateMoveAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e)
        {
            Jeu.zeMoveGenerator.getPotentialMove(game.getState());
            System.out.println(Jeu.zeMoveGenerator.toString());
        }
        
    }
    
}
