package game.gui;

import game.GameException;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import diabalik.AlphaBeta;
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
	JSpinner depthSpinner;
	
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
        getActionMap().put("solve", new SolveGameAction()); 
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "solve");
        
        
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
		
		// Depth Panel
		
		
		// History State
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout( new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		JPanel depthPanel = new JPanel();
		depthPanel.setBorder( BorderFactory.createTitledBorder("AlphaBeta"));
		
		JLabel depthLabel = new JLabel("Prof.");
		depthPanel.add( depthLabel);
		SpinnerModel depthModel = new SpinnerNumberModel( 1, //initial value
                0, //min
                20, //max
                1);                //step
		depthSpinner = new JSpinner(depthModel);
		depthPanel.add(depthSpinner);
		rightPanel.add( depthPanel);
		
		JPanel historyPanel = new JPanel();
		historyPanel.setBorder( BorderFactory.createTitledBorder("Historique"));
		historyPanel.add( guiHistory );
		rightPanel.add( historyPanel);
		
		this.add( rightPanel, BorderLayout.LINE_END );
		
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
    private class SolveGameAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e)
        {
        	AlphaBeta solveur = new AlphaBeta(game);
        	SpinnerModel depthModel = depthSpinner.getModel();
        	int depth = 1;
            if (depthModel instanceof SpinnerNumberModel) {
                depth = ((SpinnerNumberModel) depthModel).getNumber().intValue();;
            }
        	System.out.println("############## SOLVING #############");
        	try {
        		solveur.findBestMoves(game.getJoueurs()[game.getState().getTurn()], game.getState(), depth);
        	}
        	catch (GameException ge) {
				System.err.println("SOLVEUR : "+ ge.getMessage());
			}
        	System.out.println("####################################");
        }
    }
    
}
