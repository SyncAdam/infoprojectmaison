public abstract class Revetement { // cxlasse abstraite avec les diff attributs + methodes abstraites montant 
    double surface;
    int idRevetement;
    boolean pourMur;
    boolean pourSol;
    boolean pourPlafond;
    String designation;
    double prixUnitaire;

    abstract double montant(double surface);
    
}
// Calculer le montant en fonction de la surface ex: montant(double surface) : double
// mettre surface dans le main
// return prix unit* surface defioni par des murs 
// Générer une exeption pour la peinture au sol 
