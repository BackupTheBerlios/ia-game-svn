/*
 * Created on Mar 8, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

/**
 * Hold a 3D Canva with a scene that is displayed against a 
 * background, with some selected lights.
 * 
 * @author dutech from Andrex Davidson's "Killer Game Programming"
 */
public class Scene3D extends JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final int PWIDTH = 512;   // size of panel
    private static final int PHEIGHT = 512; 
    
    private static final int BOUNDSIZE = 100;  // larger than world
    
    private static final Point3d USERPOSN = new Point3d(0,5,7);
    // initial user position
    
    private SimpleUniverse su;
    private BranchGroup sceneBG;
    private BranchGroup axesBG;
    private BranchGroup floorBG;
    private BoundingSphere bounds;   // for environment nodes
    
    // the object(s) being displayed
    private Shape3D currentObject;
    private Switch objAxesSwitch;
    private Switch objBBoxSwitch;
    private Switch objHighSwitch;
    
    // for handling scene Viewpoint
    OrbitBehavior orbit;
    
    // for handling object movements
    private TransformGroup objRotTG;
    private MouseRotate objRotBeh;
    private TransformGroup objTraTG;
    private MouseTranslate objTraBeh;
    
    // private Java3dTree j3dTree;   // frame to hold tree display
    private JLabel statusLabel;
    
    /**
     * A panel holding a 3D canvas: the usual way of linking Java 3D to Swing.
     * 
     * @param p_scene The Scene to be displayed
     */
    public Scene3D( BranchGroup p_scene, JLabel p_statusLabel)
    // A panel holding a 3D canvas: the usual way of linking Java 3D to Swing
    {
       statusLabel = p_statusLabel;
        
      setLayout( new BorderLayout() );
      setOpaque( false );
      setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

      GraphicsConfiguration config =
                    SimpleUniverse.getPreferredConfiguration();
      Canvas3D canvas3D = new Canvas3D(config);
      add("Center", canvas3D);
      canvas3D.setFocusable(true);     // give focus to the canvas 
      canvas3D.requestFocus();
      
      // listen for some key
      canvas3D.addKeyListener( new GlobalKeyAdapter());

      su = new SimpleUniverse(canvas3D);

      // j3dTree = new Java3dTree();   // create a display tree for the SG

      createSceneGraph(p_scene);
      initUserPosition();        // set user's viewpoint
      orbitControls(canvas3D);   // controls for moving the viewpoint
      
      su.addBranchGraph( sceneBG );

      // j3dTree.updateNodes( su );    // build the tree display window

    } // end of WrapCheckers3D()
    
    /**
     * TODO. Question : how to dynamically add
     * @param p_scene
     */
    public void setModel( BranchGroup p_scene )
    {
    }
    
    /**
     * Initialize and compile the Scene.
     */
    private void createSceneGraph(BranchGroup p_scene) 
    // initilise the scene
    { 
      sceneBG = new BranchGroup();
      bounds = new BoundingSphere(new Point3d(0,0,0), BOUNDSIZE);   

      addAxes();            // add axes
      addFloor();
      lightScene();         // add the lights
      addBackground();      // add the sky
      
      setScene();

      sceneBG.addChild( p_scene );
      //floatingSphere();     // add the floating sphere

    // j3dTree.recursiveApplyCapability( sceneBG );   // set capabilities for tree display

      sceneBG.compile();   // fix the scene
    } // end of createSceneGraph()
    
    /**
     * Position various objects of the scene.
     */
    private void setScene()
    {
        // the Objects of the scene
        BranchGroup sceneGroup = new BranchGroup();
        currentObject = new Hex3D();
        sceneGroup.addChild( currentObject );
        // add axes
        objAxesSwitch = new Switch();
        objAxesSwitch.setCapability( Switch.ALLOW_SWITCH_READ );
        objAxesSwitch.setCapability( Switch.ALLOW_SWITCH_WRITE );
        objAxesSwitch.setWhichChild( Switch.CHILD_NONE );
        objAxesSwitch.addChild( new Axes3D().getBG() );
        sceneGroup.addChild( objAxesSwitch );
        // add BBox
        objBBoxSwitch = new Switch();
        objBBoxSwitch.setCapability( Switch.ALLOW_SWITCH_READ );
        objBBoxSwitch.setCapability( Switch.ALLOW_SWITCH_WRITE );
        objBBoxSwitch.setWhichChild( Switch.CHILD_NONE );
        objBBoxSwitch.addChild( new SelectBox3D( currentObject ).getBox());
        sceneGroup.addChild( objBBoxSwitch );
        
        // add Highlight
        objHighSwitch = new Switch();
        objHighSwitch.setCapability( Switch.ALLOW_SWITCH_READ );
        objHighSwitch.setCapability( Switch.ALLOW_SWITCH_WRITE );
        objHighSwitch.setWhichChild( Switch.CHILD_NONE);
        objHighSwitch.addChild( new Higlight3D( currentObject ).getTG() );
        sceneGroup.addChild( objHighSwitch );
        
        // a rotation TransformGroup
        objRotTG = new TransformGroup();
        objRotTG.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRotTG.setCapability( TransformGroup.ALLOW_TRANSFORM_READ);
        objRotTG.addChild( sceneGroup );
        objRotBeh = new MouseRotate(objRotTG);
        objRotBeh.setSchedulingBounds( bounds );
        objRotBeh.setEnable( false );
        sceneBG.addChild( objRotBeh );
        
        // a translation TransformGroup set to the initial position
        Transform3D t3d = new Transform3D();
        t3d.set( new Vector3f(0,1,0)); 
        objTraTG = new TransformGroup(t3d);
        objTraTG.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTraTG.setCapability( TransformGroup.ALLOW_TRANSFORM_READ);
        objTraTG.addChild(objRotTG);
        objTraBeh = new MouseTranslate(objTraTG);
        objTraBeh.setSchedulingBounds( bounds );
        objTraBeh.setEnable( false );
        sceneBG.addChild( objTraBeh);
        
        sceneBG.addChild(objTraTG);
    }    
    /**
     * Add some axes.
     */
    private void addAxes()
    {
        Axes3D myAxes = new Axes3D();
        axesBG = myAxes.getBG();
        sceneBG.addChild( axesBG );
    }
    /**
     * Add a checker floor. 
     */
    private void addFloor()
    {
        CheckerFloor3D myFloor = new CheckerFloor3D();
        floorBG = myFloor.getBG();
        sceneBG.addChild( floorBG );
    }
    
    /**
     * Set up ambiante light and 2 directional lights.
     */
    private void lightScene()
    /* One ambient light, 2 directional lights */
    {
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        
        // Set up the ambient light
        AmbientLight ambientLightNode = new AmbientLight(white);
        ambientLightNode.setInfluencingBounds(bounds);
        sceneBG.addChild(ambientLightNode);
        
        // Set up the directional lights
        Vector3f light1Direction  = new Vector3f(-1.0f, -1.0f, -1.0f);
        // left, down, backwards 
        Vector3f light2Direction  = new Vector3f(1.0f, -1.0f, 1.0f);
        // right, down, forwards
        
        DirectionalLight light1 = 
            new DirectionalLight(white, light1Direction);
        light1.setInfluencingBounds(bounds);
        sceneBG.addChild(light1);
        
        DirectionalLight light2 = 
            new DirectionalLight(white, light2Direction);
        light2.setInfluencingBounds(bounds);
        sceneBG.addChild(light2);
    }  // end of lightScene()
    /**
     * A background.
     */
    private void addBackground()
    // A blue sky
    { 
        Background back = new Background();
        back.setApplicationBounds( bounds );
        back.setColor(0.85f, 0.85f, 0.85f);    // sky colour
        sceneBG.addChild( back );
    }  // end of addBackground() 
    /**
     * OrbitBehaviour allows the user to rotate around the scene, and to
       zoom in and out.
     * @param c the Canvas3D
     */
    private void orbitControls(Canvas3D c)
    /* OrbitBehaviour allows the user to rotate around the scene, and to
       zoom in and out.  */
    {
        orbit = new OrbitBehavior(c, OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(bounds);
        
        ViewingPlatform vp = su.getViewingPlatform();
        vp.setViewPlatformBehavior(orbit);        
    }  // end of orbitControls()
    /**
     * Set the user's initial viewpoint using lookAt().
     */
    private void initUserPosition()
    // Set the user's initial viewpoint using lookAt()
    {
        ViewingPlatform vp = su.getViewingPlatform();
        TransformGroup steerTG = vp.getViewPlatformTransform();
        
        Transform3D t3d = new Transform3D();
        steerTG.getTransform(t3d);
        
        // args are: viewer posn, where looking, up direction
        t3d.lookAt( USERPOSN, new Point3d(0,0,0), new Vector3d(0,1,0));
        t3d.invert();
        
        steerTG.setTransform(t3d);
    }  // end of initUserPosition()

    /**
     * Listen for Esc, and Cntr-Q for quitting.
     */
    class GlobalKeyAdapter extends KeyAdapter
    {
        public void keyPressed( KeyEvent e) 
        {
            int keyCode = e.getKeyCode();
            if( (keyCode == KeyEvent.VK_ESCAPE) ||
                    ((keyCode == KeyEvent.VK_Q) && (e.isControlDown()))) {
                System.exit(0);
            }
            else if(keyCode == KeyEvent.VK_O) {
                // object is transformed
                orbit.setEnable( false );
                objRotBeh.setEnable( true );
                objTraBeh.setEnable( true );
                statusLabel.setText( "Object Transform");
            }
            else if(keyCode == KeyEvent.VK_V) {
                // scene is transformed
                orbit.setEnable( true );
                objRotBeh.setEnable( false );
                objTraBeh.setEnable( false );
                statusLabel.setText( "View Transform");
            }
            else if(keyCode == KeyEvent.VK_I) {
                // info about object
                System.out.println( currentObject.getBounds().toString());
            }
            else if(keyCode == KeyEvent.VK_A) {         
                objAxesSwitch.setWhichChild( (objAxesSwitch.getWhichChild() == Switch.CHILD_ALL)  ? Switch.CHILD_NONE : Switch.CHILD_ALL);
            }
            else if(keyCode == KeyEvent.VK_B) {         
                objBBoxSwitch.setWhichChild( (objBBoxSwitch.getWhichChild() == Switch.CHILD_ALL)  ? Switch.CHILD_NONE : Switch.CHILD_ALL);
            }
            else if(keyCode == KeyEvent.VK_H) {         
                objHighSwitch.setWhichChild( (objHighSwitch.getWhichChild() == Switch.CHILD_ALL)  ? Switch.CHILD_NONE : Switch.CHILD_ALL);
            }
        }
    }
}
