package insa.Batiment;
import java.io.Serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


public class Immeuble extends Batiment implements Serializable{

    public int idImmeuble;
    public Niveau[] niveau;
    
    public Immeuble(int idImmeuble, int nbrNiveau){

        this.idImmeuble = idImmeuble;
        Niveau[] niveau = new Niveau[nbrNiveau];
        for(int i = 0; i < nbrNiveau; i++)
        {
            System.out.println("Donnez le nombre d'appartement pour le niveau " + i);
            int nm = Lire.i();
            niveau[i] = new Niveau(i, nm);
        }
        this.niveau = niveau;
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

}
