package fr.kyo.crkf.Entity;

public class Adresse {
    private int id_adresse;
    private String adresse;
    private Ville ville;

    public Adresse(int id_adresse, String adresse, Ville ville) {
        this.id_adresse = id_adresse;
        this.adresse = adresse;
        this.ville = ville;
    }
    public int getId_adresse() {
        return id_adresse;
    }
    public void setId_adresse(int id_adresse) {
        this.id_adresse = id_adresse;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public Ville getVille() {
        return ville;
    }
    public void setVille(Ville ville) {
        this.ville = ville;
    }
}
