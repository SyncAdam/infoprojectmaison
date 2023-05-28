package insa.GUI;

import java.util.ArrayList;

import insa.Batiment.Mur;
import insa.Batiment.Piece;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

public class OurToolBar extends Pane{

    MainPane parentPane;

    Button autoWall; //déclaration de tous les boutons de la barre d'outils
    Button buttonMur;
    Button buttonPorte;
    Button buttonPiece;
    Button unselectButton;
    Button modifyButton;
    Button moveLeft;
    Button moveRight;
    Button moveUp;
    Button moveDown;

    boolean autoWallState; //variables qui servent à détecter l'état des boutons pour effectuer les bonne interactions
    boolean wallButtonState;
    boolean roomButtonState;
    boolean modifyButtonState;
    boolean buttonPorteState;
    boolean mouseIsInTheToolBar;

    BorderPane navigateBorderPane;

    ToolBar toolBar;

    double translateFactor;

    OurToolBar(MainPane parentPane)
    {
        this.toolBar = new ToolBar();//création de la toolBar

        this.autoWallState = false; //initialisation des variables
        this.wallButtonState = false;
        this.roomButtonState = false;
        this.modifyButtonState = false;
        this.buttonPorteState  = false;
        this.mouseIsInTheToolBar = false;


        this.buttonMur = new Button("Mur"); //initilaisation de boutons
        this.autoWall = new Button("Automatique");
        this.buttonPorte = new Button("Porte");
        this.buttonPiece = new Button("Piece");
        this.unselectButton = new Button("Unselect all");
        this.modifyButton = new Button("Modifier");
        this.moveLeft= new Button("<");
        this.moveRight = new Button(">");
        this.moveUp = new Button("^");
        this.moveDown = new Button("v");

        this.parentPane = parentPane;

        double width =  102; //largeur des boutons

        
        
        this.toolBar.setMinHeight(10000);
      

        this.buttonMur.setMinWidth(width); //largeur des boutons : pour l'esthétique
        this.buttonPiece.setMinWidth(width);
        this.unselectButton.setMinWidth(width);
        this.modifyButton.setMinWidth(width);
        this.buttonPorte.setMinWidth(width);
        this.autoWall.setMinWidth(width);
        this.moveLeft.setMinWidth(10);
        this.moveRight.setMinWidth(10);
        this.moveUp.setMinWidth(10);
        this.moveDown.setMinWidth(10);

        this.buttonMur.setStyle("-fx-background-color: #CAC6C6; "); //design bouttons : pour l'esthétique
        this.buttonPiece.setStyle("-fx-background-color: #CAC6C6; ");
        this.unselectButton.setStyle("-fx-background-color: #CAC6C6; ");
        this.modifyButton.setStyle("-fx-background-color: #CAC6C6; ");
        this.buttonPorte.setStyle("-fx-background-color: #CAC6C6; ");
        this.autoWall.setStyle("-fx-background-color: #CAC6C6; ");
        this.moveLeft.setStyle("-fx-background-color: #CAC6C6; ");
        this.moveRight.setStyle("-fx-background-color: #CAC6C6; ");
        this.moveUp.setStyle("-fx-background-color: #CAC6C6; ");
        this.moveDown.setStyle("-fx-background-color: #CAC6C6; ");

        this.navigateBorderPane = new BorderPane(); //affichage des boutons de déplacement en croix
        navigateBorderPane.setLeft(moveLeft);
        navigateBorderPane.setRight(moveRight);
        navigateBorderPane.setTop(moveUp);
        navigateBorderPane.setBottom(moveDown);

        this.translateFactor = 30.0d;

        navigateBorderPane.setAlignment(moveUp, Pos.CENTER); //on centre les boutons ^ et v pour l'esthétique
        navigateBorderPane.setAlignment(moveDown, Pos.CENTER);

        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setMinWidth(110);
        toolBar.getItems().addAll(autoWall, modifyButton, buttonMur, buttonPiece, buttonPorte, unselectButton, navigateBorderPane);//on ajoute tout les boutons à la tool bar

        this.getChildren().add(toolBar);

        this.moveLeft.setOnAction(event -> { //gestion déplacement avec flèches
            for(Node o : this.parentPane.canva.getChildren())
            {
                if(o instanceof Shape)
                {
                    o.setTranslateX(o.getTranslateX() + translateFactor);
                }
            }
            this.parentPane.canva.globalTranslate.setX(this.parentPane.canva.globalTranslate.getX() + translateFactor);
            System.out.println(this.parentPane.canva.globalTranslate.getX());
        });

        this.moveRight.setOnAction(event -> {//gestion déplacement avec flèches
            for(Node o : this.parentPane.canva.getChildren())
            {
                if(o instanceof Shape)
                {
                    o.setTranslateX(o.getTranslateX() - translateFactor);
                }
            }
            this.parentPane.canva.globalTranslate.setX(this.parentPane.canva.globalTranslate.getX() - translateFactor);
            System.out.println(this.parentPane.canva.globalTranslate.getX());
        });

        this.moveUp.setOnAction(event -> {//gestion déplacement avec flèches
            for(Node o : this.parentPane.canva.getChildren())
            {
                if(o instanceof Shape)
                {
                    o.setTranslateY(o.getTranslateY() + translateFactor);
                }
            }
            this.parentPane.canva.globalTranslate.setY(this.parentPane.canva.globalTranslate.getY() + translateFactor);
            System.out.println(this.parentPane.canva.globalTranslate.getY());
        });

        this.moveDown.setOnAction(event -> {//gestion déplacement avec flèches
            for(Node o : this.parentPane.canva.getChildren())
            {
                if(o instanceof Shape)
                {
                    o.setTranslateY(o.getTranslateY() - translateFactor);
                }
            }
            this.parentPane.canva.globalTranslate.setY(this.parentPane.canva.globalTranslate.getY() - translateFactor);
            System.out.println(this.parentPane.canva.globalTranslate.getY());
        });

        this.unselectButton.setOnAction(event -> {


            for (int i = 0; i <  parentPane.canva.selectedSurfaces.size(); i++) { //on remet toutes les lignes en noir
                this.parentPane.canva.murlineHT.get(parentPane.canva.selectedSurfaces.get(i)).setStroke(Color.BLACK);
            }
            this.parentPane.canva.selectedSurfaces.clear();

            
            
        });

        this.buttonPiece.setOnAction(event -> {
            ArrayList<Object> temp = new ArrayList<Object>();

            if(parentPane.canva.selectedSurfaces.size() <3){
                parentPane.log.setTxt("Veuillez selectionner plus de mur pour créer une pièce !");
                parentPane.log.setColor(Color.RED);
            }else{

            while(this.parentPane.canva.selectedSurfaces.size() != 0)
            {
                


                for (int i = 0; i <  parentPane.canva.selectedSurfaces.size(); i++) { //on remet toutes les lignes en noir
                    this.parentPane.canva.murlineHT.get(parentPane.canva.selectedSurfaces.get(i)).setStroke(Color.BLACK);
                }

                temp.add(this.parentPane.canva.selectedSurfaces.get(0));
                this.parentPane.canva.selectedSurfaces.remove(0);
                
            }

            if(this.parentPane.canva.selectedAppartement != null)
            {
                int nextindex = 0;
                boolean idAvailable = false;

                ArrayList<Object> var = new ArrayList<>();
                if(this.parentPane.canva.selectedAppartement == null)
                {
                    for(Object o : temp)
                    {
                        if(o instanceof Mur)
                        {
                            Mur m = (Mur) o;
                            m.getDebut().setX(m.getDebut().getX() - this.parentPane.canva.selectedAppartement.px);
                            m.getDebut().setY(m.getDebut().getY() - this.parentPane.canva.selectedAppartement.py);
                            var.add(m);
                        }
                    }
                }
                else
                {
                    for(Object o : temp)
                    {
                        if(o instanceof Mur)
                        {
                            Mur m = (Mur) o;
                            m.getDebut().setX(m.getDebut().getX());
                            m.getDebut().setY(m.getDebut().getY());
                            var.add(m);
                        }
                    }
                }
                

                temp = var;
                
                while (!idAvailable) {
                    boolean found = false;
                    for (Piece p : this.parentPane.canva.selectedAppartement.pieces) {
                        if (p.getPieceId() == nextindex) {
                            found = true;
                            break;
                        }
                    }

                    if(!found)
                    {
                        idAvailable = true;
                    } 
                    else 
                    {
                        nextindex++;
                    }

                }
                Piece p = new Piece(nextindex, temp);
                this.parentPane.canva.selectedAppartement.addPiece(p);
                this.parentPane.canva.selectedPiece = p;
                this.parentPane.canva.HilightRoom(p, Color.web("#eff704", 0.3), this.parentPane.canva.globalTranslate.getX(), this.parentPane.canva.globalTranslate.getY());
            }
            else if(this.parentPane.canva.selectedAppartement == null)
            {
                Piece pi = new Piece(0, temp);
                this.parentPane.hierarchy.nonClassePieces.add(pi);
                this.parentPane.canva.HilightRoom(pi, Color.web("#eff704", 0.3), this.parentPane.canva.globalTranslate.getX(), this.parentPane.canva.globalTranslate.getY());
            }
            
            
            this.parentPane.hierarchy.hierarchyRefresh();



        } 
        });

        buttonMur.setOnAction(e ->{ //actualise juste la bonne variable pour pouvoir les actions adéquates (cf Display Canva)
            if (wallButtonState == false){
                buttonMur.setStyle("-fx-background-color: #4AE87D; "); //colo en vert du bouton
                
                wallButtonState = true;
                this.parentPane.log.setTxt("Veuillez cliquer sur 2 points pour créer un mur");
               // Mur mur = new Mur(idOfCurrentSelectedPoint, null, null);

               
            }else{
                buttonMur.setStyle("-fx-background-color: #CAC6C6; "); //colo en gris défaut
                wallButtonState = false;
                this.parentPane.log.setTxt("Vous quittez l'outils mur");}
            
        });

        modifyButton.setOnAction(e->{
            if(modifyButtonState == false){
                
                this.parentPane.log.setTxt("[Mode modification] - Cliquer sur un point ou un mur pour le modifier");
                modifyButton.setStyle("-fx-background-color: #4AE87D; "); //colo en vert du bouton
                modifyButtonState = true;
            }else{
                modifyButtonState = false;this.parentPane.log.setTxt("Vous venez de quitter l'outils de modification");
                modifyButton.setStyle("-fx-background-color: #CAC6C6; "); //colo en gris défaut
            }

        });

        buttonPorte.setOnAction(e->{
            if(buttonPorteState == false)
            {
                buttonPorteState = true;
                buttonPorte.setStyle("-fx-background-color: #4AE87D; "); //colo en vert du bouton
                this.parentPane.log.setTxt("Veuillez cliquer sur un mur pour ajouter une porte");
            }
            else
            {
                buttonPorteState = false; 
                buttonPorte.setStyle("-fx-background-color: #CAC6C6; ");
                this.parentPane.log.setTxt("Vous avez quitté le mode porte");
            }

            /*if (this.parentPane.canva.selectedSurfaces.size() == 0){parentPane.log.setTxt("Veuillez selectionner au moins un mur avant de créer une porte");} //on vérif qu'il y a bien un mur selectionné
            else{ //si au moins un mur est selectionné :

                for (int i = 0; i < this.parentPane.canva.selectedSurfaces.size(); i++) { //pour i allant de 0 au nombre de mur selectionné, on crééer un porte qu'on ajoute dans le tableau, puis on affiche
                    if(this.parentPane.canva.selectedSurfaces.get(i) instanceof Mur)
                    {
                        this.parentPane.canva.doorTabList.add(new Porte(this.parentPane.canva.doorTabList.size(), (Mur)this.parentPane.canva.selectedSurfaces.get(i))); //on créer la porte

                        this.parentPane.canva.DisplayPorte(this.parentPane.canva.doorTabList.get(this.parentPane.canva.doorTabList.size()-1));  //on affiche la denière porte créée.
                    }    
                }
                
            }*/

        });

        autoWall.setOnAction(e -> {
            if(autoWallState == false){
                autoWallState = true;
                autoWall.setStyle("-fx-background-color: #4AE87D; "); //colo en vert du bouton
                this.parentPane.log.setTxt("Mode auto activé, veuillez cliquer sur l'écran pour ajouter coin + mur");

            }else{autoWallState = false; 
                autoWall.setStyle("-fx-background-color: #CAC6C6; ");
            this.parentPane.log.setTxt("Vous avez quitté le mode auto");}

        });

        this.toolBar.setOnMouseEntered(e->{ //empèche de placer un point quand on clique sur un bouton
            mouseIsInTheToolBar = true;
            
        });
        
         this.toolBar.setOnMouseExited(e->{
            mouseIsInTheToolBar = false;
            
        });
        
    }
    
}
