package insa.GUI;

import insa.Batiment.Coin;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ManualPoint extends App{



Coin Coin = new Coin(1, 70, 100);



    public void Initialise(){

        Stage newPointStage = new Stage();
        TextField idField = new TextField("ID du point");
        TextField xCoord = new TextField("Coordonnée en X");
        TextField yCoord = new TextField("Coordonnée en Y");
        Button okButton = new Button("Ok ");
        
        VBox disposition = new VBox(); //on créer un groupe root
        disposition.getChildren().add(idField);
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
            Integer id = Integer.parseInt(idField.getText());
            System.out.println(id + cx + cy);
            Coin coin = new Coin(id, cx, cy);
            coin.DisplayPoint(root);
            newPointStage.close();

        });

    }
    


    
}
