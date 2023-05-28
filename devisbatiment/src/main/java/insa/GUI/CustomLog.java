package insa.GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/*Nouvelles méthodes : setTxt(String) : permet de changer le txt du log
 * setColor(Color) : TRIVIAL
 * setSize(int) : change la taille d'écriture.
 */

public class CustomLog  {
    
    Text logTxt = new Text();//création d'un label
    BorderPane root;
    

    CustomLog(BorderPane root){
        this.root = root;
    }
    
    public void Initialise(){
        
        logTxt.setText("Application démarée"); //texte du label
        root.setBottom(logTxt);
        logTxt.setFont(Font.font(14));
        
        root.setBottom(logTxt); //on ajoute le label en bas de du border pane.
        BorderPane.setAlignment(logTxt ,Pos.BOTTOM_LEFT); //on centre le txt.

    }

    public void setTxt(String txt){
        logTxt.setFill(Color.BLACK); //à chaque fois qu'on affiche un new texte, il est noir par défaut, et on précise si on veut en changer la couleur
       logTxt.setText(txt);
    }

    public void setColor(Color couleur){
        logTxt.setFill(couleur);    
    }

    public void setSize(int taille){

        logTxt.setFont(Font.font(taille));
    }

    public void clear(){
        logTxt.setText("");
    }
}
