package fr.kyo.crkf.controller;

import com.jfoenix.controls.JFXDrawer;
import fr.kyo.crkf.ApplicationCRKF;
import fr.kyo.crkf.Entity.Diplome;
import fr.kyo.crkf.Entity.Personne;
import fr.kyo.crkf.controller.ecole.EcoleAroundProfesseurController;
import fr.kyo.crkf.controller.ecole.EcoleController;
import fr.kyo.crkf.controller.instrument.InstrumentController;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;

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
    @FXML
    private JFXDrawer drawer;
    private ApplicationCRKF applicationCRKF;
    private Personne personne;
    private ProfesseurController professeurController;

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
        try {
            FXMLLoader fxmlLoaderEcoleAroundPage = new FXMLLoader();
            fxmlLoaderEcoleAroundPage.setLocation(ApplicationCRKF.class.getResource("ecole_around_page.fxml"));
            VBox ecoleAroundProf = fxmlLoaderEcoleAroundPage.load();
            EcoleAroundProfesseurController ecoleAroundProfesseurController = fxmlLoaderEcoleAroundPage.getController();
            ecoleAroundProfesseurController.setApplicationCRKF(applicationCRKF);
            ecoleAroundProfesseurController.setPersonne(personne);
            ecoleAroundProfesseurController.setProfesseurController(professeurController);
            drawer.setSidePane(ecoleAroundProf);
            drawer.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeDetail(){
        professeurController.closeDetail();
    }
    @FXML
    private void openUpdateModal(){
        applicationCRKF.openUpdateProfesseurModal(professeurController, personne);
        professeurController.filter();
    }
    @FXML
    private void delete(){
        if(applicationCRKF.deleteModal()){
            DAOFactory.getPersonneDAO().delete(personne);
            professeurController.filter();
            professeurController.closeDetail();
        }
    }

    public void setProfesseurController(ProfesseurController professeurController){
        this.professeurController = professeurController;
    }

    public void setDrawer(JFXDrawer drawer){
        this.drawer = drawer;
    }
}
