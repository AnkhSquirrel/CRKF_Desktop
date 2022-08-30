package fr.kyo.crkf.Entity;

public class Ecole {
    private int id;
    private Adresse adresse;

    public Ecole(int id,Adresse adresse) {
        this.id = id;
        this.adresse = adresse;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Adresse getAdresse() {
        return adresse;
    }
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
}
