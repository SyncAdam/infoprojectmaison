package insa.GUI;

public class PieceNonFermeeException extends Exception {
    public PieceNonFermeeException(){
        System.out.println("Pas de pièce fermée detectée");
    }
    
}
