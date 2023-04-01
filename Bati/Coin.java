package Bati;
import java.io.Serializable;

public class Coin implements Serializable{
    
    private int idCoin; //identificateur du coin
    private double cx;  //abscisse du coin
    private double cy;  //ordonnee du coin

    //constructeur sans parametre demande des parametres
    Coin(int idCoin)
    {   
        this.idCoin = idCoin;
        System.out.println("Donnez le coordonnée x de votre coin " + this.idCoin);
        this.cx = Lire.d();
        System.out.println("Donnez le coordonnée y de votre coin " + this.idCoin);
        this.cy = Lire.d();
    }

    //constructeur simple, tout est fourni
    Coin(int id, double x, double y)
    {
        this.cx = x;
        this.cy = y;
        this.idCoin = id;
    }

    public double getX()
    {
        return this.cx;
    }

    public double getY()
    {
        return this.cy;
    }

    public void setX(double x)
    {
        this.cx = x;
    }

    public void setY(double y)
    {
        this.cy = y;
    }

    @Override
    public boolean equals(Object obj) throws RuntimeException
    {
        if(obj instanceof Coin)
        {
            Coin c = (Coin) obj;        //casting object to Coin
            if(c.getX() == this.cx && c.getY() == this.cy)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            throw new RuntimeException("Types non compatibles");
        }
    }

    @Override
    public String toString()
    {
        return("\nLe coin " + this.idCoin + " a pour abscisse : " + this.cx + " et ordonnée : " +  this.cy);
    }
    
}
