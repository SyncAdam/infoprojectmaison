package insa.Batiment;


import insa.GUI.App;
//import javafx.scene.shape.Polygon;

public class Porte extends App {

    //private Mur mur = new Mur(5,new Coin(1, 25.0, 28.0) , new Coin(1, 29.0, 2.0));
    public int idPorte;
    public Mur mur;
    //double l, L; //pas utiles, juste plus court pour les formules trigo d'après
    public Porte(int idPorte, Mur mur1){ //on récupère le mur sur lequel la porte va être
        this.mur = mur1;
        this.idPorte = idPorte;
       // this.largeur = L;

    }

    /*
    public double largeur;
    public double hauteur;
   
    
    
  
    Polygon rectangle = new Polygon(); //on utilise des polygone pour faire des rectangles blanc et non des rectangles directement, car c'est plus simple pour les aligner avec le mur 

    double xp1, xp2,xp3,xp4; //abscisses des 4 points du rectangle qui va servir à afficher la porte
    double yp1,yp2,yp3,yp4; //idem
    double t; //angle du mur de la porte avec l'origine (pour pouvoir bien orienter la porte.)



*/
    /*public void Display(BorderPane root){
        

        double x1 = mur.getDebut().getX(); //on récup les coords des 2 pts qui def le mur :
        double y1 = mur.getDebut().getY();
        double x2 = mur.getFin().getX();
        double y2 = mur.getFin().getY();
     

        l = 8.0; //a modifier au besoin = épaisseur du trait qui sert à tracer le mur 
        L = 40.0; //largeur de l'ouverture

        //on va calculer les coords des 4 coins du rectangle qui sera affiché en blanc pour afficher la porte :(bcp de trigo et long....J'Y AI PASSÉ AU MOINS 3H!!!!)

        t = Math.atan((y1-y2)/(x1-x2)); //angle que forme le mur avec l'horizon
    

        xp1 = -(l/2)*Math.sin(t)+(L/2)*Math.cos(t)+x1-((x1-x2)/2);
        yp1 = (l/2)*Math.cos(t)+(L/2)*Math.sin(t)+y1-((y1-y2)/2);

        xp2 = (l/2)*Math.sin(t)-(L/2)*Math.cos(t)+x1-((x1-x2)/2);
        yp2 = -(l/2)*Math.cos(t)-(L/2)*Math.sin(t)+y1-((y1-y2)/2);

        xp3 = (l/2)*Math.sin(t)+(L/2)*Math.cos(t)+x1-((x1-x2)/2);
        yp3 = -(l/2)*Math.cos(t)+(L/2)*Math.sin(t)+y1-((y1-y2)/2);

        xp4 = -(l/2)*Math.sin(t)-(L/2)*Math.cos(t)+x1-((x1-x2)/2);
        yp4 = (l/2)*Math.cos(t)-(L/2)*Math.sin(t)+y1-((y1-y2)/2);

        rectangle.getPoints().addAll(new Double[]{xp1,yp1,xp4,yp4,xp2,yp2,xp3,yp3});
        rectangle.setFill(Color.WHITESMOKE); //temporaire pour visualiser la porte

        root.getChildren().add(rectangle); //on affiche le rectangle / porte
        //UnselectAll();





        
    }*/












public double surface(){
    return(0);
}
    
}