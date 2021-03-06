/* Generated By:JavaCC: Do not edit this line. BAJParser.java */
/**
 * AVERTISSEMENT : Ces fichiers sont g�n�r�s de mani�re automatique par JavaCC
 * � partir du fichier BAJParser.jj
 */

package jeu.shazamm.parser;

import java.util.*;
import jeu.shazamm.coup.*;
import jeu.shazamm.core.*;

public class BAJParser implements BAJParserConstants {

  //public  ArrayList historique;
  public  HistoriqueCoup historique;
  public  ArrayList listePartie;

  int numCloneur; // joueur qui vient de jouer clone.
  int numVoleur; // joueur qui vient de jouer larcin.
  int numRecycleur; // joueur qui vient de jouer recyclage.

  /**
   * Initialisation
   */
  public void init()
  {
     //historique = new ArrayList();
     historique = new HistoriqueCoup();
     listePartie = new ArrayList();

     numCloneur = Constantes.NO_COLOR;
     numVoleur = Constantes.NO_COLOR;
     numRecycleur = Constantes.NO_COLOR;
  }

// Un fichier est une suite de 'coup'
  final public void Input() throws ParseException {
                init();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case kwCoupManche:
      case kwCoupTour:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      coup();
    }
    jj_consume_token(0);
  }

// Un coup en entier
// MANCHE , TOUR, MISE
  final public void coup() throws ParseException {
          int manche;
          int tour;
    manche = getMancheTour();
    tour = getMancheTour();
    System.out.println( "Reconnu Manche = "+manche+ ", Tour = "+tour);
    getMise();
  }

// Quel est No de la MANCHE ou du TOUR
  final public int getMancheTour() throws ParseException {
        Token num;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case kwCoupManche:
      jj_consume_token(kwCoupManche);
      break;
    case kwCoupTour:
      jj_consume_token(kwCoupTour);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    num = jj_consume_token(NUM);
           // DEBUG System.out.println( "Num vaut "+num.image.toString() );
           {if (true) return Integer.parseInt( num.image.toString() );}
    throw new Error("Missing return statement in function");
  }

// mise des joueurs
// BILAN DES MISES, id_joueur1 (R), id_joueur2 (V), END_TR, nb_pts1, nb_pts2, (sorts)*
//
  final public void getMise() throws ParseException {
        Token id1, id2, miseT, sortT, sortC, sortV, recT, idC, idV, idR;
        String joueur1, joueur2, miseStr, sortStr, sortCStr, sortVStr, recTStr;
        int mise1, mise2, nbRec;
        ArrayList sorts1, sorts2;
        Coup coup1, coup2;

        sorts1 = new ArrayList();
        sorts2 = new ArrayList();

        coup1 = null;
        coup2 = null;

        numCloneur = Constantes.NO_COLOR;
    jj_consume_token(kwBilanMise);
    id1 = jj_consume_token(idJoueur);
        // joueur1 est rouge
        joueur1 = id1.image.toString();
        joueur1 = joueur1.substring( 1, joueur1.length()-1 );
        System.out.println( "Joueur1 = " + joueur1 );
        historique.nomJoueurRouge = joueur1;
    id2 = jj_consume_token(idJoueur);
        // joueur2 est vert
        joueur2 = id2.image.toString();
        joueur2 = joueur2.substring( 1, joueur2.length()-1 );
        System.out.println( "Joueur2 = " + joueur2 );
        historique.nomJoueurVert = joueur2;
    jj_consume_token(END_TR);
    jj_consume_token(kwMise);
    miseT = jj_consume_token(idMise);
       miseStr =  miseT.image.toString();
       miseStr = miseStr.substring( 1, miseStr.length()-1 );
       mise1 = Integer.parseInt( miseStr.trim());
       System.out.println( joueur1 + " -> " + mise1 + " pts"  );
    miseT = jj_consume_token(idMise);
       miseStr =  miseT.image.toString();
       miseStr = miseStr.substring( 1, miseStr.length()-1 );
       mise2 = Integer.parseInt( miseStr.trim());
       System.out.println( joueur2 + " -> " + mise2 + " pts"  );
    jj_consume_token(END_TR);
    jj_consume_token(kwSorts);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case idSort:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      sortT = jj_consume_token(idSort);
         sortStr = sortT.image.toString();
         int numSort = Integer.parseInt( sortStr.substring(0,2).trim() );
         int numJoueur = (sortStr.charAt(2) == 'r' ? 1 : 2);
         System.out.println( "Joueur"+numJoueur+" : sort_"+numSort );

         if( numJoueur == 1 ) {
            sorts1.add( new Integer( numSort ));
         }
         else {
            sorts2.add( new Integer( numSort ));
         }
        if( numSort == 2 ) { // clone
            numCloneur = numJoueur;
        }
        if( numSort == 3 ) { // larcin
            numVoleur = numJoueur;
        }
        if( numSort == 6 ) { // recyclage
            numRecycleur = numJoueur;
        }
    }
    jj_consume_token(END_TR);
     coup1 = new MiseC( Constantes.ROUGE, mise1, sorts1 );
     coup2 = new MiseC( Constantes.VERT, mise2, sorts2 );
     System.out.println( "Coup1 = " + coup1.toString() );
     System.out.println( "Coup2 = " + coup2.toString() );

     historique.add( coup1 );
     historique.add( coup2 );

     sorts1.clear();
     sorts2.clear();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case kwClonage:
      jj_consume_token(kwClonage);
      sortC = jj_consume_token(idClone);
        sortCStr = sortC.image.toString();
        // cherche si un sort est clon�
        if( sortCStr.length() > 5 ) {
          int numSort = Integer.parseInt( sortCStr.substring(3, sortCStr.indexOf("-")-1).trim());
          System.out.println( "Le sort -"+numSort+"- est clon\u00e9 par "+numCloneur );
          if( numSort == 3 ) numVoleur = numCloneur;
          if( numSort == 6 ) numRecycleur = numCloneur;
          if (numCloneur == 1) {
             sorts1.add( new Integer( numSort ));
          }
          else {
             sorts2.add( new Integer( numSort ));
          }
        }
        else {
          System.out.println( "Aucun sort n'est clon\u00e9!");
        }
      jj_consume_token(END_TR);
     if (numCloneur == 1) {
       coup1 = new CloneC( Constantes.ROUGE, sorts1);
       System.out.println( "Coup1 = " + coup1.toString() );
       historique.add( coup1 );
     }
     if (numCloneur == 2) {
       coup2 = new CloneC( Constantes.VERT, sorts2);
       System.out.println( "Coup2 = " + coup2.toString() );
       historique.add( coup2 );
     }
     sorts1.clear();
     sorts2.clear();
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case kwDestruction:
    case kwVol:
    case END_TR:
    case kwmVol:
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case kwDestruction:
        case kwVol:
        case kwmVol:
          ;
          break;
        default:
          jj_la1[4] = jj_gen;
          break label_3;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case kwDestruction:
          jj_consume_token(kwDestruction);
          sortT = jj_consume_token(idVol);
          sortVStr = sortT.image.toString();
          System.out.println( "lu pour num dest = |"+sortVStr+"|");
          String[] listDest = sortVStr.split("([,< ])+");
          for( int nbVol = 0; nbVol < listDest.length; nbVol++ ) {
            if( !listDest[nbVol].equals("") ) {
              //int numDest = Integer.parseInt( sortVStr.substring(0, sortVStr.length()-1  ).trim());
              int numDest = Integer.parseInt( listDest[nbVol] );
              System.out.println( "Le sort -"+numDest+"- est d\u00e9truit");
            }
          }
          break;
        case kwVol:
        case kwmVol:
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case kwVol:
            jj_consume_token(kwVol);
            break;
          case kwmVol:
            jj_consume_token(kwmVol);
            break;
          default:
            jj_la1[5] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          //(<kwVol>
                sortT = jj_consume_token(idVol);
          sortVStr = sortT.image.toString();
          System.out.println( "lu pour num vol = |"+sortVStr+"|");
          String[] listVol = sortVStr.split("([,< ])+");
          for( int nbVol = 0; nbVol < listVol.length; nbVol++ ) {
             System.out.println("strvol =|"+listVol[nbVol]+"|");
             //int numVol = Integer.parseInt(  sortVStr.substring(0, sortVStr.length()-1  ).trim());
             if( !listVol[nbVol].equals("") ) {
               int numVol = Integer.parseInt( listVol[nbVol] );
                   System.out.println( "Le sort -"+numVol+"- est vol\u00e9 par "+numVoleur);
                   //if (numVol == 2) {
                      // il faut changer le num�ro du cloneur
                   //   numCloneur = numVoleur;
                   //}
                   if (numVol == 6) {
                  // il faut changer le numRecycleur
                  numRecycleur = numVoleur;
               }
               if (numVoleur == 1) {
                  sorts1.add( new Integer( numVol ));
               }
               else {
                  sorts2.add( new Integer( numVol ));
                   }
                 }
              }
          break;
        default:
          jj_la1[6] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      jj_consume_token(END_TR);
     if (numVoleur == 1) {
       coup1 = new LarcinC( Constantes.ROUGE, sorts1);
       System.out.println( "Coup1 = " + coup1.toString() );
       historique.add( coup1 );
     }
     if (numVoleur == 2) {
       coup2 = new LarcinC( Constantes.VERT, sorts2);
       System.out.println( "Coup2 = " + coup2.toString() );
       historique.add( coup2 );
     }
     sorts1.clear();
     sorts2.clear();
      break;
    default:
      jj_la1[7] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case kwRecyclage:
      jj_consume_token(kwRecyclage);
      recT = jj_consume_token(idRec);
        recTStr = recT.image.toString();
        nbRec= Integer.parseInt( recTStr.substring(0, recTStr.indexOf("p")-1).trim() );
        System.out.println( "Recyclage de "+nbRec+" pts par "+numRecycleur );
      jj_consume_token(END_TR);
     if (numRecycleur == 1) {
       coup1 = new RecyclageC( Constantes.ROUGE, nbRec);
       System.out.println( "Coup1 = " + coup1.toString() );
       historique.add( coup1 );
     }
     if (numRecycleur == 2) {
       coup2 = new RecyclageC( Constantes.VERT, nbRec);
       System.out.println( "Coup2 = " + coup2.toString() );
       historique.add( coup2 );
     }
      break;
    default:
      jj_la1[8] = jj_gen;
      ;
    }
  }

// Une recherche est une suite de parties...
  final public void rechercheListe() throws ParseException {
           init();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case kwLienPartie:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_4;
      }
      partie();
    }
    jj_consume_token(kwDernierResultats);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case kwRefPartie:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_5;
      }
      jj_consume_token(kwRefPartie);
      jj_consume_token(NUM);
    }
    jj_consume_token(0);
  }

// Liens vers une partie
// 2 liens (un pour le num�ro, un pour le nom)
  final public int partie() throws ParseException {
          Token num;
          int idPartie;
    jj_consume_token(kwLienPartie);
    jj_consume_token(kwRefPartie);
    jj_consume_token(NUM);
    jj_consume_token(kwLienPartie);
    jj_consume_token(kwRefPartie);
    num = jj_consume_token(NUM);
           // DEBUG System.out.println( "Num vaut "+num.image.toString() );
           idPartie = Integer.parseInt( num.image.toString() );
           listePartie.add( new Integer( idPartie ));
           System.out.println("Partie no = "+idPartie);
           {if (true) return idPartie;}
    throw new Error("Missing return statement in function");
  }

  public BAJParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[11];
  static private int[] jj_la1_0;
  static {
      jj_la1_0();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x60,0x60,0x200000,0x400,0x4001800,0x4001000,0x4001800,0x6001800,0x2000,0x4000,0x8000,};
   }

  public BAJParser(java.io.InputStream stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new BAJParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.InputStream stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  public BAJParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new BAJParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  public BAJParser(BAJParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  public void ReInit(BAJParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[29];
    for (int i = 0; i < 29; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 11; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 29; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

//  public static void main(String args[]) throws ParseException {
//    BAJParser parser = new BAJParser(System.in);
//    parser.init();
//    parser.Input();
//  }

}
