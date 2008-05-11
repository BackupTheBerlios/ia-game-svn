/*
 * Created on Feb 7, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package game.gui;

import game.GameException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import diabalik.AlphaBeta;
import diabalik.EtatJeu;
import diabalik.GenerateurMouvement;
import diabalik.Jeu;
import diabalik.Joueur;
import diabalik.Mouvement;
/**
 * @author dutech
 */
public class JJeu extends Composite {

    public Jeu game;
    public ArrayList history;
    public int historyPosition;
    public AlphaBeta searchEngine;
    
	private Group group_jeu = null;
	private Composite composite_boutons = null;
	private Group group_mvt = null;
	private Text textArea_jeu = null;
	private List list_mvt = null;
	private Text text_mvt = null;
	
	private Button button_down = null;
	private Button button_up = null;
	private Button button_ab = null;
	private Button button_val = null;
	
	
	private Font fixedFont = null;
/**
     * @param parent
     * @param style
     */
    public JJeu(Composite parent, int style)
    {
        super(parent, style);
   
		initialize();
		history = null;
		setPosition( 0 );
		pack();
 }
	private void initialize() 
	{
	    searchEngine = new AlphaBeta( new GenerateurMouvement() );
	    
	    // Fonte de taille fixe
		fixedFont = new Font( getDisplay(), "Monospace", 10, SWT.NORMAL);
		
		RowLayout rowLayout4 = new RowLayout();
		rowLayout4.wrap = false;
		rowLayout4.fill = true;
		this.setLayout(rowLayout4);
		
		
		//setSize(new org.eclipse.swt.graphics.Point(800,600));
		createGroup_jeu();
		createGroup_mvt();
		
		//pack();
		
	}

	
	/**
	 * Met en place un nouvel GameHistory et s�lectionne le premier
	 * Mouvement.
	 * @param p_history
	 */
	public void setHistory( Jeu p_jeu, ArrayList p_history )
	{
	    game = p_jeu;
	    history = p_history;
	    list_mvt.removeAll();
	    
	    // met � jour la liste
	    for (Iterator iter = history.iterator(); iter.hasNext();) {
            EtatJeu element = (EtatJeu) iter.next();
            list_mvt.add( element.dernierMvt.toString() );
        }
	    
	    // choisi une position
	    setPosition( 0 );
	}
	
	/**
	 * A appeler quand history a �t� modifi�.
	 * Pour remettre la list_mvt � jour.
	 */
    public void updatedHistory()
    {
        list_mvt.removeAll();
	    
	    // met � jour la liste
	    for (Iterator iter = history.iterator(); iter.hasNext();) {
            EtatJeu element = (EtatJeu) iter.next();
            list_mvt.add( element.dernierMvt.toString() );
        }
	    
	    // choisi une position
	    setPosition( historyPosition );
    }
	/**
	 * S�lectionne l'EtatJeu � la position p_position.
	 * historyPosition est contraint entre 0 et history.size().
	 * @param p_position
	 * @return false si history n'est pas initialis�.
	 */
	public boolean setPosition( int p_position )
	{
	    if( history == null ) {
	        historyPosition = -1;
	        return false;
	    }
	    
	    if( p_position < 0) p_position = 0;
	    if( p_position >= history.size() ) p_position = history.size() - 1;
	    
	    historyPosition = p_position;
	    
	    // affiche le Plateau
	    EtatJeu platal = (EtatJeu) history.get(historyPosition);
	    textArea_jeu.setText( platal.toString() );
	    
	    // met � jour la liste
	    list_mvt.setSelection( historyPosition );
	    // et le texte
	    text_mvt.setText( list_mvt.getItem( historyPosition ));
	    return true;
	}

	/**
	 * Impose un nouveau Mouvement � la position donn�e. Tout l'historique est 
	 * chang� de mani�re descendante.
	 * @param p_position
	 * @param mvtStr String d�crivant le Mouvement
	 */
	public void changeMvt( int p_position, String mvtStr )
	{
	    // r�cup�re l'EtatJeu
//	    EtatJeu etat = (EtatJeu) history.get( p_position );
//	    etat.valide = true;
//	    
//	    if( etat != null ) {
//	        Mouvement mvt = new Mouvement();
//	        mvt.extractFrom( mvtStr, game );
//	        game.setState( etat );
//	        try {
//	            game.applyMove( mvt );
//	        }
//	        catch(GameException e) {
//	            System.out.println( e.getMessage() );
//	        }
//	        updatedHistory();
//	    }
	    if( p_position > 0 ) {
	        Mouvement mvt = new Mouvement();
	        mvt.extractFrom( mvtStr, game );
	        history = game.alterHistory( historyPosition, mvt, history );
	        updatedHistory();
	    }
	}
	
	public void addNewMvt( int p_position )
	{
	    EtatJeu newEtat = new EtatJeu( (EtatJeu) history.get( p_position ));
	    history.add( p_position+1, newEtat );
	    history = game.alterHistory( p_position+1, new Mouvement(), history );
        updatedHistory();
	}
	
	public void searchBestMoves( int p_position )
	{
	    EtatJeu etat = (EtatJeu) history.get( p_position );
	    game.setState( new EtatJeu( etat ));
	    String strJoueur = Joueur.toString( game.getJoueur( game.getTour()).couleur );
	    
//	    System.out.println( "--- Recherche de la suite de meilleurs Mvt Array ---");
//	    System.out.println( game.getState().toString()+"\n----\n");
//        Date dateDebutMvtArray = new Date();
//        try {
//            int valMvtArray = searchEngine.findBestMovesArray( game.getJoueur(game.getTour()), game, 50);
//            Date dateFinMvtArray = new Date();
//            long timeSpentMvtArray = dateFinMvtArray.getTime() - dateDebutMvtArray.getTime();
//            long nbMinMvtArray = timeSpentMvtArray / (1000*60);
//            System.out.println( "   "+searchEngine.nbVisitedStates+ " mouvements �valu�s en "+timeSpentMvtArray+"ms ("+nbMinMvtArray+" min)");
//            System.out.println( "   val = " + valMvtArray);
//        } catch(GameException e) {
//            System.out.println( "Recherche impossible : "+e.getMessage());
//        }
//        for (int ind = 0; ind < searchEngine.bestMoves.length; ind++) {
//            if( searchEngine.bestMoves[ind] != null ) {
//                System.out.println( ind + " = " + searchEngine.bestMoves[ind].toString());
//            }
//        }
        // Date de d�but -- BestMoves
        System.out.println( "--- Recherche de la suite de meilleurs Mvt ---");
        Date dateDebutMvt = new Date();
        try {
            int valMvt = searchEngine.findBestMoves( game.getJoueur(game.getTour()), game, 50);
            Date dateFinMvt = new Date();
            long timeSpentMvt = dateFinMvt.getTime() - dateDebutMvt.getTime();
            long nbMinMvt = timeSpentMvt / (1000*60);
            System.out.println( "   "+searchEngine.nbVisitedStates+ " mouvements �valu�s en "+timeSpentMvt+"ms ("+nbMinMvt+" min)");
            System.out.println( "   " + strJoueur + " val = " + valMvt);
        } catch(GameException e) {
            System.out.println( "Recherche impossible : "+e.getMessage());
        }
        System.out.println( game.getState().displayBestMoves() );
	}
	
	public void searchBest( int p_position )
	{
	    EtatJeu etat = (EtatJeu) history.get( p_position );
	    game.setState( new EtatJeu( etat ));
	    String strJoueur = Joueur.toString( game.getJoueur( game.getTour()).couleur );
	    
	    // Date de d�but -- Best
	    System.out.println("--- Recherche de la valeur ---");
	    Date dateDebut = new Date();
	    try {
	        int val = searchEngine.findBest( game.getJoueur(game.getTour()), game, 50);
	        Date dateFin = new Date();
	        long timeSpent = dateFin.getTime() - dateDebut.getTime();
	        long nbMin = timeSpent / (1000*60);
	        System.out.println( "   "+searchEngine.nbVisitedStates+ " mouvements �valu�s en "+timeSpent+"ms ("+nbMin+" min)");
	        System.out.println( "   "+strJoueur+" val = " + val);
	    } catch(GameException e) {
	        System.out.println( "Recherche impossible : " + e.getMessage() );
	    }   
	}

	
	/**
	 * This method initializes group	
	 *
	 */    
	private void createGroup_jeu() {
	    int wText = 400;
	    int hText = 400;
	    
	    RowData myRowData;
	    RowLayout myLayout = new RowLayout();
	    //myLayout.numColumns = 1;
	    myLayout.marginWidth = 10;
	    myLayout.marginHeight = 10;
	    //myLayout.pack = false;
	    //myLayout.fill = true;
	    
		group_jeu = new Group(this, SWT.BORDER | SWT.SHADOW_NONE);		   
		group_jeu.setLayout( myLayout );
		group_jeu.setText("Plateau");
		//group_jeu.setSize( new Point( wText + 4 * margin, hText));
		
		textArea_jeu = new Text(group_jeu, SWT.MULTI);
		myRowData = new RowData();
		myRowData.height = hText;
		myRowData.width = wText;
		textArea_jeu.setLayoutData( myRowData );
		textArea_jeu.setFont( fixedFont );
		//textArea_jeu.setLocation(new Point( 2, 2));
		textArea_jeu.setEditable(false);
		textArea_jeu.setText("N/A");
		//textArea_jeu.setSize(new Point(wText, hText));
		
		group_jeu.pack();
	}
	/**
	 * This method initializes composite1	
	 *
	 */    
	private void createComposite_boutons() {
	    RowLayout myLayout = new RowLayout();
	    myLayout.fill = true;
	    myLayout.justify = true;
	    myLayout.pack = true;
	    
		composite_boutons = new Composite(group_mvt, SWT.NONE);
		GridData myGridData;
		myGridData = new GridData();
		myGridData.horizontalAlignment = GridData.FILL;
		myGridData.grabExcessHorizontalSpace = true;
		composite_boutons.setLayoutData( myGridData );
		
		composite_boutons.setLayout(myLayout);
		//composite_boutons.setBounds(new org.eclipse.swt.graphics.Rectangle(15,190,64,64));
		
		button_up = new Button(composite_boutons, SWT.NONE);
		button_up.setText( "HAUT");
		button_up.addSelectionListener( new SelectionAdapter() {
		    public void widgetSelected( SelectionEvent e) {
		        setPosition( historyPosition - 1 );
		    }
		});
		button_down = new Button(composite_boutons, SWT.NONE);
		button_down.setText( "BAS" );
		button_down.addSelectionListener( new SelectionAdapter() {
		    public void widgetSelected( SelectionEvent e) {
		        setPosition( historyPosition + 1 );
		    }
		});
		button_ab = new Button(composite_boutons, SWT.NONE);
		button_ab.setText( "Sol" );
		button_ab.addSelectionListener( new SelectionAdapter() {
		    public void widgetSelected( SelectionEvent e) {
		        searchBestMoves( historyPosition );
		    }
		});
		button_val = new Button(composite_boutons, SWT.NONE);
		button_val.setText( "Val");
		button_val.addSelectionListener( new SelectionAdapter() {
		    public void widgetSelected( SelectionEvent e) {
		        searchBest( historyPosition );
		    }
		});
		composite_boutons.pack();
		
	}
	/**
	 * This method initializes group1	
	 *
	 */    
	private void createGroup_mvt() {
	    int wList = 200;
	    int hList = 150;
	    
	    GridData myGridData;
	    GridLayout myLayout = new GridLayout();
	    myLayout.numColumns = 1;
	    myLayout.marginWidth = 10;
	    myLayout.marginHeight = 10;
	    
		group_mvt = new Group(this, SWT.NONE);
		group_mvt.setLayout( myLayout );
		group_mvt.setText("Mouvements");
		
		list_mvt = new List(group_mvt, SWT.V_SCROLL);
		myGridData = new GridData();
		//myGridData.heightHint = hList;
		myGridData.widthHint = wList;
		myGridData.verticalAlignment = GridData.FILL;
		//myGridData.verticalSpan = 2;
		myGridData.grabExcessVerticalSpace = true;
		list_mvt.setLayoutData(myGridData);
		//list_mvt.setLocation(new Point(2, 2));
		//list_mvt.setSize(new Point(wList, hList));
		list_mvt.addMouseListener( new MouseAdapter() {
		    public void mouseUp( MouseEvent e) {
		        setPosition( list_mvt.getSelectionIndex() );
		    }
		});
		
		createComposite_boutons();
		
		text_mvt = new Text( group_mvt, SWT.NONE);
		myGridData = new GridData();
		myGridData.horizontalAlignment = GridData.FILL;
		myGridData.grabExcessHorizontalSpace = true;
		text_mvt.setLayoutData( myGridData );
		text_mvt.addKeyListener( new KeyAdapter() {
		    public void keyPressed( KeyEvent e) {
		        switch( e.character ) {
		        case SWT.CR:
		            // change mvt and history
		            changeMvt( historyPosition, text_mvt.getText());
		        break;
		        }
		        switch( e.keyCode ) {
		        case SWT.ARROW_UP:
		            // alter position
		            setPosition( historyPosition-1 );
		        break;
		        case SWT.ARROW_DOWN:
		            // alter position
		            setPosition( historyPosition+1 );
		        break;
		        case SWT.INSERT:
		            addNewMvt( historyPosition );
		        break;
		        }
		    }
		});
		
		group_mvt.pack();
	}
}
