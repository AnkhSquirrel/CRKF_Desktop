package fr.kyo.crkf;

import fr.kyo.crkf.Entity.*;

import fr.kyo.crkf.controller.*;
import fr.kyo.crkf.controller.ecole.EcoleAroundProfesseurController;
import fr.kyo.crkf.controller.ecole.EcoleController;
import fr.kyo.crkf.controller.ecole.EcoleModalController;
import fr.kyo.crkf.controller.instrument.InstrumentModalController;
import fr.kyo.crkf.controller.instrument.DetailInstrumentController;
import fr.kyo.crkf.controller.instrument.InstrumentController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ApplicationCRKF extends javafx.application.Application {

    NavbarController navbarController;
    AccueilController accueilController;
    BorderPane mainWindow = new BorderPane();

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoaderNavbar = new FXMLLoader();
            fxmlLoaderNavbar.setLocation(ApplicationCRKF.class.getResource("navbar.fxml"));
            MenuBar navbar = fxmlLoaderNavbar.load();
            navbarController = fxmlLoaderNavbar.getController();
            navbarController.setMainApp(this);

            openMainMenu();

            mainWindow.setTop(navbar);

            Scene scene = new Scene(mainWindow);

            stage.setTitle("CRKF");
            stage.setMinWidth(840);
            stage.setMinHeight(620);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openMainMenu(){
        try{
            FXMLLoader fxmlLoaderAccueil = new FXMLLoader();
            fxmlLoaderAccueil.setLocation(ApplicationCRKF.class.getResource("accueil.fxml"));
            VBox accueil = fxmlLoaderAccueil.load();
            accueilController = fxmlLoaderAccueil.getController();
            accueilController.setMainApp(this);

            mainWindow.setCenter(accueil);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openProfesseurList() {
        try {
            FXMLLoader fxmlLoaderListeProfesseur = new FXMLLoader();
            fxmlLoaderListeProfesseur.setLocation(ApplicationCRKF.class.getResource("liste_professeur.fxml"));
            GridPane listeProfesseur = fxmlLoaderListeProfesseur.load();
            ProfesseurController professeurController = fxmlLoaderListeProfesseur.getController();
            professeurController.setApplicationCRKF(this);

            mainWindow.setCenter(listeProfesseur);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEcoleList() {
        try {
            FXMLLoader fxmlLoaderListeEcole = new FXMLLoader();
            fxmlLoaderListeEcole.setLocation(ApplicationCRKF.class.getResource("liste_ecole.fxml"));
            GridPane listeEcole = fxmlLoaderListeEcole.load();
            EcoleController ecoleController = fxmlLoaderListeEcole.getController();
            ecoleController.setApplicationCRKF(this);

            mainWindow.setCenter(listeEcole);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openInstrumentList() {
        try {
            FXMLLoader fxmlLoaderListeInstrument = new FXMLLoader();
            fxmlLoaderListeInstrument.setLocation(ApplicationCRKF.class.getResource("liste_instrument.fxml"));
            GridPane listeInstrument = fxmlLoaderListeInstrument.load();
            InstrumentController instrumentController = fxmlLoaderListeInstrument.getController();
            instrumentController.setApplicationCRKF(this);

            mainWindow.setCenter(listeInstrument);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAboutModal(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A Propos");
        alert.setHeaderText("CRKF est un outil de gestion pour conservatoire");
        alert.setContentText("K.Y.O");
        alert.showAndWait();
    }
    public void openDetailInstrument(Instrument instrument){
        try {
            FXMLLoader fxmlLoaderListeInstrument = new FXMLLoader();
            fxmlLoaderListeInstrument.setLocation(ApplicationCRKF.class.getResource("detail_instrument.fxml"));
            AnchorPane detailInstrument = fxmlLoaderListeInstrument.load();
            DetailInstrumentController detailInstrumentController = fxmlLoaderListeInstrument.getController();
            detailInstrumentController.setApplicationCRKF(this);
            detailInstrumentController.setInstrument(instrument);

            mainWindow.setCenter(detailInstrument);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openDetailProfesseur(Personne personne){
        try {
            FXMLLoader fxmlLoaderListeInstrument = new FXMLLoader();
            fxmlLoaderListeInstrument.setLocation(ApplicationCRKF.class.getResource("detail_professeur.fxml"));
            VBox detailProfesseur = fxmlLoaderListeInstrument.load();
            DetailProfesseurController detailProfesseurController = fxmlLoaderListeInstrument.getController();
            detailProfesseurController.setApplicationCRKF(this);
            detailProfesseurController.setPersonne(personne);

            mainWindow.setCenter(detailProfesseur);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openDetailEcole(Ecole ecole){
        try {
            FXMLLoader fxmlLoaderListeEcole = new FXMLLoader();
            fxmlLoaderListeEcole.setLocation(ApplicationCRKF.class.getResource("detail_ecole.fxml"));
            VBox detailEcole = fxmlLoaderListeEcole.load();
            DetailEcoleController detailEcoleController = fxmlLoaderListeEcole.getController();
            detailEcoleController.setApplicationCRKF(this);
            detailEcoleController.setEcole(ecole);

            mainWindow.setCenter(detailEcole);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEcoleAroundPage(Personne personne){
        try {
            FXMLLoader fxmlLoaderEcoleAroundPage = new FXMLLoader();
            fxmlLoaderEcoleAroundPage.setLocation(ApplicationCRKF.class.getResource("ecole_around_page.fxml"));
            VBox ecoleAroundProf = fxmlLoaderEcoleAroundPage.load();
            EcoleAroundProfesseurController ecoleAroundProfesseurController = fxmlLoaderEcoleAroundPage.getController();
            ecoleAroundProfesseurController.setApplicationCRKF(this);
            ecoleAroundProfesseurController.setPersonne(personne);
            mainWindow.setCenter(ecoleAroundProf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void openCreateInstrumentModal(InstrumentController instrumentController) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_instrument.fxml"));
            AnchorPane modalPane = fxmlLoader.load();
            InstrumentModalController instrumentModalController = fxmlLoader.getController();

            instrumentModalController.setModal(modal);
            instrumentModalController.setCreate(true);
            instrumentModalController.setInstrumentController(instrumentController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajouter un instrument");

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCreateEcoleModal(){
        Stage modal = new Stage();
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_ecole.fxml"));
                AnchorPane modalPane = fxmlLoader.load();
                EcoleModalController EcoleModalController = fxmlLoader.getController();

            EcoleModalController.setModal(modal);
            EcoleModalController.setCreate(true);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajout d'une école");

            modal.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void openUpdateEcole(Ecole ecole) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_ecole.fxml"));
            AnchorPane modalPane = fxmlLoader.load();
            EcoleModalController createEcoleModalController = fxmlLoader.getController();

            createEcoleModalController.setModal(modal);
            createEcoleModalController.setCreate(false);
            createEcoleModalController.setEcole(ecole);
            createEcoleModalController.setApplicationCRKF(this);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modification d'une école");

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void openUpdateInstrumentModal(Instrument instrument) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_instrument.fxml"));
            AnchorPane modalPane = fxmlLoader.load();
            InstrumentModalController instrumentModalController = fxmlLoader.getController();

            instrumentModalController.setModal(modal);
            instrumentModalController.setCreate(false);
            instrumentModalController.setInstrumentUpdate(instrument);
            instrumentModalController.setApplicationCRKF(this);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modifier un instrument");

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteModal() {
        boolean delete = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet element?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            delete = true;
        return delete;
    }

    public void openVilleGestion(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ApplicationCRKF.class.getResource("gestion_ecole.fxml"));
            GridPane gestionEcole = fxmlLoader.load();
            GestionFamilleController gestionFamilleController = fxmlLoader.getController();
            gestionFamilleController.setApplicationCRKF(this);

            mainWindow.setCenter(gestionEcole);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openFamilleGestion() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ApplicationCRKF.class.getResource("gestion_famille.fxml"));
            GridPane gestionFamille = fxmlLoader.load();
            GestionFamilleController gestionFamilleController = fxmlLoader.getController();
            gestionFamilleController.setApplicationCRKF(this);

            mainWindow.setCenter(gestionFamille);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openModalCreateFamille(GestionFamilleController gestionFamilleController) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_famille.fxml"));
            AnchorPane modalPane = fxmlLoader.load();
            FamilleModalController familleModalController = fxmlLoader.getController();

            familleModalController.setModal(modal);
            familleModalController.setCreate(true);
            familleModalController.setGestionFamilleController(gestionFamilleController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajouter une famille");

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openModalUpdateFamille(GestionFamilleController gestionFamilleController, Famille famille) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_famille.fxml"));
            AnchorPane modalPane = fxmlLoader.load();
            FamilleModalController familleModalController = fxmlLoader.getController();

            familleModalController.setModal(modal);
            familleModalController.setCreate(false);
            familleModalController.setGestionFamilleController(gestionFamilleController);
            familleModalController.setFamille(famille);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modifier une famille");

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openClassificationGestion() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ApplicationCRKF.class.getResource("gestion_classification.fxml"));
            GridPane gestionClassification = fxmlLoader.load();
            GestionClassificationController gestionClassificationController = fxmlLoader.getController();
            gestionClassificationController.setApplicationCRKF(this);

            mainWindow.setCenter(gestionClassification);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCreateClassificationModal(GestionClassificationController gestionClassificationController) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_classification.fxml"));
            AnchorPane modalPane = fxmlLoader.load();
            ClassificationModalController classificationModalController = fxmlLoader.getController();

            classificationModalController.setModal(modal);
            classificationModalController.setCreate(true);
            classificationModalController.setGestionClassificationController(gestionClassificationController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Ajouter une Classification");

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openUpdateClassificationModal(GestionClassificationController gestionClassificationController, Classification classification) {
        Stage modal = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationCRKF.class.getResource("modal_classification.fxml"));
            AnchorPane modalPane = fxmlLoader.load();
            ClassificationModalController classificationModalController = fxmlLoader.getController();

            classificationModalController.setModal(modal);
            classificationModalController.setCreate(false);
            classificationModalController.setClassification(classification);
            classificationModalController.setGestionClassificationController(gestionClassificationController);

            modal.setScene(new Scene(modalPane));
            modal.setResizable(false);
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(mainWindow.getScene().getWindow());
            modal.setTitle("Modifier une Classification");

            modal.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}