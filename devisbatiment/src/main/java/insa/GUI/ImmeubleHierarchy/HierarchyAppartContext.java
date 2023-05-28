package insa.GUI.ImmeubleHierarchy;

import java.util.ArrayList;

import insa.Batiment.Appartement;
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

public class HierarchyAppartContext extends HierarchyItemContext{

    HierarchyAppartContext(TreeItem<String> parentObject, Object targetObject, ImmeubleHierarchy fatherTree)
    {
        super(parentObject, targetObject, fatherTree);

        MenuItem mi3 = new MenuItem("Changer le niveau");
        MenuItem mi4 = new MenuItem("Supprimer l'appartement");
        MenuItem mi5 = new MenuItem("Changer ID");

        this.getItems().addAll(mi3, mi5, mi4);

        mi3.setOnAction(event -> {

            Stage appartStage = new Stage();
            appartStage.setTitle("Changer le Niveau");
            
            Button okButton = new Button("Ok");
            VBox disposition = new VBox(); //on créer un groupe root

            TableView<Niveau> catalog = new TableView<>(null);

            TableColumn<Niveau, Integer> nivImmID = new TableColumn<Niveau, Integer>("ImmeubleID");
            nivImmID.setMinWidth(60);
            nivImmID.setCellValueFactory(
                    new PropertyValueFactory<Niveau, Integer>("ParentImmeubleID"));

            TableColumn<Niveau, Integer> nivID = new TableColumn<Niveau, Integer>("NiveauID");
            nivID.setMinWidth(60);
            nivID.setCellValueFactory(
                    new PropertyValueFactory<Niveau, Integer>("idNiveau"));

            catalog.getColumns().addAll(nivImmID, nivID);

            ObservableList<Niveau> obslist = FXCollections.observableList(new ArrayList<Niveau>());

            for(Immeuble imm : this.fatherTree.loadedImmeubles)
            {
                obslist.addAll(FXCollections.observableList(imm.niveau));
            }

            catalog.setItems(obslist);
           
            disposition.getChildren().addAll(catalog, okButton);
            
            Scene sceneNiveau = new Scene(disposition, 120,180, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
            
            appartStage.setScene(sceneNiveau);
            appartStage.show();

            okButton.setOnAction(event0 -> {

                Appartement n = (Appartement) this.targetObject;

                try{
                    if(!this.fatherTree.nonClasseAppartements.contains(n))
                    {
                        for(int i = 0; i < this.fatherTree.loadedImmeubles.size(); i++)
                        {
                            for(int j = 0; j < this.fatherTree.loadedImmeubles.get(i).niveau.size(); j++)
                            {
                                this.fatherTree.loadedImmeubles.get(i).niveau.get(j).removeAppartement(n);
                            }
                            
                        }
                    }
                    else if(this.fatherTree.nonClasseAppartements.contains(n))
                    {
                        this.fatherTree.nonClasseAppartements.remove(n);
                    }

                    for(Appartement appart : catalog.getSelectionModel().getSelectedItem().appartements)
                    {
                        if(appart.getIdAppartement() == n.getIdAppartement()) throw new RuntimeException();
                    }

                    catalog.getSelectionModel().getSelectedItem().addAppartement((Appartement)this.targetObject);
                    catalog.getSelectionModel().getSelectedItem().getAppartement(n.getIdAppartement()).setParentNiveau(catalog.getSelectionModel().getSelectedItem());
                    catalog.getSelectionModel().getSelectedItem().getAppartement(n.getIdAppartement()).setParentImmeuble(catalog.getSelectionModel().getSelectedItem().getParentImmeuble());
                    this.fatherTree.hierarchyRefresh();
                }
                catch(NumberFormatException e)
                {
                    e.printStackTrace();
                }
                
                appartStage.close();
    
            });
        });


        mi4.setOnAction(event -> {
            
            if(this.fatherTree.nonClasseAppartements.contains((Appartement)this.targetObject))
            {
                this.fatherTree.nonClasseAppartements.remove((Appartement)this.targetObject);
            }
            else
            {
                for(int i = 0; i < this.fatherTree.loadedImmeubles.size(); i++)
                {
                    for(int j = 0; j < this.fatherTree.loadedImmeubles.get(i).niveau.size(); j++)
                    {
                        this.fatherTree.loadedImmeubles.get(i).niveau.get(j).removeAppartement((Appartement)this.targetObject);
                    }                    
                }
            }

            this.fatherTree.hierarchyRefresh();

        });

        mi5.setOnAction(event -> {
            Stage changeAppartIDStage = new Stage();
            changeAppartIDStage.setTitle("Changer ID");
            TextField pieceID = new TextField("Identificateur de l'appartement");
            
            
            Button okButton = new Button("Ok");
            VBox disposition = new VBox(); //on créer un groupe root
           
            disposition.getChildren().addAll(pieceID, okButton);
            
            Scene sceneAppart = new Scene(disposition, 300,80, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
            
            changeAppartIDStage.setScene(sceneAppart);
            changeAppartIDStage.show();

            okButton.setOnAction(event0 -> {

                try{

                    //this amount of nesting is terrible
                    if(this.targetObject instanceof Appartement)
                    {
                        int newID = Integer.parseInt(pieceID.getText());
                        Appartement appp = (Appartement) this.targetObject;

                        for(int i = 0; i < this.fatherTree.loadedImmeubles.size(); i++)
                        {
                            for(Niveau niv : this.fatherTree.loadedImmeubles.get(i).niveau)
                            {
                                if(niv.appartements.contains(appp))
                                {
                                    for(Appartement appartement : niv.appartements)
                                    {
                                        if(newID == appartement.getIdAppartement()) throw new RuntimeException("Cannot have the same ID for two appartements in a niveau");
                                    }
                                }
                            }
                        }
                        appp.setAppartementId(Integer.parseInt(pieceID.getText()));
                        
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
