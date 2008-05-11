/*
 * Created on Dec 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;


import game.GameException;

import java.io.IOException;


/**
 * Composé d'un JPlateau, de deux Joueurs et d'un historique de coups.
 * On mémorise le dernier mouvement, ainsi que qui doit jouer.
 * Enfin, pour voir, on peut calculer le facteur de branchement.
 * @author dutech
 */
public class Jeu {

	private EtatJeu zeEtat;
	
	Historique zeHistorique;
	
	static private PieceFactory zePieceFactory;
	static private JoueurFactory zeJoueurFactory;
	
	/**
	 * Ca sera � 'rouge' de positionner Indiana.
	 */
	public Jeu()
	{
	    zePieceFactory = new PieceFactory();
		zeJoueurFactory = new JoueurFactory();
		
		zeEtat = new EtatJeu();
		zeHistorique = new Historique( this );
		
		initEtatJeu( zeEtat );
		
		zeHistorique.add( new EtatJeu(zeEtat) );
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
	
	public Historique getHistorique()
	{
		return zeHistorique;
	}
	
	void initEtatJeu(EtatJeu etat)
	{
		etat.zeJoueurs = new Joueur[2];
		etat.zeJoueurs[Joueur.jaune] = zeJoueurFactory.create( Joueur.jaune );
		etat.zeJoueurs[Joueur.rouge] = zeJoueurFactory.create( Joueur.rouge );
		
		Plateau board = etat.zePlateau;
		for(int col = 0; col < 7; col ++) {
			if( col != 3) {
				board.setCase( new PositionGrid2D(col,6), zePieceFactory.create( getJoueur(Joueur.jaune), Piece.coureur ));
				board.setCase( new PositionGrid2D(col,0), zePieceFactory.create( getJoueur(Joueur.rouge), Piece.coureur ));
			}
			else {
				board.setCase( new PositionGrid2D(col,6), zePieceFactory.create( getJoueur(Joueur.jaune), Piece.passeur ));
				board.setCase( new PositionGrid2D(col,0), zePieceFactory.create( getJoueur(Joueur.rouge), Piece.passeur ));
			}
		}
		etat.setTurn( Joueur.rouge);
		etat.setChanged();
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
//	public int getTour()
//	{
//	    return zeEtat.tour;
//	}
//	public void setTour( int coul )
//	{
//	    zeEtat.tour = coul;
//	}
//	public boolean getFinJeu()
//	{
//	    return zeEtat.finJeu;
//	}
//	public void setFinJeu( boolean flag)
//	{
//	    zeEtat.finJeu = flag;
//	}

	
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
		zeHistorique.readFromFile(fileName);
		zeEtat = zeHistorique.getState(0);
	    //applyMoves( fileName, Integer.MAX_VALUE );
	}
	/**
	 * Applique un Mouvement à l'EtatJeu courant.
	 * @param mvt à appliquer
	 * @throws GameException en cas de Mouvement irrégulier
	 */
	public EtatJeu applyMove( Mouvement mvt)
	throws GameException
	{
		applyMove( mvt, zeEtat );
		zeHistorique.add( new EtatJeu( zeEtat ));
		return zeEtat;
	}
	/**
	 * Applique un Mouvement à un EtatJeu.
	 * ATTENTION : l'EtatJeu est modifié.
	 * TODO : faire qu'on applique un Coup/Mouvement à un EtatJeu, avec potentiellement
	 *        un skelette de coup qui soit déterminé par le jeu (comme ds Shazamm ???)
	 * @param mvt à appliquer
	 * @param etat du jeu où le mouvement est appliqué
	 * @throws GameException en cas de Mouvement irrégulier
	 */
	public void applyMove( Mouvement mvt, EtatJeu etat )
	throws GameException
	{
		//EtatJeu etatResult = new EtatJeu(etat);
		
	    if( mvt != null ) {
	        etat.setLastMove( mvt );
	        if( etat.isValid() == false ) {
	            throw new GameException( "EtatJeu non valide");
	        }
	        if( mvt.zeJoueur == null ) {
	            throw new GameException( "Pas de Player : " + mvt.toString());
	        }
	        if( etat.getTurn() < 0 ) {
	        	// set first joueur
	        	etat.setTurn( mvt.zeJoueur.couleur );
	        }
	        if( mvt.zeJoueur.couleur != etat.getTurn() ) {
	            throw new GameException( "Mauvais joueur : c'est au tour de " + Joueur.toString(etat.getTurn()) + " de jouer : " + mvt.toString());    
	        }
	        
	        // deplacer ?
	        if( mvt.zeType == Mouvement.depl ) {
	        	try {
	        		//etat = deplacer( etat, mvt.zeJoueur.couleur, mvt.posDebut, mvt.posFin);
	        		deplacer( etat, mvt.zeJoueur.couleur, mvt.posDebut, mvt.posFin);
	        	}
	        	catch( MoveException me ) {
	        		System.err.println("DEPLACER : " + me.getMessage());
	        		throw new GameException( "Déplacement irrégulier : " + mvt.toString());
	        	}
	        	etat.setNbMvtLeft(etat.getNbMvtLeft()-1);
	        }
	        if( mvt.zeType == Mouvement.pass ) {
	        	try {
	        		//etat = fairePasse( etat, mvt.zeJoueur.couleur, mvt.posDebut, mvt.posFin);
	        		fairePasse( etat, mvt.zeJoueur.couleur, mvt.posDebut, mvt.posFin);
	        	}
	        	catch( MoveException me ) {
	        		System.err.println("PASSER : " + me.getMessage());
	        		throw new GameException( "Passe irrégulière : " + mvt.toString());
	        	}
	        	etat.setNbMvtLeft(etat.getNbMvtLeft()-1);;
	        }
	        if( mvt.zeType == Mouvement.none ) {
	        	etat.setNbMvtLeft(0);
	        }
	        if( etat.getNbMvtLeft() == 0 ) {
	        	etat.setNbMvtLeft(3);
	        	etat.setTurn( (etat.getTurn() + 1) % 2 );
	        }
	    }
	    //return etat;
	}
	
//	public void setFirstJoueur( int couleur )
//	{
//	    setTour( couleur );
//	}
//	/**
//	 * D�termine le prochain joueur.
//	 */
//	public void nextJoueur()
//	{
//	    setTour(( getTour() + 1 ) % 2);
//	}
	
	/**
	 * Deplace un joueur qui n'a pas la balle.
	 * ATTENTION : l'EtatJeu est modifié.
	 * TODO : faire qu'on applique un Coup/Mouvement à un EtatJeu, avec potentiellement
	 *        un skelette de coup qui soit déterminé par le jeu (comme ds Shazamm ???)
	 * @param etat EtatJeu sur qui on essaie de déplacer un joueur
	 * @param joueur
	 * @param origin Case d'origine du 'coureur'
	 * @param wish Case d'arrivée du 'coureur'
	 * @return EtatJeu le nouvel EtatJeu
	 * @throws MoveException 
	 */
	public void deplacer( EtatJeu etat, int joueur, PositionGrid2D origin, PositionGrid2D wish)
		throws MoveException
	{
		//EtatJeu etat = new EtatJeu( etat );
		Joueur player = etat.zeJoueurs[joueur];
		
		// il existe un pion coureur dans la case de départ
		Piece pion = etat.zePlateau.getCase(origin);
		if( pion == null ) {
			throw new MoveException("Pas de pion à déplacer");
		}
		if( pion.m_joueur.sameColor(player) == false ) {
			throw new MoveException("Pion pas de la bonne couleur");
		}
		if( pion.type != Piece.coureur) {
			throw new MoveException("Le pion n'est pas un coureur");
		}
		
		// case d'arrivée vide
		if( etat.zePlateau.isCaseEmpty(wish) == false ) {
			throw new MoveException("Case d'arrivée déjà occupée");
		}
		
		// bouge pièce
		etat.zePlateau.setCase(origin, null);
		etat.zePlateau.setCase(wish, pion);
		//return etat;
	}
	/**
	 * ATTENTION : l'EtatJeu est modifié.
	 * TODO : faire qu'on applique un Coup/Mouvement à un EtatJeu, avec potentiellement
	 *        un skelette de coup qui soit déterminé par le jeu (comme ds Shazamm ???)
	 * @param etat
	 * @param joueur
	 * @param origin
	 * @param wish
	 * @return
	 * @throws MoveException
	 */
	public void fairePasse( EtatJeu etat, int joueur, PositionGrid2D origin, PositionGrid2D wish )
		throws MoveException
	{
		//EtatJeu etat = new EtatJeu( etat );
		Joueur player = etat.zeJoueurs[joueur];
		
		// il existe un pion passeur dans la case de départ
		Piece pionStart = etat.zePlateau.getCase(origin);
		if( pionStart == null ) {
			throw new MoveException("Pas de pion pour passer");
		}
		if( pionStart.m_joueur.sameColor(player) == false ) {
			throw new MoveException("Pion de départ n'est pas de la bonne couleur");
		}
		if( pionStart.type != Piece.passeur) {
			throw new MoveException("Le pion de départ n'est pas un passeur");
		}
		
		// il existe un pion coureur dans la case d'arriv�e
		Piece pionEnd = etat.zePlateau.getCase(wish);
		if( pionEnd == null ) {
			throw new MoveException("Pas de pion pour réceptionner");
		}
		if( pionEnd.m_joueur.sameColor(player) == false ) {
			throw new MoveException("Pion d'arrivée n'est pas de la bonne couleur");
		}
		if( pionEnd.type != Piece.coureur) {
			throw new MoveException("Le pion d'arrivée n'est pas un coureur");
		}
		
		//pionStart.type = Piece.coureur;
		etat.zePlateau.setCase(origin, pionEnd);
		//pionEnd.type = Piece.passeur;
		etat.zePlateau.setCase(wish, pionStart);
		//return etat;
	}
	
	/**
	 * Prendre une Piece d'un Player pour la poser sur le Plateau
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