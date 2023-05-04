package insa.Batiment;

import java.io.Serializable;

import insa.GUI.App;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Coin extends App implements Serializable{
    
    private int idCoin; //identificateur du coin
    private double cx;  //abscisse du coin
    private double cy;  //ordonnee du coin
    boolean superpositionState = false; //sert juste à ne pas changer la couleur du point immédiatement après sa création

    /**
     * <p>Cette constructeur cree un coin en demandant a l'utilisateur de saisir les coordonnees</p>
     * <p>Attention! Dependence du class Lire!</p>
     * @param idCoin L'identificateur du coin
     */
    Coin(int idCoin)
    {   
        this.idCoin = idCoin;
        System.out.println("Donnez le coordonnée x de votre coin " + this.idCoin);
        this.cx = Lire.d();
        System.out.println("Donnez le coordonnée y de votre coin " + this.idCoin);
        this.cy = Lire.d();
    }

    /**
     * <p>Cette constructeur cree un coin avec les parametres donnees</p>
     * //@deprecated Parce qu'on utilise pas mais on le guard au cas ou. Attention! Compilateur jete warning quand appellé//
     * @param id L'identificateur du coin
     * @param x L'abscisse du coin
     * @param y L'ordonnee du coin
     */
    //@Deprecated
    public Coin(int id, double x, double y)
    {
        this.cx = x;
        this.cy = y;
        this.idCoin = id;
    }

    public int getId()
    {
        return idCoin;
    }

    /**
     * Methode qui permet de recuperer l'abscisse du coin
     * @return l'abscisse du coin en <code>double</code>
     */

    public double getX()
    {
        return this.cx;
    }

    /**
     * Methode qui permet de recuperer l'ordonnee du coin
     * @return l'ordonne du coin en <code>double</code>
     */

    public double getY()
    {
        return this.cy;
    }

    /**
     * Methode qui permet de changer l'abscisse du coin
     * @param x <code>double</code> qui sera affecte a l'abscisse du coin
     */

    public void setX(double x)
    {
        this.cx = x;
    }

    /**
     * Methode qui permet de changer l'ordonnee du coin
     * @param y <code>double</code> qui sera affecte a l'ordonne du coin
     */

    public void setY(double y)
    {
        this.cy = y;
    }


    /**
     * Cette methode regarde si le coin est coincident avec un autre coin
     * @param obj - un objet (normalement Coin) a verifer s'il coincide avec l'autre.
     * @return <code>vrai</code> si les deux coins coicident <code>false</code> sinon.
     * @throws RuntimeException si les objets sont non compatibles
     * <p><i>Peut etre faire une methode static?</i></p>
     */
    
    @Override
    public boolean equals(Object obj) throws RuntimeException
    {
        if(obj instanceof Coin) // si c'est un coin on fait ça
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
    
    public void DisplayPoint(BorderPane canva){
        
        Circle circle1 = new Circle(cx, cy, 5);
        canva.getChildren().add(circle1);
        
        circle1.setOnMouseEntered(e -> { //on détecte quand la souris passe sur un point (POUR PLUS TARD : EFFET MAGNET ?)
      
            circle1.setFill(Color.BLUE); 
            pointAlreadyExist = true;  //empeche de pouvoir placer un point au même eendroit (cf classe app)
            idOfCurrentSelectedPoint = idCoin; //on actualise cette variable avec l'id du point selectionné 
            System.out.println("L'id du point selectionné est" + idOfCurrentSelectedPoint);
            log.setTxt("po");
            
        }); 
            //System.out.println("Point affiché");
            circle1.setOnMouseExited(e -> { circle1.setFill(Color.BLACK); 
                pointAlreadyExist = false;
                System.out.println(pointAlreadyExist);}); 


            circle1.setOnMouseClicked(e -> {
                if (wallButtonState == true){
                    selectedPoint.add(this);

                }





            });
        

    }
    
    
}