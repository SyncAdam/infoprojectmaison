package insa.Batiment;

import java.io.Serializable;
import java.util.ArrayList;

import insa.Batiment.Revetements.Revetement;
import insa.Batiment.Revetements.RevetementException;

public abstract class Surface implements Serializable{

    public ArrayList<Revetement> revetements;

    public abstract double surface(double h);
    public abstract void addRevetement(Revetement rev) throws RevetementException;
    
}
