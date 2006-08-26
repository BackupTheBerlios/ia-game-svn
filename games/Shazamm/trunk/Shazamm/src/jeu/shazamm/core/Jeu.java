/*
 * Created on Apr 9, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import java.util.Iterator;

import jeu.utils.GameException;

/**
 * Cette classe permet de g�rer un jeu de Shazamm. On applique des Coups
 * � des Etats.
 * @author dutech
 */
public class Jeu {
    
    /**
     * Creation avec rien de sp�cial.
     *
     */
    public Jeu()
    {
    }
    
    /**
     * L'EtatJeu initial.
     * @return Etat inital
     */
    public EtatJeu getInitState( int manaDebut, int nbCarte)
    {
        EtatJeu etat = new EtatJeu();

        etat.prepareForNewManche( manaDebut, nbCarte );
        return etat;
    }
    
    /**
     * Applique un coup � l'EtatJeu qui est dupliqu� AVANT d'appliquer le Coup.
     * @param p_etat
     * @param p_coup
     * @return Un nouvel EtatJeu
     * @throws GameException
     */
    public EtatJeu apply( EtatJeu p_etat, Coup p_coup )
    throws GameException
    {
        EtatJeu result = new EtatJeu( p_etat );
        result.dernierCoup = p_coup;
        p_coup.apply( this, result);
        update( result );
        return result;
    }
    
    /**
     * Ajoute a la liste des cartes jou�es les cartes mis�es par le joueur.
     * Pas de carte en double (ind�pendamment de la couleur).
     * @param etat
     * @param joueur
     */
    public void addCartesToPlay( EtatJeu etat, Joueur joueur )
    throws GameException
    {
        // CHECK joueur = DOIT_MISER
        if( joueur.etat == Constantes.DOIT_MISER ) {
            // essaie d'ajouter les cartes mis�es
            for (Iterator iCarte = joueur.cartesMisees.iterator(); iCarte.hasNext();) {
                Carte currCarte = (Carte) iCarte.next();
                // ajoute aux cartes jou�es si existe pas d�j�
                etat.addCarteCheck( currCarte, joueur);
            }
        }
        else {
            throw new GameException( Constantes.strCoul(joueur.coul) + " n'a pas le droit de miser");
        }
        // ENSURE joueur = ATTENDRE (fait par Coup)
    }

    /**
     * On essaye d'appliquer, dans l'ordre des priorit�es, les Cartes qui ont
     * �t� jou�es par les Joueurs.
     * @param etat
     * @return false si les cartes emp�chent de finaliser la r�solution de la mise.
     * Souvent, il faut alors un nouveau type de Coup.
     */
    public boolean applyCartes(EtatJeu etat)
    {
        // essaye d'appliquer les cartes dans l'ordre
        
        for (Iterator itCarte = etat.cartesJouees.iterator(); itCarte.hasNext();) {
            Carte currCarte = (Carte) itCarte.next();
            
            itCarte.remove();
            if( etat.autoriseSort == true ) {
                // DEBUG
                System.out.println( "#### Application de "+ currCarte.toString() );
                if( currCarte.effet( etat ) == false ) {
                    // DEBUG
                    System.out.println( etat.toString() );
                    return false;
                }
                // DEBUG
                System.out.println( etat.toString() );
            }
        }
        return true;
    }
    
  
    /**
     * Fait bouger le mur en fonction des mana et des cartes jou�es.
     * @param etat
     * @return null ou Joueur touch� par le mur.
     */
    Joueur moveFire( EtatJeu etat)
    {   
        int dirMove = etat.dirMove();
        boolean oneWinner = false;
        
        System.out.println( "Move vers " + Constantes.strCoul( dirMove ));
        oneWinner = etat.zePlateau.moveFire( dirMove );
        if( oneWinner == true ) {
            return etat.getJoueur( dirMove );
        }
        if( etat.brasierPlayed == true ) {
            oneWinner = etat.zePlateau.moveFire( dirMove );
        }
        if( oneWinner == true ) 
            return etat.getJoueur( dirMove );
        else
            return null;
    }
    
    /**
     * Si les mises sont faites, on r�sout le tour en testant pour des gagnants.
     * Sinon, on va appliquer les cartes jusqu'� ce qu'on ait besoin
     * d'interagir avec les Joueurs.
     * @param etat
     * @return true si le jeu a chang�
     */
    public boolean update( EtatJeu etat )
    {
        Joueur joueurRouge = etat.getJoueur( Constantes.ROUGE );
        Joueur joueurVert = etat.getJoueur( Constantes.VERT );
        
        // si les deux sont en attente -> applyCartes
        if( (joueurRouge.etat == Constantes.DOIT_ATTENDRE) && (joueurVert.etat == Constantes.DOIT_ATTENDRE)) {
            boolean peutFinir = applyCartes( etat );
            if( peutFinir == true ) {  
                // on termine la mise courante
                for( int i=0; i < etat.zeJoueurs.length; i++) {
                    etat.zeJoueurs[i].etat = Constantes.FIN_MISE;
                }
                
                boolean rougeWin = false;
                boolean vertWin = false;

                // fait se d�placer le mur 
                Joueur joueurTouche = moveFire( etat );
                
                // on met � jour le Mana
                for( int i=0; i < etat.zeJoueurs.length; i++) {
                    etat.zeJoueurs[i].mana.update();
                }
                //System.out.println("*DEBUG* EtatJeu\n"+etat.toString());
                
                // qq'un a touch� l'autre?
                if( joueurTouche != null ) {
                    if( etat.zePlateau.update() != Constantes.NO_COLOR) { // gagn�
                        joueurTouche.etat = Constantes.PERDU;
                        etat.getOtherJoueur( joueurTouche ).etat = Constantes.GAGNE;
                        return true;
                    }
                    // Personne n'a gagn�, on fait une nouvelle manche.
                    etat.prepareForNewManche( 50, 3);
                    return true;
                }
                
                // si le Mana est vide
                if( joueurRouge.mana.manaAfter == 0 ) {
                    //System.out.println( "*DEBUG* Mana Rouge Vide, vert="+joueurVert.mana.manaAfter);
                    while( (joueurVert.mana.manaAfter > 0) && (vertWin == false)) {
                        vertWin = etat.zePlateau.moveFire( Constantes.ROUGE);
                        joueurVert.mana.manaAfter--;
                        //System.out.println( "*DEBUG* Move vers Rouge = "+vertWin);
                    }
                    // un gagnant?
                    int perdant = etat.zePlateau.update();
                    
                    if( perdant != Constantes.NO_COLOR ) { // gagn�?
                        etat.getJoueur(perdant).etat = Constantes.PERDU;
                        etat.getOtherJoueur( perdant ).etat = Constantes.GAGNE;
                        return true;
                    }
                    // Personne n'a gagn�, on fait une nouvelle Manche.
                    etat.prepareForNewManche( 50, 3);
                    return true;
                }
                else if( joueurVert.mana.manaAfter == 0 ) {
                    while( (joueurRouge.mana.manaAfter > 0) && (rougeWin == false)) {
                        rougeWin = etat.zePlateau.moveFire( Constantes.VERT);
                        joueurRouge.mana.manaAfter--;
                    }
                    // un gagnant?
                    int perdant = etat.zePlateau.update();
                    
                    if( perdant != Constantes.NO_COLOR ) { // gagn�?
                        etat.getJoueur(perdant).etat = Constantes.PERDU;
                        etat.getOtherJoueur( perdant ).etat = Constantes.GAGNE;
                        return true;
                    }
                    // Personne n'a gagn�, on fait une nouvelle Manche.
                    etat.prepareForNewManche( 50, 3);
                    return true;
                }
            
                // Personne n'a gagn�, on fait un nouveau paris.
                etat.prepareForNewTour();
                return true;
            }
        }
        return false;
    }
}
