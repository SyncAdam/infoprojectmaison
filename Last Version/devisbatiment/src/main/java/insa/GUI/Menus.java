package insa.GUI;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class Menus extends App{
    int test =4;
    MenuBar menuBar = new MenuBar(); //creation de la menubar
    Menu nouveau = new Menu("Nouveau"); //Sous Menus
    Menu importer = new Menu("Importer");

    Menu fichier = new Menu("Fichier"); //On def les menus principaux
    Menu edition = new Menu("Édition");
    MenuItem NProjet = new MenuItem("Projet"); //Elements du menu fichier-->Nouveau
    MenuItem NNiveau = new MenuItem("Niveau");
    MenuItem NPoint = new MenuItem("Point");
    MenuItem ImportProj = new MenuItem("Projet");
    MenuItem ImportCatalogue = new MenuItem("Catalogue");

    BorderPane root = new BorderPane();
    public void InitialiseMenus(BorderPane root){

        
       
        menuBar.getMenus().add(fichier);//on ajoute les menus principaux à la barre de menu
        menuBar.getMenus().add(edition);


        fichier.getItems().add(nouveau);
        fichier.getItems().add(importer);

       
        nouveau.getItems().addAll(NProjet, NPoint, NNiveau);



        importer.getItems().addAll(ImportProj, ImportCatalogue);
        root.setTop(menuBar); //on ajoute la barre de menu en haut du borderpane
     

        
        NPoint.setOnAction(e -> {ManualPoint manualPoint = new ManualPoint(); manualPoint.Initialise();});


    }





}
