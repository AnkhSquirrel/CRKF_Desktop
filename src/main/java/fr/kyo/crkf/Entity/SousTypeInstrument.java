package fr.kyo.crkf.Entity;

public class SousTypeInstrument {
    private int id;
    private String sousType;

    public SousTypeInstrument(int id, String sousType) {
        this.id = id;
        this.sousType = sousType;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getSousType() {
        return sousType;
    }
    public void setSousType(String sousType) {
        this.sousType = sousType;
    }
}
