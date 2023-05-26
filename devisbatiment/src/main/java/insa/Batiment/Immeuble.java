package insa.Batiment;

import java.util.ArrayList;

import insa.Batiment.Revetements.Revetement;

import java.io.Serializable;
import java.text.DecimalFormat;
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
    public ArrayList<Integer> AllNivIDS;
    public int maxX;
    public int maxY;
    
    public Immeuble(int idImmeuble){

        this.idImmeuble = idImmeuble;
        this.niveau = new ArrayList<Niveau>();
        this.AllNivIDS = new ArrayList<Integer>();
        for(int i = 0; i < this.niveau.size(); i++)
        {
            this.AllNivIDS.add(this.niveau.get(i).getIdNiveau());
        }
        this.maxX = 0;
        this.maxY = 0;
  
    }

    Immeuble(int idImmeuble, ArrayList<Niveau> nivs)
    {
        this.idImmeuble = idImmeuble;
        this.niveau = nivs;
        this.AllNivIDS = new ArrayList<Integer>();
        this.maxX = 0;
        this.maxY = 0;
    }

    public ArrayList<Integer> getAllNivIDS()
    {
        ArrayList<Integer> in = new ArrayList<>();
        for(int i = 0; i < this.niveau.size(); i++)
        {
            in.add(this.niveau.get(i).getIdNiveau());
        }
        return in;
    }

    public static void saveImmeuble(ArrayList<Immeuble> is, String nomFichier) throws IOException, ClassNotFoundException
    {
        FileOutputStream fileOutputStream = new FileOutputStream(nomFichier);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(is);

        System.out.println("Immeuble sauvguarde");
  
        fileOutputStream.close();
        objectOutputStream.close();
    }

    public static ArrayList<Immeuble> loadImmeuble(String nomFichier) throws IOException, ClassNotFoundException
    {
        FileInputStream fileInputStream = new FileInputStream(nomFichier);
        ObjectInputStream ObjectInputStream = new ObjectInputStream(fileInputStream);

        ArrayList<Immeuble> immeubles = (ArrayList<Immeuble>)ObjectInputStream.readObject();
        
        System.out.println("Immeuble charge");
        
        ObjectInputStream.close();
        fileInputStream.close();
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
                    
                    String[] withoutType = reste[2].split(";");
                    String[] pieceIds = withoutType[0].split(",");

                    for(String mystring : pieceIds)
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

                    Appartement myAppartement = new Appartement(Integer.parseInt(reste[0]), pieces, Double.parseDouble(reste[3]), Double.parseDouble(reste[4]));
                    input.add(myAppartement);
                    break;

                case "Niveau":
                    ArrayList<Appartement> apparts = new ArrayList<>();

                    String[] mystrings = reste[1].split(",");
                    for(String mystring : mystrings)
                    {
                        for(int i = 0; i < input.size() ; i++)
                        {
                            if(input.get(i) instanceof Appartement && ((Appartement) input.get(i)).getIdAppartement() == Integer.parseInt(mystring))  //ne devrait pas jeter une exception
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
                            if(input.get(i) instanceof Niveau && ((Niveau) input.get(i)).getIdNiveau() == Integer.parseInt(mystring))  //ne devrait pas jeter une exception
                            {
                                niveaux.add((Niveau) input.get(i));
                                input.remove(i);
                                break;
                            }
                        }        
                    }                    

                    Immeuble imm = new Immeuble(Integer.parseInt(reste[0]), niveaux);
                    for(Niveau niv : imm.niveau)
                    {
                        for(Appartement app : niv.appartements)
                        {
                            app.setParentImmeuble(imm);
                            app.setParentNiveau(niv);
                        }
                        niv.changeParentImmeuble(imm);
                    }

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

    public int getIdImmeuble()
    {
        return this.idImmeuble;
    }

    public void addNiveau(Niveau n)
    {
        this.niveau.add(n);
    }

    public void removeNiveau(Niveau n)
    {
        this.niveau.remove(n);
    }

    public Niveau getNiveau(int ind)
    {
        for(Niveau n : niveau)
        {
            if(n.getIdNiveau() == ind)
            {
                return n;
            }
        }
        return null;
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

    public static ArrayList<String> revetementString(ArrayList<Immeuble> ims)
    {
        ArrayList<String> res = new ArrayList<>();
        ArrayList<Revetement> revs = Revetement.readDef();

        for(Revetement r : revs)
        {
            double cout = 0;
            double surface = 0;
            for(int i = 0; i < ims.size(); i++)
            {
                for(int j = 0; j < ims.get(i).niveau.size(); j++)
                {
                    for(int k = 0; k < ims.get(i).niveau.get(j).appartements.size(); k++)
                    {
                        for(int l = 0; l < ims.get(i).niveau.get(j).appartements.get(k).pieces.size(); l++)
                        {
                            for(int m = 0; m < ims.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.size(); m++)
                            {
                                for(Revetement revetement : ims.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.get(m).revetements)
                                {
                                    if(revetement.getIdRevetement() == r.getIdRevetement())
                                    {
                                        surface += ims.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.get(m).surface(ims.get(i).niveau.get(j).getHeight());
                                        cout += ims.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.get(m).surface(ims.get(i).niveau.get(j).getHeight()) * r.getPrixUnitaire();
                                    }
                                }
                            }
                            for(int m = 0; m < ims.get(i).niveau.get(j).appartements.get(k).pieces.get(l).soletplafond.size(); m++)
                            {
                                for(Revetement revetement : ims.get(i).niveau.get(j).appartements.get(k).pieces.get(l).soletplafond.get(m).revetements)
                                {
                                    if(revetement.getIdRevetement() == r.getIdRevetement())
                                    {
                                        surface += ims.get(i).niveau.get(j).appartements.get(k).pieces.get(l).soletplafond.get(m).surface(ims.get(i).niveau.get(j).getHeight());
                                        cout += ims.get(i).niveau.get(j).appartements.get(k).pieces.get(l).soletplafond.get(m).surface(ims.get(i).niveau.get(j).getHeight()) * r.getPrixUnitaire();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            int decimalPlaces = 2;

            if(cout != 0 && surface != 0)
            {
                res.add(r.getDesignation() + " (" + r.getIdRevetement() + ")");

                DecimalFormat decimalFormat = new DecimalFormat("#0." + "0".repeat(decimalPlaces));
                String s1 = decimalFormat.format(surface);
                res.add(s1);

                DecimalFormat decimalFormat1 = new DecimalFormat("#0." + "0".repeat(decimalPlaces));
                String s2 = decimalFormat1.format(cout);
                res.add(s2);
            }

        }

        return res;
    }

}