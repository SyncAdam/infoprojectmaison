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

    boolean ctrlIsPressed; //variables pour quand on appuie sur CTRL
    boolean HIsPressed;
    boolean VIsPressed;

    boolean pointAlreadyExist;
    boolean autoWallState;
    public boolean wallButtonState; //Donne juste l'état des boutons pour pouvoir les utiliser ailleur
    public boolean roomButtonState; //Donne juste l'état des boutons pour pouvoir les utiliser ailleur
    public boolean modifyButtonState;//

    int idOfCurrentSelectedPoint;
    
    Menus menuBar;
    ToolBar toolBar;
    ImmeubleHierarchy hierarchy;

    CheckBox autoWall;

    Button buttonMur;
    //Button buttonPoint;
    Button buttonPorte;
    Button buttonPiece;
    Button unselectButton;
    Button modifyButton;

    //Group canva;
    BorderPane viewerLayout = new BorderPane();
    protected CustomLog log; //on créer le custom log
    public DisplayCanvas canva;
    
    public String cataloguePath, projectPath;

    //protected Scene scene1 = new Scene(root, Color.GREY); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
    
    MainPane()
    {
        this.ctrlIsPressed = false;
        this.HIsPressed =false;
        this.pointAlreadyExist = false;
        this.idOfCurrentSelectedPoint = 999;
        this.menuBar = new Menus(this);
        this.autoWall = new CheckBox("Automatique");

        this.autoWallState = false;
        this.wallButtonState = false;
        this.roomButtonState = false;
        this.modifyButtonState = false;
        this.toolBar = new ToolBar();

        this.hierarchy = new ImmeubleHierarchy(this);

        this.buttonMur = new Button("Mur");
        //this.buttonPoint = new Button("Point");
        this.buttonPorte = new Button("Porte");
        this.buttonPiece = new Button("Piece");
        this.unselectButton = new Button("Unselect all");
        this.modifyButton = new Button("Modifier");
        double witdh =  102;

        this.buttonMur.setMinWidth(witdh); //largeur des boutons : pour l'esthétique
        this.buttonPiece.setMinWidth(witdh);
        this.unselectButton.setMinWidth(witdh);
        this.modifyButton.setMinWidth(witdh);
        this.buttonPorte.setMinWidth(witdh);

        this.buttonMur.setStyle("-fx-background-color: #CAC6C6; "); //largeur des boutons : pour l'esthétique
        this.buttonPiece.setStyle("-fx-background-color: #CAC6C6; ");
        this.unselectButton.setStyle("-fx-background-color: #CAC6C6; ");
        this.modifyButton.setStyle("-fx-background-color: #CAC6C6; ");
        this.buttonPorte.setStyle("-fx-background-color: #CAC6C6; ");



        //this.canva = new Group();
        this.canva = new DisplayCanvas(this);
        this.viewerLayout = new BorderPane();
        this.log = new CustomLog(this);

        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setMinWidth(110);
        toolBar.getItems().addAll(autoWall, modifyButton, buttonMur, buttonPiece, buttonPorte, unselectButton);//on ajoute tout les boutons à la tool bar

        this.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(0), Insets.EMPTY))); // j'ai pas trouvé plus simple pour changer la couleur de fond
        this.setRight(toolBar);
        this.setCenter(canva);
        this.setTop(menuBar);
        this.setLeft(hierarchy);

        log.Initialise(); //on ini le log

        this.setOnKeyPressed(e -> { //gestion de la detection des touches
            if(e.getCode()== KeyCode.CONTROL){ //si CTRL est pressé
                System.out.println("CTRL pressé");
                ctrlIsPressed = true; //si ctrl est appuyé, on met la variable correspondante à true.
                log.setTxt("Cliquer sur les éléments à selectionner en maintenant CTRL");
            }
            if(e.getCode()== KeyCode.H){ //si h est pressé
                System.out.println("H pressé");

                if (HIsPressed == true){HIsPressed =false ;log.setTxt("Mode horizontal désactivé");//si on rappui sur H, on quitte l'outils
                }else{ //Si H n'est pas pressé on entre dans l'outils 
                    HIsPressed = true; //si h est appuyé, on met la variable correspondante à true.
                    VIsPressed =false ; //evite de bugguer si V était encore activé
                    log.setTxt("Mode horizontal activé");
                }
            }
            if(e.getCode()== KeyCode.V){ //si V est pressé
                System.out.println("V pressé");

                if (VIsPressed == true){VIsPressed =false ;log.setTxt("Mode vertical désactivé");//si on rappui sur V, on quitte l'outils
                }else{ //Si V n'est pas pressé on entre dans l'outils 
                    VIsPressed = true; //si h est appuyé, on met la variable correspondante à true.
                    HIsPressed =false ; //evite de bugguer si H était encore activé
                    log.setTxt("Mode vertical activé");
                }
            }
        });

        this.setOnKeyReleased(e -> { //gestion des actions clavier
            if(e.getCode()== KeyCode.CONTROL){
                System.out.println("CTRL relaché");
                ctrlIsPressed = false; //si ctrl est relaché, on met la variable correspondante à false.
                log.setTxt("Appuyez sur CTRL pour selectionner des élements");
                
            }
 
        });

        buttonMur.setOnAction(e ->{ //actualise juste la bonne variable pour pouvoir les actions adéquates (cf Display Canva)
            if (wallButtonState == false){
                buttonMur.setStyle("-fx-background-color: #4AE87D; "); //colo en vert du bouton
                
                wallButtonState = true;
                log.setTxt("Veuillez cliquer sur 2 points pour créer un mur");
               // Mur mur = new Mur(idOfCurrentSelectedPoint, null, null);

               
            }else{
                buttonMur.setStyle("-fx-background-color: #CAC6C6; "); //colo en gris défaut
                wallButtonState = false;
                log.setTxt("Vous quittez l'outils mur");}
            
        });

        modifyButton.setOnAction(e->{
            if(modifyButtonState == false){
                
                log.setTxt("[Mode modification] - Cliquer sur un point pour le modifier");
                modifyButton.setStyle("-fx-background-color: #4AE87D; "); //colo en vert du bouton
                modifyButtonState = true;
            }else{
                modifyButtonState = false;log.setTxt("Vous venez de quitter l'outils de modification");
                modifyButton.setStyle("-fx-background-color: #CAC6C6; "); //colo en gris défaut
            }
            
        });



        



        autoWall.setOnAction(e -> {
            autoWallState= autoWall.isSelected();
            System.out.println("Etat de la case : " + autoWallState);
        });

       

        


        

    }
    
}
