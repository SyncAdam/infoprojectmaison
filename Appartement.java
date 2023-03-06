public class Appartement {
    
    int idAppartement;  //nombre identifiant l'appartement
    int nbrePieces;     //nombre de pieces de l'appartement
    Piece[] pieces;     //les pieces

    //constructeur avec identifiant et nombre de pieces fournit
    Appartement(int id, int nbrePieces)
    {
        this.idAppartement = id;
        this.nbrePieces = nbrePieces;

        this.pieces = new Piece[this.nbrePieces];

        //vue qu'on a pas les pieces pour cette constructeur, on cree des coins pour definir les pieces
        Coin[] coins= new Coin[this.nbrePieces * 2];    //selon le constructeur des pieces on a besoin de 2 coins par pieces pour les definir
        for(int i = 0; i < nbrePieces*2; i++)
        {
            coins[i] = new Coin(i);
        }

        //prenant chaque coin, on defini des pieces, on les associe a l'objet en creation
        for(int i = 0; i < nbrePieces; i++)
        {
            this.pieces[i] = new Piece(coins[i*2], coins[(i*2)+1]); 
        }
    }

    public String toString(){
        return "L'appartement " + this.idAppartement + " a " + this.nbrePieces + " pieces";
    }

}