public class Coin {
    
    int idCoin;
    double x;
    double y;

    double distance(Coin c){
        return Math.sqrt(Math.pow(Math.abs(x - c.x), 2) + Math.pow(Math.abs(y - c.y), 2));
    };

    Coin(int id, int x, int y){
        this.x = x;
        this.y = y;
        this.idCoin = id;
    }

    /*
    void toString(){

    }
    */
}
