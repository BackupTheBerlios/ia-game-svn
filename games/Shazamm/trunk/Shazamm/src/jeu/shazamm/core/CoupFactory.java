/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.core;

import jeu.shazamm.coup.*;
import jeu.utils.ExtractException;

/**
 * Le but est de créer les coups possible à Shazamm.
 * @author dutech
 */
public class CoupFactory {
    
    /**
     * En partant d'une chaîne de caractère, on essaye de reconstruire
     * l'un des Coups connu.
     * Pour l'instant, il y a : MiseC, CloneC, LarcinC, RecyclageC.
     * @return null ou iseC, CloneC, LarcinC, RecyclageC.
     */
    public Coup extractFrom( String buff )
    {
        MiseC aMise = new MiseC();
        String eMise = null;
        CloneC aClone = new CloneC();
        String eClone = null;
        LarcinC aLarcin = new LarcinC();
        String eLarcin = null;
        RecyclageC aRecyclage = new RecyclageC();
        String eRecyclage = null;
        
        try {
            aMise.extractFrom( buff );
            return aMise;
        } 
        catch (ExtractException e) {
            eMise = e.getMessage();
        }
        catch( Exception e) {
        }
        
        try {
            aClone.extractFrom( buff );
            return aClone;
        } 
        catch (ExtractException e) {
            eClone = e.getMessage();
        }
        catch( Exception e) {
        }
        
        try {
            aLarcin.extractFrom( buff );
            return aLarcin;
        } 
        catch (ExtractException e) {
            eLarcin = e.getMessage();
        }
        catch( Exception e) {
        }
        
        try {
            aRecyclage.extractFrom( buff );
            return aRecyclage;
        } 
        catch (ExtractException e) {
            eRecyclage = e.getMessage();
        }
        catch( Exception e) {
        }
        
        System.out.println( "CoupFactory : return null");
        System.out.println( "Mise : " + eMise);
        System.out.println( "Clone : " + eClone);
        System.out.println( "Larcin : " + eLarcin);
        System.out.println( "Recyclage : " + eRecyclage);
        return null;
    }

}
