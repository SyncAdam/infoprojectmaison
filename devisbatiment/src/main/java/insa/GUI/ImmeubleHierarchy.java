package insa.GUI;

import insa.Batiment.Immeuble;
import insa.Batiment.Surface;
import insa.Batiment.Revetements.Revetement;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

import java.util.Map;

public class ImmeubleHierarchy extends TreeView{

    private Map<TreeItem<String>, Object> objectMap;

    MainPane parentPane;

    ArrayList<Immeuble> loadedImmeubles;
    ArrayList<Revetement> revetements;

    ImmeubleHierarchy(MainPane parentPane)
    {
        this.parentPane = parentPane;
        this.loadedImmeubles = new ArrayList<Immeuble>();
        this.revetements  = Revetement.readDef();
        this.objectMap = new HashMap<>();
    }

    public void hierarchyRefresh()
    {

        TreeItem<String> rootItem = new TreeItem<String> ("Project");
        rootItem.setExpanded(true);

        //pour chaque immeuble
        for (int i = 0; i < this.loadedImmeubles.size(); i++) {

            TreeItem<String> item = new TreeItem<String> ("Immeuble " + this.loadedImmeubles.get(i).idImmeuble);
            item.setExpanded(true);
            this.objectMap.put(item, this.loadedImmeubles.get(i));

            //chaque niveau
            for (int j = 0; j < this.loadedImmeubles.get(i).niveau.size(); j++){

                TreeItem<String> nivitem = new TreeItem<String> ("Niveau " + this.loadedImmeubles.get(i).niveau.get(j).getNivId());
                nivitem.setExpanded(true);
                item.getChildren().add(nivitem);
                this.objectMap.put(nivitem, this.loadedImmeubles.get(i).niveau.get(j));

                //chaque appartement
                for (int k = 0; k < this.loadedImmeubles.get(i).niveau.get(j).appartements.size(); k++)
                {

                    TreeItem<String> aptitem = new TreeItem<String> ("");
                    aptitem.setGraphic(new Label("Appartement " + this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).getAppartId()));
                    aptitem.setExpanded(false);
                    nivitem.getChildren().add(aptitem);

                    //chaque piece
                    for (int l = 0; l < this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.size(); l++)
                    {

                        TreeItem<String> pieceitem = new TreeItem<String> ("Piece " + this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).getPieceId());
                        pieceitem.setExpanded(false);
                        aptitem.getChildren().add(pieceitem);
                        this.objectMap.put(pieceitem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l));

                        //chaque mur
                        for (int m = 0; m < this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.size(); m++)
                        {                           
                            ContextMenu cMenu = new ContextMenu();

                            MenuItem m1 = new MenuItem("Changer revetement");
    
                            cMenu.getItems().addAll(m1);

                            TreeItem<String> muritem = new TreeItem<String> ("");
                            muritem.setGraphic(new Label("Mur " + this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.get(m).getID()));

                            muritem.getGraphic().setOnContextMenuRequested(event -> {

                                cMenu.show(muritem.getGraphic(), event.getScreenX(), event.getScreenY());

                            });

                            this.objectMap.put(muritem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.get(m));

                            m1.setOnAction(event -> {
                                //revetementSelection(this.objectMap.get(muritem));
                            });

                            pieceitem.getChildren().add(muritem);
                        }

                        //plafond et sol
                        TreeItem<String> plafondItem = new TreeItem<String> ("");
                        plafondItem.setGraphic(new Label("Plafond"));
                        TreeItem<String> solItem = new TreeItem<String> ("");
                        solItem.setGraphic(new Label("Sol"));

                        ContextMenu cMenup = new ContextMenu();
                        ContextMenu cMenus = new ContextMenu();

                        MenuItem m1p = new MenuItem("Changer revetement");
                        MenuItem m1s = new MenuItem("Changer revetement");

                        /*
                        m1p.setOnAction(event -> {
                            revetementSelection(this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).soletplafond.get(1));
                        });
                        m1s.setOnAction(event -> {
                            revetementSelection(this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).soletplafond.get(0));
                        });
                        */

                        cMenup.getItems().addAll(m1p);
                        cMenus.getItems().addAll(m1s);

                        plafondItem.getGraphic().setOnContextMenuRequested(event -> {
                            cMenup.show(plafondItem.getGraphic(), event.getScreenX(), event.getScreenY());
                        });

                        solItem.getGraphic().setOnContextMenuRequested(event -> {
                            cMenus.show(solItem.getGraphic(), event.getScreenX(), event.getScreenY());
                        });

                        this.objectMap.put(solItem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).soletplafond.get(0));
                        this.objectMap.put(plafondItem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).soletplafond.get(1));

                        pieceitem.getChildren().addAll(plafondItem, solItem);
                    }

                }

            }
            rootItem.getChildren().add(item);
        }
        this.setRoot(rootItem);
    }

    private Revetement revetementSelection(Surface s) {
        Stage selectionStage = new Stage();

        // Create the selection scene
        Button selectButton = new Button("Select");
        selectButton.setOnAction(e -> selectionStage.close());
        VBox selectionLayout = new VBox(selectButton);
        selectionLayout.setSpacing(10);
        selectionLayout.setPadding(new Insets(10));
        Scene selectionScene = new Scene(selectionLayout, 200, 100);

        selectionStage.setTitle("Revetements");
        selectionStage.setScene(selectionScene);

        Revetement r = new Revetement();

        // Set the selection stage as a new window
        selectionStage.showAndWait();
        return r;
    }

}
