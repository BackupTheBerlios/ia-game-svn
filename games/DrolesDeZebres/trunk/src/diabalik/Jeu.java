/*
 * Created on Dec 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package diabalik;


import game.GameException;

import java.io.IOException;
import java.util.Observable;


/**
 * Composé d'un JPlateau, de deux Joueurs et d'un historique de coups.
 * On mémorise le dernier mouvement, ainsi que qui doit jouer.
 * Enfin, pour voir, on peut calculer le facteur de branchement.
 * @author dutech
 */
public class Jeu extends Observable
{

	private EtatJeu zeEtat;
	
	Historique zeHistorique;
    int indexEtat;
	
	static private PieceFactory zePieceFactory;
	static private JoueurFactory zeJoueurFactory;
    static public MoveFactory zeMoveFactory;
    static public MoveGenerator zeMoveGenerator;
    static public HeuristicState zeHeuristicState;
	
	/**
	 * Ca sera � 'rouge' de positionner Indiana.
	 */
	public Jeu()
	{
	    zePieceFactory = new PieceFactory();
		zeJoueurFactory = new JoueurFactory();
        zeMoveFactory = new MoveFactory(this);
        // TODO should belong to another class (ie Algorithm)
        zeMoveGenerator = new MoveGenerator();
        zeHeuristicState = new HeuristicState(this);
		
		zeEtat = new EtatJeu();
		zeHistorique = new Historique( this );
		
		initEtatJeu( zeEtat );
		
		zeHistorique.add( new EtatJeu(zeEtat) );
        setIndexState(0);
	}
//	public Jeu( Jeu zeJeu )
//	{
//	    zeEtat = new EtatJeu( zeJeu.zeEtat );
//	}
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
        for (Joueur player : etat.zeJoueurs) {
            etat.zeJoueurs[player.couleur].nbBloqueur = 8;
            etat.zeJoueurs[player.couleur].nbContact = 0;
        }
		
		Plateau board = etat.zePlateau;
		for(int col = 0; col < 7; col ++) {
			if( col != 3) {
				board.setCase( new PositionGrid2D(col,6), zePieceFactory.create( getJoueur(Joueur.rouge), Piece.coureur ));
				board.setCase( new PositionGrid2D(col,0), zePieceFactory.create( getJoueur(Joueur.jaune), Piece.coureur ));
			}
			else {
				board.setCase( new PositionGrid2D(col,6), zePieceFactory.create( getJoueur(Joueur.rouge), Piece.passeur ));
				board.setCase( new PositionGrid2D(col,0), zePieceFactory.create( getJoueur(Joueur.jaune), Piece.passeur ));
			}
		}
		etat.setTurn( Joueur.jaune);
		etat.setChanged();
	}
	
	/*
	 * Toutes les fonctions pour rendre transparents l'EtatJeu
	 */
	public EtatJeu getState()
	{
	    return zeEtat;
	}
	void setState( EtatJeu etat)
	{
	    zeEtat.copy(etat);
	}
    public void setIndexState( int index )
    {
        indexEtat = index;
        setState( getHistorique().getState(indexEtat));
    }
    public int getIndexState()
    {
        return indexEtat;
    }
	public Plateau getPlateau()
	{
	    return zeEtat.zePlateau;
	}
//	public void setPlateau( Plateau plat)
//	{
//	    zeEtat.zePlateau = plat;
//	}
	public Joueur[] getJoueurs()
	{
	    return zeEtat.zeJoueurs;
	}
//	public void setJoueurs( Joueur[] jou)
//	{
//	    zeEtat.zeJoueurs = jou;
//	}
	public Joueur getJoueur( int coul )
	{
	    return zeEtat.zeJoueurs[coul];
	}
//	public void setJoueur( int coul, Joueur jou)
//	{
//	    zeEtat.zeJoueurs[coul] = jou;
//	}
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
	public int getValue(EtatJeu etat, Joueur player)
	{
		if (etat.getWinner() == player.couleur ) {
			return 5;
		}
		else if( etat.getWinner() == Joueur.otherColor(player.couleur)) {
			return -5;
		}
		
		return 0;
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
		zeHistorique.readFromFile(fileName);
        setIndexState(0);
		//zeEtat = zeHistorique.getState(0);
	    //applyMoves( fileName, Integer.MAX_VALUE );
	}
	/**
	 * Applique un Mouvement à l'EtatJeu courant.
	 * @param mvt à appliquer
	 * @throws GameException en cas de Mouvement irrégulier
	 */
	public EtatJeu applyMove( Mouvement mvt)
	throws GameException, MoveException
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
	throws GameException, MoveException
	{
		//EtatJeu etatResult = new EtatJeu(etat);
        mvt.apply(etat);
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
	 * Prendre une Piece d'un Player pour la poser sur le Plateau
	 * @param joueur
	 * @param type de la Piece
	 * @param pos sur le Plateau
	 * @return true si c'est possible (case vide)
	 */
//	public boolean poserPiece( int joueur, int type, PositionGrid2D pos)
//	{
//			if( getPlateau().isCaseEmpty(pos)) {
//			    getPlateau().setCase(pos, zePieceFactory.create( getJoueur(joueur), type ));
//				return true;
//			}
//		return false;
//	}
    

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