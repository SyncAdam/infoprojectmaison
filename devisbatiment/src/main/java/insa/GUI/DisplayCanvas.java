package insa.GUI;

import javafx.scene.control.Alert;
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
import insa.Batiment.Surface;

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
    public ArrayList<Surface> selectedSurfaces; //sert juste à garder en mémoire les murs de la pièce que l'on veut créer avec l'outils pièce
    public double[] coordsOfHilightedPoint = new double[2]; //utile pour quand on a le mode vertical ou horizon activé et qu'on veut mettre un point alligné avec un point qui existe déjà pour faire un carré
    public Piece selectedPiece;

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
        this.selectedPiece = null;
       
        this.doorTabList = new ArrayList<Porte>();
    
        this.selectedPoint = new ArrayList<Coin>();
        this.selectedSurfaces = new ArrayList<Surface>();

        /*

        this.drawingCanvas = new Canvas(this.getWidth(), this.getHeight());
        this.getChildren().add(this.drawingCanvas);
        this.drawingCanvas.heightProperty().bind(this.heightProperty());
        this.drawingCanvas.setOnMouseClicked((t) -> {

        });   

        */

        parentPane.buttonPiece.setOnAction(e->{ //quand on veut crééer une pièce avec l'outils pièce
        
           
                if(selectedSurfaces.size()<3){parentPane.log.setTxt("Vous ne pouvez pas créer une pièce de moins de 3 murs, veuillez en selectionner plus !"); parentPane.log.setColor(Color.RED);}
                else{
                parentPane.log.setTxt("Création d'une pièce de " + selectedSurfaces.size() + " murs");
                bufferMurPiece.clear();//on vide le buffer
                for (int index = 0; index < selectedSurfaces.size(); index++) { //de i aux nbr de mur selectionnés on :

                    bufferMurPiece.add(selectedSurfaces.get(index));//ajoute à bufferMurpièce le mur d'id à la position i dand IdOfSelectedWall
                    wallTab.remove(selectedSurfaces.get(index));

                }
                pieceTab.add(new Piece(pieceTab.size()-1, bufferMurPiece)); //on créer la pièce avec les murs selectionnés
                bufferMurPiece.clear(); //on vide le buffer
            }
 
        });




        parentPane.buttonPorte.setOnAction(e->{ //quand bouton porte cliqué
            if (selectedSurfaces.size() == 0){parentPane.log.setTxt("Veuillez selectionner au moins un mur avant de créer une porte");} //on vérif qu'il y a bien un mur selectionné
            else{ //si au moins un mur est selectionné :

                for (int i = 0; i < selectedSurfaces.size(); i++) { //pour i allant de 0 au nombre de mur selectionné, on crééer un porte qu'on ajoute dans le tableau, puis on affiche
                    if(selectedSurfaces.get(i) instanceof Mur)
                    {
                        doorTabList.add(new Porte(doorTabList.size(), (Mur)selectedSurfaces.get(i))); //on créer la porte

                        DisplayPorte(doorTabList.get(doorTabList.size()-1));  //on affiche la denière porte créée.
                    }    
                }
                
            }


        });


        this.setOnMouseClicked(e -> { //détection du clic + récup coords
            if (parentPane.ctrlIsPressed == false && this.parentPane.projectOpened == true && this.parentPane.modifyButtonState == false){
                 //sert juste à ne pas crééer de nouveau éléments lorsqu'on veut juste selectionner un point/mur avec ctrl
                System.out.println(parentPane.pointAlreadyExist + " vrai");
                if (parentPane.pointAlreadyExist == false){
                    
                    if (parentPane.autoWallState == false) { //si on clique et que autowall est off :

                        if(parentPane.HIsPressed == true){ //si on clique et que H est pressé, on récup la coord le Y du pt d'avant et le X du clic
                            this.coinTab.add(new Coin(this.coinTab.size(), e.getX(), coinTab.get(coinTab.size()-1).getY())); //on créér le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                            parentPane.log.setTxt("Vous venez de créer un point d'ID : " + Integer.toString(coinTab.size()-1) );
                            
                        }else if(parentPane.VIsPressed == true){ //si V est pressé, on fait comme pour H mais à l'inverse
                            this.coinTab.add(new Coin(this.coinTab.size(), coinTab.get(coinTab.size()-1).getX(), e.getY())); //on créér le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                            parentPane.log.setTxt("Vous venez de créer un point d'ID : " + Integer.toString(coinTab.size()-1) );

                        }else{ //si ni V ni H n'est pas pressé on place le point à l'endroit du clic
                
                            this.coinTab.add(new Coin(this.coinTab.size(), e.getX(), e.getY())); //on créé le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                            parentPane.log.setTxt("Vous venez de créer un point d'ID : " + Integer.toString(coinTab.size()-1) );

                            /*NOTE : si on a un seul élement dans l'array list, sont index est 0, mais quand on demande la taille (.size()),
                            * on obtient 1, d'où le "-1", pour faire coller à la taille.
                            */

                        

                    }
                }
                    if (parentPane.autoWallState == true){ //si autowall activé :
                        if(parentPane.HIsPressed == true){ //si on clique et que H est pressé, on récup la coord le Y du pt d'avant et le X du clic
                            this.coinTab.add(new Coin(this.coinTab.size(), e.getX(), coinTab.get(coinTab.size()-1).getY())); //on créér le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                            wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1))); //on créér un mur à partir des 2 dernies coins du coinTab
                            /*NOTE : coinTab.size()-1 correspond à l'index du dernier point et coinTab.size()-2, à celui de l'avant dernier */

                            DisplayMur(wallTab.get(wallTab.size()-1));
                            System.out.println("Le coin d'id " + coinTab.get(coinTab.size()-1).getId()+ "est à la pos " + (coinTab.size()-1));
                        }else if(parentPane.VIsPressed == true){ //si V est pressé, on fait comme pour H mais à l'inverse
                            this.coinTab.add(new Coin(this.coinTab.size(), coinTab.get(coinTab.size()-1).getX(), e.getY())); //on créér le new point dans le tableau
                            DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
                            wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1))); //on créér un mur à partir des 2 dernies coins du coinTab
                                                    
                            DisplayMur(wallTab.get(wallTab.size()-1));
                            System.out.println("Le coin d'id " + coinTab.get(coinTab.size()-1).getId()+ "est à la pos " + (coinTab.size()-1));

                        }else{ //si ni V ni H n'est pas pressé on place le point à l'endroit du clic
                
                            coinTab.add(new Coin(coinTab.size(), e.getX(), e.getY())); //on créé le new point dans le tableau avec comme id sa position dans le tableau
                            DisplayCoin(coinTab.get(coinTab.size()-1)); //on l'affiche (le dernier pt du coinTab)
                        
                            if(coinTab.size() > 1) wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1))); //on créér un mur à partir des 2 dernies coins du coinTab

                            /*NOTE : coinTab.size()-1 correspond à l'index du dernier point et coinTab.size()-2, à celui de l'avant dernier */
                            DisplayMur(wallTab.get(wallTab.size()-1)); //on l'affcihe
                            
                            System.out.println("Mur affiché");
                            
                            System.out.println("Le coin d'id " + coinTab.get(coinTab.size()-1).getId()+ "est à la pos " + (coinTab.size()-1));
                    
                     }
                }
                }

                if(parentPane.pointAlreadyExist ==true){ //si ya déjà un point

                    if (parentPane.autoWallState == false && parentPane.wallButtonState == false && parentPane.HIsPressed == false && parentPane.VIsPressed == false &&parentPane.modifyButtonState == false ){ //si on est pas dans un outils spécial et qu'on veut crééer un pr où il y en a déjà
                        
                            System.out.println("Vous ne pouvez pas placer un point ici ! ") ;
                        
                        parentPane.log.setTxt("Vous ne pouvez pas placer de point ici !");
                        parentPane.log.setColor(Color.RED);
                    }

                    if(parentPane.autoWallState == true){ //si autowall est activé et qu'il y a déjà un point : on fait un mur avec le dernier point créé et le point selectionné
                    
                        coinTab.add(coinTab.size(), coinTab.get(parentPane.idOfCurrentSelectedPoint));                        
                    
                        wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1)));

                        DisplayMur(wallTab.get(wallTab.size()-1));//on l'affiche
                    

                        /*Raisonnement de ce que j'ai fait (21/04): quand on clique 2 fois sur un coin, c'est forcément que l'on fait une pièce (du moins si le auto wall a tjrs été activé),
                        * Donc on va parcourir tous les murs de walltab en partant du dernier jusquà trouver un point commun avec le dernier mur
                        *placé. Et a chaque fois, on ajoute dans une liste de mur de l'array liste de liste de mur. (oui une array liste de liste !)
                        */

                        //==================================================
                        //Tout ca existe deja dans piece mais un peu plus efficacement
                        //==================================================

                        

                        // on va commencer par vérifier l'avant dernier mur, sinon la boucle while sort dès la première itération
                        
                        murArrayTab.add(new ArrayList<Mur>()); //on créer un arraylist de mur dans l'arraylist d'arraylist de mur
                        murArrayTab.get(murArrayTab.size()-1).add(wallTab.get(wallTab.size()-1)); //on ajoute le dernier mur crééé dans l'array liste de l'array liste d'array liste de mur, car il ne sera pas ajouté par la boucle while

                            /*
                            while (wallTab.get(i).getIdDebut() != parentPane.idOfCurrentSelectedPoint && wallTab.get(i).getIdFin() != parentPane.idOfCurrentSelectedPoint){ //tant que les 2 coins du mur i sont différents du currentidpoint on continue
                                
                                murArrayTab.get(murArrayTab.size()-1).add(wallTab.get(i)); //on ajoute au tableau dans le tableau mur i de la pièce
                                j++;
                                 //condition de si on a parcouru tous les murs et q'on est pas sorti du while
                                //POUR PLUS TARD : EXEPTION QUI Détecte QUAND ERREUR DANS LA BOUCLE --> LA PI7CE N'EST PAS FERMée
                            }
                            */
                            
                            ArrayList<Object> temp = new ArrayList<>();
                            for(int i = wallTab.size()-1; i >= 0; i--)
                            {
                                temp.add((Object) wallTab.get(i));
                                wallTab.remove(i);
                            }
                                //pieceTab.add(new Piece(murArrayTab.size()-1, temp)); // on ajoute dans l'arrayList de pièce la new pièce créée                        
                            this.parentPane.hierarchy.nonClassePieces.add(new Piece(murArrayTab.size()-1, temp));
                            this.parentPane.hierarchy.hierarchyRefresh();
                            if(this.selectedPiece != null)
                            {
                               HilightRoom(this.selectedPiece, this.parentPane.backgroundColor);
                            }
                            this.selectedPiece = this.parentPane.hierarchy.nonClassePieces.get(this.parentPane.hierarchy.nonClassePieces.size()-1);
                                                        //pieceTab.get(pieceTab.size()-1).toString();
                            HilightRoom(this.parentPane.hierarchy.nonClassePieces.get(this.parentPane.hierarchy.nonClassePieces.size()-1), Color.web("#eff704", 0.3)); 

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
    }

    /*
    public void concatenateTransform(Transform trans) {
        Transform oldTrans = this.drawingCanvas.getGraphicsContext2D().getTransform();
        Transform newTrans = oldTrans.createConcatenation(trans);
        this.setTransform(newTrans);
    }

    public void setTransform(Transform trans) {
        this.drawingCanvas.getGraphicsContext2D().setTransform(new Affine(trans));
    }

    public Transform getTransform() {
        return this.drawingCanvas.getGraphicsContext2D().getTransform();
    }
    */

    public void showNoProjectOpenedPopup()
    {
        Alert albert = new Alert(Alert.AlertType.WARNING);
        albert.setTitle("Project Error");
        albert.setHeaderText("Aucun projet est ouvert");
        albert.setContentText("Ouvrez un projet d'abord.");
        albert.showAndWait();
    }

    public void DisplayCoin(Coin c)
    {
        Circle circle1 = new Circle(c.getX(), c.getY(), 7);
        this.getChildren().add(circle1);
        
        circle1.setOnMouseEntered(e -> { //on détecte quand la souris passe sur un point (POUR PLUS TARD : EFFET MAGNET ?)
      
            circle1.setFill(Color.BLUE); 
            this.parentPane.pointAlreadyExist = true;  //empeche de pouvoir placer un point au même eendroit (cf classe app)
            this.parentPane.idOfCurrentSelectedPoint = c.getId(); //on actualise cette variable avec l'id du point selectionné
            coordsOfHilightedPoint[0]= c.getX() ; //on rentre les coords du point survolé pour pouvoir faire un carré avec mode H ou V
            coordsOfHilightedPoint[1]= c.getY() ;

            System.out.println("L'id du point selectionné est" + this.parentPane.idOfCurrentSelectedPoint);
            parentPane.log.setTxt("ID : " + this.parentPane.idOfCurrentSelectedPoint);
            
        }); 

        circle1.setOnMouseExited(e -> {

                circle1.setFill(Color.BLACK); 
                this.parentPane.pointAlreadyExist = false;
                System.out.println(this.parentPane.pointAlreadyExist);

        }); 

        circle1.setOnMouseClicked(e -> {
            if (parentPane.modifyButtonState == true && c.onAWall == false && parentPane.HIsPressed == false && parentPane.VIsPressed == false ){
                circle1.setRadius(0); // j'ai pas trouvé comment le faire s'auto-détruire, donc on réduit ça taille à 0 pour le faire disparaitre
                PointModifier pointModifier = new PointModifier(this.parentPane, c);
                pointModifier.Initialise();
                
                
            } 
            if(c.onAWall == true)
            {
                this.parentPane.log.setTxt("Vous ne pouvez pas modifier un coin qui est deja sur un murs");
                this.parentPane.log.setColor(Color.RED);
            }

            if(parentPane.HIsPressed == true  || parentPane.VIsPressed == true)
            {
                this.parentPane.log.setTxt("Veuillez désactiver le mode horizontal ou vertical pour modifier un coin");
                this.parentPane.log.setColor(Color.RED);
            }
            
            if (this.parentPane.wallButtonState == true){ //si on a cliqué sur le bouton pour créer un mur et que l'on clique sur un point qui existe déjà, on l'ajoute à selected point
                circle1.setFill(Color.RED);
                this.selectedPoint.add(c);
                if (selectedPoint.size() == 1){parentPane.log.setTxt("Veuillez cliquer sur un deuxième point");}
                if (selectedPoint.size() >= 2){ //si on a déjà un point qui a été cliqué alors :
                    wallTab.add(new Mur(wallTab.size(), selectedPoint.get(0), selectedPoint.get(1))); //on créér un nouveau mur avec les 2 pts selectionés
                    DisplayMur(wallTab.get(wallTab.size()-1)); //on affiche le mur nouvellement créé
                    selectedPoint.clear(); //on vide les murs de selected point
                    selectedPoint.clear();
                    parentPane.log.setTxt("Vous venez de créer un nouveau mur, veuillez en selectionner 2 autres");
                }
            }

            if(parentPane.HIsPressed == true && parentPane.modifyButtonState == false){ //si on clique sur un point qui existe déjà avec le mode horizontale activé on va récupérer le y du pt d'avant et le x du point cliqué puis  crééer un new point
                this.coinTab.add(new Coin(this.coinTab.size(), coordsOfHilightedPoint[0], coinTab.get(coinTab.size()-1).getY())); //on créér le new point dans le tableau
                DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
            } else if(parentPane.VIsPressed == true  && parentPane.modifyButtonState == false){//si on clique sur un point qui existe déjà avec le mode vertical activé on va récupérer le x du pt d'avant et le y du point cliqué puis  crééer un new point
                this.coinTab.add(new Coin(this.coinTab.size(), coinTab.get(coinTab.size()-1).getX(),coordsOfHilightedPoint[1])); //on créér le new point dans le tableau
                DisplayCoin(this.coinTab.get(this.coinTab.size()-1)); //on l'affiche
            }

        });
    }
    

    public void DisplayMur(Mur m){
        DisplayCoin(m.getDebut());
        DisplayCoin(m.getFin());
    
        m.ligne.setOnMouseEntered(e -> { //on détecte quand la souris passe la ligne (POUR PLUS TARD : EFFET MAGNET ?)
            
            if (m.superpositionState == false){
                m.superpositionState = true;
            }
            else{

                System.out.println("superposition ligne"); //permet de ne pas la selectionner dès sa création
                if(this.parentPane.ctrlIsPressed ==true && m.isSelected == false){
                    setColor(m.ligne, Color.RED);
                } //si on est en mode selection (ctrl pressé) et que le mur n'est pas selectionné, on colore en rouge au survol
            
                m.ligne.setOnMouseClicked(event -> { //si la souris est sur la ligne et clique :

                    if(this.parentPane.ctrlIsPressed ==true){ //clique + ctrl :
                        if(m.isSelected == false){//si le mur n'est pas déjà selectionné, on le selectionne 

                            selectSurface(m);
                            if(parentPane.buttonPorteState == true)
                            {                                       //si on clique sur un mur et qu'on est en mode porte :
                                System.out.println("portte crée");
                                doorTabList.add(new Porte(doorTabList.size(), m)); 
                                DisplayPorte(doorTabList.get(doorTabList.size()-1));
                            }   

                        }
                        
                        else{ //si on a cliqué dessus et qu'il était déjà selectionné on le déselectionne

                            deselectSurface(m);
                            
                        }
                    } 
                });  
                
                
        
            }
        }); 

        this.getChildren().add(m.ligne);

        System.out.println("Point affiché");

        m.ligne.setOnMouseExited(e -> {  //si la souris n'est plus sur la ligne et que ctrl n'est pas selectionné, on remet le trait en noir
            if (m.isSelected == false){
                setColor(m.ligne, Color.BLACK);
            } //si on quitte le point de la souris et qu'il n'est pas selectionné, on le met en noir
        }); //on remet la couleur en noir
    }

    public void selectSurface(Surface m)
    {
        if(m instanceof Mur)
        {
            Mur M = (Mur) m;
            setColor(M.ligne, Color.GREENYELLOW);
            this.selectedSurfaces.add(m);
            M.isSelected = true;//on le def comme selectionné
            this.parentPane.log.setTxt("mur(s) selectionné(s)");
        }
    }

    public void deselectSurface(Surface m)
    {
        if(m instanceof Mur)
        {
            Mur M = (Mur) m;
            setColor(M.ligne, Color.BLACK);
            selectedSurfaces.remove(m);
            M.isSelected = false;
        }
    }

    public void deselectSurface(int ind)
    {
        if(this.selectedSurfaces.get(ind) instanceof Mur)
        {
            Mur M = (Mur) this.selectedSurfaces.get(ind);
            setColor(M.ligne, Color.BLACK);
            selectedSurfaces.remove(this.selectedSurfaces.get(ind));
            M.isSelected = false;
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
        else if(obj instanceof Porte)
        {
            DisplayPorte((Porte) obj);
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