package fr.kyo.crkf.controller;

import fr.kyo.crkf.ApplicationCRKF;
import javafx.fxml.FXML;

public class NavbarController {

    @FXML
    private ApplicationCRKF application;

    public void setMainApp(ApplicationCRKF application) {
        this.application = application;
    }

    @FXML
    protected void openMainMenu(){
        application.openMainMenu();
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
    protected void openAboutModal(){
        application.openAboutModal();
    }

    @FXML
    protected void openFamilleGestion(){
        application.openFamilleGestion();
    }

    @FXML
    protected void openClassificationGestion(){
        application.openClassificationGestion();
    }

    @FXML
    protected void openVilleGestion(){
        application.openVilleGestion();
    }

    @FXML
    protected void openDepartementGestion(){
        application.openDepartementGestion();
    }

    @FXML
    protected void openCycleGestion(){
        application.openCycleGestion();
    }

}
