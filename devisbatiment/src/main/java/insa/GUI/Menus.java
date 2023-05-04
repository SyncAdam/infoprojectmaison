package insa.GUI;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;


public class Menus extends App{

    public static MenuBar createMenus()
    {
        MenuBar menuBar = new MenuBar(); //creation de la menubar

        Menu fichier = new Menu("Fichier"); //On def les menus principaux
        Menu edition = new Menu("Édition");
        Menu view = new Menu("View");

        Menu nouveau = new Menu("Nouveau"); //Sous Menus
        Menu importer = new Menu("Importer");
        
        MenuItem NProjet = new MenuItem("Projet");  //Elements du menu fichier-->Nouveau
        MenuItem NNiveau = new MenuItem("Niveau");
        MenuItem NPoint = new MenuItem("Point");
        MenuItem ImportProj = new MenuItem("Projet");
        MenuItem ImportCatalogue = new MenuItem("Catalogue");

        MenuItem editeur = new MenuItem("Editeur");     //Elements du menu viewer
        MenuItem viewer = new MenuItem("Viewer");

        menuBar.getMenus().add(fichier);        //on ajoute les menus principaux à la barre de menu
        menuBar.getMenus().add(edition);
        menuBar.getMenus().add(view);

        fichier.getItems().add(nouveau);           //apres on ajoute les sous menus
        fichier.getItems().add(importer);
        view.getItems().addAll(editeur, viewer);

        nouveau.getItems().addAll(NProjet, NPoint, NNiveau);

        importer.getItems().addAll(ImportProj, ImportCatalogue);
          
        NPoint.setOnAction(e -> {
            ManualPoint manualPoint = new ManualPoint();
            manualPoint.Initialise();
        });


        ImportCatalogue.setOnAction(e->{

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Materials catalogue selector");
            File selectedFile = fileChooser.showOpenDialog(null);
            
            System.out.println("Dossier du catalogue selectionné : " +selectedFile.getPath());

            cataloguePath = selectedFile.getPath(); //variable héritée de la classe App qui permet de récupérer le bon chemin

        });

        ImportProj.setOnAction(e->{

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Project selector");
            File selectedFile = fileChooser.showOpenDialog(null);
            
            System.out.println("Dossier du projet selectionné : " + selectedFile.getPath());

            projectPath = selectedFile.getPath();

        });




        return menuBar;
    }

}
