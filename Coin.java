public class Coin {
    
    int idCoin; //identificateur du coin
    double cx;  //abscisse du coin
    double cy;  //ordonnee du coin

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

    public String toString()
    {
        return("Le coin " + this.idCoin + " a pour abscisse : " + this.cx + " et ordonnée : " +  this.cy);
    }
    
}
