public class Niveau {
    
    int idNiveau;
    Appartement[] appartements;

    Niveau(int id, Appartement[] apps)
    {
        this.idNiveau = id;
        this.appartements = apps;
    }

    Niveau(int id, int nbreApps)
    {
        this.idNiveau = id;
        Appartement []apps = new Appartement[nbreApps];

        for(int i = 0; i < nbreApps; i++)
        {
            System.out.println("Donnez le nombre de piece de l'appartement" + (i+1));
            int n = Lire.i();
            apps[i] = new Appartement(i, n);
        }

        this.appartements = apps;

    }

}
