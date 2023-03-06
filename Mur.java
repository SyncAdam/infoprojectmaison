public class Mur {
    
    //Attributes
    Coin debut;     //Point de départ du mur
    Coin fin;     //Point de fin du mur
    int idMur;
    double hauteur = 2.3;

    Mur(int id, Coin c1, Coin c2){
        this.debut = c1;
        this.fin = c2;
        this.idMur = id;
    }

    //Methods
    public String toString()
    {
        return this.debut + " + " + this.fin + "i";
    }
    double longueur()
    {
        return Math.sqrt(Math.pow(Math.abs(this.debut.cx - this.fin.cx), 2) + Math.pow(Math.abs(this.debut.cy - this.fin.cy), 2));
    }
    double surface()
    {
        return longueur() *hauteur;
    }
    void dessiner()
    {}
    
}
