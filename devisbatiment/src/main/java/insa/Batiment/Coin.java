package insa.Batiment;

import java.io.Serializable;

public class Coin implements Serializable{
    
    private int idCoin; //identificateur du coin
    private double cx;  //abscisse du coin
    private double cy;  //ordonnee du coin
    boolean superpositionState; //sert juste à ne pas changer la couleur du point immédiatement après sa création
    public boolean onAWall;
    
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
        this.superpositionState = false;
        this.onAWall = false;
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
        this.superpositionState = false;
        this.onAWall = false;
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
    
}