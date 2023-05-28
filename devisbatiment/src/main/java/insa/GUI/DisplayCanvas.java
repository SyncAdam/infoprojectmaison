package insa.GUI;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Hashtable;

import insa.Batiment.Appartement;
import insa.Batiment.Coin;
import insa.Batiment.Mur;
import insa.Batiment.Niveau;
import insa.Batiment.Piece;
import insa.Batiment.Porte;

public class DisplayCanvas extends Pane{
 
    MainPane parentPane;        //type non-primitive passé par référence

    public ArrayList<Coin> coinTab; //création du tableau dynamique qui contient tous les points placés graphiquement
    public ArrayList<Mur> wallTab;
    public ArrayList<ArrayList<Mur>> murArrayTab; //on créer une array liste qui contient des listes de mur, pour pouvoir envoyer dans le constructeur de pièce
    public ArrayList<Piece> pieceTab;
    public ArrayList<Integer> iDOfSelectedWall;
    
    public ArrayList<Porte> doorTabList;
    public ArrayList<Object> bufferCoinPiece; //contient temporairement tous les coins d'une boucle de mur lorsqu'elle est detectée pour pouvoir utiliser le construteur de pièce que t'as fait
    public ArrayList<Object> bufferMurPiece;

    public ArrayList<Coin> selectedPoint; //sert juste à garder en mémoire les 2 points que l'on veut relier avec l'outils mur
    public ArrayList<Mur> selectedSurfaces; //sert juste à garder en mémoire les murs de la pièce que l'on veut créer avec l'outils pièce
    public double[] coordsOfHilightedPoint = new double[2]; //utile pour quand on a le mode vertical ou horizon activé et qu'on veut mettre un point alligné avec un point qui existe déjà pour faire un carré
    public Piece selectedPiece;
    public Appartement selectedAppartement;

    public Translate globalTranslate;

    public Hashtable<Mur, Line> murlineHT;
    public Hashtable<Line, Mur> linemurHT;
    Line ligne = new Line(); //Prévisualisation du mur .


    DisplayCanvas(MainPane parentPane)
    {
        this.parentPane = parentPane;

        this.coinTab = new ArrayList<Coin>();
        this.wallTab = new ArrayList<Mur>();
        this.bufferCoinPiece = new ArrayList<Object>();
        this.murArrayTab = new ArrayList<ArrayList<Mur>>(); //on créer une array liste qui contient des listes de mur, pour pouvoir envoyer dans le constructeur de pièce
        this.pieceTab = new ArrayList<Piece>();
        this.bufferMurPiece = new ArrayList<Object>();
        this.iDOfSelectedWall = new ArrayList<Integer>();
        this.doorTabList = new ArrayList<Porte>();

        this.ligne = new Line();

        this.globalTranslate = new Translate(0, 0);

        this.murlineHT = new Hashtable<>();
        this.linemurHT = new Hashtable<>();
    
        this.selectedPoint = new ArrayList<Coin>(0);
        this.selectedSurfaces = new ArrayList<Mur>(0);
        this.selectedPiece = null;
        this.selectedAppartement = null;


        
        this.getChildren().add(ligne);

        this.setOnMouseMoved(e->{

            if(!this.getChildren().contains(ligne)){
                Line ligne = new Line(); //Prévisualisation du mur .
                this.getChildren().add(ligne);
            } 
            
            if (parentPane.toolbar.autoWallState == true){
    //quand on déplace la souris et qu'on est en automatique, on affiche le futur mur
                
                if (coinTab.size()>0){
                    if (parentPane.HIsPressed == true){
                        ligne.setStrokeWidth(4);
                        ligne.setStartX(coinTab.get(coinTab.size()-1).getX());
                        ligne.setStartY(coinTab.get(coinTab.size()-1).getY());
                        ligne.setEndX(e.getX());
                        ligne.setEndY(coinTab.get(coinTab.size()-1).getY());

                    }else if(parentPane.VIsPressed == true){
                        ligne.setStrokeWidth(4);
                        ligne.setStartX(coinTab.get(coinTab.size()-1).getX());
                        ligne.setStartY(coinTab.get(coinTab.size()-1).getY());
                        ligne.setEndX(coinTab.get(coinTab.size()-1).getX());
                        ligne.setEndY(e.getY());
                    }
                    else{
                        ligne.setStrokeWidth(4);
                        ligne.setStartX(coinTab.get(coinTab.size()-1).getX());
                        ligne.setStartY(coinTab.get(coinTab.size()-1).getY());
                        ligne.setEndX(e.getX() - this.globalTranslate.getX());
                        ligne.setEndY(e.getY() - this.globalTranslate.getY());
                    }
                 }       
            }
            else
            {
                ligne.setStrokeWidth(0);
            }//quand on est plus en auto on cache la ligne}
        });

        this.setOnMouseClicked(e -> { //détection du clic + récup coords
            if (parentPane.ctrlIsPressed == false && this.parentPane.projectOpened == true && this.parentPane.toolbar.wallButtonState == false && parentPane.toolbar.modifyButtonState == false && parentPane.toolbar.buttonPorteState == false && parentPane.toolbar.mouseIsInTheToolBar == false){
                 //sert juste à ne pas crééer de nouveau éléments lorsqu'on veut juste selectionner un point/mur avec ctrl
               
                if (parentPane.pointAlreadyExist == false){
                    
                    if (parentPane.toolbar.autoWallState == false) { //si on clique et que autowall est off :

                        if(parentPane.HIsPressed == true){ //si on clique et que H est pressé, on récup la coord le Y du pt d'avant et le X du clic
                            this.coinTab.add(new Coin(this.coinTab.size(), e.getX(), coinTab.get(coinTab.size()-1).getY())); //on créér le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                            parentPane.log.setTxt("Vous venez de créer un point d'ID : " + Integer.toString(coinTab.size()-1) );
                            
                        }else if(parentPane.VIsPressed == true){ //si V est pressé, on fait comme pour H mais à l'inverse
                            this.coinTab.add(new Coin(this.coinTab.size(), coinTab.get(coinTab.size()-1).getX(), e.getY())); //on créér le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                            parentPane.log.setTxt("Vous venez de créer un point d'ID : " + Integer.toString(coinTab.size()-1) );

                        }else{ //si ni V ni H n'est pas pressé on place le point à l'endroit du clic
                
                            this.coinTab.add(new Coin(this.coinTab.size(), e.getX() - this.globalTranslate.getX(), e.getY() - this.globalTranslate.getY())); //on créé le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1), this.globalTranslate.getX(), this.globalTranslate.getY()); //on l'affiche
                            parentPane.log.setTxt("Vous venez de créer un point d'ID : " + Integer.toString(coinTab.size()-1) );

                            /*NOTE : si on a un seul élement dans l'array list, sont index est 0, mais quand on demande la taille (.size()),
                            * on obtient 1, d'où le "-1", pour faire coller à la taille.
                            */

                        

                    }
                }
                    if (parentPane.toolbar.autoWallState == true){ //si autowall activé :
                        if(parentPane.HIsPressed == true){ //si on clique et que H est pressé, on récup la coord le Y du pt d'avant et le X du clic
                            this.coinTab.add(new Coin(this.coinTab.size(), e.getX() - this.globalTranslate.getX(), coinTab.get(coinTab.size()-1).getY())); //on créér le new point dans le tableau
                            /*NOTE : coinTab.size()-1 correspond à l'index du dernier point et coinTab.size()-2, à celui de l'avant dernier */

                        }else if(parentPane.VIsPressed == true){ //si V est pressé, on fait comme pour H mais à l'inverse

                            this.coinTab.add(new Coin(this.coinTab.size(), coinTab.get(coinTab.size()-1).getX(), e.getY()- this.globalTranslate.getY())); //on créér le new point dans le tableau

                        }else{ //si ni V ni H n'est pas pressé on place le point à l'endroit du clic
                
                            coinTab.add(new Coin(coinTab.size(), e.getX() - this.globalTranslate.getX(), e.getY() - this.globalTranslate.getY())); //on créé le new point dans le tableau avec comme id sa position dans le tableau                            if(coinTab.size() > 1) wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1))); //on créér un mur à partir des 2 dernies coins du coinTab
                            
                            /*NOTE : coinTab.size()-1 correspond à l'index du dernier point et coinTab.size()-2, à celui de l'avant dernier */                   
                        }

                        wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1)));
                        DisplayCoin(this.coinTab.get(this.coinTab.size()-1), this.globalTranslate.getX(), this.globalTranslate.getY()); //on l'affiche
                        DisplayMur(wallTab.get(wallTab.size()-1), this.globalTranslate.getX(), this.globalTranslate.getY());
                        
                }
                }

                if(parentPane.pointAlreadyExist ==true){ //si ya déjà un point

                    if (parentPane.toolbar.autoWallState == false && parentPane.toolbar.wallButtonState == false && parentPane.HIsPressed == false && parentPane.VIsPressed == false &&parentPane.toolbar.modifyButtonState == false ){ //si on est pas dans un outils spécial et qu'on veut crééer un pr où il y en a déjà
                        
                            System.out.println("Vous ne pouvez pas placer un point ici ! ") ;
                        
                        parentPane.log.setTxt("Vous ne pouvez pas placer de point ici !");
                        parentPane.log.setColor(Color.RED);
                    }

                    if(parentPane.toolbar.autoWallState == true){ //si autowall est activé et qu'il y a déjà un point : on fait un mur avec le dernier point créé et le point selectionné
                    
                    
                        coinTab.add(coinTab.size(), coinTab.get(parentPane.idOfCurrentSelectedPoint));                        
                    
                        wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1)));

                        DisplayMur(wallTab.get(wallTab.size()-1), this.globalTranslate.getX(), this.globalTranslate.getY());//on l'affiche
                    
                    }

                }
            }
            else if(this.parentPane.projectOpened == false)
            {
                showNoProjectOpenedPopup();
            }
        });
    }

    public void clearCanva()
    {
        this.getChildren().clear();
        this.ligne = new Line();
        this.getChildren().add(this.ligne);
    }


    public void showNoProjectOpenedPopup()
    {
        Alert albert = new Alert(Alert.AlertType.WARNING);
        albert.setTitle("Erreur");
        albert.setHeaderText("Aucun projet n'est ouvert !");
        albert.setContentText("Veuillez en ouvrir un ou le créer via le menu fichier");
        albert.showAndWait();
    }

    public void DisplayCoin(Coin c, double x, double y)
    {
        Circle circle1 = new Circle(c.getX()+ x, c.getY() + y, 7);
        this.getChildren().add(circle1);
        
        circle1.setOnMouseEntered(e -> { //on détecte quand la souris passe sur un point 
      
            circle1.setFill(Color.BLUE); 
            this.parentPane.pointAlreadyExist = true;  //empeche de pouvoir placer un point au même eendroit (cf classe app)
            this.parentPane.idOfCurrentSelectedPoint = c.getId(); //on actualise cette variable avec l'id du point selectionné
            coordsOfHilightedPoint[0]= c.getX() + x; //on rentre les coords du point survolé pour pouvoir faire un carré avec mode H ou V
            coordsOfHilightedPoint[1]= c.getY() + y;

            System.out.println("L'id du point selectionné est" + this.parentPane.idOfCurrentSelectedPoint);
           // parentPane.log.setTxt("ID : " + this.parentPane.idOfCurrentSelectedPoint);
            
        }); 

        circle1.setOnMouseExited(e -> {

                circle1.setFill(Color.BLACK); 
                this.parentPane.pointAlreadyExist = false;
                System.out.println(this.parentPane.pointAlreadyExist);

        }); 

        circle1.setOnMouseClicked(e -> {
            if (parentPane.toolbar.modifyButtonState == true &&  parentPane.HIsPressed == false && parentPane.VIsPressed == false ){
                circle1.setRadius(0); // j'ai pas trouvé comment le faire s'auto-détruire, donc on réduit ça taille à 0 pour le faire disparaitre
                PointModifier pointModifier = new PointModifier(this.parentPane, c);
                pointModifier.Initialise();
                
                
            } 
            if(c.onAWall == true && parentPane.toolbar.modifyButtonState == true)
            {
                this.parentPane.log.setTxt("Vous ne pouvez pas modifier un coin qui est deja sur un murs");
                this.parentPane.log.setColor(Color.RED);
            }

            if(parentPane.HIsPressed == true  || parentPane.VIsPressed == true)
            {
                this.parentPane.log.setTxt("Veuillez désactiver le mode horizontal ou vertical pour modifier un coin");
                this.parentPane.log.setColor(Color.RED);
            }
            
            if (this.parentPane.toolbar.wallButtonState == true){ //si on a cliqué sur le bouton pour créer un mur et que l'on clique sur un point qui existe déjà, on l'ajoute à selected point
                circle1.setFill(Color.RED);
                this.selectedPoint.add(c);
                if (selectedPoint.size() == 1){parentPane.log.setTxt("Veuillez cliquer sur un deuxième point");}
                if (selectedPoint.size() >= 2){ //si on a déjà un point qui a été cliqué alors :
                    wallTab.add(new Mur(wallTab.size(), selectedPoint.get(0), selectedPoint.get(1))); //on créér un nouveau mur avec les 2 pts selectionés
                    DisplayMur(wallTab.get(wallTab.size()-1), 0, 0); //on affiche le mur nouvellement créé
                    selectedPoint.clear(); //on vide les murs de selected point
                    selectedPoint.clear();
                    parentPane.log.setTxt("Vous venez de créer un nouveau mur, veuillez en selectionner 2 autres");
                }
            }

            if(parentPane.HIsPressed == true && parentPane.toolbar.modifyButtonState == false){ //si on clique sur un point qui existe déjà avec le mode horizontale activé on va récupérer le y du pt d'avant et le x du point cliqué puis  crééer un new point
                this.coinTab.add(new Coin(this.coinTab.size(), coordsOfHilightedPoint[0], coinTab.get(coinTab.size()-1).getY())); //on créér le new point dans le tableau
                DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                if(parentPane.toolbar.autoWallState == true){
                    wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1))); //on créér un mur à partir des 2 dernies coins du coinTab
                    /*NOTE : coinTab.size()-1 correspond à l'index du dernier point et coinTab.size()-2, à celui de l'avant dernier */
                    DisplayMur(wallTab.get(wallTab.size()-1), 0, 0);
                }
            } else if(parentPane.VIsPressed == true  && parentPane.toolbar.modifyButtonState == false){//si on clique sur un point qui existe déjà avec le mode vertical activé on va récupérer le x du pt d'avant et le y du point cliqué puis  crééer un new point
                this.coinTab.add(new Coin(this.coinTab.size(), coinTab.get(coinTab.size()-1).getX(),coordsOfHilightedPoint[1])); //on créér le new point dans le tableau
                DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche

                if(parentPane.toolbar.autoWallState == true){
                    wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1))); //on créér un mur à partir des 2 dernies coins du coinTab                         
                    DisplayMur(wallTab.get(wallTab.size()-1), 0, 0);


                }
            }

        });
    }

    public void DisplayCoin(Coin c)
    {
        Circle circle1 = new Circle(c.getX(), c.getY(), 7);
        this.getChildren().add(circle1);
        
        circle1.setOnMouseEntered(e -> { //on détecte quand la souris passe sur un point (POUR PLUS TARD : EFFET MAGNET ?)
      
            circle1.setFill(Color.BLUE); 
            this.parentPane.pointAlreadyExist = true;  //empeche de pouvoir placer un point au même eendroit (cf classe app)
            this.parentPane.idOfCurrentSelectedPoint = c.getId(); //on actualise cette variable avec l'id du point selectionné
            coordsOfHilightedPoint[0]= c.getX(); //on rentre les coords du point survolé pour pouvoir faire un carré avec mode H ou V
            coordsOfHilightedPoint[1]= c.getY();

            System.out.println("L'id du point selectionné est" + this.parentPane.idOfCurrentSelectedPoint);
            parentPane.log.setTxt("ID : " + this.parentPane.idOfCurrentSelectedPoint);
            
        }); 

        circle1.setOnMouseExited(e -> {

                circle1.setFill(Color.BLACK); 
                this.parentPane.pointAlreadyExist = false;
                

        }); 

        circle1.setOnMouseClicked(e -> {



            if (parentPane.toolbar.modifyButtonState == true && c.onAWall == false && parentPane.HIsPressed == false && parentPane.VIsPressed == false ){
                circle1.setRadius(0); // j'ai pas trouvé comment le faire s'auto-détruire, donc on réduit ça taille à 0 pour le faire disparaitre
                PointModifier pointModifier = new PointModifier(this.parentPane, c);
                pointModifier.Initialise();
            } 
            if(c.onAWall == true && parentPane.toolbar.modifyButtonState == true)
            {
                this.parentPane.log.setTxt("Vous ne pouvez pas modifier un coin qui est déjà sur un mur");
                this.parentPane.log.setColor(Color.RED);
            }

            if(parentPane.HIsPressed == true  || parentPane.VIsPressed == true)
            {
                this.parentPane.log.setTxt("Veuillez désactiver le mode horizontal ou vertical pour modifier un coin");
                this.parentPane.log.setColor(Color.RED);
            }
            
            if (this.parentPane.toolbar.wallButtonState == true){ //si on a cliqué sur le bouton pour créer un mur et que l'on clique sur un point qui existe déjà, on l'ajoute à selected point
                circle1.setFill(Color.RED);
                this.selectedPoint.add(c);
                if (selectedPoint.size() == 1){parentPane.log.setTxt("Veuillez cliquer sur un deuxième point");}
                if (selectedPoint.size() >= 2){ //si on a déjà un point qui a été cliqué alors :
                    wallTab.add(new Mur(wallTab.size(), selectedPoint.get(0), selectedPoint.get(1))); //on créér un nouveau mur avec les 2 pts selectionés
                    DisplayMur(wallTab.get(wallTab.size()-1), 0, 0); //on affiche le mur nouvellement créé
                    selectedPoint.clear(); //on vide les murs de selected point
                    selectedPoint.clear();
                    parentPane.log.setTxt("Vous venez de créer un nouveau mur, veuillez en selectionner 2 autres");
                }
            }

            if(parentPane.HIsPressed == true && parentPane.toolbar.modifyButtonState == false){ //si on clique sur un point qui existe déjà avec le mode horizontale activé on va récupérer le y du pt d'avant et le x du point cliqué puis  crééer un new point
                this.coinTab.add(new Coin(this.coinTab.size(), coordsOfHilightedPoint[0], coinTab.get(coinTab.size()-1).getY())); //on créér le new point dans le tableau
                DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                if(parentPane.toolbar.autoWallState == true){
                    wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1))); //on créér un mur à partir des 2 dernies coins du coinTab
                    /*NOTE : coinTab.size()-1 correspond à l'index du dernier point et coinTab.size()-2, à celui de l'avant dernier */
                    DisplayMur(wallTab.get(wallTab.size()-1), 0, 0);
                }
            } else if(parentPane.VIsPressed == true  && parentPane.toolbar.modifyButtonState == false){//si on clique sur un point qui existe déjà avec le mode vertical activé on va récupérer le x du pt d'avant et le y du point cliqué puis  crééer un new point
                this.coinTab.add(new Coin(this.coinTab.size(), coinTab.get(coinTab.size()-1).getX(),coordsOfHilightedPoint[1])); //on créér le new point dans le tableau
                DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche

                if(parentPane.toolbar.autoWallState == true){
                    wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1))); //on créér un mur à partir des 2 dernies coins du coinTab                         
                    DisplayMur(wallTab.get(wallTab.size()-1), 0, 0);


                }
            }

        });
    }
    

    public void DisplayMur(Mur m, double x, double y){
        DisplayCoin(m.getDebut(), x, y);
        DisplayCoin(m.getFin(), x, y);

        Line foobar = new Line(); //ligne qui représente un mur 
      
        foobar.setStartX(m.getDebut().getX() + x);
        foobar.setStartY(m.getDebut().getY() + y);
        foobar.setEndX(m.getFin().getX() + x);
        foobar.setEndY(m.getFin().getY() + y);
        foobar.setStrokeWidth(4);

        murlineHT.put(m, foobar);//permet de lier une ligne du displayCanva à un mur réel
        linemurHT.put(foobar, m);
    
        foobar.setOnMouseEntered(e -> { //on détecte quand la souris passe la ligne (POUR PLUS TARD : EFFET MAGNET ?)
            
            if (m.superpositionState == false){
                m.superpositionState = true;
            }
            else{

                System.out.println("superposition ligne"); //permet de ne pas la selectionner dès sa création
                if(this.parentPane.ctrlIsPressed ==true && m.isSelected == false){
                    setColor(foobar, Color.RED);
                } //si on est en mode selection (ctrl pressé) et que le mur n'est pas selectionné, on colore en rouge au survol
       
        
            }
        }); 



        foobar.setOnMouseClicked(event -> { //si la souris est sur la ligne et clique :


            if(parentPane.toolbar.modifyButtonState == true){
                //m.ligne.setStrokeWidth(0); // on l'efface temporairement
                WallModifier WallModifier = new WallModifier(parentPane, m);
                WallModifier.Initialise();

            }
            if(parentPane.toolbar.buttonPorteState == true){ //si on clique sur mur avec outils porte : on créer et affiche porte
                doorTabList.add(new Porte(doorTabList.size(), m)); 
                DisplayPorte(doorTabList.get(doorTabList.size()-1));
                m.door =true;

            }

            if(this.parentPane.ctrlIsPressed ==true){ //clique + ctrl :
                if(m.isSelected == false){//si le mur n'est pas déjà selectionné, on le selectionne 

                    selectSurface(m);


                }
                
                else{ //si on a cliqué dessus et qu'il était déjà selectionné on le déselectionne

                    deselectSurface(m);
                    
                }
            } 
        });  

        this.getChildren().add(murlineHT.get(m));

        System.out.println("Point affiché");

        foobar.setOnMouseExited(e -> {  //si la souris n'est plus sur la ligne et que ctrl n'est pas selectionné, on remet le trait en noir
            if (m.isSelected == false){
                setColor(foobar, Color.BLACK);
            } //si on quitte le point de la souris et qu'il n'est pas selectionné, on le met en noir
        }); //on remet la couleur en noir

        if(m.door == true){DisplayPorte(m);}
    }

    public void DisplayMur(Mur m){

        DisplayCoin(m.getDebut());
        DisplayCoin(m.getFin());

        Line foobar = new Line();
      
        foobar.setStartX(m.getDebut().getX());
        foobar.setStartY(m.getDebut().getY());
        foobar.setEndX(m.getFin().getX());
        foobar.setEndY(m.getFin().getY());
        foobar.setStrokeWidth(4);

        murlineHT.put(m, foobar); //HashTable qui permet à partir d'un mur de retrouver la ligne correspondante 
        linemurHT.put(foobar, m); //HashTable qui permet à partir d'une lingne de retrouver le mur correspondant 
    
        foobar.setOnMouseEntered(e -> { //on détecte quand la souris passe la ligne (POUR PLUS TARD : EFFET MAGNET ?)
            
            if (m.superpositionState == false){
                m.superpositionState = true;
            }
            else{

                System.out.println("superposition ligne"); //permet de ne pas la selectionner dès sa création
                if(this.parentPane.ctrlIsPressed ==true && m.isSelected == false){
                    setColor(foobar, Color.RED);
                } //si on est en mode selection (ctrl pressé) et que le mur n'est pas selectionné, on colore en rouge au survol
       
        
            }
        }); 



        foobar.setOnMouseClicked(event -> { //si la souris est sur la ligne et clique :


            if(parentPane.toolbar.modifyButtonState == true){
                //m.ligne.setStrokeWidth(0); // on l'efface temporairement
                WallModifier WallModifier = new WallModifier(parentPane, m);
                WallModifier.Initialise();

            }
            if(parentPane.toolbar.buttonPorteState == true){
                
                doorTabList.add(new Porte(doorTabList.size(), m)); 
                DisplayPorte(doorTabList.get(doorTabList.size()-1));
                m.door = true;

            }

            if(this.parentPane.ctrlIsPressed ==true){ //clique + ctrl :
                if(m.isSelected == false){//si le mur n'est pas déjà selectionné, on le selectionne 

                    selectSurface(m);


                }
                
                else{ //si on a cliqué dessus et qu'il était déjà selectionné on le déselectionne

                    deselectSurface(m);
                    
                }
            } 
        });  

        this.getChildren().add(murlineHT.get(m));

        System.out.println("Point affiché");

        foobar.setOnMouseExited(e -> {  //si la souris n'est plus sur la ligne et que ctrl n'est pas selectionné, on remet le trait en noir
            if (m.isSelected == false){
                setColor(foobar, Color.BLACK);
            } //si on quitte le point de la souris et qu'il n'est pas selectionné, on le met en noir
        }); //on remet la couleur en noir

        if(m.door == true){DisplayPorte(m);}

    }

    public void selectSurface(Mur m)
    {

            setColor(murlineHT.get(m), Color.GREENYELLOW);
            this.selectedSurfaces.add(m);
            m.isSelected = true;//on le def comme selectionné
            this.parentPane.log.setTxt("mur(s) selectionné(s)");
        
    }

    public void deselectSurface(Mur m)
    {

            setColor(murlineHT.get(m), Color.BLACK);
            selectedSurfaces.remove(m);
            m.isSelected = false;
        
    }

    public void deselectSurface(int ind)
    {
        if(this.selectedSurfaces.get(ind) instanceof Mur)
        {
            Mur M = (Mur) this.selectedSurfaces.get(ind);
            setColor(murlineHT.get(M), Color.BLACK);
            selectedSurfaces.remove(this.selectedSurfaces.get(ind));
            M.isSelected = false;
        }
    }

    public void translateAllToZero()
    {
        this.globalTranslate.setX(0);
        this.globalTranslate.setY(0);

        for(Node o : this.getChildren())
        {
            if(o instanceof Shape)
            {
                o.setTranslateX(0);
                o.setTranslateY(0);
            }
        }
    }

    public void DisplayPorte(Porte p){
        Polygon rectangle = new Polygon(); //on utilise des polygone pour faire des rectangles blanc et non des rectangles directement, car c'est plus simple pour les aligner avec le mur 
        double l, L; //pas utiles, juste plus court pour les formules trigo d'après

        double t =0;
        double x1 = p.mur.getDebut().getX(); //on récup les coords des 2 pts qui def le mur :
        double y1 = p.mur.getDebut().getY();
        double x2 = p.mur.getFin().getX();
        double y2 = p.mur.getFin().getY();
        double xp1, xp2,xp3,xp4; //abscisses des 4 points du rectangle qui va servir à afficher la porte
        double yp1,yp2,yp3,yp4; //idem

        l= 8.0; //a modifier au besoin = épaisseur du trait qui sert à tracer le mur 
        L = 82.0; //largeur de l'ouverture

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
        this.getChildren().add(rectangle); // on l'affiche sur le pane 
    }



    public void DisplayPorte(Mur mur){
        Polygon rectangle = new Polygon(); //on utilise des polygone pour faire des rectangles blanc et non des rectangles directement, car c'est plus simple pour les aligner avec le mur 
        double l, L; //pas utiles, juste plus court pour les formules trigo d'après

        double t =0;
        double x1 = mur.getDebut().getX(); //on récup les coords des 2 pts qui def le mur :
        double y1 = mur.getDebut().getY();
        double x2 = mur.getFin().getX();
        double y2 = mur.getFin().getY();
        double xp1, xp2,xp3,xp4; //abscisses des 4 points du rectangle qui va servir à afficher la porte
        double yp1,yp2,yp3,yp4; //idem

        l= 8.0; //a modifier au besoin = épaisseur du trait qui sert à tracer le mur 
        L = 82.0; //largeur de l'ouverture

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
        this.getChildren().add(rectangle); // on l'affiche sur le pane 
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
    
          // create a polygon
        Polygon polygon = new Polygon(points);
        polygon.setFill(couleur); //on colore le polygone avec la couleur du constructeur de la méthode
        this.getChildren().add(polygon); // on l'affiche sur le pane 

    }

    public void HilightRoom(Piece piece, Color couleur, double x, double y){
        //public void HilightRoom(){
        

        ArrayList<Coin> coinsPiece = new ArrayList<Coin>(); //arrayliste destinnée à contenir tous les points qui forment la pièce

        for (int i = 0; i <piece.murs.size(); i++) { //piece.murs = array liste qui contient tous les murs de la pièce
            coinsPiece.add(piece.murs.get(i).getDebut()); //on ajoute tous les points qui constituent la pièce 
    
            
        }
        double points[] = new double [coinsPiece.size()*2]; //tableau de taille 2 fois le nb de point pour stocker ttes les coords de tous les pts dans un tableau
        int j =0;
        for (int i = 0; i < coinsPiece.size(); i++) {  
            //on veut récup les coords x y du point dans la case i de l'array liste, ajouter ces coords aux pos j et j+1 du tableau. j avance de 2 en 2 et i de 1 en 1 pour ne pas sauter de points
            points[j] = coinsPiece.get(i).getX() + x; //en i on met la coord x du pt J
            points[j+1] = coinsPiece.get(i).getY() + y;//en i+1 on met la coord x du pt J
            j = j+2;
            
        }
        for (int i = 0; i < coinsPiece.size()*2 ; i++) {
            System.out.println("tab points : "+ points[i]);
            
        }
    
          // create a polygon
        Polygon polygon = new Polygon(points);
        polygon.setFill(couleur); //on colore le polygone avec la couleur du constructeur de la méthode
        this.getChildren().add(polygon); // on l'affiche sur le pane 

    }


    public void resetSelection()
    {
        this.selectedAppartement = null;
        this.selectedPiece = null;
        this.selectedSurfaces = new ArrayList<>();
        this.coinTab = new ArrayList<>();
        this.murArrayTab = new ArrayList<>();
    }

    public void setColor(Line l, Color couleur){
        l.setStroke(couleur);
    }

    public void DisplayPiece(Piece p, double x, double y)
    {
        for(int i = 0; i < p.murs.size(); i++)
        {

            DisplayMur(p.murs.get(i), x, y);
            
        }
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

    public void DisplayAppart(Appartement appart, double px, double py)
    {
        for(int i = 0; i < appart.pieces.size(); i++)
        {
            DisplayPiece(appart.pieces.get(i), px, py);
        }



    }

    public void DisplayNiveau(Niveau niv)
    {
        for(int i = 0; i < niv.appartements.size(); i++)
        {
            DisplayAppart(niv.appartements.get(i), niv.appartements.get(i).px, niv.appartements.get(i).py);
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
            DisplayMur((Mur) obj, 0, 0);
        }
        else if(obj instanceof Porte)
        {
            DisplayPorte((Porte) obj);
        }
        else if(obj instanceof Piece)
        {   
            DisplayPiece((Piece) obj, 0, 0);  
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