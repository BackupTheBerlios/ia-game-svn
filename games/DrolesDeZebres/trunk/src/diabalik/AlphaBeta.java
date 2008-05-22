/*
 * Created on May 12, 2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

import game.GameException;

import java.util.ArrayList;

import org.apache.log4j.Logger;


public class AlphaBeta {
  
    int nbVisitedStates;
    ArrayList<Mouvement> bestMoves;
    Jeu game;
    
//  ---------- a Private Logger ---------------------
	private Logger logger = Logger.getLogger(AlphaBeta.class);;
	// --------------------------------------------------
    
    public AlphaBeta( Jeu game )
    {
        this.game = game;
    }
    
    public int findBestMoves( Joueur zeJoueur, EtatJeu etat, int depthMax )
    throws GameException
    {
        nbVisitedStates = 0;
        return lookForMaxMoves( zeJoueur, etat, depthMax, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    /**
    * Cherche la meilleure valeur ET les meilleur Mouvement pour Max.
    * La suite des meilleurs Mouvement est dans EtatJeu.bestMoves.
    * @param zeJoueur
    * @param zeJeu
    * @param depth
    * @param alpha Valeur min du joueur cherchant à Maximiser
    * @param beta  Valeur max du joueur cherchant à Minimiser
    * @return
    * @throws GameException
    */
   public int lookForMaxMoves( Joueur zeJoueur, EtatJeu etat, int depth, int alpha, int beta )
   throws GameException
   {
	   Mouvement bestMove = new Mouvement();
       nbVisitedStates ++;
       logger.info("MAX["+depth+"] alpha="+alpha+" beta="+beta);
       logger.debug(etat.toString());
       // Il est temps d'�valuer
       if( timeToCut( etat, depth )) {
           logger.info("MAX["+depth+"] ==> Cutoff="+game.getValue( etat, zeJoueur ));
    	   //zeJeu.getState().bestMoves.clear();
           return game.getValue( etat, zeJoueur );
       }
       
       // G�n�re les mvt potentiels
       //ArrayList<EtatJeu> listeMvtPot = new ArrayList<EtatJeu>( Jeu.zeMoveGenerator.getPotentialMove(etat));
       ArrayList<EtatJeu> listeMvtPot = Jeu.zeMoveGenerator.getPotentialMove(etat);
       //ArrayList listeMvtPot = generateNextMoves( zeJeu );
       // cherche le meilleur � partir d'ici
       
       logger.info("MAX["+depth+"] NbMove="+listeMvtPot.size());
       
       for (EtatJeu tmpEtat : listeMvtPot) {
    	   int bestMin;
    	   if( tmpEtat.getTurn() == zeJoueur.couleur ) {
    		   // toujours au même (MAXIMIZING) de jouer
    		   bestMin = lookForMaxMoves( zeJoueur, tmpEtat, depth-1, alpha, beta);
    	   }
    	   else {
    		   bestMin = lookForMinMoves( zeJoueur, tmpEtat, depth-1, alpha, beta);
    	   }
           if( bestMin > alpha ) {
               // meilleur mouvement
               logger.info("MAX["+depth+"] alpha="+alpha+" beta="+beta+" ==> new BestMove="+tmpEtat.getLastMove().toString());
               alpha = bestMin;
               bestMove = tmpEtat.getLastMove();
//               zeJeu.getState().bestMoves.clear();
//               zeJeu.getState().bestMoves.add( tmpJeu.getState().dernierMvt );
//               zeJeu.getState().bestMoves.addAll( tmpJeu.getState().bestMoves );
           }
           if( alpha >= beta ) {
               // pas besoin de continuer, on est sur de faire mieux que l'autre...
        	   logger.info("MAX["+depth+"] alpha="+alpha+" beta="+beta+" ==> Beta Cutoff ");
               return beta;
           }
       }
       logger.info("MAX["+depth+"] alpha="+alpha+" beta="+beta+" ==> return Alpha ");
       return alpha;
   }
   public int lookForMinMoves( Joueur zeJoueur, EtatJeu etat, int depth, int alpha, int beta )
   throws GameException
   {
	   Mouvement bestMove = new Mouvement();
       nbVisitedStates ++;
       logger.info("MIN["+depth+"] alpha="+alpha+" beta="+beta);
       logger.debug(etat.toString());
       // Il est temps d'�valuer
       if( timeToCut( etat, depth )) {
           //addEvalues( zeJeu );
           logger.info("MIN["+depth+"] ==> Cutoff="+game.getValue( etat, zeJoueur ));
           //zeJeu.getState().bestMoves.clear();
           return game.getValue( etat, zeJoueur );
       }
       
       // G�n�re les mvt potentiels
       //ArrayList listeMvtPot = new ArrayList(generateNextMoves( zeJeu ));
       ArrayList<EtatJeu> listeMvtPot = Jeu.zeMoveGenerator.getPotentialMove(etat);
       // cherche le meilleur � partir d'ici
       logger.info("MIN["+depth+"] NbMove="+listeMvtPot.size());
       
       for (EtatJeu tmpEtat : listeMvtPot) {
           int bestMax;
    	   if( tmpEtat.getTurn() == zeJoueur.couleur ) {
    		   // toujours au même (MAXIMIZING) de jouer
    		   bestMax = lookForMinMoves( zeJoueur, tmpEtat, depth-1, alpha, beta);
    	   }
    	   else {
    		   bestMax = lookForMaxMoves( zeJoueur, tmpEtat, depth-1, alpha, beta);
    	   }
           if( bestMax < beta ) {
               // meilleur mouvement pour Min
               logger.info("MIN["+depth+"] alpha="+alpha+" beta="+beta+" ==> new BestMove="+tmpEtat.getLastMove().toString());
               beta = bestMax;
               bestMove = tmpEtat.getLastMove();
//               zeJeu.getState().bestMoves.clear();
//               zeJeu.getState().bestMoves.add( tmpJeu.getState().dernierMvt );
//               zeJeu.getState().bestMoves.addAll( tmpJeu.getState().bestMoves );
           }
           if( beta <= alpha ) {
               // pas besoin de continuer
               logger.info("MIN"+depth+"] alpha="+alpha+" beta="+beta+" ==> Beta Cutoff ");
               return alpha;
           }
       }
       logger.info("MIN["+depth+"] alpha="+alpha+" beta="+beta+" ==> return Beta ");
       return beta;
   }
   
   /**
    * Arrete-t-on la recherche en profondeur ?
    * @param etat
    * @param depth (profondeur � laquelle on veut encore descendre)
    * @return true si on doit s'arr�ter.
    */
   public boolean timeToCut( EtatJeu etat, int depth )
   {
       if ((depth == 0) || (etat.isEndGame())) {
           return true;
       }   
       return false; 
   }
}
    
