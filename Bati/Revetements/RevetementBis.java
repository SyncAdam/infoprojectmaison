/*
package Bati.Revetements;
import java.io.*;

public class RevetementBis {
int idRevetement;
double prixUnitaire;
String Designation;


RevetementBis(String type, int id, double prix){ //constructeur pour créér un revetement manuellement
    this.idRevetement = id;
    this.prixUnitaire =prix;
    this.Designation = type;

}


void LireCatalogue(){
    BufferedReader catalogue=new BufferedReader(new FileReader("revetement.csv"));
    String lignelue;// Ligne lue depuis le fichier
   while((lignelue=catalogue.readLine())!=null)
   {
   //on découpe la ligne et on fait un new revetement
    RevetementBis test = new RevetementBis("Test", 4, prixUnitaire);
   }
etudiants.close();
    //quand cette méthode est appelée, on va instancier toutes les lignes du catalogue
   
}


double montant(double surface){
 return this.prixUnitaire * surface;
}

}

*/