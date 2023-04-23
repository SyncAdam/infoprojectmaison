package insa.Batiment;
import java.io.Serializable;

import java.util.ArrayList;

import insa.Batiment.Revetements.Revetement;

public class Sol implements Serializable, Surface {

    public ArrayList<Mur> murs;
    Revetement revetement;

    //Constructeur
    Sol(ArrayList<Mur> murs)
    {
        this.murs = murs;
        this.revetement = null;
    }

    public void changeRevetement(Revetement rev)
    {
        if(rev.pourSol) this.revetement = rev;
    }

    //Répétition du code a cause du modele
    //pourquoi pas mettre tout ces infos dans piece???
    //+probleme si les murs changent
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


