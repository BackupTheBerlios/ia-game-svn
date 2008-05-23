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
    double estimatedStatesToVisit;
    static int facteurBranchement = 6*4+5+1;
    ArrayList<Mouvement> bestStrategy, tmpStrategy;
    Jeu game;
    
//  ---------- a Private Logger ---------------------
	private Logger logger = Logger.getLogger(AlphaBeta.class);
	// --------------------------------------------------
    
    public AlphaBeta( Jeu game )
    {
        this.game = game;
    }
    
    public ArrayList<Mouvement> getBestStrategy()
    {
    	return bestStrategy;
    }
    public int getNbVisitedStates()
    {
    	return nbVisitedStates;
    }
    public double getEstimatedStatesToVisit()
    {
    	return estimatedStatesToVisit;
    }
    
    public int findBestMoves( Joueur zeJoueur, EtatJeu etat, int depthMax )
    throws GameException
    {
        nbVisitedStates = 0;
        estimatedStatesToVisit = Math.pow(facteurBranchement, depthMax);
        logger.info("START depthMax="+depthMax+"  estNb="+estimatedStatesToVisit);
        bestStrategy = new ArrayList<Mouvement>();
        tmpStrategy = new ArrayList<Mouvement>();
        
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
       nbVisitedStates ++;
       logger.info("MAX["+depth+"] alpha="+alpha+" beta="+beta+" visited="+nbVisitedStates);
       logger.debug(etat.toString());
       // Il est temps d'�valuer
       if( timeToCut( etat, depth )) {
           logger.info("MAX["+depth+"] ==> Cutoff="+Jeu.zeHeuristicState.getValue( etat, zeJoueur ));
    	   //zeJeu.getState().bestMoves.clear();
           return Jeu.zeHeuristicState.getValue( etat, zeJoueur );
       }
       
       // G�n�re les mvt potentiels
       //ArrayList<EtatJeu> listeMvtPot = new ArrayList<EtatJeu>( Jeu.zeMoveGenerator.getPotentialMove(etat));
       ArrayList<EtatJeu> listeMvtPot = Jeu.zeMoveGenerator.getPotentialMove(etat);
       //ArrayList listeMvtPot = generateNextMoves( zeJeu );
       // cherche le meilleur � partir d'ici
       
       estimatedStatesToVisit -= (facteurBranchement - listeMvtPot.size()) * Math.pow(facteurBranchement, depth-1);
       logger.info("MAX["+depth+"] NbMove="+listeMvtPot.size()+" estNb="+estimatedStatesToVisit);
       
       int nbSeen = 0;
       for (EtatJeu tmpEtat : listeMvtPot) {
    	   nbSeen++;
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
               // it adds to the actuel bestStrategy if it increases is size
               if( depth == (bestStrategy.size()+1)) {
            	   bestStrategy.add(0, tmpEtat.getLastMove());
            	   logger.debug("MAX["+depth+"] bestStrategy="+bestStrategy.toString());
               }
               else {
            	   // it could increase the actuel tmpStrategy
            	   if( depth == (tmpStrategy.size()+1)) {
            		   tmpStrategy.add(0, tmpEtat.getLastMove());
            		   // which may become THE new bestStrategy
                	   if( tmpStrategy.size() >= bestStrategy.size() ) {
                		   bestStrategy = tmpStrategy;
                		   logger.debug("MAX["+depth+"] bestStrategy="+bestStrategy.toString());
                	   }
            	   }
            	   else {
            		   // a new tmpStrategy is initiated
            		   tmpStrategy = new ArrayList<Mouvement>();
                	   tmpStrategy.add(0, tmpEtat.getLastMove());
            	   }
            	   
               }
               logger.debug("MAX["+depth+"] tmpStrategy="+tmpStrategy.toString());
               
//               zeJeu.getState().bestMoves.clear();
//               zeJeu.getState().bestMoves.add( tmpJeu.getState().dernierMvt );
//               zeJeu.getState().bestMoves.addAll( tmpJeu.getState().bestMoves );
           }
           if( alpha >= beta ) {
               // pas besoin de continuer, on est sur de faire mieux que l'autre...
        	   estimatedStatesToVisit -= (listeMvtPot.size()-nbSeen) * Math.pow(facteurBranchement, depth-1);
        	   logger.info("MAX["+depth+"] alpha="+alpha+" beta="+beta+" ==> Beta Cutoff estNb="+estimatedStatesToVisit);
        	   
               return beta;
           }
           if( Jeu.zeHeuristicState.isMaxValue(alpha) ) {
        	   // pas besoin de continuer, on est sur de gagner...
        	   estimatedStatesToVisit -= (listeMvtPot.size()-nbSeen) * Math.pow(facteurBranchement, depth-1);
        	   logger.info("MAX["+depth+"] alpha="+alpha+" beta="+beta+" ==> WIN Cutoff estNb="+estimatedStatesToVisit);
        
        	   return alpha;
           }
       }
       logger.info("MAX["+depth+"] alpha="+alpha+" beta="+beta+" ==> return Alpha ");
       return alpha;
   }
   public int lookForMinMoves( Joueur zeJoueur, EtatJeu etat, int depth, int alpha, int beta )
   throws GameException
   {
       nbVisitedStates ++;
       logger.info("MIN["+depth+"] alpha="+alpha+" beta="+beta+" visited="+nbVisitedStates);
       logger.debug(etat.toString());
       // Il est temps d'�valuer
       if( timeToCut( etat, depth )) {
           //addEvalues( zeJeu );
           logger.info("MIN["+depth+"] ==> Cutoff="+Jeu.zeHeuristicState.getValue( etat, zeJoueur ));
           //zeJeu.getState().bestMoves.clear();
           return Jeu.zeHeuristicState.getValue( etat, zeJoueur );
       }
       
       // G�n�re les mvt potentiels
       //ArrayList listeMvtPot = new ArrayList(generateNextMoves( zeJeu ));
       ArrayList<EtatJeu> listeMvtPot = Jeu.zeMoveGenerator.getPotentialMove(etat);
       // cherche le meilleur � partir d'ici
       
       estimatedStatesToVisit -= (facteurBranchement - listeMvtPot.size()) * Math.pow(facteurBranchement, depth-1);
       logger.info("MIN["+depth+"] NbMove="+listeMvtPot.size()+" estNb="+estimatedStatesToVisit);
       
       int nbSeen = 0;
       for (EtatJeu tmpEtat : listeMvtPot) {
    	   nbSeen++;
           int bestMax;
    	   if( tmpEtat.getTurn() == Joueur.otherColor(zeJoueur.couleur) ) {
    		   // toujours au même (MINIMIZING) de jouer
    		   bestMax = lookForMinMoves( zeJoueur, tmpEtat, depth-1, alpha, beta);
    	   }
    	   else {
    		   bestMax = lookForMaxMoves( zeJoueur, tmpEtat, depth-1, alpha, beta);
    	   }
           if( bestMax < beta ) {
               // meilleur mouvement pour Min
               logger.info("MIN["+depth+"] alpha="+alpha+" beta="+beta+" ==> new BestMove="+tmpEtat.getLastMove().toString());
               beta = bestMax;
               // it adds to the actuel bestStrategy if it increases is size
               if( depth == (bestStrategy.size()+1)) {
            	   bestStrategy.add(0, tmpEtat.getLastMove());
            	   logger.debug("MIN["+depth+"] bestStrategy="+bestStrategy.toString());
               }
               else {
            	   // it could increase the actuel tmpStrategy
            	   if( depth == (tmpStrategy.size()+1)) {
            		   tmpStrategy.add(0, tmpEtat.getLastMove());
            		   // which may become THE new bestStrategy
                	   if( tmpStrategy.size() >= bestStrategy.size() ) {
                		   bestStrategy = tmpStrategy;
                		   logger.debug("MIN["+depth+"] bestStrategy="+bestStrategy.toString());
                	   }
            	   }
            	   else {
            		   // a new tmpStrategy is initiated
            		   tmpStrategy = new ArrayList<Mouvement>();
                	   tmpStrategy.add(0, tmpEtat.getLastMove());
            	   }
            	   
               }
               logger.debug("MIN["+depth+"] tmpStrategy="+tmpStrategy.toString());
               
//               zeJeu.getState().bestMoves.clear();
//               zeJeu.getState().bestMoves.add( tmpJeu.getState().dernierMvt );
//               zeJeu.getState().bestMoves.addAll( tmpJeu.getState().bestMoves );
           }
           if( beta <= alpha ) {
               // pas besoin de continuer
        	   estimatedStatesToVisit -= (listeMvtPot.size()-nbSeen) * Math.pow(facteurBranchement, depth-1);
               logger.info("MIN["+depth+"] alpha="+alpha+" beta="+beta+" ==> Beta Cutoff estNb="+estimatedStatesToVisit);
               return alpha;
           }
           if( Jeu.zeHeuristicState.isMaxValue(-beta) ) {
        	   // pas besoin de continuer, on est sur de gagner...
        	   estimatedStatesToVisit -= (listeMvtPot.size()-nbSeen) * Math.pow(facteurBranchement, depth-1);
        	   logger.info("MIN["+depth+"] alpha="+alpha+" beta="+beta+" ==> WIN Cutoff  estNb="+estimatedStatesToVisit);
        	   return beta;
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
    
