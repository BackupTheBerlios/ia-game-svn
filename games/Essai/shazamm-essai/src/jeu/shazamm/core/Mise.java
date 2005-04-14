/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import jeu.utils.GameException;

/**
 * @author dutech
 */
public class Mise {
    
    public int manaBefore;
    public int manaBet;
    public int strikeForce;
    public int manaSpent;
    public int manaAfter;
    
    /**
     * Une Mise cr��e � partir d'un montant initial de Mana.
     * @param initialMana
     */
    public Mise( int initialMana )
    {
        manaBefore = initialMana;
        manaBet = 0;
        strikeForce = 0;
        manaSpent = 0;
        manaAfter = 0;
    }
    /**
     * Clone.
     */
    public Mise( Mise p_mise )
    {
        manaBefore = p_mise.manaBefore;
        manaBet = p_mise.manaBet;
        strikeForce = p_mise.strikeForce;
        manaSpent = p_mise.manaSpent;
        manaAfter = p_mise.manaAfter;
    }
    /**
     * Egalit� des mises?.
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Mise) {
            Mise other = (Mise) obj;
            boolean result = (manaBefore == other.manaBefore) &&
            (manaBet == other.manaBet) &&
            (strikeForce == other.strikeForce) &&
            (manaSpent == other.manaSpent) &&
            (manaAfter == other.manaAfter);
            return result;
        }
        return false;
    }
    
    /**
     * Extrait une mise � partir d'une chaine de caract�re.
     * @param buff
     * @param etat
     */
    public void extractFrom( String buff, EtatJeu etat)
    {
        
    }
    /**
     * Pr�pare une mise qui doit respecter les r�gles.
     * @param nbMana points de mana mis�s.
     * @throws GameException
     */
    public void setBet( int nbMana )
    throws GameException
    {
        if( nbMana < 1 ) {
            throw new GameException( "nbMana doit �tre au moins �gal � 1");
        }
        if( nbMana > manaBefore ) {
            throw new GameException( "nbMana ne peut �tre sup�rieur � ce qui est en r�serve");
        }
        manaBet = nbMana;
        strikeForce = manaBet;
        manaSpent = manaBet;
    }

    /**
     * Pr�pare un nouveau Bet en recopiant 'manaAFter' dans 'manaBefore'.
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
     * Met � jour le mana restant, et pr�vient si perd.
     * @return true si mana restant est nul.
     */
    public boolean update()
    {
        manaAfter = manaBefore - manaSpent;
        if( manaAfter <= 0 ) {
            manaAfter = 0;
            return true;
        }
        return false;
    }
    
    /**
     * Classique.
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
