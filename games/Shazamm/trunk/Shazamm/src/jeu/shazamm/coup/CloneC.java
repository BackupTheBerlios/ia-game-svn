/*
 * Created on Nov 19, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.coup;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import jeu.shazamm.core.*;
import jeu.utils.ExtractException;
import jeu.utils.GameException;

/**
 * @author dutech
 */
public class CloneC extends Coup {

    public int coulJoueur;
    public int sorts[];
    
    /**
     * Un clonage est constitué d'une liste d'au plus une carte
     * à cloner.
     * Elle est représentée et stockée sous la forme :  
     * j:c([xx])	
     */
    public CloneC()
    {
        super();
    }

    /**
     * Crée avec un/des sorts.
     * @param p_coul
     * @param p_sorts Liste de priorité de sorts.
     */
    public CloneC( int p_coul, List p_sorts )
    {
        coulJoueur = p_coul;
        sorts = null;
        if (p_sorts != null ) {
            sorts = new int[p_sorts.size()];
            int i = 0;
            for (Iterator iSort = p_sorts.iterator(); iSort.hasNext(); i++) {
                Integer sort_priority = (Integer) iSort.next();
                sorts[i] = sort_priority.intValue();
            }
        }
    } 
    /**
     * Une copie conforme.
     */
    public Coup copy()
    {
        CloneC result = new CloneC();
        result.coulJoueur = coulJoueur;
        if( sorts != null ) {
            result.sorts = (int[]) sorts.clone();
        }
        
        return result;
    }

    /**
     * Remet tout à zéro
     */
    public void reset()
    {
        coulJoueur = Constantes.VIDE;;
        sorts = null;    }

    /** 
     * Ajoute un sort précédent de l'adversaire aux sorts joués par le joueur.
     * Puis, ce dernier attend de nouveau.
     */
    public void apply(Jeu p_jeu, EtatJeu p_etat) 
    throws GameException
    {
        Joueur zeJoueur = p_etat.getJoueur( coulJoueur );
        if( zeJoueur.etat == Constantes.DOIT_CLONER ) {
            boolean cloneFound = true;
            //si un sort est cloné, il doit être dans la liste des sorts joués
            // par l'adversaire
            if( sorts.length > 0 ) {
                Joueur otherJoueur = p_etat.getOtherJoueur(zeJoueur);
                cloneFound = false;
                for (Iterator iterC = otherJoueur.mainPrecedente.iterator(); iterC.hasNext();) {
                    Carte currCarte = (Carte) iterC.next();
                    if( currCarte.priority == sorts[0] ) {
                        cloneFound = true;
                        // ajoute à la main courante
                        zeJoueur.cartesMisees.add( currCarte );
                        // ajoute aux cartes jouées si existe pas déjà
                        p_etat.addCarteCheck( currCarte, zeJoueur );
                    }
                }
            }
            if( cloneFound ) {
                // change le statut du joueur
                zeJoueur.etat = Constantes.DOIT_ATTENDRE;
            }
            else {
                throw new GameException( "Le sort cloné n'a pas été joué au tour précédent.");
            }
        }
        else {
            throw new GameException( Constantes.strCoul(zeJoueur.coul)+" ne peut cloner!");
        }
    }

    /**
     * Extrait le coup en vérifiant que le code du coup est le bon.
     * @param buff
     */
    public void extractFrom(String buff) 
    throws ExtractException
    {
        StringTokenizer st = new StringTokenizer( buff, ":(,+ \t\n\r\f");
	    String token;
	    
	    // joueur
	    token = st.nextToken();
	    coulJoueur = Constantes.decodeCoul( token );
	    if( coulJoueur == Constantes.NO_COLOR ) {
	        throw new ExtractException(">"+token+"< n'est pas une couleur valide!");
	    }
	   
	    // type de coup
	    token = st.nextToken();
	    if( !token. equals("c") ) {
	        // c'est pas un clonage
	        throw new ExtractException(">"+token+"< n'annonce pas un clonage!");
	    }
	    
	    // mise
	    sorts = extractListFrom( st );
	    if( sorts.length > 1 ) {
	        throw new ExtractException( "Trop de cartes clonées!");
	    }
    }

    /**
     * Format = "j:c([xx]).
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        
        strbuf.append( Constantes.strCoul( coulJoueur ) + ":");
        strbuf.append( "c(");
        if( sorts != null ) {
            for (int i = 0; i < sorts.length; i++) {
                strbuf.append( sorts[i]+", ");
            }
        }
        strbuf.append(")");
        return strbuf.toString();
    }
}
