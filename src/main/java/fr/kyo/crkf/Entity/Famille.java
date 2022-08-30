package fr.kyo.crkf.Entity;

public class Famille {
    private int id_famille;
    private String famille;
    private Classification classification;

    public Famille(int id_famille, String famille, Classification classification) {
        this.id_famille = id_famille;
        this.famille = famille;
        this.classification = classification;
    }
    public int getId_famille() {
        return id_famille;
    }
    public void setId_famille(int id_famille) {
        this.id_famille = id_famille;
    }
    public String getfamille() {
        return famille;
    }
    public void setfamille(String famille) {
        this.famille = famille;
    }
    public Classification getclassification() {
        return classification;
    }
    public void setclassification(Classification classification) {
        this.classification = classification;
    }
}
