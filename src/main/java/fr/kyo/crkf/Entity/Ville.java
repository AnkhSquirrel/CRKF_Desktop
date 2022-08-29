package fr.kyo.crkf.Entity;

public class Ville {
    private int id_ville;
    private String ville;
    private Departement departement;

    public Ville(int id_ville, String ville, Departement departement) {
        this.id_ville = id_ville;
        this.ville = ville;
        this.departement = departement;
    }
    public int getId_ville() {
        return id_ville;
    }
    public void setId_ville(int id_ville) {
        this.id_ville = id_ville;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public Departement getDepartement() {
        return departement;
    }
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
