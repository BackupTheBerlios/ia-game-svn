/*
 * Created on Oct 24, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import jeu.shazamm.parser.BAJParser;
import jeu.shazamm.parser.ParseException;

/**
 * Un HistoriqueCoup est une liste de Coup ET le noms des joueurs.
 * On peut le lire à partir de fichier, ou même de BaJ.
 * On peut le sauver dans le format d'Alain. 
 * @author dutech
 */
public class HistoriqueCoup {

    	public ArrayList listeCoups;
    	public String nomJoueurVert;
    	public String nomJoueurRouge;
    	
    	/**
    	 * Crée un historique vide.
    	 */
    	public HistoriqueCoup()
    	{
    	    listeCoups = new ArrayList();
    	}
    	/**
    	 * Crée une copie (superficielle) de l'historique.
    	 * C'est compliqué de cloner des Coups.
    	 */
    	public HistoriqueCoup( HistoriqueCoup p_hist )
    	{
    	    copy(p_hist);
    	}
    	/**
    	 * Crée une copie (superficielle) de l'historique.
    	 * C'est compliqué de cloner des Coups.
    	 */
    	public void copy( HistoriqueCoup p_hist )
    	{
    	    nomJoueurRouge = p_hist.nomJoueurRouge;
    	    nomJoueurVert = p_hist.nomJoueurVert;
    	    listeCoups.clear();
    	    for (Iterator iterC = p_hist.listeCoups.iterator(); iterC.hasNext();) {
                Coup element = (Coup) iterC.next();
                add( element );
            }
    	}
    	/**
    	 * Ajoute un coup à l'historique.
    	 * @param p_coup
    	 */
    	public void add( Coup p_coup )
    	{
    	    listeCoups.add( p_coup );
    	}
    	/**
    	 * Nettoie l'historique.
    	 */
    	public void clear()
    	{
    	    nomJoueurRouge = null;
    	    nomJoueurVert = null;
    	    listeCoups.clear();
    	}
    	/**
    	 * Lit une partie sauvée dans un fichier texte.
    	 * @param fileName Nom du fichier
    	 * @throws FileNotFoundException
    	 * @throws IOException
    	 */
    	public void readFromASCIIFile( String fileName )
    	throws FileNotFoundException,
    	       IOException
    	{
    	    FileReader myFile = new FileReader( fileName );
    	    BufferedReader myReader = new BufferedReader( myFile );
    	    
    	    nomJoueurRouge = myReader.readLine();
    	    nomJoueurVert = myReader.readLine();
    	    
    	    String lineRead = myReader.readLine();
    	    
    	    CoupFactory coupReader = new CoupFactory();
    	    Coup coupRead = null;
    	    
    	    clear();
    	    
    	    int nbLu = 0;
    	    while(lineRead != null) {
    	        nbLu ++;
    	        coupRead = coupReader.extractFrom( lineRead );
    	        add( coupRead );
   
    	        lineRead = myReader.readLine();
    	    }
    	    
    	    myReader.close();
    	    myFile.close();
    	}
    	/**
    	 * Utilise un fichier HTML récupéré sur BAJ.
    	 * @param fileName
    	 * @throws FileNotFoundException
    	 * @throws ParseException
    	 */
    	public void readFromBaJFile( String fileName )
    	throws FileNotFoundException, ParseException
    	{
    	    FileReader myFile = new FileReader( fileName );
    	    BAJParser parser = new BAJParser( myFile );
    	    parser.Input();

    	    copy( parser.historique );
    	}
    	/**
    	 * Utilise une connexion vers une page de BAJ.
    	 * @param stream
    	 * @throws ParseException
    	 */
    	public void readFromBajWeb( InputStream stream )
    	throws ParseException
    	{
    	    BAJParser parser = new BAJParser( stream );
    	    parser.Input();
    	    
    	    copy( parser.historique );
    	}
    	
    	/**
    	 * Sauve l'historique dans un fichier lisible.
    	 */
    	public void writeToASCIFile( String fileName )
    	throws IOException
    	{
    	    FileWriter myFile = new FileWriter( fileName );
    	    BufferedWriter myWriter = new BufferedWriter( myFile );
    	    
    	    myWriter.write( nomJoueurRouge );
    	    myWriter.newLine();
    	    myWriter.write( nomJoueurVert );
    	    myWriter.newLine();
    	    
    	    for( Iterator iter = listeCoups.iterator(); iter.hasNext();) {
    	        Coup element = (Coup) iter.next();
    	        myWriter.write( element.toString());
    	        myWriter.newLine();
    	    }
    	    myWriter.close();
    	    myFile.close();
    	}
    	
    	/**
    	 * Format:<br>
    	 *  [...] Coup.toString() + "\n" <br>
    	 */
    	public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append( "ROUGE = "+nomJoueurRouge+"\n");
            strbuf.append( "VERT = "+nomJoueurVert+"\n");
            for (Iterator iter = listeCoups.iterator(); iter.hasNext();) {
                Coup element = (Coup) iter.next();
                strbuf.append( element.toString() + "\n");
            }
            return strbuf.toString();
        }
}
