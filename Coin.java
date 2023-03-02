public class Coin {
    
    int idCoin;
    double cx;
    double cy;

    Coin(int id, double x, double y){
        this.cx = x;
        this.cy = y;
        this.idCoin = id;
    }

    double distance(Coin c){
        return Math.sqrt(Math.pow(Math.abs(cx - c.cx), 2) + Math.pow(Math.abs(cy - c.cy), 2));
    };

    public String toString(){
        return("Le coin " + this.idCoin + " a pour abscisse : " + this.cx + " et ordonnée : " +  this.cy);
    }
    
}
