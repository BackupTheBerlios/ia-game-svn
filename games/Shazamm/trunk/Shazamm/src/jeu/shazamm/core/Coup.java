/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import jeu.utils.ExtractException;
import jeu.utils.GameException;

/**
 * @author dutech
 */
abstract public class Coup {
    /**
     * Crée en faisant un reset. 
     */
    public Coup()
    {
        reset();
    }
    
    /**
     * Crée en partant d'un String.
     * @param buff
     * @param zeJeu auquel le Coup est lié.
     */
    public Coup( String buff )
    throws ExtractException
    {
        reset();
        extractFrom( buff );
    }
    
    /**
     * Crée un clone a partir du coup actuel.
     */
    abstract public Coup copy();
    
    /** 
     * Remet tout à zéro.
     */
    abstract public void reset();
    
    /**
     * Applique le Coup au jeu.
     * @param p_jeu
     */
    abstract public void apply( Jeu p_jeu, EtatJeu p_etat )
    throws GameException;
    
    /**
     * Extrait le coup en vérifiant que le code du coup est le bon.
     * @param buff
     */
    abstract public void extractFrom( String buff )
    throws ExtractException;
    
    /**
     * Extrait un tableau d'entier d'une chaine du genre "(xx, yy, z )".
     * @param st Tokenizer où ")" <br>est</br> un token.
     * @return tableau d'entier
     */
    public int[] extractListFrom( StringTokenizer st )
    {
        Vector extracted = new Vector(3);
        String token;
        
        while( st.hasMoreTokens() ) {
            token = st.nextToken();
            if( token.contains(")")) {
                token = token.replace(")", "");
                if( token.length() > 0 )
                    extracted.addElement(token);
                break;
            }
            else {
                extracted.addElement(token);
            }
        }
        int result[] = new int[extracted.size()];
        int i=0;
        for (Iterator iter = extracted.iterator(); iter.hasNext();) {
            String element = (String) iter.next();
            System.out.println( "list lu= <"+element+">");
            result[i] = Integer.decode(element).intValue();
            i++;
        }
        return result;
    }
    
}
