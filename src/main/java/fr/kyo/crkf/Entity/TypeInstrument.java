package fr.kyo.crkf.Entity;

public class TypeInstrument {
    private int id;
    private String type;
    private SousTypeInstrument sousTypeInstrument;

    public TypeInstrument(int id, String type, SousTypeInstrument sousTypeInstrument) {
        this.id = id;
        this.type = type;
        this.sousTypeInstrument = sousTypeInstrument;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public SousTypeInstrument getSousTypeInstrument() {
        return sousTypeInstrument;
    }
    public void setSousTypeInstrument(SousTypeInstrument sousTypeInstrument) {
        this.sousTypeInstrument = sousTypeInstrument;
    }
}
