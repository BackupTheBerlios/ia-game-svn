/*
 * Created on Mar 8, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graphic;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.media.j3d.BranchGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sun.j3d.utils.geometry.ColorCube;

/**
 * Application that displays a Panel with a Scene3D.
 * 
 * @author dutech (from Andrew Davidson's "Killer Game Programming"
 */
public class Viewer3D extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;



    public Viewer3D() 
    {
        super("Viewer3D");
        Container c = getContentPane();
        c.setLayout( new BorderLayout() );
        
        // the objects to be displayed
        BranchGroup myGroup = new BranchGroup();
        myGroup.addChild( new ColorCube(0.5f) );
        
        JLabel statusLabel = new JLabel( "Initial State");
        c.add(statusLabel, BorderLayout.NORTH);
        // panel holding the 3D canvas
        Scene3D w3d = new Scene3D( null /*myGroup*/, statusLabel );
        c.add(w3d, BorderLayout.CENTER);
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        pack();
        setResizable(false);    // fixed size display
        setVisible(true);
    } // end of Viewer3D
    
    
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new Viewer3D();
    }
    
}
