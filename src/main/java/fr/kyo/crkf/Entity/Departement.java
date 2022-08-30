package fr.kyo.crkf.Entity;

public class Departement {
    private int id;
    private String departement;

    public Departement(int id,String departement) {
        this.id = id;
        this.departement = departement;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDepartement() {
        return departement;
    }
    public void setDepartement(String departement) {
        this.departement = departement;
    }
}
