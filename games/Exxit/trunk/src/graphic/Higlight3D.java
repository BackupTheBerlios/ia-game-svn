/*
 * Created on Mar 28, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graphic;

import javax.media.j3d.Appearance;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;

public class Higlight3D {
    
    private Shape3D selectedNode;
    private Shape3D highlight3D;
    private TransformGroup highlightTG;
    
    static final float LINE_WIDTH = 3f;
    static final Color3f BLUE = new Color3f( 0f, 0f, 0.8f);
    static final Color3f WHITE = new Color3f( 1f, 1f, 1f );
    static final float SHINY = 40f;
    static final float TRANSPARENT = 0.7f;
    static final float SCALE_FACTOR = 1.1f;
    
    public Higlight3D( Shape3D p_node )
    {
        selectedNode = p_node;
        
        highlight3D = (Shape3D) selectedNode.cloneNode( true );
        highlight3D.setAppearance( initialAppearance() );
        setGeometry();
        
    }
    
    public TransformGroup getTG()
    {
        return highlightTG;
    }
    
    private void setGeometry()
    {
        Transform3D t3d = new Transform3D();
        t3d.set( SCALE_FACTOR );
        
        highlightTG = new TransformGroup( t3d );
        highlightTG.addChild( highlight3D );
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
