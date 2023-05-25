package insa.GUI.ImmeubleHierarchy;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import insa.Batiment.Appartement;
import insa.Batiment.Mur;
import insa.Batiment.Niveau;
import insa.Batiment.Piece;
import javafx.event.EventHandler;

public class ClickEventHandler implements EventHandler<MouseEvent>{

    Object parentObject;
    ImmeubleHierarchy fatherTree;
    Object targetObject;

    ClickEventHandler(Object parentObject, ImmeubleHierarchy fatherTree, Object targetObject)
    {
        this.parentObject = parentObject;
        this.fatherTree = fatherTree;
        this.targetObject = targetObject;
    }

    @Override
    public void handle(MouseEvent arg0) {

        if(this.targetObject instanceof Piece)
        {
            if(this.fatherTree.parentPane.canva.selectedPiece != null)
            {
                this.fatherTree.parentPane.canva.HilightRoom(this.fatherTree.parentPane.canva.selectedPiece, this.fatherTree.parentPane.backgroundColor);
            }
            this.fatherTree.parentPane.canva.clearCanva();
            if(this.fatherTree.parentPane.canva.selectedAppartement != null) this.fatherTree.parentPane.canva.DisplayObject(this.fatherTree.parentPane.canva.selectedAppartement);
            this.fatherTree.parentPane.canva.selectedPiece = (Piece) this.targetObject;
            this.fatherTree.parentPane.canva.HilightRoom((Piece) this.targetObject, Color.web("#eff704", 0.3));
            this.fatherTree.parentPane.canva.DisplayObject(this.targetObject);
        }
        else if(this.targetObject instanceof Mur)
        {
            int limit = this.fatherTree.parentPane.canva.selectedSurfaces.size();
            for(int i = 0; i < limit; i++)
            {
                this.fatherTree.parentPane.canva.deselectSurface(this.fatherTree.parentPane.canva.selectedSurfaces.get(i));
            }

            this.fatherTree.parentPane.canva.selectSurface((Mur)this.targetObject);
            
        }
        else if(this.targetObject instanceof Appartement)
        {
            this.fatherTree.parentPane.canva.resetSelection();
            this.fatherTree.parentPane.canva.clearCanva();
            this.fatherTree.parentPane.canva.DisplayObject(this.targetObject);
            this.fatherTree.parentPane.canva.selectedAppartement = (Appartement) this.targetObject;
        }
        else if(this.targetObject instanceof Niveau)
        {
            this.fatherTree.parentPane.canva.resetSelection();
            this.fatherTree.parentPane.canva.clearCanva();
            this.fatherTree.parentPane.canva.DisplayObject(this.targetObject);   
        }
        
    }
    
}
