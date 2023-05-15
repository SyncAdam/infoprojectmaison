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
    
    Immeuble(int idImmeuble, int nbrNiveau){

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

    Immeuble(int idImmeuble, ArrayList<Niveau> nivs)
    {
        this.idImmeuble = idImmeuble;
        this.niveau = nivs;
    }

    public void afficher (){

    }

    public static void saveImmeuble(ArrayList<Immeuble> is, String nomFichier) throws IOException, ClassNotFoundException
    {
        FileOutputStream fos = new FileOutputStream(nomFichier);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(is);

        System.out.println("Immeuble sauvguarde");
  
        fos.close();
        oos.close();
    }

    public static ArrayList<Immeuble> loadImmeuble(String nomFichier) throws IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(nomFichier);
        ObjectInputStream ois = new ObjectInputStream(fis);

        ArrayList<Immeuble> immeubles = (ArrayList<Immeuble>)ois.readObject();
        
        System.out.println("Immeuble charge");
        
        ois.close();
        fis.close();
        return immeubles;
    }

    public static ArrayList<Immeuble> importImmeuble(String nomFichier) throws IOException, ClassNotFoundException
    {
        ArrayList<Immeuble> im = new ArrayList<>();
        BufferedReader bure = new BufferedReader(new FileReader(nomFichier));
        String line;

        ArrayList<Object> input = new ArrayList<>();

        line = bure.readLine();

        while(line != null)
        {

            String[] mys = line.split(";", 2);
            String[] reste;

            reste = mys[1].split(";");

            switch(mys[0])
            {
                case "Coin":
                    Coin c = new Coin(Integer.parseInt(reste[0]), Double.parseDouble(reste[1]), Double.parseDouble(reste[2]));
                    input.add(c);
                    break;

                case "Piece":
                    ArrayList<Object> coins = new ArrayList<>();

                    String[] splitted = reste[1].split(",");
                    for(String mystring : splitted)
                    {
                        for(int i = 0; i < input.size() ; i++)
                        {
                            if(input.get(i) instanceof Coin && ((Coin) input.get(i)).getId() == Integer.parseInt(mystring))  //ne devrait pas jeter une exception
                            {
                                coins.add(input.get(i));
                                input.remove(i);
                                break;
                            }
                        }        
                    }        

                    Piece p = new Piece(Integer.parseInt(reste[0]), coins);
                    input.add(p);
                    break;

                case "Revetement":
                    //A faire
                    break;

                case "Appartement":
                    ArrayList<Piece> pieces = new ArrayList<Piece>();
                        
                    String[] strings = reste[2].split(",");
                    for(String mystring : strings)
                    {
                        for(int i = 0; i < input.size() ; i++)
                        {
                            if(input.get(i) instanceof Piece && ((Piece) input.get(i)).getPieceId() == Integer.parseInt(mystring))  //ne devrait pas jeter une exception
                            {
                                pieces.add((Piece) input.get(i));
                                input.remove(i);
                                break;
                            }
                        }        
                    }

                    Appartement myAppartement = new Appartement(Integer.parseInt(reste[0]), Integer.parseInt(reste[1]), pieces);
                    input.add(myAppartement);
                    break;

                case "Niveau":
                    ArrayList<Appartement> apparts = new ArrayList<>();

                    String[] mystrings = reste[1].split(",");
                    for(String mystring : mystrings)
                    {
                        for(int i = 0; i < input.size() ; i++)
                        {
                            if(input.get(i) instanceof Appartement && ((Appartement) input.get(i)).getAppartId() == Integer.parseInt(mystring))  //ne devrait pas jeter une exception
                            {
                                apparts.add((Appartement) input.get(i));
                                input.remove(i);
                                break;
                            }
                        } 
                    }       

                    Niveau n = new Niveau(Integer.parseInt(reste[0]), apparts);
                    input.add(n);
                    break;

                case "Immeuble":
                    ArrayList<Niveau> niveaux = new ArrayList<Niveau>();

                    String[] mystrs = reste[1].split(",");
                    for(String mystring : mystrs)
                    {
                        for(int i = 0; i < input.size() ; i++)
                        {
                            if(input.get(i) instanceof Niveau && ((Niveau) input.get(i)).getNivId() == Integer.parseInt(mystring))  //ne devrait pas jeter une exception
                            {
                                niveaux.add((Niveau) input.get(i));
                                input.remove(i);
                                break;
                            }
                        }        
                    }                    

                    Immeuble imm = new Immeuble(Integer.parseInt(reste[0]), niveaux);
                    im.add(imm);
                    break;

                default:
                    break;
            }
            line = bure.readLine();

        }

        System.out.println("Immeubles chargees avec succes");

        bure.close();
        return im;
    }

    public static void exportImmeubles(ArrayList<Immeuble> immeubles, String path) throws IOException, ClassNotFoundException
    {
        //a faire
    }

    public static double calculRevetement(Immeuble immeuble)
    {

        double res = 0;

        for(int i = 0; i < immeuble.niveau.size(); i++)
        {
            for(int j = 0; j < immeuble.niveau.get(i).appartements.size(); j++)
            {
                for(int k = 0; k < immeuble.niveau.get(i).appartements.get(j).pieces.size(); k++)
                {
                    res += immeuble.niveau.get(i).appartements.get(j).pieces.get(k).calculrevetement(immeuble.niveau.get(i).getHeight());
                }
            }
        }
        
        return res;
    }

}