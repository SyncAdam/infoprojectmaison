package insa.Batiment;

import java.util.ArrayList;

import insa.Batiment.Revetements.Revetement;
import insa.Batiment.Revetements.RevetementException;

public class Sol extends Surface {

    public ArrayList<Mur> murs;

    //Constructeur
    Sol(ArrayList<Mur> murs)
    {
        this.murs = murs;
        this.revetements = new ArrayList<Revetement>();
    }

    public void addRevetement(Revetement rev) throws RevetementException
    {
        if(rev.pourSol) this.revetements.add(rev);
        else
        {
            throw new RevetementException();
        }
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
        return r/10000;
    }

}


