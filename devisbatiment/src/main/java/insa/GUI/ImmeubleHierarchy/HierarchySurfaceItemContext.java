package insa.GUI.ImmeubleHierarchy;

import insa.Batiment.Surface;
import insa.Batiment.Revetements.Revetement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HierarchySurfaceItemContext extends ContextMenu {

    TreeItem<String> parentObject;
    ImmeubleHierarchy fatherTree;
    Surface targetObject;

    HierarchySurfaceItemContext(TreeItem<String> parentObject, Surface targetObject, ImmeubleHierarchy fatherTree)
    {
        this.parentObject = parentObject;
        this.fatherTree = fatherTree;
        this.targetObject = targetObject;

        MenuItem mi1 = new MenuItem("Ajouter un revêtement");
        MenuItem mi2 = new MenuItem("Revêtements");

        this.getItems().addAll(mi1, mi2);

        mi1.setOnAction(event -> {
            revetementSelection(this.targetObject);
        });

        mi2.setOnAction(event -> {
            showRevetements(this.targetObject);
        });

    }

    void revetementSelection(Surface s) {

        Stage selectionStage = new Stage();

        // Create the selection scene
        Button selectButton = new Button("Add");
        Label title = new Label("Catalogue des revêtements");

        TableView<Revetement> catalog = new TableView<>(null);

        TableColumn<Revetement, Integer> revIdCol = new TableColumn<Revetement, Integer>("ID");
        revIdCol.setMinWidth(10);
        revIdCol.setCellValueFactory(
                new PropertyValueFactory<Revetement, Integer>("idRevetement"));

        TableColumn<Revetement, String> revNameCol = new TableColumn<Revetement, String>("Designation");
        revNameCol.setMinWidth(150);
        revNameCol.setCellValueFactory(
                new PropertyValueFactory<Revetement, String>("designation"));

        TableColumn<Revetement, Boolean> revPourMurCol = new TableColumn<Revetement, Boolean>("bpourMur");
        revPourMurCol.setCellValueFactory(
                new PropertyValueFactory<Revetement, Boolean>("pourMur"));

        TableColumn<Revetement, Boolean> revPourSolCol = new TableColumn<Revetement, Boolean>("bpourSol");
        revPourSolCol.setCellValueFactory(
            new PropertyValueFactory<Revetement, Boolean>("pourSol"));

        TableColumn<Revetement, Boolean> revPourPlafondCol = new TableColumn<Revetement, Boolean>("bpourPlafond");
        revPourPlafondCol.setCellValueFactory(
            new PropertyValueFactory<Revetement, Boolean>("pourPlafond"));

        TableColumn<Revetement, Double> revPrixCol = new TableColumn<Revetement, Double>("Prix unitaire");
        revPrixCol.setCellValueFactory(
            new PropertyValueFactory<Revetement, Double>("prixUnitaire"));

        catalog.getColumns().addAll(revIdCol, revNameCol, revPourMurCol, revPourPlafondCol, revPourSolCol, revPrixCol);

        ObservableList<Revetement> obslist = FXCollections.observableList(this.fatherTree.revetements);

        catalog.setItems(obslist);
       

        selectButton.setOnAction(e ->   {
            try{
                this.targetObject.addRevetement(catalog.getSelectionModel().getSelectedItem());
                selectionStage.close();
            }
            catch(RuntimeException err)
            {
                err.printStackTrace();
                Alert albert = new Alert(Alert.AlertType.WARNING);
                albert.setTitle("Revetement Error");
                albert.setHeaderText("Le revêtement n'a pas été ajouté !");
                albert.setContentText("La revêtement n'est pas appliquable a cette surface !");
                albert.showAndWait();
            }
        });
        VBox selectionLayout = new VBox(title, catalog, selectButton);
        selectionLayout.setSpacing(10);
        selectionLayout.setPadding(new Insets(10));
        Scene selectionScene = new Scene(selectionLayout, 530, 300);

        selectionStage.setTitle("Revêtements");
        selectionStage.setScene(selectionScene);

        // Set the selection stage as a new window
        selectionStage.showAndWait();
    }

    void showRevetements(Surface s)
    {
        Stage selectionStage = new Stage();

        // Create the selection scene
        Button closeButton = new Button("Fermer");
        Button delButton = new Button("Enlever");
        Label title = new Label("Revêtements pour cette surface");

        TableView<Revetement> revetementstable = new TableView<>();

        TableColumn<Revetement, String> appliedrevetementname = new TableColumn<Revetement, String>();
        appliedrevetementname.setCellValueFactory(
            new PropertyValueFactory<Revetement, String>("designation")
        );

        TableColumn<Revetement, Double> appliedrevetementprix = new TableColumn<Revetement, Double>();
        appliedrevetementprix.setCellValueFactory(
            new PropertyValueFactory<Revetement, Double>("prixUnitaire")
        );

        revetementstable.getColumns().addAll(appliedrevetementname, appliedrevetementprix);

        ObservableList<Revetement> obslist = FXCollections.observableList(s.revetements);

        revetementstable.setItems(obslist);

        HBox buttons = new HBox( delButton, closeButton);

        VBox selectionLayout = new VBox(title, revetementstable, buttons);
        selectionLayout.setSpacing(10);
        selectionLayout.setPadding(new Insets(10));
        Scene selectionScene = new Scene(selectionLayout);

        selectionStage.setTitle("Revêtements");
        selectionStage.setScene(selectionScene);

        // Set the selection stage as a new window
        selectionStage.showAndWait();
    }

}
