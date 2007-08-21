/*
 * Created on Feb 20, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.util.ArrayList;

import junit.framework.TestCase;
import junit.framework.Assert;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import drolesDZ.Jeu;

/**
 * @author dutech
 */
public class T_JGameHistory extends TestCase {

    Display zeDisplay;
    Shell zeShell;
    JGameHistory zeGUI;
    Jeu zeJeu;
    ArrayList zeHistory;
    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        // System.out.println( "#### setUp ####");   
        // graphic
        zeDisplay = new Display();
        zeShell = new Shell( zeDisplay );
        zeJeu = new Jeu();
        zeHistory = zeJeu.loadHistory( "grojaky.mvt");
        //zeHistory = zeJeu.loadHistory( "T_Jeu_2.mvt");
    }
    protected void runShell()
    {
        zeShell.open();
        while(!zeShell.isDisposed()) {
            if( !zeDisplay.readAndDispatch() ) {
                zeDisplay.sleep();
            }
        }
        zeDisplay.dispose();
    }

    public void testJGameHistory()
    {
        System.out.println( "#### testJGameHistory ####");
        zeGUI = new JGameHistory( zeShell, SWT.NONE );
        runShell();
    }

    public void testSetHistory()
    {
        System.out.println( "#### testSetHistory ####");
        zeGUI = new JGameHistory( zeShell, SWT.NONE );
        
        zeGUI.setHistory( zeJeu, zeHistory );
        
        runShell();
    }

    public void testSetPosition()
    {
        System.out.println( "#### testSetPosition ####");
        zeGUI = new JGameHistory( zeShell, SWT.NONE );
        
        zeGUI.setHistory( zeJeu, zeHistory );
        Assert.assertTrue( zeGUI.setPosition( -2 ));
        Assert.assertTrue( zeGUI.setPosition( 100 ));
        Assert.assertTrue( zeGUI.setPosition( 3 ));
        
        runShell();
    }

}
