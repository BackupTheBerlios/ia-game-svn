/*
 * Created on Nov 19, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.coup;

import java.util.StringTokenizer;

import jeu.shazamm.core.Constantes;
import jeu.shazamm.core.Coup;
import jeu.shazamm.core.EtatJeu;
import jeu.shazamm.core.Jeu;
import jeu.shazamm.core.Joueur;
import jeu.utils.ExtractException;
import jeu.utils.GameException;

/**
 * @author dutech
 */
public class RecyclageC extends Coup {

    public int coulJoueur;
    public int modif;
    
    /**
     * On peut recycler +/- 5 points :   
     * j:r(+-x)	
     */
    public RecyclageC()
    {
        super();
    }
    /**
     * Crée avec une mise et des sorts.
     * @param p_coul du Joueur
     * @param p_mise Mana joué
     * @param p_sorts liste des priorité, ou null.
     */
    public RecyclageC( int p_coul, int p_modif)
    {
        coulJoueur = p_coul;
        modif = p_modif;
    }
    /**
     * Une copie conforme.
     */
    public Coup copy()
    {
        RecyclageC result = new RecyclageC();
        result.coulJoueur = coulJoueur;
        result.modif = modif;
        
        return result;
    }

    /**
     * Remet tout à zéro
     */
    public void reset()
    {
        coulJoueur = Constantes.VIDE;;
        modif=0;
    }

    /** 
     * Ajoute un sort précédent de l'adversaire aux sorts joués par le joueur.
     * Puis, ce dernier attend de nouveau.
     */
    public void apply(Jeu p_jeu, EtatJeu p_etat) 
    throws GameException
    {
        Joueur zeJoueur = p_etat.getJoueur( coulJoueur );
        if( zeJoueur.etat == Constantes.DOIT_RECYCLER ) {
            // on ne peut dépenser trop ou pas assez!
            if( zeJoueur.mana.manaBet+modif < 1 ) {
                modif = 1 - zeJoueur.mana.manaBet; 
            }
            else if (zeJoueur.mana.manaBet+modif > zeJoueur.mana.manaBefore) {
                modif = zeJoueur.mana.manaBefore - zeJoueur.mana.manaBet;
            }
            zeJoueur.mana.manaBet += modif;
            zeJoueur.mana.strikeForce += modif;
            zeJoueur.mana.manaSpent += modif;
            
            // change le statut du joueur
            zeJoueur.etat = Constantes.DOIT_ATTENDRE;
            
        }
        else if( (zeJoueur.etat == Constantes.DOIT_MISER ) ||
                 (zeJoueur.etat == Constantes.PERDU) ||
                 (zeJoueur.etat == Constantes.GAGNE) ){
            // on vient de jouer un FDM !!!
            // on ne fait rien
        }
        else {
            throw new GameException( Constantes.strCoul(zeJoueur.coul)+" ne peut recycler!");
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
	    if( !token. equals("r") ) {
	        // c'est pas un clonage
	        throw new ExtractException(">"+token+"< n'annonce pas un clonage!");
	    }
	    
	    // mise
	    int nbPoints[] = extractListFrom( st );
	    if( nbPoints.length != 1 ) {
	        throw new ExtractException( "Pas de modification valide");
	    }
	    modif = nbPoints[0];
	    if( (modif > 5 ) || (modif < (-5))) {
	        throw new ExtractException( "Recyclage trop important");
	    }
    }

    /**
     * Format = "j:r(+-x).
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        
        strbuf.append( Constantes.strCoul( coulJoueur ) + ":");
        strbuf.append( "r("+modif+")");
        
        return strbuf.toString();
    }
}
