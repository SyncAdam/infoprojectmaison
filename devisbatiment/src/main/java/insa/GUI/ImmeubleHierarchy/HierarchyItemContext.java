package insa.GUI.ImmeubleHierarchy;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

public class HierarchyItemContext extends ContextMenu{

    TreeItem<String> parentObject;
    ImmeubleHierarchy fatherTree;
    Object targetObject;

    HierarchyItemContext(TreeItem<String> parentObject, Object targetObject, ImmeubleHierarchy fatherTree)
    {
        this.parentObject = parentObject;
        this.fatherTree = fatherTree;
        this.targetObject = targetObject;

        MenuItem mi1 = new MenuItem("Voir");
        MenuItem mi2 = new MenuItem("Revetements");

        this.getItems().addAll(mi1, mi2);

        mi1.setOnAction(event -> {
            this.fatherTree.parentPane.canva.DisplayObject(targetObject);
        });

        mi2.setOnAction(event -> {
            
        });
    }
    
}
