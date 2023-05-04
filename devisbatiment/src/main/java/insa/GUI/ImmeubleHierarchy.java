package insa.GUI;

import insa.Batiment.Immeuble;

import java.util.ArrayList;

import javafx.scene.control.TreeView;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TreeItem;

public class ImmeubleHierarchy extends TreeView{

    public void hierarchyRefresh(ArrayList<Immeuble> immeubles)
    {
        TreeItem<String> rootItem = new TreeItem<String> ("Project");
        rootItem.setExpanded(true);
        for (int i = 0; i < immeubles.size()-1; i++) {
            TreeItem<String> item = new TreeItem<String> ("Immeuble" + immeubles.get(i).idImmeuble);           
            rootItem.getChildren().add(item);
        }        
        TreeView<String> tree = new TreeView<String> (rootItem); 
        this.setRoot(rootItem);
    }
   
    
}
