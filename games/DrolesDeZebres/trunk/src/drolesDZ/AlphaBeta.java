/*
 * Created on Jan 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

import game.GameException;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author dutech
 */
public class AlphaBeta {
    public GenerateurMouvement genMvt;
    //public ArrayList listeJeuEvalues;
    public int nbVisitedStates;
    public Mouvement[] bestMoves;
    
    public AlphaBeta( GenerateurMouvement p_genMvt)
    {
        genMvt = p_genMvt;
        //listeJeuEvalues = new ArrayList();
    }
    
    /**
     * Cherche les meilleurs Mouvement avec une profondeur de recherche donn�e.
     * @param zeJoueur dont on cherche � optimiser les Mvts.
     * @param zeJeu Jeu de d�part
     * @param depthMax profondeur max
     * @throws GameException
     */
    public int findBestMoves( Joueur zeJoueur, Jeu zeJeu, int depthMax )
    throws GameException
    {
        nbVisitedStates = 0;
        return lookForMaxMoves( zeJoueur, zeJeu, depthMax, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    public int findBest( Joueur zeJoueur, Jeu zeJeu, int depthMax )
    throws GameException
    {
        nbVisitedStates = 0;
        return lookForMax( zeJoueur, zeJeu, depthMax, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    public int findBestMovesArray( Joueur zeJoueur, Jeu zeJeu, int depthMax )
    throws GameException
    {
        bestMoves = new Mouvement[depthMax+1];
        nbVisitedStates = 0;
        return lookForMaxMovesArray( zeJoueur, zeJeu, depthMax, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    /**
     * Cherche la valeur Max de l'�tat suivant.
     * @param zeJoueur
     * @param zeJeu jeu actuel
     * @param depth Profondeur de recherche voulue.
     * @param alpha meilleure valeur courante pour joueur Max
     * @param beta meilleure valeur courante pour joueur Min
     * @return meilleure valeure pour Max
     * @throws GameException
     */
    public int lookForMax( Joueur zeJoueur, Jeu zeJeu, int depth, int alpha, int beta )
    throws GameException
    {
        nbVisitedStates ++;
        //debug_System.out.println( "MAX["+depth+"]\n"+zeJeu.displayStr());
        // Il est temps d'�valuer
        if( timeToCut( zeJeu, depth )) {
            //addEvalues( zeJeu );
            //debug_System.out.println( "MAX["+depth+"]CutOff = "+zeJeu.getValue( zeJoueur ));
            return zeJeu.getValue( zeJoueur );
        }
        
        // G�n�re les mvt potentiels
        ArrayList listeMvtPot = new ArrayList(generateNextMoves( zeJeu ));
        // cherche le meilleur � partir d'ici
        //debug_System.out.println( "MAX["+depth+"]"+listeMvtPot.size()+" mvt possibles");
        for (Iterator iter = listeMvtPot.iterator(); iter.hasNext();) {
            Jeu tmpJeu = (Jeu) iter.next();
            alpha = Math.max( alpha, lookForMin( zeJoueur, tmpJeu, depth-1, alpha, beta));
//            int bestMin = lookForMinMoves( zeJoueur, tmpJeu, depth-1, alpha, beta);
//            if( bestMin > alpha ) {
//                // meilleur mouvement
//                //debug_System.out.println( "MAX["+depth+"]"+"new bestMvt = "+ tmpJeu.getState().dernierMvt.toString() );
//                alpha = bestMin;
//            }
            if( alpha >= beta ) {
                // pas besoin de continuer
                //debug_System.out.println( "MAX["+depth+"]"+"Beta Cutoff = "+beta );
                return beta;
            }
        }
        //debug_System.out.println( "MAX["+depth+"]"+"Alpha = "+alpha);
        return alpha;
    }
    public int lookForMin( Joueur zeJoueur, Jeu zeJeu, int depth, int alpha, int beta )
    throws GameException
    {
        nbVisitedStates ++;
        //debug_System.out.println( "MIN["+depth+"]\n"+zeJeu.displayStr());
        // Il est temps d'�valuer
        if( timeToCut( zeJeu, depth )) {
            //addEvalues( zeJeu );
            //debug_System.out.println( "MIN["+depth+"]CutOff = "+zeJeu.getValue( zeJoueur ));
            return zeJeu.getValue( zeJoueur );
        }
        
        // G�n�re les mvt potentiels
        ArrayList listeMvtPot = new ArrayList(generateNextMoves( zeJeu ));
        // cherche le meilleur � partir d'ici
        //debug_System.out.println( "MIN["+depth+"]"+listeMvtPot.size()+" mvt possibles");
        for (Iterator iter = listeMvtPot.iterator(); iter.hasNext();) {
            Jeu tmpJeu = (Jeu) iter.next();
            beta = Math.min( beta, lookForMax( zeJoueur, tmpJeu, depth-1, alpha, beta));
//            int bestMax = lookForMaxMoves( zeJoueur, tmpJeu, depth-1, alpha, beta);
//            if( bestMax < beta ) {
//                // meilleur mouvement pour Min
//                //debug_System.out.println( "MIN["+depth+"]"+"new bestMvt = "+ tmpJeu.getState().dernierMvt.toString() );
//                beta = bestMax;
//            }
            if( beta <= alpha ) {
                // pas besoin de continuer
                //debug_System.out.println( "MIN["+depth+"]"+"Alpha Cutoff = "+alpha );
                return alpha;
            }
        }
        //debug_System.out.println( "MIN["+depth+"]"+"Beta = "+beta);
        return beta;
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
        // Il est temps d'�valuer
        if( timeToCut( zeJeu, depth )) {
            //addEvalues( zeJeu );
            zeJeu.getState().bestMoves.clear();
            //debug_System.out.println( "MAX["+depth+"]CutOff = "+zeJeu.getValue( zeJoueur ));
            return zeJeu.getValue( zeJoueur );
        }
        
        // G�n�re les mvt potentiels
        ArrayList listeMvtPot = new ArrayList(generateNextMoves( zeJeu ));
        //ArrayList listeMvtPot = generateNextMoves( zeJeu );
        // cherche le meilleur � partir d'ici
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
    public int lookForMinMoves( Joueur zeJoueur, Jeu zeJeu, int depth, int alpha, int beta )
    throws GameException
    {
        nbVisitedStates ++;
        //debug_System.out.println( "MIN["+depth+"]\n"+zeJeu.displayStr());
        // Il est temps d'�valuer
        if( timeToCut( zeJeu, depth )) {
            //addEvalues( zeJeu );
            //debug_System.out.println( "MIN["+depth+"]CutOff = "+zeJeu.getValue( zeJoueur ));
            zeJeu.getState().bestMoves.clear();
            return zeJeu.getValue( zeJoueur );
        }
        
        // G�n�re les mvt potentiels
        ArrayList listeMvtPot = new ArrayList(generateNextMoves( zeJeu ));
        // cherche le meilleur � partir d'ici
        //debug_System.out.println( "MIN["+depth+"]"+listeMvtPot.size()+" mvt possibles");
        for (Iterator iter = listeMvtPot.iterator(); iter.hasNext();) {
            Jeu tmpJeu = (Jeu) iter.next();
            int bestMax = lookForMaxMoves( zeJoueur, tmpJeu, depth-1, alpha, beta);
            if( bestMax < beta ) {
                // meilleur mouvement pour Min
                //debug_System.out.println( "MIN["+depth+"]"+"new bestMvt = "+ tmpJeu.getState().dernierMvt.toString() );
                beta = bestMax;
                zeJeu.getState().bestMoves.clear();
                zeJeu.getState().bestMoves.add( tmpJeu.getState().dernierMvt );
                zeJeu.getState().bestMoves.addAll( tmpJeu.getState().bestMoves );
            }
            if( beta <= alpha ) {
                // pas besoin de continuer
                //debug_System.out.println( "MIN["+depth+"]"+"Alpha Cutoff = "+alpha );
                return alpha;
            }
        }
        //debug_System.out.println( "MIN["+depth+"]"+"Beta = "+beta);
        return beta;
    }

    /**
     * Cherche la meilleure valeur ET les meilleur Mouvement pour Max.
     * La suite des meilleurs Mouvement est dans bestMoves[].
     * @param zeJoueur
     * @param zeJeu
     * @param depth
     * @param alpha
     * @param beta
     * @return
     * @throws GameException
     */
    public int lookForMaxMovesArray( Joueur zeJoueur, Jeu zeJeu, int depth, int alpha, int beta )
    throws GameException
    {
        nbVisitedStates ++;
        //debug_System.out.println( "MAX["+depth+"]\n"+zeJeu.displayStr());
        // Il est temps d'�valuer
        if( timeToCut( zeJeu, depth )) {
            addEvalues( zeJeu );
            zeJeu.getState().bestMoves.clear();
            //debug_System.out.println( "MAX["+depth+"]CutOff = "+zeJeu.getValue( zeJoueur ));
            return zeJeu.getValue( zeJoueur );
        }
        
        // G�n�re les mvt potentiels
        ArrayList listeMvtPot = new ArrayList(generateNextMoves( zeJeu ));
        // cherche le meilleur � partir d'ici
        //debug_System.out.println( "MAX["+depth+"]"+listeMvtPot.size()+" mvt possibles");
        for (Iterator iter = listeMvtPot.iterator(); iter.hasNext();) {
            Jeu tmpJeu = (Jeu) iter.next();
            int bestMin = lookForMinMovesArray( zeJoueur, tmpJeu, depth-1, alpha, beta);
            if( bestMin > alpha ) {
                // meilleur mouvement
                //debug_System.out.println( "MAX["+depth+"]"+"new bestMvt = "+ tmpJeu.getState().dernierMvt.toString() );
                alpha = bestMin;
                bestMoves[depth] = tmpJeu.getState().dernierMvt;
                
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
    public int lookForMinMovesArray( Joueur zeJoueur, Jeu zeJeu, int depth, int alpha, int beta )
    throws GameException
    {
        nbVisitedStates ++;
        //debug_System.out.println( "MIN["+depth+"]\n"+zeJeu.displayStr());
        // Il est temps d'�valuer
        if( timeToCut( zeJeu, depth )) {
            addEvalues( zeJeu );
            //debug_System.out.println( "MIN["+depth+"]CutOff = "+zeJeu.getValue( zeJoueur ));
            zeJeu.getState().bestMoves.clear();
            return zeJeu.getValue( zeJoueur );
        }
        
        // G�n�re les mvt potentiels
        ArrayList listeMvtPot = new ArrayList(generateNextMoves( zeJeu ));
        // cherche le meilleur � partir d'ici
        //debug_System.out.println( "MIN["+depth+"]"+listeMvtPot.size()+" mvt possibles");
        for (Iterator iter = listeMvtPot.iterator(); iter.hasNext();) {
            Jeu tmpJeu = (Jeu) iter.next();
            int bestMax = lookForMaxMovesArray( zeJoueur, tmpJeu, depth-1, alpha, beta);
            if( bestMax < beta ) {
                // meilleur mouvement pour Min
                //debug_System.out.println( "MIN["+depth+"]"+"new bestMvt = "+ tmpJeu.getState().dernierMvt.toString() );
                beta = bestMax;
                bestMoves[depth] = tmpJeu.getState().dernierMvt;
            }
            if( beta <= alpha ) {
                // pas besoin de continuer
                //debug_System.out.println( "MIN["+depth+"]"+"Alpha Cutoff = "+alpha );
                return alpha;
            }
        }
        //debug_System.out.println( "MIN["+depth+"]"+"Beta = "+beta);
        return beta;
    }

    
    /**
     * G�n�re les mouvements possibles � partir d'une position.
     * @param p_jeu actuel
     * @return ArrayList des Jeu suivants
     * @throws GameException
     */
    public ArrayList generateNextMoves( Jeu p_jeu )
    throws GameException
    {
        genMvt.potentialJeu( p_jeu );
        return genMvt.listeJeu;
    }
    
    /**
     * Ajoute le Jeu � la liste des Jeu �valu�s (s'il ne l'�tait pas d�j�).
     * @param p_jeu
     */
    public void addEvalues( Jeu p_jeu )
    {
        // s'il n'est pas d�j� �valu�
//        for( Iterator iJeuS = listeJeuEvalues.iterator(); iJeuS.hasNext();) {
//            EtatJeu tmpJeuS = (EtatJeu) iJeuS.next();
//            if( p_jeu.getState().equals( tmpJeuS )) {
//                return;
//            }
//        }
//        listeJeuEvalues.add( p_jeu.getState());
    }
    public String displayEvalues() 
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( "-----<Jeu � �valuer>-------\n");
//        for (Iterator iterJeu = listeJeuEvalues.iterator(); iterJeu.hasNext();) {
//            EtatJeu tmpJeu = (EtatJeu) iterJeu.next();
//            strbuf.append( tmpJeu.toString());
//        }
        strbuf.append( "-----<end Jeu � �valuer>-----\n");
        return strbuf.toString();
    }
    /**
     * Arr�te-t-on la recherche en profondeur?
     * @param zeJeu
     * @param depth (profondeur � laquelle on veut descendre)
     * @return true si on doit s'arr�ter.
     */
    public boolean timeToCut( Jeu zeJeu, int depth )
    {
        if ((depth == 0) || (zeJeu.getFinJeu())) {
            return true;
        }   
        return false; 
    }
}
