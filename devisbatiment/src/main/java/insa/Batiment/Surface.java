package insa.Batiment;

import java.io.Serializable;
import java.util.ArrayList;

import insa.Batiment.Revetements.Revetement;
import insa.Batiment.Revetements.RevetementException;

public abstract class Surface implements Serializable{

    public ArrayList<Revetement> revetements;

    public abstract double surface(double h);
    public abstract void addRevetement(Revetement rev) throws RevetementException;

    public double calculRevetement(double h)
    {
        double res = 0;
        for(int i = 0; i < revetements.size(); i++)
        {
            res += this.surface(h) * revetements.get(i).prixUnitaire;
        }

        return res;
    }
    
}
