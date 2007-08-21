/*
 * Created on Mar 7, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graphic;

import java.awt.Font;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Text2D;

/**
 * Create a visual Oxyz system.
 * 
 */
public class Axes3D {
    
    BranchGroup myGroup;
    
    private final static Color3f blue = new Color3f(0.0f, 0.0f, 1.0f);
    private final static Point3d origin = new Point3d(0.0, 0.0, 0.0);
    private final static Vector3d ox = new Vector3d(2.0, 0, 0);
    private final static Vector3d oy = new Vector3d(0,2,0);
    private final static Vector3d oz = new Vector3d(0,0,2);
    
    /**
     * Create the 3 lines with text symbols.
     */
    public Axes3D() 
    {
        myGroup = new BranchGroup();
        
        // X-axis
        LineArray axisX=new LineArray(2,LineArray.COORDINATES|LineArray.COLOR_3);
        axisX.setCoordinate(0, origin);
        axisX.setCoordinate(1, new Point3d(ox));
        axisX.setColor(0, blue);
        axisX.setColor(1, blue);
        myGroup.addChild( new Shape3D(axisX) );
        myGroup.addChild( makeText( ox, "X"));

        // Y-axis
        LineArray axisY=new LineArray(2,LineArray.COORDINATES|LineArray.COLOR_3);
        axisY.setCoordinate(0, origin);
        axisY.setCoordinate(1, new Point3d(oy));
        axisY.setColor(0, blue);
        axisY.setColor(1, blue);
        myGroup.addChild( new Shape3D(axisY) );
        myGroup.addChild( makeText( oy, "Y"));
        
        // Z-axis
        LineArray axisZ=new LineArray(2,LineArray.COORDINATES|LineArray.COLOR_3);
        axisZ.setCoordinate(0, origin);
        axisZ.setCoordinate(1, new Point3d(oz));
        axisZ.setColor(0, blue);
        axisZ.setColor(1, blue);
        myGroup.addChild( new Shape3D(axisZ) );
        myGroup.addChild( makeText( oz, "Z"));
        
    }

    private TransformGroup makeText(Vector3d vertex, String text)
    // Create a Text2D object at the specified vertex
    {
        Text2D message = new Text2D(text, blue, "SansSerif", 36, Font.BOLD );
        // 36 point bold Sans Serif
        
        TransformGroup tg = new TransformGroup();
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(vertex);
        tg.setTransform(t3d);
        tg.addChild(message);
        return tg;
    }
    
    /**
     * Get Axes as a BranchGroup.
     */
    public BranchGroup getBG()
    {
        return myGroup;
    }
    
    
}
