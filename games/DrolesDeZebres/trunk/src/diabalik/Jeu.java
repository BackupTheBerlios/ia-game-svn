/*
 * Created on Dec 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import drolesDZ.GameException;

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
		setJoueur( Joueur.jaune, zeJoueurFactory.create( Joueur.jaune ));
		setJoueur( Joueur.rouge,  zeJoueurFactory.create( Joueur.rouge ));
		
		initPlateau();
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
	
	void initPlateau()
	{
		Plateau board = getPlateau();
		for(int col = 0; col < 7; col ++) {
			if( col != 3) {
				board.cases[0][col] = zePieceFactory.create( getJoueur(Joueur.jaune), Piece.coureur );
				board.cases[6][col] = zePieceFactory.create( getJoueur(Joueur.rouge), Piece.coureur );
			}
			else {
				board.cases[0][col] = zePieceFactory.create( getJoueur(Joueur.jaune), Piece.passeur );
				board.cases[6][col] = zePieceFactory.create( getJoueur(Joueur.rouge), Piece.passeur );
			}
		}
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
	public int getTour()
	{
	    return zeEtat.tour;
	}
	public void setTour( int coul )
	{
	    zeEtat.tour = coul;
	}
	public boolean getFinJeu()
	{
	    return zeEtat.finJeu;
	}
	public void setFinJeu( boolean flag)
	{
	    zeEtat.finJeu = flag;
	}

	/**
	 * Ouvre un fichier et crée un historique des mouvements joués. 
	 * @param fileName
	 * @return ArrayList de EtatJeu
	 * @throws IOException
	 */
	public ArrayList<EtatJeu> loadHistory( String fileName )
	throws IOException
	{
	    FileReader myFile = new FileReader( fileName );
	    BufferedReader myReader = new BufferedReader( myFile );
	    
	    String lineRead = myReader.readLine();
	    Mouvement mvtRead = new Mouvement();
	    
	    zeEtat.reset();
	    ArrayList<EtatJeu> history = new ArrayList<EtatJeu>();
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
	public void writeHistory( String fileName, ArrayList<EtatJeu> history )
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

	public ArrayList<EtatJeu> alterHistory( int index, Mouvement mvt, ArrayList<EtatJeu> history )
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
	        
	        // deplacer ?
	        if( mvt.zeType == Mouvement.depl ) {
	        	if( !deplacer(mvt.zeJoueur.couleur, mvt.posDebut, mvt.posFin)) {
	        		throw new GameException( "Déplacement irrégulier : " + mvt.toString());
	        	}
	        }
	        if( mvt.zeType == Mouvement.pass ) {
	        	if( !fairePasse(mvt.zeJoueur.couleur, mvt.posDebut, mvt.posFin)) {
	        		throw new GameException( "Passe irrégulière : " + mvt.toString());
	        	}
	        }
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
	 * Deplace un joueur qui n'a pas la balle.
	 * @param joueur
	 * @param origin
	 * @param wish
	 * @return
	 */
	public boolean deplacer( int joueur, PositionGrid2D origin, PositionGrid2D wish)
	{
		Joueur player = getJoueur(joueur);
		
		// il existe un pion coureur dans la case de départ
		Piece pion = getPlateau().getCase(origin);
		if( pion == null ) return false;
		if( pion.m_joueur.sameColor(player) == false ) return false;
		if( pion.type != Piece.coureur) return false;
		
		// case d'arrivée vide
		if( getPlateau().isCaseEmpty(wish) == false ) return false;
		
		// bouge pièce
		getPlateau().setCase(origin, null);
		getPlateau().setCase(wish, pion);
		return true;
	}
	public boolean fairePasse( int joueur, PositionGrid2D origin, PositionGrid2D wish )
	{
		Joueur player = getJoueur(joueur);
		
		// il existe un pion passeur dans la case de départ
		Piece pionStart = getPlateau().getCase(origin);
		if( pionStart == null ) return false;
		if( pionStart.m_joueur.sameColor(player) == false ) return false;
		if( pionStart.type != Piece.passeur) return false;
		
		// il existe un pion coureur dans la case d'arrivée
		Piece pionEnd = getPlateau().getCase(origin);
		if( pionEnd == null ) return false;
		if( pionEnd.m_joueur.sameColor(player) == false ) return false;
		if( pionEnd.type != Piece.coureur) return false;
		
		pionStart.type = Piece.coureur;
		pionEnd.type = Piece.passeur;
		return true;
	}
	
	/**
	 * Prendre une Piece d'un Joueur pour la poser sur le Plateau
	 * @param joueur
	 * @param type de la Piece
	 * @param pos sur le Plateau
	 * @return true si c'est possible (case vide)
	 */
	public boolean poserPiece( int joueur, int type, PositionGrid2D pos)
	{
			if( getPlateau().isCaseEmpty(pos)) {
			    getPlateau().setCase(pos, zePieceFactory.create( getJoueur(joueur), type ));
				return true;
			}
		return false;
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

}
/**
 * Cree des Pieces pour Diabalik.
 */
class PieceFactory {
	
	public Piece create( Joueur p_joueur, int p_type )
	{
		Piece myPiece = new Piece();
		myPiece.m_joueur = p_joueur;
		myPiece.type = p_type;
		myPiece.setDisplayStr();
		
		return myPiece;
	}

}
/**
 * Cree des Joueurs pour Diabalik
 */
class JoueurFactory {
	
	public Joueur create( int p_couleur )
	{
		Joueur myJoueur = new Joueur();
		myJoueur.couleur = p_couleur;
		
		return myJoueur;
	}
}