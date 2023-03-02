public class Mur {
    
    //Attributes
    Coin d;     //Point de départ du mur
    Coin f;     //Point de fin du mur
    
    Mur(){
        
    }

    //Methods
    double longueur(){
        return d.distance(f);
    }
    double surface(){
        return 0;
    }
    
}
