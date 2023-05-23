package insa.GUI.ImmeubleHierarchy;

import insa.Batiment.Immeuble;
import insa.Batiment.Niveau;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HierarchyNivContext extends HierarchyItemContext{

    HierarchyNivContext(TreeItem<String> parentObject, Object targetObject, ImmeubleHierarchy fatherTree)
    {
        super(parentObject, targetObject, fatherTree);

        MenuItem mi3 = new MenuItem("Changer d'immeuble");
        MenuItem mi4 = new MenuItem("Supprimer le niveau");

        this.getItems().addAll(mi3, mi4);

        mi3.setOnAction(event -> {

            Stage niveauStage = new Stage();
            niveauStage.setTitle("Changer d'immeuble");
            
            Button okButton = new Button("Ok");
            VBox disposition = new VBox(); //on créer un groupe root

            TableView<Immeuble> catalog = new TableView<>(null);

            TableColumn<Immeuble, Integer> ID = new TableColumn<Immeuble, Integer>("ImmeubleID");
            ID.setMinWidth(60);
            ID.setCellValueFactory(
                    new PropertyValueFactory<Immeuble, Integer>("idImmeuble"));

            catalog.getColumns().add(ID);

            ObservableList<Immeuble> obslist = FXCollections.observableList(this.fatherTree.loadedImmeubles);

            catalog.setItems(obslist);
           
            disposition.getChildren().addAll(catalog, okButton);
            
            Scene sceneNiveau = new Scene(disposition, 150,180, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
            
            niveauStage.setScene(sceneNiveau);
            niveauStage.show();

            okButton.setOnAction(event0 -> {

                Niveau n = (Niveau) this.targetObject;

                try{
                    if(!this.fatherTree.nonClasseNiveaux.contains(n))
                    {
                        for(int i = 0; i < this.fatherTree.loadedImmeubles.size(); i++)
                        {
                            
                            this.fatherTree.loadedImmeubles.get(i).niveau.remove(n);
                            
                        }
                    }
                    else if(this.fatherTree.nonClasseNiveaux.contains(n))
                    {
                        this.fatherTree.nonClasseNiveaux.remove(n);
                    }

                    for(Niveau niv : catalog.getSelectionModel().getSelectedItem().niveau)
                    {
                        if(niv.getIdNiveau() == n.getIdNiveau()) throw new RuntimeException();
                    }

                    catalog.getSelectionModel().getSelectedItem().addNiveau(n);
                    catalog.getSelectionModel().getSelectedItem().getNiveau(n.idNiveau).changeParentImmeuble(catalog.getSelectionModel().getSelectedItem());
                    this.fatherTree.hierarchyRefresh();
                }
                catch(RuntimeException e)
                {
                    e.printStackTrace();
                    this.fatherTree.parentPane.log.setTxt("L'ID existe déjà ! ");
                }
                
                niveauStage.close();
    
            });
        });

        mi4.setOnAction(event -> {
            if(!this.fatherTree.nonClasseNiveaux.contains((Niveau)this.targetObject))
            {
                for(int i = 0; i < this.fatherTree.loadedImmeubles.size(); i++)
                {
                    
                    this.fatherTree.loadedImmeubles.get(i).niveau.remove((Niveau)this.targetObject);
                    
                }
            }
            else if(this.fatherTree.nonClasseNiveaux.contains((Niveau)this.targetObject))
            {
                this.fatherTree.nonClasseNiveaux.remove((Niveau)this.targetObject);
            }
            this.fatherTree.hierarchyRefresh();
        });
    }
    
}
