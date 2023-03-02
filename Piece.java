public class Piece {
    
    Sol sol;
    Plafond plafond;

    Coin[] coins = new Coin[4];   //Coins sont definies de bas-gauche, haut-gauche, haut-droite, bas droite
    Mur[] murs = new Mur[4];     //Murs sont definies des coins 12, 23, 34, 41

    Piece(Coin c1, Coin c2)     //On defini une piece a partir de 2 coins le coin de gauche en bas, et le coin de haut a droite      
    {                             
        this.coins[0] = c1;  //le premier coin est bas-gauche
        this.coins[1] = new Coin(2, c1.cx, c2.cy);
        this.coins[2] = c2;
        this.coins[3] = new Coin(4, c2.cx, c1.cy);

        this.murs[0] = new Mur(0, coins[0], coins[1]);
        this.murs[1] = new Mur(1, coins[1], coins[2]);
        this.murs[2] = new Mur(2, coins[2], coins[3]);
        this.murs[3] = new Mur(3, coins[3], coins[0]);
    };

    double surface()
    {   
        return 0;
    }

    int devisPiece(){
        return 0;
    }

    void dessiner(){

    }
}
