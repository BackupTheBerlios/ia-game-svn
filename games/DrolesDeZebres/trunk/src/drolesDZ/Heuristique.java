/*
 * Created on Mar 11, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package drolesDZ;

/**
 * Cette heuristique est d�di�e au plateau original.
 * Le principe est de faire un calcul en "pire cas".
 * On essaie, sans tenir compte des propri�t�s sp�ciales 
 * des Lions et des Crocodiles, de donner le maximum au joueur 
 * adverse en le faisant majoritaire dans un maximum de r�gions,
 * en commen�ant par les r�gions les plus grandes.
 * Puis, le joueur actuel compl�te en mettant ses plus grosses
 * pi�ces dans les r�gions o� il peut �tre majoritaire...
 * Cela se fait en plusieurs �tapes :
 *  - calcul d'indicateurs pour chaque r�gion
 *  - non-majeur essaie de s'approprier les r�gions par ordre d�croissant
 *  - majeur s'approprie les r�gions qui restent
 *  - tant qu'il reste des pi�ces � non-majeur, si elles sont plus grosses 
 * que celles de majeur, elles vont dans des zones de non-majeur.
 * @author dutech
 */
public class Heuristique {
    
    /** classement des r�gions par taille */
    public static int[] orderRegion = {1, 2, 0, 5, 3, 4};
    
    /** Cases libres dans chaque r�gion */
    public int[] nbCaseLeft;
    /** Couleur majoritaire pour chaque r�gion */
    public int[] coulMajority;
    /** Valeur de chaque r�gion (positif pour joueur majeur) */
    public int[] val;
    
    /** Les joueurs */
    public Joueur[] zeJoueurs;
    /** Nombre de pi�ces par encore plac�es pour chaque joueur */
    public int[] piecesLeft;
    /** Couleur du joueur majeur */
    public int coulJoueur;
    /** Plateau de jeu */
    public Plateau zePlateau;
    
    /**
     * Pr�pare le calcul de l'heuristique et clone les r�serves des joueurs.
     * @param p_state
     * @param p_coulJoueur
     */
    public Heuristique( EtatJeu p_state, int p_coulJoueur )
    {
        zePlateau = p_state.zePlateau;
        coulJoueur = p_coulJoueur;
        val = new int[Plateau.nbRegion];
        nbCaseLeft = new int[Plateau.nbRegion];
        coulMajority = new int[Plateau.nbRegion];
        for( int i=0; i < Plateau.nbRegion; i++ ) {
            val[i] = 0;
            nbCaseLeft[i] = -1;
            coulMajority[i] = -1;
        }
        
        // Deep clone of joueurs
        piecesLeft = new int[p_state.zeJoueurs.length];
        zeJoueurs = new Joueur[p_state.zeJoueurs.length];
        for( int i=0; i < p_state.zeJoueurs.length; i++ ) {
            zeJoueurs[i] = new Joueur( p_state.zeJoueurs[i] );
            piecesLeft[i] = 0;
            for( int j=0; j<zeJoueurs[i].reserve.length; j++ ) {
                piecesLeft[i] += zeJoueurs[i].reserve[j];
            }
        }
        
    }
    
    
    /**
     * Remplit les r�gions de fa�on � avoir une majorit� dans chaque,
     * avec une priorit� pour le joueur non-majeur.
     * val, nbCaseLeft, coulMajority sont maj pour r�gions.
     * piecesLeft est maj pour Joueurs.
     * @param startIndice
     * @return
     */
    public void distributMajorityRegion()
    {
        int startIndice = 0;
        while( startIndice < orderRegion.length ) {
            // DEBUG *******************************************
            System.out.println( "distributeMajority region=" + startIndice);
            // *************************************************
            // cherche qui peut gagner la majorit�
            int[][] currentRegion = Plateau.regions[startIndice];
            int forJoueur = 0;
            int againstJoueur = 0;
            int toMajority = currentRegion.length / 2 + 1;
            val[startIndice] = 0;
            nbCaseLeft[startIndice] = currentRegion.length;
            for( int i = 0; i < currentRegion.length; i++ ) {
                
                // compte les cases de chacun et la valeur
                if( !zePlateau.isCaseEmpty( currentRegion[startIndice][0],
                        currentRegion[startIndice][1])) {
                    nbCaseLeft[startIndice]--;
                    Piece currentPiece = zePlateau.cases[currentRegion[startIndice][0]][currentRegion[startIndice][1]];
                    if( currentPiece.m_joueur.couleur == coulJoueur ) {
                        forJoueur++;
                        val[startIndice] += currentPiece.val;
                    }
                    else {
                        againstJoueur ++;
                        val[startIndice] -= currentPiece.val;
                    }
                }
            }
            // DEBUG *******************************************
            System.out.println( "forJoueur = " + forJoueur + ", againstJoueur = " + againstJoueur + ", toMajority = " + toMajority);
            // *************************************************
            // pour qui est la majorit�
            if( forJoueur < toMajority ) {
                // Elle peut �tre gagn� par le joueur non-majeur
                // si il n'a pas assez de pi�ce �a sera fait par l'autre joueur
                if( piecesLeft[1-coulJoueur] >= (toMajority-againstJoueur) ) {
                    fillToMajority( startIndice, toMajority-againstJoueur, 1 - coulJoueur );
                    // DEBUG *******************************************
                    System.out.println( "fill for against!");
                    // *************************************************
                }
                else {
                    fillToMajority( startIndice, toMajority-forJoueur, coulJoueur);
                    // DEBUG *******************************************
                    System.out.println( "fill for majeur joueur!");
                    // *************************************************                    
                }
            }
        }
        startIndice++;
    }
    
    private void distributeLeftPieces()
    {
        
    }
    
    /**
     * Essaie de faire remplir une r�gion pour obtenir une majorit� par
     * 'p_coulJoueur'.
     * nbCasesLeft, coulMajority et val[p_region] sont maj.
     * @param p_region
     * @param p_nbPieces
     * @param p_coulJoueur
     */
    private void fillToMajority( int p_region, int p_nbPieces, int p_coulJoueur )
    {
        // qui va remplir?
        int coulJoueurRempli = p_coulJoueur;
        int facteurValPiece = 1;
        if( p_coulJoueur == coulJoueur ) {
            //c'est l'autre qui rempli
           facteurValPiece = -1;
        }
        
        coulMajority[p_region] = coulJoueurRempli;
        for( int nb=0; nb < p_nbPieces; nb++ ) {
            int valPiece = getBiggestPiece( zeJoueurs[coulJoueurRempli]);
            if( valPiece < 0 ) {
                System.err.println( "Heuristique : plus de piece pour "+Joueur.toString(coulJoueurRempli));
                System.err.println( toString() );
                System.exit(1);
            }
            else {
                val[p_region] += facteurValPiece * valPiece;
                nbCaseLeft[p_region]--;
                piecesLeft[coulJoueurRempli]--;
            }
        }
    }
    
    /**
     * Essaie de prendre la plus grosse pi�ce restante dans la r�serve du joueur.
     * @param p_joueur
     * @return val ou -1
     * @todo Mieux g�rer Lion et Crocodile ??
     */
    private int getBiggestPiece( Joueur p_joueur ) 
    {
        // on essaie les pieces par ordre de valeur
        if( p_joueur.prendrePiece(Piece.zebre) ) {
            return 6;
        }
        if( p_joueur.prendrePiece(Piece.elephant) ) {
            return 5;
        }
        if( p_joueur.prendrePiece(Piece.gazelle) ) {
            return 2;
        }
        if( p_joueur.prendrePiece(Piece.crocodile) ) {
            return 0;
        }
        if( p_joueur.prendrePiece(Piece.lion)) {
            return 1;
        }
        return -1;
    }
    
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( "HEURISTIQUE\n");
        for( int i=0; i < Plateau.nbRegion; i++ ) {
            strbuf.append( " {"+Joueur.toString(coulMajority[i])+"}"+val);
            strbuf.append( "("+nbCaseLeft[i]+") |");
        }
        strbuf.append("\n");
        
        for( int i = 0; i < zeJoueurs.length; i++ ) {
            strbuf.append( Joueur.toString(i) + " = " + zeJoueurs[i].displayReserve()+"\n");
        }
        return strbuf.toString();
    }
}
