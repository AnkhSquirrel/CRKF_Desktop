package fr.kyo.crkf.Entity;

public class Ville {
    private int id;
    private String ville;
    private Departement departement;

    public Ville(int id,String ville, Departement departement) {
        this.id = id;
        this.ville = ville;
        this.departement = departement;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
