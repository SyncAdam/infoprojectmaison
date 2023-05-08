package insa.Batiment;
import java.io.Serializable;
import java.util.ArrayList;

import insa.Batiment.Revetements.Revetement;
import insa.Batiment.Revetements.RevetementException;

public class Mur extends Surface implements Serializable{
    
    //Attributes
    private Coin debut;     //Point de départ du mur
    private Coin fin;     //Point de fin du mur
    private int idMur;
    private int nmbrPortes;     //NON!!!!!!!!!
    private int nmbrFenetres;   //NON!!!!!!!!!
    public boolean superpositionState;
    
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
        this.nmbrFenetres = 0;
        this.nmbrPortes = 0;
        this.revetements = new ArrayList<Revetement>();
        this.superpositionState = false;
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
        this.nmbrFenetres = 0;
        this.nmbrPortes = 0;
        this.revetements = new ArrayList<Revetement>();
        this.superpositionState = false;

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
        this.nmbrFenetres = 0;
        this.nmbrPortes = 0;
        this.revetements = new ArrayList<Revetement>();
        this.superpositionState = false;

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

    /**
     * <p>Methode pour ajouter des fenetres </p>
     * @param n <code>int</code> nombre de fenetre a ajouter au mur
     */

    public void addFenetres(int n)          //NOOOON!!!!!!!!
    {
        this.nmbrFenetres += n;
    }

    /**
     * <p>Methode pour ajouter des portes au mur </p>
     * @param n <code>int</code> nombre de fenetre a ajouter au mur
     */

    public void addPortes(int n)            //NOOOON!!!!!!!!
    {
        this.nmbrPortes += n;
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

    public int getNFenetres()           //NOOOOOOOOOOOOOON!!!!!!!!!!
    {
        return this.nmbrFenetres;
    }

    public int getNPortes()             //NOOOOOOOOOOOOOON!!!!!!!!!!
    {
        return this.nmbrPortes;
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

    /*

    public void DisplayMur(BorderPane canva){
        debut.DisplayPoint(root);
        fin.DisplayPoint(root);
      
        ligne.setStartX(debut.getX());
        ligne.setStartY(debut.getY());
        ligne.setEndX(fin.getX());
        ligne.setEndY(fin.getY());
        ligne.setStrokeWidth(4);
        canva.getChildren().add(ligne);



     
        ligne.setOnMouseEntered(e -> { //on détecte quand la souris passe la ligne (POUR PLUS TARD : EFFET MAGNET ?)
            
            if (superpositionState == false){superpositionState = true;}else{System.out.println("superposition ligne"); //permet de ne pas la selectionner dès sa création
            if(ctrlIsPressed ==true && isSelected == false){setColor(Color.RED);} //si on est en mode selection (ctrl pressé) et que le mur n'est pas selectionné, on colore en rouge au survol
            
            ligne.setOnMouseClicked(event -> { //si la souris est sur la ligne et clique :
                if(ctrlIsPressed ==true){ //clique + ctrl :
                    if(isSelected == false){
                    setColor(Color.GREENYELLOW);
                    iDOfSelectedWall.add(this.idMur);
                    isSelected = true;}
                    log.setTxt("mur(s) selectionné(s)");
                    /*else{
                        setColor(Color.BLACK);
                        iDOfSelectedWall.remove(this.idMur);
                        isSelected = false;
                        
                    }
                } 


                
            });
           


            
        
        
        }
        }); 
        System.out.println("Point affiché");
        ligne.setOnMouseExited(e -> {  //si la souris n'est plus sur la ligne et que ctrl n'est pas selectionné, on remet le trait en noir
            if (isSelected == false){setColor(Color.BLACK);} //si on quitte le point de la souris et qu'il n'est pas selectionné, on le met en noir
    
    
    
        }); //on remet la couleur en noir

            
            


    }


    public void setColor(Color couleur){
        ligne.setStroke(couleur);


    }

    */

    //public void setSelectedState(boolean state){isSelected = state;}
}
