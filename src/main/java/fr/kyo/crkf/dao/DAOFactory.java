package fr.kyo.crkf.dao;

import java.sql.Connection;

public class DAOFactory {
    private DAOFactory() {}

    private static final Connection connexion = CRKFConnect.getInstance();
    public static VilleDAO getVilleDAO() {
        return new VilleDAO(connexion);
    }
    public static InstrumentDAO getInstrumentDAO() {
        return new InstrumentDAO(connexion);
    }
    public static FamilleDAO getFamilleDAO() {
        return new FamilleDAO(connexion);
    }
    public static DepartementDAO getDepartementDAO() {
        return new DepartementDAO(connexion);
    }
    public static ClassificationDAO getClassificationDAO() {
        return new ClassificationDAO(connexion);
    }
    public static AdresseDAO getAdresseDAO() {
        return new AdresseDAO(connexion);
    }
    public static EcoleDAO getEcoleDAO() {
        return new EcoleDAO(connexion);
    }
    public static CycleDAO getCycleDAO() {
        return new CycleDAO(connexion);
    }
    public static PersonneDAO getPersonneDAO() {
        return new PersonneDAO(connexion);
    }
}
