public class Peinture extends Revetement{


    public Peinture (int id, double prix){

    this.idRevetement =id;
    this.prixUnitaire = prix;
    }
    double montant (double surface){
        return this.surface * this.prixUnitaire;
    }
}