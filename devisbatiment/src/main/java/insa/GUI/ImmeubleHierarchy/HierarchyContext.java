package insa.GUI.ImmeubleHierarchy;

import insa.Batiment.Appartement;
import insa.Batiment.Immeuble;
import insa.Batiment.Niveau;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HierarchyContext extends ContextMenu{

    ImmeubleHierarchy fatherTree;

    HierarchyContext(ImmeubleHierarchy fatherTree)
    {
        this.fatherTree = fatherTree;

        MenuItem mi1 = new MenuItem("Creer Immeuble");
        MenuItem mi2 = new MenuItem("Creer Niveau");
        MenuItem mi3 = new MenuItem("Creer Appartement");

        this.setAutoHide(true);

        this.getItems().addAll(mi1, mi2, mi3);

        mi1.setOnAction(event -> {

            Stage immeubleStage = new Stage();
            immeubleStage.setTitle("Creer un Immeuble");
            TextField immeublesID = new TextField("Identificateur d'immeuble");
            Button okButton = new Button("Ok");
            VBox disposition = new VBox(); //on créer un groupe root
           
            disposition.getChildren().addAll(immeublesID, okButton);
            
            Scene sceneImmeuble = new Scene(disposition, 300,80, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
            
            immeubleStage.setScene(sceneImmeuble);
            immeubleStage.show();

            okButton.setOnAction(event0 -> {

                try{
                    int id = Integer.parseInt(immeublesID.getText());
                    
                    Immeuble i = new Immeuble(id);
                    for(Immeuble imm : this.fatherTree.loadedImmeubles)
                    {
                        if(imm.getIdImmeuble() == i.getIdImmeuble())
                        {
                            throw new RuntimeException();
                        }
                    }
                    this.fatherTree.loadedImmeubles.add(new Immeuble(id));
                    
                    this.fatherTree.hierarchyRefresh();
                }
                catch(RuntimeException e)
                {
                    e.printStackTrace();
                    this.fatherTree.parentPane.log.setTxt("Erreur de creation de l'immeuble. ID deja existe");
                }
                
                immeubleStage.close();
    
            });


        });

        mi2.setOnAction(event -> {

            Stage niveauStage = new Stage();
            niveauStage.setTitle("Creer un Niveau");
            TextField niveauID = new TextField("Identificateur du Niveau");
            TextField niveauH = new TextField("Hauteur du niveau");
            
            Button okButton = new Button("Ok");
            VBox disposition = new VBox(); //on créer un groupe root
           
            disposition.getChildren().addAll(niveauID, niveauH, okButton);
            
            Scene sceneNiveau = new Scene(disposition, 300,80, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
            
            niveauStage.setScene(sceneNiveau);
            niveauStage.show();

            okButton.setOnAction(event0 -> {

                double d = Double.parseDouble(niveauH.getText());

                try{
                    if(d <= 0) throw new RuntimeException();
                    this.fatherTree.nonClasseNiveaux.add(new Niveau(Integer.parseInt(niveauID.getText()), d));
                    this.fatherTree.hierarchyRefresh();
                }
                catch(RuntimeException e)
                {
                    e.printStackTrace();
                    this.fatherTree.parentPane.log.setTxt("Hauteur de niveau impossible");
                }
                
                niveauStage.close();
    
            });
        });

        mi3.setOnAction(event -> {

            Stage appartStage = new Stage();
            appartStage.setTitle("Creer un Niveau");
            TextField appartID = new TextField("Identificateur de niveau");
            
            
            Button okButton = new Button("Ok");
            VBox disposition = new VBox(); //on créer un groupe root
           
            disposition.getChildren().addAll(appartID, okButton);
            
            Scene sceneAppart = new Scene(disposition, 300,80, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
            
            appartStage.setScene(sceneAppart);
            appartStage.show();

            okButton.setOnAction(event0 -> {

                try{
                    this.fatherTree.nonClasseAppartements.add(new Appartement(Integer.parseInt(appartID.getText())));
                    this.fatherTree.hierarchyRefresh();
                }
                catch(RuntimeException e)
                {
                    e.printStackTrace();
                    this.fatherTree.parentPane.log.setTxt("Error en creation de l'appartement");
                }
                
                appartStage.close();
    
            });
            
        });
    }
    
}
