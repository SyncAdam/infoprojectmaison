package insa.GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import insa.Batiment.Coin;
import insa.Batiment.Mur;


public class App extends Application {
    
    protected  static boolean pointAlreadyExist = false;
    protected static int idOfCurrentSelectedPoint = 999; //(default)

    
    CheckBox autoWall = new CheckBox("autoWall");
    boolean autoWallState = false;
    Group canva = new Group();
    protected BorderPane root = new BorderPane(); //on créer un borderpane root
    CustomLog log = new CustomLog(root); //on créer le custom log
    
    int i =1;
    int k=0;
    //protected CustomLog log = new CustomLog(); 
    public void start(Stage homeWindow) throws IOException {
        
        
        Menus Menus = new Menus();
       
        Scene scene1 = new Scene(root, Color.GREY); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
        

        root.setRight(autoWall);
        root.setCenter(canva);
        
        
        
        




        Menus.InitialiseMenus(root);
 
        //log.Initialise();
    
       
        homeWindow.setWidth(1000);
        homeWindow.setHeight(700); //def de la hauteur de la fenetre 
        
        Coin[] CoinClic = new Coin[100];
        CoinClic[0] = new Coin(0,0,0); // on def un point par défaut comme ca si on fait un mur dès le début il est forcément relié 
        Mur[] MurClic = new Mur[100];


        root.setOnMouseClicked(e -> { //détection du clic + récup coords
            System.out.println(pointAlreadyExist + " vrai");
            if (pointAlreadyExist == false){
                if (autoWallState == false) {
                CoinClic[i]= new Coin(i, e.getX(), e.getY());
                CoinClic[i].DisplayPoint(root);

                System.out.println("clic detecté");

                i++;
                }if (autoWallState == true){
                    
                        CoinClic[i]= new Coin(i, e.getX(), e.getY()); //on créer un point
                        CoinClic[i].DisplayPoint(root); //on l'affiche
                        MurClic[k] = new Mur(k , CoinClic[i-1], CoinClic[i]);
                        
                        MurClic[k].DisplayMur(root);

                        k++;
                        i++;
                    
                }
            } if(pointAlreadyExist ==true){ //si ya déjà un point
                if (autoWallState == false){ //et que autowall est désactivé, on dit qu'on ne peut pas placer un autre point au même endroit
                System.out.println("Vous ne pouvez pas placer un point ici ! ") ;
                }else{ //si autowall est activé et qu'il y a déjà un point : on fait un mur avec le dernier point créé et le point selectionné
                   /* MurClic[k] = new Mur(k , CoinClic[i-1], CoinClic[idOfCurrentSelectedPoint]); //on créer avec le point précédent et le point selectionné
                    MurClic[k].DisplayMur(root); //on l'affiche
                    k++; //on incrémente i de 1 sinon les prochains murs ne seront pas bien placés
                    i = idOfCurrentSelectedPoint;*/


                }
            
            
            
            }
    });



        autoWall.setOnAction(e -> {
            autoWallState= autoWall.isSelected();
            System.out.println("Etat de la case : " + autoWallState);});
        
        
       

        
        


        homeWindow.setScene(scene1);
        homeWindow.show();
    }




}