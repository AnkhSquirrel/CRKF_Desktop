package fr.kyo.crkf.searchable;

import fr.kyo.crkf.entity.Ville;
import fr.kyo.crkf.dao.DAOFactory;

public class SearchableProfesseur {

    private String nomEtPrenom;
    private final int vehiculeCV;
    private int ville;
    private int departement;
    private int instrument;

    public SearchableProfesseur() {
        this.nomEtPrenom = "";
        this.vehiculeCV = 0;
        ville = 0;
        departement = 0;
        instrument = 0;
    }

    public String getNomEtPrenom() {
        return nomEtPrenom;
    }

    public void setNomEtPrenom(String nomEtPrenom) {
        this.nomEtPrenom = nomEtPrenom;
    }

    public Integer getVehiculeCV() {
        return vehiculeCV;
    }

    public void setVille(Ville ville) {
        this.ville = ville.getVilleId();
    }

    public Ville getVille() {
        return DAOFactory.getVilleDAO().getByID(ville);
    }
    public int getVilleId() {
        return ville;
    }

    public int getDepartementId() {
        return departement;
    }

    public void setDepartementId(int departement) {
        this.departement = departement;
    }

    public int getInstrumentId() {
        return instrument;
    }

    public void setInstrument(int instrumentId) {
        instrument = instrumentId;
    }
}
