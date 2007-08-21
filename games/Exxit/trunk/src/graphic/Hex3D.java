/*
 * Created on Mar 28, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graphic;

import java.util.ArrayList;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleFanArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 * A 3D Hexagon half red, half black.
 * @author dutech
 */
public class Hex3D extends Shape3D {
    
    
    private TriangleFanArray faceUp;
    private TriangleFanArray faceDown;
    private QuadArray sideUp;
    private QuadArray sideDown;
    static final double halfThick = 0.1;
    static final double radius = 1.0;
    
    static final Color3f colBlack = new Color3f( 0f, 0f, 0f);
    static final Color3f colRed = new Color3f( 1f, 0f, 0f);
    
    public Hex3D()
    {
        createGeometry();
    }
    
    private void createGeometry()
    {
        // 6 points of an Hex
        ArrayList<Point3d> coordFUp = new ArrayList<Point3d>();
        ArrayList<Point3d> coordSUp = new ArrayList<Point3d>();
        ArrayList<Point3d> coordFDown = new ArrayList<Point3d>();
        ArrayList<Point3d> coordSDown = new ArrayList<Point3d>();
        // first point
        coordFUp.add( new Point3d( 0, halfThick, 0));
        coordFDown.add( new Point3d( 0, -halfThick, 0));
        // otherPoints - Order is important for face normals
        for( double angle=0; angle >= -2*Math.PI; angle -= Math.PI/3 ) {
            coordFUp.add( new Point3d( radius * Math.cos(angle),
                                       halfThick, radius * Math.sin(angle)));
        }
        for( double angle=0; angle <= 2*Math.PI; angle += Math.PI/3 ) {
            coordFDown.add( new Point3d( radius * Math.cos(angle),
                                       -halfThick, radius * Math.sin(angle)));
        }
        
        // for each of the six Up sides
        double angleLast = 0;
        double angleNew = 0;
        for( int i=0; i<6; i++ ) {
            angleNew -= Math.PI/3;
            
            coordSUp.add( new Point3d( radius * Math.cos( angleLast),
                    halfThick, radius * Math.sin(angleLast)));
            coordSUp.add( new Point3d( radius * Math.cos( angleLast),
                    0, radius * Math.sin(angleLast)));
            coordSUp.add( new Point3d( radius * Math.cos( angleNew),
                    0, radius * Math.sin(angleNew)));
            coordSUp.add( new Point3d( radius * Math.cos( angleNew),
                    halfThick, radius * Math.sin(angleNew)));
            
            angleLast = angleNew;
        }
        // for Down sides
        angleLast = 0;
        angleNew = 0;
        for( int i=0; i<6; i++ ) {
            angleNew += Math.PI/3;
            
            coordSDown.add( new Point3d( radius * Math.cos( angleLast),
                    -halfThick, radius * Math.sin(angleLast)));
            coordSDown.add( new Point3d( radius * Math.cos( angleLast),
                    0, radius * Math.sin(angleLast)));
            coordSDown.add( new Point3d( radius * Math.cos( angleNew),
                    0, radius * Math.sin(angleNew)));
            coordSDown.add( new Point3d( radius * Math.cos( angleNew),
                    -halfThick, radius * Math.sin(angleNew)));
            
            angleLast = angleNew;
        }
        
        // Geometry
        Point3d[] points;
        int[] nbVertex;
        // color
        Color3f colors[];
        
        // Create Fan for face Up
        points = new Point3d[coordFUp.size()];
        coordFUp.toArray( points );
        // number of strip in the Fan
        nbVertex = new int[1];
        for (int i = 0; i < nbVertex.length; i++) {
            nbVertex[i] = coordFUp.size();
        }
        faceUp  = new TriangleFanArray(points.length, 
                    GeometryArray.COORDINATES | GeometryArray.COLOR_3,
                    nbVertex);
        faceUp.setCoordinates(0, points);
        colors = new Color3f[points.length];
        for(int i=0; i < colors.length; i++)
          colors[i] = colBlack;
        faceUp.setColors(0, colors);
        addGeometry(faceUp);
        
        // Create for side Up
        points = new Point3d[coordSUp.size()];
        coordSUp.toArray( points );
        sideUp = new QuadArray( points.length, 
                    GeometryArray.COORDINATES | GeometryArray.COLOR_3 );
        sideUp.setCoordinates(0, points);
        colors = new Color3f[points.length];
        for(int i=0; i < colors.length; i++)
          colors[i] = colBlack;
        sideUp.setColors(0, colors);
        addGeometry(sideUp);
        
        // Create Fan for face Down
        points = new Point3d[coordFDown.size()];
        coordFDown.toArray( points );
        // number of strip in the Fan
        nbVertex = new int[1];
        for (int i = 0; i < nbVertex.length; i++) {
            nbVertex[i] = coordFDown.size();
        }
        faceDown  = new TriangleFanArray(points.length, 
                    GeometryArray.COORDINATES | GeometryArray.COLOR_3,
                    nbVertex);
        faceDown.setCoordinates(0, points);        
        colors = new Color3f[points.length];
        for(int i=0; i < colors.length; i++)
          colors[i] = colRed;
        faceDown.setColors(0, colors);

        addGeometry(faceDown);
        
        // Create for side Up
        points = new Point3d[coordSDown.size()];
        coordSDown.toArray( points );
        sideDown = new QuadArray( points.length, 
                    GeometryArray.COORDINATES | GeometryArray.COLOR_3 );
        sideDown.setCoordinates(0, points);
        colors = new Color3f[points.length];
        for(int i=0; i < colors.length; i++)
          colors[i] = colRed;
        sideDown.setColors(0, colors);
        addGeometry(sideDown);
    }
    
}
