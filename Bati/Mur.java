package Bati;
import java.io.Serializable;

public class Mur implements Serializable{
    
    //Attributes
    private Coin debut;     //Point de départ du mur
    private Coin fin;     //Point de fin du mur
    private int idMur;
    private int nmbrPortes;
    private int nmbrFenetres;
    //List revetments

    //Constructors

    Mur(int id, Coin c1, Coin c2)
    {
        this.debut = c1;
        this.fin = c2;
        this.idMur = id;
        this.nmbrFenetres = 0;
        this.nmbrPortes = 0;
    }

    Mur(int id, Coin c1)
    {
        this.debut = c1;
        Coin fin = new Coin(id+1);
        this.fin = fin;
        this.idMur = id;
        this.nmbrFenetres = 0;
        this.nmbrPortes = 0;
    }

    Mur(int id)
    {
        Coin debut = new Coin((id*2)-1);
        Coin fin = new Coin(id*2);

        this.debut = debut;
        this.fin = fin;
        this.idMur = id;
        this.nmbrFenetres = 0;
        this.nmbrPortes = 0;

    }
    
    //Methods

    public static boolean murIntersect(Mur a, Mur b)
    {
        double x = (b.ordonneeintercept() - a.ordonneeintercept()) / (a.pente() - b.pente());
        if((x > a.getDebut().getX() && x < a.getFin().getX()) || (x > a.getFin().getX() && x < a.getDebut().getX()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public double pente()
    {
        return (this.getFin().getY() - this.getDebut().getY()) / (this.getFin().getX() - this.getDebut().getX());
    }

    public double ordonneeintercept()
    {
        return this.getDebut().getY() - this.pente() * this.getDebut().getX();
    }

    public void addFenetres(int n)
    {
        this.nmbrFenetres += n;
    }

    public void addPortes(int n)
    {
        this.nmbrPortes += n;
    }

    public String toString()
    {
        return "\nLe mur " + this.idMur + " a un coin pour debut en:\n\t" + this.debut.getX() + "x " + this.debut.getY() + "y"
             +  "\net pour fin:\n\t" + this.fin.getX() + "x " + this.fin.getY() + "y\n";
    }

    public double longueur()
    {
        return Math.sqrt(Math.pow(Math.abs(this.debut.getX() - this.fin.getX()), 2) + Math.pow(Math.abs(this.debut.getY() - this.fin.getY()), 2));
    }

    public double surface(int h)
    {
        return this.longueur() * h;
    }

    void dessiner()
    {}

    //setters getters

    public Coin getDebut()
    {
        return this.debut;
    }

    public Coin getFin()
    {
        return this.fin;
    }

    public int getNFenetres()
    {
        return this.nmbrFenetres;
    }

    public int getNPortes()
    {
        return this.nmbrPortes;
    }

    public void setDebut(Coin d)
    {
        this.debut = d;
    }

    public void setFin(Coin d)
    {
        this.fin = d;
    }

    public int getID()
    {
        return this.idMur;
    }    
}
