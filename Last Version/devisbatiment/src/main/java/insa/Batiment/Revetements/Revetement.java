package insa.Batiment.Revetements;

import java.util.ArrayList;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Revetement { // cxlasse abstraite avec les diff attributs + methodes abstraites montant

    int idRevetement;
    boolean pourMur;
    boolean pourSol;
    boolean pourPlafond;
    String designation;
    double prixUnitaire;

    Revetement()
    {
    }

    Revetement(int id, String des, boolean mur, boolean sol, boolean plafond, double prix)
    {
        this.idRevetement = id;
        this.designation = des;
        this.pourMur = mur;
        this.pourSol = sol;
        this.pourPlafond = plafond;
        this.prixUnitaire = prix;
    }

    public static double montant(Revetement R, double s)
    {
        return R.prixUnitaire * s;
    }

    public static ArrayList<Revetement> readDef()
    {
        ArrayList<Revetement> rev = new ArrayList<Revetement>();

        try {

            FileReader fileReader = new FileReader("Bati/Revetements/revs.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            

            line = bufferedReader.readLine();

            while (line != null) {

                String[] mys = line.split(";");
                Revetement myRevetement = new Revetement();

                try{

                    myRevetement.idRevetement = Integer.parseInt(mys[0]);
                    myRevetement.designation = mys[1];
                    myRevetement.pourMur = (mys[2].equals("1") ? true : false );
                    myRevetement.pourSol = (mys[3].equals("1") ? true : false );
                    myRevetement.pourPlafond = (mys[4].equals("1") ? true : false );
                    myRevetement.prixUnitaire = Double.parseDouble(mys[5]);

                    rev.add(myRevetement);

                }
                catch(NumberFormatException e)
                {
                }
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            fileReader.close();

        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        return rev;
       
    }

    @Override
    public String toString()
    {
        return "\nRevetement " + this.idRevetement + " " + this.designation + " a un prix " + this.prixUnitaire;
    }


    
}
// Calculer le montant en fonction de la surface ex: montant(double surface) : double
// mettre surface dans le main
// return prix unit* surface defioni par des murs 
// Générer une exeption pour la peinture au sol 