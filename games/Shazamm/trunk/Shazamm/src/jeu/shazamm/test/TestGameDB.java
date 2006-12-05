/*
 * Created on Dec 5, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import jeu.utils.GameDBManager;

public class TestGameDB {

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try {
            GameDBManager dbase = new GameDBManager("/home/dutech/EclipseWork/ShazammSVN/data");
            
            List liste = dbase.searchGame("CasusBelli", "nico2", 2000, 3000);
            for (Iterator tmpFich = liste.iterator(); tmpFich.hasNext();) {
                File fichier = (File) tmpFich.next();
                System.out.println(fichier.getName());
            }
        }
        catch( Exception e) {
            System.out.println(e.getMessage());
        }
       
    }

}
