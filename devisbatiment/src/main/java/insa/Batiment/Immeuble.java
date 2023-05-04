package insa.Batiment;

import java.util.ArrayList;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Immeuble extends Batiment implements Serializable{

    public int idImmeuble;
    public ArrayList<Niveau> niveau;
    
    public Immeuble(int idImmeuble, int nbrNiveau){

        this.idImmeuble = idImmeuble;
        niveau = new ArrayList<Niveau>();

        for(int i = 0; i < nbrNiveau; i++)
        {
            System.out.println("Donnez le nombre d'appartement pour le niveau " + i);
            int nm = Lire.i();
            Niveau myniv = new Niveau(i, nm);
            niveau.add(myniv);
        }
  
    }

    public void afficher (){

    }

    public static void saveImmeuble(Immeuble I, String nomFichier) throws IOException, ClassNotFoundException
    {
        FileOutputStream fos = new FileOutputStream(nomFichier + ".txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(I);

        System.out.println("Immeuble sauvguarde");
  
        fos.close();
        oos.close();
    }

    public static Immeuble loadImmeuble(String nomFichier) throws IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(nomFichier + ".txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Immeuble b = (Immeuble)ois.readObject(); // down-casting object
  
        System.out.println("Immeuble charge");
        
        ois.close();
        fis.close();
        return b;
    }

    public static ArrayList<Immeuble> importImmeuble(String nomFichier) throws IOException, ClassNotFoundException
    {
        ArrayList<Immeuble> im = new ArrayList<>();
        Immeuble i = new Immeuble(0, 0);
        BufferedReader bure = new BufferedReader(new FileReader(nomFichier));
        String line;

        line = bure.readLine();

        if(line != null)
        {
            ArrayList<Object> input = new ArrayList<>();

            String[] mys = line.split(";", 2);
            String[] reste;

            reste = mys[1].split(";", 0);

            switch(mys[0])
            {
                case "Coin":
                    Coin c = new Coin(Integer.parseInt(reste[0]), Double.parseDouble(reste[1]), Double.parseDouble(reste[2]));
                    input.add(c);
                    break;
                case "Piece":
                    Piece p = new Piece(Integer.parseInt(reste[0]));
            }   

        }

        

        return im;
    }

}