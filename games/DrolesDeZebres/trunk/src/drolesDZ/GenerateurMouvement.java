/*
 * Created on Dec 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

import game.GameException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author dutech
 */
public class GenerateurMouvement {
	
	public ArrayList listeMvt;
	public ArrayList listePosL, listePosC;
	public ArrayList listePiece;
	public ArrayList listeJeu;
	
	private Random genAlea;
	
	public GenerateurMouvement()
	{
	    listePosL = new ArrayList();
	    listePosC = new ArrayList();
	    listeMvt = new ArrayList();
	    listePiece = new ArrayList();
	    listeJeu = new ArrayList();
	    
	    genAlea = new Random();
	}
	public Mouvement initIndianaRandom( Jeu zeJeu)
	{
	    int position = genAlea.nextInt( Plateau.maxIndiana );
	    zeJeu.poserIndianaRegles( position );
	    Mouvement result = new Mouvement();
	    result.set( position );
	    return result;
	}
	public Jeu nextMoveRandom( Jeu zeJeu )
	throws GameException
	{
	    try {
	        potentialJeu( zeJeu );
        } catch (GameException e) {
            System.err.println( "Bizzare : on g�n�re des �ventuels mouvents irr�guliers.");
        }
	    
	    int nbPotential = listeJeu.size();
	    int indiceJ;
	    Jeu newJeu = null;
	    
	    if( nbPotential > 0 ) { // mouvement possible
	        indiceJ = genAlea.nextInt( nbPotential );
	        newJeu = (Jeu) listeJeu.get(indiceJ);
	        newJeu.setNbPotential( nbPotential );
	    }
	    else {
	        throw new GameException( "Auncun mouvement valide!!");
	    }
	    return newJeu;
	}
	
	/**
	 * Cherche tous les nouveau jeux possible pour le prochain tour.
	 * @param zeJeu
	 * @throws GameException
	 */
	public void potentialJeu( Jeu zeJeu )
	throws GameException
	{
	    // table rase
	    listeMvt.clear();
	    listeJeu.clear();
	    
	    Joueur zeJoueur = zeJeu.getJoueur(zeJeu.getTour());
	    potentialCases( zeJeu.getPlateau() );
	    potentialPiece( zeJoueur );
	    
	    // DEBUG ************************
	    //displayListePos();
	    //displayListePiece();
	    // ******************************
	    
	    for( Iterator  iL = listePosL.iterator(), iC = listePosC.iterator();
	    iL.hasNext(); ) {
	        Integer posL = (Integer) iL.next();
	        Integer posC = (Integer) iC.next();
	        // des pi�ces � jouer
	        if( listePiece.isEmpty() == false ) {
	            for( Iterator  iP = listePiece.iterator(); iP.hasNext(); ) {
	                Integer type = (Integer) iP.next();
	                Mouvement mvt = new Mouvement( zeJoueur, type.intValue(), 
	                        posL.intValue(), posC.intValue());
	                Jeu newJeu = new Jeu(zeJeu);
	  
	                // DEBUG *************************
	                //System.out.println( "potentialJeu : " + mvt.toString());
	                // *******************************
	                
	                // le mouvement devrait s'appliquer
	                if( !newJeu.poserPieceRegles(mvt.zeJoueur.couleur, mvt.zeType, mvt.posL, mvt.posC)) {
	                    throw new GameException( "Pose de Piece irr�guli�re");
	                }
	                makeMovesIndiana( newJeu, mvt );
	                makeMovesSwitch( newJeu, mvt );
	            }
	        }
	        else { // pas de piece � joueur
	            Jeu newJeu = new Jeu( zeJeu );
	            // mouvement nul
	            Mouvement mvt = new Mouvement();
		        mvt.zeJoueur = newJeu.getJoueur(newJeu.getTour());
		        mvt.setNoPiece();

                // DEBUG *************************
                //System.out.println( "potentialJeu : " + mvt.toString());
                // *******************************
		        
		        // le mouvement devrait s'appliquer
                if( !newJeu.poserPieceRegles(mvt.zeJoueur.couleur, mvt.zeType, mvt.posL, mvt.posC)) {
                    throw new GameException( "Pose de Piece irr�guli�re");
                }
                makeMovesIndiana( newJeu, mvt );
                makeMovesSwitch( newJeu, mvt );
	        }
	    }
	}
	/** 
	 * Cherche si des 
	 * echanges sont possibles avant de chercher le mvt d'Indiana
	 * @param aJeu o� il faut appliquer le Mouvement
	 * @param aMvt � appliquer
	 * @throws GameException
	 */
	public void makeMovesSwitch( Jeu aJeu, Mouvement aMvt )
	throws GameException
	{
        
        // peut-on faire des �changes
        if(aMvt.zeType == Piece.crocodile) {

            // trouver les positions du croco
            int posCrocoL, posCrocoC;
            if( aMvt.nbEchange > 0 ) {
                posCrocoL = aMvt.echL[aMvt.nbEchange-1];
                posCrocoC = aMvt.echC[aMvt.nbEchange-1];
            }
            else {
                posCrocoL = aMvt.posL;
                posCrocoC = aMvt.posC;
            }
            int echange[][] = aJeu.possibleSwitchAroundCroco( posCrocoL, posCrocoC);
            for (int indE = 0; indE < echange.length; indE++) {
                
                // DEBUG *******************************************
                // System.out.println( "Envisage ech = " + echange[indE][0] + ", " + echange[indE][1]);
                // *************************************************
                
                if( (echange[indE][0] != -1) && (echange[indE][1] != -1) ) {
                    // on le fait si c'est pas revenir en arri�re...
                    if( aMvt.nbEchange == 0 ) {
                        // Premier �change
                        Mouvement swapMvt = new Mouvement(aMvt);
                        // ne continuera que si on n'atteint pas le nb d'�change maxi.
                        if(swapMvt.addEchange( echange[indE][0], echange[indE][1])) {
                            Jeu swapJeu = new Jeu( aJeu );
                            
                            // DEBUG *******************************
                            // System.out.println( "Premier Ech : " + swapMvt.toString());
                            // *************************************
                            
                            if( !swapJeu.switchCrocoGazelleRegles( swapMvt.echL[swapMvt.nbEchange-2], swapMvt.echC[swapMvt.nbEchange-2], swapMvt.echL[swapMvt.nbEchange-1], swapMvt.echC[swapMvt.nbEchange-1])) {
                                throw new GameException( "Echange irregulier");
                            }
                            makeMovesIndiana( swapJeu, swapMvt );
                            makeMovesSwitch( swapJeu, swapMvt );
                        }
                    }

                    else { // deja eu echange...
                        if( (echange[indE][0] != aMvt.echL[aMvt.nbEchange-2]) ||
                                (echange[indE][1] != aMvt.echC[aMvt.nbEchange-2]) ) {
                            Mouvement swapMvt = new Mouvement(aMvt);
                            // ne continuera que si on n'atteint pas le nb d'�change maxi.
                            if (swapMvt.addEchange( echange[indE][0], echange[indE][1])) {
                                Jeu swapJeu = new Jeu( aJeu );
                                
                                // DEBUG *******************************
                                // System.out.println( " Ech no " + aMvt.nbEchange +" : " + swapMvt.toString());
                                // *************************************
                                
                                if( !swapJeu.switchCrocoGazelleRegles( swapMvt.echL[swapMvt.nbEchange-2], swapMvt.echC[swapMvt.nbEchange-2], swapMvt.echL[swapMvt.nbEchange-1], swapMvt.echC[swapMvt.nbEchange-1])) {
                                    throw new GameException( "Echange irregulier");
                                }
                                makeMovesIndiana( swapJeu, swapMvt );
                                makeMovesSwitch( swapJeu, swapMvt );
                            }
                        }
                    }
                }
            }
        }
	}
	/**
	 * Chercher les mouvements d'Indiana � partir d'un mouvement de Piece d�j� appliqu�
	 * � une situation de jeu.
	 * @param aJeu o� le mvt a �t� appliqu�
	 * @param aMvt le mvt appliqu�
	 */
	public void makeMovesIndiana( Jeu aJeu, Mouvement aMvt )
	throws GameException
	{
	    //	  Que faire de Indiana
	    if( aJeu.computeValidMoveIndiana() ) {
	        for( int i=0; i < 4; i++ ) {
	            if( aJeu.getValidMoveIndiana(i) > 0 ) {
	                Jeu nextJeu = new Jeu(aJeu);
	                if( !nextJeu.bougerIndianaRegles(aJeu.getValidMoveIndiana(i))) {
	                    
	                    throw new GameException( "Mouvement Impala irr�gulier\n"
	                            +nextJeu.displayStr()
	                            +"\n Impala -> "+aJeu.getValidMoveIndiana(i)+"\n");
	                }
	                nextJeu.computeBonusScore();
	                nextJeu.nextJoueur();
	                Mouvement nextMvt = new Mouvement(aMvt);
	                nextMvt.set( aJeu.getValidMoveIndiana(i));
	                nextJeu.setDernierMvt(nextMvt);
	                listeMvt.add(nextMvt);
	                listeJeu.add( nextJeu);
	            }
	        }
	    }
	    else {
	        // fin du jeu
	        aJeu.computeBonusScore();
	        aJeu.setFinJeu(true);
	        aJeu.setDernierMvt(aMvt);
	        listeMvt.add(aMvt);
	        listeJeu.add( aJeu);
	    }
	}
	public String displayListeJeu()
	{
	    StringBuffer strbuf = new StringBuffer();
        strbuf.append( "listeJeu = {");
        for( Iterator  i = listeJeu.iterator(); i.hasNext(); ) {
            Jeu zeJeu = (Jeu) i.next();
            strbuf.append( "------\n" + zeJeu.displayStr() + "\n" );
        }
        strbuf.append( "}");
        return strbuf.toString();  
	}
	
	/**
	 * Cherche tous les Mouvement possible pour le prochain joueur.
	 * @param zeJeu
	 */
	public void potentialMvt( Jeu zeJeu )
	{
	    // table rase
	    listeMvt.clear();
	    Joueur zeJoueur = zeJeu.getJoueur(zeJeu.getTour());
	    potentialCases( zeJeu.getPlateau() );
	    potentialPiece( zeJoueur );
	    
	    for( Iterator  iL = listePosL.iterator(), iC = listePosC.iterator();
	    iL.hasNext(); ) {
	        Integer posL = (Integer) iL.next();
	        Integer posC = (Integer) iC.next();
	        for( Iterator  i = listePiece.iterator(); i.hasNext(); ) {
	            Integer type = (Integer) i.next();
	            
	            listeMvt.add( new Mouvement( zeJoueur, type.intValue(), 
	                    					posL.intValue(), posC.intValue()));
	        }
	    }
	}
	public String displayListeMvt()
	{
	    StringBuffer strbuf = new StringBuffer();
        strbuf.append( "listeMvt = {");
        for( Iterator  i = listeMvt.iterator(); i.hasNext(); ) {
            Mouvement mvt = (Mouvement) i.next();
            strbuf.append( "(" + mvt.toString() + " ),\n" );
        }
        strbuf.append( "}");
        return strbuf.toString();  
	}
	/**
	 * Cherche toutes les positions possibles pour jouer en fonction d'Indiana
	 * et du plateau.
	 * @param zePlateau
	 */
	public void potentialCases( Plateau zePlateau )
	{
		// clear liste
		listePosL.clear();
		listePosC.clear();
		
		// sur une ligne
		int posL = zePlateau.getLineFromIndiana();
		if( posL >= 0 ) {
			if( !zePlateau.isLineFull(posL) ) {
				for( int lPosC=0; lPosC < Plateau.tailleC; lPosC++) {
					if( zePlateau.isCaseEmpty( posL, lPosC)) {
						listePosL.add( new Integer(posL));
						listePosC.add( new Integer(lPosC));
					}
				}
				return; // on etait sur une ligne
			}
			else {
				return; //pas de case possibles
			}
		}
		// alors sur une colonne
		int posC = zePlateau.getColFromIndiana();
		if( posC >= 0 ) {
			if( !zePlateau.isColFull(posC) ) {
				for( int lPosL=0; lPosL < Plateau.tailleL; lPosL++) {
					if( zePlateau.isCaseEmpty( lPosL, posC)) {
						listePosL.add( new Integer(lPosL));
						listePosC.add( new Integer(posC));
					}
				}
				return; // on etait sur une colonne
			}
			else {
				return; //pas de case possibles
			}
		} 
	}
	public String displayListePos() 
	{
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( "listePos = {");
        for( Iterator  iL = listePosL.iterator(), iC = listePosC.iterator();
        	 iL.hasNext(); ) {
            Integer posL = (Integer) iL.next();
            Integer posC = (Integer) iC.next();
            strbuf.append( "(" + posL.intValue()+", "+posC.intValue()+"), ");
        }
        strbuf.append( "}");
        return strbuf.toString();
	}
	/**
	 * Cherche toutes les pieces qui peuvent �tre joue� par un Player.
	 * @param zeJoueur
	 */
	public void potentialPiece( Joueur zeJoueur )
	{
	    // on repart de Zero
	    listePiece.clear();
	    
	    for( int indP=0; indP < Piece.nbType-1; indP++) {
	        if( zeJoueur.reserve[indP] > 0 ) {
	            listePiece.add( new Integer(indP));
	        }
	    }
	}
	public String displayListePiece() 
	{
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( "listePiece = {");
        for( Iterator  i = listePiece.iterator(); i.hasNext(); ) {
            Integer type = (Integer) i.next();
            strbuf.append( " "+ Piece.toString(type.intValue()) +",");
        }
        strbuf.append( "}");
        return strbuf.toString();
	}
}
