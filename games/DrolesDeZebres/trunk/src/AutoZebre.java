
/*
 * Created on Mar 6, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import gui.JGameHistory;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import drolesDZ.Jeu;

/**
 * This graphical program is an interface to an automated player
 * for "Drole de Zebres".
 * @author dutech
 */
public class AutoZebre {

    Display zeDisplay;
    Shell zeShell;
    
    // specific to this program
    /// Set of the parameters to AutoZebre
    Parameters zeParameters;
    /// The GUI interface to AutoZebre
    JGameHistory zeJGameHistory;
    //String fileNameJeu;
    
    /**
     * Creates Display and Shell, then graphical components.
     */
    public AutoZebre(String[] args)
    {
        
        zeParameters = new Parameters();
        //System.out.println( "Before .........\n" + zeParameters.toString());
        zeParameters.check( args );
        //System.out.println( "After .........\n" + zeParameters.toString());
        
        zeDisplay = new Display();
        zeShell = new Shell( zeDisplay );
        //zeShell.setSize( 700, 500 );
        init(args);
        zeShell.pack();
    }
    
    /**
     * Initialize the graphical components.
     * May load args[0] as a history file name.
     */
    public void init(String[] args)
    {   
        // the interface
        zeJGameHistory = new JGameHistory( zeShell, SWT.NONE );
        createMenu( zeShell );
        
        // attach a game
        actionLoadFile( zeParameters.gameFileName );
    }
    /**
     * Create the menuBar.
     * @param parent the shell.
     */
    private void createMenu( Decorations parent )
	{
	    Menu menuBar = new Menu( parent, SWT.BAR );
	    parent.setMenuBar( menuBar );
	    
	    // File Menu
	    MenuItem fileItem = new MenuItem( menuBar, SWT.CASCADE );
	    fileItem.setText( "Fichier" );
	    Menu fileMenu = new Menu( parent, SWT.DROP_DOWN );
	    fileItem.setMenu( fileMenu );
	    // ---- Open
	    MenuItem openMenuItem = new MenuItem( fileMenu, SWT.PUSH );
	    openMenuItem.setText( "&Ouvrir\tCtrl+O" );
	    openMenuItem.setAccelerator(SWT.CTRL+'O');
	    openMenuItem.addListener(SWT.Selection, new Listener() {
	        public void handleEvent( Event e ) {
	            //System.out.println( "Ouvrir un fichier");
	            actionOpenFile();
	        }
	    });
        // ---- Save
	    MenuItem saveMenuItem = new MenuItem( fileMenu, SWT.PUSH );
	    saveMenuItem.setText( "&Sauver\tCtrl+S" );
	    saveMenuItem.setAccelerator(SWT.CTRL+'S');
	    saveMenuItem.addListener(SWT.Selection, new Listener() {
	        public void handleEvent( Event e ) {
	            //System.out.println( "Sauver un fichier");
	            actionSaveFile();
	        }
	    });
	    // ---------
	    MenuItem separatorMenuItem = new MenuItem( fileMenu, SWT.SEPARATOR );
	    // ---- Exit
	    MenuItem exitMenuItem = new MenuItem( fileMenu, SWT.PUSH );
	    exitMenuItem.setText( "&Quitter\tCtrl+Q" );
	    exitMenuItem.setAccelerator(SWT.CTRL+'Q');
	    exitMenuItem.addListener(SWT.Selection, new Listener() {
	        public void handleEvent( Event e ) {
	            //System.out.println( "Quitter l'application");
	            zeShell.dispose();
	        }
	    });
	   menuBar.setVisible( true );
	}
	    
    /**
     * Display a FileDialog so as to select a file to be opened.
     */
    private void actionOpenFile()
    {
        String[] filterExtensions = {"*.mvt"};
        FileDialog zeOpenDialog = new FileDialog( zeShell, SWT.OPEN);
        zeOpenDialog.setText( "Choisir le fichier à ouvrir");
        zeOpenDialog.setFilterPath( "./share" );
        zeOpenDialog.setFilterExtensions( filterExtensions );
        String selectedFile = zeOpenDialog.open();
        
        actionLoadFile( selectedFile );
    }
    /**
     * Open a file and load its content as a History.
     * @param fileName
     */
    private void actionLoadFile( String fileName )
    {
        System.out.println( "Loading "+ fileName);
        Jeu zeJeu = new Jeu();
        ArrayList zeHistory;
        try {
            zeParameters.gameFileName = fileName;
            zeHistory = zeJeu.loadHistory( fileName );
            zeJGameHistory.setHistory( zeJeu, zeHistory );
        }
        catch(Exception e) {
            System.err.println( e.getMessage() );
        }
    }
    /**
     * Display a FileDialog so as to select a file to be saved.
     */
    private void actionSaveFile()
    {
        String[] filterExtensions = {"*.mvt"};
        FileDialog zeSaveDialog = new FileDialog( zeShell, SWT.SAVE);
        zeSaveDialog.setText( "Choisir le fichier à sauver");
        zeSaveDialog.setFilterPath( "./share" );
        zeSaveDialog.setFilterExtensions( filterExtensions );
        String selectedFile = zeSaveDialog.open();
        
        actionWriteFile( selectedFile );
    }
    /**
     * Save an History in fileName.
     * @param fileName
     */
    private void actionWriteFile( String fileName )
    {
        // new fileName
        if( fileName != null ) {
            zeParameters.gameFileName = fileName;
        }
        System.out.println( "Writing " + zeParameters.gameFileName );
        // si fileNameJeu n'est pas null
        if( zeParameters.gameFileName != null ) {
            try {
                zeJGameHistory.game.writeHistory( zeParameters.gameFileName, zeJGameHistory.history);
            }
            catch(Exception e) {
                System.err.println( e.getMessage() );
            }
        }
    }
    
    /**
     * Run the "infinite" graphical loop.
     */
    public void run()
    {
        zeShell.open();
        //infinite loop
        while( !zeShell.isDisposed() ) {
            if( !zeDisplay.readAndDispatch()) {
                zeDisplay.sleep();
            }
        }
        zeDisplay.dispose();
    }
    
    public static void main(String[] args)
    {
        AutoZebre zeProgram = new AutoZebre( args );
        zeProgram.run();
    }


}

