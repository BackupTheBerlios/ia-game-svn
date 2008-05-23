package game.gui;

import game.GameException;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import diabalik.AlphaBeta;
import diabalik.Jeu;

public class JGame extends JPanel
implements Observer, PropertyChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JGameState guiState;
	JGameHistory guiHistory;
	JSpinner depthSpinner;
	JTextArea	solveurText;
	JProgressBar progressBar;
	
	Thread aThread;
	AlgoRunnable solveurRunnable;
	Task task;
	AlphaBeta algo;
	int depth;
	
	/** Model */
	Jeu game;
	
	// ---------- a Private Logger ---------------------
	private Logger logger = Logger.getLogger(JGame.class);
	// --------------------------------------------------
	
	public JGame( Jeu game )
	{
		super();
		
		this.game = game;
		algo = new AlphaBeta(this.game);
		
		guiState = new JGameState( this.game.getState() );
		guiHistory = new JGameHistory( this.game.getHistorique());
		// TODO : ad-hoc to JList
        guiHistory.histoList.addListSelectionListener( new MyListSelectionListener());
        guiHistory.histoList.getModel().addListDataListener( new MyListDataListener());
		guiHistory.histoList.setSelectedIndex(0);
        
        getActionMap().put("generate", new GenerateMoveAction()); 
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0), "generate");
        getActionMap().put("solve", new SolveGameAction(this)); 
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "solve");
        
        
		build();
        //this.game.addObserver(this);
	}
	
	void build()
	{
		// Need for it to have a BorderLayout as the default FlowLayout does
		// NOT fill up space...
		this.setLayout( new BorderLayout());
		
		// Game State
		JPanel statePanel = new JPanel(new BorderLayout(5,5));
		statePanel.setBorder(BorderFactory.createTitledBorder("Etat du Jeu"));
		statePanel.add( guiState, BorderLayout.CENTER );
		this.add( statePanel, BorderLayout.CENTER );
		
		// Depth Panel
		
		// Solveur Panel
		JPanel solveurPanel = new JPanel( new BorderLayout(10,10));
		solveurPanel.setBorder( BorderFactory.createTitledBorder("AlphaBeta"));
		
		JPanel depthPanel = new JPanel(new BorderLayout());
		JLabel depthLabel = new JLabel("Prof.");
		depthPanel.add( depthLabel, BorderLayout.BEFORE_LINE_BEGINS);
		SpinnerModel depthModel = new SpinnerNumberModel( 1, //initial value
                0, //min
                20, //max
                1);                //step
		depthSpinner = new JSpinner(depthModel);
		depthPanel.add(depthSpinner, BorderLayout.CENTER);
		JButton solveButton = new JButton();
		solveButton.setText(" Solve ");
		solveButton.setAction( new SolveGameAction(this) );
		depthPanel.add( solveButton, BorderLayout.AFTER_LAST_LINE);
		solveurPanel.add( depthPanel, BorderLayout.BEFORE_LINE_BEGINS);
		
		solveurText = new JTextArea();
		solveurText.setLineWrap(true);
		solveurText.setEditable(false);
		solveurText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		solveurPanel.add( solveurText, BorderLayout.CENTER);
		
		progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        solveurPanel.add( progressBar, BorderLayout.BEFORE_FIRST_LINE );
		
		this.add( solveurPanel, BorderLayout.PAGE_END);
		
		// History Panel
		// Need for it to have a BorderLayout as the default FlowLayout does
		// NOT fill up space...
		JPanel historyPanel = new JPanel(new BorderLayout());
		historyPanel.setBorder( BorderFactory.createTitledBorder("Historique"));
		historyPanel.add( guiHistory, BorderLayout.CENTER );
		
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
    private class SolveGameAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	JGame guiGame;
    	
    	public SolveGameAction(JGame guiGame)
    	{
    		this.guiGame = guiGame;
    		putValue(Action.NAME, " Solve ");
    		putValue(Action.ACCELERATOR_KEY, KeyEvent.VK_S);
    	}
    	
        public void actionPerformed(ActionEvent e)
        {
        	solveurText.setText(null);
    		solveurText.setText("Recherche de solution");
    		
    		SpinnerModel depthModel = depthSpinner.getModel();
        	depth = 1;
            if (depthModel instanceof SpinnerNumberModel) {
                depth = ((SpinnerNumberModel) depthModel).getNumber().intValue();;
            }
            progressBar.setIndeterminate(true);
            
        	// Instances of javax.swing.SwingWorker are not reusuable, so
            // we create new instances as needed.
            task = new Task();
            task.addPropertyChangeListener(guiGame);
            task.execute();
        }
    }
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt)
    {
    	logger.info("Main setText : " + evt.getPropertyName());
        if ("progress" == evt.getPropertyName()) {
        	solveurText.append( algo.getNbVisitedStates() + " / " + algo.getEstimatedStatesToVisit() + "\n ");
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        } 
    }
    
    /**
     * A SwingWorker Task that starts the solveur and watch its progression...
     * @author alain
     *
     */
    class Task extends SwingWorker<Void, Void> {
    	
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground()
        {
        	// runs the algorithme;
            
    		solveurRunnable = new AlgoRunnable();
			aThread = new Thread(solveurRunnable);
			aThread.start();
			
			logger.info("Task");
			while( aThread.isAlive() ) {
				solveurText.setText(algo.getNbVisitedStates() + " / " + algo.getEstimatedStatesToVisit() + "\n ");
				logger.info("Task");
				// Sleep 
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {}
			}
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done()
        {
        	progressBar.setIndeterminate(false);
        	solveurText.setText(null);
    		solveurText.append( algo.getNbVisitedStates() + " états visités\n");
    		solveurText.append( algo.getBestStrategy().toString());
        }
    }
    
    /**
     * A thread for running the solver.
     * @author alain
     *
     */
    class AlgoRunnable implements Runnable
	{
		boolean stopFlag  = false;
		
		AlgoRunnable()
		{
			stopFlag = false;
	    }
		public void run()
		{
			
            try {
        		algo.findBestMoves(game.getJoueurs()[game.getState().getTurn()], game.getState(), depth);
        	}
        	catch (GameException ge) {
				System.err.println("SOLVEUR : "+ ge.getMessage());
			}
        	
			stop();
		}
		public void stop()
		{
			stopFlag = true;
		}
	}
    
}
