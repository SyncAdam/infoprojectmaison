import java.io.*;

public class Sol implements Serializable {
    Coin c1,c2;
    double surface;
    //Constructeur
    Sol(Coin c1, Coin c2){
        c1 = this.c1;
        c2 = this.c2;
        
    }

public double surface(){


surface = Math.sqrt(Math.pow((c1.cx-c2.cx),2))*Math.sqrt(Math.pow((c1.cy-c2.cy),2));

    return(surface);
}

}


