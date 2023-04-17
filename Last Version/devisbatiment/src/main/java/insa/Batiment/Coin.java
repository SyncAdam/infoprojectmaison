package insa.Batiment;
import java.io.Serializable;
import insa.GUI.App;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Coin  extends App implements Serializable{
    
    private int idCoin; //identificateur du coin
    private double cx;  //abscisse du coin
    private double cy;  //ordonnee du coin

    //constructeur sans parametre demande des parametres
    public Coin(int idCoin)
    {   
        this.idCoin = idCoin;
        System.out.println("Donnez le coordonnée x de votre coin " + this.idCoin);
        this.cx = Lire.d();
        System.out.println("Donnez le coordonnée y de votre coin " + this.idCoin);
        this.cy = Lire.d();
    }

    //constructeur simple, tout est fourni
    public Coin(int id, double x, double y)
    {
        this.cx = x;
        this.cy = y;
        this.idCoin = id;
        //System.out.println("Nouveau coin créé");
        //log.setLogTxt("Point");
    }

    public double getX()
    {
        return this.cx;
    }

    public double getY()
    {
        return this.cy;
    }

    public void setX(double x)
    {
        this.cx = x;
    }

    public void setY(double y)
    {
        this.cy = y;
    }

    @Override
    public boolean equals(Object obj) throws RuntimeException
    {
        if(obj instanceof Coin)
        {
            Coin c = (Coin) obj;        //casting object to Coin
            if(c.getX() == this.cx && c.getY() == this.cy)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            throw new RuntimeException("Types non compatibles");
        }
    }

    @Override
    public String toString()
    {
        return("\nLe coin " + this.idCoin + " a pour abscisse : " + this.cx + " et ordonnée : " +  this.cy);
    }


    boolean superpositionState = false; //sert juste à ne pas changer la couleur du point immédiatement après sa création
    public void DisplayPoint(BorderPane canva){
        
        Circle circle1 = new Circle(cx, cy, 5);
        canva.getChildren().add(circle1);

        
        circle1.setOnMouseEntered(e -> { //on détecte quand la souris passe sur un point (POUR PLUS TARD : EFFET MAGNET ?)
        //System.out.println("superposition");
        if (superpositionState == false){ //permet de ne pas tout de suite surligner le point dès sa création
            superpositionState = true;}
        else{
            circle1.setFill(Color.BLUE); 
            pointAlreadyExist = true;  //empeche de pouvoir placer un point au même eendroit (cf classe app)
            idOfCurrentSelectedPoint = idCoin; //on actualise cette variable avec l'id du point selectionné 
        }}); 
            //System.out.println("Point affiché");
            circle1.setOnMouseExited(e -> { circle1.setFill(Color.BLACK); 
                pointAlreadyExist = false;
                System.out.println(pointAlreadyExist);}); 
        

    }
    
}
