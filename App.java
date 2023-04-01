import java.io.IOException;

import Bati.*;
import Bati.Revetements.Revetement;

public class App {
    public static void main(String[]args)
    {   
        Immeuble myImmeuble;
        try
        {
            myImmeuble = Immeuble.loadImmeuble("FirstImmeuble.txt");
            System.out.println(myImmeuble.niveau[0].appartements.get(0).pieces.get(0).pieceIntegre());
            
        }
        catch(IOException | ClassNotFoundException e)
        {
        }
        System.out.println(Revetement.readDef());
    }
}