/*
 * Created on May 12, 2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

import game.GameException;

import java.util.ArrayList;
import java.util.Iterator;


public class AlphaBeta {
  
    int nbVisitedStates;
    ArrayList<Mouvement> bestMoves;
    Jeu game;
    
    public AlphaBeta( Jeu game )
    {
        this.game = game;
    }
    
    /**
    * Cherche la meilleure valeur ET les meilleur Mouvement pour Max.
    * La suite des meilleurs Mouvement est dans EtatJeu.bestMoves.
    * @param zeJoueur
    * @param zeJeu
    * @param depth
    * @param alpha
    * @param beta
    * @return
    * @throws GameException
    */
   public int lookForMaxMoves( Joueur zeJoueur, Jeu zeJeu, int depth, int alpha, int beta )
   throws GameException
   {
       nbVisitedStates ++;
       //debug_System.out.println( "MAX["+depth+"]\n"+zeJeu.displayStr());
       // Il est temps d'ï¿½valuer
       if( timeToCut( zeJeu, depth )) {
           //debug_System.out.println( "MAX["+depth+"]CutOff = "+zeJeu.getValue( zeJoueur ));
           return zeJeu.getValue( zeJoueur );
       }
       
       // Gï¿½nï¿½re les mvt potentiels
       ArrayList listeMvtPot = new ArrayList(generateNextMoves( zeJeu ));
       //ArrayList listeMvtPot = generateNextMoves( zeJeu );
       // cherche le meilleur ï¿½ partir d'ici
       //debug_System.out.println( "MAX["+depth+"]"+listeMvtPot.size()+" mvt possibles");
       for (Iterator iter = listeMvtPot.iterator(); iter.hasNext();) {
           Jeu tmpJeu = (Jeu) iter.next();
           int bestMin = lookForMinMoves( zeJoueur, tmpJeu, depth-1, alpha, beta);
           if( bestMin > alpha ) {
               // meilleur mouvement
               //debug_System.out.println( "MAX["+depth+"]"+"new bestMvt = "+ tmpJeu.getState().dernierMvt.toString() );
               alpha = bestMin;
               zeJeu.getState().bestMoves.clear();
               zeJeu.getState().bestMoves.add( tmpJeu.getState().dernierMvt );
               zeJeu.getState().bestMoves.addAll( tmpJeu.getState().bestMoves );
           }
           if( alpha >= beta ) {
               // pas besoin de continuer
               //debug_System.out.println( "MAX["+depth+"]"+"Beta Cutoff = "+beta );
               return beta;
           }
       }
       //debug_System.out.println( "MAX["+depth+"]"+"Alpha = "+alpha);
       return alpha;
   }
   
   /**
    * Arrete-t-on la recherche en profondeur ?
    * @param etat
    * @param depth (profondeur à laquelle on veut encore descendre)
    * @return true si on doit s'arrêter.
    */
   public boolean timeToCut( EtatJeu etat, int depth )
   {
       if ((depth == 0) || (game.isEndState( etat ) {
           return true;
       }   
       return false; 
   }
}
    
