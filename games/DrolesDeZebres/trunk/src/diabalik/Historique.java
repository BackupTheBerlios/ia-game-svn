package diabalik;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class Historique extends Observable
{
	Jeu zeJeu;
	ArrayList<EtatJeu> hist;
	
	public enum KindChanges {ALTER, ADD};
	
	public Historique( Jeu aGame )
	{
		zeJeu = aGame;
		hist = new ArrayList<EtatJeu>();
		
		setChanged();
		notifyObservers( new UpdateStatus( KindChanges.ALTER, 0, hist.size()-1) );
	}
	
	/**
	 * Ouvre un fichier et crée un Historique des mouvements joués.
	 * @param fileName 
	 * @throws IOException
	 */
	public void readFromFile( String fileName )
	throws IOException
	{
		FileReader myFile = new FileReader( fileName );
	    BufferedReader myReader = new BufferedReader( myFile );
	    
	    String lineRead = myReader.readLine();
	    Mouvement mvtRead = new Mouvement();
	    
	    // first EtatJeu in list
	    EtatJeu zeEtat = new EtatJeu();
	    zeJeu.initEtatJeu(zeEtat);
	    hist = new ArrayList<EtatJeu>();
	    hist.add( new EtatJeu( zeEtat ));
	    
	    int nbLu = 0;
	    while(lineRead != null) {
	        nbLu ++;
            System.out.println("Reading line "+ nbLu);
	        try {
                mvtRead = Jeu.zeMoveFactory.extractFrom(lineRead);
	            //mvtRead.extractFrom( lineRead, zeJeu);
                //System.out.println("applying "+ mvtRead.toString());
	            zeJeu.applyMove( mvtRead, zeEtat );
	        }
	        catch (Exception e) {
	            zeEtat.setValid( false );
	            zeEtat.errMsg = e.getMessage();
                //System.err.println(e.getMessage());
	        }
	        hist.add( new EtatJeu( zeEtat ));
	        //System.out.println(  displayStr() );//debug
	        lineRead = myReader.readLine();
	    }
	    myFile.close();
	    
	    setChanged();
		notifyObservers( new UpdateStatus( KindChanges.ALTER, 0, hist.size()-1) );
	}
	/**
	 * Ecrit l'Historique dans un fichier
	 * @param fileName
	 * @throws IOException
	 */
	public void writeToFile( String fileName )
	throws IOException
	{
		FileWriter myFile = new FileWriter( fileName );
	    BufferedWriter myWriter = new BufferedWriter( myFile );
	    
	    EtatJeu etat = null;
	    for( int index=0; index < hist.size(); index++ ) {
	        etat = (EtatJeu) hist.get( index );
	        myWriter.write( etat.getLastMove().toString());
	        myWriter.newLine();
	    }
	    myWriter.close();
	    myFile.close();
	}
	
    
	/**
	 * Alter Mouvement at currentPosition of the History.
	 * @param newMove
	 */
	public void alterMove( int position, Mouvement newMove )
	{
		// récupérer l'EtatJeu à modifier : c'est l'�tat pr�c�dent
	    EtatJeu zeEtat = new EtatJeu(hist.get( position-1 ));
	    // le rendre valide par défaut
	    //zeEtat.setValid( true );
	    //System.out.println( "Apply "+mvt.toString()+" to \n" + zeEtat.toString());
	    try {
	    	zeJeu.applyMove( newMove, zeEtat );
        }
        catch (Exception e) {
            zeEtat.setValid( false );
            zeEtat.errMsg = e.getMessage();
        }
        hist.set( position, new EtatJeu( zeEtat ));
        validate( position );
        
        setChanged();
		notifyObservers( new UpdateStatus( KindChanges.ALTER, position, hist.size()-1) );
	}
	/**
	 * Récupère l'état jeu à une position donnée.
	 * @param position
	 * @return
	 */
	public EtatJeu getState( int position)
	{
		return hist.get( position );
	}
	void validate( int position )
	{
		for( int i=position+1; i < hist.size(); i++ ) {
            Mouvement move = hist.get(i).getLastMove();
	        EtatJeu zeEtat = new EtatJeu(hist.get( i-1 ));
	        try {
	            zeJeu.applyMove( move, zeEtat );
	        }
	        catch (Exception e) {
	            zeEtat.setValid( false );
	            zeEtat.errMsg = e.getMessage();
	        }    
	        hist.set( i, new EtatJeu( zeEtat ));
        }
	}
	/**
	 * Insère un nouvel état à la fin de l'historique.
	 * @param aState
	 * @return position de l'élément inséré.
	 */
	public int add(EtatJeu aState)
	{
		hist.add(aState);
		
		setChanged();
		notifyObservers( new UpdateStatus( KindChanges.ADD, hist.size()-2, hist.size()-1) );
		
		return hist.size()-1;
	}
	/**
	 * Classical.
	 */
	public String toString()
	{
		StringBuffer tmpStr = new StringBuffer();
		for (EtatJeu etat : hist) {
			tmpStr.append(etat.toString());
		}
		return tmpStr.toString();
	}
	
	/**
	 * To indicate which kind of update is necessary.
	 * @author alain
	 */
	public class UpdateStatus {
		public KindChanges kind;
		public int start,end;
		public UpdateStatus(KindChanges kind, int start, int end)
		{
			this.kind = kind;
			this.start = start;
			this.end = end;
		}
	}
}
