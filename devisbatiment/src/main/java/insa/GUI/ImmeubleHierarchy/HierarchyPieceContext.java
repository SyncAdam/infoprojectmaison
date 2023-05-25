package insa.GUI.ImmeubleHierarchy;

import java.util.ArrayList;

import insa.Batiment.Appartement;
import insa.Batiment.Immeuble;
import insa.Batiment.Niveau;
import insa.Batiment.Piece;
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

public class HierarchyPieceContext extends HierarchyItemContext{

    HierarchyPieceContext(TreeItem<String> parentObject, Object targetObject, ImmeubleHierarchy fatherTree)
    {
        super(parentObject, targetObject, fatherTree);

        MenuItem mi3 = new MenuItem("Changer d'appartement");
        MenuItem mi4 = new MenuItem("Supprimer la Piece");

        this.getItems().addAll(mi3, mi4);

        mi3.setOnAction(event -> {

            Stage pieceStage = new Stage();
            pieceStage.setTitle("Changer d'appartement");
            
            Button okButton = new Button("Ok");
            VBox disposition = new VBox(); //on créer un groupe root

            TableView<Appartement> catalog = new TableView<>(null);

            TableColumn<Appartement, Integer> nivImmID = new TableColumn<Appartement, Integer>("ImmeubleID");
            nivImmID.setMinWidth(60);
            nivImmID.setCellValueFactory(
                    new PropertyValueFactory<Appartement, Integer>("ParentImmeubleID"));

            TableColumn<Appartement, Integer> nivID = new TableColumn<Appartement, Integer>("NiveauID");
            nivID.setMinWidth(60);
            nivID.setCellValueFactory(
                    new PropertyValueFactory<Appartement, Integer>("ParentNiveauID"));

            TableColumn<Appartement, Integer> AppID = new TableColumn<Appartement, Integer>("AppartementID");
            AppID.setMinWidth(100);
            AppID.setCellValueFactory(
                    new PropertyValueFactory<Appartement, Integer>("idAppartement"));

            catalog.getColumns().addAll(nivImmID, nivID, AppID);

            ObservableList<Appartement> obslist = FXCollections.observableList(new ArrayList<Appartement>());

            
            for(Immeuble imm : this.fatherTree.loadedImmeubles)
            {
                for(Niveau niv : imm.niveau)
                {
                    obslist.addAll(FXCollections.observableList(niv.appartements));
                }
            }

            catalog.setItems(obslist);
           
            disposition.getChildren().addAll(catalog, okButton);
            
            Scene sceneNiveau = new Scene(disposition, 180,180, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
            
            pieceStage.setScene(sceneNiveau);
            pieceStage.show();

            okButton.setOnAction(event0 -> {

                Piece n = (Piece) this.targetObject;

                try{
                    if(!this.fatherTree.nonClassePieces.contains(n))
                    {
                        for(int i = 0; i < this.fatherTree.loadedImmeubles.size(); i++)
                        {
                            for(int j = 0; j < this.fatherTree.loadedImmeubles.get(i).niveau.size(); j++)
                            {
                                for(int k = 0; k < this.fatherTree.loadedImmeubles.get(i).niveau.get(j).appartements.size(); k++)
                                {
                                    this.fatherTree.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).removePiece(n);
                                }
                            }
                        }
                    }
                    else if(this.fatherTree.nonClassePieces.contains(n))
                    {
                        this.fatherTree.nonClassePieces.remove(n);
                    }

                    for(Piece piece : catalog.getSelectionModel().getSelectedItem().pieces)
                    {
                        if(piece.getPieceId() == n.getPieceId()) throw new RuntimeException();
                    }

                    catalog.getSelectionModel().getSelectedItem().addPiece((Piece)this.targetObject);
                    this.fatherTree.hierarchyRefresh();
                }
                catch(NumberFormatException e)
                {
                    e.printStackTrace();
                    this.fatherTree.parentPane.log.setTxt("ID deja existe");
                }
                
                pieceStage.close();
    
            });
        });

        mi4.setOnAction(event -> {

            if(!this.fatherTree.nonClassePieces.contains((Piece)this.targetObject))
            {
                for(int i = 0; i < this.fatherTree.loadedImmeubles.size(); i++)
                {
                    for(int j = 0; j < this.fatherTree.loadedImmeubles.get(i).niveau.size(); j++)
                    {
                        for(int k = 0; k < this.fatherTree.loadedImmeubles.get(i).niveau.get(j).appartements.size(); k++)
                        {
                            this.fatherTree.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).removePiece((Piece)this.targetObject);
                        }
                    }
                }
            }
            else if(this.fatherTree.nonClassePieces.contains((Piece)this.targetObject))
            {
                this.fatherTree.nonClassePieces.remove((Piece)this.targetObject);
            }

            this.fatherTree.hierarchyRefresh();
            
        });
    }
    
}
