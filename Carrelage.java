public class Carrelage extends Revetement{



    public Carrelage (int id, double prix){

    this.idRevetement =id;
    this.prixUnitaire = prix;
    }
    double montant (double surface){
        return this.surface * this.prixUnitaire;
    }
}