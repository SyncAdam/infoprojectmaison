package insa.GUI;

import insa.Batiment.Coin;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ManualPoint{


    MainPane parentPane;
    Coin coin;

    ManualPoint(MainPane parentPane)
    {
        this.parentPane = parentPane;
        coin = new Coin(1, 70, 100);
    }

    public void Initialise(){

        Stage newPointStage = new Stage();
        
        TextField xCoord = new TextField("Coordonnée en X");
        TextField yCoord = new TextField("Coordonnée en Y");
        Button okButton = new Button("Ok ");
        
        VBox disposition = new VBox(); //on créer un groupe root
       
        disposition.getChildren().add(xCoord);
        disposition.getChildren().add(yCoord);
        disposition.getChildren().add(okButton);
        

        
       

        Scene scenePoint = new Scene(disposition, 50,80, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
        
        newPointStage.setScene(scenePoint);
        newPointStage.show();

        //parentPane.canva.DisplayCoin(coin);

        okButton.setOnAction(e->{
            Double cx = Double.parseDouble(xCoord.getText()); //on récup la valeur des field et cconv en STRING
            Double cy = Double.parseDouble(yCoord.getText());

            parentPane.canva.coinTab.add(new Coin(parentPane.canva.coinTab.size(), cx, cy)); //on créé le new point dans le tableau
            parentPane.canva.DisplayCoin(parentPane.canva.coinTab.get(parentPane.canva.coinTab.size()-1));



            newPointStage.close();

        });

    }
    


    
}
