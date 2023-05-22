package insa.Batiment;

import java.util.ArrayList;

import insa.Batiment.Revetements.Revetement;
import insa.Batiment.Revetements.RevetementException;
import javafx.scene.shape.Line;

public class Mur extends Surface{
    
    //Attributes
    private Coin debut;     //Point de départ du mur
    private Coin fin;     //Point de fin du mur
    private int idMur;
    public boolean superpositionState;
    public Line ligne;
    
    public boolean isSelected = false;

    //Constructors

    /**
     * <p>Constructeur pour un mur</p>
     * @param id <code>int</code> id sera affecte au mur
     * @param c1 <code>Coin</code> le coin de depart du mur
     * @param c2 <code>Coin</code> le coin de fin du mur
     */

    public Mur(int id, Coin c1, Coin c2)
    {
        this.debut = c1;
        this.fin = c2;
        this.idMur = id;
        this.revetements = new ArrayList<Revetement>();
        this.superpositionState = false;
        
        c1.onAWall = true; //permet d'empécher la modification par la suite.
        c2.onAWall = true;

        this.ligne = new Line();
      
        ligne.setStartX(this.getDebut().getX());
        ligne.setStartY(this.getDebut().getY());
        ligne.setEndX(this.getFin().getX());
        ligne.setEndY(this.getFin().getY());
        ligne.setStrokeWidth(4);
    }

    /**
     * <p>Constructeur pour un mur</p>
     * <p>Le coin de depart est donne, le fin est demandee</p>
     * @param id <code>int</code> id sera affecte au mur
     * @param c1 <code>Coin</code> le coin de depart du mur
     */

    Mur(int id, Coin debut)
    {
        this.debut = debut;
        Coin fin = new Coin(id+1);
        this.fin = fin;
        this.idMur = id;
        this.revetements = new ArrayList<Revetement>();
        this.superpositionState = false;

        this.ligne = new Line();

        this.debut.onAWall = true; //permet d'empécher la modification par la suite.
        this.fin.onAWall = true;
      
        ligne.setStartX(this.getDebut().getX());
        ligne.setStartY(this.getDebut().getY());
        ligne.setEndX(this.getFin().getX());
        ligne.setEndY(this.getFin().getY());
        ligne.setStrokeWidth(4);

    }

     /**
     * <p>Constructeur pour un mur, </p>
     * <p>Le coin de depart et le fin sont demandee</p>
     * @param id <code>int</code> id sera affecte au mur
     */

    Mur(int id)
    {
        Coin debut = new Coin((id*2)-1);
        Coin fin = new Coin(id*2);

        this.debut = debut;
        this.fin = fin;
        this.idMur = id;
        this.revetements = new ArrayList<Revetement>();
        this.superpositionState = false;

        this.debut.onAWall = true; //permet d'empécher la modification par la suite.
        this.fin.onAWall = true;

        this.ligne = new Line();

        debut.onAWall = true; //permet d'empécher la modification par la suite.
        fin.onAWall = true; 
      
        ligne.setStartX(this.getDebut().getX());
        ligne.setStartY(this.getDebut().getY());
        ligne.setEndX(this.getFin().getX());
        ligne.setEndY(this.getFin().getY());
        ligne.setStrokeWidth(4);

    }
    
    //Methods

    /**
     * <p>Methode qui permet de verifier si deux murs s'intersectent ou pas</p>
     * <p>Le coin de depart et le fin sont demandee</p>
     * @param a <code>Mur</code> un des murs a verifier
     * @param b <code>Mur</code> l'autre mur a verifier s'il s'intersecte avec l'autre
     * @return <code>vrai</code> si les murs s'intersectent, <code>faux</code> sinon
     */

    public static boolean murIntersect(Mur a, Mur b)
    {
        double x = (b.ordonneeintercept() - a.ordonneeintercept()) / (a.pente() - b.pente());
        if((x > a.getDebut().getX() && x < a.getFin().getX()) || (x > a.getFin().getX() && x < a.getDebut().getX()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * <p>Methode qui permet de calculer la pente du droite qui porte le mur</p>
     * @return <code>double</code> la pente de l'equation du droite qui porte le mur
     */

    public double pente()
    {
        return (this.getFin().getY() - this.getDebut().getY()) / (this.getFin().getX() - this.getDebut().getX());
    }

    /**
     * <p>Methode qui permet de calculer l'intersection du droite qui porte le mur et l'axe des ordonnees</p>
     * @return <code>double</code> L'intersection du droite qui porte le mur et l'axe des ordonnees
     */

    public double ordonneeintercept()
    {
        return this.getDebut().getY() - this.pente() * this.getDebut().getX();
    }

    @Override
    public String toString()
    {
        return "\nLe mur " + this.idMur + " a un coin pour debut en:\n\t" + this.debut.getX() + "x " + this.debut.getY() + "y"
             +  "\net pour fin:\n\t" + this.fin.getX() + "x " + this.fin.getY() + "y\n";
    }

    /**
     * <p>Methode qui calcule la longueur du mur</p>
     * @return <code>double</code> la longueur du mur
     */

    public double longueur()
    {
        return Math.sqrt(Math.pow(Math.abs(this.debut.getX() - this.fin.getX()), 2) + Math.pow(Math.abs(this.debut.getY() - this.fin.getY()), 2));
    }

    /**
     * <p>Methode pour calculer la surface du mur</p>
     * @param h <code>double</code> la hauteur de la piece/niveau
     * @return <code>double</code> la surface du mur
     */

    public double surface(double h)
    {
        return this.longueur() * h;
    }

    public void addRevetement(Revetement rev) throws RevetementException
    {
        if(rev.pourMur) this.revetements.add(rev);
        else
        {
            throw new RevetementException();
        }
    }

    void dessiner()
    {}

    //setters getters

    public Coin getDebut()
    {
        return this.debut;
    }

    public Coin getFin()
    {
        return this.fin;
    }

    /**
     * <p>Methode qui permet de changer le coin de depart d'un mur</p>
     * <i>Attention! Quand les coins d'un mur sont changes, la piece n'est plus integre! </i>
     * @param d <code>Coin</code> a affecter comme coin de depart 
     */

    public void setDebut(Coin d)
    {
        this.debut = d;
    }

    public void setFin(Coin d)
    {
        this.fin = d;
    }

    public int getID()
    {
        return this.idMur;
    }

    public int getIdDebut()
    {
        return debut.getId();
    }

    public int getIdFin()
    {
        return fin.getId();
    }

    public void setSelectedState(boolean state){
        isSelected = state;
    }
}
