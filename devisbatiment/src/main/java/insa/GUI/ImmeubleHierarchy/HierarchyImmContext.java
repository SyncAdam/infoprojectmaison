package insa.GUI.ImmeubleHierarchy;

import insa.Batiment.Immeuble;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

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

        this.getItems().addAll(mi2);

        mi2.setOnAction(event -> {
            System.out.println(Immeuble.calculRevetement(this.targetObject)); 
        });
    }
    
}
