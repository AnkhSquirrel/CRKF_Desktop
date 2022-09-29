package fr.kyo.crkf.controller.ecole;

import com.jfoenix.controls.JFXDrawer;
import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.entity.*;
import fr.kyo.crkf.dao.DAOFactory;
import fr.kyo.crkf.tools.Pair;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.io.IOException;

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
    private JFXDrawer drawer;
    @FXML
    private ApplicationCRKF applicationCRKF;
    @FXML
    private TableView<Pair<Personne, Double>> prof;
    @FXML
    private TableColumn<Pair<Personne, Double>, String> profPrenom;
    @FXML
    private TableColumn<Pair<Personne, Double>, String> profNom;
    @FXML
    private TableColumn<Pair<Personne, Double>, String> situation;
    private Ecole ecole;
    private EcoleController ecoleController;

    @FXML
    private void initialize(){
        profNom.setCellValueFactory(cellData -> cellData.getValue().getFirst().getNomStringProperty());
        profPrenom.setCellValueFactory(cellData -> cellData.getValue().getFirst().getPrenomStringProperty());
        situation.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(ecole.getEcoleId() == cellData.getValue().getFirst().getEcoleID() ? "employé" : "éligible"));

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
        prof.setItems(FXCollections.observableArrayList(DAOFactory.getPersonneDAO().getByDistance(ecole.getEcoleAdresse().getVille().getLatitude(), ecole.getEcoleAdresse().getVille().getLongitude())));
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
        ecoleController.filter();
        ecoleController.closeDetail();
    }

    @FXML
    private void openFamilleStudyInSchool() {
        try {
            FXMLLoader fxmlLoaderFamilleStudyInSchool = new FXMLLoader();
            fxmlLoaderFamilleStudyInSchool.setLocation(ApplicationCRKF.class.getResource("familly_study_in_ecole.fxml"));
            VBox familleStudyInSchool = fxmlLoaderFamilleStudyInSchool.load();
            FamilleEnseigneeController familleEnseigneeController = fxmlLoaderFamilleStudyInSchool.getController();
            familleEnseigneeController.setEcole(ecole);
            familleEnseigneeController.setEcoleController(ecoleController);
            drawer.setSidePane(familleStudyInSchool);
            drawer.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeDetail(){
        ecoleController.closeDetail();
    }
    public void setDrawer(JFXDrawer drawer){
        this.drawer = drawer;
    }

    public void setEcoleController(EcoleController ecoleController) {
        this.ecoleController = ecoleController;
    }

}
