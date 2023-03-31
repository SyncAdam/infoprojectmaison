import java.io.*;

public class App {
    public static void main(String[]args)
    {   
        Niveau n0 = new Niveau(0, 3);
        System.out.println(n0.appartements.get(0).pieces.get(0).surface());
    }
}