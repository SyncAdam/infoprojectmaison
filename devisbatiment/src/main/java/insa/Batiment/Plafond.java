package insa.Batiment;
import java.io.Serializable;

import java.util.ArrayList;

import insa.Batiment.Revetements.Revetement;
import insa.Batiment.Revetements.RevetementException;

public class Plafond extends Surface implements Serializable{

    ArrayList<Mur> murs;

    //Constructeur
    Plafond(ArrayList<Mur> murs)
    {
        this.murs = murs;
        this.revetements = new ArrayList<Revetement>();
    }

    public void addRevetement(Revetement rev) throws RevetementException
    {
        if(rev.pourPlafond) this.revetements.add(rev);
        else
        {
            throw new RevetementException();
        }
    }

    public double surface(double h)
    {
        double r = 0;
        double dx;
        double dy;
        for(int i = 0; i < this.murs.size(); i++)
        {
            dx = this.murs.get(i).getFin().getX() - this.murs.get(i).getDebut().getX();
            dy = (this.murs.get(i).getFin().getY() + this.murs.get(i).getDebut().getY())/2;
            r += dx*dy;
        }
        return r;
    }

}
