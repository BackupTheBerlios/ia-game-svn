/*
 * Created on May 12, 2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

import java.util.ArrayList;

import diabalik.move.DeplacerMove;
import diabalik.move.PasserMove;

public class MoveGenerator {
    
    PositionGrid2D runnerDir[] = {
            PositionGrid2D.UP,
            PositionGrid2D.RIGHT, 
            PositionGrid2D.DOWN,
            PositionGrid2D.LEFT 
    };
    ArrayList<PositionGrid2D> listRunner;
    PositionGrid2D posThrower;
    ArrayList<EtatJeu> listNextState;
     
    public MoveGenerator()
    {
        listRunner = new ArrayList<PositionGrid2D>();
    }
    
    public ArrayList<EtatJeu> getPotentialMove( EtatJeu etat )
    {
        listNextState = new ArrayList<EtatJeu>(); 
        
        // where are the runners
        updateListPiece(etat);
        moveRunner(etat);
        throwBall(etat);
        
        return listNextState;
    }
    
    /**
     * Find all 'Piece' of current Joueur.
     * @param etat
     * @return
     */
    private void updateListPiece( EtatJeu etat)
    {
        listRunner.clear();
        PositionGrid2D place;
        
        Plateau board = etat.zePlateau;
        for( int ligne=0; ligne<Plateau.tailleL; ligne++) {
            for( int col=0; col<Plateau.tailleC; col++) {
                place = new PositionGrid2D(ligne,col);
                if( board.getCase(place) != null ) {
                    Piece pion = board.getCase(place);
                    if(pion.m_joueur.couleur == etat.getTurn()) {
                        if( pion.type == Piece.coureur ) {
                            listRunner.add(new PositionGrid2D(place));
                        }
                        else {
                            posThrower = new PositionGrid2D(place);
                        }
                    }
                        
                }
                
            }
        }
    }
    /**
     * Try every 'deplacer' for every 'runner'.
     * @param etat
     */
    private void moveRunner( EtatJeu etat)
    {
        for (PositionGrid2D runner : listRunner) {
            for (PositionGrid2D dir : runnerDir) {
                Mouvement move = new DeplacerMove( etat.zeJoueurs[etat.getTurn()],
                        runner, runner.add(dir));
                try {
                    EtatJeu nextState = new EtatJeu( etat );
                    move.apply(nextState);
                    listNextState.add(nextState);
                    //System.out.println("  "+move.toString()+" OK");
                } catch (Exception e) {
                    //System.out.println(e.getMessage());
                    //System.out.println("  "+move.toString()+" INVALIDE");
                }
            }
        }
    }
    /**
     * Try every 'passer' to every 'runner'.
     * @param etat
     */
    private void throwBall( EtatJeu etat )
    {
        for (PositionGrid2D runner : listRunner) {
            Mouvement move = new PasserMove( etat.zeJoueurs[etat.getTurn()],
                    posThrower, runner);
            try {
                EtatJeu nextState = new EtatJeu( etat );
                move.apply(nextState);
                listNextState.add(nextState);
                //System.out.println("  "+move.toString()+" OK");
            } catch (Exception e) {
                //System.out.println(e.getMessage());
                //System.out.println("  "+move.toString()+" INVALIDE");
            }
            }
    }
    
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append("**** Thrower at " + posThrower.toString());
        strbuf.append("**** listRunner\n");
        for (PositionGrid2D runner : listRunner) {
            strbuf.append("  "+runner.toString());
        }
        if( listNextState != null ) {
            strbuf.append("**** nextState\n");
            for (EtatJeu etat : listNextState) {
                strbuf.append( etat.toString());
            }
        }
        return strbuf.toString();
    }
}
