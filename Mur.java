public class Mur {
    
    //Attributes
    Coin debut;     //Point de départ du mur
    Coin fin;     //Point de fin du mur
    int idMur;

    Mur(int id, Coin c1, Coin c2){
        this.debut = c1;
        this.fin = c2;
        this.idMur = id;
    }

    //Methods
    public String toString(){
        return this.debut + " + " + this.fin + "i";
    }
    double longueur(){
        return debut.distance(fin);
    }
    double surface(){
        return 0;
    }
    void dessiner(){}
    
}
