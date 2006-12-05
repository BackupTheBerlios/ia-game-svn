/*
 * Created on Dec 5, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class GameDBManager {
    
    public File rootDB; // la racine de l'arborescence
    String gameFilePattern = "game_[0-9]*_.*_vs_.*\\.sha";
    String gameRepPattern = "game_[0-9]*_[0-9]*";
    int gameFileNbStart = 5;
    int gameFileNbEnd = 11;
    
    /**
     * Cr�e la "base de donn�es de parties" dont la racine est � l'adresse
     * absolue 'nameRootDB'.
     */
    public GameDBManager( String nameRootDB )
    throws GameDBException
    {
        rootDB = new File( nameRootDB);
        if( rootDB.isDirectory() != true ) {
            rootDB = null;
            throw new GameDBException( ">"+nameRootDB+"< n'est pas un r�pertoire valide.");
        }  
    }
    
    /**
     * Cherche dans la liste des fichiers de partie les parties
     * incluant le(s) joueur(s) sp�cifi�(s).
     * @param player1
     * @param player2 peut �tre null
     * @param numMin peut �tre null
     * @param numMax peut �tre null
     * @return une List de File qui sont les parties recherch�es.
     */
    public List searchGame( String player1, String player2, int numMin, int numMax)
    {
        
        return searchGameRecu( rootDB, toLinuxName(player1), toLinuxName(player2),
                               numMin, numMax);
    }
    /**
     * Cherche de mani�re r�cursive.
     */
    protected List searchGameRecu( File path, String linuxName1, String linuxName2,
                                   int numMin, int numMax )
    {
        ArrayList result = new ArrayList();
        
        //System.out.println("On cherche dans "+path.getAbsolutePath());
        // d'abord toutes les parties valides
        File[] jeux = path.listFiles( new GameNameFilter() );
        for (int i = 0; i < jeux.length; i++) {
            //System.out.println( jeux[i].getName());
            if( checkName( jeux[i], linuxName1, linuxName2, numMin, numMax)) {
                //System.out.println("---> OK");
                result.add(jeux[i]);
            }
        }
        
        // tous les sous r�pertoires
        File[] rep = path.listFiles( new GameRepFilter() );
        for (int i = 0; i < rep.length; i++) {
            result.addAll( searchGameRecu(rep[i], linuxName1, linuxName2, numMin, numMax));
        }
        
        return result;
    }
        
    /** 
     * V�rifie que le fichier nameFile correspond aux crit�res de recherche.
     * Le nameFile contient les deux noms (si pas null) et le numero de partie est
     * compris dans les bornes min et max.
     * 
     * @return True si les crit�res sont v�rifi�s.
     */
    public boolean checkName( File nameFile, String linuxName1, String linuxName2, int numMin, int numMax)
    {
        String name = nameFile.getName();
        // les noms
        //System.out.println(name+" contains <"+linuxName1+">");
        if( name.indexOf( linuxName1) == -1) {
            return false;
        }
        //System.out.println(name+" contains <"+linuxName2+">");
        if( linuxName2 != null ) {
            if( name.indexOf( linuxName2 ) == -1 ) {
                return false;
            }
        }
        
        // le num�ro
        String number = name.substring( gameFileNbStart, gameFileNbEnd);
        int val = Integer.parseInt(number);
        if( (val < numMin) || (val > numMax )) {
            return false;
        }
        return true;
    }
    
    /**
     * Transforme le nom d'un joueur en une version "correcte" pour
     * un nom de fichier.
     * @param name
     * @return un nom "linuxien"
     */
    public String toLinuxName( String name )
    {
        if( name == null ) {
            return null;
        }
        String result = new String( name );
        result = result.replace( " ", "_");
        result = result.replace( "�", "e");
        result = result.replace( "�", "e");
        result = result.replace( "�", "e");
        result = result.replace( "�", "a");
        result = result.replace( "�", "o");
        result = result.replace( "�", "u");
        result = result.replace( "�", "u");
        result = result.replace( "�", "e");
        result = result.replace( "@", "a");
        return result;
    }

    /**
     * Pour savoir si un nom de fichier est un nom de partie valide.
     */
    class GameNameFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            //System.out.println("Filter <"+name+">");
            return name.matches( gameFilePattern);
        }
    }
    /**
     * Pour savoir si un nom de r�pertoire est un nom valide.
     */
    class GameRepFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            //System.out.println("Filter <"+name+">");
            return name.matches( gameRepPattern);
        }
    }
}
