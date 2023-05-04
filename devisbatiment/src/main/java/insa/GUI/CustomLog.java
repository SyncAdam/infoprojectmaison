package insa.GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class CustomLog  {
    
    Text logTxt = new Text();//création d'un label
    BorderPane root;

    CustomLog(BorderPane root){
        this.root = root;
    }
    
    public void Initialise(){
        
        logTxt.setText("Texte par défaut"); //texte du label
        root.setBottom(logTxt);
        
        root.setBottom(logTxt); //on ajoute le label en bas de du border pane.
        BorderPane.setAlignment(logTxt ,Pos.BOTTOM_LEFT); //on centre le txt.

    }

    public void setLogTxt(String txt){
       logTxt.setText(txt);
    }
}
