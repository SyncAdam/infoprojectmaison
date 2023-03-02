public class App {
    public static void main(String[]args)
    {
        Coin c1 = new Coin(0, 0, 0);
        Coin c2 = new Coin(2, 3 ,3);
        Piece mypiece = new Piece(c1, c2);
        System.out.println(mypiece.coins[0].cx);
    }
}
