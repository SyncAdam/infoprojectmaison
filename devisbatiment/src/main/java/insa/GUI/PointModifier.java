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
    //boolean deleteButtonState = false;

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
        xCoord.setPromptText(xstring);
        yCoord.setPromptText(ystring);
        Button okButton = new Button("Modifier");
        Button DelButton = new Button("Supprimer");

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(okButton, DelButton);
        
        VBox disposition = new VBox(); //on créer un groupe root
       
        disposition.getChildren().add(label);
        disposition.getChildren().add(xCoord);
        disposition.getChildren().add(yCoord);
        disposition.getChildren().add(buttonBar);      

        Scene scenePoint = new Scene(disposition, 200,100, Color.GREEN ); //on créer une scene à laquelle on ajoute le grp root et on def la color du grp.
        newPointStage.setResizable(false);
        //newPointStage.setTitle("Modificateur de point");
        newPointStage.setOnCloseRequest(null);
        newPointStage.setScene(scenePoint);
        newPointStage.show();

        //parentPane.canva.DisplayCoin(coin);

        
        if(coin.onAWall ==true){
            DelButton.setDisable(true);
            okButton.setText("Quitter");
            xCoord.setDisable(true);
            yCoord.setDisable(true);
        }        

     
        newPointStage.setOnCloseRequest(e -> { //permet juste de désactiver la fermeture de la fen^tre pour éviter tout bug
            e.consume(); // Annule l'événement de fermeture
           
        });


        DelButton.setOnAction(e-> {
            parentPane.canva.coinTab.set(coin.getId(), new Coin(coin.getId(), 0, 0)); //on remplace le point supprimé par un point "nul", on ne le supprime pas de coinTab car sinon ça décallerait tout les id par rapport à la position dans le tableau et ça serait le barda
            newPointStage.close();
        });

        okButton.setOnAction(e->{
            if(coin.onAWall == true){
            Double cx = Double.parseDouble(xCoord.getText()); //on récup la valeur des field et cconv en double
            Double cy = Double.parseDouble(yCoord.getText());

            coin.setX(cx); //on modif coords du point
            coin.setY(cy);
            
            parentPane.canva.DisplayCoin(coin);

            newPointStage.close();
            }else{
                parentPane.canva.DisplayCoin(coin);
                newPointStage.close();
            }

        });


    }
    

    }
    
