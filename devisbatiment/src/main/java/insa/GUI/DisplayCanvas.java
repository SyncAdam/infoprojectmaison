package insa.GUI;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import insa.Batiment.Appartement;
import insa.Batiment.Coin;
import insa.Batiment.Mur;
import insa.Batiment.Niveau;
import insa.Batiment.Piece;
import insa.Batiment.Porte;
import insa.GUI.ImmeubleHierarchy.HierarchySurfaceItemContext;

public class DisplayCanvas extends Pane{

    MainPane parentPane;        //type non-primitive passé par référence

    public ArrayList<Coin> coinTab; //création du tableau dynamique qui contient tous les points placés graphiquement
    public ArrayList<Mur> wallTab;
    public ArrayList<ArrayList<Mur>> murArrayTab; //on créer une array liste qui contient des listes de mur, pour pouvoir envoyer dans le constructeur de pièce
    public ArrayList<Piece> pieceTab;
    public ArrayList<Integer> iDOfSelectedWall;
    public ArrayList<Porte> doorTabList;
    public ArrayList<Coin> bufferCoinPiece; //contient temporairement tous les coins d'une boucle de mur lorsqu'elle est detectée pour pouvoir utiliser le construteur de pièce que t'as fait

    public ArrayList<Coin> selectedPoint;


    DisplayCanvas(MainPane parentPane)
    {
        this.parentPane = parentPane;

        this.coinTab = new ArrayList<Coin>();
        this.wallTab = new ArrayList<Mur>();
        this.bufferCoinPiece = new ArrayList<Coin>();
        this.murArrayTab = new ArrayList<ArrayList<Mur>>(); //on créer une array liste qui contient des listes de mur, pour pouvoir envoyer dans le constructeur de pièce
        this.pieceTab = new ArrayList<Piece>();
        this.iDOfSelectedWall = new ArrayList<Integer>();
        this.doorTabList = new ArrayList<Porte>();
    
        selectedPoint = new ArrayList<Coin>();

        this.setOnMouseClicked(e -> { //détection du clic + récup coords
            if (parentPane.ctrlIsPressed == false){
                 //sert juste à ne pas crééer de nouveau éléments lorsqu'on veut juste selectionner un point/mur avec ctrl
                System.out.println(parentPane.pointAlreadyExist + " vrai");
                if (parentPane.pointAlreadyExist == false){
                    
                    if (parentPane.autoWallState == false) { //si on clique et que autowall est off :

                        if(parentPane.HIsPressed == true){ //si on clique et que H est pressé, on récup la coord le Y du pt d'avant et le X du clic
                            this.coinTab.add(new Coin(this.coinTab.size(), e.getX(), coinTab.get(coinTab.size()-1).getY())); //on créér le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                        }else if(parentPane.VIsPressed == true){ //si V est pressé, on fait comme pour H mais à l'inverse
                            this.coinTab.add(new Coin(this.coinTab.size(), coinTab.get(coinTab.size()-1).getX(), e.getY())); //on créér le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche

                        }else{ //si ni V ni H n'est pas pressé on place le point à l'endroit du clic
                
                        this.coinTab.add(new Coin(this.coinTab.size(), e.getX(), e.getY())); //on créé le new point dans le tableau
                        DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche

                        /*NOTE : si on a un seul élement dans l'array list, sont index est 0, mais quand on demande la taille (.size()),
                        * on obtient 1, d'où le "-1", pour faire coller à la taille.
                        */

                        System.out.println("clic detecté");

                    }
                }
                    if (parentPane.autoWallState == true){ //si autowall activé :
                        if(parentPane.HIsPressed == true){ //si on clique et que H est pressé, on récup la coord le Y du pt d'avant et le X du clic
                            this.coinTab.add(new Coin(this.coinTab.size(), e.getX(), coinTab.get(coinTab.size()-1).getY())); //on créér le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                            wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-1), coinTab.get(coinTab.size()-2))); //on créér un mur à partir des 2 dernies coins du coinTab
                            /*NOTE : coinTab.size()-1 correspond à l'index du dernier point et coinTab.size()-2, à celui de l'avant dernier */

                            DisplayMur(wallTab.get(wallTab.size()-1));
                            System.out.println("Le coin d'id " + coinTab.get(coinTab.size()-1).getId()+ "est à la pos " + (coinTab.size()-1));
                        }else if(parentPane.VIsPressed == true){ //si V est pressé, on fait comme pour H mais à l'inverse
                            this.coinTab.add(new Coin(this.coinTab.size(), coinTab.get(coinTab.size()-1).getX(), e.getY())); //on créér le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                            wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-1), coinTab.get(coinTab.size()-2))); //on créér un mur à partir des 2 dernies coins du coinTab
                                                    
                            DisplayMur(wallTab.get(wallTab.size()-1));
                            System.out.println("Le coin d'id " + coinTab.get(coinTab.size()-1).getId()+ "est à la pos " + (coinTab.size()-1));

                        }else{ //si ni V ni H n'est pas pressé on place le point à l'endroit du clic
                
               



                            coinTab.add(new Coin(coinTab.size(), e.getX(), e.getY())); //on créé le new point dans le tableau avec comme id sa position dans le tableau
                            DisplayCoin(coinTab.get(coinTab.size()-1)); //on l'affiche (le dernier pt du coinTab)
                        
                        
                            wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-1), coinTab.get(coinTab.size()-2))); //on créér un mur à partir des 2 dernies coins du coinTab
                            /*NOTE : coinTab.size()-1 correspond à l'index du dernier point et coinTab.size()-2, à celui de l'avant dernier */
                            DisplayMur(wallTab.get(wallTab.size()-1)); //on l'affcihe
                            
                            System.out.println("Mur affiché");
                            
                            System.out.println("Le coin d'id " + coinTab.get(coinTab.size()-1).getId()+ "est à la pos " + (coinTab.size()-1));
                    
                     }
                }
                }

                if(parentPane.pointAlreadyExist ==true){ //si ya déjà un point

                    if (parentPane.autoWallState == false){ //et que autowall est désactivé, on dit qu'on ne peut pas placer un autre point au même endroit
                        System.out.println("Vous ne pouvez pas placer un point ici ! ") ;
                    }

                    else{ //si autowall est activé et qu'il y a déjà un point : on fait un mur avec le dernier point créé et le point selectionné
                    
                        coinTab.add(coinTab.size(), coinTab.get(parentPane.idOfCurrentSelectedPoint));
                        //coinTab.remove(idOfCurrentSelectedPoint);
                        
                    
                        wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1)));

                        DisplayMur(wallTab.get(wallTab.size()-1));//on l'affiche
                    

                        /*Raisonnement de ce que j'ai fait (21/04): quand on clique 2 fois sur un coin, c'est forcément que l'on fait une pièce (du moins si le auto wall a tjrs été activé),
                        * Donc on va parcourir tous les murs de walltab en partant du dernier jusquà trouver un point commun avec le dernier mur
                        *placé. Et a chaque fois, on ajoute dans une liste de mur de l'array liste de liste de mur. (oui une array liste de liste !)
                        */

                        //==================================================
                        //Tout ca existe deja dans piece mais un peu plus efficacement
                        //==================================================

                        

                        int i = wallTab.size()-2; // on va commencer par vérifier l'avant dernier mur, sinon la boucle while sort dès la première itération
                        int j =0;
                        
                        murArrayTab.add(new ArrayList<Mur>()); //on créer un arraylist de mur dans l'arraylist d'arraylist de mur
                        murArrayTab.get(murArrayTab.size()-1).add(wallTab.get(wallTab.size()-1)); //on ajoute le dernier mur crééé dans l'array liste de l'array liste d'array liste de mur, car il ne sera pas ajouté par la boucle while
                        try{
                            while (wallTab.get(i).getIdDebut() != parentPane.idOfCurrentSelectedPoint && wallTab.get(i).getIdFin() != parentPane.idOfCurrentSelectedPoint){ //tant que les 2 coins du mur i sont différents du currentidpoint on continue
                                
                                murArrayTab.get(murArrayTab.size()-1).add(wallTab.get(i)); //on ajoute au tableau dans le tableau mur i de la pièce
                                i--;
                                j++;
                                if(j >= wallTab.size()-1){throw new PieceNonFermeeException();} //condition de si on a parcouru tous les murs et q'on est pas sorti du while
                                //POUR PLUS TARD : EXEPTION QUI Détecte QUAND ERREUR DANS LA BOUCLE --> LA PI7CE N'EST PAS FERMée
                            }
                            //pieceTab.add(new Piece(murArrayTab.size()-1, murArrayTab.get(murArrayTab.size()-1))); // on ajoute dans l'arrayList de pièce la new pièce créée

                            
                        
                            System.out.println("Pièce de " + (murArrayTab.get(murArrayTab.size()-1).size()+1) + " murs créée");
                            int nbMurDernierePiece = (murArrayTab.get(murArrayTab.size()-1).size()+1);
                            System.out.println(nbMurDernierePiece + "Murs dans la pice");


                            for (int k = 0; k <nbMurDernierePiece; k++) { //piece.murs = array liste qui contient tous les murs de la pièce
                               bufferCoinPiece.add(wallTab.get(wallTab.size()-1-k).getFin()); //on ajoute tous les points qui constituent la pièce
                               
                               System.out.println(wallTab.get(wallTab.size()-1-k).getFin().getX());
                               System.out.println(wallTab.get(wallTab.size()-1-k).getFin().getY());
                              // bufferCoinPiece.add(new Coin(k, 100.0 , 200.0));//test


                              
                            }

                            pieceTab.add(new Piece(pieceTab.size(), bufferCoinPiece));
                            pieceTab.get(pieceTab.size()-1).toString();
                            HilightRoom(pieceTab.get(pieceTab.size()-1), Color.YELLOW); 
                        }
                        catch(PieceNonFermeeException err){
                            System.out.println(err);
                        }
                        
                    }

                }
            }
        });
        /*Piece piece = new Piece(1);
        HilightRoom(piece, Color.YELLOW);*/
    }

    public void DisplayCoin(Coin c)
    {
        Circle circle1 = new Circle(c.getX(), c.getY(), 5);
        this.getChildren().add(circle1);
        
        circle1.setOnMouseEntered(e -> { //on détecte quand la souris passe sur un point (POUR PLUS TARD : EFFET MAGNET ?)
      
            circle1.setFill(Color.BLUE); 
            this.parentPane.pointAlreadyExist = true;  //empeche de pouvoir placer un point au même eendroit (cf classe app)
            this.parentPane.idOfCurrentSelectedPoint = c.getId(); //on actualise cette variable avec l'id du point selectionné 
            System.out.println("L'id du point selectionné est" + this.parentPane.idOfCurrentSelectedPoint);
            //this.parentPane.log.setTxt("po");
            
        }); 

        //System.out.println("Point affiché");

        circle1.setOnMouseExited(e -> {

                circle1.setFill(Color.BLACK); 
                this.parentPane.pointAlreadyExist = false;
                System.out.println(this.parentPane.pointAlreadyExist);

        }); 

        circle1.setOnMouseClicked(e -> {
            
            if (this.parentPane.wallButtonState == true){
                this.selectedPoint.add(c);
            }

        });
    }

    public void DisplayMur(Mur m){
        DisplayCoin(m.getDebut());
        DisplayCoin(m.getFin());

        Line ligne = new Line();
      
        ligne.setStartX(m.getDebut().getX());
        ligne.setStartY(m.getDebut().getY());
        ligne.setEndX(m.getFin().getX());
        ligne.setEndY(m.getFin().getY());
        ligne.setStrokeWidth(4);
        this.getChildren().add(ligne);
     
        ligne.setOnMouseEntered(e -> { //on détecte quand la souris passe la ligne (POUR PLUS TARD : EFFET MAGNET ?)
            
            if (m.superpositionState == false){
                m.superpositionState = true;
            }
            else{

                System.out.println("superposition ligne"); //permet de ne pas la selectionner dès sa création
                if(this.parentPane.ctrlIsPressed ==true && m.isSelected == false){
                    setColor(ligne, Color.RED);
                } //si on est en mode selection (ctrl pressé) et que le mur n'est pas selectionné, on colore en rouge au survol
            
                ligne.setOnMouseClicked(event -> { //si la souris est sur la ligne et clique :
                    if(this.parentPane.ctrlIsPressed ==true){ //clique + ctrl :
                        if(m.isSelected == false){

                            setColor(ligne, Color.GREENYELLOW);
                            iDOfSelectedWall.add(m.getID());
                            m.isSelected = true;
                        }
                        this.parentPane.log.setTxt("mur(s) selectionné(s)");
                        /*else{
                            setColor(Color.BLACK);
                            iDOfSelectedWall.remove(this.idMur);
                            isSelected = false;
                            
                        }*/
                    } 
                });    
        
            }
        }); 

        System.out.println("Point affiché");

        ligne.setOnMouseExited(e -> {  //si la souris n'est plus sur la ligne et que ctrl n'est pas selectionné, on remet le trait en noir
            if (m.isSelected == false){
                setColor(ligne, Color.BLACK);
            } //si on quitte le point de la souris et qu'il n'est pas selectionné, on le met en noir
        }); //on remet la couleur en noir
    }



    public void HilightRoom(Piece piece, Color couleur){
        //public void HilightRoom(){
        

        ArrayList<Coin> coinsPiece = new ArrayList<Coin>(); //arrayliste destinnée à contenir tous les points qui forment la pièce

        for (int i = 0; i <piece.murs.size(); i++) { //piece.murs = array liste qui contient tous les murs de la pièce
            coinsPiece.add(piece.murs.get(i).getDebut()); //on ajoute tous les points qui constituent la pièce 
    
            
        }
        double points[] = new double [coinsPiece.size()*2]; //tableau de taille 2 fois le nb de point pour stocker ttes les coords de tous les pts dans un tableau
        int j =0;
        for (int i = 0; i < coinsPiece.size(); i++) {  
            //on veut récup les coords x y du point dans la case i de l'array liste, ajouter ces coords aux pos j et j+1 du tableau. j avance de 2 en 2 et i de 1 en 1 pour ne pas sauter de points
            points[j] = coinsPiece.get(i).getX(); //en i on met la coord x du pt J
            points[j+1] = coinsPiece.get(i).getY();//en i+1 on met la coord x du pt J
            j = j+2;

            
        }
        for (int i = 0; i < coinsPiece.size()*2 ; i++) {
            System.out.println("tab points : "+ points[i]);
            
        }
        
        /*double points[] = { 10.0d, 140.0d, 30.0d, 110.0d, 40.0d,
            50.0d, 50.0d, 40.0d, 110.0d, 30.0d, 140.0d, 10.0d };*/
    
          // create a polygon
        Polygon polygon = new Polygon(points);
        polygon.setFill(couleur); //on colore le polygone avec la couleur du constructeur de la méthode
        this.getChildren().add(polygon); // on l'affiche sur le pane 



    }


    public void setColor(Line l, Color couleur){
        l.setStroke(couleur);
    }

    public void DisplayPiece(Piece p)
    {
        for(int i = 0; i < p.murs.size(); i++)
        {
            DisplayMur(p.murs.get(i));
        }
    }

    public void DisplayAppart(Appartement appart)
    {
        for(int i = 0; i < appart.pieces.size(); i++)
        {
            DisplayPiece(appart.pieces.get(i));
        }
    }

    public void DisplayNiveau(Niveau niv)
    {
        for(int i = 0; i < niv.appartements.size(); i++)
        {
            DisplayAppart(niv.appartements.get(i));
        }
    }

    public void DisplayObject(Object obj)
    {
        if(obj instanceof Coin)
        {
            DisplayCoin((Coin) obj);
        }
        else if(obj instanceof Mur)
        {
            DisplayMur((Mur) obj);
        }
        else if(obj instanceof Piece)
        {   
            DisplayPiece((Piece) obj);  
        }
        else if(obj instanceof Appartement)
        {
            DisplayAppart((Appartement) obj);
        }
        else if(obj instanceof Niveau)
        {
            DisplayNiveau((Niveau) obj);
        }
    }
}