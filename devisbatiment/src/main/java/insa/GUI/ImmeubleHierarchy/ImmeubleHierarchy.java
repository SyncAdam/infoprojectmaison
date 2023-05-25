package insa.GUI.ImmeubleHierarchy;

import insa.Batiment.Appartement;
import insa.Batiment.Immeuble;
import insa.Batiment.Niveau;
import insa.Batiment.Piece;
import insa.Batiment.Revetements.Revetement;
import insa.GUI.MainPane;

import java.util.ArrayList;

import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;

public class ImmeubleHierarchy extends TreeView<String>{

    MainPane parentPane;

    public ArrayList<Immeuble> loadedImmeubles;
    public ArrayList<Revetement> revetements;
    public ArrayList<Piece> nonClassePieces;
    public ArrayList<Appartement> nonClasseAppartements;
    public ArrayList<Niveau> nonClasseNiveaux;

    public HierarchyContext hierarchyMenu;
    private IHClick hClickHandler;

    public ImmeubleHierarchy(MainPane parentPane)
    {
        this.parentPane = parentPane;
        this.loadedImmeubles = new ArrayList<Immeuble>();
        this.revetements  = Revetement.readDef();
        this.nonClassePieces = new ArrayList<>();
        this.nonClasseAppartements = new ArrayList<>();
        this.nonClasseNiveaux = new ArrayList<>();

        this.hierarchyMenu = new HierarchyContext(this);

        hClickHandler = new IHClick(this);
        
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, hClickHandler);
    }

    public void hierarchyRefresh()
    {

        TreeItem<String> rootItem = new TreeItem<String> ("Project");
        rootItem.setExpanded(true);

        //pour chaque immeuble
        for (int i = 0; i < this.loadedImmeubles.size(); i++) {

            TreeItem<String> item = new TreeItem<String> ("");
            item.setGraphic(new Label("Immeuble " + this.loadedImmeubles.get(i).idImmeuble));
            HierarchyImmContext itemMenu = new HierarchyImmContext(item, this.loadedImmeubles.get(i), this);
            item.getGraphic().setOnContextMenuRequested(event -> {
                itemMenu.show(item.getGraphic(), event.getScreenX(), event.getScreenY());
            });
            item.setExpanded(true);
            //chaque niveau

            for (int j = 0; j < this.loadedImmeubles.get(i).niveau.size(); j++){

                TreeItem<String> nivitem = new TreeItem<String> ("");
                HierarchyNivContext nivMenu = new HierarchyNivContext(nivitem, this.loadedImmeubles.get(i).niveau.get(j), this);
                nivitem.setGraphic(new Label("Niveau " + this.loadedImmeubles.get(i).niveau.get(j).getIdNiveau()));
                nivitem.getGraphic().setOnContextMenuRequested(event -> {
                    nivMenu.show(nivitem.getGraphic(), event.getScreenX(), event.getScreenY());
                });
                nivitem.setExpanded(true);
                item.getChildren().add(nivitem);

                ClickEventHandler clicker = new ClickEventHandler(nivitem, this, this.loadedImmeubles.get(i).niveau.get(j));
                        
                nivitem.getGraphic().addEventHandler(MouseEvent.MOUSE_CLICKED, clicker); 
                //chaque appartement
                
                for (int k = 0; k < this.loadedImmeubles.get(i).niveau.get(j).appartements.size(); k++)
                {

                    TreeItem<String> aptitem = new TreeItem<String> ("");
                    HierarchyAppartContext aptMenu = new HierarchyAppartContext(aptitem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k), this);
                    aptitem.setGraphic(new Label("Appartement " + this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).getIdAppartement()));
                    aptitem.setExpanded(false);

                    aptitem.getGraphic().setOnContextMenuRequested(event -> {
                        aptMenu.show(aptitem.getGraphic(), event.getScreenX(), event.getScreenY());
                    });

                    nivitem.getChildren().add(aptitem);

                    ClickEventHandler clicker0 = new ClickEventHandler(aptitem, this, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k));
                        
                    aptitem.getGraphic().addEventHandler(MouseEvent.MOUSE_CLICKED, clicker0); 

                    //chaque piece
                    for (int l = 0; l < this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.size(); l++)
                    {

                        TreeItem<String> pieceitem = new TreeItem<String>("");
                        HierarchyPieceContext pieceMenu = new HierarchyPieceContext(pieceitem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l), this);

                        pieceitem.setGraphic(new Label("Piece " + this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).getPieceId()));
                        pieceitem.setExpanded(false);
                        pieceitem.getGraphic().setOnContextMenuRequested(event -> {
                            pieceMenu.show(pieceitem.getGraphic(), event.getScreenX(), event.getScreenY());
                        });

                        aptitem.getChildren().add(pieceitem);

                        ClickEventHandler clicker1 = new ClickEventHandler(pieceitem, this, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l));
                        
                        pieceitem.getGraphic().addEventHandler(MouseEvent.MOUSE_CLICKED, clicker1); 

                        //chaque mur
                        for (int m = 0; m < this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.size(); m++)
                        {                           
                            TreeItem<String> muritem = new TreeItem<String> ("");
                            HierarchySurfaceItemContext murMenu = new HierarchySurfaceItemContext(muritem, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.get(m), this);
                            muritem.setGraphic(new Label("Mur " + this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.get(m).getID()));

                            muritem.getGraphic().setOnContextMenuRequested(event -> {

                                murMenu.show(muritem.getGraphic(), event.getScreenX(), event.getScreenY());

                            });

                            ClickEventHandler clickerMur = new ClickEventHandler(this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l), this, this.loadedImmeubles.get(i).niveau.get(j).appartements.get(k).pieces.get(l).murs.get(m));
                        
                            muritem.getGraphic().addEventHandler(MouseEvent.MOUSE_CLICKED, clickerMur); 

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

        TreeItem<String> nonClasse = new TreeItem<String> ("");
        nonClasse.setGraphic(new Label("Non classé"));
        nonClasse.setExpanded(true);

        for(int i = 0; i < nonClasseNiveaux.size(); i++)
        {
            TreeItem<String> ncNivItem = new TreeItem<String> ("");
            HierarchyNivContext ncNivMenu = new HierarchyNivContext(ncNivItem, this.nonClasseNiveaux.get(i), this);
            ncNivItem.setGraphic(new Label("Niveau " + this.nonClasseNiveaux.get(i).getIdNiveau()));
            ncNivItem.getGraphic().setOnContextMenuRequested(event -> {
                ncNivMenu.show(ncNivItem.getGraphic(), event.getScreenX(), event.getScreenY());
            });
            ncNivItem.setExpanded(true);
            nonClasse.getChildren().add(ncNivItem);
        }
        for(int i = 0; i < nonClasseAppartements.size(); i++)
        {
            TreeItem<String> aptitem = new TreeItem<String> ("");
            HierarchyAppartContext aptMenu = new HierarchyAppartContext(aptitem, this.nonClasseAppartements.get(i), this);
            aptitem.setGraphic(new Label("Appartement " +this.nonClasseAppartements.get(i).getIdAppartement()));
            aptitem.setExpanded(false);

            aptitem.getGraphic().setOnContextMenuRequested(event -> {
                aptMenu.show(aptitem.getGraphic(), event.getScreenX(), event.getScreenY());
            });

            nonClasse.getChildren().add(aptitem);
        }
        for(int i = 0; i < nonClassePieces.size(); i++)
        {
            TreeItem<String> pieceitem = new TreeItem<String>("");
            HierarchyPieceContext pieceMenu = new HierarchyPieceContext(pieceitem, this.nonClassePieces.get(i), this);

            pieceitem.setGraphic(new Label("Piece " + this.nonClassePieces.get(i).getPieceId()));
            pieceitem.setExpanded(false);
            pieceitem.getGraphic().setOnContextMenuRequested(event -> {
                pieceMenu.show(pieceitem.getGraphic(), event.getScreenX(), event.getScreenY());
            });

            ClickEventHandler clicker = new ClickEventHandler(pieceitem, this, this.nonClassePieces.get(i));
                        
            pieceitem.getGraphic().addEventHandler(MouseEvent.MOUSE_CLICKED, clicker);

            nonClasse.getChildren().add(pieceitem);

            //chaque mur
            for (int m = 0; m < this.nonClassePieces.get(i).murs.size(); m++)
            {                           
                TreeItem<String> muritem = new TreeItem<String> ("");
                HierarchySurfaceItemContext murMenu = new HierarchySurfaceItemContext(muritem, this.nonClassePieces.get(i).murs.get(m), this);
                muritem.setGraphic(new Label("Mur " + this.nonClassePieces.get(i).murs.get(m).getID()));

                muritem.getGraphic().setOnContextMenuRequested(event -> {

                    murMenu.show(muritem.getGraphic(), event.getScreenX(), event.getScreenY());

                });

                ClickEventHandler clickerMur = new ClickEventHandler(this.nonClassePieces.get(i), this, this.nonClassePieces.get(i).murs.get(m));

                muritem.getGraphic().addEventHandler(MouseEvent.MOUSE_CLICKED, clickerMur); 

                pieceitem.getChildren().add(muritem);
            }

            //plafond et sol
            TreeItem<String> plafondItem = new TreeItem<String> ("");
            plafondItem.setGraphic(new Label("Plafond"));
            TreeItem<String> solItem = new TreeItem<String> ("");
            solItem.setGraphic(new Label("Sol"));

            HierarchySurfaceItemContext plafondMenu = new HierarchySurfaceItemContext(plafondItem, this.nonClassePieces.get(i).soletplafond.get(1), this);
            HierarchySurfaceItemContext solMenu = new HierarchySurfaceItemContext(solItem, this.nonClassePieces.get(i).soletplafond.get(0), this);

            plafondItem.getGraphic().setOnContextMenuRequested(event -> {

                plafondMenu.show(plafondItem.getGraphic(), event.getScreenX(), event.getScreenY());

            });
            solItem.getGraphic().setOnContextMenuRequested(event -> {

                solMenu.show(solItem.getGraphic(), event.getScreenX(), event.getScreenY());

            });

            pieceitem.getChildren().addAll(plafondItem, solItem);
        }

        rootItem.getChildren().add(nonClasse);
        this.setRoot(rootItem);

    }

}
