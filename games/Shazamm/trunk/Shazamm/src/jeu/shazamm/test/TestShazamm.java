/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jeu.shazamm.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import jeu.shazamm.core.Coup;
import jeu.shazamm.core.CoupFactory;
import jeu.shazamm.core.EtatJeu;
import jeu.shazamm.core.HistoriqueCoup;
import jeu.shazamm.core.Jeu;
import jeu.shazamm.parser.ParseException;
import jeu.utils.GameException;

/**
 * Test assez fruste du mécanisme du jeu. Il faut entrer des Coups
 * au clavier, et le jeu répond.
 * @author dutech
 */
public class TestShazamm {
    
    Jeu zeJeu;
    HistoriqueCoup zeCoups;
    ArrayList zePartie;
    ArrayList listePartiesCherchees;
    
    FileWriter myLogFile;
    
    private DecimalFormat df = new DecimalFormat("000000");
    
    public TestShazamm()
    {
        zeJeu = new Jeu();
        zeCoups = new HistoriqueCoup();
        zePartie = new ArrayList();
        listePartiesCherchees = new ArrayList();
        myLogFile = null;
    }
    /**
     * Affiche les diverses commandes.
     */
    public void help() {
        System.out.println("Liste des commandes (remplacez # par un nombre)");
        
        System.out.println("*** Manipulations de base sur le Jeu ***"); 
        System.out.println("  np - Lance une nouvelle partie interactive");
        
        System.out.println("*** Manipulations de base sur les Historiques ***"); 
        System.out.println("  ch - Charge une nouvelle partie à partir d'un fichier BAJ");
        System.out.println("  lh - Charge la partie à partie d'un fichier texte");
        System.out.println("  sh - Sauve la partie dans un fichier texte");
        System.out.println("  vh - Vérifie que l'historique est valable");
        
        System.out.println("*** Utilisation d'internet ***"); 
        System.out.println("  gh#### - Va chercher la partie N°### sur BoiteAJeux");
        System.out.println("  gl - Va chercher un ensemble de partie et les sauve");
        System.out.println("  gv - Va chercher un ensemble de partie, les vérifie et les sauve");
        
        System.out.println("*** General ***");
        System.out.println("   h - voir la liste des commandes (Help)");		
        System.out.println("   q - Quitte");
    }
    /**
     * Gère le menu et les actions de manière interactive.
     */
    public void humanInterface()
    {
        boolean fini = false; // arrête le programme ?
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        
        while( fini != true ) {
            // Message d'invite
            
            System.out.print("=> ");
            String choix = "";
            try {
                choix = reader.readLine();
                if( choix != null ) {
                    
                    if (choix.startsWith("h")) {
                        help();
                    } 
                    else if (choix.equals("q")) {
                        System.out.println("Au revoir !");
                        fini = true;
                        
                    }
                    // ***** Menu Partie *****
                    else if (choix.equals("np")) {	
                        runNewGame();	
                    }		
                    
                    // ***** Menu Historique *****
                    else if (choix.equals("ch")) {
                        System.out.print("Nom du fichier BAJ à charger ?");
                        choix = reader.readLine();
                        
                        zeCoups.readFromBaJFile( choix );
                        System.out.println( zeCoups.toString());
                    }
                    else if (choix.equals("lh")) {
                        System.out.print("Nom du fichier texte à charger ?");
                        choix = reader.readLine();
                        
                        zeCoups.readFromASCIIFile( choix );
                        System.out.println( zeCoups.toString());
                    }
                    else if (choix.equals("sh")) {
                        System.out.print("Nom du fichier à sauver ?");
                        choix = reader.readLine();
                        
                        zeCoups.writeToASCIFile( choix );
                    }
                    else if( choix.equals("vh")) {
                        zePartie.clear();
                        EtatJeu currentEtat;
                        // Comme si chacun avait toutes les cartes.
                        currentEtat = zeJeu.getInitState(50,14);
                        System.out.println( currentEtat.toString());
                        zePartie.add( currentEtat);
                        
                        for (Iterator iterC = zeCoups.listeCoups.iterator(); iterC.hasNext();) {
                            Coup element = (Coup) iterC.next();
                            System.out.println( "Application de " + element.toString());
                            currentEtat = zeJeu.apply( currentEtat, element);
                            System.out.println( currentEtat.toString());
                            zePartie.add( currentEtat );
                           
                        }
                    }
                    
                    // ***** Menu Internet *****
                    else if (choix.startsWith("gh")) {                       
                        String choixNb = choix.substring( choix.indexOf('h')+1);
                        int numPartie = Integer.parseInt(choixNb);			
                        
//                        URL bajURL;
//                        
//                        bajURL = new URL("http://www.boiteajeux.net/jeux/shz/historique.php?id=" + numPartie );
//                        System.out.println("Connexion à l'url");
//                        URLConnection connexion = bajURL.openConnection();
//                        InputStream connexionStream = connexion.getInputStream();
//                        
//                        zeCoups.readFromBajWeb( connexionStream );
                        loadBAJGame( numPartie );
                        System.out.println( zeCoups.toString());
                    }
                    else if (choix.startsWith("gl")) {
                        String input;
                        // numero de la première partie
                        System.out.println("Numéro de la première partie : ");
                        input = reader.readLine();
                        int numPartie = Integer.parseInt( input );
                        // nombre de partie à charger
                        System.out.println("Nombre de partie à charger : ");
                        input = reader.readLine();
                        int nbPartie = Integer.parseInt( input );
                        // répertoire où sont les données
                        System.out.println("Répertoire où sauver les données : ");
                        String baseRep = reader.readLine();
                        if( baseRep.equals("")) baseRep = new String( "." );
                        
                        // On sauvegarde dans répertoire "game_###_###"
                        String nomRep = new String( baseRep+"/game_"+numPartie+"_"+(numPartie+nbPartie-1));
                        File rep = new File ( nomRep );
                        rep.mkdir();
                        
                        // Pour chacune des parties
                        int indexPartie;
                        String nomPartieStr, numPartieStr;
                        for( int i=0; i<nbPartie; i++ ) {
                            indexPartie = numPartie+i;
                            numPartieStr = df.format( indexPartie );
                            loadBAJGame( indexPartie );
                            
                            String nomR = toLinuxName( zeCoups.nomJoueurRouge );
                            String nomV = toLinuxName( zeCoups.nomJoueurVert );
                            nomPartieStr = new String( nomRep+"/game_"+numPartieStr+"_"+nomR+"_vs_"+nomV+".sha");
                            zeCoups.writeToASCIFile( nomPartieStr );
                            System.out.println("Sauve ... "+nomPartieStr);
                        }
                    }
                    else if (choix.startsWith("gv")) {
                        String input;
                        // numero de la première partie
                        System.out.println("Numéro de la première partie : ");
                        input = reader.readLine();
                        int numPartie = Integer.parseInt( input );
                        // nombre de partie à charger
                        System.out.println("Nombre de partie à charger : ");
                        input = reader.readLine();
                        int nbPartie = Integer.parseInt( input );
                        // répertoire où sont les données
                        System.out.println("Répertoire où sauver les données : ");
                        String baseRep = reader.readLine();
                        if( baseRep.equals("")) baseRep = new String( "." );
                        // Fichier de log
                        System.out.println("Nom du fichier de log : ");
                        String logFile = reader.readLine();
                        FileWriter myLogFile = new FileWriter( logFile );
                	    BufferedWriter myLogWriter = new BufferedWriter( myLogFile );
                        myLogWriter.write( "Essai de sauvegarder de "+numPartie+" à "+ (numPartie+nbPartie-1));
                        myLogWriter.newLine();
                        
                        // On sauvegarde dans répertoire "game_###_###"
                        String nomRep = new String( baseRep+"/game_"+numPartie+"_"+(numPartie+nbPartie-1));
                        File rep = new File ( nomRep );
                        rep.mkdir();
                        
                        // Pour chacune des parties
                        int indexPartie;
                        String nomPartieStr, numPartieStr;
                        for( int i=0; i<nbPartie; i++ ) {
                            indexPartie = numPartie+i;
                            numPartieStr = df.format( indexPartie );
                            try {
                                loadBAJGame( indexPartie );
                            
                                if( checkGame( false ) == true ) {
                                    try {
                                        String nomR = toLinuxName( zeCoups.nomJoueurRouge );
                                        String nomV = toLinuxName( zeCoups.nomJoueurVert );
                                        nomPartieStr = new String( nomRep+"/game_"+numPartieStr+"_"+nomR+"_vs_"+nomV+".sha");
                                        zeCoups.writeToASCIFile( nomPartieStr );
                                        System.out.println("Sauve ... "+nomPartieStr);
                                    }
                                    catch( Exception e) {
                                        System.out.println( "Partie "+indexPartie+" impossible à charger !!");
                                        myLogWriter.write( "Partie "+indexPartie+" impossible à charger !!");
                                        myLogWriter.newLine();
                                    }
                                }
                                else {
                                    System.out.println( "Partie "+indexPartie+" impossible à interpréter !!");
                                    myLogWriter.write( "Partie "+indexPartie+" impossible à interpréter !!");
                                    myLogWriter.newLine();
                                }
                            }
                            catch( ParseException pe) {
                                System.out.println( "Partie "+indexPartie+" => ParseException : "+pe.getMessage());
                                myLogWriter.write( "Partie "+indexPartie+" => ParseException : "+pe.getMessage());
                                myLogWriter.newLine();
                            }
                        }
                        myLogWriter.close();
                        myLogFile.close();
                    }
//                    else if (choix.startsWith("gl")) {
//                    	System.out.print("Nom du joueur recherché : ");
//            			String nomJoueur = reader.readLine();
//            			
//            			URL searchURL;
//            			searchURL = new URL("http://www.boiteajeux.net/index.php?p=recherche");
//            			URLConnection connexion = searchURL.openConnection();
//            			// Méthode POST
//            			connexion.setUseCaches(false);
//            			connexion.setDefaultUseCaches(false);
//            			connexion.setDoInput(true);
//            			connexion.setDoOutput(true);
//            			connexion.setRequestProperty("Content-Type", "application/octet-stream");
//            			
//            			DataOutputStream connOutput = new DataOutputStream( connexion.getOutputStream());
//            			String data = "pJeu" + URLEncoder.encode("shz","UTF-8") +
//            				          "pLoginJ" + URLEncoder.encode( nomJoueur,"UTF-8" ) +
//            				          "pEtat" + URLEncoder.encode("3","UTF-8");
//            			connOutput.writeBytes( data );
//            			connOutput.
//            			
//            			Trajectoire traj=new Trajectoire(choix);
//            			zeModel.setLenObSeq(traj.etats.length);
//            			zeModel.setObSeq(traj.etats);			
//            			AfficheSequence();
//         
//                    }
                    
                    // ***** Rien de reconnu
                    else {
                        if (choix.equals("")) {
                        } 
                        else {
                            System.out.println("Commande '"+choix+"' non reconnue ! Tapez h pour avoir la liste des commandes");			    
                        }
                    }
                }
            }
            catch( Exception e) {
                if( myLogFile != null ) {
                    try {
                    myLogFile.flush();
                    myLogFile.close();
                    }
                    catch( Exception exc) 
                    {}
                }
                   
                System.out.print("*** ERREUR ***");
                e.printStackTrace();
            }
        }
    }
    /**
     * Charge une partie sur BaJ d'après son numéro.
     * @param numPartie
     * @throws Exception
     */
    private void loadBAJGame( int numPartie )
    throws Exception
    {
        URL bajURL;
        
        bajURL = new URL("http://www.boiteajeux.net/jeux/shz/historique.php?id=" + numPartie );
        System.out.println("Connexion à l'url");
        URLConnection connexion = bajURL.openConnection();
        InputStream connexionStream = connexion.getInputStream();
        
        zeCoups.readFromBajWeb( connexionStream );
    }
    /**
     * Vérifie que la série de coup courante est correctement interprétée
     * par le moteur de jeu.
     * @param verb True si on veut être verbeux.
     * @return true si bien interprété.
     */
    private boolean checkGame( boolean verb )
    {
        try {
            EtatJeu currentEtat;
            // Comme si chacun avait toutes les cartes.
            currentEtat = zeJeu.getInitState(50,14);
            if( verb ) System.out.println( currentEtat.toString());
            zePartie.add( currentEtat);
            
            for (Iterator iterC = zeCoups.listeCoups.iterator(); iterC.hasNext();) {
                Coup element = (Coup) iterC.next();
                if( verb ) System.out.println( "Application de " + element.toString());
                currentEtat = zeJeu.apply( currentEtat, element);
                if( verb ) System.out.println( currentEtat.toString());
                zePartie.add( currentEtat );
             
            }
        }
        catch( GameException ge) {
            return false;
        }
        return true;
    }
    /**
     * Transforme le nom d'un joueur en une version "correcte" pour
     * un nom de fichier.
     * @param name
     * @return un nom "linuxien"
     */
    private String toLinuxName( String name )
    {
        String result = new String( name );
        result = result.replace( " ", "_");
        result = result.replace( "é", "e");
        result = result.replace( "è", "e");
        result = result.replace( "ê", "e");
        result = result.replace( "â", "a");
        result = result.replace( "ô", "o");
        result = result.replace( "ù", "u");
        result = result.replace( "û", "u");
        result = result.replace( "ë", "e");
        return result;
    }
    /**
     * Cycle :
     * <li> lecture d'un Coup au clavier </li>
     * <li> l'état du Jeu avec l'application de ce Coup</li>
     * <li> mise à jour de l'Etat du Jeu</li>
     */
    public void runNewGame()
    {
        CoupFactory coupReader = new CoupFactory();
        EtatJeu avant, apres;
        Coup zeCoup;
        byte buffer[] = new byte[100];
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        String read = "";
        // initialise le jeu
        avant = zeJeu.getInitState(50,5);
        
        System.out.println( "---- AVANT ----------------------");
        System.out.println( avant );
        System.out.println( "---------------------------------");
        
        while( true ) {
            System.out.print("Coup = ");
            try {
                read = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            System.out.println("Coup ="+read+"=");
            zeCoup = coupReader.extractFrom( read );
            
            if( zeCoup == null ) {
                System.out.println( "Coup invalide >> "+read);
            }
            else {
                try {
                    apres = zeJeu.apply( avant, zeCoup );
                    System.out.println( "---- APRES ----------------------");
                    System.out.println( apres );
                    System.out.println( "---------------------------------");
                    zeJeu.update( apres );
                    System.out.println( "---- APRES UPDATE----------------");
                    System.out.println( apres );
                    System.out.println( "---------------------------------");
                    avant = apres;
                } 
                catch (GameException ge) {
                    System.out.println( ge.getMessage());
                }
                
                catch (Exception e) {
                    System.out.println( e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    public static void main(String[] args)
    {
        TestShazamm test = new TestShazamm();
        test.help();
        test.humanInterface();
    }
}
