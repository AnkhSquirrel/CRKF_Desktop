package fr.kyo.crkf.controller;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Diplome;
import fr.kyo.crkf.Entity.Personne;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DetailProfesseurController {
    @FXML
    private Label nom;
    @FXML
    private Label cv;
    @FXML
    private Label ecole;
    @FXML
    private TableView<Diplome> diplomeTable;
    @FXML
    private TableColumn<Diplome,String> instrumentColumn;
    @FXML
    private TableColumn<Diplome,String> cycleColumn;
    private ApplicationCRKF applicationCRKF;
    private Personne personne;

    @FXML
    private void initialize(){
        instrumentColumn.setCellValueFactory(cellData -> cellData.getValue().getInstrument().getNomStringProperty());
        cycleColumn.setCellValueFactory(cellData -> cellData.getValue().getCycle().getCycleStringProperty());
    }
    public void setApplicationCRKF(ApplicationCRKF applicationCRKF){
        this.applicationCRKF = applicationCRKF;
    }
    public void setPersonne(Personne personne){
        this.personne = personne;
        nom.setText(personne.getPrenom() + " " + personne.getNom());
        cv.setText(String.valueOf(personne.getVehiculeCv()));
        ecole.setText(personne.getEcole().getNom());

        diplomeTable.setItems(FXCollections.observableArrayList(personne.getDiplomes()));
    }
    @FXML
    private void openEcoleAroundPage() {
        applicationCRKF.openEcoleAroundPage(personne);
    }
}
