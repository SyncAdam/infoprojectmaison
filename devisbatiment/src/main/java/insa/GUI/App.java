package insa.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;



/*NOTE DE Timéo POUR ADAM DU 21/04  : 
 * Voilà tout ce que j'ai rajouté : 
 * Si on clique 2 fois sur un même point ca créér un mur avec le point qui existe déja.
 * Ensuite ça créér une ArrayList de mur dans l'ArrayList d'arraylist de mur qui s'appelle "murArrayTab" (avec détection du contour fermé)
 * Ensuite ca essaye de créer une pièce (ligne commentée vers ligne 150), mais il doit y avoir un soucis avec le constructeur de piece.
 * Je l'ai un tout petit peu modifié pour qu'il prenne en argument un arrayList de murs comme tu le voulais. Je te laisse décomenter la ligne 150 pour tester. (regarde la console)
 * Sinon tous les points sont sotckés dans coinTab et les murs dans murTab (arrayList).
 * Pour l'instant il y a une erreur quand on essaye de fermer une pièce en cliquant sur un point quand il manque un mur, il faut que je fasse un truc avec les exceptions.
 */


//Il reste a faire la surface des pieces, fixer le dessin, et le calcul de devis, quelquepart autre que dans le console


public class App extends Application {
    

    @Override
    public void start(Stage homeWindow) throws IOException {

        Scene myScene = new Scene(new MainPane(), Color.GREY);
       
        homeWindow.getIcons().add(new Image("file:src/main/resources/insa/icon.png"));
        homeWindow.setWidth(1200);
        homeWindow.setHeight(700); //def de la hauteur de la fenetre 
       
       
        homeWindow.setScene(myScene);
        homeWindow.show();
        
        //coinTab.add(new Coin(0, 0, 0)); //on place un point invisible à l'origine : permet de faire un mur dès le premier clic si l'user le veut.    
    }

    public static void main(String[] args) {
        launch();       
    }
}