/*
 * Created on Mar 7, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graphic;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class Viewer extends Frame implements WindowListener {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Viewer(BranchGroup theScene) 
    {
        super("- Chapitre 2 : geometries élementaires -");
        this.addWindowListener(this);
        this.setLayout(new BorderLayout());
        
        //creation de la scene java3d
        Canvas3D canvas=new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        SimpleUniverse myWorld=new SimpleUniverse(canvas);
        myWorld.addBranchGraph(theScene);
        myWorld.getViewingPlatform().setNominalViewingTransform();
        //fin de creation
        
        this.add("Center",canvas);
        
    }

    public void windowActivated(WindowEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    public void windowClosed(WindowEvent e)
    {
    }

    public void windowClosing(WindowEvent e)
    {
        System.exit(0);
    }

    public void windowDeactivated(WindowEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    public void windowDeiconified(WindowEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    public void windowIconified(WindowEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    public void windowOpened(WindowEvent e)
    {
        // TODO Auto-generated method stub
        
    }
    
}
