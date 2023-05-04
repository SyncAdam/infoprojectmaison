package insa.GUI;

import insa.Batiment.Coin;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ManualPoint extends App{



    Coin Coin = new Coin(1, 70, 100);



    public void Initialise(){

        Stage newPointStage = new Stage();
        
        TextField xCoord = new TextField("Coordonnée en X");
        TextField yCoord = new TextField("Coordonnée en Y");
        Button okButton = new Button("Ok ");
        
        VBox disposition = new VBox(); //on créer un groupe root
       
        disposition.getChildren().add(xCoord);
        disposition.getChildren().add(yCoord);
        disposition.getChildren().add(okButton);
        

        
       

        Scene scenePoint = new Scene(disposition, 500,500, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
        
        newPointStage.setScene(scenePoint);
        newPointStage.show();

        Coin.DisplayPoint(root);
        okButton.setOnAction(e->{
            Double cx = Double.parseDouble(xCoord.getText()); //on récup la valeur des field et cconv en STRING
            Double cy = Double.parseDouble(yCoord.getText());

            coinTab.add(new Coin(coinTab.size(), cx, cy)); //on créé le new point dans le tableau
            coinTab.get(coinTab.size()-1).DisplayPoint(root); //on l'affiche



            newPointStage.close();

        });

    }
    


    
}
