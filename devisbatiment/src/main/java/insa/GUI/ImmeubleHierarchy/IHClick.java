package insa.GUI.ImmeubleHierarchy;

import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class IHClick implements EventHandler<MouseEvent>{

    ImmeubleHierarchy parentObject;

    IHClick(ImmeubleHierarchy parentObject)
    {
        this.parentObject = parentObject;
    }

    @Override
    public void handle(MouseEvent arg0) {
        Node node = arg0.getPickResult().getIntersectedNode();

        if((node instanceof Labeled) && arg0.getButton() == MouseButton.SECONDARY)
        {
            this.parentObject.hierarchyMenu.show(this.parentObject, arg0.getScreenX(), arg0.getScreenY());
        }
        else
        {
            ContextMenuEvent event = new ContextMenuEvent(ContextMenuEvent.CONTEXT_MENU_REQUESTED, arg0.getScreenX(), arg0.getScreenY(), 0, 0, false, null);
            this.parentObject.fireEvent(event);
        }
    }
    
}
