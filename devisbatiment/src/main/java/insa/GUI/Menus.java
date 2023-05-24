package insa.GUI;

import java.io.File;
import java.io.IOException;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.util.ArrayList;

import insa.Batiment.Immeuble;


public class Menus extends MenuBar{

    MainPane parentPane;

    MenuBar menuBar;

    Menu fichier; //On def les menus principaux
    
    

    Menu nouveau; //Sous Menus
    Menu importer;
    
    MenuItem sauver;
    MenuItem charger;
    
    MenuItem NProjet;  //Elements du menu fichier-->Nouveau
 
    MenuItem NPoint;
    MenuItem ImportProj;
    MenuItem ImportCatalogue ;

    MenuItem editeur;     //Elements du menu viewer
    MenuItem viewer;
 
    Menu devisMenu;
    MenuItem generate;

    Menus(MainPane parentPane)
    {
        this.parentPane = parentPane;

        fichier = new Menu("Fichier"); //On def les menus principaux
        
        //this.view = new Menu("View");

        this.nouveau = new Menu("Nouveau"); //Sous Menus
        this.importer = new Menu("Importer");
        this.sauver = new MenuItem("Sauvgarder");
        this.charger = new MenuItem("Charger");
        this.devisMenu = new Menu("Devis");
       
       

        this.generate = new MenuItem("Générer");
        this.NProjet = new MenuItem("Projet");  //Elements du menu fichier-->Nouveau
        
        this.NPoint = new MenuItem("Point");
        this.ImportProj = new MenuItem("Projet");
        this.ImportCatalogue = new MenuItem("Catalogue");

        //this.editeur = new MenuItem("Editeur");     //Elements du menu viewer
        //this.viewer = new MenuItem("Viewer");

        this.getMenus().add(fichier);        //on ajoute les menus principaux à la barre de menu
       

        fichier.getItems().addAll(nouveau, sauver, charger, importer, devisMenu);

        nouveau.getItems().addAll(NProjet, NPoint);
        devisMenu.getItems().add(generate);

        importer.getItems().addAll(ImportProj, ImportCatalogue);
          
        NPoint.setOnAction(e -> {
            ManualPoint manualPoint = new ManualPoint(this.parentPane);
            manualPoint.Initialise();
        });


        ImportCatalogue.setOnAction(e->{

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Materials catalogue selector");
            File selectedFile = fileChooser.showOpenDialog(null);
            
            System.out.println("Dossier du catalogue selectionné : " +selectedFile.getPath());

            this.parentPane.cataloguePath = selectedFile.getPath(); //variable héritée de la classe App qui permet de récupérer le bon chemin

        });

        ImportProj.setOnAction(e->{

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Project selector");
            System.out.println("Opening project...");

            var selectedFile = fileChooser.showOpenDialog(null);

            if(selectedFile != null)
            {
                if(selectedFile.getPath() != null)
                {
                    this.parentPane.projectPath = selectedFile.getPath();
                }
                System.out.println("Nom de fichier selectionné : " + selectedFile.getPath());

                try{
                    ArrayList<Immeuble> ims = new ArrayList<>();

                    ims = Immeuble.importImmeuble(selectedFile.getPath());

                    for(int i = 0; i < parentPane.hierarchy.loadedImmeubles.size(); i++)
                    {
                        parentPane.hierarchy.loadedImmeubles.remove(i);     //out with the old
                    }
    
                    for(Immeuble i : ims)
                    {
                        parentPane.hierarchy.loadedImmeubles.add(i);        //in with the new
                    }
                    this.parentPane.projectOpened = true;
                    this.parentPane.hierarchy.hierarchyRefresh();
                    this.parentPane.canva.clearCanva();
                }
                catch(IOException | ClassNotFoundException err)
                {
                    System.out.println("Impossible de charger le fichier");
                } 

            }
            else
            {
                System.out.println("Aborting project selection");
            }            

        });



        sauver.setOnAction(event -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sauver");

            var selectedFile = fileChooser.showSaveDialog(null);

            if(selectedFile != null)
            {
                if(selectedFile.getPath() != null)
                {
                    try
                    {
                        Immeuble.saveImmeuble(this.parentPane.hierarchy.loadedImmeubles, selectedFile.getPath());
                    }
                    catch(IOException | ClassNotFoundException err)
                    {
                        System.out.println("Immeuble n'est pas sauve");
                    }
                }
                System.out.println("Nom de fichier selectionné : " + selectedFile.getPath());

            }
            else
            {
                System.out.println("Aborting project selection");
            }
        });

        charger.setOnAction(event -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Project selector");
            System.out.println("Opening project...");

            var selectedFile = fileChooser.showOpenDialog(null);

            if(selectedFile != null)
            {
                if(selectedFile.getPath() != null)
                {
                    try{
                        for(int i = 0; i < parentPane.hierarchy.loadedImmeubles.size(); i++)
                        {
                            parentPane.hierarchy.loadedImmeubles.remove(i);
                        }
                        this.parentPane.hierarchy.loadedImmeubles = Immeuble.loadImmeuble(selectedFile.getPath());
                        this.parentPane.hierarchy.hierarchyRefresh();
                        this.parentPane.canva.clearCanva();
                    }
                    catch(IOException | ClassNotFoundException err)
                    {
                        err.printStackTrace();
                    }
                } 
            }
        });

        NProjet.setOnAction(event -> {
            this.parentPane.hierarchy.hierarchyRefresh();
            this.parentPane.projectOpened = true;
        });


        generate.setOnAction(e -> {
            DevisTxt.generate(new ArrayList<>(), 54);
        });

    }

}
