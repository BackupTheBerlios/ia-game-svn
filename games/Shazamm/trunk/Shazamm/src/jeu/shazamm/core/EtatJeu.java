/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


/**
 * 
 * Pour appliquer les effets des cartes, on mémorise une TreeMap (carte, coul_beneficiaire)
 * qui permettra de gérer les doublons (-> pas d'effet) et l'ordre des carte.
 *	
 * @author dutech
 */
public class EtatJeu {
    
    public Joueur zeJoueurs[];
    public Plateau zePlateau;
    public Coup dernierCoup;
    
    /// Manche
    public int manche;
    /// Tour
    public int tour;
    
    /// autorise-t-on les Sorts (Mutisme)?
    public boolean autoriseSort;
    /// brasier joué? 
    public boolean brasierPlayed;
    /// QuiPerdGagne joué?
    public boolean qpgPlayed;
    /// Résistance joué par qui?
    public int resistPlayed;
    
    /// Lise des carte devant faire effet.
    public ArrayList cartesJouees;
    
    /**
     * Creation des Joueurs et du Plateau.
     */
    public EtatJeu()
    {
        zeJoueurs = new Joueur[2];
        zeJoueurs[0] = new Joueur( 50, Constantes.ROUGE );
        zeJoueurs[1] = new Joueur( 50, Constantes.VERT );
        
        zePlateau = new Plateau(19);
        
        dernierCoup = null;
        
        // les divers flags
        autoriseSort = true;
        brasierPlayed = false;
        qpgPlayed = false;
        resistPlayed = Constantes.NO_COLOR;
        
        cartesJouees = new ArrayList();
        
        manche = 0;
        tour = 0;
    }
    /**
     * Clone.
     */
    public EtatJeu( EtatJeu p_etat )
    {
        zeJoueurs = new Joueur[p_etat.zeJoueurs.length];
        for( int i=0; i < zeJoueurs.length; i++ ) {
            zeJoueurs[i] = new Joueur( p_etat.zeJoueurs[i]);
        }
        
        zePlateau = new Plateau( p_etat.zePlateau );
        
        if( p_etat.dernierCoup == null ) {
        dernierCoup = null;
        }
        else {
            dernierCoup = p_etat.dernierCoup.copy();
        }
        
        autoriseSort = p_etat.autoriseSort;
        brasierPlayed = p_etat.brasierPlayed;
        qpgPlayed = p_etat.qpgPlayed;
        resistPlayed = p_etat.resistPlayed;
        
        // une copie simple devrait suffire
        cartesJouees = (ArrayList) p_etat.cartesJouees.clone();
        
        manche = p_etat.manche;
        tour = p_etat.tour;
    }
    
    /**
     * Egalité (sauf le dernierCoup, manche et tour).
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof EtatJeu) {
            EtatJeu other = (EtatJeu) obj;
            boolean result = (Arrays.equals( zeJoueurs, other.zeJoueurs)) &&
            (zePlateau.equals( other.zePlateau ));
            result = result && (cartesJouees.equals(other.cartesJouees));
            result = result && (autoriseSort == other.autoriseSort);
            result = result && (brasierPlayed == other.brasierPlayed);
            result = result && (qpgPlayed == other.qpgPlayed);
            result = result && (resistPlayed == other.resistPlayed);
            return result;
        }
        return false;
    }
    
    /**
     * Trouve un joueur en fonction de sa couleur.
     * @param couleur
     * @return Joueur ou null si pas bonne couleur.
     */
    public Joueur getJoueur( int couleur )
    {
        if( couleur == Constantes.ROUGE ) {
            return zeJoueurs[0];
        }
        else if( couleur == Constantes.VERT ) {
            return zeJoueurs[1];
        }
        return null;
    }
    /**
     * Trouve l'autre joueur.
     */
    public Joueur getOtherJoueur( Joueur player)
    {
        if( player.equals(zeJoueurs[0])) {
            return zeJoueurs[1];
        }
        else if( player.equals(zeJoueurs[1])) {
            return zeJoueurs[0];
        }
        return null;
    }
    /**
     * Trouve l'autre joueur.
     */
    public Joueur getOtherJoueur( int couleur )
    {
        if( couleur == Constantes.ROUGE ) {
            return zeJoueurs[1];
        }
        else if( couleur == Constantes.VERT ) {
            return zeJoueurs[0];
        }
        return null;
    }
    
    /**
     * Ajoute une carte à carteJouees, en vérifiant qu'elle n'est pas
     * déjà jouée par l'autre joueur.
     * @param p_carte à ajouter
     * @return true si la carte est bien ajoutée, false si on la retire
     */
    public boolean addCarteCheck( Carte p_carte, Joueur p_benef )
    {
        int index = 0;
        for (Iterator itCarte = cartesJouees.iterator(); itCarte.hasNext();) {
            Carte currCarte = (Carte) itCarte.next();
            if( currCarte.compareTo(p_carte) > 0 ) {
                //currCarte is bigger
                p_carte.beneficiaire = p_benef.coul;
                cartesJouees.add( index, p_carte );
                return true;
            }
            else if( currCarte.compareTo(p_carte) == 0) {
                if( currCarte.beneficiaire != p_benef.coul) {
                    // déjà jouée par l'autre
                    cartesJouees.remove(index);
                    return false;
                }
                else {
                    p_carte.beneficiaire = p_benef.coul;
                    cartesJouees.add( index, p_carte );
                    return true;
                }
            }
            index++;
        }
        p_carte.beneficiaire = p_benef.coul;
        cartesJouees.add(p_carte);
        return true;
    }
    /**
     * Ajoute une carte à carteJouees, sans vérifier qu'elle n'existe pas.
     * L'ajout se fait dans l'ordre...
     * @param p_carte à ajouter
     * @return true si la carte est bien ajoutée
     */
    public boolean addCarteNoCheck( Carte p_carte, Joueur p_benef )
    {
        int index = 0;
        for (Iterator itCarte = cartesJouees.iterator(); itCarte.hasNext();) {
            Carte currCarte = (Carte) itCarte.next();
            if( currCarte.compareTo(p_carte) > 0 ) {
                //currCarte is bigger
                p_carte.beneficiaire = p_benef.coul;
                cartesJouees.add( index, p_carte );
                return true;
            }
            index++;
        }
        p_carte.beneficiaire = p_benef.coul;
        cartesJouees.add(p_carte);
        return true;
    }
    /**
     * Vers quel joueur va se déplacer le mur
     * @return ROUGE, VERT ou NO_COLOR.
     */
    public int dirMove()
    {
        Joueur joueurRouge = getJoueur( Constantes.ROUGE );
        Joueur joueurVert = getJoueur( Constantes.VERT );
        int dirMoved = Constantes.NO_COLOR;
        
        // dans quelle direction se déplace le mur?.
        if( qpgPlayed == false ) {
            if( joueurRouge.mana.strikeForce >
                    joueurVert.mana.strikeForce ) {
                dirMoved = Constantes.VERT;
            }
            else if( joueurRouge.mana.strikeForce <
                    joueurVert.mana.strikeForce ) {
                dirMoved = Constantes.ROUGE;
            }
        }
        else {
            if( joueurRouge.mana.strikeForce <
                    joueurVert.mana.strikeForce ) {
                dirMoved = Constantes.VERT;
            }
            else if( joueurRouge.mana.strikeForce >
                    joueurVert.mana.strikeForce ) {
                dirMoved = Constantes.ROUGE;
            }
        }
        if( dirMoved != resistPlayed) {
            return dirMoved;
        }
        else {
            return Constantes.NO_COLOR;
        }
                
    }
    
    /**
     * Prépare pour un nouveau tour de jeu.
     */
    public void prepareForNewTour()
    {
        tour += 1;
        
        // les divers flags
        brasierPlayed = false;
        qpgPlayed = false;
        resistPlayed = Constantes.NO_COLOR;
        
        cartesJouees.clear();
        
        for( int i=0; i < zeJoueurs.length; i++) {
            zeJoueurs[i].prepareForNewTour();
        }
    }
    /**
     * Prépare pour une nouvelle manche.
     * @param nbPoints mana de départ
     * @param nbPioche combien on pioche de cartes
     */
    public void prepareForNewManche( int nbPoints, int nbPioche)
    {
        tour += 1;
        manche += 1;
        
        // les divers flags
        autoriseSort = true;
        brasierPlayed = false;
        qpgPlayed = false;
        resistPlayed = Constantes.NO_COLOR;
        
        cartesJouees.clear();
        
        for( int i=0; i < zeJoueurs.length; i++) {
            zeJoueurs[i].prepareForNewManche( nbPoints, nbPioche);
        }
    }

    /**
     * Classique.
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( ">>>>>> Manche "+manche+", Tour "+tour+" <<<<<<\n");
        strbuf.append( "Dernier coup = ");
        if( dernierCoup == null ) {
            strbuf.append( "-\n");
        }
        else {
            strbuf.append( dernierCoup.toString() + "\n");
        }
        strbuf.append( zePlateau.toString()+"\n");
        
        
        if( qpgPlayed == true ) {
            strbuf.append( ">>> QuiPerdGagne joué <<<\n");
        }
        if( brasierPlayed == true ) {
            strbuf.append( ">>> Brasier joué <<<\n");
        }
        if( resistPlayed != Constantes.NO_COLOR ) {
            strbuf.append( ">>> Résistance joué par "+Constantes.strCoul(resistPlayed)+" <<<\n");
        }
        if( autoriseSort == false ) {
            strbuf.append( ">>> Les sorts sont interdits <<<\n");
        }
  
        strbuf.append( "---- cartesJouées ----\n");
        //strbuf.append( cartesJouees.entrySet().toString() );
        for (Iterator itCarte = cartesJouees.iterator(); itCarte.hasNext();) {
            Carte currCarte = (Carte) itCarte.next();
            strbuf.append( currCarte.toString() +"\n" );
        }
        
        strbuf.append( "ROUGE : " + zeJoueurs[0].toString() + "\n");
        strbuf.append( "VERT : " + zeJoueurs[1].toString() + "\n");
        return strbuf.toString();
    }

}
