package insa.Batiment.Revetements;

import java.util.ArrayList;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Revetement implements Serializable{

    public int idRevetement;
    public boolean pourMur;
    public boolean pourSol;
    public boolean pourPlafond;
    public String designation;
    public double prixUnitaire;

    public Revetement()
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
            Path path = Paths.get("src", "main", "java", "insa", "Batiment", "Revetements", "revs.txt");

            FileReader fileReader = new FileReader(path.toString());
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

        //This is the stupidest shit i've ever seen

        public int getIdRevetement()
        {
            return this.idRevetement;
        }
        public void setIdRevetement()
        {
    
        }
        public boolean getPourMur()
        {
            return this.pourMur;
        }
        public void setpourMur()
        {
            
        }
        public boolean getPourSol()
        {
            return this.pourSol;
        }
        public void setpourSol()
        {
            
        }
        public boolean getPourPlafond()
        {
            return this.pourPlafond;
        }
        public void setpourPlafond()
        {
            
        }
        public String getDesignation()
        {
            return this.designation;
        }
        public void setdesignation()
        {
            
        }
        public double getPrixUnitaire()
        {
            return this.prixUnitaire;
        }
        public void setprixUnitaire()
        {
            
        }

        public static boolean equals(Revetement r1, Revetement r2)
        {
            if(r1.idRevetement == r2.idRevetement && r1.designation == r2.designation && r1.pourMur == r2.pourMur
             && r1.pourSol == r2.pourSol && r1.pourPlafond == r2.pourPlafond && r1.prixUnitaire == r2.prixUnitaire)
            return true;
            else 
            {
                return false;
            }
        }

 
    
}
// Calculer le montant en fonction de la surface ex: montant(double surface) : double
// mettre surface dans le main
// return prix unit* surface defioni par des murs 
// Générer une exeption pour la peinture au sol 