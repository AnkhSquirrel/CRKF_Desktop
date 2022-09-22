package fr.kyo.crkf.controller;

import fr.kyo.crkf.ApplicationCRKF;
import javafx.fxml.FXML;

public class AccueilController {

    @FXML
    private ApplicationCRKF application;

    public void setMainApp(ApplicationCRKF application) {
        this.application = application;
    }

    @FXML
    protected void openProfesseurList() {
        application.openProfesseurList();
    }

    @FXML
    protected void openEcoleList() {
        application.openEcoleList();
    }

    @FXML
    protected void openInstrumentList() {
        application.openInstrumentList();
    }

    @FXML
    protected void openGestionVille() {
        application.openVilleGestion();
    }

    @FXML
    protected void openGestionDepartement() {
        application.openDepartementGestion();
    }

    @FXML
    protected void openGestionFamille() {
        application.openFamilleGestion();
    }

    @FXML
    protected void openGestionClassification() {
        application.openClassificationGestion();
    }

    @FXML
    protected void openGestionCycle() {
        application.openCycleGestion();
    }

}