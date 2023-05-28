package insa.GUI.ImmeubleHierarchy;

import insa.Batiment.Immeuble;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HierarchyImmContext extends ContextMenu{

    TreeItem<String> parentObject;
    ImmeubleHierarchy fatherTree;
    Immeuble targetObject;

    HierarchyImmContext(TreeItem<String> parentObject, Immeuble targetObject, ImmeubleHierarchy fatherTree)
    {
        this.parentObject = parentObject;
        this.fatherTree = fatherTree;
        this.targetObject = targetObject;
        
        MenuItem mi2 = new MenuItem("Revêtements");
        MenuItem mi3 = new MenuItem("Changer ID");

        this.getItems().addAll(mi2, mi3);

        mi2.setOnAction(event -> {
            System.out.println(Immeuble.calculRevetement(this.targetObject)); 
        });

        mi3.setOnAction(event-> {
            Stage changeAppartIDStage = new Stage();
            changeAppartIDStage.setTitle("Changer ID");
            TextField pieceID = new TextField("Identificateur de l'immeuble");
            
            
            Button okButton = new Button("Ok");
            VBox disposition = new VBox(); //on créer un groupe root
           
            disposition.getChildren().addAll(pieceID, okButton);
            
            Scene sceneAppart = new Scene(disposition, 300,80, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
            
            changeAppartIDStage.setScene(sceneAppart);
            changeAppartIDStage.show();

            okButton.setOnAction(event0 -> {

                try{

                    //this amount of nesting is terrible
                    if(this.targetObject instanceof Immeuble)
                    {
                        int newID = Integer.parseInt(pieceID.getText());
                        Immeuble imm = (Immeuble) this.targetObject;

                        if(this.fatherTree.loadedImmeubles.contains(imm))
                        {
                            for(Immeuble imme : this.fatherTree.loadedImmeubles)
                            {
                                if(imme.getIdImmeuble() == newID) throw new RuntimeException("Cannot have two immeubles with the same ID");
                            }
                        }
                        imm.setAppartementId(Integer.parseInt(pieceID.getText()));
                        
                    }
                    this.fatherTree.hierarchyRefresh();
                }
                catch(RuntimeException e)
                {
                    e.printStackTrace();
                    this.fatherTree.parentPane.log.setTxt("Erreur lors de la changement d'ID");
                }
                
                changeAppartIDStage.close();
    
            });
        });
    }
    
}
