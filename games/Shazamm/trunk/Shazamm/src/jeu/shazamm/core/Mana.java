/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import jeu.utils.GameException;

/**
 * Permet de gérer le Mana d'un joueur.
 * @author dutech
 */
public class Mana {
    
    public int manaBefore;
    public int manaBet;
    public int strikeForce;
    public int manaSpent;
    public int manaAfter;
    public int manaMax;
    
    /**
     * Une Mana créée à partir d'un montant initial de Mana.
     * @param initialMana
     */
    public Mana( int initialMana )
    {
        reset(initialMana);
    }
    /**
     * Clone.
     */
    public Mana( Mana p_mana )
    {
        manaBefore = p_mana.manaBefore;
        manaBet = p_mana.manaBet;
        strikeForce = p_mana.strikeForce;
        manaSpent = p_mana.manaSpent;
        manaAfter = p_mana.manaAfter;
        manaMax = p_mana.manaMax;
    }
    /**
     * Egalité des mises?.
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Mana) {
            Mana other = (Mana) obj;
            boolean result = (manaBefore == other.manaBefore) &&
            (manaBet == other.manaBet) &&
            (strikeForce == other.strikeForce) &&
            (manaSpent == other.manaSpent) &&
            (manaAfter == other.manaAfter) &&
            (manaMax == other.manaMax);
            return result;
        }
        return false;
    }
    
    /**
     * Extrait une mise à partir d'une chaine de caractère.
     * @param buff
     * @param etat
     */
    public void extractFrom( String buff, EtatJeu etat)
    {
        
    }
    /**
     * Prépare une mise qui doit respecter les règles.
     * @param nbMana points de mana misés.
     * @throws GameException
     */
    public void setBet( int nbMana )
    throws GameException
    {
        if( nbMana < 1 ) {
            throw new GameException( "nbMana doit être au moins égal à 1");
        }
        if( nbMana > manaBefore ) {
            throw new GameException( "nbMana ne peut être supérieur à ce qui est en réserve");
        }
        manaBet = nbMana;
        strikeForce = manaBet;
        manaSpent = manaBet;
    }

    /** 
     * Avec une valeur initiale qui sera aussi la valeur maximale.
     * @param nbMana
     */
    public void reset( int nbMana )
    {
        manaMax = nbMana;
        manaBefore = nbMana;
        manaBet = 0;
        strikeForce = 0;
        manaSpent = 0;
        manaAfter = 0;
    }
    /**
     * Prépare un nouveau Bet en recopiant 'manaAFter' dans 'manaBefore'.
     */
    public void prepareNewBet()
    {
        manaBefore = manaAfter;
        manaBet = 0;
        strikeForce = 0;
        manaSpent = 0;
        manaAfter = 0;
    }
    /**
     * Met à jour le mana restant, et prévient si perd.
     * @return true si mana restant est nul.
     */
    public boolean update()
    {
        manaAfter = manaBefore - manaSpent;
        if( manaAfter > manaMax )
            manaAfter = manaMax;
        if( manaAfter <= 0 ) {
            manaAfter = 0;
            return true;
        }
        return false;
    }
    
    /**
     * Classique.<br>
     * [before] => Bet | Force | Spent = After
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( "["+manaBefore+"] => ");
        strbuf.append( manaBet + " | " + strikeForce + " | " + manaSpent );
        strbuf.append( " = " + manaAfter);
        return strbuf.toString();
    }
}
