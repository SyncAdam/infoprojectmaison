package insa.Batiment;
import java.io.Serializable;

import java.util.ArrayList;

public class Piece implements Serializable{

    public int idPiece;
    public ArrayList<Mur> murs;
    public boolean pieceValide;

    //Object oriented programming is overly complicated IMO!

    Piece(int id)
    {
        this.idPiece = id;
        this.murs = new ArrayList<Mur>();

        Mur pm = new Mur(1);
        murs.add(pm);
        int iterator = 2;

        try{
            //Illisible mais comment optimiser mieux?????
            while(!murs.get(0).getDebut().equals(murs.get(murs.size()-1).getFin()))   
            {
                Mur mur = new Mur(iterator, murs.get(murs.size()-1).getFin());
                murs.add(mur);
                iterator++;
            }

            Coin foobar = murs.get(murs.size()-1).getDebut();
            murs.remove(murs.size()-1);
            Mur mur = new Mur(iterator-1, foobar, murs.get(0).getDebut());
            murs.add(mur);
            this.pieceValide = true;
            
            System.out.println("Piece creee");
        }
        catch(RuntimeException e)
        {
            System.out.println("Erreur pendant la creation de votre piece");
        }
        
    }

    public void deleteMur(int idMur)
    {
        this.murs.remove(idMur);
        this.pieceValide = false;
    }

    public boolean pieceIntegre()
    {
        try
        {
            //D'abord parcourir les murs, voir s'ils forment un cycle eulerien
            //L'illisibilité je ne fais pas expres c'est la nature de POO
            if(!this.murs.get(0).getDebut().equals(this.murs.get(this.murs.size()-1).getFin()))
            {
                this.pieceValide = false;
                return false;
            }
            for(int i = 1; i < murs.size()-1; i++)
            {
                if(!this.murs.get(i).getDebut().equals(this.murs.get(i-1).getFin()) || !this.murs.get(i).getFin().equals(this.murs.get(i+1).getDebut()))
                {
                    this.pieceValide = false;
                    return false;
                } 
            }

            //Voir si les murs s'intersectent les un des autres
            for(int i = 0; i < murs.size(); i++)
            {
                for(int j = i+1; j < murs.size()-i; j++)
                {
                    if(Mur.murIntersect(murs.get(i), murs.get(j)))
                    {
                        this.pieceValide = false;
                        return false;
                    }
                }
            }

            //En tout cas le surface ne doit etre inferier a 0
            if(this.surface() <= 0)
            {
                this.pieceValide = false;
                return false;  
            }

            this.pieceValide = true;
            return true;
        }
        catch(RuntimeException e)
        {
            System.out.println("Impossible de verifier l'integrite de votre piece");
        }
        return false;
    }

    public double surface()
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