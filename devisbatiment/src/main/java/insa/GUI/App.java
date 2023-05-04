package insa.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

import insa.Batiment.Coin;
import insa.Batiment.Mur;
import insa.Batiment.Piece;
import insa.Batiment.Porte;



/*NOTE DE Timéo POUR ADAM DU 21/04  : 
 * Voilà tout ce que j'ai rajouté : 
 * Si on clique 2 fois sur un même point ca créér un mur avec le point qui existe déja.
 * Ensuite ça créér une ArrayList de mur dans l'ArrayList d'arraylist de mur qui s'appelle "murArrayTab" (avec détection du contour fermé)
 * Ensuite ca essaye de créer une pièce (ligne commentée vers ligne 150), mais il doit y avoir un soucis avec le constructeur de piece.
 * Je l'ai un tout petit peu modifié pour qu'il prenne en argument un arrayList de murs comme tu le voulais. Je te laisse décomenter la ligne 150 pour tester. (regarde la console)
 * Sinon tous les points sont sotckés dans coinTab et les murs dans murTab (arrayList).
 * Pour l'instant il y a une erreur quand on essaye de fermer une pièce en cliquant sur un point quand il manque un mur, il faut que je fasse un truc avec les exceptions.
 */


public class App extends Application {
    
    protected static boolean pointAlreadyExist = false;
    protected static int idOfCurrentSelectedPoint = 999; //(default)
   // protected static int idOfCurrentSelectedWall = 999; //(default)
    protected static boolean ctrlIsPressed = false; 
    

    
    
    
  
    ToolBar toolBar = new ToolBar();
    CheckBox autoWall = new CheckBox("autoWall");
    boolean autoWallState = false;
    Button buttonMur = new Button("Mur");
    Button buttonPoint = new Button("Point");
    Button buttonPorte = new Button("Porte");
    Button unselectButton = new Button("Unselect all");


    Group canva = new Group();
    protected  BorderPane root = new BorderPane(); //on créer un borderpane root
    BorderPane viewerLayout = new BorderPane();
    CustomLog log = new CustomLog(root); //on créer le custom log
    
    int i =1;
    int k=0; //ne sert plus à rien je crois
    //protected CustomLog log = new CustomLog(); 
    public static ArrayList<Coin> coinTab = new ArrayList<Coin>(); //création du tableau dynamique qui contient tous les points placés graphiquement
    public static ArrayList<Mur> wallTab = new ArrayList<Mur>();
    public static ArrayList<ArrayList<Mur>> murArrayTab = new ArrayList<ArrayList<Mur>>(); //on créer une array liste qui contient des listes de mur, pour pouvoir envoyer dans le constructeur de pièce
    public static ArrayList<Piece> pieceTab = new ArrayList<Piece>();
    public static ArrayList<Integer> iDOfSelectedWall = new ArrayList<Integer>();
    public static ArrayList<Porte> doorTab = new ArrayList<Porte>();
    protected Scene scene1 = new Scene(root, Color.GREY); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
    
    @Override
    public void start(Stage homeWindow) throws IOException {
        root.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(0), Insets.EMPTY))); // j'ai pas trouvé plus simple pour changer la couleur de fond
        
        MenuBar menuBar = Menus.createMenus();
    
       
         
        root.setRight(toolBar);
        root.setCenter(canva);
        root.setTop(menuBar);
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.getItems().addAll(autoWall, buttonMur, buttonPoint, buttonPorte, unselectButton);
 
        //log.Initialise();
    
       
        homeWindow.setWidth(1000);
        homeWindow.setHeight(700); //def de la hauteur de la fenetre 
        
        
        
        coinTab.add(new Coin(0, 0, 0)); //on place un point invisible à l'origine : permet de faire un mur dès le premier clic si l'user le veut.


        scene1.setOnKeyPressed(e -> { //gestion de la detection de l'appui pour la selection
            if(e.getCode()== KeyCode.CONTROL){
                System.out.println("CTRL pressé");
                ctrlIsPressed = true; //si ctrl est appuyé, on met la variable correspondante à true.
            }
        });
        scene1.setOnKeyReleased(e -> { //gestion de la detection du relachement de ctrl pour la sélection
        if(e.getCode()== KeyCode.CONTROL){
            System.out.println("CTRL relaché");
            ctrlIsPressed = false; //si ctrl est relaché, on met la variable correspondante à false.
        }
    });
       
        
        root.setOnMouseClicked(e -> { //détection du clic + récup coords
            if (ctrlIsPressed == false){ //sert juste à ne pas crééer de nouveau éléments lorsqu'on veut juste selectionner un point/mur avec ctrl
            System.out.println(pointAlreadyExist + " vrai");
            if (pointAlreadyExist == false){
                if (autoWallState == false) {
            
                    System.out.println(coinTab.size());
                    System.out.println(i);
                    
                    coinTab.add(new Coin(coinTab.size(), e.getX(), e.getY())); //on créé le new point dans le tableau
                    coinTab.get(coinTab.size()-1).DisplayPoint(root); //on l'affiche

                    /*NOTE : si on a un seul élement dans l'array list, sont index est 0, mais quand on demande la taille (.size()),
                    * on obtient 1, d'où le "-1", pour faire coller à la taille.
                    */

                    System.out.println("clic detecté");

                }
                if (autoWallState == true){


                        coinTab.add(new Coin(coinTab.size(), e.getX(), e.getY())); //on créé le new point dans le tableau avec comme id sa position dans le tableau
                        coinTab.get(coinTab.size()-1).DisplayPoint(root); //on l'affiche (le dernier pt du coinTab)
                    
                       
                        wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-1), coinTab.get(coinTab.size()-2))); //on créér un mur à partir des 2 dernies coins du coinTab
                        /*NOTE : coinTab.size()-1 correspond à l'index du dernier point et coinTab.size()-2, à celui de l'avant dernier */
                        
                        
                        
                        wallTab.get(wallTab.size()-1).DisplayMur(root);
                        System.out.println("Le coin d'id " + coinTab.get(coinTab.size()-1).getId()+ "est à la pos " + (coinTab.size()-1));
                   
                }
            }

            if(pointAlreadyExist ==true){ //si ya déjà un point
                if (autoWallState == false){ //et que autowall est désactivé, on dit qu'on ne peut pas placer un autre point au même endroit
                    System.out.println("Vous ne pouvez pas placer un point ici ! ") ;
                }
                else{ //si autowall est activé et qu'il y a déjà un point : on fait un mur avec le dernier point créé et le point selectionné
                
                    coinTab.add(coinTab.size(), coinTab.get(idOfCurrentSelectedPoint));
                    //coinTab.remove(idOfCurrentSelectedPoint);
                    int i = 0;
                    while ( i <= coinTab.size()-1){
                        System.out.println("AVANT  :Le point d'id :" +coinTab.get(i).getId() + " se trouve à l'index " + i);
                        i++;
                    }
                   
                    wallTab.add(new Mur (wallTab.size(), coinTab.get(coinTab.size()-2), coinTab.get(coinTab.size()-1)));

                    wallTab.get(wallTab.size()-1).DisplayMur(root);//on l'affiche
                   

                    /*Raisonnement de ce que j'ai fait (21/04): quand on clique 2 fois sur un coin, c'est forcément que l'on fait une pièce (du moins si le auto wall a tjrs été activé),
                     * Donc on va parcourir tous les murs de walltab en partant du dernier jusquà trouver un point commun avec le dernier mur
                     *placé. Et a chaque fois, on ajoute dans une liste de mur de l'array liste de liste de mur. (oui une array liste de liste !)
                     */

                    i = wallTab.size()-2; // on va commencer par vérifier l'avant dernier mur, sinon la boucle while sort dès la première itération
                    int j =0;
                    
                    murArrayTab.add(new ArrayList<Mur>()); //on créer un arraylist de mur dans l'arraylist d'arraylist de mur
                    murArrayTab.get(murArrayTab.size()-1).add(wallTab.get(wallTab.size()-1)); //on ajoute le dernier mur crééé dans l'array liste de l'array liste d'array liste de mur, car il ne sera pas ajouté par la boucle while
                    try{
                    while (wallTab.get(i).getIdDebut() != idOfCurrentSelectedPoint && wallTab.get(i).getIdFin() != idOfCurrentSelectedPoint){ //tant que les 2 coins du mur i sont différents du currentidpoint on continue
                        
                        murArrayTab.get(murArrayTab.size()-1).add(wallTab.get(i)); //on ajoute au tableau dans le tableau mur i de la pièce
                        i--;
                        j++;
                        if(j >= wallTab.size()-1){throw new PieceNonFermeeException();} //condition de si on a parcouru tous les murs et q'on est pas sorti du while
                        //POUR PLUS TARD : EXEPTION QUI Détecte QUAND ERREUR DANS LA BOUCLE --> LA PI7CE N'EST PAS FERMée
                    }
                    //pieceTab.add(new Piece(murArrayTab.size()-1, murArrayTab.get(murArrayTab.size()-1))); // on ajoute dans l'arrayList de pièce la new pièce créée
                 
                    System.out.println("Pièce de " + (murArrayTab.get(murArrayTab.size()-1).size()+1) + " murs créée");
                }catch(PieceNonFermeeException err){System.out.println(err);}
                }

            }
        }
    });

        autoWall.setOnAction(e -> {
            autoWallState= autoWall.isSelected();
            System.out.println("Etat de la case : " + autoWallState);
        });

        unselectButton.setOnAction(e->{UnselectAll();    });

        buttonPorte.setOnAction(e->{ //quand bouton porte cliqué
            if (iDOfSelectedWall.size() == 0){System.out.println("Veuillez selectionner au moins un mur avant de créer une porte");} //on vérif qu'il y a bien un mur selectionné
            else{ //si au moins un mur est selectionné :

                for (int i = 0; i < iDOfSelectedWall.size(); i++) { //pour i allant de 0 au nombre de mur selectionné, on crééer un porte qu'on ajoute dans le tableau, puis on affiche
                    doorTab.add(new Porte(doorTab.size(), wallTab.get(iDOfSelectedWall.get(i))));
                    doorTab.get(doorTab.size()-1).Display(root);  //on affiche la denière porte créée.
                }
                
            }


        });

        homeWindow.setScene(scene1);
        homeWindow.show();
    }











    
    public void UnselectAll(){
        for (int i = wallTab.size()-1; i != -1; i--) { //on met tous les murs en noir + on met leur etat de selection à jour
            wallTab.get(i).setColor(Color.BLACK);
            wallTab.get(i).setSelectedState(false);                           
        }

        iDOfSelectedWall.clear(); //on deselectionne tous les murs


    }
}