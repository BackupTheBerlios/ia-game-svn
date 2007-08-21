/*
 * Created on Dec 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Composé d'un JPlateau et de deux Joueurs.
 * On mémorise le dernier mouvement, ainsi que qui doit jouer.
 * Enfin, pour voir, on peut calculer le facteur de branchement.
 * @author dutech
 */
public class Jeu {

	private EtatJeu zeEtat;
	
	static private PieceFactory zePieceFactory;
	static private JoueurFactory zeJoueurFactory;
	
	/**
	 * Ca sera à 'rouge' de positionner Indiana.
	 */
	public Jeu()
	{
	    zePieceFactory = new PieceFactory();
		zeJoueurFactory = new JoueurFactory();
		
		zeEtat = new EtatJeu();
		
		// ca il faut continuer à le faire ici
		setJoueurs( new Joueur[2] );
		setJoueur( Joueur.rouge, zeJoueurFactory.create( Joueur.rouge ));
		setJoueur( Joueur.vert,  zeJoueurFactory.create( Joueur.vert ));
	}
	public Jeu( Jeu zeJeu )
	{
	    zeEtat = new EtatJeu( zeJeu.zeEtat );
	}
	public boolean equals(Object obj) 
	{
	    if (obj instanceof Jeu) {
            Jeu game= (Jeu) obj;
            return zeEtat.equals( game.zeEtat );
	    }
	    return false;
	}
	
	/*
	 * Toutes les fonctions pour rendre transparents l'EtatJeu
	 */
	public EtatJeu getState()
	{
	    return zeEtat;
	}
	public void setState( EtatJeu etat)
	{
	    zeEtat = etat;
	}
	public Plateau getPlateau()
	{
	    return zeEtat.zePlateau;
	}
	public void setPlateau( Plateau plat)
	{
	    zeEtat.zePlateau = plat;
	}
	public Joueur[] getJoueurs()
	{
	    return zeEtat.zeJoueurs;
	}
	public void setJoueurs( Joueur[] jou)
	{
	    zeEtat.zeJoueurs = jou;
	}
	public Joueur getJoueur( int coul )
	{
	    return zeEtat.zeJoueurs[coul];
	}
	public void setJoueur( int coul, Joueur jou)
	{
	    zeEtat.zeJoueurs[coul] = jou;
	}
	public Mouvement getDernierMvt()
	{
	    return zeEtat.dernierMvt;
	}
	public void setDernierMvt( Mouvement mvt )
	{
	    zeEtat.dernierMvt = mvt;
	}
	public boolean getBonusDonne()
	{
	    return zeEtat.bonusDonne;
	}
	public void setBonusDonne( boolean flag )
	{
	    zeEtat.bonusDonne = flag;
	}
	public int getTour()
	{
	    return zeEtat.tour;
	}
	public void setTour( int coul )
	{
	    zeEtat.tour = coul;
	}
	public int[] getValidMoveIndiana()
	{
	    return zeEtat.validMoveIndiana;
	}
	public int getValidMoveIndiana( int indice )
	{
	    return zeEtat.validMoveIndiana[indice];
	}
	public void setValidMoveIndiana( int indice, int val )
	{
	    zeEtat.validMoveIndiana[indice] = val;
	}
	public boolean getFinJeu()
	{
	    return zeEtat.finJeu;
	}
	public void setFinJeu( boolean flag)
	{
	    zeEtat.finJeu = flag;
	}
	public int getNbPotential()
	{
	    return zeEtat.nbPotential;
	}
	public void setNbPotential( int nb )
	{
	    zeEtat.nbPotential = nb;
	}

	/**
	 * Ouvre un fichier et crée un historique des mouvements joués. 
	 * @param fileName
	 * @return ArrayList de EtatJeu
	 * @throws IOException
	 */
	public ArrayList loadHistory( String fileName )
	throws IOException
	{
	    FileReader myFile = new FileReader( fileName );
	    BufferedReader myReader = new BufferedReader( myFile );
	    
	    String lineRead = myReader.readLine();
	    Mouvement mvtRead = new Mouvement();
	    
	    zeEtat.reset();
	    ArrayList history = new ArrayList();
	    history.add( new EtatJeu( zeEtat ));
	    
	    int nbLu = 0;
	    while(lineRead != null) {
	        nbLu ++;
	        try {
	            mvtRead.extractFrom( lineRead, this);
	            applyMove( mvtRead );
	        }
	        catch (Exception e) {
	            zeEtat.valide = false;
	            zeEtat.errMsg = e.getMessage();
	        }
	        history.add( new EtatJeu( zeEtat ));
	        //System.out.println(  displayStr() );//debug
	        lineRead = myReader.readLine();
	    }
	    
	    myFile.close();
	    return history;
	}
	public void writeHistory( String fileName, ArrayList history )
	throws IOException
	{
	    FileWriter myFile = new FileWriter( fileName );
	    BufferedWriter myWriter = new BufferedWriter( myFile );
	    
	    EtatJeu etat = null;
	    for( int index=0; index < history.size(); index++ ) {
	        etat = (EtatJeu) history.get( index );
	        myWriter.write( etat.dernierMvt.toString());
	        myWriter.newLine();
	    }
	    myWriter.close();
	    myFile.close();
	}

	public ArrayList alterHistory( int index, Mouvement mvt, ArrayList history )
	{
	    // récupérer l'EtatJeu à modifier
	    EtatJeu etat = (EtatJeu) history.get( index-1 );
	    setState( new EtatJeu(etat) );
	    // le rendre valide par défaut
	    zeEtat.valide = true;
	    //System.out.println( "Apply "+mvt.toString()+" à \n" + zeEtat.toString());
	    try {
            applyMove( mvt );
        }
        catch (Exception e) {
            zeEtat.valide = false;
            zeEtat.errMsg = e.getMessage();
        }
        history.set( index, new EtatJeu( zeEtat ));
        
	    for( int i=index+1; i < history.size(); i++ ) {
	        etat = (EtatJeu) history.get( i );
	        try {
	            applyMove( etat.dernierMvt );
	        }
	        catch (Exception e) {
	            zeEtat.valide = false;
	            zeEtat.errMsg = e.getMessage();
	        }    
	        history.set( i, new EtatJeu( zeEtat ));
        }
	    return history;
	}
	
	/**
	 * Ouvre un fichier et applique au jeu tous les Mouvements lus dans
	 * le fichier. 
	 * @param fileName
	 * @throws GameException
	 * @throws IOException
	 */
	public void applyMoves( String fileName )
	throws GameException, IOException
	{
	    applyMoves( fileName, Integer.MAX_VALUE );
	}
	/**
	 * Ouvre un fichier et applique au jeu au maximum nbMove Mouvements lus dans
	 * le fichier. 
	 * @param fileName
	 * @param nbMoves Nombre de mouvement max appliqués
	 * @throws GameException
	 * @throws IOException
	 */
	public void applyMoves( String fileName, int nbMove )
	throws GameException, IOException
	{
	    FileReader myFile = new FileReader( fileName );
	    BufferedReader myReader = new BufferedReader( myFile );
	    
	    String lineRead = myReader.readLine();
	    Mouvement mvtRead = new Mouvement();
	    
	    int nbLu = 0;
	    while( (lineRead != null) && (nbLu < nbMove)) {
	        nbLu ++;
	        
	        mvtRead.extractFrom( lineRead, this);
	        applyMove( mvtRead );
	        //System.out.println(  displayStr() );//debug
	        lineRead = myReader.readLine();
	    }
	    
	    myFile.close();
	}
	/**
	 * Applique un Mouvement au jeu actuel.
	 * @param mvt à appliquer
	 * @throws GameException en cas de Mouvement irrégulier
	 */
	public void applyMove( Mouvement mvt )
	throws GameException
	{
	    if( mvt != null ) {
	        setDernierMvt( mvt );
	        if( getState().valide == false ) {
	            throw new GameException( "EtatJeu non valide");
	        }
	        if( mvt.zeJoueur == null ) {
	            throw new GameException( "Pas de Joueur : " + mvt.toString());
	        }
	        if( getTour() < 0 ) { 
	            setFirstJoueur( mvt.zeJoueur.couleur );
	        }
	        if( mvt.zeJoueur.couleur != getTour() ) {
	            throw new GameException( "Mauvais joueur : c'est au tour de " + Joueur.toString(getTour()) + " de jouer : " + mvt.toString());    
	        }
	        if( !poserPieceRegles(mvt.zeJoueur.couleur, mvt.zeType, mvt.posL, mvt.posC)) {
	            throw new GameException( "Pose de Piece irrégulière : " + mvt.toString());
	        }
	        for( int ind=1; ind < mvt.nbEchange; ind++ ) {
	            if( !switchCrocoGazelleRegles( mvt.echL[ind-1], mvt.echC[ind-1], mvt.echL[ind], mvt.echC[ind] )) {
	                throw new GameException( "Echange de Piece irrégulier : " + mvt.toString());
	            }
	        }
	        if( computeValidMoveIndiana() == false ) {
	            setFinJeu(true);
	        }
	        else {
	            if( !bougerIndianaRegles( mvt.nbIndiana) ) {
	                throw new GameException( "Mvt Impala irrégulier : "+mvt.toString());
	            }
	        }
	        computeBonusScore();
	        nextJoueur();
	    }
	}
	
	public void setFirstJoueur( int couleur )
	{
	    setTour( couleur );
	}
	/**
	 * Détermine le prochain joueur.
	 */
	public void nextJoueur()
	{
	    setTour(( getTour() + 1 ) % 2);
	}
	
	/**
	 * Calcule si on vient d'avoir un bonus, et les scores...
	 *
	 */
	public void computeBonusScore()
	{
		// attribution du bonus
		giveBonus( getTour() );
		
		// calcul de la valeur des regions
		getPlateau().computeValRegions();
		
		// et des score
		computeScore();
	}
	/**
	 * Un Joueur essaie de poser une Piece, et les règles sont appliquées.
	 * @param joueur indice du Joueur
	 * @param type de la Piece
	 * @param ligne du JPlateau
	 * @param col du JPlateau
	 * @return true si le mouvement est possible
	 */
	public boolean poserPieceRegles( int joueur, int type, int ligne, int col )
	{
	    // En fait on ne veut pas poser de piece
	    if( type < 0 ) return true;
	    // il faut qu'Indiana soit bien positionné
	    if( (ligne != getPlateau().getLineFromIndiana()) && 
	        (col != getPlateau().getColFromIndiana()) ) {
	        return false;
	    }
		// peut-on poser cette pièce là?
		boolean pose = poserPiece( joueur, type, ligne, col);
		if( pose == false ) {
		    return false;
		}
		
		// elle peut avoir un effet particulier
		switch(type) {
		case Piece.zebre :
		case Piece.gazelle :
			changeIfLionAround( ligne, col);
			break;
		case Piece.lion :
			changeAroundLion( ligne, col);
			break;
		case Piece.elephant :
		case Piece.crocodile :
		    //rien de spécial
		}		
		return true;
	}	
	/**
	 * Prendre une Piece de la reserve d'un Joueur pour la poser sur le JPlateau
	 * @param joueur
	 * @param type de la Piece
	 * @param ligne du JPlateau
	 * @param col du JPlateau
	 * @return true si c'est possible (case vide et piece disponible)
	 */
	public boolean poserPiece( int joueur, int type, int ligne, int col)
	{
		if( getJoueur(joueur).prendrePiece( type ) ) {
			if( getPlateau().isCaseEmpty( ligne, col)) {
			    getPlateau().cases[ligne][col] = zePieceFactory.create( getJoueur(joueur), type );
				return true;
			}
			else {
			    getJoueur(joueur).reprendrePiece( type );
			}
		}
		return false;
	}
	/**
	 * Prendre une Piece du JPlateau pour la remettre dans la reserve d'un
	 * Joueur.
	 * @param ligne du Platean
	 * @param col du JPlateau
	 * @return true si c'est possible
	 */
	public boolean enleverPiece( int ligne, int col)
	{
		if( !getPlateau().isCaseEmpty( ligne, col) ) {
			int tmpJ = getPlateau().cases[ligne][col].m_joueur.couleur;
			int type = getPlateau().cases[ligne][col].type;
			getJoueur(tmpJ).reprendrePiece( type );
			getPlateau().cases[ligne][col] = null;
			return true;
		}
		return false;
	}
	/**
	 * Essaye de retourner une Piece (zebre ou gazelle) sur le JPlateau.
	 * @param ligne du JPlateau
	 * @param col du JPlateau
	 * @return true si c'est possible
	 */
	public boolean flipPiece( int ligne, int col)
	{
		if( !getPlateau().isCaseEmpty( ligne, col) ) {
			Joueur tmpJ = getPlateau().cases[ligne][col].m_joueur;
			int type = getPlateau().cases[ligne][col].type;
			if( (type == Piece.zebre ) || (type == Piece.gazelle)) {
			    getPlateau().cases[ligne][col] = null;
			    getPlateau().cases[ligne][col] = zePieceFactory.create( tmpJ, Piece.flipped);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * On essaie de faire avancer Indiana en respectant les regles.
	 * @param nbCase dans le sens des aiguilles d'une montre
	 * @return true si le mvt est valide
	 */
	public boolean bougerIndianaRegles( int nbCase )
	{
	    if( nbCase > 0 ) {
	        if (getPlateau().posIndiana == -1 ) {
	            getPlateau().advanceIndiana( nbCase );
	            return true;
	        }
	        for( int i=0; i<4; i++) {
	            if( getValidMoveIndiana(i) == nbCase) {
	                // mvt valide
	                getPlateau().advanceIndiana( nbCase );
	                return true;
	            }
	        }
	    }
		return false;
	}
	/**
	 * Essaie de poser Indiana en respectant les règles.
	 * @param position
	 * @return true si la position est valide
	 */public boolean poserIndianaRegles( int position )
	{
		int oldPosition = getPlateau().posIndiana;
		getPlateau().setIndiana( position );
		if( !validIndiana() ) {
		    getPlateau().setIndiana( oldPosition );
			return false;
		}
		return true;
	}
	/**
	 * Cherche les positions valides pour Indiana.
	 * @return true si on peut trouver une position
	 * @return modifie validMoveIndiana !
	 */
	public boolean computeValidMoveIndiana()
	{
		int oldPosition = getPlateau().posIndiana;
		boolean existValid = false;
		int nbMove = 0;
		for( int i=0; i<4; i++) setValidMoveIndiana(i, -1);
		// trois positions suivantes
		for( nbMove=0; nbMove<3; nbMove++) {
		    getPlateau().advanceIndiana(1);
			if( validIndiana()) {
				existValid = true;
				setValidMoveIndiana(nbMove, nbMove+1);
			}
		}
		if( existValid == false ) { //cherche plus loin
			while( (existValid == false) && (nbMove <= Plateau.maxIndiana)) {
				nbMove++;
				getPlateau().advanceIndiana(1);
				if( validIndiana()) {
					existValid = true;
					setValidMoveIndiana(3, nbMove);
					getPlateau().setIndiana( oldPosition );
					return true;
				}
			}
		}
		else {
		    getPlateau().setIndiana( oldPosition );
			return true;
		}
		getPlateau().setIndiana( oldPosition );
		return false;
	}
	/**
	 * La position d'Indiana est-elle valide.
	 * @return false si ligne/colonne pleine OU ni sur ligne ni sur colonne.
	 */
	public boolean validIndiana()
	{
		// sur une ligne
		int posL = getPlateau().getLineFromIndiana();
		if( posL >= 0 ) { // bien sur une ligne
			if( getPlateau().isLineFull( posL )) {
				return false;
			}
			else {
				return true;
			}
		}
		// sur une colonne
		int posC = getPlateau().getColFromIndiana();
		if( posC >= 0 ) { // bien sur une colonne
			if( getPlateau().isColFull( posC )) {
				return false;
			}
			else {
				return true;
			}
		}
		// bien embetté car ni sur une ligne ni sur une colonne
		return false;
	}
	
	/**
	 * Regarde autour d'une case et applique les transformations dues au lion.
	 * @param ligne du JPlateau
	 * @param col du JPlateau
	 */
	public void changeAroundLion( int ligne, int col)
	{
		Piece zePiece;
		zePiece = getPlateau().getCaseUp(ligne, col);
		if( zePiece != null ) changedByLion( zePiece, ligne-1, col );
		zePiece = getPlateau().getCaseDown(ligne, col);
		if( zePiece != null ) changedByLion( zePiece, ligne+1, col );
		zePiece = getPlateau().getCaseLeft(ligne, col);
		if( zePiece != null ) changedByLion( zePiece, ligne, col-1 );
		zePiece = getPlateau().getCaseRight(ligne, col);
		if( zePiece != null ) changedByLion( zePiece, ligne, col+1 );
	}
	/**
	 * zebre->flipped, gazelle->enlevée.
	 */
	public void changedByLion( Piece zePiece, int ligne, int col)
	{
		switch( zePiece.type) {
		case Piece.zebre:
			flipPiece( ligne, col);
			break;
		case Piece.gazelle:
			enleverPiece( ligne, col );
			break;
		}
	}
	/**
	 * Si un lion est à côté, zebre ou gazelle sont flippés.
	 * @param ligne du JPlateau
	 * @param col du JPlateau
	 */
	public void changeIfLionAround( int ligne, int col)
	{
		Piece zePiece = getPlateau().cases[ligne][col];
		if( zePiece == null ) return;
		if( isLionAround(ligne, col) )
			flipPiece( ligne, col );
	}
	/**
	 * Regarde si il y a un lion autour d'une certaine position.
	 * @param ligne du JPlateau
	 * @param col du JPlateau
	 * @return true si un lion est à côté
	 */
	public boolean isLionAround( int ligne, int col)
	{
		Piece zePiece;
		zePiece = getPlateau().getCaseUp(ligne, col);
		if( zePiece != null ) {
			if( zePiece.type == Piece.lion ) return true;
		}
		zePiece = getPlateau().getCaseDown(ligne, col);
		if( zePiece != null ) {
			if( zePiece.type == Piece.lion ) return true;
		}
		zePiece = getPlateau().getCaseLeft(ligne, col);
		if( zePiece != null ) {
			if( zePiece.type == Piece.lion ) return true;
		}
		zePiece = getPlateau().getCaseRight(ligne, col);
		if( zePiece != null ) {
			if( zePiece.type == Piece.lion ) return true;
		}
		return false;
	}
	
	/**
	 * Cherche toutes les cases voisines qui sont des gazelles et 
	 * derrière une rivière.
	 * @param ligne du Plateau
	 * @param col du Plateau
	 * @return Tableau[4][2] avec des -1 ou des coordonnées LxC de cases
	 */
	public int[][] possibleSwitchAroundCroco( int ligne, int col)
	{
	    int[][] pos = {{-1,-1}, {-1,-1}, {-1,-1}, {-1,-1}};
	    
	    int currentRegion = Plateau.indRegion[ligne][col];
	    Piece voisin = null;
	    int indice = 0;
	    // pour chaque direction
	    voisin = getPlateau().getCaseUp( ligne, col );
	    if( voisin != null ) {
	        if ((voisin.type == Piece.gazelle) &&
	            (Plateau.indRegion[ligne-1][col] != currentRegion )) {
                pos[indice][0] = ligne-1;
                pos[indice][1] = col;
                indice++;
            }
	    }
	    voisin = getPlateau().getCaseDown( ligne, col );
	    if( voisin != null ) {
	        if ((voisin.type == Piece.gazelle) &&
		            (Plateau.indRegion[ligne+1][col] != currentRegion )) {
                pos[indice][0] = ligne+1;
                pos[indice][1] = col;
                indice++;
            }
	    }
	    voisin = getPlateau().getCaseLeft( ligne, col );
	    if( voisin != null ) {
	        if ((voisin.type == Piece.gazelle) &&
		            (Plateau.indRegion[ligne][col-1] != currentRegion )) {
                pos[indice][0] = ligne;
                pos[indice][1] = col-1;
                indice++;
            }
	    }
	    voisin = getPlateau().getCaseRight( ligne, col );
	    if( voisin != null ) {
	        if ((voisin.type == Piece.gazelle) &&
		            (Plateau.indRegion[ligne][col+1] != currentRegion )) {
                pos[indice][0] = ligne;
                pos[indice][1] = col+1;
                indice++;
            }
	    }
	    
	    return pos;
	}
	/**
	 * Echange un Croco et une Gazelle.
	 * En fonction de la présence de lion, la gazelle peut se retrouver flippée.
	 * @param crocoL
	 * @param crocoC
	 * @param gazL
	 * @param gazC
	 * @return true si on a réussi
	 */
	public boolean switchCrocoGazelleRegles( int crocoL, int crocoC, int gazL, int gazC ) 
	{
	    Piece croco = getPlateau().cases[crocoL][crocoC];
	    if( croco == null ) return false;
	    if( (gazL < 0) || (gazC < 0)) {
	        System.out.println("Out of Bound");
	    }
	    Piece gaz = getPlateau().cases[gazL][gazC];
	    if( gaz == null ) return false;
	    
	    if( (croco.type == Piece.crocodile) && (gaz.type == Piece.gazelle)) {
	        getPlateau().switchPieces( crocoL, crocoC, gazL, gazC);
	        // doit-on flipper la gazelle
	        if( isLionAround( crocoL, crocoC)) {
	            flipPiece( crocoL, crocoC );
	        }
	        return true;
	    }
	    return false;
	}
	/**
	 * Si le joueur vient de terminer une région, il a un bonus de 5.
	 * @param p_joueur qui vient de jouer.
	 * @return true si bonus accordé
	 */
	public boolean giveBonus( int p_joueur)
	{
		if( getBonusDonne() != true ) {
			if( getPlateau().isAnyOneRegionFull() ) {
			    setBonusDonne( true );
			    getJoueur(p_joueur).bonus = 5;
				return true;
			}
		}
		return false;
	}
	/**
	 * On calcule le score actuel des deux joueurs.
	 */
	public void computeScore()
	{
	    for( int ind=0; ind<2; ind++)
	    {
	        getJoueur(ind).score = getJoueur(ind).bonus;
	    }
	    for( int indR = 0; indR < Plateau.nbRegion; indR++) {
	        if( getPlateau().valRegions[indR][0] >= 0 ) {
	            // un des joueurs est majoritaire
	            getJoueur(getPlateau().valRegions[indR][0]).score += getPlateau().valRegions[indR][1];
	        }
	    }
	}
	
	public String displayStr()
	{
		return zeEtat.toString();
	}

	public Joueur getJoueur( String token )
	{
	    int couleur = Joueur.decode( token );
	    if( (couleur >= 0) && (couleur < getJoueurs().length)) return getJoueur(couleur);
	    return null;
	}

	/**
	 * Evalue la position actuelle du point du Joueur.
	 * @return
	 */
	public int getValue( Joueur p_joueur )
	{
	    computeScore();
	    int positive = getJoueur( p_joueur.couleur ).score;
	    int negative = getJoueur( 1 - p_joueur.couleur ).score;
	    
	    return positive - negative;
	}
}
/**
 * Cree des Pieces pour Drole de Zebre.
 */
class PieceFactory {
	
	public Piece create( Joueur p_joueur, int p_type )
	{
		Piece myPiece = new Piece();
		myPiece.m_joueur = p_joueur;
		myPiece.type = p_type;
		switch( p_type ) {
		case Piece.zebre: 
			myPiece.val = 6;
			myPiece.setDisplayStr();	
			break;
		case Piece.gazelle: 
			myPiece.val = 2;
			myPiece.setDisplayStr();
			break;
		case Piece.elephant: 
			myPiece.val = 5;
			myPiece.setDisplayStr();
			break;
		case Piece.lion: 
			myPiece.val = 1;
			myPiece.setDisplayStr();
			break;
		case Piece.crocodile: 
			myPiece.val = 0;
			myPiece.setDisplayStr();
			break;
		case Piece.flipped: 
			myPiece.val = 0;
			myPiece.setDisplayStr();
			break;
		}
		return myPiece;
	}

}
/**
 * Cree des Joueurs pour Drole de Zebre
 */
class JoueurFactory {
	
	public Joueur create( int p_couleur )
	{
		Joueur myJoueur = new Joueur();
		initReserve( myJoueur );
		myJoueur.bonus = 0;
		myJoueur.score = 0;
		myJoueur.couleur = p_couleur;
		
		return myJoueur;
	}
	
	private void initReserve( Joueur p_joueur )
	{
		p_joueur.reserve = new int[Piece.nbType-1];
		p_joueur.reserve[Piece.zebre] = 5;
		p_joueur.reserve[Piece.gazelle] = 6;
		p_joueur.reserve[Piece.elephant] = 1;
		p_joueur.reserve[Piece.lion] = 1;
		p_joueur.reserve[Piece.crocodile] = 2;
	}

}