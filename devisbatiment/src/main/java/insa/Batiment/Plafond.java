package insa.Batiment;
import java.io.Serializable;

import java.util.ArrayList;

import insa.Batiment.Revetements.Revetement;

public class Plafond implements Serializable, Surface{

    ArrayList<Mur> murs;
    Revetement revetement;

    //Constructeur
    Plafond(ArrayList<Mur> murs)
    {
        this.murs = murs;
        this.revetement = null;
    }

    public void changeRevetement(Revetement rev)
    {
        if(rev.pourPlafond) this.revetement = rev;
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
