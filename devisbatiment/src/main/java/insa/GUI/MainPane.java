package insa.GUI;

import insa.GUI.ImmeubleHierarchy.ImmeubleHierarchy;
import javafx.geometry.Insets;
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
    public boolean buttonPorteState;

    int idOfCurrentSelectedPoint;

    Menus menuBar;
    OurToolBar toolbar;
    ImmeubleHierarchy hierarchy;

    BorderPane viewerLayout = new BorderPane();
    public CustomLog log; //on créer le custom log
    public DisplayCanvas canva;

    public boolean projectOpened;
    
    public String cataloguePath, projectPath;
    
    public final Color backgroundColor;

    MainPane()
    {
        this.ctrlIsPressed = false;
        this.HIsPressed =false;
        this.pointAlreadyExist = false;
        this.idOfCurrentSelectedPoint = 999;
        this.menuBar = new Menus(this);
        
        this.backgroundColor = Color.WHITESMOKE;

        this.hierarchy = new ImmeubleHierarchy(this);

        
        this.canva = new DisplayCanvas(this);
        this.toolbar = new OurToolBar(this);

        this.viewerLayout = new BorderPane();
        this.log = new CustomLog(this);

        this.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(0), Insets.EMPTY))); // j'ai pas trouvé plus simple pour changer la couleur de fond
        this.setCenter(canva);
        this.setRight(toolbar);
        this.setTop(menuBar);
        this.setLeft(hierarchy);

        log.Initialise(); //on ini le log

        this.setOnKeyPressed(e -> { //gestion de la det[ection des touches
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

    }
    
}
