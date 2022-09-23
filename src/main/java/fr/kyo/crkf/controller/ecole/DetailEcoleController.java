package fr.kyo.crkf.controller.ecole;

import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.entity.*;
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
    private TableView<Personne> profEmbauche;
    @FXML
    private TableColumn<Personne, String> profEmbauchePrenom;
    @FXML
    private TableColumn<Personne, String> profEmbaucheNom;
    private Ecole ecole;
    private EcoleController ecoleController;

    @FXML
    private void initialize(){
        profEmbaucheNom.setCellValueFactory(cellData -> cellData.getValue().getNomStringProperty());
        profEmbauchePrenom.setCellValueFactory(cellData -> cellData.getValue().getPrenomStringProperty());
    }

    public void setApplicationCRKF(ApplicationCRKF applicationCRKF) {
        this.applicationCRKF = applicationCRKF;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
        labelAdresse.setText(ecole.getEcoleAdresse().getAdresseLibelle());
        labelVille.setText(ecole.getEcoleAdresse().getVille().getVilleLibelle());
        labelDepartement.setText(ecole.getEcoleAdresse().getVille().getDepartement().toString());
        labelNomEcole.setText("Ecole : " + ecole.getEcoleNom());
        profEmbauche.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getByEcole(ecole.getEcoleId())));
    }

    @FXML
    private void deleteEcole(){
        if(applicationCRKF.deleteModal()){
            DAOFactory.getEcoleDAO().delete(ecole);
            ecoleController.filter();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("L'école n'a pas été supprimée");
            alert.showAndWait();
        }
    }
    @FXML
    private void updateEcole(){
        applicationCRKF.openUpdateEcole(ecole, ecoleController);
    }

    @FXML
    private void closeDetail(){
        ecoleController.closeDetail();
    }

    public void setEcoleController(EcoleController ecoleController) {
        this.ecoleController = ecoleController;
    }

}
