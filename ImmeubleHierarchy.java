package insa.GUI.ImmeubleHierarchy;

import insa.Batiment.Immeuble;
import insa.Batiment.Revetements.Revetement;
import insa.GUI.MainPane;

import java.util.ArrayList;

import javafx.scene.control.TreeView;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;

public class ImmeubleHierarchy extends TreeView<String>{

    MainPane parentPane;

    public ArrayList<Immeuble> loadedImmeubles;
    public ArrayList<Revetement> revetements;

    public ImmeubleHierarchy(MainPane parentPane)
    {
        this.parentPane = parentPane;
        this.loadedImmeubles = new ArrayList<Immeuble>();
        this.revetements  = Revetement.readDef();    
    }

    public void hierarchyRefresh()
    {

        TreeItem<String> rootItem = new TreeItem<String> ("Project");
        rootItem.setExpanded(true);

        //pour chaque immeuble
        for (int i = 0; i < this.loadedImmeubles.size(); i++) {

            TreeItem<String> item = new TreeItem<String> ("");
            item.setGraphic(new Label("Immeuble " + this.loadedImmeubles.get(i).idImmeuble));
            item.setExpanded(true);
            //chaque niveau

            for (int j = 0; j < this.loadedImmeubles.get(i).niveau.size(); j++){

                TreeItem<String> nivitem = new TreeItem<String> ("");
                HierarchyItemContext nivMenu = new HierarchyItemContext(nivitem, this.loadedImmeubles.get(i).niveau.get(j), this);
                nivitem.setGraphic(new Label("Niveau " + this.loadedImmeubles.get(i).niveau.get(j).getNivId()));
                nivitem.getGraphic().setOnContextMenuRequested(event -> {
                    nivMenu.show(nivitem.getGraphic(), event.getScreenX(), event.getScreenY());
                });
                nivitem.setExpanded(true);
                item.getChildren().add(nivitem);
                //chaque appartement
                
                for (int k = 0; k < this.loadedImmeubles.get(i).niveau.get(j).appartements.size(); k++)
                {

                    TreeItem<String> aptitem = new TreeItem<String> ("");
                    HierarchyItemContext aptMenu = new HierarchyItemContext(aptitem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k), this);
                    aptitem.setGraphic(new Label("Appartement " + this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).getAppartId()));
                    aptitem.setExpanded(false);

                    aptitem.getGraphic().setOnContextMenuRequested(event -> {
                        aptMenu.show(aptitem.getGraphic(), event.getScreenX(), event.getScreenY());
                    });

                    nivitem.getChildren().add(aptitem);

                    //chaque piece
                    for (int l = 0; l < this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.size(); l++)
                    {

                        TreeItem<String> pieceitem = new TreeItem<String>("");
                        HierarchyItemContext pieceMenu = new HierarchyItemContext(pieceitem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l), this);

                        pieceitem.setGraphic(new Label("Piece " + this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).getPieceId()));
                        pieceitem.setExpanded(false);
                        pieceitem.getGraphic().setOnContextMenuRequested(event -> {
                            pieceMenu.show(pieceitem.getGraphic(), event.getScreenX(), event.getScreenY());
                        });

                        aptitem.getChildren().add(pieceitem);

                        //chaque mur
                        for (int m = 0; m < this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.size(); m++)
                        {                           
                            TreeItem<String> muritem = new TreeItem<String> ("");
                            HierarchySurfaceItemContext murMenu = new HierarchySurfaceItemContext(muritem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.get(m), this);
                            muritem.setGraphic(new Label("Mur " + this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.get(m).getID()));

                            muritem.getGraphic().setOnContextMenuRequested(event -> {

                                murMenu.show(muritem.getGraphic(), event.getScreenX(), event.getScreenY());

                            });

                            pieceitem.getChildren().add(muritem);
                        }

                        //plafond et sol
                        TreeItem<String> plafondItem = new TreeItem<String> ("");
                        plafondItem.setGraphic(new Label("Plafond"));
                        TreeItem<String> solItem = new TreeItem<String> ("");
                        solItem.setGraphic(new Label("Sol"));

                        HierarchySurfaceItemContext plafondMenu = new HierarchySurfaceItemContext(plafondItem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).soletplafond.get(1), this);
                        HierarchySurfaceItemContext solMenu = new HierarchySurfaceItemContext(solItem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).soletplafond.get(0), this);

                        plafondItem.getGraphic().setOnContextMenuRequested(event -> {

                            plafondMenu.show(plafondItem.getGraphic(), event.getScreenX(), event.getScreenY());

                        });
                        solItem.getGraphic().setOnContextMenuRequested(event -> {

                            solMenu.show(solItem.getGraphic(), event.getScreenX(), event.getScreenY());

                        });

                        pieceitem.getChildren().addAll(plafondItem, solItem);
                    }

                }

            }
            rootItem.getChildren().add(item);
        }
        this.setRoot(rootItem);
    }

}
