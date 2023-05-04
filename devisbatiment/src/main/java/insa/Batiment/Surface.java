package insa.Batiment;

import insa.Batiment.Revetements.Revetement;

public interface Surface {

    abstract double surface(double h);
    abstract void changeRevetement(Revetement rev);
    
}
