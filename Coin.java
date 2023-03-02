public class Coin {
    
    int idCoin;
    double cx;
    double cy;

    double distance(Coin c){
        return Math.sqrt(Math.pow(Math.abs(cx - c.cx), 2) + Math.pow(Math.abs(cy - c.cy), 2));
    };

    Coin(int id, double x, double y){
        this.cx = x;
        this.cy = y;
        this.idCoin = id;
    }

    /*
    void toString(){

    }
    */
}
