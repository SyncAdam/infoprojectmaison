package insa.GUI;
import insa.Batiment.Coin;
import insa.Batiment.Mur;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WallModifier {
    MainPane parentPane;
    Mur mur;
    //boolean deleteButtonState = false;

    WallModifier(MainPane parentPane, Mur mur)
    {
        this.parentPane = parentPane;
        this.mur = mur;
    }

    public void Initialise(){
        String Longeur = Double.toString(mur.longueur());
        
        Stage newPointStage = new Stage();

        Label label = new Label("Le mur selectionné mesure : " + Longeur + " m.");
        

        Button okButton = new Button("ok");
        Button DelButton = new Button("Supprimer");

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(okButton, DelButton);
        
        VBox disposition = new VBox(); //on créer un groupe root
       
        disposition.getChildren().add(label);

        disposition.getChildren().add(buttonBar);      

        Scene scenePoint = new Scene(disposition, 400,100, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
        newPointStage.setResizable(false);
     
        newPointStage.setOnCloseRequest(null);
        newPointStage.setScene(scenePoint);
        newPointStage.show();

        //parentPane.canva.DisplayCoin(coin);

        okButton.setOnAction(e->{
            //parentPane.canva.DisplayMur(mur);
           
            newPointStage.close();

        });

        DelButton.setOnAction(e-> {
            if(mur.inARoom == false){ //si mur pas dans une pièce on peut le supprimer
            mur.ligne.setStrokeWidth(0); // on l'efface 
            mur.getDebut().onAWall = false;
            mur.getFin().onAWall = false;
            parentPane.canva.wallTab.remove(mur);
            newPointStage.close();}else{
                parentPane.log.setTxt("Vous ne pouvez pas supprimer un mur qui est dans une pièce");
                parentPane.log.setColor(Color.RED);
            }

        });


        /*newPointStage.setOnCloseRequest(e -> { //permet juste de désactiver la fermeture de la fen^tre pour éviter tout bug
            e.consume(); // Annule l'événement de fermeture
           
        });*/

    }
    
}