package insa.Batiment;
import java.io.Serializable;
import java.util.ArrayList;

public class Niveau implements Serializable{
    
    public int idNiveau;
    public ArrayList<Appartement> appartements;
    public double height;

    Niveau(int id, int nbreApps)
    {
        this.idNiveau = id;
        appartements = new ArrayList<Appartement>(nbreApps);

        Appartement foobar;

        for(int i = 0; i < nbreApps; i++)
        {
            foobar = new Appartement(i, idNiveau);
            appartements.add(foobar);
        }

    }

    Niveau(int id, ArrayList<Appartement> apparts)
    {
        this.idNiveau = id;
        this.appartements = apparts;
    }

    public int getNivId()
    {
        return this.idNiveau;
    }

    public double getHeight()
    {
        return this.height;
    }

    //A verifier si les appartements coincident

}
