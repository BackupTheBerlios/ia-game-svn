/*
 * Created on Mar 28, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graphic;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.Box;

/**
 * A SelectBox3D is a blue transparent Box which is computed from
 * the node Bounding Box.
 * @author dutech
 */
public class SelectBox3D {
    
    private Box selBox;
    private Node selectedNode;
    
    static final float LINE_WIDTH = 3f;
    static final Color3f BLUE = new Color3f( 0f, 0f, 0.8f);
    static final Color3f WHITE = new Color3f( 1f, 1f, 1f );
    static final float SHINY = 40f;
    static final float TRANSPARENT = 0.7f;
    
    public SelectBox3D( Node p_node )
    {
        selectedNode = p_node;
        
        setGeometry();
    }
    
    public Box getBox()
    {
        return selBox;
    }
    
    private void setGeometry()
    {
        
        //get the Node geometry
        BoundingBox nodeBox = new BoundingBox( selectedNode.getBounds());
        Point3d upperP = new Point3d();
        Point3d lowerP = new Point3d();
        nodeBox.getUpper( upperP );
        nodeBox.getLower( lowerP );
        selBox = new Box( (float) (upperP.x - lowerP.x),
                (float) (upperP.y - lowerP.y), 
                (float) (upperP.z - lowerP.z),
                initialAppearance() );
    }
    
    /**
     * create a blue transparent material with solid lines.
     * @return
     */
    private Appearance initialAppearance()
    {
        Appearance ap = new Appearance();
        
        // solid thick blue line
        LineAttributes lineAT = new LineAttributes();
        lineAT.setLineWidth( LINE_WIDTH );
        ap.setLineAttributes( lineAT );
        
        // blue Material
        Material blueMat = new Material();
        blueMat.setDiffuseColor( BLUE );
        blueMat.setSpecularColor( WHITE );
        blueMat.setShininess( SHINY );
        ap.setMaterial( blueMat );
        
        // transparent
        TransparencyAttributes transpAT = new TransparencyAttributes();
        transpAT.setTransparencyMode( TransparencyAttributes.BLENDED );
        transpAT.setTransparency( TRANSPARENT );
        ap.setTransparencyAttributes( transpAT );
        
        return ap;
    }
    
}
