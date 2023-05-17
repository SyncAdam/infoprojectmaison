package insa.GUI;
import insa.Batiment.Coin;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PointModifier {
    MainPane parentPane;
    Coin coin;

    PointModifier(MainPane parentPane, Coin coin)
    {
        this.parentPane = parentPane;
        this.coin = coin;
    }

    public void Initialise(){
        String xstring = Double.toString(coin.getX());
        String ystring = Double.toString(coin.getY());

        Stage newPointStage = new Stage();

        Label label = new Label("ID du point selectionné : " + coin.getId());
        
        TextField xCoord = new TextField(xstring);
        TextField yCoord = new TextField(ystring);
        Button okButton = new Button("Modifier");
        Button DelButton = new Button("Supprimer");

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(okButton, DelButton);
        
        VBox disposition = new VBox(); //on créer un groupe root
       
        disposition.getChildren().add(label);
        disposition.getChildren().add(xCoord);
        disposition.getChildren().add(yCoord);
        disposition.getChildren().add(buttonBar);
        

        
       

        Scene scenePoint = new Scene(disposition, 150,100, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
        
        newPointStage.setScene(scenePoint);
        newPointStage.show();

        //parentPane.canva.DisplayCoin(coin);

        okButton.setOnAction(e->{
            Double cx = Double.parseDouble(xCoord.getText()); //on récup la valeur des field et cconv en double
            Double cy = Double.parseDouble(yCoord.getText());

            coin.setX(cx); //on modif coords du point
            coin.setY(cy);
            
            parentPane.canva.DisplayCoin(coin);



            newPointStage.close();

        });


        /*DelButton.setOnAction(e->{
            
        });*/

    }
    
}
