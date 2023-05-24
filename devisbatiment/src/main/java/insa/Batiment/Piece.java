package insa.Batiment;

import java.io.Serializable;

import java.util.ArrayList;

public class Piece implements Serializable{

    public int idPiece;
    public ArrayList<Mur> murs;
    public ArrayList<Surface> soletplafond;
    public boolean pieceValide;
    public String Sfonction;

    //Object oriented programming is overly complicated IMO!

    /**
     * Constructeur qui permet de creer une piece a partir d'un <code>idPiece</code> en demandant de creer chaque coin, 
     * et placant des murs jusqu'au dernier coin qui est coincidant avec le premier
     * @param id
     */

     

    public Piece(int id)
    {
        this.idPiece = id;
        this.murs = new ArrayList<Mur>();
        this.soletplafond = new ArrayList<Surface>();
        this.pieceValide = false;
        this.Sfonction = "";
        
        Mur pm = new Mur(1);
        murs.add(pm);
        int iterator = 2;

        try{
            //Illisible mais comment optimiser mieux?????
            //On creer des murs jusqu'a ce que ca soit un cycle
            while(!murs.get(0).getDebut().equals(murs.get(murs.size()-1).getFin()))   
            {
                Mur mur = new Mur(iterator, murs.get(murs.size()-1).getFin());
                murs.add(mur);
                iterator++;
            }

            //pour eviter la repetition de premier coin du premier mur et le dernier coin du dernier
            Coin foobar = murs.get(murs.size()-1).getDebut();
            murs.remove(murs.size()-1);
            Mur mur = new Mur(iterator-1, foobar, murs.get(0).getDebut());
            murs.add(mur);
            

            Sol sol = new Sol(murs);
            Plafond plafond = new Plafond(murs);

            this.soletplafond.add((Surface)sol);
            this.soletplafond.add((Surface)plafond);
            
            System.out.println("Piece creee");
        }
        catch(RuntimeException e)
        {
            System.out.println("Erreur pendant la creation de votre piece");
        }
        
    }

    public Piece(int idPiece, ArrayList<Object> objects)
    {
        this.idPiece = idPiece;
        this.murs = new ArrayList<Mur>();
        this.soletplafond = new ArrayList<Surface>();
        this.pieceValide = false;
        this.Sfonction = "";

        if(objects.get(0) instanceof Coin)
        {
            for(int i = 0; i < objects.size(); i++)
            {
                if(i != objects.size()-1)
                {
                    Mur m = new Mur(i, (Coin)objects.get(i), (Coin)objects.get(i+1));
                    m.inARoom = true;
                    murs.add(m);
                }
                else
                {
                    Mur m = new Mur(i, (Coin)objects.get(i), (Coin)objects.get(0));
                    m.inARoom = true;
                    murs.add(m);
                    
                }
            }
        }

        else if(objects.get(0) instanceof Mur)
        {
            for(int i = 0; i < objects.size(); i++)
            {
                murs.add((Mur)objects.get(i));
                murs.get(i).inARoom = true;
            }
        }

        Sol sol = new Sol(murs);
        Plafond plafond = new Plafond(murs);

        this.soletplafond.add((Surface)sol);
        this.soletplafond.add((Surface)plafond);
    }


    /**
     * <p>En faisant appel a cette methode un mur sera effacee</p>
     * 
     * <i>Notez que le liste sera shifte pour remplir le place du mur efface</i>
     * @param idMur - l'identificateur du mur a effacer
     */

    public void deleteMur(int idMur)
    {
        this.murs.remove(idMur);
        this.pieceValide = false;
    }

    public void refreshsoletplafond()
    {
        for(Surface s : soletplafond)
        {
            soletplafond.remove(s);
        }

        Sol sol = new Sol(murs);
        Plafond plafond = new Plafond(murs);

        soletplafond.add(sol);
        soletplafond.add(plafond);
    }

    /**
     * <p>Methode qui verifie si une piece est integre en regardent si elle est fermee ou s'il y a des murs
     * qui s'intersectent</p>
     * @return <code>true</code> si la piece a l'aire integre <code>false</code> sinon
     */

    public boolean pieceIntegre()
    {
        try
        {
            //D'abord parcourir les murs, voir s'ils forment un cycle eulerien
            //L'illisibilité je ne fais pas expres c'est la nature de POO

            /*
            if(!this.murs.get(0).getDebut().equals(this.murs.get(this.murs.size()-1).getFin()) || !this.murs.get(0).getDebut().equals(this.murs.get(this.murs.size()-1).getDebut()))
            {
                this.pieceValide = false;
                return false;
            }
            */

            for(int i = 0; i < murs.size()-1; i++)
            {
                if(i == murs.size()-1)
                {
                    if(!this.murs.get(i).getDebut().equals(this.murs.get(0).getFin()) || !this.murs.get(i).getFin().equals(this.murs.get(0).getDebut()))
                    {
                        this.pieceValide = false;
                        return false;
                    }
                }
                else if(!this.murs.get(i).getDebut().equals(this.murs.get(i+1).getFin()) || !this.murs.get(i).getFin().equals(this.murs.get(i+1).getDebut()))
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

            refreshsoletplafond();

            this.pieceValide = true;
            return true;
        }
        catch(RuntimeException e)
        {
            System.out.println("Impossible de verifier l'integrite de votre piece");
        }
        return false;
    }

    public double calculrevetement(double h)
    {
        double res = 0;
        for(int i = 0; i < murs.size(); i++)
        {
            res += murs.get(i).calculRevetement(h);
        }
        for(int i = 0; i < soletplafond.size(); i++)
        {
            res += soletplafond.get(i).calculRevetement(h);
        }
        return res;
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
    
    public int getPieceId()
    {
        return this.idPiece;
    }
}