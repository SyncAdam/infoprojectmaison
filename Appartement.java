import java.io.Serializable;

import java.util.ArrayList;

public class Appartement implements Serializable{
    
    private int idAppartement;
    private int niveauAppartement;
    ArrayList<Piece> pieces;

    Appartement(int id, int niv)
    {
        this.idAppartement = id;
        this.niveauAppartement = niv;

        System.out.println("================================================");
        System.out.println("Combien de pieces voulez vous pour l'appartement " + this.idAppartement);

        int n = Lire.i();
        while(n <= 0)
        {
            System.out.println("Nombre de pieces inacceptable pour l'appartement " + (this.idAppartement));
            n = Lire.i();
        }
        pieces = new ArrayList<Piece>(n);

        for(int i = 0; i < n; i++)
        {
            Piece p = new Piece(i+1);
            pieces.add(p);
        }

        System.out.println("Appartement " + this.idAppartement + " creee");
        
    }

    //Á Verifier s'il y a des pieces coincidents
    
}
