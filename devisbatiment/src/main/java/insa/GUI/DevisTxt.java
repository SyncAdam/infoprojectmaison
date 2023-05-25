package insa.GUI;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DevisTxt{


    public static void generate(ArrayList<String> liste, double total){
        try{
        BufferedWriter devis=new BufferedWriter(new FileWriter("Devis.txt",false));



        devis.write("              _______________________________________");
        devis.newLine();
        devis.write("             |                                       |");
        devis.newLine();
        devis.write("             |               Devis                   |");
        devis.newLine();
        devis.write("             |_______________________________________|");
        devis.newLine();
        devis.newLine();
        devis.write("Remarque : Les couts sont donnes en euros et les surfaces en m^2");
        devis.newLine();
        devis.write("      ________________________________________________________");
        devis.newLine();
        devis.write("	 |       Revetement          |    Surface     |   cout    |");
        devis.newLine();
        devis.write("	  --------------------------------------------------------");

        /*liste.add("Teste");
        liste.add("coucut");
        liste.add("wh");
        liste.add("Tesdfdfte");
        liste.add("ceofkdoucut");
        liste.add("wshfdf");
        liste.add("Tesfdfte");
        liste.add("codfdfucut");
        liste.add("wezzsh");*/

        if(liste.size() == 0){
            devis.newLine();
            devis.newLine();
            devis.write("Veuillez appliquer des revetements avant de générer le devis !!! ");}
         else{

            for (int i = 0; i < liste.size(); i = i+3) { //tout ce qui suit permet de générer le tableau avec les éléments centrés
                int constante =0;
                devis.newLine();
                devis.write("	 |");
                String Texte = liste.get(i);
                //String Texte = "Test";
                for (int j = 0; j < (28 - Texte.length())/2; j++) {
                    devis.write(" ");
                }
                devis.write(Texte);
                if(Texte.length()%2 == 0){constante = 1; }else{constante =0;}
                for (int j = 0; j < ((28 - Texte.length())/2)-constante; j++) {
                    devis.write(" ");
                }
                devis.write("|");


                Texte = liste.get(i+1);


                //String Texte = "Test";
                for (int j = 0; j < (17 - Texte.length())/2; j++) {
                    devis.write(" ");
                }
                devis.write(Texte);
                if(Texte.length()%2 == 0){constante = 0; }else{constante =1;}
                for (int j = 0; j < ((17 - Texte.length())/2)-constante; j++) {
                    devis.write(" ");
                }
                devis.write("|");

                Texte = liste.get(i+2);
                //String Texte = "Test";
                for (int j = 0; j < (12 - Texte.length())/2; j++) {
                    devis.write(" ");
                }
                devis.write(Texte);
                if(Texte.length()%2 == 0){constante = 1; }else{constante =0;}
                for (int j = 0; j < ((12 - Texte.length())/2)-constante; j++) {
                    devis.write(" ");
                }
                devis.write("|");

                devis.newLine();
                devis.write("	  --------------------------------------------------------");
                
            }
            devis.newLine();
            //devis.write("	 |                            |        Total: |");
            devis.write("Le cout total revient a : " + total + " euros");
        }


            System.out.println("Devis généré");
        devis.close();
        }catch(IOException err){
            System.out.println("Veuillez fermer le devis actuellement ouvert");

        }
    }




    
}