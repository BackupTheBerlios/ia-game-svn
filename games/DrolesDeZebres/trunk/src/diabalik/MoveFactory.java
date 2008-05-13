/*
 * Created on May 12, 2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

import game.ExtractException;

import java.util.StringTokenizer;

import diabalik.move.DeplacerMove;
import diabalik.move.NullMove;
import diabalik.move.PasserMove;

public class MoveFactory {
    
    Jeu game;
    
    public MoveFactory( Jeu game )
    {
        this.game = game;
    }
    
    /**
     * Create a Move from a String representation.
     * @return null ou iseC, CloneC, LarcinC, RecyclageC.
     * @throws ExtractException 
     */
    public Mouvement extractFrom( String buff )
    throws ExtractException    
    {
        //System.out.println("MoveFactory : "+buff);
      
        PositionGrid2D posDebut, posFin;
        
        StringTokenizer st = new StringTokenizer( buff, ":(,)+ \t\n\r\f");
        String token;
        
        // Player
        token = st.nextToken();
        Joueur zeJoueur = game.getJoueur( token );
        // debug
//        if( zeJoueur != null ) {
//            System.out.println( "(J)  "+token+" : "+ zeJoueur.toString());
//        }
//        else {
//            System.out.println( "(J)  "+token+" : "+ Joueur.type_nul);   
//        };
        // end-debug
        // Piece
        token = st.nextToken();
        int zeType = Mouvement.decode( token );
        
        // case
        if( zeJoueur != null ) {
            token = st.nextToken();
            int posX = Integer.decode( token ).intValue();
            
            token = st.nextToken();
            int posY = Integer.decode( token ).intValue();
            posDebut = new PositionGrid2D(posX,posY);
            
            // echange ou suite
            token = st.nextToken();
            
            if( token.equals("-") ) {
                
                token = st.nextToken();
                posX = Integer.decode( token ).intValue();
                
                token = st.nextToken();
                posY = Integer.decode( token ).intValue();
                posFin = new PositionGrid2D(posX,posY);
                
                switch(zeType) {
                case Mouvement.depl:
                    return new DeplacerMove( zeJoueur, posDebut, posFin);
                case Mouvement.pass:
                    return new PasserMove( zeJoueur, posDebut, posFin);
                case Mouvement.none:
                    return new NullMove( zeJoueur );
                default:
                    throw new ExtractException("Move not recognised : " + buff);
                }
            }
        }
        throw new ExtractException("Move not recognised : " + buff);
    }
}
