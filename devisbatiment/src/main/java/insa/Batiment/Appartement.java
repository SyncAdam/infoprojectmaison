package insa.Batiment;
import java.io.Serializable;

import java.util.ArrayList;

public class Appartement implements Serializable{
    
    private int idAppartement;
    Niveau parentNiveau;
    Immeuble parentImmeuble;
    public ArrayList<Piece> pieces;

    public Appartement(int id)
    {
        this.idAppartement = id;
        this.pieces = new ArrayList<>();
        parentNiveau = null;
        parentImmeuble = null;
    }

    public Appartement(int id, ArrayList<Piece> pieces)
    {
        this.idAppartement = id;
        this.pieces = pieces;
        parentNiveau = null;
        parentImmeuble = null;
    }
 
    public void setParentImmeuble(Immeuble I)
    {
        this.parentImmeuble = I;
    }

    public void setParentNiveau(Niveau N)
    {
        this.parentNiveau = N;
    }

    public int getParentNiveauID()
    {
        return this.parentNiveau.getIdNiveau();
    }

    public int getParentImmeubleID() throws NullPointerException
    {
        return this.parentImmeuble.getIdImmeuble();
    }

    public int getIdAppartement()
    {
        return this.idAppartement;
    }

    public void addPiece(Piece p)
    {
        this.pieces.add(p);
    }

    public void removePiece(Piece p)
    {
        this.pieces.remove(p);
    }



    //Á Verifier s'il y a des pieces coincidents
    
}
