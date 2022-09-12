package fr.kyo.crkf.controller.ecole;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.*;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DetailEcoleController {
    @FXML
    private Label labelDepartement;

    @FXML
    private Label labelVille;

    @FXML
    private Label labelNomEcole;

    @FXML
    private Label labelAdresse;

    @FXML
    private ApplicationCRKF applicationCRKF;
    @FXML
    private Button retour;
    @FXML
    private TableView<Personne> profEmbauche;
    @FXML
    private TableView<Personne> profTravail;
    @FXML
    private TableColumn<Personne, String> profEmbauchePrenom;
    @FXML
    private TableColumn<Personne, String> profEmbaucheNom;

    private Ecole ecole;
    private Personne personne;

    @FXML
    private void initialize(){
        profEmbaucheNom.setCellValueFactory(CellData -> CellData.getValue().getNomStringProperty());
        profEmbauchePrenom.setCellValueFactory(CellData -> CellData.getValue().getPrenomStringProperty());
    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF) {
        this.applicationCRKF = applicationCRKF;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
        labelAdresse.setText(ecole.getAdresse().getAdresse());
        labelVille.setText(ecole.getAdresse().getVille().getVille());
        labelDepartement.setText(ecole.getAdresse().getVille().getDepartement().toString());
        labelNomEcole.setText("Ecole : " + ecole.getNom());
        profEmbauche.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getByEcole(ecole.getId_ecole())));
    }

    @FXML
    public void openEcoleList() {
        applicationCRKF.openEcoleList();
    }

    @FXML
    private void deleteEcole(){
        if(applicationCRKF.deleteModal()){
            DAOFactory.getEcoleDAO().delete(ecole);
            applicationCRKF.openEcoleList();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("L'école n'a pas été supprimée");
            alert.showAndWait();
        }
    }
    @FXML
    private void updateEcole(){
        applicationCRKF.openUpdateEcole(ecole);
    }

}
