package insa.GUI;

import insa.GUI.ImmeubleHierarchy.ImmeubleHierarchy;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


public class MainPane extends BorderPane{

    boolean ctrlIsPressed;
    boolean pointAlreadyExist;
    boolean autoWallState;
    public boolean wallButtonState;
    int idOfCurrentSelectedPoint;
    
    Menus menuBar;
    ToolBar toolBar;
    ImmeubleHierarchy hierarchy;

    CheckBox autoWall;

    Button buttonMur;
    Button buttonPoint;
    Button buttonPorte;
    Button unselectButton;

    //Group canva;
    BorderPane viewerLayout = new BorderPane();
    protected CustomLog log; //on créer le custom log
    public DisplayCanvas canva;
    
    public String cataloguePath, projectPath;

    //protected Scene scene1 = new Scene(root, Color.GREY); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
    
    MainPane()
    {
        this.ctrlIsPressed = false;
        this.pointAlreadyExist = false;
        this.idOfCurrentSelectedPoint = 999;
        this.menuBar = new Menus(this);
        this.autoWall = new CheckBox("autoWall");
        this.autoWallState = false;
        this.wallButtonState = false;
        this.toolBar = new ToolBar();
        this.hierarchy = new ImmeubleHierarchy(this);
        this.buttonMur = new Button("Mur");
        this.buttonPoint = new Button("Point");
        this.buttonPorte = new Button("Porte");
        this.unselectButton = new Button("Unselect all");
        //this.canva = new Group();
        this.canva = new DisplayCanvas(this);
        this.viewerLayout = new BorderPane();
        this.log = new CustomLog(this);

        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.getItems().addAll(autoWall, buttonMur, buttonPoint, buttonPorte, unselectButton);

        this.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(0), Insets.EMPTY))); // j'ai pas trouvé plus simple pour changer la couleur de fond
        this.setRight(toolBar);
        this.setCenter(canva);
        this.setTop(menuBar);
        this.setLeft(hierarchy);

        log.Initialise(); //on ini le log

        this.setOnKeyPressed(e -> { //gestion de la detection de l'appui pour la selection
            if(e.getCode()== KeyCode.CONTROL){
                System.out.println("CTRL pressé");
                ctrlIsPressed = true; //si ctrl est appuyé, on met la variable correspondante à true.
                log.setTxt("Cliquer sur les éléments à selectionner en maintenant CTRL");
            }
        });

        this.setOnKeyReleased(e -> { //gestion de la detection du relachement de ctrl pour la sélection
            if(e.getCode()== KeyCode.CONTROL){
                System.out.println("CTRL relaché");
                ctrlIsPressed = false; //si ctrl est relaché, on met la variable correspondante à false.
                log.setTxt("Appuyez sur CTRL pour selectionner des élements");
            }
        });

        buttonMur.setOnAction(e ->{
            if (wallButtonState == false){
                wallButtonState = true;
                log.setTxt("Veuillez cliquer sur 2 points pour créer un mur");
               // Mur mur = new Mur(idOfCurrentSelectedPoint, null, null);
            }
        });

        autoWall.setOnAction(e -> {
            autoWallState= autoWall.isSelected();
            System.out.println("Etat de la case : " + autoWallState);
        });

        unselectButton.setOnAction(e->{
            //UnselectAll();
        });

        /*

        buttonPorte.setOnAction(e->{ //quand bouton porte cliqué
            if (iDOfSelectedWall.size() == 0){System.out.println("Veuillez selectionner au moins un mur avant de créer une porte");} //on vérif qu'il y a bien un mur selectionné
            else{ //si au moins un mur est selectionné :

                for (int i = 0; i < iDOfSelectedWall.size(); i++) { //pour i allant de 0 au nombre de mur selectionné, on crééer un porte qu'on ajoute dans le tableau, puis on affiche
                    doorTab.add(new Porte(doorTab.size(), wallTab.get(iDOfSelectedWall.get(i))));
                    doorTab.get(doorTab.size()-1).Display(root);  //on affiche la denière porte créée.
                }
                
            }


        });

        */

    }

    public void resetCanva()
    {
        this.canva = new DisplayCanvas(this);
    }
    
}
