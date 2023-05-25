package insa.Batiment;
import java.io.Serializable;
import java.util.ArrayList;

public class Niveau implements Serializable{
    
    public int idNiveau;
    public ArrayList<Appartement> appartements;
    public double height;
    public Immeuble parentImmeuble;

    public Niveau(int id, double h)
    {
        this.idNiveau = id;
        appartements = new ArrayList<Appartement>();
        this.height = h;
        this.parentImmeuble = null;
    }

    public Niveau(int id, double h, Immeuble I)
    {
        this.idNiveau = id;
        appartements = new ArrayList<Appartement>();
        this.height = h;
        this.parentImmeuble = I;
    }

    Niveau(int id, ArrayList<Appartement> apparts)
    {
        this.idNiveau = id;
        this.appartements = apparts;
        this.height = 2.8d;
    }

    public int getParentImmeubleID() throws NullPointerException
    {
        return this.parentImmeuble.getIdImmeuble();
    }

    public Immeuble getParentImmeuble() throws NullPointerException
    {
        return this.parentImmeuble;
    }

    public void changeParentImmeuble(Immeuble I)
    {
        this.parentImmeuble = I;
    }

    public Appartement getAppartement(int ind)
    {
        for(Appartement n : appartements)
        {
            if(n.getIdAppartement() == ind)
            {
                return n;
            }
        }
        return null;
    }

    public void addAppartement(Appartement A)
    {
        this.appartements.add(A);
    }

    public void removeAppartement(Appartement A)
    {
        this.appartements.remove(A);
    }


    public int getIdNiveau()
    {
        return this.idNiveau;
    }

    public double getHeight()
    {
        return this.height;
    }

    public void setHeight(double g)
    {
        this.height = g;
    }

    //A verifier si les appartements coincident

}
