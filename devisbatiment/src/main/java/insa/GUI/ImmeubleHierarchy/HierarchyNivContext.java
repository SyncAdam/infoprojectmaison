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
import javafx.scene.control.TextField;
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
        MenuItem mi5 = new MenuItem("Changer la hauteur du niveau");
        MenuItem mi6 = new MenuItem("Changer ID");

        this.getItems().addAll(mi3, mi5, mi6, mi4);

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

        mi5.setOnAction(event -> {

            Stage appartStage = new Stage();
            appartStage.setTitle("Changer la hauteur du niveau");
            TextField hauteurField = new TextField("hautuer");
            
            Button okButton = new Button("Ok");
            VBox disposition = new VBox(); //on créer un groupe root
           
            disposition.getChildren().addAll(hauteurField, okButton);
            
            Scene sceneAppart = new Scene(disposition, 300,80, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
            
            appartStage.setScene(sceneAppart);
            appartStage.show();

            okButton.setOnAction(event0 -> {

                try{
                    Niveau n = (Niveau)this.targetObject;
                    n.setHeight(Double.parseDouble(hauteurField.getText()));
                }
                catch(RuntimeException e)
                {
                    e.printStackTrace();
                    this.fatherTree.parentPane.log.setTxt("Erreur lors de la création de l'appartement");
                }
                
                appartStage.close();
    
            });
        });

        mi6.setOnAction(event -> {
            Stage changeAppartIDStage = new Stage();
            changeAppartIDStage.setTitle("Changer ID");
            TextField pieceID = new TextField("Identificateur du niveau");
            
            
            Button okButton = new Button("Ok");
            VBox disposition = new VBox(); //on créer un groupe root
           
            disposition.getChildren().addAll(pieceID, okButton);
            
            Scene sceneAppart = new Scene(disposition, 300,80, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
            
            changeAppartIDStage.setScene(sceneAppart);
            changeAppartIDStage.show();

            okButton.setOnAction(event0 -> {

                try{

                    //this amount of nesting is despicable
                    if(this.targetObject instanceof Niveau)
                    {
                        int newID = Integer.parseInt(pieceID.getText());
                        Niveau nivniv = (Niveau) this.targetObject;

                        for(int i = 0; i < this.fatherTree.loadedImmeubles.size(); i++)
                        {
                            if(this.fatherTree.loadedImmeubles.get(i).niveau.contains(nivniv))
                            {
                                for(int j = 0; j < this.fatherTree.loadedImmeubles.get(i).niveau.size(); j++)
                                {
                                    if(this.fatherTree.loadedImmeubles.get(i).niveau.get(j).getIdNiveau() == newID) throw new RuntimeException("Cannot have two niveaux with the same ID in an immeule");
                                }
                            }
                        }
                        nivniv.setNiveauId(Integer.parseInt(pieceID.getText()));
                        
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
